from subject_page import *
from plugintopic_query import *

class show_topic_list(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.specialSubjectService = __spring__.getBean("specialSubjectService")
        self.specialSubject = None
        
    def execute(self):
        if self.subject == None:
            self.addActionError(u"无法加载指定的学科。")
            return self.ERROR
        
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
            
            return self.show_list()
        else:
            request.setAttribute("subject",self.subject)
            request.setAttribute("head_nav","specialsubject")
            return "/WEB-INF/subjectpage/" + self.templateName + "/specialsubject_page_error.ftl"
        
    def show_list(self):
        qry = PlugInTopicQuery(""" pt.plugInTopicId, pt.title, pt.createUserId, pt.createUserName, pt.createDate,pt.parentGuid,pt.parentObjectType """)
        qry.parentGuid = self.specialSubject.objectGuid
        qry.parentObjectType = "specialsubject"
        pager = self.params.createPager()
        pager.itemName = u"讨论"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        topic_list = qry.query_map(pager)
        request.setAttribute("topic_list", topic_list)
        request.setAttribute("pager", pager)
        
        request.setAttribute("topic_list", topic_list)
        request.setAttribute("pager", pager)        
        request.setAttribute("specialSubject", self.specialSubject)        
        request.setAttribute("subject",self.subject)
        request.setAttribute("head_nav","specialsubject")
        return "/WEB-INF/subjectpage/" + self.templateName + "/show_topic_list.ftl"