from cn.edustar.jitar.util import ParamUtil
from site_config import SiteConfig
from base_action import ActionExecutor,ActionResult
from cn.edustar.jitar.service import ChatMessageService
from cn.edustar.jitar.pojos import ChatMessage
from cn.edustar.jitar.service import ChatUserService
from cn.edustar.jitar.pojos import ChatUser
from cn.edustar.jitar.pojos import User

class saveMessage(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
        self.usermsg_svc = __spring__.getBean("chatMessageService")
        self.userchat_svc = __spring__.getBean("chatUserService")
        self.user_svc = __jitar__.userService
        
    def execute(self):      
        if self.loginUser == None:
            return
        userName = self.loginUser.trueName
        if userName == None:
            userName = self.loginUser.loginName
        userId = self.loginUser.userId
        roomId = self.params.getStringParam("roomId")
        sMsg = self.params.getStringParam("txtword")
        seluser = self.params.getStringParam("seluser")
        selcolor = self.params.getStringParam("selcolor")
        selface = self.params.getStringParam("selface")
        IsPrivate= self.params.getStringParam("IsPrivate")
        face= self.params.getStringParam("face")
        if face==None or face=="":
            face=""
        if selcolor==None or selcolor=="":
            selcolor="#000000"
        receiverColor="#000000"
        
        sMsg=sMsg.replace("<","&lt;")
        sMsg=sMsg.replace(">","&gt;")
        sMsg=sMsg.replace("\r\n","<br/>")
        sMsg=sMsg.replace("\n\r","<br/>")
        sMsg=sMsg.replace("\n","<br/>")
        sMsg=sMsg.replace("\r","<br/>")
        chatMessage=ChatMessage()
        chatMessage.setRoomId(int(roomId))
        chatMessage.setSenderId(userId)
        chatMessage.setSenderName(userName)
        chatMessage.setReceiverId(int(seluser))
        if int(seluser)>0 :
            receiverUser=self.user_svc.getUserById(int(seluser))
            chatMessage.receiverName=receiverUser.trueName
            chtuser=self.userchat_svc.getChatUser(int(roomId),int(seluser))
            if chtuser==None:
                receiverColor="#000000"
            else:    
                receiverColor=chtuser.getFontColor()
        else :
            chatMessage.setReceiverName(u"大家")
        chatMessage.talkContent=sMsg
        if int(seluser)>0 :
            chatMessage.setIsSendAll(False)
        else :
            chatMessage.setIsSendAll(True)
        if IsPrivate==None or IsPrivate=="" :
            chatMessage.setIsPrivate(False)
        else:
            chatMessage.setIsPrivate(True)
        chatMessage.setFaceImg(face)
        chatMessage.setSenderColor(selcolor)
        chatMessage.setReceiverColor(receiverColor)    
        self.usermsg_svc.SaveChatMessage(chatMessage)
        
        self.userchat_svc.saveChatUserFontColor(int(roomId),userId,selcolor)
        return "/WEB-INF/ftl/chat/savemessage.ftl"