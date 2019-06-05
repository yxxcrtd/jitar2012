from java.util import HashMap
from cn.edustar.jitar.util import FileCache
from cn.edustar.jitar.model import SiteUrlModel
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command

# 用户相册分类 .
class photo_category:
    def __init__(self):
        self.params = ParamUtil(request)
    
    def execute(self):
        #print "Hello"
        writer = response.writer
        #self.params = ParamUtil(request)
        
        loginName = request.getAttribute("loginName")
        if(loginName == None or loginName == ''):
            writer.write(u"没有该用户。")
            return
        user = __jitar__.userService.getUserByLoginName(loginName)
        if user == None:
            writer.write(u"无法加载当前用户。")
            return
        self.userName = user.loginName
        fc = FileCache()
        content = fc.getUserFileCacheContent(self.userName, "photo_category.html",144000)
        if content != "":
            response.getWriter().write(content)
            fc = None
            return
        
        hql = """select new Map(stap.id as id, stap.title as title)
              from PhotoStaple stap
              where stap.isHide = 0 and stap.userId = """ + str(user.userId) + """
              ORDER BY stap.orderNum ASC
              """
        photocate = Command(hql).open()
        templateProcessor = __spring__.getBean("templateProcessor")
        map = HashMap()
        map.put("photocate", photocate)
        map.put("user", user)
        map.put("UserSiteUrl", self.getUserSiteUrl())
        content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/photo_category.ftl", "utf-8")
        
        fc.writeUserFileCacheContent(self.userName, "photo_category.html",content)        
        response.getWriter().write(content)
        fc = None
        
    def getUserSiteUrl(self):
        siteUrl = SiteUrlModel.getSiteUrl()
        userSiteUrl = request.getSession().getServletContext().getInitParameter("userUrlPattern");
        if userSiteUrl == None or userSiteUrl == "":
            userSiteUrl = siteUrl + "u/" + self.userName + "/"
        else:
            userSiteUrl = userSiteUrl.replace("{loginName}", self.userName)            
        return userSiteUrl