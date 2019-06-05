from subject_page import *
from cn.edustar.jitar.pojos import SubjectWebpart

class add_custorm_module(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)

    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        webpartId = self.params.safeGetIntParam("webpartId")
        if webpartId == 0:
            subjectWebpart = SubjectWebpart()
        else:
            subjectWebpart = self.subjectService.getSubjectWebpartById(webpartId)
            
        if request.getMethod() == "POST":
            moduleName = self.params.safeGetStringParam("moduleName")
            webpartZone = self.params.safeGetIntParam("webpartZone")
            content = self.params.safeGetStringParam("content")
            if "" == moduleName:
                self.addActionError(u"请输入模块名称。")
                return self.ERROR
            
            if 0 == webpartZone:
                self.addActionError(u"请选择模块位置。")
                return self.ERROR
            
            if "" == content:
                self.addActionError(u"请输入模块内容。")
                return self.ERROR            
            
            if webpartId == 0:                
                subjectWebpart.setSubjectId(self.subject.subjectId)
                subjectWebpart.setRowIndex(0)
                subjectWebpart.setSystemModule(False)
                subjectWebpart.setVisible(True)
            subjectWebpart.setModuleName(moduleName)
            subjectWebpart.setDisplayName(moduleName)
            subjectWebpart.setWebpartZone(webpartZone)
            subjectWebpart.setContent(content)
            self.subjectService.saveOrUpdateSubjectWebpart(subjectWebpart)
            response.sendRedirect("custormmodule.py?id=" + str(self.subject.subjectId))            
        
        request.setAttribute("subjectWebpart", subjectWebpart)
        request.setAttribute("subject", self.subject)
        return "/WEB-INF/subjectmanage/add_custorm_module.ftl"
