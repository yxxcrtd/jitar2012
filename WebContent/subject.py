from cn.edustar.jitar.util import ParamUtil
from javax.servlet.http import HttpServletResponse

class subject:
    def __init__(self):
        self.subjectService = __spring__.getBean("subjectService")
        
    def execute(self):
        params = ParamUtil(request)
        unitId = 0
        if params.existParam("unitId") == True:
            unitId = params.safeGetIntParam("unitId")
        subjectUrlPattern = request.getAttribute("SubjectUrlPattern")
        contextPath = request.getContextPath()
        if contextPath != "/":
            contextPath = contextPath + "/"
        if params.existParam("id") == True:
            id = params.safeGetIntParam("id")
            subject = self.subjectService.getSubjectById(id)
            if subject != None:
                subjectCode = subject.subjectCode
                if subjectUrlPattern != None and subjectUrlPattern != "":
                    subjectUrlPattern = subjectUrlPattern.replace("{subjectCode}", subjectCode)
                    if unitId != 0:
                        subjectUrlPattern = subjectUrlPattern + "?unitId=" + str(unitId)
                    response.setStatus(301)
                    response.setHeader("Location", subjectUrlPattern)
                    response.setHeader("Connection", "close")
                    #response.sendRedirect(subjectUrlPattern)
                    return
                else:
                    response.setStatus(301)
                    subjectUrl = request.getContextPath() + "/k/" + subjectCode + "/"
                    if unitId != 0:
                        subjectUrl = subjectUrl + "?unitId=" + str(unitId)
                    response.setHeader("Location", subjectUrl)
                    response.setHeader("Connection", "close")
                    #response.sendRedirect(request.getContextPath() + "/k/" + subjectCode + "/")
                    return
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return
        elif params.existParam("subjectId") == True and params.existParam("gradeId") == True :
            metaSubjectId = params.safeGetIntParam("subjectId")
            metaGradeId = params.safeGetIntParam("gradeId")
            subject = self.subjectService.getSubjectByMetaData(metaSubjectId, metaGradeId)
            if subject != None:
                subjectCode = subject.subjectCode
                if subjectUrlPattern != None and subjectUrlPattern != "":
                    subjectUrlPattern = subjectUrlPattern.replace("{subjectCode}", subjectCode)
                    if unitId != 0:
                        subjectUrlPattern = subjectUrlPattern + "?unitId=" + str(unitId)
                    response.setStatus(301)
                    response.setHeader("Location", subjectUrlPattern)
                    response.setHeader("Connection", "close")
                    #response.sendRedirect(subjectUrlPattern)
                    return
                else:
                    response.setStatus(301)
                    subjectUrl = request.getContextPath() + "/k/" + subjectCode + "/"
                    if unitId != 0:
                        subjectUrl = subjectUrl + "?unitId=" + str(unitId)
                        
                    response.setHeader("Location", subjectUrl)
                    response.setHeader("Connection", "close")
                    #response.sendRedirect(request.getContextPath() + "/k/" + subjectCode + "/")
                    return
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return
