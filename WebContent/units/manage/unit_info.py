from unit_page import *
from java.lang import String
from cn.edustar.jitar.util import FileCache

class unit_info(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.unitTypeService = __jitar__.unitTypeService
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        
        if self.isUnitAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
        
        unitTypeList = self.unitTypeService.getUnitTypeNameList()
        request.setAttribute("unitTypeList", unitTypeList)        
        if request.getMethod() == "POST":
            unitTitle = self.params.safeGetStringParam("unitTitle")
            siteTitle = self.params.safeGetStringParam("siteTitle")
            logo = self.params.safeGetStringParam("unitlogo")
            unitName = self.params.safeGetStringParam("unitName")
            header = self.params.safeGetStringParam("header")
            footer = self.params.safeGetStringParam("footer")
            unitType = self.params.safeGetStringParam("unitType")
            unitPhoto = self.params.safeGetStringParam("unitPhoto")
            unitInfo = self.params.safeGetStringParam("unitInfo")
            if unitTitle == "":
                self.addActionError(u"机构名称不能为空。")
                return self.ERROR
            if siteTitle == "":
                self.addActionError(u"机构网站名称不能为空。")
                return self.ERROR
            if unitName == "":
                self.addActionError(u"机构英文名称不能为空。")
                return self.ERROR
            if unitType == "":
                self.addActionError(u"机构属性不能为空。")
                return self.ERROR            
            if String(unitName).matches("^[A-Za-z0-9_]+$") == False:
                self.addActionError(u"机构英文名称只能是英文、字母、下划线。")
                return self.ERROR
            
            #检查英文名称是否存在
            un = self.unitService.getUnitByName(unitName)
            if un != None and un.unitId != self.unit.unitId:
                self.addActionError(u"机构英文名称已经存在，请换另外一个名字。")
                return self.ERROR
            
            self.unit.setUnitName(unitName)
            self.unit.setUnitTitle(unitTitle)
            self.unit.setSiteTitle(siteTitle)
            self.unit.setUnitType(unitType)
            if logo == "":
                self.unit.setUnitLogo(None)
            else:
                if logo.find("/") == -1:
                    self.addActionError(u"机构 Logo 必须是一个 URL 地址，相对地址或者绝对地址均可。")
                    return self.ERROR
                self.unit.setUnitLogo(logo)
            
            if unitPhoto == "":
                self.unit.setUnitPhoto(None)
            else:
                if unitPhoto.find("/") == -1:
                    self.addActionError(u"机构 图片必须是一个 URL地址，相对地址或者绝对地址均可。")
                    return self.ERROR
                self.unit.setUnitPhoto(unitPhoto)
                            
            #if desc == "":
            #    self.unit.setUnitDescription(None)
            #else:
            #    self.unit.setUnitDescription(desc)
                
            if header == "":
                self.unit.setHeaderContent(None)
            else:
                self.unit.setHeaderContent(header)
                
            if footer == "":
                self.unit.setFooterContent(None)
            else:
                self.unit.setFooterContent(footer)                
            
            if unitInfo == "":
                self.unit.setUnitInfo(None)
            else:
                self.unit.setUnitInfo(unitInfo)
                                
            self.unitService.saveOrUpdateUnit(self.unit)
            
            fc = FileCache()
            fc.deleteUnitCacheFile(self.unit.unitName)
            fc = None            
            return self.SUCCESS            
        
        request.setAttribute("unit", self.unit)
        
        return "/WEB-INF/unitsmanage/unit_info.ftl"
