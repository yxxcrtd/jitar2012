# -*- coding: utf-8 -*-
from cn.edustar.jitar.data import BaseQuery

# 分类查询
class CategoryQuery (BaseQuery):
    def __init__(self, selectFields):
      BaseQuery.__init__(self, selectFields)
      #分类的父分类, 缺省 = null
      self.parentId = None
      
      #分类的类型　缺省是文章分类
      self.itemType = 'default'
      
      
    def initFromEntities(self, qctx):
      qctx.addEntity("Category" , "cat" , "")
    
    def resolveEntity(self, qctx, entity):
        BaseQuery.resolveEntity(self, qctx, entity)
      
    # 提供 where 条件.
    def applyWhereCondition(self,qctx):
      if self.parentId == None:
        qctx.addAndWhere("cat.parentId = NULL")
      elif self.parentId == 0:
        qctx.addAndWhere("cat.parentId = NULL")
      else:
        qctx.addAndWhere("cat.parentId = :parentId")
        qctx.setInteger("parentId", self.parentId)
        
      if self.itemType != None:
        qctx.addAndWhere("cat.itemType = :itemType")
        qctx.setString("itemType", self.itemType)
        
    # 提供排序 order 条件.
    def applyOrderCondition(self, qctx):
      # 排序方式, 0 - commentId DESC, 1 - createDate
        qctx.addOrder("cat.orderNum ASC") 
      