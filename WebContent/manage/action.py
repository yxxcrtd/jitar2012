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
class action (ActionExecutor):
  
  # 构造.
  def __init__(self):
    self.params = ParamUtil(request)
    self.photoService = __jitar__.photoService
    self.categoryService = __jitar__.categoryService
    self.photo_service = __spring__.getBean("photoStapleService")
    #self.photoStapleService = __jitar__.photoStapleService
    return


  # 根据'cmd'进行派发.
  def dispatcher(self, cmd):
    # 必须要求是登录用户和具有管理员的权限.
    if self.loginUser == None:
      return ActionResult.LOGIN
    if self.canAdmin() == False:
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
    # 查询关键字.
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
    request.setAttribute("photoList", photoList)
    request.setAttribute("pager", pager)
    request.setAttribute("k", query.k)
    request.setAttribute("f", query.f)
    request.setAttribute("sc", query.sysCateId)
    request.setAttribute("ps", query.photoStaple)
    
    # 返回到要显示的页面.
    return ADMIN_PHOTO_LIST

