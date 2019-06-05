from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.util import EncryptDecrypt
from cn.edustar.push import PushData
from cn.edustar.jitar.jython import JythonBaseAction
from cn.edustar.push import MashupUser
from java.util import Date
from javax.servlet.http import Cookie

class userenter(JythonBaseAction):
    ERROR = "/WEB-INF/ftl/Error.ftl"
    ACCESS_DENIED = "/WEB-INF/ftl/Error.ftl"
    ACCESS_ERROR = "/WEB-INF/ftl/mengv1/access_error.ftl"
    SUCCESS = "/WEB-INF/ftl/success.ftl"
    LOGIN = "/login.jsp"
    
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        fromUrl = self.params.safeGetStringParam("u")
        userEncGuid = self.params.safeGetStringParam("g")
        userTitle = self.params.safeGetStringParam("t")
        unitName = self.params.safeGetStringParam("n")
        if fromUrl == "" or userEncGuid == "":
            self.addActionError(u"无效的参数 !")
            return self.ERROR
        
        servletContext = request.getSession().getServletContext()
        privateKey = servletContext.getInitParameter("privateKey")
        if privateKey == None or privateKey == "":
            privateKey = "www.chinaedustar.com"
        
        
        des = EncryptDecrypt(privateKey)
        userGuid = des.decrypt(userEncGuid)
        
        # 验证是否登录
        validateUrl = fromUrl + "push/validate_user.py?g=" + userGuid

        pd = PushData()
        ret = pd.executeGetRequest(validateUrl)
        if ret == "OK":
            #将登陆信息附加到市平台
            session.setAttribute("platuser",userGuid)
            mashupService = __spring__.getBean("mashupService")
            mashupUser = mashupService.getMashupUserByGuid(userGuid)
            if mashupUser == None:
                mashupUser = MashupUser()
                mashupUser.setMashupUserGuid(userGuid)
                mashupUser.setFromUrl(fromUrl)
                mashupUser.setTitle(userTitle)
                mashupUser.setUnitName(unitName)
            mashupUser.setLastUpdated(Date())       
            mashupService.saveOrUpdateMashupUser(mashupUser)
            #转向到市平台
            cityUrl = self.get_site_url()
            # print "cityUrl = ",cityUrl
            # 构造会话存储，目前可以存储在 Cookie 里面
            self.saveToCookie(userEncGuid)
            response.sendRedirect(cityUrl)
            
        else:
            self.addActionLink(u"请重新登陆后再进入 !", fromUrl + "login.jsp")
            return self.ERROR

        
    def saveToCookie(self, userEncGuid):
        namecookie = Cookie("mashup", userEncGuid)
        namecookie.setMaxAge(60 * 60)
        namecookie.setPath("/")
        response.addCookie(namecookie)
        
    def get_site_url(self):
        url = request.getScheme() + "://" + request.getServerName()
        if request.getServerPort() != 80:
            url = url + ":" + str(request.getServerPort())
        url = url + request.getContextPath() + "/"
        return url
