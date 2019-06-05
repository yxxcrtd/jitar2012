from cn.edustar.jitar.util import ParamUtil

class get_unit_list:
    def execute(self):
        params = ParamUtil(request)
        fromUnitId = params.safeGetIntParam("fromUnitId")
        if fromUnitId != 0:
            request.setAttribute("fromUnitId", fromUnitId)
        return "/WEB-INF/ftl/admin/get_unit_list.ftl"
