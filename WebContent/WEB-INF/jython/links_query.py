# -*- coding: utf-8 -*-
from cn.edustar.jitar.data import BaseQuery

# 公告查询
class LinksQuery(BaseQuery):
  def __init__(self, selectFields):
    BaseQuery.__init__(self, selectFields)
    self.objectType = None    self.objectId = None    self.linkType = None
    self.orderType = 0
  
  def initFromEntities(self, qctx):
    qctx.addEntity("Link" , "lnk", "")
  
  def resovleEntity(self, qctx, entity):
    BaseQuery.resolveEntity(self, qctx, entity);
  
  def applyWhereCondition(self, qctx):
    if self.objectType != None:
      qctx.addAndWhere("lnk.objectType = :objectType")
      qctx.setInteger("objectType", self.objectType)
    if self.objectId != None:
      qctx.addAndWhere("lnk.objectId = :objectId")
      qctx.setInteger("objectId", self.objectId)  
    if self.linkType != None:
      qctx.addAndWhere("lnk.linkType = :linkType")
      qctx.setInteger("linkType", self.linkType)
  def applyOrderCondition(self, qctx):
    # 排序方式, 0 - placardId DESC
    if self.orderType == 0:
      qctx.addOrder("lnk.linkId DESC")
    elif self.orderType == 1:
      qctx.addOrder("lnk.title DESC")