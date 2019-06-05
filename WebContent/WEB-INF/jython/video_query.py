# -*- coding: utf-8 -*-
from cn.edustar.jitar.data import BaseQuery
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner

# =======================================================================
# 视频查询.
class VideoQuery (BaseQuery, SubjectMixiner):
  ORDER_TYPE_VIDEOID_DESC = 0     # default.
  ORDER_TYPE_CREATEDATE_DESC = 1
  ORDER_TYPE_VIEWCOUNT_DESC = 2
  ORDER_TYPE_COMMENTCOUNT = 3

  def __init__(self, selectFields):
    BaseQuery.__init__(self, selectFields)
    self.params = ParamUtil(request)
    self.cate_svc = __jitar__.categoryService
    # 要查询的视频所属用户, == null 表示不限制.
    self.userId = None
    # 是否审核通过, 缺省 = 0 表示审核通过的.
    self.auditState = 0
    # 默认显示没有删除的照片
    self.delState = False
    # 所属状态, 缺省 = null 表示不限制. 0引用 1原创
    self.typeState = None
    self.unitId = None
    self.categoryId = None
    self.gradeId = None
    self.subjectId = None
    self.specialSubjectId = None
    self.videoIds=None
    #视频的自定义分类
    self.userCateId=None
    # 默认是精确匹配
    self.FuzzyMatch = False
    
    # 查询关键字. 
    self.k = self.params.safeGetStringParam("k", None)
    # 查询字段. 0 标题  1简介  2用户
    self.f = self.params.safeGetStringParam("f", "0")    
    self.categoryId = self.params.getIntParamZeroAsNull("categoryId")
    self.userCateId = self.params.getIntParamZeroAsNull("userCateId")
    self.subjectId = self.params.getIntParamZeroAsNull("subjectId")
    self.gradeId = self.params.getIntParamZeroAsNull("gradeId")
    self.userId = self.params.getIntParamZeroAsNull("userId")
    self.unitId = self.params.getIntParamZeroAsNull("unitId")
    
    request.setAttribute("f", self.f)
    request.setAttribute("k", self.k)
    request.setAttribute("categoryId", self.categoryId)
    request.setAttribute("userCateId", self.userCateId)    
    
    # 排序方式, 0 - videoId DESC, 1 - createDate DESC, 2 - viewCount DESC, 3 - commentCount DESC
    self.orderType = 0
   
  def initFromEntities(self, qctx):
    qctx.addEntity("Video", "v", "")
    
  def resolveEntity(self, qctx, entity):
    if "u" == entity:
      qctx.addEntity("User", "u", "v.userId = u.userId")
    elif ("sc" == entity):
      qctx.addJoinEntity("v", "v.sysCate", "sc", "LEFT JOIN")
    else:
      BaseQuery.resolveEntity(self, qctx, entity)

  # 提供 where 条件.
  def applyWhereCondition(self, qctx):
    if self.k != None and self.k != "":
      self._applyKeywordFilter(qctx)
    if self.userId != None:
      qctx.addAndWhere("v.userId = :userId")
      qctx.setInteger("userId", self.userId)
      
    if self.auditState != None:
      qctx.addAndWhere("v.auditState = :auditState")
      qctx.setInteger("auditState", self.auditState)
     
    if self.unitId != None:
      qctx.addAndWhere("v.unitId = :unitId")
      qctx.setInteger("unitId", self.unitId)
      
    if self.categoryId != None:
      #只查询分类自己的  
      #qctx.addAndWhere("v.categoryId = :categoryId")
      #qctx.setInteger("categoryId", self.categoryId)
      #查询自己和子孙分类
      list=self.cate_svc.getCategoryIds(self.categoryId)
      cateIds=""
      for c in list:
        if cateIds=="":
          cateIds=cateIds+str(c)
        else:
          cateIds=cateIds+","+str(c)
      qctx.addAndWhere("v.categoryId IN (" + cateIds + ")")
      
    if self.userCateId != None:
      qctx.addAndWhere("v.userCateId = :userCateId")
      qctx.setInteger("userCateId", self.userCateId)
            
    if self.subjectId != None:
      qctx.addAndWhere("v.subjectId = :subjectId")
      qctx.setInteger("subjectId", self.subjectId)
      
    if self.videoIds != None:
      strWhereClause = " v.videoId IN (" + self.videoIds + ")" 
      qctx.addAndWhere(strWhereClause)

    if self.gradeId != None:
      qctx.addAndWhere("v.gradeId >= :gradeId1 and v.gradeId < :gradeId2")
      qctx.setInteger("gradeId1", self.convertRoundMinNumber(self.gradeId))
      qctx.setInteger("gradeId2", self.convertRoundMaxNumber(self.gradeId)) 
      #print "self.convertRoundMinNumber(self.gradeId)=",self.convertRoundMinNumber(self.gradeId)   
      #print "self.convertRoundMaxNumber(self.gradeId)=",self.convertRoundMaxNumber(self.gradeId)     
    
    if self.specialSubjectId != None:
      qctx.addAndWhere("v.specialSubjectId = :specialSubjectId")
      qctx.setInteger("specialSubjectId", self.specialSubjectId)
      
  def _applyKeywordFilter(self, qctx):
    newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
    if self.f == "0":
      qctx.addAndWhere("(v.title LIKE :likeKey) OR (v.tags LIKE :likeKey) OR (v.addIp LIKE :likeKey)")
      qctx.setString("likeKey", "%" + newKey + "%")
    elif self.f == "1":
      qctx.addAndWhere(" v.summary LIKE :likeKey")
      qctx.setString("likeKey", "%" + newKey + "%")
    elif self.f == "2":
      qctx.addAndWhere("(u.nickName LIKE :likeKey) OR (u.trueName LIKE :likeKey) OR (u.loginName LIKE :likeKey)")
      qctx.setString("likeKey", "%" + newKey + "%")
    elif self.f == "3": #只查标题
      qctx.addAndWhere("v.title LIKE :likeKey")
      qctx.setString("likeKey", "%" + newKey + "%")
    else:    
      qctx.addAndWhere("(v.title LIKE :likeKey) OR (v.tags LIKE :likeKey) OR (v.addIp LIKE :likeKey)")
      qctx.setString("likeKey", "%" + newKey + "%")

  # 提供排序 order 条件.
  def applyOrderCondition(self, qctx):
    # 排序方式, 0 - videoId DESC, 1 - createDate DESC, 2 - viewCount DESC, 3 - commentCount DESC
    if self.orderType == 0:
      qctx.addOrder("v.videoId DESC")
    elif self.orderType == 1:
      qctx.addOrder("v.createDate DESC")
    elif self.orderType == 2:
      qctx.addOrder("v.viewCount DESC")
    elif self.orderType == 3:
      qctx.addOrder("v.commentCount DESC")

