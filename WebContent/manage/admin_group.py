from cn.edustar.jitar.pojos import Group, AccessControl,Message
from cn.edustar.jitar.data import Command
from base_action import *
from group_query import GroupQuery

# 协作组管理.
class admin_group (ActionExecutor, SubjectMixiner):
  def __init__(self):
    ActionExecutor.__init__(self)
    self.group_svc = __jitar__.groupService
    self.meetingsService = __spring__.getBean("meetingsService")

  def dispatcher(self, cmd):
    # 必须要求登录和具有管理权限.
    if self.loginUser == None: return ActionResult.LOGIN

    accessControlService = __spring__.getBean("accessControlService")
    if accessControlService.isSystemAdmin(self.loginUser) == False:
      if accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SUPERADMIN, 0) == False and accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SYSTEMCONTENTADMIN, 0) == False:      # 没有用户管理权.
        self.addActionError(u"您不具有用户管理权限, 或未经审核?")
        return ActionResult.ERROR
    
    # 派发命令.
    if cmd == None or cmd == "" : cmd = "list"
    
    if cmd == 'list':
      return self.list()
    elif cmd == "audit":
      return self.audit()
    #elif cmd == "edit":
      #return self.edit()
    elif cmd == "delete":
      return self.delete()
    elif cmd == "recover":
      return self.recover()
    elif cmd == "lock":
      return self.lock(True)
    elif cmd == "unlock":
      return self.lock(False)
    elif cmd == "hide":
      return self.hide(True)
    elif cmd == "unhide":
      return self.hide(False)
    elif cmd == "best":
      return self.best(u'设置为优秀团队', True)
    elif cmd == "unbest":
      return self.best(u'取消优秀团队', False)
    elif cmd == "rcmd":
      return self.rcmd(u'设置为推荐协作组', True)
    elif cmd == "unrcmd":
      return self.rcmd(u'取消推荐协作组', False)
    elif cmd == "crash":
      return self.crash()
    elif cmd == "open":
      return self.open()
    elif cmd == "close":
      return self.close()

    return self.unknownCommand(cmd)
  
  
  # 列出协作组.
  def list(self):
    # 构造查询对象.
    qry = GroupQuery(""" g.groupId, g.groupName, g.groupTitle, g.groupIcon, g.createDate, g.subjectId, g.gradeId, 
          g.createUserId, g.groupTags, g.groupIntroduce, g.groupState, g.userCount, g.visitCount,g.XKXDName,g.XKXDId, 
          g.isBestGroup, g.isRecommend, u.nickName, u.loginName, sc.name as categoryName 
          """)
    qry.groupState = None     # 缺省为列出所有.
    
    # 获得限定参数.
    # print "params = ", self.params
    type = self.params.getStringParam("type")
    request.setAttribute("type", type)
    request.setAttribute("typeTitle", self.typeToTitle(type))
    
    if type == "unaudit":
      qry.groupState = Group.GROUP_STATE_WAIT_AUDIT
      qry.parentId=None
    elif type == "locked":
      qry.groupState = Group.GROUP_STATE_LOCKED
    elif type == "deleted":
      qry.groupState = Group.GROUP_STATE_DELETED
    elif type == "hided":
      qry.groupState = Group.GROUP_STATE_HIDED
    elif type == "best":
      qry.isBestGroup = True
    elif type == "rcmd":
      qry.isRecommend = True
    
    qry.k = self.params.getStringParam("k", None)
    request.setAttribute("k", qry.k)
    qry.categoryId = self.params.getIntParamZeroAsNull("sc")
    
    #print "qry.categoryId="+str(qry.categoryId)
    
    request.setAttribute("sc", qry.categoryId)
    #多学科多学段导致的问题无法使用下面的方法搜索
    qry.subjectId = self.params.getIntParamZeroAsNull("subj")
    qry.gradeId = self.params.getIntParamZeroAsNull("gradeId")
    
    qry.parentId = None
    
    #print "qry.gradeId="+str(qry.gradeId)
    #print "qry.subjectId="+str(qry.subjectId)
    
    request.setAttribute("subj", qry.subjectId)
    request.setAttribute("gradeId", qry.gradeId)

    # 构造分页并查询数据.
    pager = self.createPager()
    pager.totalRows = qry.count()
    group_list = qry.query_map(pager)
    
    request.setAttribute("group_list", group_list)
    request.setAttribute("pager", pager)
    self.putGroupCategoryTree()
    self.putSubjectList()
    self.putGradeList()
    return "/WEB-INF/ftl/admin/group_list.ftl"  
  
  # 审核通过一个或多个协作组.
  def audit(self):
    def check_logic(g, batcher):
      if g.groupState != Group.GROUP_STATE_WAIT_AUDIT:
        self.addActionError(u"协作组 " + g.toDisplayString() + u" 状态不是等待审核, 不能进行审核通过操作.")
        return False
      return True
    
    # 审核通过的实际业务执行.
    def do_business(g, batcher):
      self.group_svc.auditGroup(g)
      #发送消息
      messageService = __spring__.getBean("messageService")
      if self.loginUser != None and messageService != None :
        message = Message()
        message.setSendId(self.loginUser.userId)
        message.setReceiveId(g.getCreateUserId())
        message.setTitle(u"您创建的群组 '" + g.getGroupTitle() + u"' 已经通过审核")
        message.setContent(u"祝贺您，您创建的群组 '" + g.getGroupTitle() + u"' 已经通过审核")
        messageService.sendMessage(message)
      return True
    
    batcher = self.createBatcher(operate=u'审核通过操作',
        check_logic=check_logic, do_business=do_business)
    batcher.execute()
    return batcher.result

  # 删除一个或多个协作组.
  def delete(self):
    def check_logic(g, batcher):
      if g.groupState == Group.GROUP_STATE_DELETED:
        self.addActionError(u"协作组 " + g.toDisplayString() + u" 已经被删除, 未再次进行删除.")
        return False
      return True
    
    # 审核通过的实际业务执行.
    def do_business(g, batcher):
      self.group_svc.deleteGroup(g)
      return True
    
    batcher = self.createBatcher(operate=u'删除',
        check_logic=check_logic, do_business=do_business)
    batcher.execute()
    return batcher.result


  # 恢复一个或多个协作组.
  def recover(self):
    def check_logic(g, batcher):
      if g.groupState != Group.GROUP_STATE_DELETED:
        self.addActionError(u"协作组 " + g.toDisplayString() + u" 未被删除, 因此不能进行恢复操作.")
        return False
      return True
    
    # 审核通过的实际业务执行.
    def do_business(g, batcher):
      self.group_svc.recoverGroup(g)
      return True
    
    batcher = self.createBatcher(operate=u'恢复',
        check_logic=check_logic, do_business=do_business)
    batcher.execute()
    return batcher.result


  # 锁定/解锁多个协作组.
  def lock(self, isLock):
    def check_logic(g, batcher):
      if isLock:
        if g.groupState != Group.GROUP_STATE_NORMAL:
          self.addActionError(u"协作组 " + g.toDisplayString() + u" 被删除、隐藏或已经锁定, 不能执行锁定操作.")
          return False
      else:
        if g.groupState != Group.GROUP_STATE_LOCKED:
          self.addActionError(u"协作组 " + g.toDisplayString() + u" 未被锁定或被删除、隐藏, 不能执行解锁操作.")
          return False
      return True
    
    # 审核通过的实际业务执行.
    def do_business(g, batcher):
      self.group_svc.lockGroup(g, isLock)
      return True
    
    if isLock: 
      operate = u'锁定' 
    else: 
      operate = u'解锁'
    batcher = self.createBatcher(operate=operate,
        check_logic=check_logic, do_business=do_business)
    batcher.execute()
    return batcher.result


  # 隐藏/解除隐藏多个协作组.
  def hide(self, isHide):
    def check_logic(g, batcher):
      if isHide:
        if g.groupState == Group.GROUP_STATE_HIDED:
          self.addActionError(u"协作组 " + g.toDisplayString() + u" 已经被隐藏了, 未再次进行隐藏")
          return False
        if g.groupState != Group.GROUP_STATE_NORMAL:
          self.addActionError(u"协作组 " + g.toDisplayString() + u" 被删除、锁定或未审核, 不能执行隐藏操作.")
          return False
      else:
        if g.groupState != Group.GROUP_STATE_HIDED:
          self.addActionError(u"协作组 " + g.toDisplayString() + u" 未被隐藏或被删除、锁定, 不能执行解除隐藏操作.")
          return False
      return True
    
    # 审核通过的实际业务执行.
    def do_business(g, batcher):
      self.group_svc.hideGroup(g, isHide)
      return True
    
    if isHide: 
      operate = u'隐藏' 
    else: 
      operate = u'解除隐藏'
    batcher = self.createBatcher(operate=operate,
        check_logic=check_logic, do_business=do_business)
    batcher.execute()
    return batcher.result


  # 设置/取消优秀团队.
  def best(self, operate, isBest):
    def check_logic(g, batcher):
      if isBest:
        if g.isBestGroup:
          self.addActionError(u"协作组 " + g.toDisplayString() + u" 已经是优秀团队了, 未再次进行设置.")
          return False
      else:
        if not g.isBestGroup:
          self.addActionError(u"协作组 " + g.toDisplayString() + u" 不是优秀团队, 因而不需进行取消操作.")
          return False
      return True
    
    # 审核通过的实际业务执行.
    def do_business(g, batcher):
      self.group_svc.setGroupBestGroup(g, isBest)
      return True
    
    batcher = self.createBatcher(operate=operate,
        check_logic=check_logic, do_business=do_business)
    batcher.execute()
    return batcher.result

  # 开启协作组会议
  def open(self):
    def check_logic(g, batcher):
      return True
    def do_business(g, batcher):
      self.meetingsService.openMeetings("group", g.groupId)
      return True
    batcher = self.createBatcher(operate=u'开启协作组会议', check_logic=check_logic, do_business=do_business)
    batcher.execute()
    return batcher.result

  # 关闭协作组会议
  def close(self):
    def check_logic(g, batcher):
      return True
    # 审核通过的实际业务执行.
    def do_business(g, batcher):
      self.meetingsService.closeMeetings("group", g.groupId)
      return True
    batcher = self.createBatcher(operate=u'关闭协作组会议', check_logic=check_logic, do_business=do_business)
    batcher.execute()
    return batcher.result



  # 设置/取消推荐协作组.
  def rcmd(self, operate, isRcmd):
    def check_logic(g, batcher):
      if isRcmd:
        if g.isRecommend:
          self.addActionError(u"协作组 " + g.toDisplayString() + u" 已经是推荐协作组了, 未再次进行设置.")
          return False
      else:
        if not g.isRecommend:
          self.addActionError(u"协作组 " + g.toDisplayString() + u" 不是推荐协作组, 因而不需进行取消操作.")
          return False
      return True
    
    # 审核通过的实际业务执行.
    def do_business(g, batcher):
      self.group_svc.setGroupRecommend(g, isRcmd)
      return True
    
    batcher = self.createBatcher(operate=operate,
        check_logic=check_logic, do_business=do_business)
    batcher.execute()
    return batcher.result


  # 彻底删除协作组.
  def crash(self):
    def check_logic(g, batcher):
      if g.groupState != Group.GROUP_STATE_DELETED:
        self.addActionError(u"协作组 " + g.toDisplayString() + u" 不是删除状态, 需要先删除协作组才能执行彻底删除操作.")
        return False
      return True
    
    # 审核通过的实际业务执行.
    def do_business(g, batcher):
      self.group_svc.crashGroup(g)
      return True
    
    batcher = self.createBatcher(operate=u'彻底删除',
        check_logic=check_logic, do_business=do_business)
    batcher.execute()
    return batcher.result

    
  
  # 创建协作组管理所用的缺省分页对象.
  def createPager(self):
    pager = self.params.createPager()
    pager.itemName = u"协作组"
    pager.itemUnit = u"个"
    pager.pageSize = 10
    return pager


  # 通过 groupId 得到指定协作组, 直接从 db 中加载.
  def getGroupById(self, groupId):
    cmd = Command(""" FROM Group g WHERE g.groupId = :groupId """)
    cmd.setInteger("groupId", groupId)
    group_list = cmd.open(1)
    if group_list == None or group_list.size() == 0:
      return None
    return group_list[0]
  
  
  # 缺省协作组操作权限检查.
  def default_check_right(self, g, batcher):
    if self.loginUser == None: return False     # 前面应该检查过了.
    if self.loginUser.loginName == "admin":
      return True      
    accessControlService = __spring__.getBean("accessControlService")
    if accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SUPERADMIN, 0) == False and accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SYSTEMCONTENTADMIN, 0) == False:
      self.addActionError(u"没有权限来执行操作.")
      return False
    
    return True
    

  # 标准的批处理初始化, 将所选中的用户标识做为任务.
  def batcher_initer(self, batcher):
    if self.loginUser == None:
      batcher.result = ActionResult.LOGIN
      return False

    group_ids = self.params.getIdList("groupId")
    if group_ids == None or group_ids.size() == 0:
      self.addActionError(u"没有选择要操作的协作组.")
      batcher.result = ActionResult.ERROR
      return False
    
    batcher.taskList = group_ids
    return True


  # 标准的批处理结束.
  def batcher_finisher(self, batcher):
    self.addActionMessage(u"共对 " + str(batcher.count) + u" 个协作组执行了" + batcher.operate + u"操作.")
    batcher.result = ActionResult.SUCCESS
    return True
  
  
  # 构造并返回一个标准协作组管理任务执行器.
  def createBatcher(self, operate=u'执行操作',
                    check_logic=empty_func,
                    do_business=None):
    # 获取数据步骤, 得到要操作的协作组.
    def get_data(groupId, batcher):
      g = self.getGroupById(groupId)
      if g == None:
        self.addActionError(u"未能找到指定标识为 " + str(groupId) + u" 的协作组.")
        return None
      batcher.data = g
      return g;
    
    # 记录日志.
    def do_log(g, batcher):
      batcher.count += 1
      self.addActionMessage(u"对协作组 " + g.toDisplayString() + u" 成功地执行了" + batcher.operate + u"操作.")
      return
    
    batcher = BusinessBatcher(initer=self.batcher_initer,
                              finisher=self.batcher_finisher)
    batcher.result = ActionResult.ERROR
    batcher.command = self.params.getStringParam("cmd")    # 缺省 cmd = 参数中的.
    batcher.operate = operate
    batcher.get_data = get_data
    batcher.check_logic = check_logic
    batcher.check_right = self.default_check_right
    batcher.do_business = do_business
    batcher.do_log = do_log
    
    return batcher
    


  # 把协作组分类放到当前 request 中.
  def putGroupCategoryTree(self):
    cate_svc = __jitar__.categoryService
    group_categories = cate_svc.getCategoryTree('group')
    # print "group_categories = ", group_categories
    request.setAttribute("group_categories", group_categories)
    

  # 根据类型获得其对应的中文说明, 也许放到模板里面更符合视图分离原则.
  def typeToTitle(self, type):
    if type == "best":
      return u"优秀团队"
    elif type == "rcmd":
      return u"推荐协作组"
    elif type == "unaudit":
      return u"待审核协作组"
    elif type == "deleted":
      return u"已删除协作组"
    elif type == "locked":
      return u"已锁定协作组"
    elif type == "hided":
      return u"已隐藏协作组"
    else:
      return u"协作组"
    
