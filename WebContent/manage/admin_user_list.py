# coding=utf-8
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.pojos import User, AccessControl
from cn.edustar.jitar.model import Configure
from cn.edustar.jitar.util import CommonUtil, FileCache
from user_query import UserQuery
from base_manage import BaseManage
from util import Util
from base_action import *
import sys

ADMIN_USER_LIST = "/WEB-INF/ftl/admin/admin_user_list.ftl"  
# 用户管理(部分).
class admin_user_list(BaseManage, Util):
  userService = __jitar__.userService
  meetingsService = __spring__.getBean("meetingsService")
  unitService = __jitar__.unitService
  accessControlService = __spring__.getBean("accessControlService")
  
  # 构造.
  def __init__(self):
    BaseManage.__init__(self)
    self.config = __jitar__.configService.getConfigure()
    self.typeId = None
    
  # 主执行入口.
  def execute(self):
    if self.isSystemUserAdmin() == False:
      self.addActionError(u"您不具有用户管理权限。")
      return self.ERROR
  
    cmd = self.params.getStringParam("cmd")
    if cmd == None or cmd == "" : cmd = "list"
    
    result = self.dispatcher(cmd)
    
    if not self.hasActionLinks():
      self.addDefaultReturnActionLink()
    return result

  # 根据 cmd 进行派发.
  def dispatcher(self, cmd):
    if cmd == "list":
      return self.list()
    elif cmd == "edit":
      return self.edit()
    elif cmd == "save":
      return self.save()
    elif cmd == "delete":
      return self.delete()
    elif cmd == "del":
      return self.dele()
    elif cmd == "audit":
      return self.audit()
    elif cmd == "lock":
      return self.lock()
    elif cmd == "renew":
      return self.normalize()
    elif cmd == "set":
      return self.set_cmd()
    elif cmd == "push":
      return self.push()
    elif cmd == "unpush":
      return self.unpush()
    elif cmd == "open":
      return self.open()
    elif cmd == "close":
      return self.close()
    
    cache = __jitar__.cacheProvider.getCache('user')
    cache.clear()
    self.addActionError(u"未知命令: " + cmd)
    return self.ERROR
 
  # 列出用户列表.
  def list(self):
    self._set_config_item()

    # 根据参数处理.
    type = self.params.getStringParam("type")
    self.typeId = self.params.safeGetIntParam("typeId")
    if self.typeId == 0: self.typeId = None    
    request.setAttribute("typeId", self.typeId)
    request.setAttribute("type", "")
    request.setAttribute("typeName", self.typeToTitle(self.typeId))
    userService = __jitar__.userService
    userTypeList = userService.getAllUserType()
    if userTypeList != None and len(userTypeList) > 0:
      request.setAttribute("userTypeList", userTypeList)  
    return self.user_list(type)

  # 设置和配置有关的 request 环境变量.
  def _set_config_item(self):
    # 是否显示 email,register-date 列.
    cfg_showEmail = self.config.getBoolValue(Configure.USER_ADMIN_SHOW_EMAIL, True)
    if cfg_showEmail : 
      cfg_showEmail = 'true'
    else:
      cfg_showEmail = 'false'
    request.setAttribute("cfg_showEmail", cfg_showEmail)
    
  
  # 列出一般性用户列表.  
  def user_list(self, type):
    # 系统用户管理员可以管理所有用户
    pager = self.createPager()
    qry = UserQuery(""" u.userId, u.loginName, u.nickName, u.trueName, u.userIcon, u.userStatus, u.pushState,
                        u.email, u.subjectId, u.gradeId, u.createDate, u.qq, u.idCard, u.positionId, u.userType, subj.subjectName, 
                        grad.gradeName, unit.unitTitle
                         """)
    qry.userStatus = None
    qry.userTypeId = self.typeId
    qry.kk = self.params.getStringParam("k")
    qry.subjectId = self.params.getIntParamZeroAsNull("subjectId")
    qry.f = self.params.getStringParam("f")

    # print("type=" + type) 
    # 根据 type 设置过滤.
    if type == "unaudit":
      qry.userStatus = User.USER_STATUS_WAIT_AUTID 
    elif type == "locked":
      qry.userStatus = User.USER_STATUS_LOCKED
    elif type == "deleted":
      qry.userStatus = User.USER_STATUS_DELETED
    
    # 普通管理员不能设置 超级管理员的内容
    if self.loginUser.loginName != "admin":
      qry.custormAndWhere = "u.loginName <> 'admin'"
    pager.totalRows = qry.count()
    user_list = qry.query_map(pager)
    
    request.setAttribute("pager", pager)
    request.setAttribute("userList", user_list)
    request.setAttribute("k", qry.kk)
    request.setAttribute("f", qry.f)
    request.setAttribute("subjectId", qry.subjectId)
    request.setAttribute("cmdtype", type)
    
    self.putSubjectList()
    self.putGradeList()
    
    
    #判断是否是中教启星的用户系统，使用了别家公司的用户系统，则部分操作需要限制
    #if request.getServletContext().getServletRegistration("CAS-Authentication-Filter") != None:
    if request.getServletContext().getFilterRegistration("CAS-Authentication-Filter") != None:
        request.setAttribute("usermgr3", 1)
    elif request.getServletContext().getFilterRegistration("ssoUserFilter") != None:
        request.setAttribute("usermgr3", 1)
    else:
        request.setAttribute("usermgr3", 0)

    # 不能输入'
    if qry.kk == "'":
      self.addActionError(u"请不要输入非法的字符串。")
      return self.ERROR
    
    return ADMIN_USER_LIST

  # 编辑用户信息.
  def edit(self):
    request.setAttribute("__referer", request.getHeader("Referer"))
        
    # 得到要操作的用户标识和用户对象.
    userId = self.params.getIntParam("userId")
        
    if userId == 0:
      self.addActionError(u"没有给出要编辑的用户标识.")
      return self.ERROR
    
    user = self.getUserById(userId)
        
    if user == None:
      self.addActionError(u"没有找到指定标识为 %d 的用户." % userId)
      return self.ERROR
    if user.loginName != 'admin':
      if user.loginName == self.loginUser.loginName:
        # 不能对自己执行管理操作.
        self.addActionError(u"不能在此处修改自己的个人信息，请到您的'工作室管理'处修改！")
        return self.ERROR
    
    # 验证权限.
    if not self.can_edit_user(user):
      return self.ERROR
    
    # 设置编辑所需数据.
    request.setAttribute("manageMode", "admin")
    request.setAttribute("user", user)
    self.putSubjectList()
    self.putGradeList()
    self.putUserCategories()
      
    # profile_update_truename_needaudit = self.config.getBoolValue(Configure.PROFILE_UPDATE_TRUENAME_NEEDAUDIT, True)
    # if profile_update_truename_needaudit:
      # self.addFieldError("trueName", "如果修改了真实姓名，则需要管理员审核通过才能登录！")
      
    # profile_update_truename_needaudit = self.config.getBoolValue(Configure.PROFILE_UPDATE_SUBJECT_NEEDAUDIT, True)
    # if profile_update_truename_needaudit:
      # self.addFieldError("subject", "如果修改了真实姓名，则需要管理员审核通过才能登录！")
      
    # profile_update_truename_needaudit = self.config.getBoolValue(Configure.PROFILE_UPDATE_GRADE_NEEDAUDIT, True)
    # if profile_update_truename_needaudit:
      # self.addFieldError("grade", "如果修改了真实姓名，则需要管理员审核通过才能登录！")
      
    # profile_update_truename_needaudit = self.config.getBoolValue(Configure.PROFILE_UPDATE_DISTRICT_NEEDAUDIT, True)
    # if profile_update_truename_needaudit:
      # self.addFieldError("district", "如果修改了真实姓名，则需要管理员审核通过才能登录！")
      
    # role = self.config.getBoolValue(Configure.USER_REGISTER_MUST_ROLE, True)
    trueName = self.config.getBoolValue(Configure.PROFILE_UPDATE_TRUENAME_NEEDAUDIT, True)
    nickName = self.config.getBoolValue(Configure.TRUENAME_EQUALS_NICKNAME, True)
    IDCard = self.config.getBoolValue(Configure.USER_REGISTER_MUST_IDCARD, True)
    subject = self.config.getBoolValue(Configure.PROFILE_UPDATE_SUBJECT_NEEDAUDIT, True)
    grade = self.config.getBoolValue(Configure.PROFILE_UPDATE_GRADE_NEEDAUDIT, True)
    # if ((profile_update_truename_needaudit) || profile_update_subject_needaudit || profile_update_grade_needaudit || profile_update_district_needaudit):
    
    # if role:
      # role = "true"
    # else:
      # role = "false"
      
    if trueName:
      trueName = "true"
    else:
      trueName = "false"
      
    if nickName:
      nickName = "true"
    else:
      nickName = "false"
      
    if IDCard:
      IDCard = "true"
    else:
      IDCard = "false"
      
    if subject:
      subject = "true"
    else:
      subject = "false"
      
    if grade:
      grade = "true"
    else:
      grade = "false"      
    
    # request.setAttribute("role", role)
    request.setAttribute("trueName", trueName)
    request.setAttribute("nickName", nickName)
    request.setAttribute("IDCard", IDCard)
    request.setAttribute("subject", subject)
    request.setAttribute("grade", grade)
    
    return "/WEB-INF/ftl/admin/user_edit.ftl"
    

  # 保存被修改的用户信息.
  def save(self):
    # 从参数中组装出用户对象.
    user = self.collectUserInfo()
    if user == None:
      return self.ERROR
    
    userUnitId = self.params.getIntParamZeroAsNull("unitId")
    if userUnitId == None:
      self.addActionError(u"用户机构必须填写。")
      return self.ERROR
    if userUnitId != user.unitId:
      # 修改个机构字段人信息
      userUnit = self.unitService.getUnitById(userUnitId)
      if userUnit == None:
        self.addActionError(u"你选择的机构不存在。")
        return self.ERROR
      
      user.setUnitId(userUnitId)
      user.setUnitPathInfo(userUnit.unitPathInfo)
   
    # 判断权限.
    if not self.can_edit_user(user):
      return self.ERROR
    
    # 保存修改.
    try:
      self.userService.updateUser(user)
    except:
      exc_type, exc_value, exc_tb = sys.exc_info()
      self.addActionError(u"错误信息:%s" % exc_value)
      return self.ERROR
      
    # 设置显示信息.
    self.addActionMessage(u"用户 %s 的信息修改完成." % user.toDisplayString())
    
    __referer = self.params.getStringParam("__referer")
    if __referer != "":
      self.addActionLink(u"返回", self.params.getStringParam("__referer"))
    else:
      self.addActionLink(u"返回", "?cmd=list")
    cache = __jitar__.cacheProvider.getCache('user')
    cache.clear()
    fc = FileCache()
    fc.deleteUserAllCache(user.loginName)
    fc = None
    return self.SUCCESS
    

  # 删除所选的一个或多个用户.
  def delete(self):
    def check_logic(u, batcher, *args):
      if u.userStatus == User.USER_STATUS_DELETED:
        self.addActionError(u"用户 " + u.toDisplayString() + u" 已经被删除了, 没有再次进行删除操作.")
        return False
      return True
    
    # 审核通过的实际业务执行.
    def do_business(u, batcher, *args):
      self.userService.removeUser(u)
      return True
    
    batcher = self.createBatcher(operate=u"删除", check_logic=check_logic, do_business=do_business)
    batcher.execute()
    return batcher.result  
  
  def dele(self):
    # 得到要操作的用户标识和用户对象.
    userId = self.params.getIntParam("userId")
    self.userService.deleteUser(userId)
    return self.SUCCESS

  # 审核通过一个或多个用户.
  def audit(self):
    def check_logic(u, batcher, *args):
      if u.userStatus != User.USER_STATUS_WAIT_AUTID:
        self.addActionError(u"用户 " + u.toDisplayString() + u" 状态不是等待审核, 不能进行审核通过操作.")
        return False
      return True
    
    # 审核通过的实际业务执行.
    def do_business(u, batcher, *args):
      self.userService.updateUserStatus(u, User.USER_STATUS_NORMAL)
      return True
    
    batcher = self.createBatcher(operate=u'审核通过',
        check_logic=check_logic, do_business=do_business)
    batcher.execute()
    return batcher.result
  

  def push(self):
    def check_logic(u, batcher, *args):
      if u.userStatus != User.USER_STATUS_NORMAL:
        self.addActionError(u"用户 " + u.toDisplayString() + u" 状态不是正常状态，无法进行推送.")
        return False
      if u.pushState == 1:
        self.addActionError(u"用户 " + u.toDisplayString() + u" 已经被推送，无需继续推送.")
        return False
      if u.pushState == 2:
        self.addActionError(u"用户 " + u.toDisplayString() + u" 已经被设置为推送，无需继续推送.")
        return False
    
      return True
    
    # 审核通过的实际业务执行.
    def do_business(u, batcher, *args):
      self.userService.setToPush(u, self.loginUser)
      return True
    
    batcher = self.createBatcher(operate=u'被推送',
        check_logic=check_logic, do_business=do_business)
    batcher.execute()
    return batcher.result

  def unpush(self):
    def check_logic(u, batcher, *args):     
      if u.pushState == 1:
        self.addActionError(u"用户 " + u.toDisplayString() + u" 已经被推送，不能进行推送状态的更改.")
        return False
      if u.pushState == 0:
        self.addActionError(u"用户 " + u.toDisplayString() + u" 没有被设置为推送.")
        return False
    
      return True

  def open(self):
    def check_logic(u, batcher, *args):
      return True
    def do_business(u, batcher, *args):
      self.meetingsService.openMeetings("user", u.userId)
      return True
    batcher = self.createBatcher(operate=u'开启个人会议室', check_logic=check_logic, do_business=do_business)
    batcher.execute()
    return batcher.result

  def close(self):
    def check_logic(u, batcher, *args):
      return True
    def do_business(u, batcher, *args):
      self.meetingsService.closeMeetings("user", u.userId)
      return True
    batcher = self.createBatcher(operate=u'关闭个人会议室', check_logic=check_logic, do_business=do_business)
    batcher.execute()
    return batcher.result

  # 锁定一个或多个用户.
  def lock(self):
    # 锁定的实际业务执行.
    def do_business(u, batcher, *args):
      self.userService.updateUserStatus(u, User.USER_STATUS_LOCKED)
      return True
    
    batcher = self.createBatcher(operate=u'锁定',do_business=do_business)
    batcher.execute()
    return batcher.result
  

  # 正常化一个或多个用户.
  def normalize(self):
    # 逻辑检查, 现在没有特别逻辑需要检查, 以后可能有特定状态不让恢复.
    def normalize_check_logic(u, batcher, *args):
      return True
    
    # 恢复操作的实际业务执行.
    def normalize_business(u, batcher, *args):
      self.userService.updateUserStatus(u, User.USER_STATUS_NORMAL)
      return True
    
    batcher = self.createBatcher(operate=u'恢复正常',do_business=normalize_business, check_logic=normalize_check_logic)
    batcher.execute()
    return batcher.result  
  
  # 设置为名师, 管理员, 取消等各种子操作.
  def set_cmd(self):
    set_to = self.params.safeGetIntParam("set_to")
    if set_to > 0:
      displayText = u"设置为 "
    elif set_to < 0:
      displayText = u"取消 "
    else:
      self.addActionError(u"未知的命令: " + set_to )
      return ActionResult.ERROR
    return self.setUserType(displayText + self.typeToTitle(set_to) + " ", set_to)

  # 设置为名师/取消名师.
  def setUserType(self, operate, userTypeId):
    def do_business(u, batcher):
      oldUserType = u.userType
      if userTypeId > 0:
        #增加
        if oldUserType == None:
          self.userService.updateUserType(u.userId, "/" + str(userTypeId) + "/")
        else:
          if oldUserType.find("/" + str(userTypeId) + "/") == -1:
            oldUserType = oldUserType + str(userTypeId) + "/"
            #按照从小到大排序
            oldUserType = CommonUtil.sortNumberString(oldUserType)
            self.userService.updateUserType(u.userId, oldUserType)
      elif userTypeId < 0:
        #取消设置
        if oldUserType != None and oldUserType.find("/" + str(abs(userTypeId)) + "/") > -1:
          oldUserType = oldUserType.replace("/" + str(abs(userTypeId)) + "/","/")
          if oldUserType == "/" : oldUserType = None
          self.userService.updateUserType(u.userId, oldUserType)
      return True
    
    batcher = self.createBatcher(operate=operate, do_business=do_business)
    batcher.execute()
    return batcher.result

  def setToFamous(self, operate, isFamous):
    def do_business(u, batcher):
      self.userService.setUserFamous(u, isFamous)
      return True
    
    batcher = self.createBatcher(operate=operate, do_business=do_business)
    batcher.execute()
    return batcher.result

  # 设置为推荐工作室/取消推荐工作室.
  def setToRecommend(self, operate, isRecommend):
    def do_business(u, batcher):
      self.userService.setUserRecommend(u, isRecommend)
      return True
    
    batcher = self.createBatcher(operate=operate, do_business=do_business)
    batcher.execute()
    return batcher.result
    
    
  # 设置为学科带头人/取消学科带头人.
  def setToExpert(self, operate, isExpert):
    def do_business(u, batcher):
      self.userService.setUserExpert(u, isExpert)
      return True
    
    batcher = self.createBatcher(operate=operate, do_business=do_business)
    batcher.execute()
    return batcher.result


  # 设置教研员/取消教研员.
  def setToComissioner(self, operate, isComissioner):
    def do_business(u, batcher):
      self.userService.setUserComissioner(u, isComissioner)
      return True
    
    batcher = self.createBatcher(operate=operate, do_business=do_business)
    batcher.execute()
    return batcher.result

  # 设置为用户管理员权限. (市级, 区县级)
  def setUserAdminLevel(self, operate, adminLevel):
    def do_business(u, batcher):
      # print "setUserAdminLevel do_business"
      self.userService.setUserAdminLevel(u, adminLevel)
      return True

    # 设置用户管理权限需要更严格的权限检测.
    def check_right(u, batcher):
      # 先检查缺省的.
      if self.default_check_right(u, batcher) == False:
        return False
      # 检查能否设置指定权限的用户管理员.
      if self.canSetAdminLevel(self.loginUser, adminLevel) == False:
        self.addActionError(u"不能将用户设置为比自己等级高或同级的管理员.")
        return False
      
    batcher = self.createBatcher(operate=operate, do_business=do_business)
    batcher.check_right = check_right
    batcher.execute()
    return batcher.result


  # 设置为内容管理员.
  def setUserCensorLevel(self, operate, censorLevel):
    def do_business(u, batcher):
      # print "setUserCensorLevel do_business"
      self.userService.setUserCensorLevel(u, censorLevel)
      return True
    # 设置用户内容权限需要更严格的权限检测.
    def check_right(u, batcher):
      # 先检查缺省的.
      if self.default_check_right(u, batcher) == False:
        return False
      # 检查能否设置指定权限的内容管理员.
      if self.canSetCensorLevel(self.loginUser, censorLevel) == False:
        self.addActionError(u"不能将用户设置为比自己等级高的内容管理权限.")
        return False
      
      return True

    batcher = self.createBatcher(operate=operate, do_business=do_business)
    batcher.check_right = check_right
    batcher.execute()
    return batcher.result



  # 创建用户管理所用的缺省分页对象.
  def createPager(self):
    pager = self.params.createPager()
    pager.itemName = u"用户"
    pager.itemUnit = u"个"
    pager.pageSize = 10
    return pager

  # 得到指定标识的用户.
  def getUserById(self, userId):
    cmd = Command(""" FROM User u WHERE u.userId = :userId """)
    cmd.setInteger("userId", userId)
    user_list = cmd.open(1)
    if user_list == None or user_list.size() == 0:
      return None
    return user_list[0]
  
  
  # 从提交的参数中收集用户信息.
  # 注意: 包括 userId, loginName, 如果调用者不需要用户提交这两个参数, 则需自己覆盖它的值.
  def collectUserInfo(self):
    params = self.params
    userId = params.getIntParam("userId")
    if userId != 0:
      user = self.getUserById(userId)
      if user == None:
        self.addActionError(u"没有找到指定标识为 %d 的用户." % userId)
        return None
    else:
      user = User()
      user.userId = userId
      user.loginName = params.getStringParam("loginName")

    # 判断身份证是否是必须填写的,
    idcardMust = self.config.getBoolValue(Configure.USER_REGISTER_MUST_IDCARD, False)
    sidcard = None
    if idcardMust == True:
      sidcard = params.getStringParam("IDCard")
      if sidcard == None or sidcard == "": 
        self.addActionError(u"注册信息中身份证号码必须填写")
        return None
    if sidcard != None:
      if sidcard != "":
        if len(sidcard) != 15 and len(sidcard) != 18 :
          self.addActionError(u"注册信息中身份证号码填写不正确")
          return None
    user.qq = params.getStringParam("QQ", "")
    user.idCard = params.getStringParam("IDCard", "")
    user.userIcon = params.getStringParam("userIcon", "images/default.gif")
    user.positionId = params.getIntParam("role")
    #user.nickName = params.getStringParam("nickName")
    user.trueName = params.getStringParam("trueName")
    user.email = params.getStringParam("email")
    user.blogName = params.getStringParam("blogName")
    user.userTags = params.getStringParam("userTags")
    user.blogIntroduce = params.getStringParam("blogIntroduce")
    user.gender = params.getIntParam("gender")
    user.subjectId = params.getIntParamZeroAsNull("subjectId")
    user.gradeId = params.getIntParamZeroAsNull("gradeId")
    user.categoryId = params.getIntParamZeroAsNull("categoryId")
    user.MobilePhone = params.getStringParam("MobilePhone","")
    return user
    
  
  # 把工作室分类放到 request 中.
  def putUserCategories(self):
    user_categories = __jitar__.categoryService.getCategoryTree('blog')
    request.setAttribute("syscate_tree", user_categories)
    
    
  # 标准的批处理初始化, 将所选中的用户标识做为任务.
  def batcher_initer(self, batcher):
    if self.loginUser == None:
      batcher.result = ActionResult.LOGIN
      return False

    user_ids = self.params.getIdList("userId")
    if user_ids == None or user_ids.size() == 0:
      self.addActionError(u"没有选择要操作的用户.")
      batcher.result = ActionResult.ERROR
      return False
    
    batcher.taskList = user_ids
    return True


  # 标准的批处理结束.
  def batcher_finisher(self, batcher):
    self.addActionMessage(u"共对 %d 个用户执行了%s操作." % (batcher.count, batcher.operate))
    batcher.result = ActionResult.SUCCESS
    return True

  
  
  # 检测是否有编辑指定用户信息的权限.
  def can_edit_user(self, u):
    # 必须登录.
    if self.loginUser == None:
      batcher.result = self.LOGIN
      return None
    
    # 可以自己编辑自己.
    if u.userId == self.loginUser.userId:
      return True
    
    # 禁止其它人对 'admin' 进行操作, 否则可能导致管理员不能使用.
    if self.loginUser.loginName == 'admin':
      return True
    if u.loginName == 'admin':
      if self.loginUser.loginName != 'admin':
        self.addActionError(u"不能对系统管理员 admin 执行编辑操作.")
        return False
      
    # 不能对超过自己的级别和同级的管理员进行操作.
    if self.accessControlService.isSystemUserAdmin(u) or self.accessControlService.isSystemAdmin(u):
      self.addActionError(u"不能对用户 " + u.toDisplayString() + u" 进行管理操作, 其管理员等级较您高或同级.")
      return False
    
    return True
    
    
  # 缺省的权限检测部分, 里面有权限业务.
  def default_check_right(self, u, batcher):
          
    # 必须有管理权限.
    if self.isSystemAdmin() == False and self.isSystemUserAdmin() == False:
      self.addActionError(u"不具有用户管理权限")
      return False
    if self.loginUser.loginName == 'admin':
      return True
    # 禁止其它人对 'admin' 进行操作, 否则可能导致管理员不能使用.
    if u.loginName == 'admin':
      if self.loginUser.loginName != 'admin':
        self.addActionError(u"不能对系统管理员(admin) 执行%s操作." % batcher.operate)
        return False
    elif u.loginName == self.loginUser.loginName:
      # 不能对自己执行管理操作.
      self.addActionError(u"不能对自己执行管理操作")
      return False
    
    # 不能对超过自己的级别和同级的管理员进行操作.
    if self.accessControlService.isSystemUserAdmin(u) or self.accessControlService.isSystemAdmin(u):
      self.addActionError(u"不能对用户 " + u.toDisplayString() + u" 进行管理操作, 其管理员等级较您高或同级.")
      return False
      
    return True  
  def empty_func(*args, **kw):
    return True
  # 构造并返回一个标准用户管理任务执行器.
  def createBatcher(self, operate=u"执行操作", check_logic=empty_func, do_business=None):
    # 获取数据步骤, 得到要操作的用户.
    def get_data(userId, batcher):
      u = self.getUserById(userId)
      if u == None:
        self.addActionError(u"未能找到指定标识为 %d 的用户." % userId)
        return None
      batcher.data = u
      return u;
    
    # 记录日志.
    def do_log(u, batcher):
      batcher.count += 1
      self.addActionMessage(u"对用户 %s 成功地执行了%s操作." % (u.toDisplayString(), batcher.operate))
      return
    
    batcher = BusinessBatcher(initer=self.batcher_initer, finisher=self.batcher_finisher)
    batcher.result = ActionResult.ERROR
    batcher.operate = operate
    batcher.get_data = get_data
    batcher.check_logic = check_logic
    batcher.check_right = self.default_check_right
    batcher.do_business = do_business
    batcher.do_log = do_log
    
    return batcher
    

  # 根据传递的 type 参数得到其显示文字.
  def typeToTitle(self, type_Id):
    if type_Id == None or type_Id == 0:
      return u"用户"
    type_Id = abs(type_Id)
    ut = self.userService.getUserTypeById(type_Id)
    if ut == None:
      return u"用户"
    else:
      return ut.typeName