# 站点统计
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar import PythonAction
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.pojos import User
from user_query import *
from base_action import *
from cn.edustar.jitar.data import *
from cn.edustar.jitar.model import *
  
# 站点统计
class admin_stat(BaseAdminAction, SubjectMixiner):
  user_svc = __jitar__.userService
  
  
  # 构造.
  def __init__(self):
    self.params = ParamUtil(request)
    self.accessControlService = __spring__.getBean("accessControlService")
  
  # 主执行入口.
  def execute(self):
    cmd = self.params.getStringParam("cmd")
    if cmd == None or cmd == "": 
      cmd = "list"
    
    result = self.dispatcher(cmd)
    
    if not self.hasActionLinks():
      self.addDefaultReturnActionLink()
    return result
  
  
  # 根据'cmd'进行派发.
  def dispatcher(self, cmd):
    # 必须要求登录
    if self.loginUser == None:
      return ActionResult.LOGIN
    
    # 必须要求具有站点统计权限.
    if self.isValidUserManager() == False:
      self.addActionError(u"您不具有站点统计权限！")
      return ActionResult.ERROR
    
    if cmd == "list":
      return self.list()
    elif cmd == "stat":
      return self.stat();
    
    self.addActionError("未知命令: " + cmd)
    return ActionResult.ERROR

 
  # 列出所有与个人信息相关的统计信息
  def list(self):
    # 根据参数处理.
    type = self.params.safeGetStringParam("type")
    if type == None or type == "" : 
      type = ""
    request.setAttribute("type", type)
    request.setAttribute("typeName", self.typeToTitle(type))
    
    if type == "group":
      return self.get_group_list(type)
    else:
      return self.user_list(type)
    
  def user_list123(self):
    hql = """SELECT new Map(u.userId as userId, u.loginName as loginName, u.trueName as trueName)
         FROM User u
         ORDER BY u.userId """
    user_list = Command(hql).open(6)
    request.setAttribute("userList", user_list)
    return "/WEB-INF/ftl/admin/Admin_Stat_List_1.ftl"

  
  # 列出统计列表.  
  def user_list(self, type):
    
    # 根据用户传递的参数执行过滤.
    pager = self.createPager()
    qry = AdminUserQuery(""" u.userId, u.loginName, u.trueName, u.blogName,
      u.subjectId, u.gradeId, u.unitId,
      subj.subjectName, grad.gradeName, unit.unitName, 
      u.visitCount, u.myArticleCount, u.otherArticleCount, u.recommendArticleCount, u.articleCommentCount, 
      u.resourceCount, u.recommendResourceCount, u.resourceCommentCount, u.resourceDownloadCount, 
      u.createGroupCount, u.jionGroupCount, u.photoCount, u.userStatus """)
    qry.userStatus = None
    qry.kk = self.params.getStringParam("k")
    qry.beginDate = self.params.getStringParam("beginDate")
    qry.endDate = self.params.getStringParam("endDate")
    qry.subjectId = self.params.getIntParamZeroAsNull("subjectId")   
    qry.f = self.params.getStringParam("f")

    # 根据管理员等级设置过滤.
    self.applyPrivFilter(self.loginUser, qry)
    
    # 得到数据.
    pager.totalRows = qry.count()
    user_list = qry.query_map(pager)
    
    # 赋值
    request.setAttribute("pager", pager)
    request.setAttribute("userList", user_list)
    request.setAttribute("k", qry.kk)
    request.setAttribute("beginDate", qry.beginDate)
    request.setAttribute("endDate", qry.endDate)
    request.setAttribute("f", qry.f)
    request.setAttribute("subjectId", qry.subjectId)
    
    self.putSubjectList()
    self.putGradeList()
    self.putUnitList()
    
    return "/WEB-INF/ftl/admin/Admin_Stat_List.ftl"


  # 导出到 Excel
  def stat(self):
    request.setCharacterEncoding("GB2312")
    qry = AdminUserQuery(""" u.userId, u.loginName, u.trueName, u.blogName,
      u.subjectId, u.gradeId, u.unitId,
      subj.subjectName, grad.gradeName,  unit.unitName, 
      u.visitCount, u.myArticleCount, u.otherArticleCount, u.recommendArticleCount, u.articleCommentCount, 
      u.resourceCount, u.recommendResourceCount, u.resourceCommentCount, u.resourceDownloadCount, 
      u.createGroupCount, u.jionGroupCount, u.photoCount, u.userStatus """)
    qry.userStatus = None
    qry.kk = self.params.getStringParam("k")
    qry.subjectId = self.params.getIntParamZeroAsNull("subjectId")
    qry.f = self.params.getStringParam("f")
    user_list = qry.query_map(65535);
    request.setAttribute("user_list", user_list)
    response.reset()
    response.setContentType("application/vnd.ms-excel")
    response.setHeader("Content-Type", "application/vnd.ms-excel; charset=GB2312")
    response.addHeader("Content-Disposition", "attachment; filename=stat.xls")
    return "/WEB-INF/ftl/admin/Admin_Stat_Excel.ftl"


  # 得到机构列表
  def putUnitList(self):
    from cn.edustar.jitar.service import UnitQueryParam
    param = UnitQueryParam()
    unit_list = __jitar__.unitService.getUnitList(param, None)
    request.setAttribute("unit_list", unit_list)
    

  # 创建用户管理所用的缺省分页对象.
  def createPager(self):
    pager = self.params.createPager()
    pager.itemName = u"用户"
    pager.itemUnit = u"个"
    pager.pageSize = 20
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

    user.userIcon = params.getStringParam("userIcon", "images/default.gif")
    user.nickName = params.getStringParam("nickName")
    user.trueName = params.getStringParam("trueName")
    user.email = params.getStringParam("email")
    user.blogName = params.getStringParam("blogName")
    user.userTags = params.getStringParam("userTags")
    user.blogIntroduce = params.getStringParam("blogIntroduce")
    user.gender = params.getIntParam("gender")
    user.subjectId = params.getIntParamZeroAsNull("subjectId")
    user.gradeId = params.getIntParamZeroAsNull("gradeId")
    user.categoryId = params.getIntParamZeroAsNull("categoryId")
    user.unitId = params.getIntParamZeroAsNull("unitId")
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
      batcher.result = ActionResult.LOGIN
      return None
    
    # 可以自己编辑自己.
    if u.userId == self.loginUser.userId:
      return True
    
    # 禁止其它人对 'admin' 进行操作, 否则可能导致管理员不能使用.
    if self.accessControlService.isSystemAdmin(u):
      self.addActionError(u"不能对系统管理员 admin 执行编辑操作.")
      return False    
    return True
    
    
  # 缺省的权限检测部分, 里面有权限业务.
  def default_check_right(self, u, batcher):
    # 必须登录.
    if self.loginUser == None:
      batcher.result = ActionResult.LOGIN
      return None
    
    # 必须有管理权限.
    accessControlService = __spring__.getBean("accessControlService")
    if accessControlService.getAllAccessControlByUser(self.loginUser) != None:
      self.addActionError(u"不具有用户管理权限")
      return False
    
    # 禁止其它人对 'admin' 进行操作, 否则可能导致管理员不能使用.self.accessControlService.isSystemAdmin(u)
    if self.accessControlService.isSystemAdmin(u):
      self.addActionError(u"不能对系统管理员(admin) 执行%s操作." % batcher.operate)
      return False
    elif u.loginName == self.loginUser.loginName:
      # 不能对自己执行管理操作.
      self.addActionError(u"不能对自己执行管理操作")
      return False
    
    return True
  

  # 构造并返回一个标准用户管理任务执行器.
  def createBatcher(self, operate='执行操作',
                    check_logic=empty_func,
                    do_business=None):
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
    
    batcher = BusinessBatcher(initer=self.batcher_initer,
                              finisher=self.batcher_finisher)
    batcher.result = ActionResult.ERROR
    batcher.operate = operate
    batcher.get_data = get_data
    batcher.check_logic = check_logic
    batcher.check_right = self.default_check_right
    batcher.do_business = do_business
    batcher.do_log = do_log
    
    return batcher
    

  # 根据传递的'type'参数得到其显示文字.
  def typeToTitle(self, type):
    if type == "group":
      return u"站点群组统计";
    #elif type == "subject":
      #return "学科统计";
    else:
      return u"站点个人统计";
  

# 附加实现 keyword 过滤的增强 UserQuery
class AdminUserQuery(UserQuery):
  def __init__(self, select):
    UserQuery.__init__(self, select)
    self.kk = None
    self.f = 'name'
    self.isAdmin = None     # 是否查找用户管理员.
    self.isCensor = None    # 是否查找内容管理员.

  # 附加 kk, f, isAdmin, isCensor 过滤条件.
  def applyWhereCondition(self, qctx):
    UserQuery.applyWhereCondition(self, qctx)
    if self.kk != None and self.kk != '':
      newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
      if self.f == 'email':
        qctx.addAndWhere("u.email LIKE :keyword")
      else:
        qctx.addAndWhere("u.loginName LIKE :keyword OR u.nickName LIKE :keyword OR u.trueName LIKE :keyword")
      qctx.setString("keyword", "%" + newKey + "%")