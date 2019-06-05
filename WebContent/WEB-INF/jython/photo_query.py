# -*- coding: utf-8 -*-
from cn.edustar.jitar.data import BaseQuery
from cn.edustar.jitar.util import ParamUtil
#from cn.edustar.jitar.pojos import Photo

# =======================================================================
# 图片查询.
class PhotoQuery (BaseQuery):
  ORDER_TYPE_ID_DESC = 0
  ORDER_TYPE_PHOTOID_DESC = 0     # default.
  ORDER_TYPE_CREATEDATE_DESC = 1
  ORDER_TYPE_VIEWCOUNT_DESC = 2
  ORDER_TYPE_COMMENTCOUNT = 3

  def __init__(self, selectFields):
    BaseQuery.__init__(self, selectFields)
    self.params = ParamUtil(request)
    self.cate_svc = __jitar__.categoryService
    # 要查询的图片所属用户, == null 表示不限制.
    self.userId = None
    # 是否审核通过, 缺省 = 0 表示审核通过的.
    self.auditState = 0
    # 默认显示没有删除的照片
    self.delState = False
    # 所属系统分类, 缺省 = null 表示不限制.
    self.sysCateId = None
    # 所属用户分类, 缺省 = null 表示不限制.
    self.userStapleId = None
    # 默认在所有地方显示照片，不局限于个人空间
    self.isPrivateShow = False
    self.unitId = None
    
    self.specialSubjectId=None
    self.unitPath = None
    self.sharedDepth = None
    self.rcmdDepth = None
    
    # 默认是精确匹配
    self.FuzzyMatch = False
    
    self.channelId = None
    self.channelCate = None
    
    # 简单实现按扩展名查询
    self.extName = None
    
    # 查询关键字. 
    self.k = self.params.safeGetStringParam("k", None)
    # 查询字段. 0 标题  1简介  2用户
    self.f = self.params.safeGetStringParam("f", "0")    
    request.setAttribute("f", self.f)
    request.setAttribute("k", self.k)
    
    # 排序方式, 0 - photoId DESC, 1 - createDate DESC, 2 - viewCount DESC, 3 - commentCount DESC
    self.orderType = 0
    self.params = ParamUtil(request)
    self.sysCateId = self.params.getIntParamZeroAsNull("categoryId")
   

  def initFromEntities(self, qctx):
    qctx.addEntity("Photo", "p", "")
    
  def resolveEntity(self, qctx, entity):
    if "u" == entity:
      qctx.addEntity("User", "u", "p.userId = u.userId")
    elif "stap" == entity:
      qctx.addJoinEntity("p", "p.staple", "stap", "LEFT JOIN")
    elif "sc" == entity:
      qctx.addJoinEntity("p", "p.sysCate", "sc", "LEFT JOIN")
    else:
      BaseQuery.resolveEntity(self,qctx, entity)


  # 提供 where 条件.
  def applyWhereCondition(self, qctx):
    if self.k != None and self.k != "":
      self._applyKeywordFilter(qctx)
    if self.userId != None:
      qctx.addAndWhere("p.userId = :userId")
      qctx.setInteger("userId", self.userId)
    if self.auditState != None:
      qctx.addAndWhere("p.auditState = :auditState")
      qctx.setInteger("auditState", self.auditState)
    if self.delState != None:
      qctx.addAndWhere("p.delState = :delState")
      qctx.setBoolean("delState", self.delState)
    if self.sysCateId != None:
      #只查询分类自己的  
      #qctx.addAndWhere("p.sysCateId = :sysCateId")
      #qctx.setInteger("sysCateId", self.sysCateId)
      #查询自己和子孙分类
      list=self.cate_svc.getCategoryIds(self.sysCateId)
      cateIds=""
      for c in list:
        if cateIds=="":
          cateIds=cateIds+str(c)
        else:
          cateIds=cateIds+","+str(c)
      qctx.addAndWhere("p.sysCateId IN (" + cateIds + ")")
            
    if self.userStapleId != None:
      qctx.addAndWhere("p.userStaple = :userStapleId")
      qctx.setInteger("userStapleId", self.userStapleId)
    if self.specialSubjectId != None:
      qctx.addAndWhere("p.specialSubjectId = :specialSubjectId")
      qctx.setInteger("specialSubjectId", self.specialSubjectId)
    if self.isPrivateShow != None:
      qctx.addAndWhere("p.isPrivateShow = :isPrivateShow")
      qctx.setBoolean("isPrivateShow", self.isPrivateShow)
    if self.extName != None:
      qctx.addAndWhere(" p.href LIKE :extName")
      qctx.setString("extName", "%" + self.extName)
    if self.unitId != None:
      qctx.addAndWhere("p.unitId = :unitId")
      qctx.setInteger("unitId", self.unitId)
    
    if self.unitPath != None:
      qctx.addAndWhere("p.unitPath LIKE :unitPath")
      qctx.setString("unitPath", self.unitPath + "%")      
    if self.sharedDepth != None:
      qctx.addAndWhere("p.sharedDepth LIKE :sharedDepth")
      qctx.setString("sharedDepth", "%" + self.sharedDepth + "%")      
    if self.rcmdDepth != None:
      qctx.addAndWhere("p.rcmdDepth LIKE :rcmdDepth")
      qctx.setString("rcmdDepth", "%" + self.rcmdDepth + "%")
      
    if self.channelId != None:
      qctx.addAndWhere("p.channelId = :channelId")
      qctx.setInteger("channelId", self.channelId)
    
    if self.channelCate != None:
      if self.FuzzyMatch == False:
        #精确匹配
        qctx.addAndWhere("p.channelCate = :channelCate")
        qctx.setString("channelCate", self.channelCate)
      else:
        # 模糊匹配
        qctx.addAndWhere("p.channelCate LIKE :channelCate")
        qctx.setString("channelCate", "%" + self.channelCate + "%")

  def _applyKeywordFilter(self, qctx):
    newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
    if self.f == "0":
      qctx.addAndWhere("(p.title LIKE :likeKey) OR (p.tags LIKE :likeKey) OR (stap.title LIKE :likeKey) OR (p.addIp LIKE :likeKey)")
      qctx.setString("likeKey", "%" + newKey + "%")
    elif self.f == "1":
      qctx.addAndWhere(" p.summary LIKE :likeKey")
      qctx.setString("likeKey", "%" + newKey + "%")
    elif self.f == "2":
      qctx.addAndWhere("(p.userNickName LIKE :likeKey) OR (p.userTrueName LIKE :likeKey)")
      qctx.setString("likeKey", "%" + newKey + "%")
    elif self.f == "3":
      qctx.addAndWhere("p.title LIKE :likeKey")
      qctx.setString("likeKey", "%" + newKey + "%")
    else:    
      qctx.addAndWhere("(p.title LIKE :likeKey) OR (p.tags LIKE :likeKey) OR (stap.title LIKE :likeKey) OR (p.addIp LIKE :likeKey)")
      qctx.setString("likeKey", "%" + newKey + "%")

  # 提供排序 order 条件.
  def applyOrderCondition(self, qctx):
    # 排序方式, 0 - photoId DESC, 1 - createDate DESC, 2 - viewCount DESC, 3 - commentCount DESC
    if self.orderType == 0:
      qctx.addOrder("p.photoId DESC")
    elif self.orderType == 1:
      qctx.addOrder("p.createDate DESC")
    elif self.orderType == 2:
      qctx.addOrder("p.viewCount DESC")
    elif self.orderType == 3:
      qctx.addOrder("p.commentCount DESC")

