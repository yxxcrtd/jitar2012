from cn.edustar.jitar.util import ParamUtil
from cn.edustar.data import Pager
from java.util import HashMap

class user_category_article_list:
    def execute(self):
        params = ParamUtil(request)
        self.categoryId = params.getIntParamZeroAsNull("cid")
        self.userName = request.getAttribute("loginName")
        self.user = __jitar__.userService.getUserByLoginName(self.userName)
        if self.user == None:
            return "/WEB-INF/user/default/user_category_article_list.ftl"
        if self.categoryId == None:self.categoryId = 0
        
        # 生成静态html
        pagingService = __spring__.getBean("pagingService")
        pagingQuery = PagingQuery()
        pagingQuery.keyName = "ArticleId"
        pagingQuery.fetchFieldsName = "*"
        pagingQuery.orderByFieldName = "ArticleId DESC"
        pagingQuery.spName = "findPagingArticleBase"
        pagingQuery.tableName = "HtmlArticleBase"
        pagingQuery.whereClause = "userId = " + str(self.user.userId) + " And HideState = 0 And AuditState = 0 And DraftState = 0 And DelState = 0"
        if self.categoryId != None and self.categoryId > 0:
            pagingQuery.whereClause = pagingQuery.whereClause + " And UserCateId=" + str(self.categoryId)
            
        pager = Pager()
        pager.setCurrentPage(params.safeGetIntParam("page", 1));
        pager.setPageSize(20)
        article_list = pagingService.getPagingList(pagingQuery, pager)
        
        request.setAttribute("article_list", article_list)
        return "/WEB-INF/user/default/user_category_article_list.ftl"