from base_action import BaseAction
from cn.edustar.jitar.pojos import Action,ActionUser,ActionReply
from cn.edustar.jitar.util import ParamUtil
from action_query import ActionQuery
from cn.edustar.jitar.data import *
from action_reply_query import ActionReplyQuery

class delete_reply(BaseAction):
    
    act_svc = __jitar__.actionService
        
    def execute(self):
        self.params = ParamUtil(request)
        actionId = self.params.getIntParam("actionId")
        actionReplyId = self.params.getIntParam("actionReplyId")
        #print "actionReplyId=",(actionId == None or actionReplyId == None)
        #print "actionId=",actionReplyId == None
        if actionId == None or actionReplyId == None :
            self.addActionError(u"缺少标识。")
            return self.ERROR
        self.action = self.act_svc.getActionById(actionId)
        if self.action == None:
            self.addActionError(u"该活动不存在。")
            return self.ERROR
        if self.act_svc.canManageAction(self.action, self.loginUser) == False:
            self.addActionError(u"你无权删除活动的回复。需要管理员，协作组管理员，集备创建者，集备主备人才可以执行删除操作。")
            return self.ERROR
        else:            
            self.act_svc.deleteActionReply(actionReplyId)
            response.sendRedirect("showAction.py?actionId=" + str(actionId))