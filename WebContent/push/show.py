from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.jython import JythonBaseAction
from cn.edustar.jitar.util import EncryptDecrypt
from cn.edustar.push import PushData

class show(JythonBaseAction):
    ERROR = "/WEB-INF/ftl/Error.ftl"
    ACCESS_DENIED = "/WEB-INF/ftl/Error.ftl"
    ACCESS_ERROR = "/WEB-INF/ftl/mengv1/access_error.ftl"
    SUCCESS = "/WEB-INF/ftl/success.ftl"
    LOGIN = "/login.jsp"    
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.fromType = ""
        self.userGuid = ""
        
    def execute(self):
        type = self.params.safeGetStringParam("type")
        orginId = self.params.safeGetIntParam("orginId")
        self.userGuid = self.params.safeGetStringParam("g")
        self.fromType = self.params.safeGetStringParam("from")
        
        #判断参数
        if type == "" or orginId == 0:
            self.addActionError(u"无效的参数 !")
            return self.ERROR
        
        cfg_svc = __spring__.getBean("configService")
        config = cfg_svc.getConfigure()
        if config == None:
            self.addActionError(u"不能加载配置对象 !")
            return self.ERROR
        if None == config["topsite_url"]:
            self.addActionError(u" topsite_url 配置项没配置!")
            return self.ERROR
        
        self.topsiteUrl = str(config["topsite_url"])
        
        servletContext = request.getSession().getServletContext()
        privateKey = servletContext.getInitParameter("privateKey")
        if privateKey == None or privateKey == "":
            privateKey = "www.chinaedustar.com"
        if self.userGuid != "":            
            des = EncryptDecrypt(privateKey)
            self.userGuid = des.decrypt(self.userGuid)
            session.setAttribute("platuser",self.userGuid)

        if type == "article":
            # 文章无权限要求            
            response.sendRedirect(self.get_site_url() + "go.py?articleId=" + str(orginId))
        elif type == "resource":
            # 需要验证
            if self.userGuid == "":
                self.addActionError(u"没有登录信息。")
                return self.ERROR
            if self.validateLoginUser() == False:
                self.addActionError(u"您的登录信息已经失效，请重新回到自己区县的站点登录。")
                return self.ERROR
            
            response.sendRedirect(self.get_site_url() + "showResource.py?resourceId=" + str(orginId))
        else:
            self.addActionError(u"不确定的转向。")
            return self.ERROR
        
    def get_site_url(self):
        url = request.getScheme() + "://" + request.getServerName()
        if request.getServerPort() != 80:
            url = url + ":" + str(request.getServerPort())
        url = url + request.getContextPath() + "/"
        return url
    
    def validateLoginUser(self):        
        pd = PushData()
        url = self.topsiteUrl + "mashup/validate_mashupuser.py?g=" + self.userGuid + "&from=" + self.fromType
        ret = pd.executeGetRequest(url)
        if ret == "OK":
            return True
        else:
            return False