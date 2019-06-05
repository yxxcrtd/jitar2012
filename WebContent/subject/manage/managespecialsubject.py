from subject_page import *
from cn.edustar.jitar.pojos import SpecialSubject
from base_specialsubject_page import SpecialSubjectQuery

class managespecialsubject(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.specialSubject_svc = __spring__.getBean("specialSubjectService")
        self.categoryService = __spring__.getBean("categoryService")
        self.specialSubject = None
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin() == False and self.isContentAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        if request.getMethod() == "POST":
            self.clear_subject_cache()
            return self.save_post()
        
        return self.get_method()
    
    def save_post(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            self.specialSubject_svc.deleteSpecialSubjectById(g)
        response.sendRedirect("managespecialsubject.py?id=" + str(self.subject.subjectId))
            
    def get_method(self):
        qry = SpecialSubjectQuery(""" ss.specialSubjectId, ss.objectGuid, ss.logo, ss.title,ss.createUserId, ss.createDate,ss.expiresDate """)
        qry.objectType = "subject"
        qry.objectId = self.subject.subjectId
        pager = self.params.createPager()
        pager.itemName = u"专题"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        ss_list = qry.query_map(pager)
        request.setAttribute("ss_list", ss_list)
        request.setAttribute("pager", pager)
        request.setAttribute("subject", self.subject)
        return "/WEB-INF/subjectmanage/managespecialsubject.ftl"
