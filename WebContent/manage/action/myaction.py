from base_blog_page import *
from base_action import BaseAction
from cn.edustar.jitar.pojos import Action
from cn.edustar.jitar.util import ParamUtil
from action_user_query import ActionUserUnitQuery
from cn.edustar.jitar.data import Command

class myaction(BaseAction):
    act_svc = __jitar__.actionService
    
    def execute(self):
        self.params = ParamUtil(request)
        self.out = response.writer
        if self.loginUser == None:
            self.addActionError(u"请先<a href='../../login.jsp'>登录</a>")
            return self.ERROR
        
        if request.getMethod() == "POST":
            return self.actionPost()
        else:                    
            qry = ActionUserUnitQuery(""" actu.actionUserId, actu.actionId, actu.actionUserId, actu.title,actu.startDateTime,actu.createDate,
                                              actu.actionUserStatus, actu.actionStatus, actu.ownerType, actu.ownerId
                                         """)
            qry.userId = self.loginUser.userId
            pager = self.params.createPager()
            pager.itemName = u"活动"
            pager.itemUnit = u"个"
            pager.pageSize = 10
            pager.totalRows = qry.count()
            user_action = qry.query_map(pager)
            request.setAttribute("user_action", user_action)
            request.setAttribute("pager", pager)
            return "/WEB-INF/ftl/action/myaction.ftl"
        
    def actionPost(self):
        ErrMsg = ""
        cmd = self.params.getStringParam("cmd")
        guids = self.params.safeGetIntValues("guid")
        if guids.size() < 1:
            self.addActionError(u"请先选择一个活动")
            self.addActionLink(u"返回", "myaction.py")
            return self.ERROR
        for actId in guids:
            action = self.act_svc.getActionByActionUserId(actId)            
            if action.status != 0:
                ErrMsg = ErrMsg + u"<li>名为 " + action.title + u" 的活动不是一个正常状态。无法进行操作."
                continue
            if cmd == "attend":
                qry = """ UPDATE ActionUser Set status = 1 WHERE actionUserId = :actionUserId """
                command = Command(qry)
                command.setInteger("actionUserId", actId)
                command.update()
            if cmd == "quit":
                qry = """ UPDATE ActionUser Set status = 2 WHERE actionUserId = :actionUserId"""
                command = Command(qry)
                command.setInteger("actionUserId", actId)
                command.update()
            if cmd == "leave":
                qry = """ UPDATE ActionUser Set status = 3 WHERE actionUserId = :actionUserId """
                command = Command(qry)
                command.setInteger("actionUserId", actId)
                command.update()
        if ErrMsg == "":ErrMsg = u"操作已完成"
        self.addActionMessage(ErrMsg)
        self.addActionLink(u"返回", "myaction.py")
        return self.SUCCESS
