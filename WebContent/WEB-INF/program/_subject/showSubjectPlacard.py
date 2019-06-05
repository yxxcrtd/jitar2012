from subject_page import *

class showSubjectPlacard(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        
    def execute(self):
        if self.subject == None:
            self.addActionError(u"无法加载指定的学科。")
            return self.ERROR
        
        placardService = __spring__.getBean("placardService")
        placardId = self.params.safeGetIntParam("placardId")
        placard = placardService.getPlacard(placardId)
        if placard == None:
            self.addActionError(u"该条公告不存在。")
            return self.ERROR
        
        if self.isAdmin() == False:
            if placard.hide == True:
                self.addActionError(u"该条公告不是公开的。")
                return self.ERROR
        
        templateName = "template1"
        if self.subject.templateName != None:
            templateName = self.subject.templateName
        request.setAttribute("subject",self.subject)
        request.setAttribute("placard",placard)
        return "/WEB-INF/subjectpage/" + templateName + "/showSubjectPlacard.ftl"