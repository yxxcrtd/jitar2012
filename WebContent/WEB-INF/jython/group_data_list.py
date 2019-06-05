# coding=utf-8
from cn.edustar.jitar.data import BaseQuery
from base_action import * 

class GroupDataQueryList(BaseQuery):
    ORDER_BY_ID_DESC = 0      # id DESC
    ORDER_BY_ID_ASC = 1       # id ASC
    
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.objectGuid = None    
        self.orderType = 1
        
    def initFromEntities(self, qctx):
        qctx.addEntity("GroupDataQuery", "gdq", "")
    
    def resolveEntity(self, qctx, entity):
        BaseQuery.resolveEntity(self, qctx, entity)
        

    def applyWhereCondition(self, qctx):
        if self.objectGuid != None:
            qctx.addAndWhere("gdq.objectGuid = :objectGuid")
            qctx.setString("objectGuid", self.objectGuid)
    

    def applyOrderCondition(self, qctx):
        if self.orderType == GroupDataQueryList.ORDER_BY_ID_DESC:
            qctx.addOrder("gdq.id DESC")
        elif self.orderType == GroupDataQueryList.ORDER_BY_ID_ASC:
            qctx.addOrder("gdq.id ASC")