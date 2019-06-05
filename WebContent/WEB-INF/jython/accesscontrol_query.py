from cn.edustar.jitar.data import BaseQuery
from base_action import *

class AccessControlQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.userId = None
        self.objectType = self.params.getIntParamZeroAsNull("objectType")
        self.objectId = None        
        self.orderType = 0
        self.custormAndWhere = None
        
        self.k = self.params.getStringParam("k")
        self.f = self.params.getStringParam("f")
        if self.f == None or self.f == "":
            self.f = "trueName"
      
        request.setAttribute("objectType", self.objectType)
        request.setAttribute("f", self.f)
        request.setAttribute("k", self.k)
        
    def initFromEntities(self, qctx):
        qctx.addEntity("AccessControl", "ac", "")
        
    def resolveEntity(self, qctx, entity):
        if "u" == entity:
            qctx.addEntity("User", "u", "ac.userId = u.userId")
        else:
            BaseQuery.resolveEntity(self, qctx, entity) 
            
    def applyWhereCondition(self, qctx):
        if self.userId != None:
            qctx.addAndWhere("ac.userId = :userId")
            qctx.setInteger("userId", self.userId)
        if self.objectType != None:
            qctx.addAndWhere("ac.objectType = :objectType")
            qctx.setInteger("objectType", self.objectType)
        if self.objectId != None:
            qctx.addAndWhere("ac.objectId = :objectId")
            qctx.setInteger("objectId", self.objectId)      
        if self.custormAndWhere != None:
            qctx.addAndWhere(self.custormAndWhere)
        if self.k != None and self.k != "":
            newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
            if self.f == "loginName":
                qctx.addAndWhere("u.loginName LIKE :keyWord")
            else:
                qctx.addAndWhere("u.trueName LIKE :keyWord")
            qctx.setString("keyWord", "%" + newKey + "%")
                
    
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("ac.accessControlId DESC")
        elif self.orderType == 1:
            qctx.addOrder("ac.userId ASC")
