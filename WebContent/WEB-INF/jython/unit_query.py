# coding=utf-8
from cn.edustar.jitar.data import BaseQuery

# 机构查询.
class UnitQuery(BaseQuery):
  def __init__(self, selectFields):
    BaseQuery.__init__(self, selectFields)
    self.parentId = None
    self.hasChild = None    
    self.k = None
    self.unitName = None

  def initFromEntities(self, qctx):
    qctx.addEntity("Unit", "unit", "")
    
    
  def resolveEntity(self, qctx, entity):
    BaseQuery.resolveEntity(self, qctx, entity)

  def applyWhereCondition(self, qctx):
    if self.k != None and self.k != '':
      self._applyKeywordFilter(qctx)
    if self.parentId != None:
      qctx.addAndWhere("unit.parentId = :parentId")
      qctx.setInteger("parentId", self.parentId)   
    if self.hasChild != None:
      qctx.addAndWhere("unit.hasChild = :hasChild")
      qctx.setBoolean("hasChild", self.hasChild)  

  def _applyKeywordFilter(self, qctx):
    newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
    qctx.addAndWhere("(unit.unitName LIKE :likeKey) OR (unit.unitType LIKE :likeKey)")
    qctx.setString("likeKey", "%" + newKey + "%")