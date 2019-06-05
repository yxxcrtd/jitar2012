from group_member_query import GroupMemberQuery
from cn.edustar.jitar.data import Command
from user_query import UserQuery
from base_action import BaseAction
from base_blog_page import RequestMixiner,ResponseMixiner
from cn.edustar.jitar.util import ParamUtil
from java.util import HashMap
from cn.edustar.jitar.util import FileCache
from cn.edustar.jitar.model import SiteUrlModel

class user_joined_groups (BaseAction, RequestMixiner, ResponseMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
        self.userService = __jitar__.userService
        
    def execute(self):
        self.userName = request.getAttribute("UserName")        
        fc = FileCache()
        content = fc.getUserFileCacheContent(self.userName, "user_joined_groups.html",14400)
        if content != "":
            response.getWriter().write(content)
            fc = None
            return        
        count = self.params.safeGetIntParam("count")
        if count == 0:count = 10
        user = self.userService.getUserByLoginName(self.userName)
        if self.canVisitUser(user) == False:
            response.writer.println(u"用户  " + self.userName + u" 不存在 ")
            return
        
        # 得到参加的协作组列表.
        qry = GroupMemberQuery(""" gm.id, gm.group, g.groupName, g.groupId, g.groupTitle,  g.userCount """)
        qry.userId = user.userId
        qry.memberStatus = 0
        qry.groupStatus = 0
        qry.orderType = 0
        group_list = qry.query_map(count)
        
        templateProcessor = __spring__.getBean("templateProcessor")
        map = HashMap()
        map.put("user", user)
        map.put("group_list", group_list)
        map.put("UserSiteUrl", self.getUserSiteUrl())
        content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_joined_groups.ftl", "utf-8")
        
        fc.writeUserFileCacheContent(self.userName, "user_joined_groups.html",content)        
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