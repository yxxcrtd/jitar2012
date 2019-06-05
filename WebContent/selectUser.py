from cn.edustar.jitar.pojos import Group
from cn.edustar.jitar.data import Command
from base_action import *
from group_query import GroupQuery

class selectUser:
  cfg_svc = __jitar__.configService

  def __init__(self):
    self.params = ParamUtil(request)  
    return

  def execute(self):
    self.config = self.cfg_svc.getConfigure()
    inputUser_Id = self.params.getStringParam("inputUser_Id")
    if inputUser_Id==None:
      inputUser_Id=""
      
    inputUserName_Id = self.params.getStringParam("inputUserName_Id")
    if inputUserName_Id==None:
      inputUserName_Id=""
      
    inputLoginName_Id = self.params.getStringParam("inputLoginName_Id")
    if inputLoginName_Id==None:
      inputLoginName_Id=""

    userUrl = self.params.getStringParam("userUrl")
    if userUrl==None:
      userUrl=""

    roomid = self.params.getStringParam("roomid")
    if roomid==None:
      roomid=""

    singleuser = self.params.getIntParam("singleuser")  #1 用户单选 0 用户多选
    showgroup = self.params.getIntParam("showgroup")
    if singleuser==None:
      singleuser=0
    if showgroup==None:
      showgroup=0
    request.setAttribute("showgroup", showgroup)
    request.setAttribute("singleuser", singleuser)
    request.setAttribute("inputUser_Id", inputUser_Id)
    request.setAttribute("inputUserName_Id", inputUserName_Id)
    request.setAttribute("inputLoginName_Id", inputLoginName_Id)
    request.setAttribute("userUrl", userUrl)
    request.setAttribute("roomid", roomid)
    # 返回
    response.contentType = "text/html; charset=UTF-8"
    return "/WEB-INF/common/selectUser.ftl"

