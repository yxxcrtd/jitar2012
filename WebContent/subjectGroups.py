# script 
from cn.edustar.jitar.data import *
from group_query import GroupQuery
from base_action import *

# 数据获取执行脚本.
class subjectGroups(SubjectMixiner):
  def __init__(self):
    self.params = ParamUtil(request)
    self.subj_svc = __jitar__.subjectService
  
  def execute(self):
    
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
    
    # 活跃度排行
    self.query_group_activity_list()
    
    # 高亮显示项目.
    request.setAttribute("head_nav", "groups")
    
    return "/WEB-INF/ftl/site_subject_groups.ftl"
  
  def get_current_gradeId(self):
    self.gradeId = self.params.getIntParam("gradeId")
    request.setAttribute("gradeId", self.gradeId)
    return self.gradeId

  def get_current_subjectId(self):
    subjectId = self.params.getIntParam("subjectId")
    request.setAttribute("subjectId" ,subjectId)
    return subjectId

  def _getCateSvc(self):
    self.get_current_gradeId()
    return __jitar__.categoryService

  # 协作组分类.
  def get_group_cates(self):
    group_cates = self._getCateSvc().getCategoryTree("group")
    request.setAttribute("group_cates", group_cates)
    self.get_current_gradeId()
    
  # 推荐协作组.
  def get_rcmd_list(self):
    qry = GroupQuery("""  subj.subjectId, g.groupId, g.groupIcon,  g.groupTitle, g.createDate, g.groupIntroduce """)
    qry.isRecommend = True
    qry.subjectId = self.get_current_subjectId()
    qry.gradeId = self.get_current_gradeId()
    rcmd_list = qry.query_map(4)
    request.setAttribute("rcmd_list", rcmd_list)
    
   
  # 热门协作组.
  def get_hot_list(self):
    qry = GroupQuery("""  subj.subjectId, g.createUserId, g.groupIcon, g.groupId, g.groupTitle, g.createDate, g.groupIntroduce,
                          u.loginName, u.nickName  """)
    qry.orderType = 2
    qry.subjectId = self.get_current_subjectId()
    qry.gradeId = self.get_current_gradeId()
    hot_list = qry.query_map()
    request.setAttribute("hot_list", hot_list)
  

  # 最新协作组.
  def get_new_group_list(self):
    qry = GroupQuery("""  subj.subjectId, g.createUserId, g.groupId, g.groupIcon, g.groupTitle, g.createDate,
                          u.loginName, u.nickName """)
    qry.orderType = 1
    qry.subjectId = self.get_current_subjectId()
    qry.gradeId = self.get_current_gradeId()
    new_group_list = qry.query_map(4)
    request.setAttribute("new_group_list", new_group_list)
   
    
  # 协作组搜索.
  def get_group_list(self): 
    qry = GroupQuery("""  subj.subjectId, g.createUserId, g.groupId, g.groupIcon, g.groupTitle, g.createDate,
                          u.loginName, u.nickName, g.userCount, g.visitCount, g.articleCount, g.topicCount, 
                            g.resourceCount, g.groupTitle , g.groupIntroduce """)
    
    pager = self.createPager()
    
    # 根据页面参数处理.
    type = self.params.getStringParam("type")
    if type == "rcmd":
      qry.isRecommend = True
    elif type == "hot":
      qry.orderType = GroupQuery.ORDER_BY_VISITCOUNT_DESC
    elif type == "rcmd":
      qry.isRecommend = True
    else:
      type = "new"
    
    
     
    request.setAttribute("type", type)
    
    qry.subjectId = self.get_current_subjectId()
    qry.gradeId = self.get_current_gradeId()
    
    qry.k = self.params.getStringParam("k")
    #qry.sysCateId = self.params.getIntParamZeroAsNull("categoryId")
    
    pager.totalRows = qry.count()
    group_list = qry.query_map(pager)
    request.setAttribute("group_list", group_list)
    request.setAttribute("pager", pager)
    
    

   # 创建分页对象.
  def createPager(self):
    pager = self.params.createPager()
    pager.itemName = u"协作组"
    pager.itemUnit = u"个"
    pager.pageSize = 10
    return pager


  # 活跃度排行
  def query_group_activity_list(self):
    # 活跃度算法 (g.userCount + g.articleCount + g.topicCount + g.resourceCount) / 10 
    hql = """ select new Map( g.groupId as groupId, g.groupTitle as groupTitle, (g.userCount + g.articleCount + g.topicCount + g.resourceCount) / 10 as totalCount)
                      FROM Group g
                      where g.groupState = 0 and subjectId = :subjectId and gradeId > :start_gradeId and gradeId < :end_gradeId
                      ORDER BY (g.userCount + g.articleCount + g.topicCount + g.resourceCount) DESC
                     """
    cmd = Command(hql)
    # 整数化学段
    round_gradeId = int(self.get_current_gradeId() / 1000) * 1000
    start_gradeId = round_gradeId - 1
    end_gradeId = round_gradeId + 1000
    
    cmd.setInteger("subjectId",self.get_current_subjectId())
    cmd.setInteger("start_gradeId",start_gradeId)
    cmd.setInteger("end_gradeId",end_gradeId)
    
    group_activity_list = cmd.open(25)
    request.setAttribute("group_activity_list", group_activity_list)
    #print "group_activity_list = ", group_activity_list
    
    