from unit_page import UnitBasePage
from base_action import SubjectMixiner

class unit_child(UnitBasePage, SubjectMixiner):
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
        
        cmd = self.params.safeGetStringParam("cmd")
        childUnitId = self.params.safeGetIntParam("childUnitId")
        if cmd == "delete" and childUnitId > 0:
            childUnit = self.unitService.getUnitById(childUnitId)
            if childUnit != None:
                if childUnit.hasChild:
                    self.addActionError(u"请先删除下级机构。。")
                    return self.ERROR
                childUnit.delState = True
                self.unitService.deleteUnit(childUnit)
        
        childunitlist = self.unitService.getAllUnitOrChildUnitList(self.unit,[False])
        request.setAttribute("unit", self.unit)
        request.setAttribute("childunitlist", childunitlist)
        return "/WEB-INF/unitsmanage/unit_child.ftl"
