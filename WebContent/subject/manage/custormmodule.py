from subject_page import *

class custormmodule(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)

    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        if request.getMethod() == "POST":
            guids = self.params.safeGetIntValues("guid")
            cmd = self.params.safeGetStringParam("cmd")
            for g in guids:
                wb = self.subjectService.getSubjectWebpartById(g)
                if wb != None:
                    if cmd == "delete":
                        self.subjectService.deleteSubjectWebpart(wb)
                    if cmd == "visible":
                        wb.setVisible(True)
                        self.subjectService.saveOrUpdateSubjectWebpart(wb)
                    if cmd == "hidden":
                        wb.setVisible(False)
                        self.subjectService.saveOrUpdateSubjectWebpart(wb)
            
        webpart_list = self.subjectService.getWebpartList(self.subject.subjectId, False)
        request.setAttribute("webpart_list", webpart_list)
        request.setAttribute("subject", self.subject)
        
        return "/WEB-INF/subjectmanage/custormmodule.ftl"
