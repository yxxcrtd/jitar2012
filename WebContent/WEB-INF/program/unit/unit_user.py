from unit_page import *
from user_query import UserQuery
from base_action import SubjectMixiner
from article_query import ArticleQuery
from java.lang import *

class unit_user(UnitBasePage):
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
        self.get_famous_teacher()
        self.get_expert_list()
        self.get_comissioner_list()    
        # 推荐工作室.
        self.get_rcmd_list()        
        # 研修之星.
        self.teacher_star()
        
        # 最新工作室.
        self.get_new_blog_list()
        
        # 热门工作室.
        self.get_hot_blog_list()
        
        # 工作室活跃度排行(访问排行榜).
        self.get_blog_visit_charts()
        
        #积分排行榜
        self.get_blog_score_charts();
    
        request.setAttribute("head_nav", "unit_user")
        request.setAttribute("unit", self.unit)      
    
        templateName = "template1"
        if self.unit.templateName != None:
            templateName = self.unit.templateName
        return "/WEB-INF/unitspage/" + templateName + "/unit_user.ftl"
    
    # 名师工作室. 
    def get_famous_teacher(self):
        qry = UserQuery(" u.loginName, u.userIcon, u.blogName, u.trueName, u.visitCount, u.articleCount, u.userType ")
        qry.userTypeId = 1
        qry.unitId = self.unit.unitId
        qry.orderType = 100
        famous_teachers = qry.query_map(3)       
        request.setAttribute("famous_teachers", famous_teachers)

    # 学科带头人工作室.
    def get_expert_list(self):
        qry = UserQuery("  u.loginName, u.userIcon, u.blogName, u.trueName, u.visitCount, unit.unitName, u.articleCount, u.userType ")
        qry.userTypeId = 3
        qry.unitId = self.unit.unitId
        qry.orderType = 100
        expert_list = qry.query_map(3)        
        request.setAttribute("expert_list", expert_list)      
  
    # 教研员工作室.
    def get_comissioner_list(self):
        qry = UserQuery(""" u.loginName, u.trueName, u.userIcon, u.blogName, u.createDate, 
            u.myArticleCount, u.otherArticleCount, u.resourceCount, u.blogIntroduce, u.articleCount, u.userType """)
        qry.userTypeId = 4
        qry.orderType = 100
        qry.unitId = self.unit.unitId
        comissioner_list = qry.query_map(6)       
        request.setAttribute("comissioner_list", comissioner_list)
  
    # 最新工作室.
    def get_new_blog_list(self):
        qry = UserQuery(""" u.loginName, u.userIcon, u.blogName, u.trueName, u.blogIntroduce, u.articleCount, u.userType """)
        qry.orderType = 0
        qry.userStatus = 0
        qry.unitId = self.unit.unitId
        new_blog_list = qry.query_map(5)
        request.setAttribute("new_blog_list" , new_blog_list)        
    
    # 热门工作室.
    def get_hot_blog_list(self):
        qry = UserQuery(""" u.loginName, u.userIcon, u.blogName, u.trueName,u.blogIntroduce, u.articleCount, u.userType """)
        qry.orderType = 1     # visitCount DESC
        qry.unitId = self.unit.unitId
        hot_blog_list = qry.query_map(5)
        request.setAttribute("hot_blog_list", hot_blog_list)       
    
    # 推荐工作室.
    def get_rcmd_list(self):
        qry = UserQuery("""  u.loginName, u.userIcon, u.blogName, u.trueName, u.articleCount, u.userType """)
        qry.userTypeId = 2
        qry.orderType = 100
        qry.unitId = self.unit.unitId
        rcmd_list = qry.query_map(5)
        request.setAttribute("rcmd_list", rcmd_list)  
  
    # 研修之星.
    def teacher_star(self):
        qry = UserQuery(""" u.userId, u.loginName, u.trueName, u.userIcon, u.blogName, u.blogIntroduce, u.articleCount, u.userType """)
        qry.userTypeId = 5
        qry.unitId = self.unit.unitId
        teacher_star = qry.query_map(2)    
        request.setAttribute("teacher_star", teacher_star) 
        
    # 工作室活跃度排行(访问排行榜).
    def get_blog_visit_charts(self):
        qry = UserQuery(""" u.visitCount, u.loginName, u.trueName,u.blogName, u.articleCount """)
        qry.orderType = 1
        qry.unitId = self.unit.unitId
        blog_visit_charts = qry.query_map(10)
        request.setAttribute("blog_visit_charts", blog_visit_charts)

    # 工作室积分排行(积分排行榜).
    def get_blog_score_charts(self):
        qry = UserQuery(""" u.userScore, u.loginName, u.trueName,u.blogName, u.articleCount """)
        qry.orderType = 6
        qry.unitId = self.unit.unitId
        blog_score_charts = qry.query_map(10)
        request.setAttribute("blog_score_charts", blog_score_charts)