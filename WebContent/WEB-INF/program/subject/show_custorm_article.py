from subject_page import *
from article_query import ArticleQuery
from contentspacearticle_query import ContentSpaceArticleQuery

class show_custorm_article(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        
    def execute(self):
        if self.subject == None:
            self.addActionError(u"无法加载指定的学科。")
            return self.ERROR
        
        self.templateName = "template1"
        partType = self.params.safeGetIntParam("partType")
        if self.subject.templateName != None:
            self.templateName = self.subject.templateName
        if partType == 2:
            qry = ContentSpaceArticleQuery(""" csa.title,csa.contentSpaceArticleId,csa.createDate,csa.pictureUrl,csa.viewCount,cs.contentSpaceId  """)
            qry.contentSpaceId = self.params.safeGetIntParam("categoryId")
        else: 
            unitId = self.params.safeGetIntParam("unitId")
            if unitId == None:
                unitId = 0
            qry = ArticleQuery(""" a.articleId, a.title, a.createDate, a.viewCount, a.commentCount, a.userId,
                                  a.recommendState, a.typeState, u.loginName, u.nickName, u.trueName """)                
            qry.subjectId = self.subject.metaSubject.msubjId
            qry.gradeId = self.subject.metaGrade.gradeId
            qry.FuzzyMatch = True
            qry.sysCateId = self.params.getIntParamZeroAsNull("categoryId")
            qry.custormAndWhereClause = "a.approvedPathInfo LIKE '%/" + str(unitId) + "/%'"
            request.setAttribute("unitId", unitId)
        
        pager = self.createPager()
        pager.totalRows = qry.count()
        article_list = qry.query_map(pager)
        request.setAttribute("article_list", article_list)
        request.setAttribute("pager", pager)
        request.setAttribute("partType", partType)
        request.setAttribute("subject", self.subject)
        request.setAttribute("Page_Title", self.params.safeGetStringParam("title"))
        
        return "/WEB-INF/subjectpage/" + self.templateName + "/show_custorm_article.ftl"
            
    def createPager(self):
        pager = self.params.createPager()
        pager.pageSize = 30
        pager.itemName = u"文章"
        pager.itemUnit = u"篇"
        return pager
