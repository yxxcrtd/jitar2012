from base_unit import *

class unit_module(UnitBasePage):
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
            module = self.params.safeGetStringParam("module")
            if module == "":
                self.unit.setModuleName(None)
            else:
                self.unit.setModuleName(module)
            #print module
            self.unitService.saveOrUpdateUnit(self.unit)
            return self.SUCCESS            
        
        request.setAttribute("unit", self.unit)        
        return "/WEB-INF/unitsmanage/unit_module.ftl"
