from leaveword_query import LeaveWordQuery
from java.util import HashMap
from cn.edustar.jitar.util import FileCache
from cn.edustar.jitar.model import SiteUrlModel
from cn.edustar.jitar.util import ParamUtil

class user_leaveword:
    def __init__(self):
        self.params = ParamUtil(request)        
        self.userService = __jitar__.userService
        
    def execute(self):
        self.userName = request.getAttribute("loginName")
        if self.userName == None or self.userName == "":
            response.getWriter().write(u"没有该用户。")
            return
        user = self.userService.getUserByLoginName(self.userName)
        if user == None:
            response.getWriter().write(u"不能加载当前用户。")
            return
        fc = FileCache()
        content = fc.getUserFileCacheContent(self.userName, "user_leaveword.html",60)
        if content != "":
            response.getWriter().write(content)
            fc = None
            return
        
        count = self.params.safeGetIntParam("count")
        if count == 0:count = 10
        qry = LeaveWordQuery(" lwd.userId,lwd.createDate, lwd.loginName, lwd.title, lwd.content")
        qry.objId =  user.userId
        qry.objType = 1
        qry.orderType = 0        
        leaveword_list = qry.query_map(count)
        templateProcessor = __spring__.getBean("templateProcessor")
        map = HashMap()
        map.put("user", user)
        map.put("leaveword_list", leaveword_list)
        map.put("UserSiteUrl", self.getUserSiteUrl())
        content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_leaveword.ftl", "utf-8")
        
        fc.writeUserFileCacheContent(self.userName, "user_leaveword.html",content)        
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