from unit_page import *
from user_query import UserQuery
from cn.edustar.jitar.util import CommonUtil

class unit_blog_list(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        
    def execute(self):

        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        if self.unit.delState == True:
            self.addActionError(u"您所访问的机构已经被删除！")
            return self.ERROR
        self.type = self.params.getStringParam("type")
        if ParamUtil.isInteger(self.type):
            self.get_typeduser_list(int(self.type))
        elif self.type == "score":
            self.get_score_list()
        elif self.type == "hot":
            self.get_hot_list()
        elif self.type == "visit":
            self.get_visit_list()
        else:
            self.get_new_blog_list()            
        
        request.setAttribute("head_nav", "unit_user")
        request.setAttribute("unit", self.unit)
        templateName = "template1"
        if self.unit.templateName != None:
            templateName = self.unit.templateName
        return "/WEB-INF/unitspage/" + templateName + "/unit_blog_list.ftl"
    
    def get_typeduser_list(self, typdId):
        qry = UserQuery(""" u.loginName, u.articleCount, u.userIcon, u.userTags, u.blogName, u.trueName, u.blogIntroduce, u.createDate, u.visitCount, 
                        u.articleCount, u.resourceCount, u.commentCount, u.userScore """)
        qry.orderType = 0
        qry.userStatus = 0
        qry.unitId = self.unit.unitId
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
        
       #按最新排序
    def get_new_blog_list(self):
        qry = UserQuery(""" u.loginName, u.articleCount, u.userIcon, u.blogName, u.trueName, u.blogIntroduce,u.createDate, u.visitCount, 
                        u.myArticleCount, u.otherArticleCount, u.resourceCount, u.commentCount,u.userScore """)
        qry.orderType = 0
        qry.userStatus = 0
        qry.unitId = self.unit.unitId
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
    
    #热门工作室
    def get_hot_list(self):
        qry = UserQuery(""" u.loginName, u.articleCount, u.userIcon, u.blogName, u.trueName,u.blogIntroduce,u.createDate, u.visitCount, 
                        u.articleCount, u.resourceCount, u.commentCount,u.userScore """)
        qry.orderType = 1
        qry.userStatus = 0
        qry.unitId = self.unit.unitId
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
        qry = UserQuery(""" u.loginName, u.articleCount, u.userIcon, u.blogName, u.trueName,u.blogIntroduce,u.createDate, u.visitCount, 
                        u.articleCount, u.resourceCount, u.commentCount,u.userScore """)
        qry.orderType = 6
        qry.userStatus = 0
        qry.unitId = self.unit.unitId
        pager = self.params.createPager()
        pager.itemName = u"用户"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        user_list = qry.query_map(pager)
        request.setAttribute("pager", pager)        
        request.setAttribute("user_list" , user_list)
        request.setAttribute("list_type",u"工作室积分排行")
        
    def get_visit_list(self):
        qry = UserQuery(""" u.loginName, u.articleCount, u.userIcon, u.blogName, u.trueName, u.blogIntroduce,u.createDate, u.visitCount, 
                        u.articleCount, u.resourceCount, u.commentCount,u.userScore """)
        qry.orderType = 1
        qry.userStatus = 0
        qry.unitId = self.unit.unitId
        pager = self.params.createPager()
        pager.itemName = u"用户"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        user_list = qry.query_map(pager)
        request.setAttribute("pager", pager)        
        request.setAttribute("user_list" , user_list)
        request.setAttribute("list_type",u"工作室访问排行榜")