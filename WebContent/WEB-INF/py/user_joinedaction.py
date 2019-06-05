from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command
from base_blog_page import *
from base_action import BaseAction
from action_query import ActionQuery
user_svc = __jitar__.userService

class user_joinedaction(BaseAction, RequestMixiner, ResponseMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
    
    def execute(self):
        self.loginName = request.getAttribute("loginName")
        # 加载当前用户对象.
        self.user = user_svc.getUserByLoginName(self.loginName)
        request.setAttribute("user", self.user)
        qry = ActionQuery(""" act.title, act.createDate, act.actionId, act.ownerId, act.ownerType, act.createUserId, act.actionType,
                              act.description, act.userLimit, act.startDateTime,act.finishDateTime, act.attendLimitDateTime, act.place,
                              act.status, act.visibility, act.attendCount
                            """)
        #qry.ownerType = "user"
        qry.status = 0
        qry.userId = self.user.userId
        action_list = qry.query_map(10)
        request.setAttribute("action_list", action_list)
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/user/default/user_joinedaction.ftl"