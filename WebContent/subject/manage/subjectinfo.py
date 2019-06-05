from subject_page import *

class subjectinfo(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)

    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        if request.getMethod() == "POST":
            logo = self.params.safeGetStringParam("subjectlogo")
            header = self.params.safeGetStringParam("header")
            footer = self.params.safeGetStringParam("footer")
            if logo == "":
                self.subject.setLogo(None)
            else:
                self.subject.setLogo(logo)
                
            if header == "":
                self.subject.setHeaderContent(None)
            else:
                self.subject.setHeaderContent(header)
                
            if footer == "":
                self.subject.setFooterContent(None)
            else:
                self.subject.setFooterContent(footer)                
                
            self.subjectService.saveOrUpdateSubject(self.subject)
            return self.SUCCESS
        
        request.setAttribute("subject",self.subject)
        return "/WEB-INF/subjectmanage/subjectinfo.ftl"