# =======================================================================
# 协作组视频查询.
class GroupVideoQuery(VideoQuery):
  def __init__(self, selectFields):
    VideoQuery.__init__(self, selectFields)
    self.groupId = None       # int, 限定某个协作组.
    self.isGroupBest = None   # boolean, 是否限定协作组精华.
    self.gresCateId = None    # int, 限定协作组视频分类.

  def initFromEntities(self, qctx):
    qctx.addEntity("GroupVideo", "gv", "gv.videoId = v.videoId")
    qctx.addEntity("Video", "v", "")


  def resolveEntity(self, qctx, entity):
    # 能够关联到协作组 g, 协作组视频分类 gvc.
    if "g" == entity:
      qctx.addEntity("Group", "g", "gv.groupId = g.groupId")
    elif "gvc" == entity:
      qctx.addJoinEntity("gv", "gv.groupCate", "gvc", "LEFT JOIN")
    else:
      VideoQuery.resolveEntity(self, qctx, entity)


  def applyWhereCondition(self, qctx):
    if self.groupId != None:
      qctx.addAndWhere("gv.groupId = :groupId");
      qctx.setInteger("groupId", self.groupId);
    if self.isGroupBest != None:
      qctx.addAndWhere("gv.isGroupBest = :isGroupBest");
      qctx.setBoolean("isGroupBest", self.isGroupBest);
    if self.gresCateId != None:
      qctx.addAndWhere("gv.groupCateId = :gresCateId");
      qctx.setInteger("gresCateId", self.gresCateId);
    VideoQuery.applyWhereCondition(self, qctx)
    
  def applyOrderCondition(self, qctx):
    if self.orderType == 0:
      qctx.addOrder("gv.id DESC")
