#encoding=utf-8
from cn.edustar.jitar.util import ParamUtil, CommonUtil
from cn.edustar.jitar.pojos import Category
from base_blog_page import *
from base_action import BaseAction
from cn.edustar.data.paging import PagingQuery
from cn.edustar.jitar.model import SiteUrlModel

# 本模块全局变量.
user_svc = __jitar__.userService
cate_svc = __jitar__.categoryService

# 显示一个用户的指定文章分类页面.
class show_user_article_category(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner, CategoryMixiner):
    def execute(self):
        self.categoryId = None
        self.userName = request.getAttribute("loginName")
        params = ParamUtil(request)
        title = params.safeGetStringParam("title")
        if title != "":request.setAttribute("title", title)
        # 解析 uri.
        if self.parseUri() == False:
            return self.sendNotFound(self.uri)
        
        # 得到要工作室主人, 并验证用户状态.
        
        self.user = user_svc.getUserByLoginName(self.userName)
        request.setAttribute("user", self.user)
        #print "self.user = ", self.user
        if self.canVisitUser(self.user) == False:
            return self.ACCESS_ERROR

        # 得到要访问的分类, 要验证分类的确属于该用户.
        self.category = self.getCategory()
        #print "self.category = ", self.category
        if self.category == None: 
            return self.sendNotFound()
        request.setAttribute("category", self.category)

        # 得到文章分类显示页面.
        page = self.getUserArticleCategoryPage(self.user)
        #print "page = ", page
        if page == None:
            return self.sendNotFound()
        request.setAttribute("page", page)

        # 得到页面功能块.
        #widget_list = self.getPageWidgets(page)
        #print "widget_list = ", widget_list
        widgets = [
                   {"id": "1", "pageId":page.pageId, "columnIndex":1, "title":"", "module":"profile", "ico":"", "data":""},
                   {"id": "2", "pageId":page.pageId, "columnIndex":1, "title":u"文章分类", "module":"user_cate", "ico":"", "data":""},
                   {"id": "3", "pageId":page.pageId, "columnIndex":1, "title":u"文章搜索", "module":"blog_search", "ico":"", "data":""}                  
                  ]
        
        request.setAttribute("widget_list", widgets)
        request.setAttribute("widgets", widgets)
        request.setAttribute("UserSiteUrl", self.getUserSiteUrl())        
        request.setAttribute("cateId", self.categoryId)
        
        
        pagingService = __spring__.getBean("pagingService")
        pagingQuery = PagingQuery()
        pagingQuery.keyName = "ArticleId"
        pagingQuery.fetchFieldsName = "*"
        pagingQuery.orderByFieldName = "ArticleId DESC"
        pagingQuery.spName = "findPagingArticleBase"
        pagingQuery.tableName = "HtmlArticleBase"
        pagingQuery.whereClause = "userId = " + str(self.user.userId) + " And HideState = 0 And AuditState = 0 And DraftState = 0 And DelState = 0"
        if self.categoryId != None and self.categoryId >0:
            pagingQuery.whereClause = pagingQuery.whereClause + " And UserCateId=" + str(self.categoryId)
        
        #得到记录总数
        count = pagingService.getRowsCount(pagingQuery)
        if count % 20 == 0:
            pageCount = int(count/20)
        else:
            pageCount = int(count/20) + 1
        request.setAttribute("pageCount", pageCount)
        return "/WEB-INF/user/default/show_user_article_category.ftl"
        
        
    # 解析 uri, 从中获取要访问的 loginName, categoryId.
    def parseUri(self):
        self.uri = self.getRequestURI()     

        if self.uri == None or self.uri == "":
            return False
        
        # 例子: /Groups/liujunxing/category/0.html -> 
        #     ['', 'Groups', 'liujunxing', 'category', '0.html']
        # 其中最后一个是分类标识+'.html', 倒数第3个是用户登录名.
        arr = self.uri.split('/')
        arr_len = len(arr)
        if arr_len < 3:
            return False
        
        # 得到分类标识部分.
        category_part = self.removeHtmlExt(arr[arr_len - 1])        # 153.html -> 153
        if isIntegerStrong(category_part) == False: 
            return False
        self.categoryId = int(category_part)
        #print "self.categoryId = ", self.categoryId
        return True
        
        
    # 得到用户要访问的分类对象.
    def getCategory(self):
        uacItemType = self.toUserArticleCategoryItemType(self.user)
        # 0 表示所有文章.
        if self.categoryId == None:self.categoryId = 0
        if self.categoryId == 0:
            category = Category()
            category.categoryId = 0
            category.itemType = uacItemType
            category.name = u"所有文章"
            return category
        
        # 得到分类.
        category = cate_svc.getCategory(self.categoryId)
        if category == None: return None
        
        # 如果分类不是用户的文章分类则返回 null
        if category.itemType != uacItemType: return None
        
        return category
    
    def getUserSiteUrl(self):
        siteUrl = SiteUrlModel.getSiteUrl()
        userSiteUrl = request.getSession().getServletContext().getInitParameter("userUrlPattern");
        if userSiteUrl == None or userSiteUrl == "":
            userSiteUrl = siteUrl + "u/" + self.userName + "/"
        else:
            userSiteUrl = userSiteUrl.replace("{loginName}", self.userName)            
        return userSiteUrl