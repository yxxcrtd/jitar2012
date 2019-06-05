from base_action import *
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.service import ChatUserService
from cn.edustar.jitar.pojos import ChatUser

# 需要类名和文件名一致.
class getUserList (ActionExecutor):
  # 构造.
  def __init__(self):
    self.params = ParamUtil(request)
    self.userchat_svc = __spring__.getBean("chatUserService")
    return
 
  
  # 根据'cmd'进行派发.
  def dispatcher(self, cmd):
      return self.getusers()

  # AJAX 提供给 
  def getusers(self):
    if self.loginUser == None:
      return
    userName = self.loginUser.trueName
    if userName == None:
      userName = self.loginUser.loginName
    roomId = self.params.safeGetIntParam("roomId")
    if roomId == None or roomId == 0:
      roomId = 1
    
    userid = self.loginUser.getUserId();
    self.userchat_svc.updateChatUserCurrentDate(roomId, userid)
    
    roomUserList = self.userchat_svc.getCacheChatUsers(roomId)
    userList = []
    for u in roomUserList :
        if u.isLeave == False and (u in userList) == False:
            userList.append(u)
    request.setAttribute("roomId", roomId)
    request.setAttribute("userList", userList)
    
    return "/WEB-INF/ftl/chat/userlist.ftl"
