from cn.edustar.jitar.data import BaseQuery
from cn.edustar.jitar.util import ParamUtil
from base_action import *

class ActionUserUnitQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        
        self.orderType = 0
        self.params = ParamUtil(request)
        self.actionId = None
        self.actionUserId = None
        self.userId = None
        
    def initFromEntities(self, qctx):
        qctx.addEntity("ActionUserUnit", "actu", "")
        
    def resolveEntity(self, qctx, entity):
        if "u" == entity:
            qctx.addEntity("User", "u", "actu.userId = u.userId")       
        else :
            BaseQuery.resolveEntity(self, qctx, entity);
            
    def applyWhereCondition(self, qctx):
        if (self.actionId != None):
            qctx.addAndWhere("actu.actionId = :actionId")
            qctx.setInteger("actionId", self.actionId)
        if (self.userId != None):
            qctx.addAndWhere("actu.userId = :userId")
            qctx.setInteger("userId", self.userId)
        
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("actu.actionUserId DESC")
        elif self.orderType == 1:
            qctx.addOrder("actu.createDate DESC")
