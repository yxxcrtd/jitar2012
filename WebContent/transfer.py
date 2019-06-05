from cn.edustar.jitar.util import ParamUtil
from javax.servlet.http import HttpServletResponse

class transfer:
    def __init__(self):
    	request.setAttribute("ru", request.getScheme() + "://" + request.getServerName() + ":" + str(request.getServerPort()) + request.getContextPath())
        self.unitService = __spring__.getBean("unitService")
        
    def execute(self):
        params = ParamUtil(request)
        contextPath = request.getContextPath()
        if contextPath != "/":
            contextPath = contextPath + "/"
        
        unitUrlPattern = request.getAttribute("UnitUrlPattern")
        if params.existParam("unitId") == True:
            unitId = params.safeGetIntParam("unitId")
            unit = self.unitService.getUnitById(unitId)
            if unit == None:
                response.contentType = "text/html; charset=UTF-8"
                response.getWriter().write(u"单位不存在。")
                return
            
            unitName = unit.unitName
            if unitName == None or unitName == "":
                response.contentType = "text/html; charset=UTF-8"
                response.getWriter().write(u"没有设置机构的英文名称。")
                return
            if unitUrlPattern != None and unitUrlPattern != "":
                unitUrlPattern = unitUrlPattern.replace("{unitName}", unitName)
                response.setStatus(301)
                response.setHeader("Location", unitUrlPattern)
                response.setHeader("Connection", "close")
                #response.sendRedirect(unitUrlPattern)
                return
            else:
                response.setStatus(301)
                response.setHeader("Location", contextPath + "d/" + unitName + "/")
                response.setHeader("Connection", "close")
                #response.sendRedirect(contextPath + "d/" + unitName + "/")
                return
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return
        elif params.existParam("unitName") == True:
            unitName = params.safeGetStringParam("unitName", None)
            if unitName == None:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return
            if unitUrlPattern != None and unitUrlPattern != "":
                unitUrlPattern = unitUrlPattern.replace("{unitName}", unitName)
                response.setStatus(301)
                response.setHeader("Location", unitUrlPattern)
                response.setHeader("Connection", "close")
                #response.sendRedirect(unitUrlPattern)
                return
            else:
                unit = self.unitService.getUnitByName(unitName)
                if unit != None:
                    response.setStatus(301)
                    response.setHeader("Location", contextPath + "d/" + unitName + "/")
                    response.setHeader("Connection", "close")                
                    #response.sendRedirect(contextPath + "d/" +unitName + "/")
                    return
                else:
                    response.contentType = "text/html; charset=UTF-8"
                    response.getWriter().write(u"没有设置机构的英文名称。")
                    return
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return