# =======================================================================
# 协作组图片查询.
class GroupPhotoQuery(PhotoQuery):
  def __init__(self, selectFields):
    PhotoQuery.__init__(self, selectFields)
    self.groupId = None       # int, 限定某个协作组.
    self.isGroupBest = None   # boolean, 是否限定协作组精华.
    self.gresCateId = None    # int, 限定协作组图片分类.
    self.isPrivateShow=None

  def initFromEntities(self, qctx):
    qctx.addEntity("GroupPhoto", "gp", "gp.photoId = p.photoId")
    qctx.addEntity("Photo", "p", "")


  def resolveEntity(self, qctx, entity):
    # 能够关联到协作组 g, 协作组资源分类 grc.
    if "g" == entity:
      qctx.addEntity("Group", "g", "gp.groupId = g.groupId")
    elif "gpc" == entity:
      qctx.addJoinEntity("gp", "gp.groupCate", "gpc", "LEFT JOIN")
    else:
      PhotoQuery.resolveEntity(self, qctx, entity)


  def applyWhereCondition(self, qctx):
    if self.groupId != None:
      qctx.addAndWhere("gp.groupId = :groupId");
      qctx.setInteger("groupId", self.groupId);
    if self.isGroupBest != None:
      qctx.addAndWhere("gp.isGroupBest = :isGroupBest");
      qctx.setBoolean("isGroupBest", self.isGroupBest);
    if self.gresCateId != None:
      qctx.addAndWhere("gp.groupCateId = :gresCateId");
      qctx.setInteger("gresCateId", self.gresCateId);
    PhotoQuery.applyWhereCondition(self, qctx)
    
  def applyOrderCondition(self, qctx):
    if self.orderType == 0:
      qctx.addOrder("gp.id DESC")
