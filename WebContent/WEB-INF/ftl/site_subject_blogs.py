# script 

from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import *
from user_query import UserQuery
from base_action import *

# 数据获取执行脚本.
class site_subject_blogs(SubjectMixiner):
  def __init__(self):
    self.params = ParamUtil(request)
    self.subj_svc = __jitar__.subjectService
    
  def execute(self):
    #print "site_subject_blogs python 脚本正在执行"
    
    
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
    self.setData("head_nav", "blogs")
    self.get_subject_comissioner()
    
    return "success"

  def _getCateSvc(self):
    return __jitar__.categoryService
  
  # 工作室分类.  
  def get_blog_cates(self): 
    blog_cates = self._getCateSvc().getCategoryTree("default")
    request.setAttribute("blog_cates", blog_cates)
 
  # 名师工作室.
  def get_channel_teacher(self):
    qry = UserQuery("""  u.loginName, u.userIcon, u.nickName, subj.subjectId """)
    qry.isFamous = True
    channel_teachers = qry.query_map(6)
    request.setAttribute("channel_teachers", channel_teachers)
  
    
  # 学科带头人工作室.
  def get_expert_list(self):
    qry = UserQuery(""" u.loginName, u.blogName, u.nickName, u.blogIntroduce, subj.subjectId """)
    qry.userTypeId = 3
    expert_list = qry.query_map(2)
    request.setAttribute("expert_list", expert_list)
    
    # 教研员工作室 - 未实现.
    
  # 工作室访问排行.
  def get_hot_list(self):
    qry = UserQuery(""" u.loginName, u.blogName, u.visitCount """)
    qry.orderType = 1
    hot_list = qry.query_map(10)
    request.setAttribute("hot_list", hot_list)
    
    
    # 主工作室查询数据.
    self.query_blog()
    

  # 得到学科教研员列表.
  def get_subject_comissioner(self):
    qry = UserQuery(""" u.loginName, u.nickName, u.userIcon, u.blogName, u.createDate, 
                        u.articleCount, u.resourceCount, u.blogIntroduce """)
    qry.subjectId = self.subject.subjectId
    qry.isComissioner = True
    comissioner_list = qry.query_map(6)
    request.setAttribute("comissioner_list", comissioner_list)
    

  # 根据条件查询工作室.
  def query_blog(self):
    qry = UserQuery(""" u.userId, u.loginName, u.nickName, u.createDate, u.blogName, u.blogIntroduce,
          u.userIcon, u.articleCount, u.resourceCount, u.commentCount, u.visitCount, u.photoCount,
          subj.subjectName, unit.unitName """)
    qry.subjectId = self.subject.subjectId
    qry.sysCateId = self.params.getIntParamZeroAsNull("categoryId")
    qry.k = self.params.getStringParam("k")
    
    pager = self.createPager()
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
    
    
