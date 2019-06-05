from subject_page import *
from base_specialsubject_page import SpecialSubjectQuery

class show_more_specialsubject_list(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.specialSubjectService = __spring__.getBean("specialSubjectService")
        self.specialSubject = None
        
    def execute(self):
        if self.subject == None:
            self.addActionError(u"无法加载指定的学科。")
            return self.ERROR
        
        cache = __jitar__.cacheProvider.getCache('page')
        
        self.templateName = "template1"
        if self.subject.templateName != None:
            self.templateName = self.subject.templateName
        
        specialSubjectId = self.params.safeGetIntParam("specialSubjectId")
        if specialSubjectId == 0:
            self.specialSubject = self.specialSubjectService.getNewestSpecialSubjectByType("subject")
            if self.specialSubject != None:
                specialSubjectId = self.specialSubject.specialSubjectId
                                
        if specialSubjectId > 0:
            if self.specialSubject == None:
                self.specialSubject = self.specialSubjectService.getSpecialSubject(specialSubjectId)
            if self.specialSubject == None:
                self.addActionError(u"无法加载指定的专题。")
                return self.ERROR
            
            return self.show_specialsubject_list()
        else:
            request.setAttribute("subject",self.subject)
            request.setAttribute("head_nav","specialsubject")
            return "/WEB-INF/subjectpage/" + self.templateName + "/specialsubject_page_error.ftl"
        
    def show_specialsubject_list(self):
        qry = SpecialSubjectQuery(""" ss.specialSubjectId, ss.objectGuid, ss.title,ss.logo,ss.description,ss.createDate,ss.expiresDate """)
        qry.objectId = self.subject.subjectId
        qry.objectType = "subject"
        pager = self.params.createPager()
        pager.itemName = u"学科专题"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        specialsubject_list = qry.query_map(pager)
        request.setAttribute("specialsubject_list", specialsubject_list)
        request.setAttribute("pager", pager)        
        request.setAttribute("specialSubject", self.specialSubject)        
        request.setAttribute("subject",self.subject)
        request.setAttribute("head_nav","specialsubject")
        return "/WEB-INF/subjectpage/" + self.templateName + "/show_more_specialsubject_list.ftl"