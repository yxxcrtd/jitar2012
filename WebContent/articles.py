# coding=utf-8
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from user_query import UserQuery
from cn.edustar.data.paging import PagingQuery
from cn.edustar.data import Pager

class articles (SubjectMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
        self.cate_svc = __jitar__.categoryService
        self.unitService = __spring__.getBean("unitService")
        self.backYearList = None
        self.hasErrorMessage = None
  
    def execute(self):
        rootUnit = self.unitService.getRootUnit()
        if rootUnit == None:
            request.setAttribute("errMessage", u"没有根机构信息，请超级管理员登录到后台管理在“其它”-“组织机构管理”创建一个根机构信息。<a href='manage/admin.py'>进后台管理</a>")
            return "/WEB-INF/ftl/site_err.ftl"
        self.unit = rootUnit
        
        self.get_backyear_list()
        # 文章分类
        self.get_article_cate()
        
        # 学科分类
        self.get_subject_list()
    
        # 学段分类
        self.get_grade_list()
        
        # 查询文章列表
        self.get_article_list()
        
        # 页面导航高亮为 'articles'
        request.setAttribute("head_nav", "articles")
        response.contentType = "text/html; charset=UTF-8"
        if self.hasErrorMessage != None:
            response.getWriter().write(self.hasErrorMessage)
            return
        return "/WEB-INF/ftl/site_articles.ftl"

    # 文章分类
    def get_article_cate(self):
        blog_cates = self.cate_svc.getCategoryTree("default")
        request.setAttribute("blog_cates", blog_cates)

    # 学段
    def get_grade_list(self):
        request.setAttribute("gradeId", self.params.getIntParamZeroAsNull("gradeId"))
        self.putGradeList()        
    
    # 学科
    def get_subject_list(self):
        self.putSubjectList()

    # 查询文章列表
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
        blogUserId = self.params.getIntParamZeroAsNull("userId")
        strWhereClause = "HideState=0 And AuditState=0 And DraftState=0 And DelState=0 And ApprovedPathInfo Like '%/" + str(self.unit.unitId) + "/%'"
        if blogUserId != None:
            strWhereClause += " And UserId=" + str(blogUserId)
        strOrderBy = "ArticleId DESC"
        list_type = u"最新文章"
        type = self.params.getStringParam("type")
        if type == "" or type == None:
            type = "new"
        if type == "hot":
            strOrderBy = "ViewCount DESC"
            list_type = u"热门文章"
        elif type == "best":
            strWhereClause = strWhereClause + " And BestState = 1"
            list_type = u"精华文章"
        elif type == "rcmd":
            strWhereClause = strWhereClause + "  And RcmdPathInfo Like '%/" + str(self.unit.unitId) + "/%'"
            list_type = u"推荐文章"
        elif type == "cmt":
            strOrderBy = "CommentCount DESC"
            list_type = u"评论最多文章"
        elif type == "famous":
            # 为了不增加额外的表，现在先得到全部名师的 id，然后再去查询。
            qry = UserQuery("u.userId")
            qry.userTypeId = 1
            user_count = qry.count()  
            user_list = qry.list(user_count)
            if user_list == None or len(user_list) < 1:
                nodata = True
            else:
                uid = ""
                for u in user_list:
                    uid += str(u) + ","
                uid = uid[0:len(uid) - 1]
                strWhereClause = strWhereClause + " And UserId IN (" + uid + ")"                
            list_type = u"名师文章"
        elif type == "digg":
            strOrderBy = "Digg DESC"
            list_type = u"按顶排序"
        elif type == "trample":
            strOrderBy = "Trample DESC"
            list_type = u"按踩排序"
        elif type == "star":
            strOrderBy = "StarCount/CommentCount DESC"
            strWhereClause = strWhereClause + " And CommentCount > 0"
            list_type = u"按星排序"
        else:
            type = "new"
        
        k = self.params.getStringParam("k")
        if k != None and k != "":
            newKey = k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
            strWhereClause = strWhereClause + " And Title Like '%" + newKey + "%'"
        subjectId = self.params.getIntParamZeroAsNull("subjectId")    
        sysCateId = self.params.getIntParamZeroAsNull("categoryId")
        gradeId = self.params.getIntParamZeroAsNull("gradeId")
        
        if subjectId != None:
            strWhereClause = strWhereClause + " And SubjectId = " + str(subjectId)
        if sysCateId != None:
            #只查询分类自己的
            #strWhereClause = strWhereClause + " And SysCateId = " + str(sysCateId)
            #查询包含子孙分类的
            list=self.cate_svc.getCategoryIds(sysCateId)
            cateIds=""
            for c in list:
                if cateIds=="":
                    cateIds=cateIds+str(c)
                else:
                    cateIds=cateIds+","+str(c)
            strWhereClause = strWhereClause + " And SysCateId IN (" + cateIds +")"            
                
        if gradeId != None:
            strWhereClause = strWhereClause + " And GradeId = " + str(gradeId)
        
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
            
        request.setAttribute("type", type)
        request.setAttribute("list_type", list_type)
        request.setAttribute("article_list", article_list)
        request.setAttribute("categoryId", sysCateId)
        request.setAttribute("subjectId", subjectId)
        request.setAttribute("k", k)
        request.setAttribute("pager", pager)
        request.setAttribute("year", year)
  
    def get_backyear_list(self):
        webSiteManageService = __spring__.getBean("webSiteManageService")
        self.backYearList = webSiteManageService.getBackYearList("article")
        if self.backYearList == None or len(self.backYearList) < 1:return
        request.setAttribute("backYearList", self.backYearList)
