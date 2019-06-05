# script
# 注意: 修改的时候一定要记得备份, 最好用代码管理类软件如 cvs, vss 等管理起来. 

from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.ui import ShowSubjectAction
from cn.edustar.jitar.data import *
from user_query import UserQuery
from site_news_query import SiteNewsQuery
from placard_query import PlacardQuery
from article_query import ArticleQuery
from resource_query import ResourceQuery
from group_query import GroupQuery
# 数据获取脚本.
class show_subject(ShowSubjectAction):
  def __init__(self):
    self.params = ParamUtil(request)
    
  def execute(self):
    #print "show_subject 脚本执行"
    self.subj_svc = __jitar__.subjectService
    
    # 学科名师,带头人工作室.
    self.get_channel_user_list()
    self.get_expert_user_list()
    
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
    
    # 得到学科.
    if self.get_current_subject() == False:
      self.addActionError("您所访问的学科不存在.")
      return "error"
    
    return "success"

  def get_subjectId(self):
    subjectId = self.params.getIntParamZeroAsNull("subjectId")
    return subjectId

  # 学科名师工作室.
  def get_famous_user_list(self):
    qry = UserQuery("""  u.loginName, u.userIcon, u.loginName, u.nickName, subj.subjectId """)
    qry.userTypeId = 1
    qry.subjectId = self.get_subjectId()
    channel_user_list = qry.query_map(3)
    request.setAttribute("channel_user_list", channel_user_list)
    
    
  # 学科带头人工作室.
  def get_expert_user_list(self):
    qry = UserQuery(""" u.loginName, u.nickName """)
    qry.userTypeId = 3
    qry.subjectId = self.get_subjectId()
    expert_user_list = qry.query_map()
    request.setAttribute("expert_user_list", expert_user_list)

    # 学科教研员 - 未实现.
    self.get_subject_comissioner()
    
    # 学科统计 - subject 对象里面包括了统计.
    
  # 学科动态.
  def get_news_list(self):
    qry = SiteNewsQuery(""" snews.createDate,  snews.newsId, snews.title """)
    qry.subjectId = self.get_subjectId()
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
    qry = ArticleQuery("""  a.createDate, a.articleId, a.title, u.loginName, u.nickName, u.userId """)
    qry.subjectId = self.get_subjectId()
    new_article_list = qry.query_map(12)
    request.setAttribute("new_article_list", new_article_list)
 

  # 该学科的热门文章列表.
  def get_hot_article_list(self):
    qry = ArticleQuery(""" a.createDate, a.articleId, a.title, u.loginName, u.nickName, u.userId """)
    qry.subjectId = self.get_subjectId()
    qry.orderType = 2
    hot_article_list = qry.query_map(12)
    request.setAttribute("hot_article_list", hot_article_list)
   
  # 该学科的推荐文章列表.
  def get_rcmd_article_list(self):
    qry = ArticleQuery(""" a.createDate, a.articleId, a.title,  u.loginName, u.nickName, u.userId """)
    qry.rcmdState = True
    qry.subjectId = self.get_subjectId()
    rcmd_article_list = qry.query_map(12)
    request.setAttribute("rcmd_article_list", rcmd_article_list)

  # 学科工作室部分:
  # 最新工作室.
  def get_new_blog_list(self):
    qry = UserQuery("""  u.loginName, u.userIcon, u.blogName, u.blogIntroduce, u.createDate """)
    qry.subjectId = self.get_subjectId()
    new_blog_list = qry.query_map(3)
    request.setAttribute("new_blog_list" , new_blog_list)
    
  # 热门工作室.
  def get_hot_blog_list(self):
    qry = UserQuery("""  u.loginName, u.userIcon, u.blogName, u.createDate, u.blogIntroduce """)
    qry.orderType = 1
    qry.subjectId = self.get_subjectId()
    hot_blog_list = qry.query_map(3)
    request.setAttribute("hot_blog_list", hot_blog_list)

  # 推荐工作室.
  def get_rcmd_blog_list(self):
    qry = UserQuery("""  u.loginName, u.userIcon, u.blogName, u.createDate, u.blogIntroduce  """)
    qry.isRecommend = True
    qry.subjectId = self.get_subjectId()
    rcmd_blog_list = qry.query_map(3)
    request.setAttribute("rcmd_blog_list", rcmd_blog_list)

  # 学科资源部分:
  # 该学科的最新资源列表.
  def get_new_resource_list(self):
    qry = ResourceQuery("""   r.createDate, r.resourceId, r.title, r.href, u.loginName, u.nickName, u.userId """)
    qry.subjectId = self.get_subjectId()
    new_resource_list = qry.query_map(11)
    request.setAttribute("new_resource_list", new_resource_list)
    
  # 该学科的热门资源.
  def get_hot_resource_list(self):
    qry = ResourceQuery(""" r.createDate, r.resourceId, r.href, r.title, u.loginName, u.nickName, u.userId """)
    qry.subjectId = self.get_subjectId()
    qry.orderType = 2
    hot_resource_list = qry.query_map(11)
    request.setAttribute("hot_resource_list", hot_resource_list)
    
  # 该学科的推荐资源.
  def get_rcmd_resource_list(self):
    qry = ResourceQuery(""" r.createDate, r.resourceId, r.href, r.title, u.loginName, u.nickName, u.userId """)
    qry.rcmdState = True
    qry.subjectId = self.get_subjectId()
    rcmd_resource_list = qry.query_map(11)
    request.setAttribute("rcmd_resource_list", rcmd_resource_list)

  # 协作组部分:
  # 最新协作组.
  def get_new_group_list(self):
    qry = GroupQuery("""  g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.groupIntroduce """)
    qry.subjectId = self.get_subjectId()
    new_group_list = qry.query_map(3)
    request.setAttribute("new_group_list", new_group_list)
    
  # 热门协作组.
  def get_hot_group_list(self):
    qry = GroupQuery("""  g.groupIcon, g.createDate, g.groupId, g.groupTitle, g.groupIntroduce """)
    qry.subjectId = self.get_subjectId()
    qry.orderType = 8
    hot_group_list = qry.query_map(3)
    request.setAttribute("hot_group_list", hot_group_list)
    
    
  # 推荐协作组.
  def get_rcmd_group_list(self):
    qry = GroupQuery("""  g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.groupIntroduce """)
    qry.subjectId = self.get_subjectId()
    qry.isBestGroup = True
    rcmd_group_list = qry.query_map(3)
    request.setAttribute("rcmd_group_list", rcmd_group_list)

    
    # 高亮显示项目.
    self.setData("head_nav", "index")
    
    return "success"
    
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
  #http://www.freemarker.org/eclipse/update
  # 得到学科教研员列表.
  def get_subject_comissioner(self):
    qry = UserQuery(""" u.loginName, u.nickName, u.userIcon, u.blogName, u.createDate, 
        u.articleCount, u.resourceCount, u.blogIntroduce """)
    qry.subjectId = self.subject.subjectId
    qry.isComissioner = True
    comissioner_list = qry.query_map(6)   # 按照界面 mengv1/subject/jiaoyanyuan.ftl 中获取 13 个.
    #print "comissioner_list = ", comissioner_list
    request.setAttribute("comissioner_list", comissioner_list)
    

