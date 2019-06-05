from cn.edustar.jitar.util import ParamUtil
from user_query import UserQuery
from base_action import SubjectMixiner
from article_query import ArticleQuery
from java.lang import *

# 站点工作室中心执行脚本class blogs (SubjectMixiner):
  def __init__(self):
    self.params = ParamUtil(request)
    self.cate_svc = __jitar__.categoryService
    return  
  
  def execute(self):
    self.get_famous_teacher()
    self.get_expert_list()
    self.get_comissioner_list()

    # 学段分类.
    self.get_grade_list()

    # 最新工作室.
    self.get_new_blog_list()
    
    # 热门工作室.    self.get_hot_blog_list()
    
    # 推荐工作室.    self.get_rcmd_list()
        
    #得到学科
    self.get_subject_list()
    
    # 文章搜索 - 文章分类.
    self.get_article_cate()
    
    # 研修之星.
    self.teacher_star()
    
    # 工作室活跃度排行(访问排行榜).
    self.get_blog_visit_charts()
    
    #积分排行榜
    self.get_blog_score_charts();
    
    # 统计信息.
    self.get_site_stat()
    
    # 页面导航高亮为 'blogs'
    request.setAttribute("head_nav", "blogs")
    
    return "/WEB-INF/ftl/site_blogs.ftl"
     
  # 名师工作室. 
  def get_famous_teacher(self):
    qry = UserQuery("  u.loginName, u.userIcon, u.blogName, u.trueName, u.visitCount, u.articleCount ")
    qry.userTypeId = 1
    qry.userStatus = 0
    qry.orderType = 100
    famous_teachers = qry.query_map(3)
    request.setAttribute("famous_teachers", famous_teachers)    
    
  #学段
  def get_grade_list(self):
      request.setAttribute("gradeId", self.params.getIntParamZeroAsNull("gradeId"))
      self.putGradeList()

  # 学科带头人工作室.
  def get_expert_list(self):
    qry = UserQuery(" u.loginName, u.userIcon, u.blogName, u.trueName, u.visitCount, unit.unitName, u.articleCount ")
    qry.userTypeId = 3
    qry.userStatus = 0
    qry.orderType = 100
    expert_list = qry.query_map(13)   
    request.setAttribute("expert_list", expert_list)
  
  # 教研员工作室.
  def get_comissioner_list(self):
    qry = UserQuery(""" u.loginName, u.trueName, u.userIcon, u.blogName, u.createDate, u.myArticleCount, u.otherArticleCount, u.resourceCount, u.blogIntroduce, u.articleCount """)
    qry.userTypeId = 4
    qry.userStatus = 0
    qry.orderType = 100
    comissioner_list = qry.query_map(6)     
    request.setAttribute("comissioner_list", comissioner_list)
  
  # 最新工作室.
  def get_new_blog_list(self):
    qry = UserQuery(""" u.loginName, u.userIcon, u.blogName, u.trueName, u.blogIntroduce, u.articleCount """)
    qry.orderType = 0
    qry.userStatus = 0
    #qry.metaSubjectId=2
    new_blog_list = qry.query_map(6)
    request.setAttribute("new_blog_list" , new_blog_list)
    
  # 热门工作室.
  def get_hot_blog_list(self):
    qry = UserQuery(" u.loginName, u.userIcon, u.blogName, u.trueName,u.blogIntroduce, u.articleCount ")
    qry.orderType = 1     # visitCount DESC
    qry.userStatus = 0
    hot_blog_list = qry.query_map(6)
    request.setAttribute("hot_blog_list", hot_blog_list)
    
  # 推荐工作室.
  def get_rcmd_list(self):
    qry = UserQuery(" u.loginName, u.userIcon, u.blogName, u.trueName, u.articleCount ")
    qry.userTypeId = 2
    qry.userStatus = 0
    qry.orderType = 100
    rcmd_list = qry.query_map(12)
    request.setAttribute("rcmd_list", rcmd_list)
  
  # 文章搜索 - 文章分类.
  def get_article_cate(self):
    article_categories = self._getCateSvc().getCategoryTree("default")
    request.setAttribute("article_categories", article_categories)
    
  # 研修之星.
  def teacher_star(self):
    qry = UserQuery(" u.userId, u.loginName, u.trueName, u.userIcon, u.blogName, u.blogIntroduce, u.articleCount ")
    qry.userTypeId = 5
    qry.orderType = 100
    teacher_star = qry.query_map(1)    
    request.setAttribute("teacher_star", teacher_star)     
      
  # 学科.
  def get_subject_list(self):
    self.putSubjectList()    
        
  # 工作室活跃度排行(访问排行榜).
  def get_blog_visit_charts(self):
    qry = UserQuery(" u.visitCount, u.loginName, u.trueName,u.blogName, u.articleCount ")
    qry.orderType = 1
    qry.userStatus = 0
    blog_visit_charts = qry.query_map(5)
    request.setAttribute("blog_visit_charts", blog_visit_charts)

  # 工作室积分排行(积分排行榜).
  def get_blog_score_charts(self):
    qry = UserQuery(" u.userScore, u.loginName, u.trueName,u.blogName, u.articleCount ")
    qry.orderType = 6
    qry.userStatus = 0
    blog_score_charts = qry.query_map(5)
    request.setAttribute("blog_score_charts", blog_score_charts)

  # 统计信息.
  def get_site_stat(self):
    site_stat = self._getTimerCountService().getTimerCountById(1)
    request.setAttribute("site_stat", site_stat)
  
  # 得到StatService
  def _getTimerCountService(self):
    return __spring__.getBean("timerCountService")

  # 得到categoryService  
  def _getCateSvc(self):
    return __jitar__.categoryService
