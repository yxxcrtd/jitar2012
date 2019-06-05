from cn.edustar.jitar.data import BaseQuery
from cn.edustar.jitar.util import ParamUtil

class ActionReplyQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        
        self.orderType = 0
        self.params = ParamUtil(request)
        self.actionId = None
        self.userId = None
        
    def initFromEntities(self, qctx):
        qctx.addEntity("ActionReply", "actr", "")
        
    def resolveEntity(self, qctx, entity):
        if "u" == entity:
            qctx.addEntity("User", "u", "actr.userId = u.userId")       
        else :
            BaseQuery.resolveEntity(self, qctx, entity);
            
    def applyWhereCondition(self, qctx):
        if (self.actionId != None):
            qctx.addAndWhere("actr.actionId = :actionId")
            qctx.setInteger("actionId", self.actionId)
        if (self.userId != None):
            qctx.addAndWhere("actr.userId = :userId")
            qctx.setInteger("userId", self.userId)
        
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("actr.actionReplyId ASC")