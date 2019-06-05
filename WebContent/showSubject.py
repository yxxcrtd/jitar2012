from cn.edustar.jitar.util import ParamUtil
from base_action import BaseAction, ActionResult, SubjectMixiner
from user_query import UserQuery
from site_news_query import SiteNewsQuery
from placard_query import PlacardQuery
from article_query import ArticleQuery
from resource_query import ResourceQuery
from group_query import GroupQuery 
from cn.edustar.jitar.data import Command

# 数据获取脚本.
class showSubject(BaseAction, SubjectMixiner):
  def __init__(self):
    self.params = ParamUtil(request)
    self.subj_svc = __jitar__.subjectService 
    self.statService = __jitar__.statService
    
  def execute(self):
        
    # 把当前学科放到环境中.从'base_action.py'中来
    self.putSubject()
    
    if self.subject == None:
      self.addActionError("您所访问的学科不存在！")
      return ActionResult.ACCESS_ERROR
    
    # 学科名师,带头人工作室, 学科教研员.
    self.get_famous_user_list()
    self.get_expert_user_list()
    self.get_subject_comissioner()    
    
    # 学科统计 - subject 对象里面包括了统计.
    self.get_subject_stat()
        
    #　学科动态.
    self.get_news_list()
    
    # 学科公告.
    self.get_placard_list()
    
    # 学科文章部分.
    # 最新,热门,推荐文章.
    self.get_new_article_list()
    self.get_hot_article_list()
    self.get_rcmd_article_list()
    
    # 学科工作室部分:
    # 最新,热门,推荐工作室.
    self.get_new_blog_list()
    self.get_hot_blog_list()
    self.get_rcmd_blog_list()
    
    
    # 最新,最热,推荐资源列表.
    self.get_new_resource_list()
    self.get_hot_resource_list()
    self.get_rcmd_resource_list()
    
    # 协作组部分:
    # 最新,热门,推荐协作组.
    self.get_new_group_list()
    self.get_hot_group_list()
    self.get_rcmd_group_list()    
     
    # 高亮显示项目.
    request.setAttribute("head_nav", "index")
    
    return "/WEB-INF/ftl/show_subject.ftl"


  # 得到当前元学科标识, 必须要有 self.subject 对象.
  def get_metaSubjectId(self):
    return self.subject.metaSubject.msubjId
  
    
  # 得到当前学科标识, 必须要有 self.subject 对象. 
  def get_subjectId(self):
    return self.subject.subjectId

  def get_current_subjectId(self):
    gradeId = self.params.getIntParam("subjectId")
    request.setAttribute("gradeId",self.gradeId)
    return gradeId
  def get_current_gradeId(self): 
    gradeId = self.params.getIntParam("gradeId")
    request.setAttribute("gradeId",self.gradeId)
    return gradeId

  # 学科名师工作室.
  def get_famous_user_list(self):
    qry = UserQuery("""  u.loginName, u.userIcon, u.loginName, u.nickName,u.trueName, subj.subjectId """)
    qry.userTypeId = 1
    qry.userStatus = 0
    qry.metaSubjectId = self.get_current_subjectId()
    qry.metaGradeId = self.get_current_gradeId()
    channel_user_list = qry.query_map(3)
    request.setAttribute("channel_user_list", channel_user_list)
    
  # 学科带头人工作室.
  def get_expert_user_list(self):
    qry = UserQuery(""" u.loginName, u.nickName,u.trueName """)
    qry.userTypeId = 3
    qry.userStatus = 0
    qry.metaSubjectId = self.get_current_subjectId()
    qry.metaGradeId = self.get_current_gradeId()
    expert_user_list = qry.query_map(10)
    request.setAttribute("expert_user_list", expert_user_list)
    
  # 学科动态.
  def get_news_list(self):
    qry = SiteNewsQuery(""" snews.createDate,  snews.newsId, snews.title """)
    qry.subjectId = self.get_subjectId() 
    qry.gradeId = self.get_current_gradeId()
    news_list = qry.query_map(7)
    request.setAttribute("news_list", news_list) 
    
    
  # 学科公告.
  def get_placard_list(self):
    qry =  PlacardQuery(""" pld.id, pld.title, pld.content """)
    qry.objType = 14  
    qry.objId = self.get_subjectId()
    placard_list = qry.query_map()
    request.setAttribute("placard_list", placard_list)
    
    
  # 学科文章部分.
  # 该学科的最新文章列表.
  def get_new_article_list(self):
    qry = ArticleQuery("""  a.createDate, a.articleId, a.title, u.loginName, a.typeState, u.nickName, u.trueName, u.userId """)
    qry.subjectId = self.get_current_subjectId()
    qry.gradeId = self.get_current_gradeId()
    new_article_list = qry.query_map(12) 
    request.setAttribute("new_article_list", new_article_list)
 

  # 该学科的热门文章列表.
  def get_hot_article_list(self):
    qry = ArticleQuery(""" a.createDate, a.articleId, a.title, a.typeState, u.loginName, u.nickName, u.trueName, u.userId """)
    qry.orderType = 2
    qry.subjectId = self.get_current_subjectId()
    qry.gradeId = self.get_current_gradeId()
    hot_article_list = qry.query_map(12)
    request.setAttribute("hot_article_list", hot_article_list)
   
  # 该学科的推荐文章列表.
  def get_rcmd_article_list(self): 
    qry = ArticleQuery(""" a.createDate, a.articleId, a.title, a.typeState, u.loginName, u.nickName,u.trueName, u.userId """)
    qry.rcmdState = True
    qry.subjectId = self.get_current_subjectId()
    qry.gradeId = self.get_current_gradeId()
    rcmd_article_list = qry.query_map(12)
    request.setAttribute("rcmd_article_list", rcmd_article_list)

  # 学科工作室部分:
  # 最新工作室.
  def get_new_blog_list(self):
    qry = UserQuery("""  u.loginName, u.userIcon, u.blogName, u.trueName,u.blogIntroduce, u.createDate """)
    qry.userStatus = 0
    #qry.setSubjectCondition(self.subject) 导航有改动
    qry.metaSubjectId = self.get_current_subjectId()
    qry.metaGradeId = self.get_current_gradeId()
    new_blog_list = qry.query_map(3)
    request.setAttribute("new_blog_list" , new_blog_list)
    
  # 热门工作室.
  def get_hot_blog_list(self):
    qry = UserQuery("""  u.loginName, u.userIcon, u.blogName, u.trueName,u.createDate, u.blogIntroduce """)
    qry.userStatus = 0
    qry.orderType = UserQuery.ORDER_TYPE_VISITCOUNT_DESC
    qry.metaSubjectId = self.get_current_subjectId()
    qry.metaGradeId = self.get_current_gradeId()
    hot_blog_list = qry.query_map(3)
    request.setAttribute("hot_blog_list", hot_blog_list)

  # 推荐工作室.
  def get_rcmd_blog_list(self):
    qry = UserQuery("""  u.loginName, u.userIcon, u.blogName,u.trueName, u.createDate, u.blogIntroduce  """)
    qry.userTypeId = 2
    qry.userStatus = 0
    qry.metaSubjectId = self.get_current_subjectId()
    qry.metaGradeId = self.get_current_gradeId()
    rcmd_blog_list = qry.query_map(3)
    request.setAttribute("rcmd_blog_list", rcmd_blog_list)

  # 学科资源部分:
  # 该学科的最新资源列表.
  def get_new_resource_list(self):
    qry = ResourceQuery("""   r.createDate, r.resourceId, r.title, r.href, u.loginName, u.nickName, u.trueName,u.userId """)
    qry.subjectId = self.get_current_subjectId()
    qry.gradeId = self.get_current_gradeId()
    new_resource_list = qry.query_map(11)
    request.setAttribute("new_resource_list", new_resource_list)
    
  # 该学科的热门资源. 
  def get_hot_resource_list(self):
    qry = ResourceQuery(""" r.createDate, r.resourceId, r.href, r.title, u.loginName, u.nickName, u.trueName,u.userId """)
    qry.subjectId = self.get_current_subjectId()
    qry.gradeId = self.get_current_gradeId()
    qry.orderType = 2
    hot_resource_list = qry.query_map(11)
    request.setAttribute("hot_resource_list", hot_resource_list)
    
  # 该学科的推荐资源.
  def get_rcmd_resource_list(self):
    qry = ResourceQuery(""" r.createDate, r.resourceId, r.href, r.title, u.loginName, u.nickName, u.trueName,u.userId """)
    qry.subjectId = self.get_current_subjectId()
    qry.gradeId = self.get_current_gradeId()
    qry.rcmdState = True
    rcmd_resource_list = qry.query_map(11)
    request.setAttribute("rcmd_resource_list", rcmd_resource_list)
 
  # 协作组部分:
  # 最新协作组.
  def get_new_group_list(self):
    qry = GroupQuery("""  g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.groupIntroduce """)
    qry.subjectId = self.get_current_subjectId()
    qry.gradeId = self.get_current_gradeId()
    new_group_list = qry.query_map(3)
    request.setAttribute("new_group_list", new_group_list)
    
  # 热门协作组.
  def get_hot_group_list(self):
    qry = GroupQuery("""  g.groupIcon, g.createDate, g.groupId, g.groupTitle, g.groupIntroduce """)
    qry.subjectId = self.get_current_subjectId()
    qry.gradeId = self.get_current_gradeId()
    qry.orderType = 8
    hot_group_list = qry.query_map(3)
    request.setAttribute("hot_group_list", hot_group_list)
    
    
  # 推荐协作组.
  def get_rcmd_group_list(self):
    qry = GroupQuery("""  g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.groupIntroduce """)
    qry.subjectId = self.get_current_subjectId()
    qry.gradeId = self.get_current_gradeId()
    qry.isRecommend = True
    rcmd_group_list = qry.query_map(3)
    request.setAttribute("rcmd_group_list", rcmd_group_list)

  # 学科统计
  def get_subject_stat(self):
    subjectId = self.params.getIntParamZeroAsNull("subjectId")
    gradeId = self.params.getIntParam("gradeId")
    beginGradeId = self.calcGradeStartId(gradeId)
    endGradeId = self.calcGradeEndId(gradeId)
    self.statService.subjectStat(subjectId, beginGradeId, endGradeId)

   
  # 得到当前要显示的学科对象.
  # 返回 True 表示找到了学科, 返回 False 表示要访问的学科不存在.
  def get_current_subject(self):
    subjectId = self.params.getIntParamZeroAsNull("subjectId")
    if subjectId == None:
      return False
    self.subject = self.subj_svc.getSubjectById(subjectId)
    #print "self.subject = ", self.subject
    if self.subject == None:
      return False
    
    return True
  
  # 得到学科教研员列表.
  def get_subject_comissioner(self):
    qry = UserQuery(""" u.loginName, u.nickName, u.userIcon, u.blogName,u.trueName, u.createDate, 
                        u.myArticleCount, u.otherArticleCount, u.resourceCount, u.blogIntroduce """)
    qry.metaSubjectId = self.get_current_subjectId()
    qry.bmd = 1
    qry.userStatus = 0
    qry.metaGradeId = self.get_current_gradeId()
    qry.isComissioner = True
    comissioner_list = qry.query_map(6)   # 按照界面 mengv1/subject/jiaoyanyuan.ftl 中获取 13 个.
    #print "comissioner_list = ", comissioner_list
    
    #hql = """select new Map(loginName, nickName, userIcon, blogName,trueName, createDate,articleCount, resourceCount, blogIntroduce)
    #         from User where subjectId = """+ str(self.get_current_subjectId()) +""" and gradeId = """ + str(self.get_current_gradeId()) + """
    #          and isComissioner=1 """
    #comissioner_list = Command(hql).open(6)
    #print "comissioner_list = ", comissioner_list
    request.setAttribute("comissioner_list", comissioner_list)