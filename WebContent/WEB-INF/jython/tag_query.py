# -*- coding: utf-8 -*-
from cn.edustar.jitar.data import BaseQuery

# 标签查询.
class TagQuery (BaseQuery):
  def __init__(self, selectFields):
    BaseQuery.__init__(self, selectFields)
    
    #标签标识, 缺省 = null, 表示不限定.
    self.tagId = None
    #标签名字, 缺省 = null , 表示不限定.
    self.tagName = None
    #是否获取被禁用的, 缺省 = 0,表示获取非禁用的.
    self.disabled = False
    #排序方式,缺省 =0, 表示按照Id 逆序排列. =1 ,按照 refCount逆序排序. =2,按照viewCount 逆序排列.
    self.orderType = 0
    
  def initFromEntities(self, qctx):
    qctx.addEntity("Tag" , "tag" , "");
    
  def resolveEntity(self, qctx, entity):
    BaseQuery.resolveEntity(self, qctx, entity);
    
  # 提供where 条件.
  def applyWhereCondition(self, qctx):
    if self.tagId != None:
      qctx.addAndWhere("tag.tagId = :tagId")
      qctx.setInteger("tagId", self.tagId)
    if self.tagName != None:
      qctx.addAndWhere("tag.tagName = :tagName")
      qctx.setString("tagName", self.tagNmae)
    if self.disabled != None:
      qctx.addAndWhere("tag.disabled = :disabled")
      qctx.setBoolean("disabled", self.disabled)
  
  # 提供排序 order 条件.
  def applyOrderCondition(self, qctx): 
    #排序方式, 0 = tagId DESC
    if self.orderType == 0:
      qctx.addOrder("tag.tagId DESC")
    elif self.orderType == 1:
      qctx.addOrder("tag.refCount DESC")
    elif self.orderType == 2:
      qctx.addOrder("tag.viewCount DESC")
      
class TagArticleQuery(BaseQuery):
  def __init__(self, selectFields):
    BaseQuery.__init__(self, selectFields)
    self.tagId = None
    self.objectType = 3
    self.auditState = 0
    self.hideState = 0
    self.draftState = False
    self.delState = False
    self.orderType = 0
    
  def initFromEntities(self, qctx):
    qctx.addEntity("TagRef" , "tr" , "");
    
  def resolveEntity(self, qctx, entity):    
    if "a" == entity:
      qctx.addEntity("Article", "a", "a.articleId = tr.objectId")
    else:
      BaseQuery.resolveEntity(self, qctx, entity);
  def applyWhereCondition(self, qctx):
    if self.tagId != None:
      qctx.addAndWhere("tr.tagId = :tagId")
      qctx.setInteger("tagId", self.tagId)
    if self.objectType != None:
      qctx.addAndWhere("tr.objectType = :objectType")
      qctx.setInteger("objectType", self.objectType)
    if self.auditState != None:
      qctx.addAndWhere("a.auditState = :auditState")
      qctx.setInteger("auditState", self.auditState)      
    if self.hideState != None:
      qctx.addAndWhere("a.hideState = :hideState")
      qctx.setInteger("hideState", self.hideState)
    if self.draftState != None:
      qctx.addAndWhere("a.draftState = :draftState")
      qctx.setBoolean("draftState", self.draftState)
    if self.delState != None:
      qctx.addAndWhere("a.delState = :delState")
      qctx.setBoolean("delState", self.delState)
  def applyOrderCondition(self, qctx): 
    if self.orderType == 0:
      qctx.addOrder("a.articleId DESC")
      
class TagUserQuery(BaseQuery):
  def __init__(self, selectFields):
    BaseQuery.__init__(self, selectFields)
    self.tagId = None
    self.objectType = 1
    self.userStatus = 0
    self.orderType = 0
    
  def initFromEntities(self, qctx):
    qctx.addEntity("TagRef" , "tr" , "");
    
  def resolveEntity(self, qctx, entity):    
    if "u" == entity:
      qctx.addEntity("User", "u", "u.userId = tr.objectId")
    else:
      BaseQuery.resolveEntity(self, qctx, entity);
  def applyWhereCondition(self, qctx):
    if self.tagId != None:
      qctx.addAndWhere("tr.tagId = :tagId")
      qctx.setInteger("tagId", self.tagId)
    if self.objectType != None:
      qctx.addAndWhere("tr.objectType = :objectType")
      qctx.setInteger("objectType", self.objectType)
    if self.userStatus != None:
      qctx.addAndWhere("u.userStatus = :userStatus")
      qctx.setInteger("userStatus", self.userStatus)
  def applyOrderCondition(self, qctx): 
    if self.orderType == 0:
      qctx.addOrder("u.userId DESC")

