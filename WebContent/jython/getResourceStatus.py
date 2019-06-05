from cn.edustar.jitar.util import ParamUtil

class getResourceStatus:
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
        
        returnText = ""
        for g in data:
            resource = resourceService.getResource(g)
            if resource != None:
                approvedPathInfo = resource.approvedPathInfo
                if approvedPathInfo == None:
                    approvedPathInfo = ""
                
                
                unitId = resource.getUnitId()
                if unitId != None:                    
                    unitArticle = unitService.getUnitById(unitId)
                    if unitArticle != None:
                        pathinfo = unitService.getUnitPathBetweenUnits(unit, unitArticle)                        
                        if pathinfo != "":
                            arraypath = pathinfo.split("/")
                            needApprove = False
                            for p in arraypath:
                                if p != "":
                                    ps = "/" + p + "/"           
                                    if approvedPathInfo.find(ps) == -1:
                                        needApprove = True
                            if needApprove == True:
                                returnText = returnText + "," + str(g)
        response.getWriter().println(returnText)
