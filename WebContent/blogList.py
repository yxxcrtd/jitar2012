from base_action import *
from site_config import SiteConfig
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil, CommonUtil
from user_query import UserQuery

class blogList(SubjectMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):        
        k = self.params.getStringParam("k")
        if k != "" and k != None:
            k = k.lower()
            if len(k) > 25 or k.find("'") > -1 or k.find("script")>-1 or k.find(">")>-1 or k.find("<")>-1  or k.find("\"")>-1  or k.find("&gt;")>-1: 
                k = ""
                response.getWriter().write(u"请输入合法的文字，并且长度不大于25.")
                return
        
        site_config = SiteConfig()
        site_config.get_config()
        self.putSubject()
        
        self.type = self.params.getStringParam("type")
        if ParamUtil.isInteger(self.type):
            self.get_typeduser_list(int(self.type))
        elif self.type == "score":
            self.get_score_list()
        elif self.type == "hot":
            self.get_hot_list()       
        else:
            self.get_new_blog_list()            
        
        return  "/WEB-INF/ftl/site_blog_list.ftl"
    
    #按最新排序
    def get_new_blog_list(self):
        qry = UserQuery(""" u.loginName, u.articleCount, u.userIcon, u.blogName, u.trueName, u.blogIntroduce,u.createDate, u.visitCount, 
                        u.myArticleCount, u.otherArticleCount, u.resourceCount, u.commentCount,u.userScore, u.userTags """)
        qry.orderType = 0
        qry.userStatus = 0
        pager = self.params.createPager()
        pager.itemName = u"用户"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        user_list = qry.query_map(pager)
        request.setAttribute("pager", pager)        
        request.setAttribute("user_list" , user_list)
        if self.type == "search":
            request.setAttribute("list_type",u"工作室搜索")
        else:
            request.setAttribute("list_type",u"最新工作室")
    
    def get_typeduser_list(self, typdId):
        qry = UserQuery(""" u.loginName, u.userIcon, u.blogName, u.trueName, u.blogIntroduce, u.createDate, u.visitCount, 
                        u.articleCount, u.resourceCount, u.commentCount,u.userScore, u.userTags """)
        qry.orderType = 0
        qry.userStatus = 0
        qry.userTypeId = typdId   
        pager = self.params.createPager()
        pager.itemName = u"用户"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        user_list = qry.query_map(pager)
        request.setAttribute("pager", pager)        
        request.setAttribute("user_list" , user_list)
        user_typename = ""
        userService = __jitar__.userService
        type_list = userService.getAllUserType()
        if type_list != None and len(type_list) > 0:
            for t in type_list:
                if t.typeId == typdId:
                    user_typename = t.typeName
                    break
        request.setAttribute("list_type", user_typename)
    
    #热门工作室
    def get_hot_list(self):
        qry = UserQuery(""" u.loginName, u.userIcon, u.blogName, u.trueName,u.blogIntroduce,u.createDate, u.visitCount, 
                        u.articleCount, u.resourceCount, u.commentCount,u.userScore, u.userTags """)
        qry.orderType = 1
        qry.userStatus = 0
        pager = self.params.createPager()
        pager.itemName = u"用户"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        user_list = qry.query_map(pager)
        request.setAttribute("pager", pager)        
        request.setAttribute("user_list" , user_list)
        request.setAttribute("list_type",u"热门工作室")
        
    #积分工作室
    def get_score_list(self):
        qry = UserQuery(""" u.loginName, u.userIcon, u.blogName, u.trueName,u.blogIntroduce,u.createDate, u.visitCount, 
                        u.articleCount, u.resourceCount, u.commentCount,u.userScore, u.userTags """)
        qry.orderType = 6
        qry.userStatus = 0
        pager = self.params.createPager()
        pager.itemName = u"用户"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        user_list = qry.query_map(pager)
        request.setAttribute("pager", pager)        
        request.setAttribute("user_list" , user_list)
        request.setAttribute("list_type",u"工作室积分排行")