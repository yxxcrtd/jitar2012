from cn.edustar.jitar.util import ParamUtil
from site_config import SiteConfig
from base_action import ActionExecutor, ActionResult
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import ChatUser
from cn.edustar.jitar.jython import BaseAdminAction

class manage(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
        self.userchat_svc = __spring__.getBean("chatUserService")
        
    def execute(self):       
        request.setAttribute("ChatRoomName", u"聊天室")
        if self.loginUser == None:
            return
        userName = self.loginUser.trueName
        if userName == None:
            userName = self.loginUser.loginName
        userId = self.loginUser.userId   
        roomId = self.params.safeGetIntParam("roomId") 
        if roomId == None or roomId == 0:
            roomId = 1
        qry = Command(""" SELECT new Map(userId as userId,userName as userName) FROM ChatUser Where isLeave=0 and roomId=""" + str(roomId) + """ ORDER BY sayDate desc """)
        userList = qry.open()

        qry = Command(" SELECT new Map(colorName as colorName,colorValue as colorValue) FROM ChatColor ")
        colorList = qry.open()        
        #得到自己的颜色
        
        chtuser = self.userchat_svc.getChatUser(roomId, userId)
        if chtuser == None:
            chatUser = ChatUser()
            chatUser.userId = userId
            chatUser.roomId = roomId
            chatUser.isLeave = False
            chatUser.isActived = True
            chatUser.userName = userName
            self.userchat_svc.saveChatUser(chatUser)
        elif chtuser.isActived == False:
            self.userchat_svc.updateChatUserIsLeave(roomId, userId, False)
            
        chtuser = self.userchat_svc.getChatUser(roomId, userId)
        mycolor = chtuser.fontColor
        if mycolor == None or mycolor == "":
            mycolor = "#000000"
        request.setAttribute("mycolor", mycolor)
        request.setAttribute("roomId", roomId)
        request.setAttribute("userList", userList)
        
        request.setAttribute("colorList", colorList)
        return "/WEB-INF/ftl/chat/manage.ftl"