from cn.edustar.jitar.util import ParamUtil
from site_config import SiteConfig
from base_action import ActionExecutor, ActionResult
from cn.edustar.jitar.service import ChatMessageService
from cn.edustar.jitar.pojos import ChatMessage
from chatMsg_Query import ChatMsgQuery

class chatMsgList(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
        self.usermessage_svc = __spring__.getBean("chatMessageService")
    def execute(self):
        roomId = self.params.safeGetIntParam("roomId")
        if roomId == None or roomId == 0:
            roomId = 1

        qry = ChatMsgQuery(""" roomId as roomId,senderId as senderId,senderName as senderName,receiverId as receiverId,receiverName as receiverName,talkContent as talkContent,sendDate as sendDate,faceImg as faceImg,senderColor as senderColor,receiverColor as receiverColor """)
        qry.roomId = str(roomId)
        pager = self.params.createPager()
        pager.itemName = u"信息"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        msgList = qry.query_map(pager)
        request.setAttribute("pager", pager)        
        request.setAttribute("msgList" , msgList)
        request.setAttribute("list_type", u"搜索")
        request.setAttribute("roomId", roomId)
        return "/WEB-INF/ftl/chat/chatMsgList.ftl"
