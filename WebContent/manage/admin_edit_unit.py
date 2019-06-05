from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Unit, SiteNav, UnitWebpart
from cn.edustar.jitar.util import CommonUtil

from base_action import *

class admin_edit_unit(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
        self.unitService = __spring__.getBean("unitService")
        self.unitTypeService = __spring__.getBean("unitTypeService")
        
    def execute(self):
        parentId = self.params.safeGetIntParam("pid")
        unitId = self.params.safeGetIntParam("unitId")
        parentUnit = self.unitService.getUnitById(parentId)
        if parentUnit == None:
            self.addActionError(u"无效的父节点。")
            self.addActionLink(u"返回", "admin_edit_unit.py?pid=" + str(parentId) + "&unitId=" + str(unitId))
            return ActionResult.ERROR
        
        unitLevel = self.unitService.getUnitDeepLevel(parentUnit)
        if unitLevel > self.unitService.getConfigUnitLevel():
            self.addActionError(u"不允许创建更多的子单位。")
            self.addActionLink(u"返回", "admin_unit_manage.py")
            return ActionResult.ERROR
        unit = self.unitService.getUnitById(unitId)
        
        unitTypeList = self.unitTypeService.getUnitTypeNameList()
        request.setAttribute("unitTypeList", unitTypeList)
        
        if request.getMethod() == "POST":
            title = self.params.safeGetStringParam("title")
            enname = self.params.safeGetStringParam("enname")
            siteTitle = self.params.safeGetStringParam("siteTitle")
            unitType = self.params.safeGetStringParam("unitType")
            if enname == "" or title == "":
                self.addActionError(u"机构名称和英文名称不能为空。")
                self.addActionLink(u"返回", "admin_edit_unit.py?pid=" + str(parentId) + "&unitId=" + str(unitId))
                return ActionResult.ERROR
            if unitType == "":
                self.addActionError(u"机构属性不能为空。")
                self.addActionLink(u"返回", "admin_edit_unit.py?pid=" + str(parentId) + "&unitId=" + str(unitId))
                return ActionResult.ERROR
            if siteTitle == "":
                self.addActionError(u"机构网站名称不能为空。")
                self.addActionLink(u"返回", "admin_edit_unit.py?pid=" + str(parentId) + "&unitId=" + str(unitId))
                return ActionResult.ERROR
            if CommonUtil.isValidName(enname) == False:
                self.addActionError(u"英文名称只能是英文字母、数字并且必须以英文字母开头。")
                self.addActionLink(u"返回", "admin_edit_unit.py?pid=" + str(parentId) + "&unitId=" + str(unitId))
                return ActionResult.ERROR
            
            if len(siteTitle)> 128:
                self.addActionError(u"网站名称长度不能大于128个字符。")
                self.addActionLink(u"返回", "admin_edit_unit.py?pid=" + str(parentId) + "&unitId=" + str(unitId))
                return ActionResult.ERROR
            
            if len(title)> 128:
                self.addActionError(u"机构名称长度不能大于128个字符。")
                self.addActionLink(u"返回", "admin_edit_unit.py?pid=" + str(parentId) + "&unitId=" + str(unitId))
                return ActionResult.ERROR
            
            if len(enname)> 20:
                self.addActionError(u"英文名称长度不能大于20个字符。")
                self.addActionLink(u"返回", "admin_edit_unit.py?pid=" + str(parentId) + "&unitId=" + str(unitId))
                return ActionResult.ERROR
            
            #检查英文名称是否已经存在
            checkUnit = self.unitService.getUnitByName(enname)
            if checkUnit != None:
                if unit == None:
                    #新建的机构
                    self.addActionError(u"机构的英文名称已经存在，请更换一个新的名称。")
                    self.addActionLink(u"返回", "admin_edit_unit.py?pid=" + str(parentId) + "&unitId=" + str(unitId))
                    return ActionResult.ERROR
                else:
                    if checkUnit.unitId != unit.unitId:
                        self.addActionError(u"机构的英文名称已经存在，请更换一个新的名称。")
                        self.addActionLink(u"返回", "admin_edit_unit.py?pid=" + str(parentId) + "&unitId=" + str(unitId))
                        return ActionResult.ERROR

            if unit == None:
                unit = Unit()
                unit.setParentId(parentUnit.unitId)
                unit.setUnitPathInfo("")
                unit.setHasChild(False)
            unit.setUnitName(enname)
            unit.setUnitTitle(title)
            unit.setSiteTitle(siteTitle)
            unit.setUnitType(unitType)
            self.unitService.saveOrUpdateUnit(unit)
            if parentUnit.hasChild == False:
                parentUnit.setHasChild(True)
                self.unitService.saveOrUpdateUnit(parentUnit)
            if unitId == 0:
                self.init_unit_nav(unit)
                self.genWebparts(unit)
            request.setAttribute("parentUnit", parentUnit)
            self.unitService.updateAccessControlUnitTitle(unit)
            return "/WEB-INF/ftl/admin/admin_edit_unit_success.ftl"
        
        request.setAttribute("parentUnit", parentUnit)
        request.setAttribute("unit", unit)
        return "/WEB-INF/ftl/admin/admin_edit_unit.ftl"
    
    def init_unit_nav(self, unit):
        unitId = unit.unitId
        siteNavService = __spring__.getBean("siteNavService")       
        siteNameArray = [u"总站首页", u"机构首页", u"机构文章", u"机构资源", u"机构图片", u"机构视频", u"机构工作室"];
        siteUrlArray = ["py/subjectHome.action", "", "article/", "resource/", "photo/", "video/", "blog/"]
        siteHightlightArray = ["index", "unit", "unit_article", "unit_resource", "unit_photo", "unit_video", "unit_user"]
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
            
    def genWebparts(self, unit):
        unit_webpart_list = self.unitService.getUnitWebpartList(unit.unitId)
        if len(unit_webpart_list) == 0:                
            moduleName = [u"机构文章", u"机构图片", u"机构资源", u"机构视频", u"图片新闻",u"机构协作组", u"最新动态", u"最新公告", u"统计信息", u"调查投票", u"机构学科",u"机构集备", u"友情链接"]
            i = 0
            for m in moduleName:
                i = i + 1
                unitWebpart = UnitWebpart()
                unitWebpart.setModuleName(m)
                unitWebpart.setDisplayName(m)
                unitWebpart.setRowIndex(i)
                unitWebpart.setVisible(True)
                unitWebpart.setSystemModule(True)
                unitWebpart.setUnitId(unit.getUnitId())
                if m == u"机构文章" or  m == u"机构资源" or  m == u"机构视频" or m == u"机构集备":
                   unitWebpart.setWebpartZone(4)
                elif m == u"图片新闻" or m == u"友情链接" or m == u"机构协作组":
                    unitWebpart.setWebpartZone(3)
                elif m == u"图片模块":
                    unitWebpart.setWebpartZone(2)
                elif m == u"机构学科":
                    unitWebpart.setWebpartZone(1)
                else:
                    unitWebpart.setWebpartZone(5)
                self.unitService.saveOrUpdateUnitWebpart(unitWebpart)
