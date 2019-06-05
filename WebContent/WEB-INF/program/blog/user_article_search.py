from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command
from base_blog_page import *
from com.alibaba.fastjson import JSONObject
from base_action import BaseAction
from cn.edustar.data.paging import PagingQuery

userService = __jitar__.userService

class user_article_search(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        self.loginName = request.getAttribute("loginName")
        if self.loginName == None or self.loginName == "":
            return self.sendNotFound(self.uri)
        self.user = userService.getUserByLoginName(self.loginName)
        #print "user = ", self.user
        if self.canVisitUser(self.user) == False:
            return self.ACCESS_ERROR
        k = self.params.safeGetStringParam("k")
        if k == None or k == "":
            self.addActionError(u"请输入关键字。")
            self.addActionLink(u"返回","../")
            return self.ERROR            
        
        request.setAttribute("user", self.user)
        request.setAttribute("loginUser", self.loginUser)
        hql = "SELECT p.skin FROM Page p WHERE p.name = 'index' and p.objId = :userId and p.objType = 1" 
        #pageSkin = Command(hql).setInteger("userId", self.user.userId).scalar()
        list = []
        list = Command(hql).setInteger("userId", self.user.userId).open(1);
        if list == None :
            pageSkin = None
        else :
            pageSkin = list[0]
        #print "pageSkin = ", pageSkin
        if pageSkin == None or pageSkin == "": pageSkin = "skin1"
        
        # 构造页面数据，由于页面不是在数据库存在的，这里的数据是虚拟数据.
        #pages : [{id: ${page.pageId}, title: '${self.user.blogName!?js_string}', layoutId: ${page.layoutId!0} }],
        page = {
                "pageId": 0,
                "layoutId": 2, # 固定是布局2
                "isSystemPage" : "true", 
                "owner" : "user", 
                "title" :"",
                "skin": pageSkin
                }        
        request.setAttribute("page", page)
        self.page = self.getUserProfilePage(self.user)
        if self.page.customSkin != None:
            customSkin = JSONObject.parse(self.page.customSkin)
            request.setAttribute("customSkin", customSkin)
        # 构造widgets .
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1,"title":u"个人档案","module":"profile", "ico":"", "data":""},                   
                   {"id": "2", "pageId":self.page.pageId, "columnIndex":1, "title":u"文章搜索", "module":"blog_search", "ico":"", "data":""} 
                  ]
        request.setAttribute("widgets", widgets)
        request.setAttribute("widget_list", widgets)
        
        
        pagingService = __spring__.getBean("pagingService")
        pagingQuery = PagingQuery()
        pagingQuery.keyName = "ArticleId"
        pagingQuery.fetchFieldsName = "*"
        pagingQuery.orderByFieldName = "ArticleId DESC"
        pagingQuery.spName = "findPagingArticleBase"
        pagingQuery.tableName = "HtmlArticleBase"
        pagingQuery.whereClause = "userId = " + str(self.user.userId) + " And HideState = 0 And AuditState = 0 And DraftState = 0 And DelState = 0"
        if k != None and k != "":
            newKey = k.replace("%","[%]").replace("_","[_]").replace("[","[[]")
            pagingQuery.whereClause = pagingQuery.whereClause + " And Title LIKE '%" + newKey + "%'"
        
        #得到记录总数
        count = pagingService.getRowsCount(pagingQuery)
        if count % 20 == 0:
            pageCount = int(count/20)
        else:
            pageCount = int(count/20) + 1
        request.setAttribute("pageCount", pageCount)
        request.setAttribute("k", k)
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/user/default/user_article_search.ftl"