from unit_page import *
from user_query import UserQuery
from base_action import SubjectMixiner
from article_query import ArticleQuery
from cn.edustar.data.paging import PagingQuery
from cn.edustar.data import Pager

class unit_article(UnitBasePage, SubjectMixiner):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.cate_svc = __jitar__.categoryService
        self.backYearList = None
        
    def execute(self):
        if self.unitService == None:
            self.addActionError(u"无法加载组织机构服务，请检查 applicationContext.xml 配置文件，是否缺少 unitService 节点。")
            return self.ERROR
        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        if self.unit.delState == True:
            self.addActionError(u"您所访问的机构已经被删除！")
            return self.ERROR
        self.get_backyear_list()

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
        
        strWhereClause = "ApprovedPathInfo Like '%/" + str(self.unit.unitId) + "/%'"
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
            strWhereClause =  strWhereClause + "  And RcmdPathInfo Like '%/" + str(self.unit.unitId) + "/%'"
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
                strWhereClause =  strWhereClause + "  And UserId IN (0)"
            else:
                uid = ""
                for u in user_list:
                    uid += str(u) + ","
                uid = uid[0:len(uid)-1]
                strWhereClause =  strWhereClause + "  And UserId IN (" + uid + ")"                
            list_type = u"名师文章"
        elif type == "digg":
            strOrderBy = "Digg DESC"
            list_type = u"按顶排序"
        elif type == "trample":
            strOrderBy = "Trample DESC"
            list_type = u"按踩排序"
        elif type == "star":
            strOrderBy = "(StarCount/CommentCount) DESC"
            list_type = u"按星排序"
            strWhereClause = strWhereClause + " And CommentCount>0"
        else:
            type = 'new'
        
        k = self.params.getStringParam("k")
        if k != "" and k != None:
            if len(k) > 25 or k.find("'")>-1:
                k = ""
            else:
                strWhereClause = strWhereClause + " And Title Like '%" + k + "%'"
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
        if totalCount <= 0:
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
        # 文章分类.
        blog_cates = self.cate_svc.getCategoryTree("default")
        request.setAttribute("blog_cates", blog_cates)
        
        request.setAttribute("head_nav", "unit_article")
        request.setAttribute("unit", self.unit)
        
        self.putGradeList()
        self.putSubjectList()
        
        templateName = "template1"
        if self.unit.templateName != None:
            templateName = self.unit.templateName
        return "/WEB-INF/unitspage/" + templateName + "/unit_article.ftl"
    
    def get_backyear_list(self):
        webSiteManageService = __spring__.getBean("webSiteManageService")
        self.backYearList = webSiteManageService.getBackYearList("article")
        if self.backYearList == None or len(self.backYearList) < 1:return
        request.setAttribute("backYearList", self.backYearList)