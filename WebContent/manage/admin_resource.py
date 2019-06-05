from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.pojos import Resource
from cn.edustar.jitar.pojos import Message
from base_action import *
from resource_query import ResourceQuery 

# 资源管理.
class admin_resource (ActionExecutor, SubjectMixiner):
  # 常量定义: Result Map for admin_resource.
  RESOURCE_LIST = "/WEB-INF/ftl/admin/resource_list.ftl"
  RECYCLE_LIST = "/WEB-INF/ftl/admin/resource_recycle_list.ftl"
  RESOURCE_EDIT = "/WEB-INF/ftl/admin/Admin_Resource_Edit.ftl"
  
  # 构造.
  def __init__(self):
    self.params = ParamUtil(request)
    self.res_svc = __jitar__.resourceService
    self.cate_svc = __jitar__.categoryService
    self.pun_svc = __jitar__.UPunishScoreService
    self.msg_svc = __spring__.getBean("messageService")
    self.channelPageService = __spring__.getBean("channelPageService")
    self.userService = __jitar__.userService
    return

  
  # 从 ActionExecutor 调用, 根据 cmd 进行不同处理.
  def dispatcher(self, cmd):
    # 必须要求登录和具有管理权限.
    if self.loginUser == None:
      return ActionResult.LOGIN
  
    configService = __jitar__.configService
    sys_config = configService.getConfigure()
    if sys_config != None:
      if sys_config["topsite_url"] != None and str(sys_config["topsite_url"]).strip() != "":
        request.setAttribute("topsite_url", "")
    accessControlService = __spring__.getBean("accessControlService")
    if False == accessControlService.isSystemContentAdmin(self.loginUser):
      self.addActionError(u"您不具有资源管理权限, 或者您的信息填写不完整, 或未经系统管理员审核.")
      return ActionResult.ERROR
    
    if cmd == "list":
      return self.list()
    elif cmd == "audit":
      return self.audit()
    elif cmd == "unaudit":
      return self.unaudit()
    elif cmd == "rcmd":
      return self.rcmd()
    elif cmd == "unrcmd":
      return self.unrcmd()
    elif cmd == "delete":
      return self.delete()
    elif cmd == "move_cate":
      return self.move_cate()
    if cmd == "recycle_list":
      return self.recycle_list()
    elif cmd == "recover":
      return self.recover()
    elif cmd == "crash":
      return self.crash()
    elif cmd == "edit":
      return self.edit()
    elif cmd == "save":
      return self.save()
    elif cmd == "push":
      return self.push()
    elif cmd == "unpush":
      return self.unpush()
  
    self.addActionError(u"未知命令: " + cmd)
    return ActionResult.ERROR
    
    
  # 显示资源列表.
  def list(self):
    # 构造查询.
    query = AdminResourceQuery(""" r.resourceId, r.title, r.shareMode, r.createDate, r.href, r.subjectId, r.gradeId, 
        r.downloadCount, r.commentCount, r.fsize, r.auditState, r.recommendState, r.tags, r.addIp, r.pushState,r.publishToZyk,
        u.userId, u.loginName, u.nickName, sc.name as sysCateName, subj.subjectName """)
    query.auditState = None
    query.delState = False         # 过滤被删除了的.
    query.shareMode = None
    
    # 根据参数设置过滤条件.
    type = self.params.getStringParam("type")
    request.setAttribute("type", type)
    if type == "rcmd":    # 推荐.
      query.rcmdState = True
    elif type == "unaudit":     # 待审核.
      query.auditState = Resource.AUDIT_STATE_WAIT_AUDIT
    
    zyk = self.params.getStringParam("zyk")
    #print "zyk=",zyk
    query.publishToZyk = None
    if(zyk != None and zyk != ""):
      if zyk == "0":
        query.publishToZyk = False
      if zyk == "1": 
        query.publishToZyk = True
    #print "query.publishToZyk=",query.publishToZyk    
    query.subjectId = self.params.getIntParamZeroAsNull("su")
    request.setAttribute("su", query.subjectId)
    query.gradeId = self.params.getIntParamZeroAsNull("gradeId")
    request.setAttribute("gradeId", query.gradeId)
    query.sysCateId = self.params.getIntParamZeroAsNull("sc")
    request.setAttribute("sc", query.sysCateId)
    query.kk = self.params.getStringParam("k")
    request.setAttribute("k", query.kk)
    query.f = self.params.getStringParam("f")
    request.setAttribute("f", query.f)
    request.setAttribute("zyk", zyk)
    
    servletContext = request.getSession().getServletContext()
    zyk_url = servletContext.getInitParameter("reslib_url")
    request.setAttribute("zyk_url", zyk_url)
    request.setAttribute("isHaszykUrl", "false")
    if (zyk_url != None and zyk_url != ""):
      request.setAttribute("isHaszykUrl", "true");
    

    # 根据管理员权限再设置过滤条件.
    #self.applyDocPrivFilter(self.loginUser, query)
    
    # 计算总量.
    pager = self.createPager()
    pager.pageSize = 10
    pager.totalRows = query.count()
    
    # 得到资源.
    resource_list = query.query_map(pager)
    
    request.setAttribute("pager", pager)
    request.setAttribute("resource_list", resource_list)
    self.putSubjectList()
    self.putGradeList()
    self.putResourceCategoryTree()

    return self.RESOURCE_LIST
  

  # 编辑.
  def edit(self):
    # 得到参数.
    resourceId = self.params.getIntParam("resourceId")
    if resourceId == 0 or resourceId == None:
      self.addActionError(u"未能找到指定标识为 %d 的资源。" % resourceId)
      return ActionResult.ERROR
    
    # 得到'resourceId'对应的'资源对象'
    resource = self.res_svc.getResource(resourceId)
    
    #x 代表来源,以利于返回(不同的模块调用本页面)
    x = self.params.getIntParam("x")
    request.setAttribute("x", x)
    subId = self.params.getIntParam("subId")
    request.setAttribute("subId", subId)
    
    
    # 判断以外情况下的资源是否存在.
    if resource == None:
      self.addActionError(u"未能找到指定标识为 %d 的资源。" % resourceId)
      return ActionResult.ERROR
        
    # 将'资源对象'传给页面.
    request.setAttribute("resource", resource)
    
    # 系统资源分类
    self.putResourceCategoryTree()
    # 个人资源分类(系统管理员暂不修改用户个人的资源分类)
    #self.putResourceUserCategory()
    # 学科, 学段.
    self.putSubjectList()
    self.putGradeList()    
    return self.RESOURCE_EDIT
  
  
  # 保存.
  def save(self):
    # 得到页面的 resourceId，如果出现网络问题或从地址栏非法输入而获得失败的，则提示'未找到...'
    resourceId = self.params.getIntParam("resourceId")
    #print "===resourceId===", resourceId
    if resourceId == 0 or resourceId == None:
      self.addActionError(u"未能找到指定标识为 %d 的资源。" % resourceId)
      return ActionResult.ERROR
    
    
    # 根据'resourceId'获得其对应的文章对象.
    resource = self.res_svc.getResource(resourceId)
    #print "===resource===", resource
    
    # 设置新的数据.
    self.setResource(resource)
    
    # 检查资源的合法性.    
    if not self.checkResource(resource):
      return ActionResult.ERROR
    
    # 保存修改的资源.
    self.res_svc.updateResource(resource)
    
    #x 代表来源,以利于返回(不同的模块调用本页面)
    x = self.params.getIntParam("x")
    request.setAttribute("x", x)
    subId = self.params.getIntParam("subId")
    request.setAttribute("subId", subId)
        
    self.addActionMessage(u"对资源 %d 成功地执行了'修改保存'操作." % resourceId)
    self.addActionLink(u"返回", "javascript:history.go(-1)")
    if x == 1:
        self.addActionLink(u"资源管理", "/subject/manage/resource.py?id=" + str(subId))
    else:
        self.addActionLink(u"资源管理", "?cmd=list")        
    return ActionResult.SUCCESS


  # 修改资源对象.
  def setResource(self, resource):
    resource.title = self.params.getStringParam("title")
    resource.sysCateId = self.params.getIntParamZeroAsNull("sysCateId")
    resource.gradeId = self.params.getIntParamZeroAsNull("gradeId")
    resource.subjectId = self.params.getIntParamZeroAsNull("subjectId")
    resource.tags = self.params.getStringParam("tags")
    resource.resTypeId = self.params.getIntParamZeroAsNull("restype")
    resource.shareMode = self.params.getIntParam("shareMode")
    resource.author = self.params.getStringParam("author")
    resource.publisher = self.params.getStringParam("publisher")
    resource.summary = self.params.getStringParam("summary")
      
    return resource
  
  
  # 验证资源的属性.
  def checkResource(self, resource):
    if resource.title.strip() == "":
      self.addActionError(u"资源标题不能为空！")
      return False
    return True
  
  

  # 审核通过所选的资源.
  def audit(self):
    def check_logic(ru, batcher):
      if ru.resource.auditState == Resource.AUDIT_STATE_OK:
        self.addActionError(u"资源 %s 已经审核通过了, 没有再次执行审核操作." % ru.resDisplay())
        return False
      return True
    
    # 业务部分.
    def do_business(ru, batcher, *args):
      self.res_svc.auditResource(ru.resource)
      ru.resource.setUnitPathInfo(ru.resource.orginPathInfo)
      ru.resource.setApprovedPathInfo(ru.resource.orginPathInfo)    
      self.res_svc.updateResource(ru.resource)      
      return True
    
    # 审核通过选中的一个或多个资源.
    batcher = self.createBatcher(operate=u'审核通过', do_business=do_business,
                                 check_logic=check_logic)
    if not batcher.execute():
      return batcher.result
    return ActionResult.SUCCESS
    
  # 推送所选的资源.
  def push(self):
    def check_logic(ru, batcher):
      if ru.resource.auditState != Resource.AUDIT_STATE_OK:
        self.addActionError(u"资源 %s 没有被审核, 不能进行推送操作." % ru.resDisplay())
        return False
      if ru.resource.pushState == 1:
        self.addActionError(u"资源 %s 已经被推送, 无需再次推送操作." % ru.resDisplay())
        return False
    
      return True
    
    # 业务部分.
    def do_business(ru, batcher, *args):
      ru.resource.setPushState(2)
      ru.resource.setPushUserId(self.loginUser.userId)
      self.res_svc.updateResource(ru.resource)
      return True
    
    # 审核通过选中的一个或多个资源.
    batcher = self.createBatcher(operate=u'设置为推送', do_business=do_business,
                                 check_logic=check_logic)
    if not batcher.execute():
      return batcher.result
    return ActionResult.SUCCESS

  # 推送所选的资源.
  def unpush(self):
    def check_logic(ru, batcher):
      if ru.resource.pushState == 1:
        self.addActionError(u"资源 %s 已经被推送，不能进行取消推送操作." % ru.resDisplay())
        return False
      if ru.resource.pushState == 0:
        self.addActionError(u"资源 %s 没有设置为推送, 无需进行取消推送操作." % ru.resDisplay())
        return False
    
      return True
    
    # 业务部分.
    def do_business(ru, batcher, *args):
      ru.resource.setPushState(0)
      ru.resource.setPushUserId(None)
      self.res_svc.updateResource(ru.resource)
      return True
    
    # 审核通过选中的一个或多个资源.
    batcher = self.createBatcher(operate=u'取消推送', do_business=do_business,
                                 check_logic=check_logic)
    if not batcher.execute():
      return batcher.result
    return ActionResult.SUCCESS
  # 取消审核所选的资源, 设置其审核状态为 WAIT_AUDIT.
  def unaudit(self):
    def check_logic(ru, batcher):
      if ru.resource.auditState != Resource.AUDIT_STATE_OK:
        self.addActionError(u"资源 %s 未审核通过, 没有再次执行取消审核操作." % ru.resDisplay())
        return False
      return True
    
    # 业务部分.
    def do_business(ru, batcher, *args):
      self.res_svc.unauditResource(ru.resource)
      ru.resource.setApprovedPathInfo(None)
      self.res_svc.updateResource(ru.resource)
      #self.res_svc.unauditResource(ru.resource)
      return True
    
    # 取消审核选中的一个或多个资源.
    batcher = self.createBatcher(operate=u'取消审核', do_business=do_business,
                                 check_logic=check_logic)
    if not batcher.execute():
      return batcher.result
    return ActionResult.SUCCESS


  # 推荐所选资源.
  def rcmd(self):
    def check_logic(ru, batcher):
      if ru.resource.recommendState:
        self.addActionError(u"资源 %s 已经是推荐资源了, 没有再次执行推荐操作." % ru.resDisplay())
        return False
      return True
    
    # 业务部分.
    def do_business(ru, batcher, *args):
      self.res_svc.rcmdResource(ru.resource)
      ru.resource.setRcmdPathInfo(ru.resource.orginPathInfo)
      self.res_svc.updateResource(ru.resource)
            
      return True
    
    # 推荐选中的一个或多个资源.
    batcher = self.createBatcher(operate=u'推荐', do_business=do_business,
                                 check_logic=check_logic)
    if not batcher.execute():
      return batcher.result
    return ActionResult.SUCCESS


  # 取消推荐所选资源.
  def unrcmd(self):
    def check_logic(ru, batcher):
      if ru.resource.recommendState == False:
        self.addActionError(u"资源 %s 不是推荐资源, 没有执行取消推荐操作." % ru.resDisplay())
        return False
      return True
    
    # 业务部分.
    def do_business(ru, batcher, *args):      
      self.res_svc.unrcmdResource(ru.resource)
      ru.resource.setRcmdPathInfo(None)
      self.res_svc.updateResource(ru.resource)
      return True
    
    # 取消推荐选中的一个或多个资源.
    batcher = self.createBatcher(operate=u'取消推荐', do_business=do_business,
                                 check_logic=check_logic)
    if not batcher.execute():
      return batcher.result
    return ActionResult.SUCCESS


  # 设置资源所属资源类型.
  def move_cate(self):
    # 得到目标分类参数, 并验证该分类的正确性.
    sysCateId = self.params.getIntParamZeroAsNull("sysCateId")
    if sysCateId != None:
      category = self.cate_svc.getCategory(sysCateId)
      if category == None:
        self.addActionError(u"指定标识的资源类型不存在.")
        return ActionResult.ERROR
      if category.itemType != 'resource':
        self.addActionError(u"指定标识的分类 %s 不是一个正确的资源类型, 请确定您是从有效链接提交的数据." % category.name)
        return ActionResult.ERROR
         
    def check_logic(ru, batcher):
      if ru.resource.sysCateId == sysCateId:
        self.addActionError(u"资源 %s 类型已经是所选类型, 未进行设置新资源类型操作." % ru.resDisplay())
        return False
      return True
    
    # 业务部分.
    def do_business(ru, batcher, *args):
      self.res_svc.updateResourceSysCate(ru.resource, sysCateId)
      return True
    
    # 取消推荐选中的一个或多个资源.
    batcher = self.createBatcher(operate=u'设置资源类型', do_business=do_business,
                                 check_logic=check_logic)
    if not batcher.execute():
      return batcher.result
    return ActionResult.SUCCESS
  
  
  # 删除所选的一个或多个资源.
  def delete(self):
    # 删除业务部分.
    def do_business(ru, batcher, *args):
      score = self.params.getIntParam("score")
      reason = self.params.getStringParam("reason")
      #print 'AAAAAAAAA'
      if score == None:
        upun = self.pun_svc.createUPunishScore(12, ru.resource.id, ru.resource.userId, score,self.loginUser.userId,self.loginUser.trueName)
      else:
        if score<0 :
          upun = self.pun_svc.createUPunishScore(12, ru.resource.id, ru.resource.userId, -1*score, reason,self.loginUser.userId,self.loginUser.trueName)
        else:
          upun = self.pun_svc.createUPunishScore(12, ru.resource.id, ru.resource.userId, score, reason,self.loginUser.userId,self.loginUser.trueName)
      self.pun_svc.saveUPunishScore(upun)
      #消息通知
      message = Message()
      message.sendId = self.loginUser.userId
      message.receiveId = ru.resource.userId
      message.title = u"管理员删除了您的资源及扣分信息"
      if reason != "":
        message.content = u"您的 " + ru.resource.title + u" 被删除,扣" + str(upun.score) + u"分,原因:" + reason
      else:  
        message.content = u"您的 " + ru.resource.title + u" 被删除,扣" + str(upun.score) + u"分"
      self.msg_svc.sendMessage(message)
      self.res_svc.deleteResource(ru.resource)
      return True
    
    # 删除选中的一个或多个资源.
    self.addActionLink(u"返回", "?cmd=list&page=" + self.params.getStringParam("ppp"))
    batcher = self.createBatcher(operate=u'删除', do_business=do_business)
    if not batcher.execute():
      return batcher.result
    
    return ActionResult.SUCCESS
  
  
  
  # 显示资源回收站.
  def recycle_list(self):
    # 构造查询.
    query = ResourceQuery(""" r.resourceId, r.title, r.shareMode, r.createDate, u.loginName, u.nickName, r.subjectId, r.gradeId, 
        r.href, r.downloadCount, r.commentCount, r.fsize, sc.name as sysCateName, subj.subjectName """)
    query.auditState = None
    query.delState = True         # 获得被删除的.
    query.shareMode = None
    
    query.subjectId = self.params.getIntParamZeroAsNull("su")
    request.setAttribute("su", query.subjectId)
    query.gradeId = self.params.getIntParamZeroAsNull("gradeId")
    request.setAttribute("gradeId", query.gradeId)
    query.sysCateId = self.params.getIntParamZeroAsNull("sc")
    request.setAttribute("sc", query.sysCateId)
    query.kk = self.params.getStringParam("k")
    request.setAttribute("k", query.kk)
    query.f = self.params.getStringParam("f")
    request.setAttribute("f", query.f)
        
    # 计算总量.
    pager = self.createPager()
    pager.totalRows = query.count()
    
    # 得到资源.
    resource_list = query.query_map(pager)
    
    request.setAttribute("pager", pager)
    request.setAttribute("resource_list", resource_list)
    self.putSubjectList()
    self.putGradeList()
    self.putResourceCategoryTree()
    
    return self.RECYCLE_LIST


  # 彻底删除选中的资源.
  def crash(self):
    # 彻底删除业务部分.
    def crash_business(ru, batcher, *args):
      self.res_svc.crashResource(ru.resource)
      return True
    
    # 彻底删除选中的一个或多个资源.
    batcher = self.createBatcher(operate=u'彻底删除操作', do_business=crash_business)
    if not batcher.execute():
      return batcher.result
    
    return ActionResult.SUCCESS
  
  
  
  # 恢复选中的一个或多个资源.
  def recover(self):
    # 恢复操作的逻辑验证部分.
    def recover_logic_checker(ru, batcher, *args):
      if ru.resource.delState == False:
        self.addActionError(u"资源 " + ru.resDisplay() + u" 并未被删除, 因此不需进行" + batcher.operate + u".")
        return False;
      return True
    
    # 恢复操作的实际业务执行.
    def recover_business(ru, batcher, *args):
      self.res_svc.recoverResource(ru.resource)
      upun = self.pun_svc.getUPunishScore(12, ru.resource.id)
      self.pun_svc.deleteUPunishScore(upun)
      return True
    
    batcher = self.createBatcher(operate=u'恢复操作', check_logic=recover_logic_checker,
                                 do_business=recover_business)
        
    if not batcher.execute():
      return batcher.result
    
    return ActionResult.SUCCESS
  
  
  
  # 得到指定标识的资源和其拥有用户.
  def getResourceAndAuthor(self, resourceId):
    cmd = Command("SELECT r, u FROM Resource r, User u WHERE r.userId = u.userId AND r.resourceId = :resourceId")
    cmd.setInteger("resourceId", resourceId)
    list = cmd.open(1)
    if list == None or list.size() == 0:
      return None
    ru = list[0]
    return ResourceAndUser(ru[0], ru[1])
    # --

  # 检查是否对资源具有指定操作权限.
  def default_check_right(self, ru, batcher):
    # 检测能否操作该资源.
    #if self.canManageResource(ru.user, ru.resource) == False:
    #  self.addActionError("不具有对资源 " + ru.resDisplay() + " 的操作权限, 可能其不属于您所管理的范围.")
    #  return False
    
    return True
  
    
  def createPager(self):
    # private 构造资源的缺省 pager.
    pager = self.params.createPager()
    pager.itemName = u"资源"
    pager.itemUnit = u"个"
    return pager
  
    
  # 构造并返回一个标准资源管理任务执行器.
  def createBatcher(self, operate=u'执行操作', check_logic=empty_func, do_business=None):
    # 获取数据步骤.
    def get_data(resourceId, batcher):
      ru = self.getResourceAndAuthor(resourceId)
      if ru == None:
        self.addActionError(u"未能找到指定标识为 " + str(resourceId) + u" 的资源")
        return None
      batcher.data = ru
      return ru;
    
    # 记录日志.
    def do_log(ru, batcher):
      batcher.count += 1
      self.addActionMessage(u"对资源 " + ru.resDisplay() + u" 成功地执行了" + batcher.operate + u"操作.")
      return
    
    batcher = BusinessBatcher(initer=self.batcher_initer,
                              finisher=self.batcher_finisher)
    batcher.result = ActionResult.ERROR
    batcher.cmd = self.params.getStringParam("cmd")
    batcher.operate = operate
    batcher.get_data = get_data
    batcher.check_logic = check_logic
    batcher.check_right = self.default_check_right
    batcher.do_business = do_business
    batcher.do_log = do_log
    
    return batcher
   
  # 标准的批处理初始化, 将所选中的资源标识做为任务.
  def batcher_initer(self, batcher):
    if self.loginUser == None:
      batcher.result = ActionResult.LOGIN
      return False

    res_ids = self.params.getIdList("resourceId")
    if res_ids == None or res_ids.size() == 0:
      self.addActionError(u"没有选择要操作的资源.")
      batcher.result = ActionResult.ERROR
      return False
    
    batcher.taskList = res_ids
    return True


  # 标准的批处理结束.
  def batcher_finisher(self, batcher):
    self.addActionMessage(u"共对 " + str(batcher.count) + u" 个资源执行了" + batcher.operate + u".")

    return True
    
  
  # 把资源分类放到 request 环境中.
  def putResourceCategoryTree(self):
    resource_categories = self.cate_svc.getCategoryTree('resource')
    request.setAttribute("resource_categories", resource_categories)
    
  def putResourceUserCategory(self):
    resource_user = self.cate_svc.getCategoryTree('user_res_1')
    request.setAttribute("resource_user", resource_user)
    
  def putChannelResourceCategoryTree(self, res):
    if res.userId == None:return
    resUser = self.userService.getUserById(res.userId)
    if resUser.channelId != None:
      channel = self.channelPageService.getChannel(resUser.channelId)
      if channel == None:return
    channel_resource_categories = self.cate_svc.getCategoryTree("channel_resource_" + str(resUser.channelId))
    request.setAttribute("channel_resource_categories", channel_resource_categories)
    
    
