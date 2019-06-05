#encoding=utf-8
from unit_page import *
from util import *
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.data.paging import PagingQuery
from cn.edustar.data import Pager

class admin_history_article_unit(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        
        if self.isContentAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
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
        
        su = self.params.getIntParamZeroAsNull("su")
        f = self.params.getStringParam("f",None)
        gradeId = self.params.getIntParamZeroAsNull("gradeId")
        sc = self.params.getIntParamZeroAsNull("sc")
        k = self.params.getStringParam("k")
        
        fieldName = None
        if f == "lname":
            fieldName = "LoginName"
        elif f == "uname":
            fieldName = "UserTrueName"
        else:
            fieldName = "Title"
            
        whereClause = "UnitId=" + str(self.unit.unitId)
        if k != None and fieldName != None:
            k = k.replace("'","")
            k = k.replace("%","")
            k = k.replace(";","")
            whereClause += "And " + fieldName + " LIKE '%" + k + "%'"
        if su != None:
            whereClause += " And subjectId = " + str(su)
        if gradeId != None:
            whereClause += " And gradeId = " + str(gradeId)
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
        request.setAttribute("gradeId", gradeId)
        request.setAttribute("su", su)
        request.setAttribute("sc", sc)
        
        request.setAttribute("article_list", article_list)
        request.setAttribute("pager", pager)
        request.setAttribute("year", year)
        request.setAttribute("unit", self.unit)
        
        self.putSubjectList()
        self.putGradeList()
        self.putArticleCategoryTree()
        return "/WEB-INF/unitsmanage/admin_history_article_unit.ftl"
    
    def putArticleCategoryTree(self):
        article_categories = __jitar__.categoryService.getCategoryTree('default')
        request.setAttribute("article_categories", article_categories)