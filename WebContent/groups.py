# coding=utf-8
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from group_query import GroupQuery
from cn.edustar.jitar.data import Command

# 协作组页面.
class groups(SubjectMixiner):
  def __init__(self):
    self.params = ParamUtil(request)
    return  
  
  def execute(self):
    
    self.qrytype = self.params.getStringParam("type")
    
    # 协作组分类, 学科, 区县.
    self.get_group_cate()
    self.get_subject_list()
    
    # 学段分类.
    self.get_grade_list()
    
    # 推荐, 热门协作组, (最新不使用).
    self.get_rcmd_group_list()
    self.get_hot_group_list()
    # self.get_new_group_list()
    
    # 查询协作组.
    self.query_group_list()
    
    
     # 页面导航高亮为 'groups'
    request.setAttribute("head_nav", "groups")
    
    self.query_group_activity_list()
    
    response.contentType = "text/html; charset=UTF-8"
    return "/WEB-INF/ftl/site_groups.ftl"
  
  def _getCateSvc(self):
    return __jitar__.categoryService

  
  # 协作组分类.
  def get_group_cate(self): 
    group_cate = self._getCateSvc().getCategoryTree("group")
    request.setAttribute("group_cate", group_cate)

    
  # 学科列表.
  def get_subject_list(self):
   self.putSubjectList()

  #学段
  def get_grade_list(self):
    request.setAttribute("gradeId", self.params.getIntParamZeroAsNull("gradeId"))
    self.putGradeList()  

  # 推荐协作组.
  def get_rcmd_group_list(self):
    qry = GroupQuery(""" g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.groupIntroduce,g.parentId,g.XKXDName,g.XKXDId """)
    qry.isRecommend = True
    rcmd_group_list = qry.query_map(10)
    request.setAttribute("rcmd_group_list", rcmd_group_list)
    
    
  # 热门协作组.
  def get_hot_group_list(self):
    qry = GroupQuery(""" g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.groupIntroduce, g.createUserId,g.parentId,g.XKXDName,g.XKXDId,
                         u.loginName, u.nickName """)
    qry.orderType = GroupQuery.ORDER_BY_VISITCOUNT_DESC     # visitCount DESC
    hot_group_list = qry.query_map()
    request.setAttribute("hot_group_list", hot_group_list)
    
    
  # 最新协作组.
  def get_new_group_list(self):
    qry = GroupQuery("""  g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.groupIntroduce,g.parentId,g.XKXDName,g.XKXDId """)
    new_group_list = qry.query_map()
    request.setAttribute("new_group_list", new_group_list)
    
    
  # 查询协作组.
  def query_group_list(self):
    qry = GroupQuery(""" g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.userCount,g.parentId,
              g.visitCount, g.articleCount, g.topicCount, g.resourceCount, g.groupIntroduce,g.XKXDName,g.XKXDId,
              g.groupTags, subj.subjectName, grad.gradeName, sc.name as scName """)  
    qry.subjectId = self.params.getIntParamZeroAsNull('subjectId')
    qry.categoryId = self.params.getIntParamZeroAsNull('categoryId')
    qry.gradeId = self.params.getIntParamZeroAsNull('gradeId')
    qry.k = self.params.getStringParam("k")
    if self.qrytype != None:
        if self.qrytype == "best":
            qry.isBestGroup = True
        if self.qrytype == "rcmd":
            qry.isRecommend = True
        if self.qrytype == "hot":
            qry.orderType = GroupQuery.ORDER_BY_VISITCOUNT_DESC
        if self.qrytype == "new":
            qry.orderType = GroupQuery.ORDER_BY_ID_DESC
        
    pager = self.createPager()
    
    pager.totalRows = qry.count() 
    group_list = qry.query_map(pager)
    request.setAttribute("group_list", group_list)
    #print "group_list = ", group_list
    request.setAttribute("pager", pager)
    request.setAttribute("subjectId", qry.subjectId)
    request.setAttribute("categoryId", qry.categoryId)
    request.setAttribute("gradeId", qry.gradeId)
    request.setAttribute("k", qry.k)
  
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
    hql = """ select new Map( g.groupId as groupId, g.groupTitle as groupTitle, g.parentId, (g.userCount + g.articleCount + g.topicCount + g.resourceCount) / 10 as totalCount)
                      FROM Group g
                      where g.groupState = 0
                      ORDER BY (g.userCount + g.articleCount + g.topicCount + g.resourceCount) DESC
                     """
    cmd = Command(hql)
    group_activity_list = cmd.open(25)
    request.setAttribute("group_activity_list", group_activity_list)
    #print "group_activity_list = ", group_activity_list
