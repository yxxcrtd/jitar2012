from cn.edustar.jitar.util import ParamUtil
from user_query import UserQuery
from site_config import SiteConfig
from java.lang import *
from cn.edustar.chat import OneUser
from cn.edustar.chat import ChatRoom

class chat:

    def __init__(self):
        self.params = ParamUtil(request)
    
    def execute(self):
        site_config = SiteConfig()
        site_config.get_config()
        request.setAttribute("ChatRoomName", u"聊天室")
        if self.loginUser == None:
            print u"请先登录。"
            return
        userName = self.loginUser.trueName
        if userName == None:
            userName = self.loginUser.loginName
        thisUser = OneUser()
        if thisUser.getUserName() != None:
            thisUser.setPosition(chatroom)
            thischatroom.addUserList(thisUser.getUserId(),thisUser.getUserName(),thisUser.getUserTime(),thisUser.getUserPosion(),thisUser.getUserSwjg(), thisUser.getUserSfks(),intChatroom)
            thischatroom.addSpeaking("","","",thisUser.getUserName(),"","","red","system",intChatroom)
            return "/WEB-INF/ftl/chat/chat.ftl"
