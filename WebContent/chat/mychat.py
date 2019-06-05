from cn.edustar.jitar.util import ParamUtil
from site_config import SiteConfig
from base_action import ActionExecutor, ActionResult
from cn.edustar.jitar.service import ChatMessageService
from cn.edustar.jitar.pojos import ChatMessage

class mychat(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
        self.usermessage_svc = __spring__.getBean("chatMessageService")
    def execute(self):   
        if self.loginUser == None:
            return
        userId = self.loginUser.userId
        userName = self.loginUser.trueName
        if userName == None:
            userName = self.loginUser.loginName
        #得到聊天信息
        roomId = self.params.safeGetIntParam("roomId")
        if roomId == 0:
            roomId = 1
        msgList = self.usermessage_svc.getTodayChatMessages(roomId)
        lastId = 0
        if msgList.size() > 0:
            lastId = msgList[msgList.size() - 1].chatMessageId
        request.setAttribute("roomId", roomId)
        request.setAttribute("lastId", lastId)
        request.setAttribute("msgList", msgList)       
        
        return "/WEB-INF/ftl/chat/mychat.ftl"