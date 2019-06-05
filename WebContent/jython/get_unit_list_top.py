# coding=utf-8
from cn.edustar.jitar.util import ParamUtil

class get_unit_list_top:
    def execute(self):
        params = ParamUtil(request)
        unitId = params.safeGetIntParam("unitId")
        unitService = __spring__.getBean("unitService")
        unit = unitService.getUnitById(unitId)    
        request.setAttribute("unitId", unitId)
        if unit == None:
            request.setAttribute("unitTitle", "")
        else:
            request.setAttribute("unitTitle", unit.unitTitle)
        return "/WEB-INF/ftl/admin/get_unit_list_top.ftl"