# 系统后台的机构管理
from base_action import *
from unit_query import UnitQuery
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Unit
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.service import UnitQueryParam
from cn.edustar.jitar.pojos import SiteNav

# 需要类名和文件名一致
class admin_unit (ActionExecutor):
  UNIT_LIST = "/WEB-INF/ftl/admin/Unit_List.ftl"
  UNIT_EDIT = "/WEB-INF/ftl/admin/Unit_Edit.ftl"
  
  # 构造.
  def __init__(self):
    self.params = ParamUtil(request)
    self.unitService = __jitar__.unitService
    self.userService = __jitar__.userService
    return
 
  
  # 根据'cmd'进行派发.
  def dispatcher(self, cmd):
    # 该命令不需要登录及管理权限.
    if cmd == "unit_options":   # ajax call.
      return self.unit_options()
    
    # 以下必须要求是登录用户和具有管理员的权限.
    if self.loginUser == None: return ActionResult.LOGIN
    if self.canAdmin() == False:
      self.addActionError(u"没有机构管理的权限.")
      return ActionResult.ERROR
    
    # 根据cmd参数，执行相应的方法.
    if cmd == "" or cmd == None or cmd == "list":
      return self.list()          # 列表显示.
    elif cmd == "edit":
      return self.edit()          # 添加.
    elif cmd == "save":
      return self.save()          # 保存.
    elif cmd == "del":
      return self.delete()        # 删除.
    else :
      self.addActionError(u"未知的命令参数：" + cmd)
      return ActionResult.ERROR    


  # 系统所有机构的列表显示.
  def list(self):
    # 构造查询.
    query = self.queryString()
      
    query.k = self.params.safeGetStringParam("k")
            
    # TODO: 权限检查.
    
    # 调用分页函数.
    pager = self.createPager()
    pager.totalRows = query.count()
        
    # 得到所有照片的列表.
    unitList = query.query_map(pager)
    #print "unitList:", unitList
        
    # 传给页面.
    request.setAttribute("unitList", unitList)
    request.setAttribute("pager", pager)
    
    
    # 将参数放到查询的页面.
    request.setAttribute("k", query.k)
    
    # 返回到要显示的页面.
    return self.UNIT_LIST
    
  # 添加或修改.
  def edit(self):
    unitId = self.params.getIntParam("unitId")
    if unitId == 0:
      unit = Unit()
    else:
      unit = self.unitService.findUnitById(unitId)
    # 传递给页面对象.
    request.setAttribute("unit", unit)
    
    
    # 返回跳转.
    return self.UNIT_EDIT
  
  
  # 保存机构.
  def save(self):
    # 得到页面的 unitId，如果出现网络问题或从地址栏非法输入而获得失败的，则提示'未找到...'
    unitId = self.params.safeGetIntParam("unitId", 0)
    
    if unitId > 0:    
      # 根据'unitId'获得其对应的机构对象.
      unit = self.unitService.findUnitById(unitId) 
    else:
      unit = Unit()
         
    # 设置新的数据.
    self.setUnit(unit)
    
    #检查是否存在同名的机构
    unitTemp = self.unitService.getAllUnitOrChildUnitList(None,[False])
    if self.checkExistUnit(unitTemp, unit) == True:
      self.addActionError(u"同名的机构已经存在")
      return ActionResult.ERROR
        
    # 保存
    self.unitService.saveOrUpdateUnit(unit)
    
    # 添加导航
    #print "unitId", unitId
    if unitId < 1:
      siteNameArray = [u"总站首页", u"机构首页", u"机构文章", u"机构资源", u"机构图片", u"机构视频", u"机构工作室"];
      siteUrlArray = ["py/subjectHome.action", "", "article/", "resource/", "photo/", "video/", "blog/"]
      siteHightlightArray = ["index", "unit", "unit_article", "unit_resource", "unit_photo", "unit_video", "unit_user"]
      siteNavService = __spring__.getBean("siteNavService")
      for i in range(0, len(siteNameArray)):
        #先判断是否存在
        siteNav = siteNavService.getSiteNavByName(SiteNav.SITENAV_OWNERTYPE_UNIT, unit.unitId, siteNameArray[i])
        if siteNav == None:          
          siteNav = SiteNav()
          siteNav.setSiteNavName(siteNameArray[i])
          siteNav.setIsExternalLink(False)
          siteNav.setSiteNavUrl(siteUrlArray[i])
          siteNav.setSiteNavIsShow(True)
          siteNav.setSiteNavItemOrder(i)
          siteNav.setCurrentNav(siteHightlightArray[i])
          siteNav.setOwnerType(SiteNav.SITENAV_OWNERTYPE_UNIT)
          siteNav.setOwnerId(unit.unitId)
          siteNavService.saveOrUpdateSiteNav(siteNav)

    
    # 返回.
    self.addActionLink(u"返回", "javascript:history.go(-1)")
    self.addActionLink(u"机构管理", "?cmd=list")
    return ActionResult.SUCCESS

  #检查同名机构是否存在
  def checkExistUnit(self, UnitList, Unit):
    for x in UnitList:
      if x.unitId != Unit.unitId and x.unitName == Unit.unitName :
        return True
    return False

  # 设置新的数据.
  def setUnit(self, unit):
    unit.unitName = self.params.getStringParam("unitName")
    unit.unitType = self.params.getStringParam("unitType")
    return unit
  
  # 删除.
  def delete(self):
    ids = self.params.getIdList("unitId")
    
    if ids == None or ids.size() == 0 : 
      self.addActionError(u"没有选择机构")
      return ActionResult.ERROR
    message = ""
    delnum = 0
    for unitId in ids:
      #判断是否存在用户
      userList = self.userService.getUserUnitList(unitId)
      if userList == None or userList.size() == 0:
        delnum = delnum + 1 
        self.unitService.removeUnit(unitId)
      else:
        message = message + u"\n机构ID=" + str(unitId) + u" 存在用户,不允许删除该机构"
    if message == "":  
      self.addActionMessage(u"操作成功")
      self.addActionLink(u"返回", "?cmd=list")
      return ActionResult.SUCCESS
    else:
      if delnum == 0:
        self.addActionError(message)
        return ActionResult.ERROR
      else:
        self.addActionMessage(u"共删除了" + str(delnum) + u"个的机构\n" + message)
        self.addActionLink(u"返回", "?cmd=list")
        return ActionResult.SUCCESS
    

  # 创建并返回一个分页对象.
  def createPager(self):
    # 调用Java的函数.
    pager = self.params.createPager()
    pager.itemName = u"机构"
    pager.itemUnit = u"个"
    pager.pageSize = 20
    return pager
    


  # 投影查询字段.
  def queryString(self):
    # 构造查询.
    query = UnitQuery(""" unit.unitId, unit.unitName, unit.unitType """)
    return query
    

  # AJAX 提供给 user, group 等修改时候切换了 district 得到新 unit_list select.
  def unit_options(self):
    unit_list = __jitar__.unitService.getAllUnitOrChildUnitList(None,[False])
    request.setAttribute("unit_list", unit_list)    
    return "/WEB-INF/ftl/admin/unit_options_ajax.ftl"
