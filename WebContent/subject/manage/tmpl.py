from subject_page import *

class tmpl(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)

    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        if request.getMethod() == "POST":
            tmpl = self.params.safeGetStringParam("tmpl")
            if tmpl == "":
                self.subject.setTemplateName(None)
            else:
                self.subject.setTemplateName(tmpl)
            self.subjectService.saveOrUpdateSubject(self.subject)
            return self.SUCCESS
        
        request.setAttribute("subject",self.subject)
        return "/WEB-INF/subjectmanage/tmpl.ftl"