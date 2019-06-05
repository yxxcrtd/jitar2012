# coding=utf-8
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.util import ParamUtil

class get_unit_list_middle:
    def __init__(self):
        self.unitService = __spring__.getBean("unitService")
        self.unit_main_url = "get_unit_list_top.py"
        self.initUnitId = None
        
    def execute(self):
        params = ParamUtil(request)
        self.initUnitId = params.safeGetIntParam("fromUnitId")
        if self.initUnitId < 1:
            self.initUnitId = None
            
        self.get_unit_tree()
        return "/WEB-INF/ftl/admin/get_unit_list_middle.ftl"
    
    def get_unit_tree(self):
        if self.initUnitId != None:
            rootUnit = self.unitService.getUnitById(self.initUnitId)
            if rootUnit == None:
                request.setAttribute("html", "")
                return
        else:
            u_list = self.unitService.getChildUnitListByParenId(0,[False])
            if len(u_list) < 1:
                rootUnit = None
            else:
                rootUnit = u_list[0]
            if rootUnit == None:
                request.setAttribute("html", "")
                return
        
        html = "d.add(" + str(rootUnit.unitId) + ",-1,'<b>" + rootUnit.unitTitle + "</b>','" + self.unit_main_url + "?unitId=" + str(rootUnit.unitId) + "','" + rootUnit.unitTitle + "','topframe');"
        html += self.getChildUnit(rootUnit)
        #print html
        request.setAttribute("html", html)
        
    def getChildUnit(self, parentUnit):
        s = "";
        unitList = self.unitService.getAllUnitOrChildUnitList(parentUnit,[False])
        if unitList != None:
            for unit in unitList:
                s += "d.add(" + str(unit.unitId) + "," + str(parentUnit.unitId) + ",'" + unit.unitTitle + "','" + self.unit_main_url + "?unitId=" + str(unit.unitId) + "','" + unit.unitTitle + "','topframe');\r\n"
                s += self.getChildUnit(unit)
        return s
