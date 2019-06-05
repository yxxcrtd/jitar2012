# -*- coding: utf-8 -*-
from cn.edustar.jitar.data import BaseQuery
from base_action import * 
# =======================================================================
# 协作组查询.
class GroupQuery (BaseQuery, SubjectMixiner):
  ORDER_BY_ID_DESC = 0      # groupId DESC
  ORDER_BY_ID_ASC = 1       # groupId ASC
  ORDER_BY_CREATEDATE_DESC = 2    # createDate DESC
  ORDER_BY_CREATEDATE_ASC = 3     # createDate ASC
  ORDER_BY_GROUPNAME_DESC = 4     # groupName DESC
  ORDER_BY_GROUPNAME_ASC = 5      # groupName ASC
  ORDER_BY_GROUPTITLE_DESC = 6    # groupTitle DESC
  ORDER_BY_GROUPTITLE_ASC = 7     # groupTitle ASC 
  ORDER_BY_VISITCOUNT_DESC = 8    # visitCount DESC
  ORDER_BY_TOPICCOUNT_DESC = 9    # topicCount DESC
  
  def __init__(self, selectFields):
    BaseQuery.__init__(self, selectFields)
    self.params = ParamUtil(request)
    
    
    self.createUserId = None    # 创建者, 缺省不限制.
    self.channelId = None    # 某个频道的
    self.groupState = 0         # 协作组状态, 缺省 = 0 表示获取审核通过的.
    self.isBestGroup = None       # 是否优秀团队, 缺省 = null 不限制.
    self.isRecommend = None     # 是否推荐协作组, 缺省 = null 不限制.
    self.parentId=0             #默认不查找子组
    self.subjectId = None       # 所属学科, 缺省 = null 不限制.
    self.gradeId = None         # 所属学段, 缺省 = null 不限制.
    
    self.subjectName = None       # 所属学科, 缺省 = null 不限制.
    self.gradeName = None         # 所属学段, 缺省 = null 不限制.
    
    self.categoryId = None      # 所属协作组分类, 缺省 = null 不限制.
    self.k = None               # 查询关键字, 缺省 = null 不限制.
    self.kk = None               # 查询关键字, 缺省 = null 不限制.
    self.orderType = 0          # 排序方式, 缺省 0 - id DESC
    self.searchtype = None

  def initFromEntities(self, qctx):
    qctx.addEntity("Group", "g", "")
    
  def resolveEntity(self, qctx, entity):
    if entity == "u":
      qctx.addEntity("User", "u", "g.createUserId = u.userId")
    #elif ("msubj" == entity):
    #  qctx.addEntity("MetaSubject", "msubj", "g.subjectId = msubj.msubjId")
    elif entity == "subj":
      qctx.addJoinEntity("g", "g.subject", "subj", "LEFT JOIN")
    elif entity == "sc":
      qctx.addJoinEntity("g", "g.sysCate", "sc", "LEFT JOIN")
    elif ("grad" == entity):
      qctx.addJoinEntity("g", "g.grade", "grad", "LEFT JOIN")
    else:
      BaseQuery.resolveEntity(self, qctx, entity);


  # 提供 where 条件.
  def applyWhereCondition(self, qctx):
    if self.createUserId != None:
      qctx.addAndWhere("g.createUserId = :createUserId")
      qctx.setInteger("createUserId", self.createUserId)
    if self.channelId != None:
      qctx.addAndWhere("u.channelId = :channelId")
      qctx.setInteger("channelId", self.channelId)
    if self.groupState != None:
      qctx.addAndWhere("g.groupState = :groupState")
      qctx.setInteger("groupState", self.groupState)
    if self.isBestGroup != None:
      qctx.addAndWhere("g.isBestGroup = :isBestGroup")
      qctx.setBoolean("isBestGroup", self.isBestGroup)
    if self.isRecommend != None:
      qctx.addAndWhere("g.isRecommend = :isRecommend") 
      qctx.setBoolean("isRecommend", self.isRecommend)
    #if self.subjectId != None:
    #  qctx.addAndWhere("g.subjectId = :subjectId")
    #  qctx.setInteger("subjectId", self.subjectId)
    #if self.gradeId != None:
    #  qctx.addAndWhere("g.gradeId = :gradeId")
    #  qctx.setInteger("gradeId", self.gradeId)
    if self.gradeId != None and self.subjectId != None:
      qctx.addAndWhere("g.XKXDId LIKE :likeKey")
      qctx.setString("likeKey", "%," + str(self.gradeId) +"/"+ str(self.subjectId) + ",%")
    elif self.gradeId != None:
      qctx.addAndWhere("g.XKXDId LIKE :likeKey")
      qctx.setString("likeKey", "%," + str(self.gradeId) +"/%")
    elif self.subjectId != None:
      qctx.addAndWhere("g.XKXDId LIKE :likeKey")
      qctx.setString("likeKey", "%/" + str(self.subjectId) +",%")
    if self.parentId!=None:
      qctx.addAndWhere("g.parentId = :parentId")
      qctx.setInteger("parentId", self.parentId)
      
      #前台查询显示出年级，下面的条件暂时修改
      #qctx.addAndWhere("g.gradeId >= :gradeStartId AND g.gradeId < :gradeEndId")
      #qctx.setInteger("gradeStartId", self.calcGradeStartId(self.gradeId))
      #qctx.setInteger("gradeEndId", self.calcGradeEndId(self.gradeId))
    
    
    #if self.gradeId != None:
     # qctx.addAndWhere("g.gradeId = :gradeId")
     # qctx.setInteger("gradeId", self.gradeId)
    if self.categoryId != None:
      qctx.addAndWhere("g.categoryId = :categoryId")
      qctx.setInteger("categoryId", self.categoryId)
      
    if self.k != None and self.k != "":
      newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
      qctx.addAndWhere("(g.groupName LIKE :keyword OR g.groupTitle LIKE :keyword OR g.groupTags LIKE :keyword OR g.groupIntroduce LIKE :keyword)")
      qctx.setString("keyword", "%" + newKey + "%")
    if self.kk != None and self.kk != "":
      if self.searchtype==None or self.searchtype=="ktname":  
        newKey = self.kk.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
        qctx.addAndWhere("(g.groupName LIKE :likeKey) OR (g.groupTitle LIKE :likeKey)")
        qctx.setString("likeKey", "%" + newKey + "%")
      elif self.searchtype=="ktperson":
        #是课题组查询负责人
        newKey = self.kk.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
        qctx.addAndWhere("g.groupId In (SELECT groupId FROM cn.edustar.jitar.pojos.GroupKTUser WHERE (teacherName LIKE :likeKey))")
        qctx.setString("likeKey", "%" + newKey + "%")
        
  # 提供排序 order 条件.
  def applyOrderCondition(self, qctx):
    if self.orderType == GroupQuery.ORDER_BY_ID_DESC:
      qctx.addOrder("g.groupId DESC")
    elif self.orderType == GroupQuery.ORDER_BY_ID_ASC:
      qctx.addOrder("g.groupId ASC")
    elif self.orderType == GroupQuery.ORDER_BY_CREATEDATE_DESC:
      qctx.addOrder("g.createDate DESC, g.groupId DESC")
    elif self.orderType == GroupQuery.ORDER_BY_CREATEDATE_ASC:
      qctx.addOrder("g.createDate ASC, g.groupId ASC")
    elif self.orderType == GroupQuery.ORDER_BY_GROUPNAME_DESC:
      qctx.addOrder("g.groupName DESC, g.groupId DESC")
    elif self.orderType == GroupQuery.ORDER_BY_GROUPNAME_ASC:
      qctx.addOrder("g.groupName ASC, g.groupId ASC")
    elif self.orderType == GroupQuery.ORDER_BY_GROUPTITLE_DESC:
      qctx.addOrder("g.groupTitle DESC, g.groupId DESC")
    elif self.orderType == GroupQuery.ORDER_BY_GROUPTITLE_ASC:
      qctx.addOrder("g.groupTitle ASC, g.groupId ASC")
    elif self.orderType == GroupQuery.ORDER_BY_VISITCOUNT_DESC:
      qctx.addOrder("g.visitCount DESC, g.groupId DESC")
    elif self.orderType == GroupQuery.ORDER_BY_TOPICCOUNT_DESC:
      qctx.addOrder("g.topicCount DESC, g.groupId DESC")

