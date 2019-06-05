# script 

from cn.edustar.jitar import PythonAction
from cn.edustar.jitar.data import *
from group_query import GroupQuery
from base_action import *

# 数据获取执行脚本.
class site_subject_groups(SubjectMixiner):
  
  def __init__(self):
    self.params = ParamUtil(request)
    self.subj_svc = __jitar__.subjectService
  
  def execute(self):
    #print "site_subject_groups python 脚本正在执行" , self.subject
    self.putSubject()
    # 协作组分类
    self.get_group_cates()
    
    # 推荐协作组
    self.get_rcmd_list()
    
    # 协作组搜索.未完成
    self.get_group_list()
    
    # 热门协作组
    self.get_hot_list()
    
    # 最新协作组
    self.get_new_group_list()
    
    self.get_group_active_list()
    
    # 高亮显示项目.
    self.setData("head_nav", "groups")
    
    return "success"
  
  
  def _getCateSvc(self):
    return __jitar__.categoryService
  # 协作组分类.
  def get_group_cates(self):
    group_cates = self._getCateSvc().getCategoryTree("group")
    request.setAttribute("group_cates", group_cates)
 
    
  # 推荐协作组.
  def get_rcmd_list(self):
    qry = GroupQuery("""  subj.subjectId, g.groupId, g.groupIcon,  g.groupTitle, g.createDate, g.groupIntroduce """)
    qry.isRecommend = True
    qry.subjectId = self.subject.subjectId
    rcmd_list = qry.query_map(4)
    request.setAttribute("rcmd_list", rcmd_list)
    
   
  # 热门协作组.
  def get_hot_list(self):
    qry = GroupQuery("""  subj.subjectId, g.createUserId, g.groupIcon, g.groupId, g.groupTitle, g.createDate, g.groupIntroduce,
                          u.loginName, u.nickName  """)
    qry.orderType = 2    
    qry.subjectId = self.subject.subjectId
    hot_list = qry.query_map()
    request.setAttribute("hot_list", hot_list)
  

  # 最新协作组.
  def get_new_group_list(self):
    qry = GroupQuery("""  subj.subjectId, g.createUserId, g.groupId, g.groupIcon, g.groupTitle, g.createDate,
                          u.loginName, u.nickName """)
    qry.orderType = 1
    new_group_list = qry.query_map(4)
    request.setAttribute("new_group_list", new_group_list)
   
  
  # 协作组活跃度排行.
  def get_group_active_list(self):
    hql = """ select new Map( g.groupId as groupId, g.groupTitle as groupTitle, (g.userCount + g.articleCount + g.topicCount + g.resourceCount) / 10 as totalCount)
                      FROM Group g
                      where g.groupState = 0 And subjectId = :subjectId
                      ORDER BY (g.userCount + g.articleCount + g.topicCount + g.resourceCount) DESC
                     """
    cmd = Command(hql)
    cmd.setInteger("subjectId",self.subject.subjectId)
    group_activity_list = cmd.open(25)
    request.setAttribute("group_activity_list", group_activity_list)
    #print "group_activity_list = ",group_activity_list
    
  # 协作组搜索. 未完成
  def get_group_list(self): 
    group_list = QueryGroupBean()
    group_list.varName = "group_list"
    group_list.usePager = 1
    group_list.pageSize = 10
    self.addBean(group_list)
        
