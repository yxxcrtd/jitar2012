from subject_page import *
from specialSubjectArticle_query import SpecialSubjectArticleQuery

class show_article_list(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.specialSubjectService = __spring__.getBean("specialSubjectService")
        self.specialSubject = None
        self.backYearList = None
        
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
        qry = SpecialSubjectArticleQuery(""" ssa.articleId, ssa.title, ssa.userId, ssa.userTrueName, ssa.createDate, ssa.loginName, ssa.typeState """)
        qry.specialSubjectId = self.specialSubject.specialSubjectId        
        pager = self.params.createPager()
        pager.itemName = u"文章"
        pager.itemUnit = u"篇"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        article_list = qry.query_map(pager)
        request.setAttribute("article_list", article_list)
        request.setAttribute("pager", pager)        
        request.setAttribute("specialSubject", self.specialSubject)        
        request.setAttribute("subject",self.subject)
        request.setAttribute("head_nav","specialsubject")
        return "/WEB-INF/subjectpage/" + self.templateName + "/show_article_list.ftl"