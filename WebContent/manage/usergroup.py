# 系统后台的角色组管理.
from base_action import *
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import UGroupUser
from cn.edustar.jitar.pojos import UGroup
from cn.edustar.jitar.pojos import UCondition1
from cn.edustar.jitar.pojos import UCondition2
from cn.edustar.jitar.pojos import UGroupPower
from cn.edustar.jitar.jython import BaseAdminAction

# 定义要返回的页面常量.
ADMIN_GROUPUSER_LIST = "/WEB-INF/ftl/admin/Admin_GroupUser_List.ftl"
ADMIN_GROUPUSERSETUP_LIST = "/WEB-INF/ftl/admin/Admin_GroupUser_Setup.ftl"
ADMIN_GROUPPOWERSETUP_LIST = "/WEB-INF/ftl/admin/Admin_GroupPower_Setup.ftl"
ADMIN_GROUPPOWERADD_LIST = "/WEB-INF/ftl/admin/Admin_GroupPower_Add.ftl"
ADMIN_GROUPCONDITION_LIST = "/WEB-INF/ftl/admin/Admin_GroupPower_Condition.ftl"
# 需要类名和文件名一致.
class usergroup (ActionExecutor):
  
  # 构造.
  def __init__(self):
    self.params = ParamUtil(request)
    self.ug_svc = __spring__.getBean("UGroupService")
    self.up_svc = __spring__.getBean("UserPowerService")
    self.user_svc = __jitar__.userService
    return


  # 根据'cmd'进行派发.
  def dispatcher(self, cmd):
    # 必须要求是登录用户和具有管理员的权限.
    if self.loginUser == None:
      return ActionResult.LOGIN
    if self.canAdmin() == False:
      self.addActionError(u"没有管理角色组的权限.")
      return ActionResult.ERROR
    
    # 根据cmd参数，执行相应的方法.
    if cmd == "" or cmd == None or cmd == "list" :
      return self.List()            # 列表显示
    elif cmd == "delete" :
      return self.Delete()          # 删除
    elif cmd == "add" :
      return self.Add()             # 增加
    elif cmd == "edit" :
      return self.Edit()             # 修改
    elif cmd=="saveadd":
      return self.Save()             # 保存  
    elif cmd == "setupuser" :
      return self.SetupUser()       # 设置组成员
    elif cmd == "adduser":
      return self.AddUser()       # 设置组成员
    elif cmd == "deleteuser":
      return self.DeleteUser()       # 删除组成员
    elif cmd == "setuppower" :
      return self.SetupPower()      # 设置组权限
    elif cmd == "savepower":
      return self.SavePower()      # 保存组权限
    elif cmd == "setcondition":
      return self.SetCondition()      # 设置组条件
    elif cmd == "savecondition":  
      return self.SaveCondition()      # 保存组条件
    else :
      self.addActionError(u"未知的命令参数：" + cmd)
      return ActionResult.ERROR    
    
    
  # 列表显示.
  def List(self) :    
    userGroups = self.ug_svc.getUGroups()
    # 传给页面.
    request.setAttribute("userGroups", userGroups)
    
    # 返回到要显示的页面.
    return ADMIN_GROUPUSER_LIST

  def SetCondition(self):
    condition1list=self.up_svc.getUCondition1()
    condition2list=self.up_svc.getUCondition2()
    groups=self.up_svc.getUGroups()
    if(groups==None):
      self.addActionError(u"没有角色组,请先设置角色组")
      return ActionResult.ERROR
    if(condition1list==None):
      request.setAttribute("scorenum", 0)
    else:  
      request.setAttribute("scorenum", condition1list.size())
    request.setAttribute("groups", groups)
    request.setAttribute("condition1list", condition1list)
    request.setAttribute("condition2list", condition2list)
    return ADMIN_GROUPCONDITION_LIST
  
  def SetupPower(self):
    id = self.params.getIntParam("id")
    if id == None or id==0: 
      self.addActionError(u"没有选择角色组")
      return ActionResult.ERROR
    uGroup= self.ug_svc.getUGroup(id)
    uGroupPower=self.up_svc.getUGroupPower(id);
    
    uploadArticleNum="";
    uploadResourceNum="";
    uploadDiskNum="";
    videoConference = 0;
    if(uGroupPower!=None):
      uploadArticleNum=uGroupPower.uploadArticleNum;
      uploadResourceNum=uGroupPower.uploadResourceNum;
      uploadDiskNum=uGroupPower.uploadDiskNum;
      videoConference=uGroupPower.videoConference;
    request.setAttribute("id", id)
    request.setAttribute("groupName", uGroup.groupName)
    request.setAttribute("uploadArticleNum", uploadArticleNum)
    request.setAttribute("uploadResourceNum", uploadResourceNum)
    request.setAttribute("uploadDiskNum", uploadDiskNum)
    request.setAttribute("videoConference", videoConference)
    return ADMIN_GROUPPOWERSETUP_LIST
  
  def SaveCondition(self):
    #保存Condition1
    num = self.params.getIntParam("num")
    if(num==0 or num==None):
      self.addActionError(u"分数段")
      return ActionResult.ERROR
    #删除原来组中的自动用户(因为条件变了)
    self.up_svc.DeleteUGroupUser(0);
    i=0
    score1=0
    score2=0
    groupId=0
    condition1list = []
    while i<num:
      groupId= self.params.getIntParam("group1_"+str(i))
      condition1=UCondition1()
      condition1.groupId=groupId
      if(i==0):
        score2 = self.params.getIntParam("score_2_0")
        score1 = score2
        condition1.conditionType=-1
      elif(i==(num-1)):
        score1 = self.params.getIntParam("score_1_"+str(i))
        score2 = score1
        condition1.conditionType=1
      else:  
        score1 = self.params.getIntParam("score_1_"+str(i))
        score2 = self.params.getIntParam("score_2_"+str(i))
        condition1.conditionType=0
      condition1.score1=score1  
      condition1.score2=score2
      condition1list.append(condition1)
      i+=1
    self.up_svc.SaveUCondition1(condition1list)
    #保存Condition2
    condition2list=self.up_svc.getUCondition2()
    for condition2 in condition2list:
      condition2Id=condition2.id
      groupId=self.params.getIntParam("group2_"+str(condition2Id))
      condition2.groupId=groupId
      self.up_svc.SaveUCondition2(condition2)
      
    self.addActionMessage(u"操作成功！")
    self.addActionLink(u"角色组管理", "?cmd=list")
    return ActionResult.SUCCESS
    
      
  def SavePower(self):
    id = self.params.getIntParam("id")
    if id == None or id==0: 
      self.addActionError(u"缺少角色组id")
      return ActionResult.ERROR
    uploadArticleNum=self.params.getIntParam("uploadArticleNum") 
    uploadResourceNum=self.params.getIntParam("uploadResourceNum")
    uploadDiskNum=self.params.getIntParam("uploadDiskNum")
    videoConference=self.params.getIntParam("videoConference")
    if(videoConference==None):
      videoConference="0"
    uGroupPower=self.up_svc.getUGroupPower(id);
    if(uGroupPower==None):
      uGroupPower=UGroupPower()
      uGroupPower.groupId=id
    uGroupPower.uploadArticleNum=uploadArticleNum
    uGroupPower.uploadResourceNum=uploadResourceNum
    uGroupPower.uploadDiskNum=uploadDiskNum
    uGroupPower.videoConference = videoConference
    
    self.up_svc.SaveUGroupPower(uGroupPower)
     
    self.addActionMessage(u"操作成功！")
    self.addActionLink(u"角色组管理", "?cmd=list")
    return ActionResult.SUCCESS

  def Save(self):
    id = self.params.getIntParam("id")
    if id == None : 
      id=0
    groupName=self.params.getStringParam("groupName") 
    groupInfo=self.params.getStringParam("groupInfo")
    if(groupName==None or groupName==""):
      self.addActionError(u"缺少角色组名称")
      return ActionResult.ERROR
    group=self.ug_svc.getUGroup(groupName)
    if(group!=None):
      if(id==0):
        self.addActionError(u"该名称已经存在")
        return ActionResult.ERROR
      else:
        if (id!=group.groupId):
          self.addActionError(u"该名称已经存在")
          return ActionResult.ERROR
    if(id==0):
      group=UGroup()
    else:
      group=self.ug_svc.getUGroup(id)
    group.groupName=groupName
    group.groupInfo=groupInfo  
    self.ug_svc.Save(group)
    if(id==0):  
      self.addActionMessage(u"增加成功！")
    else:  
      self.addActionMessage(u"修改成功！")
    self.addActionLink(u"角色组管理", "?cmd=list")
    return ActionResult.SUCCESS

  def DeleteUser(self):
    userids = self.params.safeGetIntValues("id")
    id = self.params.getIntParam("groupid")
    if id == None : 
      self.addActionError(u"缺少角色组id")
      return ActionResult.ERROR
    for userId in userids:
      #print "groupid=",id
      #print "userId=",userId
      ugroupuser=self.up_svc.FinduGroupUser(id,userId)
      if ugroupuser!=None:
        self.up_svc.DeleteUGroupUser(ugroupuser)
      
    #返回设置页面      
    uGroup= self.ug_svc.getUGroup(id)
    groupuserlist=self.up_svc.getUGroupUser(id,1)
    request.setAttribute("groupName", uGroup.groupName)
    request.setAttribute("id", id)
    request.setAttribute("groupuserlist", groupuserlist)
    return ADMIN_GROUPUSERSETUP_LIST
    
  def AddUser(self):
    #手工设置的特殊用户，如果已经存在，需要把managed设置为1
    id = self.params.getIntParam("groupid")
    if id == None : 
      self.addActionError(u"缺少角色组id")
      return ActionResult.ERROR
    invite_users = self.params.safeGetIntValues("inviteUserId")
    str_error=""
    for invite_userId in invite_users:
      user = self.user_svc.getUserById(invite_userId)
      if user == None:
        str_error = str_error + u"<li>用户" + user.trueName+"["+ str(invite_userId) + u"]不是本系统存在的用户。</li>"
      else:
        ugroupuser=self.up_svc.FinduGroupUser(id,invite_userId)
        if(ugroupuser==None):
          ugroupuser=UGroupUser()
          ugroupuser.managed=1
          ugroupuser.groupId=id
          ugroupuser.userId=invite_userId
        else:
          ugroupuser.managed=1
        #更新用户  
        self.up_svc.SaveUGroupUser(ugroupuser)
    if str_error != "":
      self.addActionError(str_error)
      return ActionResult.ERROR
    #返回设置页面      
    uGroup= self.ug_svc.getUGroup(id)
    if(uGroup==None):
      self.addActionError(u"没有找到角色组")
      return ActionResult.ERROR
    groupuserlist=self.up_svc.getUGroupUser(id,1)
    request.setAttribute("groupName", uGroup.groupName)
    request.setAttribute("id", id)
    request.setAttribute("groupuserlist", groupuserlist)
    return ADMIN_GROUPUSERSETUP_LIST
          
  def SetupUser(self):
    id = self.params.getIntParam("id")
    if id == None : 
      self.addActionError(u"没有选择角色组")
      return ActionResult.ERROR
    uGroup= self.ug_svc.getUGroup(id)
    if(uGroup==None):
      self.addActionError(u"没有找到角色组")
      return ActionResult.ERROR
    groupuserlist=self.up_svc.getUGroupUser(id,1)
    request.setAttribute("groupName", uGroup.groupName)
    request.setAttribute("id", id)
    request.setAttribute("groupuserlist", groupuserlist)
    return ADMIN_GROUPUSERSETUP_LIST
    
  def Add(self):
    request.setAttribute("groupName", "")
    request.setAttribute("groupInfo", "")
    request.setAttribute("id", 0)
    return ADMIN_GROUPPOWERADD_LIST  

  def Edit(self):
    id = self.params.getIntParam("id")
    if id == None : 
      self.addActionError(u"没有选择角色组")
      return ActionResult.ERROR
    uGroup= self.ug_svc.getUGroup(id)
    if uGroup== None:
      self.addActionError(u"没有找到角色组")
      return ActionResult.ERROR
    request.setAttribute("groupName", uGroup.groupName)
    request.setAttribute("groupInfo", uGroup.groupInfo)
    request.setAttribute("id", id)
    return ADMIN_GROUPPOWERADD_LIST 
  
  # 删除
  def Delete(self):
    ids = self.params.getIdList("id")
    if ids == None or ids.size() == 0 : 
      self.addActionError(u"没有选择角色组")
      return ActionResult.ERROR
    
    for Id in ids:
      uGroup= self.ug_svc.getUGroup(Id)
      #删除
      self.up_svc.DeleteGroup(uGroup)
      
    self.addActionMessage(u"删除成功！")
    self.addActionLink(u"角色组管理", "?cmd=list")
    return ActionResult.SUCCESS





