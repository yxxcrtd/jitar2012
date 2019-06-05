from base_action import BaseAction
from friend_query import FriendQuery
from cn.edustar.jitar.util import ParamUtil
from java.util import HashMap
from cn.edustar.jitar.util import FileCache
from cn.edustar.jitar.model import SiteUrlModel
from base_blog_page import *

class user_friendlinks(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
        self.userService = __jitar__.userService
        
    def execute(self):
        self.userName = request.getAttribute("UserName") 
        user = self.userService.getUserByLoginName(self.userName)
        if self.canVisitUser(user) == False:
            response.writer.println(u"用户 "+self.userName+u"不存在 ")
            return
        fc = FileCache()
        content = fc.getUserFileCacheContent(self.userName, "user_friendlinks.html",30)
        if content != "":
            response.getWriter().write(content)
            fc = None
            return
        
        count = self.params.safeGetIntParam("count")
        if count == 0:count = 10
        
        qry = FriendQuery("frd.userId, frd.friendId, u.trueName, u.nickName, u.loginName, u.userIcon, u.qq")
        qry.userId = user.getUserId()
        friend_list = qry.query_map(count)
        templateProcessor = __spring__.getBean("templateProcessor")
        map = HashMap()
        map.put("friend_list", friend_list)
        map.put("UserSiteUrl", self.getUserSiteUrl())
        content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_friendlinks.ftl", "utf-8")
        fc.writeUserFileCacheContent(self.userName, "user_friendlinks.html",content)        
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