# 包括 resource, user 两个对象的容器.
class ResourceAndUser:
  def __init__(self, resource, user):
    self.resource = resource
    self.user = user
  def resDisplay(self):
    return self.resource.toDisplayString()
  def userDisplay(self):
    return self.user.toDisplayString()
    

# 资源带高级过滤条件的搜索.
class AdminResourceQuery (ResourceQuery):
  def __init__(self, selectFields):
    ResourceQuery.__init__(self, selectFields)
    self.kk = None          # 查询关键字.
    self.f = 0              # kk 查询哪个字段.
    
  def applyWhereCondition(self, qctx):
    ResourceQuery.applyWhereCondition(self, qctx)
    #if self.kk != None and self.kk != '':
    #  self._applyKeywordFilter(qctx)
  
  def _applyKeywordFilter(self, qctx):
    newKey = self.kk.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
    if self.f == 'title':   # 用资源标题、标签过滤.
      qctx.addAndWhere('r.title LIKE :kk OR r.tags LIKE :kk')
      qctx.setString('kk', '%' + newKey + '%')
    elif self.f == 'intro': # 用资源简介过滤.
      qctx.addAndWhere('r.summary LIKE :kk')
      qctx.setString('kk', '%' + newKey + '%')
    elif self.f == 'uname': # 用户名 (maybe id).
      try:
        userId = int(self.kk)
        qctx.addAndWhere('r.userId = :kk')
        qctx.setInteger('kk', userId)
      except:
        qctx.addAndWhere('u.loginName = :kk OR u.nickName = :kk')
        qctx.setString('kk', newKey)
    elif self.f == 'unit':  # 用户所在机构.
      qctx.addAndWhere('u.unit.unitName LIKE :kk')
      qctx.setString('kk', '%' + newKey + '%')
