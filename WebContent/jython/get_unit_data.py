from com.alibaba.fastjson import JSONObject
from cn.edustar.jitar.util import ParamUtil

class get_unit_data:
    def execute(self):
        params = ParamUtil(request)
        pid = params.safeGetIntParam("pid")
        unitService = __spring__.getBean("unitService")
        unitlist = unitService.getChildUnitListByParenId(pid,[False])
        json = "["
        jsonData = JSONObject()
        for u in unitlist:
            jsonData.put("NodeId",str(u.unitId))
            jsonData.put("LinkText",u.unitTitle + u"（" + u.siteTitle + u"）")
            jsonData.put("LinkTitle",u.siteTitle)
            jsonData.put("LinkHref","transfer.py?unitName=" + u.unitName)
            if u.hasChild:
                jsonData.put("ChildCount","1")
            else:
                jsonData.put("ChildCount","0")
            json += jsonData.toString() + ","
        if json.endswith(","):
            json = json[0:len(json)-1]
        json += "]"
        response.setCharacterEncoding("UTF-8")
        response.setContentType("text/plain")
        response.getWriter().write(json)