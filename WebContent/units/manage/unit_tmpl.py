from unit_page import *
from cn.edustar.jitar.util import FileCache

class unit_tmpl(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        
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
        
        if request.getMethod() == "POST":
            tmpl = self.params.safeGetStringParam("tmpl")
            if tmpl == "":
                self.unit.setTemplateName(None)
            else:
                self.unit.setTemplateName(tmpl)
            self.unitService.saveOrUpdateUnit(self.unit)
            fc = FileCache()
            fc.deleteUnitCacheFile(self.unit.unitName)
            fc = None
            return self.SUCCESS
        
        request.setAttribute("unit", self.unit)
        return "/WEB-INF/unitsmanage/unit_tmpl.ftl"   
