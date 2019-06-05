from cn.edustar.jitar.util import ParamUtil
from base_action import *
from base_blog_page import *
from java.util import HashMap
from cn.edustar.jitar.util import FileCache
from cn.edustar.jitar.model import SiteUrlModel

class user_profile(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        self.userName = request.getAttribute("UserName")
        userService = __jitar__.userService
        user = userService.getUserByLoginName(self.userName)
        if user == None:
            response.getWriter().write(u"无法加载当前用户对象，")
            return
        fc = FileCache()
        # 14400 为10 天
        content = fc.getUserFileCacheContent(self.userName, "user_profile.html",14400)
        if content != "":
            response.getWriter().write(content)
            fc = None
            return
        
        templateProcessor = __spring__.getBean("templateProcessor")
        map = HashMap()
        map.put("user", user)
        map.put("UserSiteUrl", self.getUserSiteUrl())
        content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_profile.ftl", "utf-8")
        
        fc.writeUserFileCacheContent(self.userName, "user_profile.html",content)        
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