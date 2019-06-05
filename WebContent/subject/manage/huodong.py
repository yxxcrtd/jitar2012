from subject_page import *
from action_query import ActionQuery

class huodong(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)

    def execute(self):
        if self.loginUser == None:
            return self.LOGIN

        if self.isAdmin == False and self.isContentAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        if request.getMethod() == "POST":
            self.post_action()
            self.clear_subject_cache()
        
        return self.action_list()
        
    def action_list(self):
        qry = ActionQuery(""" act.actionId, act.title, act.ownerType, act.ownerId, act.createDate, act.startDateTime,
                              act.finishDateTime, act.createUserId, act.actionType, act.status """)
        qry.ownerId = self.subject.subjectId
        qry.ownerType = "subject"
        qry.status = None
        pager = self.params.createPager()
        pager.itemName = u"活动"
        pager.itemUnit = u"个"
        pager.pageSize = 25
        pager.totalRows = qry.count()
        action_list = qry.query_map(pager)
        request.setAttribute("subject", self.subject)
        request.setAttribute("action_list", action_list)
        return "/WEB-INF/subjectmanage/action.ftl"
    
    def post_action(self):
        act_svc = __spring__.getBean("actionService")
        cmd = self.params.safeGetStringParam("cmd")
        if cmd == "":
            action_status = -200
        else:
            action_status = int(cmd) 
        guids = self.params.safeGetIntValues("guid")
        for id in guids:
            action = act_svc.getActionById(id)
            if action != None:
                if action_status == -2:
                    act_svc.deleteAction(action)
                else:
                    act_svc.updateActionStatus(action_status, id)
