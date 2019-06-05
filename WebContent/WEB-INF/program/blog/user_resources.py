from resource_query import ResourceQuery
from java.util import HashMap
from cn.edustar.jitar.util import FileCache
from cn.edustar.jitar.model import SiteUrlModel

class user_resources:
    userService = __jitar__.userService
    def execute(self):
        self.userName = request.getAttribute("loginName")
        if self.userName == None or self.userName == '':
            response.getWriter().write(u"没有该用户。")
            return
        user = self.userService.getUserByLoginName(self.userName)
        if user == None:
            response.getWriter().write(u"不能加载当前用户。")
            return
        fc = FileCache()
        content = fc.getUserFileCacheContent(self.userName, "user_resources.html",30)
        if content != "":
            response.getWriter().write(content)
            fc = None
            return
        qry = ResourceQuery(" r.createDate, r.resourceId, r.title, r.href")
        qry.userId =  user.userId
        qry.orderType = 0        
        resource_list = qry.query_map(10)
        templateProcessor = __spring__.getBean("templateProcessor")
        map = HashMap()
        map.put("resource_list", resource_list)
        map.put("UserSiteUrl", self.getUserSiteUrl())
        content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_resources.ftl", "utf-8")
        
        fc.writeUserFileCacheContent(self.userName, "user_resources.html",content)        
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