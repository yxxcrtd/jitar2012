from cn.edustar.jitar.data import *
from article_query import ArticleQuery
from base_action import *
# 数据获取执行脚本.
class subjectArticles(SubjectMixiner):
  def __init__(self):
    self.params = ParamUtil(request)
    self.cate_svc = __jitar__.categoryService
    self.subj_svc = __jitar__.subjectService
    return

  def execute(self):
    self.putSubject()
    # 文章分类. 
    self.get_blog_cates()
    
    # 文章查询列表. 未完成.
    self.get_article_list()
    # 高亮显示项目.
    request.setAttribute("head_nav", "articles")    
    request.setAttribute("type", self.params.getStringParam("type"))
    
    return "/WEB-INF/ftl/site_subject_articles.ftl"
    
  # 文章分类.
  def get_blog_cates(self):
    blog_cates = self.cate_svc.getCategoryTree("default")
    request.setAttribute("blog_cates", blog_cates)

  def get_current_gradeId(self): 
    gradeId = self.params.getIntParam("gradeId")
    request.setAttribute("gradeId",self.gradeId)
    return gradeId
  def get_current_subjectId(self):
    subjectId = self.params.getIntParam("subjectId")
    request.setAttribute("subjectId", subjectId)
    return subjectId

   # 文章主查询列表, 带分页. 
  def get_article_list(self):
    qry = ArticleQuery(""" a.articleId, a.title, a.createDate, a.viewCount, a.commentCount, a.userId,
                          a.recommendState, a.typeState, u.loginName, u.nickName """)
    pager = self.createPager()
    type = self.params.getStringParam("type")
    
    if type == "rcmd":
      qry.rcmdState = True
    elif type == "hot":
      qry.orderType = 2
    elif type == "cmt":
      qry.orderType = 3
    else:
      type = "new"
    request.setAttribute("type", type)
    
    qry.subjectId = self.get_current_subjectId()
    qry.gradeId = self.get_current_gradeId()
    qry.k = self.params.getStringParam("k")
    qry.sysCateId = self.params.getIntParamZeroAsNull("categoryId")
    
    pager.totalRows = qry.count()
    article_list = qry.query_map(pager)
    request.setAttribute("article_list", article_list)
    request.setAttribute("pager", pager)
    
   
   # 创建分页对象.
  def createPager(self):
    pager = self.params.createPager()
    pager.itemName = u"文章"
    pager.itemUnit = u"篇"
    pager.pageSize = 20
    return pager

