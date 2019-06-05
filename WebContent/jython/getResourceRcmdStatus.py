from cn.edustar.jitar.util import ParamUtil

class getResourceRcmdStatus:
    def execute(self):
        resourceService = __spring__.getBean("resourceService")
        unitService = __spring__.getBean("unitService")
        
        param = ParamUtil(request)        
        data = param.safeGetIntValues("data")
        unitId = param.safeGetIntParam("unitId")
        if unitId == 0 or len(data) < 1:
            return;
        unit = unitService.getUnitById(unitId)
        if unit == None:
            return
        
        
        # 得到全部的下级单位
        unitList = unitService.getDownUnitList(unit)
        returnText = ""
        for g in data:
            resource = resourceService.getResource(g)
            if resource != None:
                rcmdPathInfo = resource.rcmdPathInfo
                if rcmdPathInfo == None:
                    rcmdPathInfo = ""
                HasRcmd = False
                for ut in unitList:
                    if rcmdPathInfo.find("/" + str(ut.unitId) + "/") > -1:
                        HasRcmd = True
                if HasRcmd == False:
                    returnText = returnText + "," + str(g)
        response.getWriter().println(returnText)
