from subject_page import *
from base_action import *
from cn.edustar.data.paging import PagingQuery
from cn.edustar.data import Pager

class article(BaseSubject, SubjectMixiner):
    def __init__(self):
        BaseSubject.__init__(self)
        self.cate_svc = __jitar__.categoryService
        self.backYearList = None
        
        
    def execute(self):
        if self.subject == None:
            self.addActionError(u"无法加载指定的学科。")
            return self.ERROR
        
        self.templateName = "template1"
        if self.subject.templateName != None:
            self.templateName = self.subject.templateName     
        
        self.get_backyear_list()
        self.get_blog_cates()
        self.get_article_list()
        if self.unitId != None and self.unitId != 0:
            request.setAttribute("unitId", self.unitId)
        request.setAttribute("subject", self.subject)
        request.setAttribute("head_nav", "article")
        return "/WEB-INF/subjectpage/" + self.templateName + "/article_page.ftl"
    
    # 文章分类.
    def get_blog_cates(self):
        blog_cates = self.cate_svc.getCategoryTree("default")
        request.setAttribute("blog_cates", blog_cates)
        
    # 文章主查询列表, 带分页. 
    def get_article_list(self):
        year = self.params.getIntParamZeroAsNull("year")            
        if self.backYearList == None:            
            year = None
        if self.backYearList != None:
            IsValidYear = False
            for y in self.backYearList:
                if year == y.backYear:
                    IsValidYear = True
                    break
            if IsValidYear == False:
                year = None
        
        strWhereClause = ""
        subjectId = self.subject.metaSubject.msubjId
        request.setAttribute("subjectId", subjectId)
        
        if subjectId != None:
            strWhereClause = strWhereClause + "SubjectId =" + str(subjectId) + " And "
        
        gradeId = self.subject.metaGrade.gradeId
        request.setAttribute("gradeId", gradeId)
        
        if gradeId != None:
            minId = self.convertRoundMinNumber(gradeId)
            maxId = self.convertRoundMaxNumber(gradeId)
            #print "minId = %s ,maxId = %s" % (minId,maxId)
            strWhereClause = strWhereClause + "GradeId >=" + str(minId) + " And GradeId < " + str(maxId) + " And "
        
        
        if self.unitId != None and self.unitId != 0:
            strWhereClause = strWhereClause + "ApprovedPathInfo LIKE '%/" + str(self.unitId) + "/%' And "
        
        
        strOrderBy = "ArticleId DESC"
        
        type = self.params.getStringParam("type")
        
            
        if type == "rcmd":
            strWhereClause = strWhereClause + " (RcmdPathInfo IS NOT NULL) And "                
        elif type == "hot":
            strOrderBy = "ViewCount DESC"
        elif type == "cmt":
            strOrderBy = "CommentCount DESC"
        else:
            type = "new"
        request.setAttribute("type", type)
        
        k = self.params.getStringParam("k")
        sysCateId = self.params.getIntParamZeroAsNull("categoryId")
        
        if k != None and k != "":
            strWhereClause = strWhereClause + " Title Like '%" + k + "%' And "
        
        if sysCateId != None:
            #只查询分类自己的
            #strWhereClause = strWhereClause + " SysCateId = " + str(sysCateId) + " And "
            #查询包含子孙分类的
            list=self.cate_svc.getCategoryIds(sysCateId)
            cateIds=""
            for c in list:
                if cateIds=="":
                    cateIds=cateIds+str(c)
                else:
                    cateIds=cateIds+","+str(c)
            strWhereClause = strWhereClause + " SysCateId IN (" + cateIds +")"  + " And "         
        
        if strWhereClause[len(strWhereClause)-4:len(strWhereClause)] == "And ":
            strWhereClause = strWhereClause[0:len(strWhereClause)-4]
        
        pagingService = __spring__.getBean("pagingService")
        pagingQuery = PagingQuery()
        pagingQuery.keyName = "ArticleId"
        pagingQuery.fetchFieldsName = "*"
        pagingQuery.orderByFieldName = strOrderBy
        pagingQuery.spName = "findPagingArticle"
        if year == None:
            pagingQuery.tableName = "Jitar_Article"
        else:
            pagingQuery.tableName = "HtmlArticle" + str(year)
        pagingQuery.whereClause = strWhereClause
        
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
        
            
        request.setAttribute("article_list", article_list)
        request.setAttribute("pager", pager)
        request.setAttribute("k", k)
        request.setAttribute("sysCateId", sysCateId)
        request.setAttribute("year", year)
        
    def get_backyear_list(self):
        webSiteManageService = __spring__.getBean("webSiteManageService")
        self.backYearList = webSiteManageService.getBackYearList("article")
        if self.backYearList == None or len(self.backYearList) < 1:return
        request.setAttribute("backYearList", self.backYearList)