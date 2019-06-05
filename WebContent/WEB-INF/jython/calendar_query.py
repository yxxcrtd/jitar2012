from cn.edustar.jitar.data import BaseQuery

class CalendarQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.objectGuid = None
        self.objectType = None
        self.orderType = 0
    
    def initFromEntities(self, qctx):
        qctx.addEntity("Calendar" , "c", "")
  
    def resolveEntity(self, qctx, entity):
        BaseQuery.resolveEntity(self, qctx, entity);

    def applyWhereCondition(self , qctx):
        if self.objectGuid != None:
            qctx.addAndWhere("c.objectGuid = :objectGuid")
            qctx.setString("objectGuid", self.objectGuid)
        if self.objectType != None:
            qctx.addAndWhere("c.objectType = :objectType")
            qctx.setString("objectType", self.objectType)
    
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("c.eventTimeBegin DESC")