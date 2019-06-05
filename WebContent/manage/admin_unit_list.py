class admin_unit_list:
    def __init__(self):
        self.unitService = __spring__.getBean("unitService")
        self.unit_main_url = "admin_unit_main.py"
    def execute(self):
        self.get_unit_tree()
        return "/WEB-INF/ftl/admin/admin_unit_list.ftl"
    
    def get_unit_tree(self):
        u_list = self.unitService.getChildUnitListByParenId(0,[False])
        if len(u_list) < 1:
            rootUnit = None
        else:
            rootUnit = u_list[0]
        if rootUnit == None:
            request.setAttribute("html", "")
            return
        html = "d.add(" + str(rootUnit.unitId) + ",-1,'<b>" + rootUnit.unitTitle + "</b>','" + self.unit_main_url + "?unitId=" + str(rootUnit.unitId) + "','" + rootUnit.unitTitle + "','unitmain');"
        html += self.getChildUnit(rootUnit)
        #print html
        request.setAttribute("html", html)
        
    def getChildUnit(self, parentUnit):
        s = "";
        #print "self.unitService.getConfigUnitLevel() = ",self.unitService.getConfigUnitLevel()
        if self.unitService.getUnitDeepLevel(parentUnit) >= self.unitService.getConfigUnitLevel()-1:
            return s
        unitList = self.unitService.getAllUnitOrChildUnitList(parentUnit,[False])
        if unitList != None:
            for unit in unitList:
                s += "d.add(" + str(unit.unitId) + "," + str(parentUnit.unitId) + ",'" + unit.unitTitle + "','" + self.unit_main_url + "?unitId=" + str(unit.unitId) + "','" + unit.unitTitle + "','unitmain');\r\n"
                s += self.getChildUnit(unit)
        return s