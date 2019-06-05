from cn.edustar.jitar.util import ParamUtil
from site_config import SiteConfig
from base_action import ActionExecutor, ActionResult
from cn.edustar.jitar.service import ChatMessageService
from cn.edustar.jitar.pojos import ChatMessage

class getMsg(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
        self.usermessage_svc = __spring__.getBean("chatMessageService")
    def execute(self):
        site_config = SiteConfig()
        site_config.get_config()        
        if self.loginUser == None:
            return
        userId = self.loginUser.userId
        userName = self.loginUser.trueName
        if userName == None:
            userName = self.loginUser.loginName
        lastId = self.params.safeGetIntParam("lastId")
        roomId = self.params.safeGetIntParam("roomId")
        if roomId == 0:
            roomId = 1
             
        #得到新的聊天信息
        msgList = self.usermessage_svc.getNewChatMessages(roomId, lastId);
        if msgList.size() > 0:
            lastId = msgList[msgList.size() - 1].chatMessageId
        request.setAttribute("roomId", roomId)
        request.setAttribute("lastId", lastId)
        request.setAttribute("msgList", msgList)
        return "/WEB-INF/ftl/chat/newMsg.ftl"