class TagGroupQuery(BaseQuery):
  def __init__(self, selectFields):
    BaseQuery.__init__(self, selectFields)
    self.tagId = None
    self.objectType = 2
    self.groupState = 0
    self.orderType = 0
    
  def initFromEntities(self, qctx):
    qctx.addEntity("TagRef" , "tr" , "");
    
  def resolveEntity(self, qctx, entity):    
    if "g" == entity:
      qctx.addEntity("Group", "g", "g.groupId = tr.objectId")
    else:
      BaseQuery.resolveEntity(self, qctx, entity);
  def applyWhereCondition(self, qctx):
    if self.tagId != None:
      qctx.addAndWhere("tr.tagId = :tagId")
      qctx.setInteger("tagId", self.tagId)
    if self.objectType != None:
      qctx.addAndWhere("tr.objectType = :objectType")
      qctx.setInteger("objectType", self.objectType)
    if self.groupState != None:
      qctx.addAndWhere("g.groupState = :groupState")
      qctx.setInteger("groupState", self.groupState)
  def applyOrderCondition(self, qctx): 
    if self.orderType == 0:
      qctx.addOrder("g.groupId DESC")
    
class TagResourceQuery(BaseQuery):
  def __init__(self, selectFields):
    BaseQuery.__init__(self, selectFields)
    self.tagId = None
    self.objectType = 12
    self.delState = False
    self.auditState = 0
    self.shareMode = 1000
    self.orderType = 0
    
  def initFromEntities(self, qctx):
    qctx.addEntity("TagRef" , "tr" , "");
    
  def resolveEntity(self, qctx, entity):    
    if "r" == entity:
      qctx.addEntity("Resource", "r", "r.resourceId = tr.objectId")
    else:
      BaseQuery.resolveEntity(self, qctx, entity);
  def applyWhereCondition(self, qctx):
    if self.tagId != None:
      qctx.addAndWhere("tr.tagId = :tagId")
      qctx.setInteger("tagId", self.tagId)
    if self.objectType != None:
      qctx.addAndWhere("tr.objectType = :objectType")
      qctx.setInteger("objectType", self.objectType)
    if self.auditState != None:
      qctx.addAndWhere("r.auditState = :auditState")
      qctx.setInteger("auditState", self.auditState)
    if self.delState != None:
      qctx.addAndWhere("r.delState = :delState")
      qctx.setBoolean("delState", self.delState)
    if self.shareMode != None:
      qctx.addAndWhere("r.shareMode >= :shareMode")
      qctx.setInteger("shareMode", self.shareMode)
  def applyOrderCondition(self, qctx): 
    if self.orderType == 0:
      qctx.addOrder("r.resourceId DESC")
      
class TagPrepareCourseQuery(BaseQuery):
  def __init__(self, selectFields):
    BaseQuery.__init__(self, selectFields)
    self.tagId = None
    self.objectType = 15
    self.status = 0
    self.orderType = 0
    
  def initFromEntities(self, qctx):
    qctx.addEntity("TagRef" , "tr" , "");
    
  def resolveEntity(self, qctx, entity):    
    if "pc" == entity:
      qctx.addEntity("PrepareCourse", "pc", "pc.prepareCourseId = tr.objectId")
    else:
      BaseQuery.resolveEntity(self, qctx, entity);
      
  def applyWhereCondition(self, qctx):
    if self.tagId != None:
      qctx.addAndWhere("tr.tagId = :tagId")
      qctx.setInteger("tagId", self.tagId)
    if self.objectType != None:
      qctx.addAndWhere("tr.objectType = :objectType")
      qctx.setInteger("objectType", self.objectType)
    if self.status != None:
      qctx.addAndWhere("pc.status = :status")
      qctx.setInteger("status", self.status)
      
  def applyOrderCondition(self, qctx): 
    if self.orderType == 0:
      qctx.addOrder("pc.prepareCourseId DESC")

class TagPhotoQuery(BaseQuery):
  def __init__(self, selectFields):
    BaseQuery.__init__(self, selectFields)
    self.tagId = None
    self.objectType = 11
    self.delState = False
    self.isPrivateShow = False
    self.orderType = 0
    
  def initFromEntities(self, qctx):
    qctx.addEntity("TagRef" , "tr" , "");
    
  def resolveEntity(self, qctx, entity):    
    if "p" == entity:
      qctx.addEntity("Photo", "p", "p.photoId = tr.objectId")
    else:
      BaseQuery.resolveEntity(self, qctx, entity);
      
  def applyWhereCondition(self, qctx):
    if self.tagId != None:
      qctx.addAndWhere("tr.tagId = :tagId")
      qctx.setInteger("tagId", self.tagId)
    if self.objectType != None:
      qctx.addAndWhere("tr.objectType = :objectType")
      qctx.setInteger("objectType", self.objectType)
    if self.isPrivateShow != None:
      qctx.addAndWhere("p.isPrivateShow = :isPrivateShow")
      qctx.setBoolean("isPrivateShow", self.isPrivateShow)
    if self.isPrivateShow != None:
      qctx.addAndWhere("p.delState = :delState")
      qctx.setBoolean("delState", self.delState)
      
  def applyOrderCondition(self, qctx): 
    if self.orderType == 0:
      qctx.addOrder("p.photoId DESC")