# -*- coding: utf-8 -*-
from cn.edustar.jitar.data import BaseQuery

# 公告查询
class PlacardQuery(BaseQuery):
  def __init__(self, selectFields):
    BaseQuery.__init__(self, selectFields)
    
    # 要查找的公告的对象类型, 缺省 = null.
    self.objType = None
    # 要查找的公告的所属对象标识, 缺省 = null 表示不限制.    self.objId = None
    #是否是某分类下的（例如 课题组的)
    self.groupCateId = None
    # 是否查询隐藏的, 缺省 = false 表示查询非隐藏的; = null 表示不限制; = true 表示查询隐藏的.    self.hideState = False
    
    # 排序方式, 缺省 = 0 表示按照 id 逆序排列.
    self.orderType = 0
  
  def initFromEntities(self, qctx):
    qctx.addEntity("Placard" , "pld", "")
    if self.groupCateId!=None:
      qctx.addEntity("Group", "g", "g.groupId=pld.objId")
      
  def resovleEntity(self, qctx, entity):
    BaseQuery.resolveEntity(self, qctx, entity);
  
  def applyWhereCondition(self, qctx):
    if self.objType != None:
      qctx.addAndWhere("pld.objType = :objType")
      qctx.setInteger("objType", self.objType)
    if self.objId != None:
      qctx.addAndWhere("pld.objId = :objId")
      qctx.setInteger("objId", self.objId)  
    if self.hideState != None:
      qctx.addAndWhere("pld.hide = :hideState")
      qctx.setBoolean("hideState", self.hideState)
    if self.groupCateId!=None:
      #qctx.addAndWhere("pld.objId IN (SELECT groupId FROM cn.edustar.jitar.pojos.Group WHERE categoryId=:categoryId )")
      qctx.addAndWhere("g.categoryId=:categoryId")
      qctx.setInteger("categoryId", self.groupCateId)
      
  def applyOrderCondition(self, qctx):
    # 排序方式, 0 - placardId DESC
    if self.orderType == 0:
      qctx.addOrder("pld.id DESC")
    elif self.orderType == 1:
      qctx.addOrder("pld.title DESC")
  
  