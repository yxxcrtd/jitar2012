from base_action import BaseAction
from cn.edustar.jitar.pojos import Action
from cn.edustar.jitar.util import ParamUtil
from action_query import ActionQuery
from site_config import SiteConfig
from cn.edustar.jitar.data import *
from base_action import BaseAction


class actionlist(BaseAction):
    
    def __init__(self):
        self.params = ParamUtil(request)
    
    def execute(self):
        
        self.out = response.writer
        if self.loginUser == None:
            self.out.write(u"请先<a href='../../login.jsp'>登录</a>，然后才能管理活动")
            return
        
        site_config = SiteConfig()
        site_config.get_config()
        show_type = self.params.getStringParam("type")
        
        self.pager = self.params.createPager()
        self.pager.itemName = u"活动"
        self.pager.itemUnit = u"个"
        qry = ActionQuery(""" act.title, act.createDate, act.actionId, act.ownerId, act.ownerType, act.createUserId, act.actionType,
                              act.description, act.userLimit, act.startDateTime,act.finishDateTime, act.attendLimitDateTime, act.place,
                              act.status, act.visibility, act.attendCount
                            """)
        if show_type == "owner":
            qry.createUserId = self.loginUser.userId
            
        self.pager.totalRows = qry.count()
        action_list = qry.query_map(self.pager)
        request.setAttribute("action_list", action_list)
        request.setAttribute("pager", self.pager)
        response.setContentType("text/html; charset=UTF-8")
        
        if show_type == "owner":
            return "/WEB-INF/ftl/action/action_manage_list.ftl"
        else:
            return "/WEB-INF/ftl/action/action_list.ftl"