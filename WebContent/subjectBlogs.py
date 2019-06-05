from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import *
from user_query import UserQuery
from base_action import BaseAction, ActionResult, SubjectMixiner

# 学科工作室
class subjectBlogs(BaseAction, SubjectMixiner):
  def __init__(self):
    self.params = ParamUtil(request)
    self.subj_svc = __jitar__.subjectService
    
  def execute(self):
    self.putSubject()
    # 工作室分类
    self.get_blog_cates()
    # 名师工作室
    self.get_channel_teacher()
    # 学科带头人工作室
    self.get_expert_list()
    # 工作室访问排行
    self.get_hot_list()

    # 高亮显示项目. 
    request.setAttribute("head_nav", "blogs")
    
    self.get_subject_comissioner()
    
    return "/WEB-INF/ftl/site_subject_blogs.ftl"


  def _getCateSvc(self):
    return __jitar__.categoryService
    
    
  def get_current_subjectId(self):
    subjectId = self.params.getIntParam("subjectId")
    request.setAttribute("subjectId" , subjectId)
    return subjectId


  def get_current_gradeId(self):
    gradeId = self.params.getIntParam("gradeId") 
    request.setAttribute("gradeId", self.gradeId)
    return gradeId


  # 工作室分类.  
  def get_blog_cates(self): 
    blog_cates = self._getCateSvc().getCategoryTree("default")
    request.setAttribute("blog_cates", blog_cates)
    self.get_current_gradeId()    
 
  # 名师工作室.
  def get_famous_teacher(self):
    qry = UserQuery("""  u.loginName, u.userIcon, u.nickName,u.trueName,u.articleCount, subj.subjectId """)
    qry.userTypeId = 1
    qry.userStatus = 0
    qry.metaSubjectId = self.get_current_subjectId()
    qry.metaGradeId = self.get_current_gradeId()
    channel_list = qry.query_map(6)
    request.setAttribute("channel_list", channel_list)  
    
  # 学科带头人工作室.
  def get_expert_list(self):
    qry = UserQuery("""  u.loginName, u.blogName, u.nickName,u.trueName, u.blogIntroduce,u.articleCount, subj.subjectId """)
    qry.userTypeId = 3
    qry.userStatus = 0
    qry.setSubjectCondition(self.subject)
    expert_list = qry.query_map(3)
    request.setAttribute("expert_list", expert_list)  
    
    # 教研员工作室 - 未实现.
    
  # 工作室访问排行.
  def get_hot_list(self):
    qry = UserQuery("""  u.loginName, u.blogName,u.trueName, u.visitCount,u.articleCount """)
    qry.orderType = 1
    qry.userStatus = 0
    qry.setSubjectCondition(self.subject)
    hot_list = qry.query_map(10)
    request.setAttribute("hot_list", hot_list)
    
    
    # 主工作室查询数据.
    self.query_blog()
    
  

  # 得到学科教研员列表.
  def get_subject_comissioner(self):
    qry = UserQuery(""" u.loginName, u.nickName, u.trueName,u.userIcon, u.blogName, u.createDate, 
                        u.myArticleCount, u.otherArticleCount, u.resourceCount, u.blogIntroduce,u.articleCount """)
    qry.setSubjectCondition(self.subject)
    qry.isComissioner = True
    qry.userStatus = 0
    qry.bmd = 1
    comissioner_list = qry.query_map(6)
    #print "comissioner_list = ", comissioner_list
    request.setAttribute("comissioner_list", comissioner_list)


  # 根据条件查询工作室.
  def query_blog(self):
    qry = UserQuery(""" u.userId, u.loginName, u.nickName, u.trueName, u.createDate, u.blogName, 
                        u.blogIntroduce, u.userIcon, u.myArticleCount, u.otherArticleCount, 
                        u.resourceCount, u.commentCount, u.visitCount, u.photoCount,u.articleCount, 
                        subj.subjectName, grad.gradeName, grad.gradeId, unit.unitName """)
    
    qry.userStatus = 0
    pager = self.createPager()
    
    # 根据页面参数处理.
    type = self.params.getStringParam("type")
    if type == "rcmd":
      qry.userTypeId = 2
    elif type == "hot":
      qry.orderType = 1
    else:
      type = "new"
      
    request.setAttribute("type", type)
    
    qry.subjectId = self.get_current_subjectId()
    qry.gradeId = self.get_current_gradeId()
    qry.k = self.params.getStringParam("k")
    qry.sysCateId = self.params.getIntParamZeroAsNull("categoryId")
    #qry.setSubjectCondition(self.subject)
    qry.sysCateId = self.params.getIntParamZeroAsNull("categoryId")
    
    pager.totalRows = qry.count()
    blog_list = qry.query_map(pager)
    request.setAttribute("blog_list", blog_list)
    request.setAttribute("pager", pager)
    return
  
  def createPager(self):
    pager = self.params.createPager()
    pager.pageSize = 10
    pager.itemName = u"工作室"
    pager.itemUnit = u"个"
    return pager
