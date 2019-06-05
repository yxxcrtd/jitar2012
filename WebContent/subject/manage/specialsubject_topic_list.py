from subject_page import *

class specialsubject_topic_list(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.specialSubject_svc = __spring__.getBean("specialSubjectService")
        self.specialSubject = None
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isContentAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        specialSubjectId = self.params.safeGetIntParam("specialSubjectId")
        
        if specialSubjectId > 0:
            self.specialSubject = self.specialSubject_svc.getSpecialSubject(specialSubjectId)
        
        if request.getMethod() == "POST":
            self.clear_subject_cache()
            return self.save_post()
        return self.get_method()
    
    def get_method(self):
        
        return "/WEB-INF/subjectmanage/specialsubject_topic_list.ftl"