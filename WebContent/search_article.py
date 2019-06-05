# script for articles.py
from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from article_query import ArticleQuery
from site_config import SiteConfig

# 显示文章
class search_article(SubjectMixiner):
  def __init__(self):
    self.params = ParamUtil(request)
    self.cate_svc = __jitar__.categoryService
    self.unitService = __spring__.getBean("unitService")
    return
  
  def execute(self):
    rootUnit = self.unitService.getRootUnit()
    if rootUnit == None:
      request.setAttribute("errMessage", u"没有根机构信息，请超级管理员登录到后台管理在“其它”-“组织机构管理”创建一个根机构信息。<a href='manage/admin.py'>进后台管理</a>")
      return "/WEB-INF/ftl/site_err.ftl"
    self.unit = rootUnit
    site_config = SiteConfig()
    site_config.get_config()

    # 文章分类
    self.get_article_cate()
    
    # 学科分类
    self.get_subject_list()

    # 学段分类
    self.get_grade_list()
    
    # 查询文章列表
    self.get_article_list()
    
    # 区县列表
    #self.get_dist_list()
    
    # 页面导航高亮为 'articles'
    request.setAttribute("head_nav", "articles")
    response.contentType = "text/html; charset=UTF-8"
    return "/WEB-INF/ftl/search_article.ftl"
  
  
  # 文章分类
  def get_article_cate(self):
    blog_cates = self.cate_svc.getCategoryTree("default")
    request.setAttribute("blog_cates", blog_cates)
  
  # 学段
  def get_grade_list(self):
      request.setAttribute("gradeId", self.params.getIntParamZeroAsNull("gradeId"))
      self.putGradeList()        
    
  # 学科
  def get_subject_list(self):
    self.putSubjectList()
    
  # 查询文章列表
  def get_article_list(self):
    qry = __PrivateArticleQuery(""" a.articleId, a.title, a.createDate, a.viewCount, a.commentCount, a.userId,
                    a.digg, a.trample, a.starCount, a.recommendState, a.typeState, u.loginName, u.nickName """)
    qry.unit = self.unit
    qry.custormAndWhereClause = " a.unitPathInfo Like '%/" + str(self.unit.unitId) + "/%' and a.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' "
    
    list_type = u"最新文章"
    type = self.params.getStringParam("type")
    if type == "" or type == None:
      type = "new"
    if type == "hot":
      qry.orderType = 2 
      list_type = u"热门文章"
    elif type == "best":
      qry.bestState = True
      list_type = u"精华文章"      
    elif type == "rcmd":
      qry.rcmdState = True
      qry.custormAndWhereClause = "a.approvedPathInfo Like '%/" + str(self.unit.unitId) + "/%' And a.rcmdPathInfo Like '%/" + str(self.unit.unitId) + "/%' "
      list_type = u"推荐文章"
    elif type == "cmt":
      qry.orderType = 3
      list_type = u"评论最多文章"
    elif type == "famous":
      qry.userIsFamous = True
      list_type = u"名师文章"
    elif type == "digg":
      qry.orderType = 4
      list_type = u"按顶排序"
    elif type == "trample":
      qry.orderType = 5
      list_type = u"按踩排序"
    elif type == "star":
      qry.orderType = 6
      list_type = u"按星排序"
    else:
      type = 'new'
      
    request.setAttribute("type", type)
    request.setAttribute("list_type", list_type)
     
    # 关键字, 学科, 分类
    k = self.params.getStringParam("k")
    if k != "" and k != None:
      qry.k = k
    qry.subjectId = self.params.getIntParamZeroAsNull("subjectId")
    #print "qry.subjectId = ", qry.subjectId 
    qry.sysCateId = self.params.getIntParamZeroAsNull("categoryId")
    qry.gradeId = self.params.getIntParamZeroAsNull("gradeId")
    
    pager = self.createPager()
    pager.totalRows = qry.count() 
    article_list = qry.query_map(pager)
    request.setAttribute("article_list", article_list)
    request.setAttribute("categoryId", qry.sysCateId)
    request.setAttribute("subjectId", qry.subjectId)
    request.setAttribute("k", qry.k)
    request.setAttribute("pager", pager)
    
  def createPager(self):
    # private 构造文章的缺省 pager.
    pager = self.params.createPager()
    pager.itemName = u"文章"
    pager.itemUnit = u"篇"
    pager.pageSize = 20
    return pager


# 此页面私有的查询对象, 支持简单关键字和 userIsFamous, 页面够用即可
class __PrivateArticleQuery (ArticleQuery):
  def __init__(self, fields):
    ArticleQuery.__init__(self, fields)
    self.userIsFamous = None
    self.k = None
  
  def applyWhereCondition(self, qctx):
    ArticleQuery.applyWhereCondition(self, qctx)
    if self.userIsFamous != None:
      qctx.addAndWhere("u.isFamous = :isFamous")
      qctx.setInteger("isFamous", self.userIsFamous)
    if self.k != None and self.k != '':
      newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
      qctx.addAndWhere("a.title LIKE :keyword OR a.articleTags LIKE :keyword");
      qctx.setString("keyword", "%" + newKey + "%");
