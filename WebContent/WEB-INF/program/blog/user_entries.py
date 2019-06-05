from base_action import BaseAction
from base_blog_page import *
from article_query import ArticleQuery
from cn.edustar.jitar.util import ParamUtil
from java.util import HashMap
from cn.edustar.jitar.util import FileCache
from cn.edustar.jitar.model import SiteUrlModel
from cn.edustar.data.paging import PagingQuery

class user_entries(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        self.userName = request.getAttribute("UserName")
        if self.userName == None or self.userName == "":
            response.getWriter().write(u"请选择用户，")
            return
        user = __jitar__.userService.getUserByLoginName(self.userName)
        if user == None:
            response.getWriter().write(u"无法加载用户，")
            return
        
        fc = FileCache()
        content = fc.getUserFileCacheContent(self.userName, "user_entries.html", 30)
        if content != "":
            response.getWriter().write(content)
            fc = None
            return
        
        count = self.params.safeGetIntParam("count")
        if count == 0:count = 10
        
        pagingService = __spring__.getBean("pagingService")
        
        pagingQuery = PagingQuery()
        pagingQuery.keyName = "ArticleId"
        pagingQuery.fetchFieldsName = "*"
        pagingQuery.orderByFieldName = "ArticleId DESC"
        pagingQuery.topCount = count
        pagingQuery.spName = "findPagingArticleBase"
        pagingQuery.tableName = "HtmlArticleBase"
        pagingQuery.whereClause = "userId = " + str(user.userId) + " And HideState = 0 And AuditState = 0 And DraftState = 0 And DelState = 0"
        
        article_list = pagingService.getPagingList(pagingQuery)
        if article_list == None or len(article_list) == 0:
            response.getWriter().write(u"没有检索到数据，")
            return
        pagingQuery = None
        templateProcessor = __spring__.getBean("templateProcessor")
        map = HashMap()
        map.put("article_list", article_list)
        map.put("UserSiteUrl", self.getUserSiteUrl())
        content = templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_entries.ftl", "utf-8")
        
        fc.writeUserFileCacheContent(self.userName, "user_entries.html", content)        
        response.getWriter().write(content)
        fc = None
        
    def getUserSiteUrl(self):
        siteUrl = SiteUrlModel.getSiteUrl()
        userSiteUrl = request.getSession().getServletContext().getInitParameter("userUrlPattern");
        if userSiteUrl == None or userSiteUrl == "":
            userSiteUrl = siteUrl + "u/" + self.userName + "/"
        else:
            userSiteUrl = userSiteUrl.replace("{loginName}", self.userName)            
        return userSiteUrl
