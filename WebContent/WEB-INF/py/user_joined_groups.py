from group_member_query import GroupMemberQuery
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command
from user_query import UserQuery
from base_action import BaseAction
from base_blog_page import RequestMixiner,ResponseMixiner

class user_joined_groups (BaseAction, RequestMixiner, ResponseMixiner):
    user_svc = __jitar__.userService
    def execute(self):
        self.params = ParamUtil(request)
        loginName = request.getAttribute("loginName")
        user = self.user_svc.getUserByLoginName(loginName)
        userId = self.params.safeGetIntParam("userId", 0)
        if user == None:
            user = self.user_svc.getUserById(userId)
            
        if self.canVisitUser(user) == False:
            response.writer.println(u"用户 %s 不存在 " % loginName)
            return
        
        count = self.params.safeGetIntParam("count")
        if count == None or count == 0 : count = 10
        
        # 得到参加的协作组列表.
        qry = GroupMemberQuery(""" gm.id, gm.group, g.groupName, g.groupId, g.groupTitle, g.groupIcon, g.topicCount, g.userCount, g.articleCount, g.resourceCount, g.visitCount """)
        qry.userId = user.userId
        qry.memberStatus = 0
        qry.groupStatus = 0
        qry.orderType = 0        
        
        group_list = qry.query_map(count)
        request.setAttribute("group_list", group_list)
        return "/WEB-INF/user/default/joined_groups.ftl"