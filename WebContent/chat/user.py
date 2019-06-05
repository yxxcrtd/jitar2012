from cn.edustar.jitar.util import ParamUtil
from site_config import SiteConfig
from cn.edustar.jitar.data import Command
from base_action import ActionExecutor, ActionResult
from cn.edustar.jitar.model import Configure
from cn.edustar.jitar.pojos import ChatUser

class user(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
        self.chatuser_svc = __spring__.getBean("chatUserService")
        
    def execute(self):
        request.setAttribute("ChatRoomName", "聊天室")
        if self.loginUser == None:
            response.getWriter().write("请先登录。")
            print "请先登录。"
            return
        roomId = self.params.getStringParam("roomId")
        if roomId == None or roomId == "":
            roomId = "1"
        userid = self.loginUser.getUserId();
        userName = self.loginUser.getTrueName();
        
        chatUser = self.chatuser_svc.getCacheChatUser(int(roomId), userid)
        if chatUser == None:
            chatUser = ChatUser()
            chatUser.userId = userid
            chatUser.roomId = int(roomId)
            chatUser.isLeave = False
            chatUser.isActived = True
            chatUser.userName = userName
            self.chatuser_svc.saveChatUser(chatUser)
        # 设置当前自己是在线状态
        self.chatuser_svc.updateChatUserIsLeave(int(roomId), userid, False)
        
        roomUserList = self.chatuser_svc.getCacheChatUsers(int(roomId))
        userList = []
        for u in roomUserList :
            if u.isLeave == False and (u in userList) == False:
                userList.append(u)
                
        request.setAttribute("roomId", roomId)
        request.setAttribute("userList", userList)
        return "/WEB-INF/ftl/chat/user.ftl"