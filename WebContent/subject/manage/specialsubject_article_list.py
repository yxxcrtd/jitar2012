from subject_page import *
from specialSubjectArticle_query import SpecialSubjectArticleQuery

class specialsubject_article_list(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.specialSubjectId = 0
        self.specialSubject_svc = __spring__.getBean("specialSubjectService")
        self.articleService =  __spring__.getBean("articleService")
        self.specialSubject = None
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isContentAdmin() == False and self.isAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
           
        self.specialSubjectId = self.params.safeGetIntParam("specialSubjectId")
        if self.specialSubjectId == 0:
            self.addActionError(u"无效的专题标识、")
            return self.ERROR
        
        self.specialSubject = self.specialSubject_svc.getSpecialSubject(self.specialSubjectId)
        if self.specialSubject == None:
            self.addActionError(u"不能加载专题、")
            return self.ERROR
        
        if request.getMethod() == "POST":
            self.clear_subject_cache()
            return self.save_post()
        return self.get_method()
    
    def save_post(self):
        cmd = self.params.safeGetStringParam("cmd")
        guids = self.params.getRequestParamValues("guid")
        if cmd == "remove":
            for g in guids:
                article = self.specialSubject_svc.getSpecialSubjectArticleByArticleId(g)
                if article != None:                    
                    self.specialSubject_svc.deleteSubjectArticleByArticleGuid(g)
        response.sendRedirect(request.getHeader("Referer"))
    
    def get_method(self):
        self.collectionQueryString()
        
        qry = SpecialSubjectArticleQuery(""" ssa.articleId, ssa.articleGuid, ssa.title, ssa.createDate, ssa.loginName, ssa.userId, ssa.userTrueName """)
        
        #if self.rcmdState != "" and self.rcmdState.isdigit() == True:
        #    if self.rcmdState == "1":
        #        qry.specialSubjectStatus = 1
        #    else:
        #        qry.specialSubjectStatus = 0
            
            
        qry.specialSubjectId = self.specialSubjectId
        pager = self.params.createPager()
        pager.itemName = u"文章"
        pager.itemUnit = u"篇"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        article_list = qry.query_map(pager)
        request.setAttribute("article_list", article_list)
        request.setAttribute("pager", pager)
        request.setAttribute("specialSubject", self.specialSubject)
        request.setAttribute("subject", self.subject)     
        return "/WEB-INF/subjectmanage/specialsubject_article_list.ftl"
    
    def collectionQueryString(self):
        self.rcmdState = self.params.safeGetStringParam("sr","")
        request.setAttribute("sr",self.rcmdState)
        
    def triblesimu(self,int1):
        if int1 == "0":
            return False
        else:
            return True  