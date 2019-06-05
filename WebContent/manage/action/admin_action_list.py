from base_action import BaseAction
from cn.edustar.jitar.pojos import Action
from cn.edustar.jitar.util import ParamUtil
from action_query import ActionQuery
from cn.edustar.jitar.data import *
from base_action import BaseAction
from cn.edustar.jitar.jython import BaseAdminAction


class admin_action_list(BaseAdminAction):
    
    def __init__(self):
        self.params = ParamUtil(request)
    
    def execute(self):
        if self.loginUser == None:
            self.out.println(u"请先<a href='../../login.jsp'>登录</a>，然后才能管理活动")
            return
        accessControlService = __spring__.getBean("accessControlService")
        if False == accessControlService.isSystemContentAdmin(self.loginUser):
            ActionError(u"没有管理权限.")
            return ActionResult.ERROR
        
        self.pager = self.params.createPager()
        self.pager.itemName = u"活动"
        self.pager.itemUnit = u"个"
        qry = ActionQuery(""" act.title, act.createDate, act.actionId, act.ownerId, act.ownerType, act.createUserId, act.actionType,
                              act.description, act.userLimit, act.startDateTime,act.finishDateTime, act.attendLimitDateTime, act.place,
                              act.status, act.visibility, act.attendCount,u.loginName,u.trueName
                            """)
            
        self.pager.totalRows = qry.count()
        action_list = qry.query_map(self.pager)
        request.setAttribute("action_list", action_list)
        request.setAttribute("pager", self.pager)
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/action/admin_action_list.ftl"