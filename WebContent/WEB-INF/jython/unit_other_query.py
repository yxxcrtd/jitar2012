from cn.edustar.jitar.data import BaseQuery
from cn.edustar.jitar.pojos import UnitGroup

class UnitGroupQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)       
        self.orderType = 0
        self.unitId = None        
        
    def initFromEntities(self, qctx):
        qctx.addEntity("UnitGroup", "ug", "")
    
    def resolveEntity(self, qctx, entity):
        BaseQuery.resolveEntity(self, qctx, entity)
    def applyWhereCondition(self, qctx):
        if self.unitId != None:
            qctx.addAndWhere("ug.unitId = :unitId")
            qctx.setInteger("unitId", self.unitId)
         
    def applyOrderCondition(self, qctx): 
        if self.orderType == 0:
            qctx.addOrder("ug.groupId DESC")

class UnitPrepareCourseQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)       
        self.orderType = 0
        self.unitId = None        
        
    def initFromEntities(self, qctx):
        qctx.addEntity("PrepareCourse", "pc", "")
    
    def resolveEntity(self, qctx, entity):
        if "u" == entity:
            qctx.addEntity("User", "u", "pc.leaderId = u.userId")
        else:
            BaseQuery.resolveEntity(self, qctx, entity)
            
    def applyWhereCondition(self, qctx):
        qctx.addAndWhere("pc.status = 0")
        if self.unitId != None:
            qctx.addAndWhere("u.unitId = :unitId")
            qctx.setInteger("unitId", self.unitId)
         
    def applyOrderCondition(self, qctx): 
        if self.orderType == 0:
            qctx.addOrder("pc.prepareCourseId DESC")
