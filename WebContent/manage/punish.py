# 系统后台的相册管理.
from base_action import *
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import UPunishScore
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.query import PunishQueryParam

# 定义要返回的页面常量.
ADMIN_PUNISH_LIST = "/WEB-INF/ftl/admin/Admin_punish_List.ftl"

# 需要类名和文件名一致.
class punish (ActionExecutor):
  
  # 构造.
  def __init__(self):
    self.params = ParamUtil(request)
    self.pun_svc = __jitar__.UPunishScoreService
    return


  # 根据'cmd'进行派发.
  def dispatcher(self, cmd):
    # 必须要求是登录用户和具有管理员的权限.
    if self.loginUser == None:
      return ActionResult.LOGIN
    if self.canAdmin() == False:
      self.addActionError(u"没有管理罚分的权限.")
      return ActionResult.ERROR
    
    # 根据cmd参数，执行相应的方法.
    if cmd == "" or cmd == None or cmd == "list" :
      return self.List()          # 列表显示
    elif cmd == "delete" :
      return self.Delete()        # 删除
    else :
      self.addActionError(u"未知的命令参数：" + cmd)
      return ActionResult.ERROR    
    
    
  # 列表显示.
  def List(self) :    
    # 构造查询.
    param = PunishQueryParam();
    param.k = self.params.safeGetStringParam("k")
    param.objType = self.params.safeGetStringParam("type")
    # 调用分页函数.
    pager = self.createPager()
    print 'a'
    punishList = self.pun_svc.getUPunishScoreList(param,pager)
    print 'punishList='+str(punishList==None) 
    # 传给页面.
    request.setAttribute("punishList", punishList)
    request.setAttribute("pager", pager)
    request.setAttribute("objType", param.objType)
    request.setAttribute("k", param.k)
    
    # 返回到要显示的页面.
    return ADMIN_PUNISH_LIST


  # 创建并返回一个分页对象.
  def createPager(self):
    # 调用Java的函数.
    pager = self.params.createPager()
    pager.itemName = u"罚分"
    pager.itemUnit = u"张"
    pager.pageSize = 20
    return pager
    
  
  # 删除(放入回收站).
  def Delete(self):
    ids = self.params.getIdList("id")
    if ids == None or ids.size() == 0 : 
      self.addActionError(u"没有选择罚分")
      return ActionResult.ERROR
    
    for Id in ids:
      punish = self.pun_svc.getUPunishScore(Id)
      self.pun_svc.deleteUPunishScore(punish) 
      
    self.addActionMessage(u"操作成功！")
    self.addActionLink(u"加、罚分管理", "?cmd=list")
    return ActionResult.SUCCESS





