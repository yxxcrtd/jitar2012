from cn.edustar.jitar.util import ParamUtil
from cn.edustar.data import Pager
from java.util import HashMap

class user_article_search_list:
    def execute(self):
        params = ParamUtil(request)
        self.k = params.safeGetStringParam("k")
        self.userName = request.getAttribute("loginName")
        self.user = __jitar__.userService.getUserByLoginName(self.userName)
        if self.user == None or self.k == None or self.k == "":
            return "/WEB-INF/user/default/user_category_article_list.ftl"
        
        # 生成静态html
        pagingService = __spring__.getBean("pagingService")
        pagingQuery = PagingQuery()
        pagingQuery.keyName = "ArticleId"
        pagingQuery.fetchFieldsName = "*"
        pagingQuery.orderByFieldName = "ArticleId DESC"
        pagingQuery.spName = "findPagingArticleBase"
        pagingQuery.tableName = "HtmlArticleBase"
        pagingQuery.whereClause = "userId = " + str(self.user.userId) + " And HideState = 0 And AuditState = 0 And DraftState = 0 And DelState = 0"
        if self.k != None and self.k != "":
            pagingQuery.whereClause = pagingQuery.whereClause + " And Title LIKE '%" + self.k + "%'"
            
        pager = Pager()
        pager.setCurrentPage(params.safeGetIntParam("page", 1));
        pager.setPageSize(20)
        article_list = pagingService.getPagingList(pagingQuery, pager)
        
        request.setAttribute("article_list", article_list)
        return "/WEB-INF/user/default/user_category_article_list.ftl"
    