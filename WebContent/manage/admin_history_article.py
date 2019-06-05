from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.data.paging import PagingQuery
from cn.edustar.data import Pager
from base_action import *

class admin_history_article(ActionExecutor, SubjectMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
    
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        
        accessControlService = __spring__.getBean("accessControlService")
        if False == accessControlService.isSystemContentAdmin(self.loginUser):
            self.addActionError(u"您不具有文章管理权限, 或者您的信息填写不完整, 或未经系统管理员审核.")
            return ActionResult.ERROR
        
        year = self.params.safeGetIntParam("backYear")
        if year == 0:
            self.addActionError(u"请选择一个年份。")
            return ActionResult.ERROR
        if request.getMethod() == "POST":
            articleService = __jitar__.articleService
            articleIdList = self.params.safeGetIntValues("articleId")
            cmd = self.params.getStringParam("cmd")
            if cmd == "":cmd = None
            if cmd == "crash":
                for id in articleIdList:
                    articleService.deleteArticleWithRelativeData(id, year)
            elif cmd=="audit":
                for id in articleIdList:
                    article = articleService.getArticle(id)
                    if article != None:
                        articleService.auditArticle(article)
            elif cmd=="unaudit":
                for id in articleIdList:
                    article = articleService.getArticle(id)
                    if article != None:
                        articleService.unauditArticle(article)
        
        su = self.params.getIntParamZeroAsNull("su")
        f = self.params.getStringParam("f", None)
        gradeId = self.params.getIntParamZeroAsNull("gradeId")
        sc = self.params.getIntParamZeroAsNull("sc")
        k = self.params.getStringParam("k")
        approved = self.params.getStringParam("approved", "")
        
        fieldName = None
        if f == "lname":
            fieldName = "LoginName"
        elif f == "uname":
            fieldName = "UserTrueName"
        else:
            fieldName = "Title"
            
        whereClause = ""
        if k != None and fieldName != None:
            k = k.replace("'", "")
            k = k.replace("%", "")
            k = k.replace(";", "")
            whereClause += "And " + fieldName + " LIKE '%" + k + "%'"
        if su != None:
            whereClause += " And subjectId = " + str(su)
        if gradeId != None:
            whereClause += " And gradeId = " + str(gradeId)
        if sc != None:
            whereClause += " And sysCateId = " + str(sc)
        if approved != "" and approved.isdigit():
            whereClause += " And AuditState = " + approved
        if whereClause != "" and len(whereClause) > 4:
            whereClause = whereClause[4:len(whereClause)]
        else:
            whereClause = ""
        
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
        request.setAttribute("approved", approved)        
        
        request.setAttribute("article_list", article_list)
        request.setAttribute("pager", pager)
        request.setAttribute("year", year)
        self.putSubjectList()
        self.putGradeList()
        self.putArticleCategoryTree()
        return "/WEB-INF/ftl/admin/admin_history_article.ftl"
    
    def putArticleCategoryTree(self):
        article_categories = __jitar__.categoryService.getCategoryTree('default')
        request.setAttribute("article_categories", article_categories)
