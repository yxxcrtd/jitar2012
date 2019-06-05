#encoding=utf-8
from subject_page import *
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.data.paging import PagingQuery
from cn.edustar.data import Pager


class admin_history_article_subject(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.params = ParamUtil(request)
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin == False and self.isContentAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        year = self.params.safeGetIntParam("backYear")
        if year == 0:
            self.addActionError(u"请选择一个年份。")
            return self.ERROR

        if request.getMethod() == "POST":
            articleService = __jitar__.articleService
            articleIdList = self.params.safeGetIntValues("articleId")
            for id in articleIdList:
                articleService.deleteArticleWithRelativeData(id,year)
        
        
        f = self.params.getStringParam("f",None)
        sc = self.params.getIntParamZeroAsNull("sc")
        k = self.params.getStringParam("k")
        
        fieldName = None
        if f == "lname":
            fieldName = "LoginName"
        elif f == "uname":
            fieldName = "UserTrueName"
        else:
            fieldName = "Title"
            
        whereClause = "subjectId = " + str(self.subject.metaSubject.msubjId) + " And gradeId = " + str(self.subject.metaGrade.gradeId)
        if k != None and fieldName != None:
            k = k.replace("'","")
            k = k.replace("%","")
            k = k.replace(";","")
            whereClause += "And " + fieldName + " LIKE '%" + k + "%'"
        if sc != None:
            whereClause += " And sysCateId = " + str(sc)
        
        pagingService = __spring__.getBean("pagingService")
        pagingQuery = PagingQuery()
        pagingQuery.keyName = "ArticleId"
        pagingQuery.fetchFieldsName = "*"
        pagingQuery.orderByFieldName = "ArticleId DESC"
        pagingQuery.spName = "findPagingArticle"
        pagingQuery.tableName = "HtmlArticle" + str(year)
        pagingQuery.whereClause = whereClause
        
        totalCount = self.params.safeGetIntParam("totalCount")
        pager = Pager()
        pager.setCurrentPage(self.params.safeGetIntParam("page", 1))
        pager.setPageSize(20)
        pager.setItemNameAndUnit(u"文章", u"篇")
        pager.setUrlPattern(self.params.generateUrlPattern())
        if totalCount == 0:
            pager.setTotalRows(pagingService.getRowsCount(pagingQuery))
        else:
            pager.setTotalRows(totalCount)
            
        article_list = pagingService.getPagingList(pagingQuery, pager)
        
        request.setAttribute("k", k)
        request.setAttribute("f", f)
        request.setAttribute("sc", sc)
        
        request.setAttribute("article_list", article_list)
        request.setAttribute("pager", pager)
        request.setAttribute("year", year)
        request.setAttribute("subject", self.subject)
        self.putArticleCategoryTree()
        return "/WEB-INF/subjectmanage/admin_history_article_subject.ftl"
    
    def putArticleCategoryTree(self):
        article_categories = __jitar__.categoryService.getCategoryTree('default')
        request.setAttribute("article_categories", article_categories)
        