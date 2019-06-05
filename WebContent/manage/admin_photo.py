# 系统后台的相册管理.
from base_action import *
from photo_query import PhotoQuery
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Photo
from cn.edustar.jitar.jython import BaseAdminAction


# 定义要返回的页面常量.
ADMIN_PHOTO_LIST = "/WEB-INF/ftl/admin/Admin_Photo_List.ftl"
ADMIN_PHOTO_RECYCLE_LIST = "/WEB-INF/ftl/admin/Admin_Photo_Recycle_List.ftl"


# 需要类名和文件名一致.
class admin_photo (ActionExecutor):
  
  # 构造.
  def __init__(self):
    self.params = ParamUtil(request)
    self.photoService = __jitar__.photoService
    self.categoryService = __jitar__.categoryService
    self.photo_service = __spring__.getBean("photoStapleService")
    self.pun_svc = __jitar__.UPunishScoreService
    return


  # 根据'cmd'进行派发.
  def dispatcher(self, cmd):
    # 必须要求是登录用户和具有管理员的权限.
    if self.loginUser == None:
      return ActionResult.LOGIN
    accessControlService = __spring__.getBean("accessControlService")
    if False == accessControlService.isSystemContentAdmin(self.loginUser):
      self.addActionError(u"没有管理相册的权限.")
      return ActionResult.ERROR
    
    # 根据cmd参数，执行相应的方法.
    if cmd == "" or cmd == None or cmd == "list" :
      return self.List()          # 列表显示
    elif cmd == "audit" :
      return self.Audit()         # 审核通过
    elif cmd == "unaudit" :
      return self.UnAudit()       # 取消审核
    elif cmd == "delete" :
      return self.Delete()        # 删除(放入回收站)
    elif cmd == "recycle_list" :
      return self.RecycleList()   # 回收站列表
    elif cmd == "crash" :
      return self.Crash()         # 彻底删除
    elif cmd == "recover" :
      return self.Recover()       # 恢复
    elif cmd == "privateShow" :   # 将选择的照片只在个人空间显示
        return self.show()
    else :
      self.addActionError(u"未知的命令参数：" + cmd)
      return ActionResult.ERROR    
    
    
  # 系统所有照片的列表显示.
  def List(self) :    
    # 构造查询.
    query = self.queryString()
    
    # 默认不过滤照片的审核与否.
    query.auditState = None
    # 照片的列表中不显示已经做了删除标志的照片.
    query.delState = False
    #显示照片不分是否是个人空间照片
    query.isPrivateShow = None
    # 查询关键字.
    pshow = self.params.safeGetStringParam("isPrivateShow")
    if pshow == "1":
        query.isPrivateShow = True
    elif pshow == "0":
        query.isPrivateShow = False
        
    query.k = self.params.safeGetStringParam("k")
    query.f = self.params.safeGetStringParam("f")
    query.sysCateId = self.params.getIntParamZeroAsNull("sc")
    query.photoStaple = self.params.getIntParamZeroAsNull("ps")
    self.putSysCategoryTree()
    self.putPhotoStaple()
    
    # TODO: 权限检查.
    
    # 调用分页函数.
    pager = self.createPager()
    pager.totalRows = query.count()
        
    # 得到所有照片的列表.
    photoList = query.query_map(pager)
    #print "photoList = ", photoList
        
    # 传给页面.
    request.setAttribute("pshow", pshow)
    request.setAttribute("photoList", photoList)
    request.setAttribute("pager", pager)
    request.setAttribute("k", query.k)
    request.setAttribute("f", query.f)
    request.setAttribute("sc", query.sysCateId)
    request.setAttribute("ps", query.photoStaple)
    
    # 返回到要显示的页面.
    return ADMIN_PHOTO_LIST


  # 把文章分类树放置到 request 中.
  def putSysCategoryTree(self):
    syscate_tree = self.categoryService.getCategoryTree('photo')
    request.setAttribute("syscate_tree", syscate_tree)


  # 所有照片的分类列表.
  def putPhotoStaple(self):
    photoStapleList = self.photo_service.getPhotoStapleList()
    request.setAttribute("photoStapleList", photoStapleList)


  # 审核.
  def Audit(self):
    def check_logic(pu, batcher):
      if pu.photo.auditState == Photo.AUDIT_STATE_OK:
        self.addActionError(u"照片 %s 已经审核通过了, 没有再次执行审核操作." % pu.photoDisplay())
        return False
      return True


    # 业务操作.
    def do_business(pu, *args):
      self.photoService.auditPhoto(pu.photo)     
      return True
      
    batcher = self.createBatcher(operate=u'审核通过', do_business=do_business, check_logic=check_logic)
    
    if not batcher.execute():
      return batcher.result    
    return ActionResult.SUCCESS
  
  # 取消审核.
  def UnAudit(self):
    def check_logic(pu, batcher):
      if pu.photo.auditState != Photo.AUDIT_STATE_OK:
        self.addActionError(u"照片 %s 未通过审核, 没有再次执行审核操作." % pu.photoDisplay())
        return False
      return True
    
    # 业务操作.
    def do_business(pu, *args):
      self.photoService.unAuditPhoto(pu.photo)      
      return True
      
    batcher = self.createBatcher(operate=u'取消审核', do_business=do_business, check_logic=check_logic)
    
    if not batcher.execute():
      return batcher.result    
    return ActionResult.SUCCESS
    
  

  # 构造并返回一个标准资源管理任务执行器.
  def createBatcher(self, operate=u'执行操作', do_business=None, check_logic=empty_func):
    # 获取数据步骤.
    def get_data(photoId, batcher):
      pu = self.getPhotoAndAuthor(photoId)
      if pu == None:
        self.addActionError(u"未能找到指定标识为 " + str(photoId) + u" 的照片")
        return None
      batcher.data = pu
      return pu;
    
    # 记录日志.
    def do_log(pu, batcher):
      batcher.count += 1
      self.addActionMessage(u"对照片 " + pu.photoDisplay() + u" 成功地执行了" + batcher.operate + u"操作.")
      return
    
    batcher = BusinessBatcher(initer=self.batcher_initer, finisher=self.batcher_finisher)
    batcher.result = ActionResult.ERROR
    batcher.cmd = self.params.getStringParam("cmd")
    batcher.operate = operate
    batcher.get_data = get_data
    batcher.check_logic = check_logic
    batcher.do_business = do_business
    batcher.do_log = do_log
    
    return batcher
    
    
  # 标准的批处理初始化, 将所选中的资源标识做为任务.
  def batcher_initer(self, batcher):
    if self.loginUser == None:
      batcher.result = ActionResult.LOGIN
      return False

    photo_ids = self.params.getIdList("photoId")
    if photo_ids == None or photo_ids.size() == 0:
      self.addActionError(u"没有选择要操作的照片.")
      batcher.result = ActionResult.ERROR
      return False
    
    batcher.taskList = photo_ids
    return True


  # 标准的批处理结束.
  def batcher_finisher(self, batcher):
    self.addActionMessage(u"共对 " + str(batcher.count) + u" 张照片执行了" + batcher.operate)

    return True
    
  def getPhotoAndAuthor(self, photoId):
    cmd = Command("SELECT p, u FROM Photo p, User u WHERE p.userId = u.userId AND p.photoId = :photoId")
    cmd.setInteger("photoId", photoId)
    list = cmd.open(1)
    if list == None or list.size() == 0:
      return None
    pu = list[0]
    return PhotoAndUser(pu[0], pu[1])


# 包括 photo 和 user 两个对象的容器.

  # 创建并返回一个分页对象.
  def createPager(self):
    # 调用Java的函数.
    pager = self.params.createPager()
    pager.itemName = u"图片"
    pager.itemUnit = u"张"
    pager.pageSize = 10
    return pager
    
  
  # 删除(放入回收站).
  def Delete(self):
    ids = self.params.getIdList("photoId")
    
    if ids == None or ids.size() == 0 : 
      self.addActionError(u"没有选择照片")
      return ActionResult.ERROR
    
    for photoId in ids:
      photo = self.photoService.findById(photoId)
      self.photoService.putIntoRecycle(photoId) # 放入回收站
      upun = self.pun_svc.createUPunishScore(11, photoId, photo.userId,self.loginUser.userId,self.loginUser.trueName)
      self.pun_svc.saveUPunishScore(upun)
      
    self.addActionMessage(u"操作成功！")
    self.addActionLink(u"相册后台管理", "?cmd=list")
    self.addActionLink(u"相册回收站", "?cmd=recycle_list")
    return ActionResult.SUCCESS


  # privateShow
  def show(self):
    ids = self.params.getIdList("photoId")
    
    if ids == None or ids.size() == 0 : 
      self.addActionError(u"没有选择照片！")
      return ActionResult.ERROR
    
    for photoId in ids:
      self.photoService.privateShow(photoId) # 放入回收站
      
    self.addActionMessage(u"操作成功")
    self.addActionLink(u"返回", "?cmd=list")
    self.addActionLink(u"相册回收站", "?cmd=recycle_list")
    return ActionResult.SUCCESS



  # 回收站列表.
  def RecycleList(self):
    # 构造查询.
    query = self.queryString()
    
    # 默认不过滤照片的审核与否.
    query.auditState = None
    # 照片的列表中不显示已经做了删除标志的照片.
    query.delState = True
    query.isPrivateShow = None
    
    # 调用分页函数.
    pager = self.createPager()
    pager.totalRows = query.count()
    
    # 得到所有照片的列表.
    photoList = query.query_map(pager)
    
    # 传给页面.
    request.setAttribute("photoList", photoList)
    request.setAttribute("pager", pager)
    
    # 返回到要显示的页面.
    return ADMIN_PHOTO_RECYCLE_LIST
    
    
    
  # 投影查询字段.
  def queryString(self):      
    # 构造查询.
    query = PhotoQuery(""" p.photoId, p.title, p.summary, p.href, p.tags, stap.title as stapTitle, 
                           sc.name as sysPhotoName, p.viewCount, p.createDate, p.addIp, 
                           u.nickName, u.loginName, p.auditState, p.delState """)
    return query
    
  # 从回收站中恢复删除的照片.
  def Recover(self):
    ids = self.params.getIdList("photoId")
    
    if ids == None or ids.size() == 0 :
      self.addActionError(u"没有选择照片")
      return ActionResult.ERROR
    
    for photoId in ids:
      self.photoService.recoverPhoto(photoId)
      upun = self.pun_svc.getUPunishScore(11, photoId)
      self.pun_svc.deleteUPunishScore(upun)
      
    self.addActionMessage(u"恢复成功")
    self.addActionLink(u"返回", "?cmd=recycle_list")
    self.addActionLink(u"相册管理", "?cmd=list")
    self.addActionLink(u"相册回收站", "?cmd=recycle_list")
    return ActionResult.SUCCESS


  def Crash(self):
    ids = self.params.getIdList("photoId")
    
    if ids == None or ids.size() == 0 : 
      self.addActionError(u"没有选择照片")
      return ActionResult.ERROR
    
    groupService = __jitar__.groupService
    for photoId in ids:
      photo = self.photoService.findById(photoId)
      if photo != None:
        groupService.deleteGroupPhotoByPhoto(photo)
        self.photoService.delPhoto(photo)
      
    self.addActionMessage(u"彻底删除成功")
    self.addActionLink(u"返回", "?cmd=recycle_list")
    self.addActionLink(u"相册回收站", "?cmd=recycle_list")
    return ActionResult.SUCCESS
    
    
# 照片和用户
class PhotoAndUser:
  def __init__(self, photo, user):
    self.photo = photo
    self.user = user
  def photoDisplay(self):
    return self.photo.toDisplayString()
  def userDisplay(self):
    return self.user.toDisplayString()


class AdminPhotoQuery (PhotoQuery):
  def __init__(self, selectFields):
    PhotoQuery.__init__(self, selectFields)
    self.k = None
    self.f = None