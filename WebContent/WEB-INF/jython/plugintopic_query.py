from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import User
from cn.edustar.jitar.model import ObjectType
from cn.edustar.jitar.service import PageKey
from cn.edustar.jitar.util import CommonUtil
from base_action import BaseAction
from cn.edustar.jitar.data import BaseQuery
from base_action import *

class PlugInTopicQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.parentGuid = None
        self.parentObjectType = None       
        self.createUserId = None
        self.orderType = 0
        self.k = self.params.getStringParam("k")
        request.setAttribute("k", self.k)        
        
    def initFromEntities(self, qctx):
        qctx.addEntity("PlugInTopic", "pt", "")
             
    def resolveEntity(self, qctx, entity):
        BaseQuery.resolveEntity(self, qctx, entity)
                   
    def applyWhereCondition(self, qctx):
        if (self.parentGuid != None):
            qctx.addAndWhere("pt.parentGuid = :parentGuid")
            qctx.setString("parentGuid", self.parentGuid)
        if (self.parentObjectType != None):
            qctx.addAndWhere("pt.parentObjectType = :parentObjectType")
            qctx.setString("parentObjectType", self.parentObjectType)
        if (self.createUserId != None):
            qctx.addAndWhere("pt.createUserId = :createUserId")
            qctx.setInteger("createUserId", self.createUserId)
            
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("pt.plugInTopicId DESC")            

class PlugInTopicReplyQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.plugInTopicId = None
        self.createUserId = None
        self.orderType = 1
        
    def initFromEntities(self, qctx):
        qctx.addEntity("PlugInTopicReply", "ptr", "")        
             
    def resolveEntity(self, qctx, entity):
        if "pt" == entity:
            qctx.addEntity("PlugInTopic", "pt", "pt.plugInTopicId = ptr.plugInTopicId")
        else:
            BaseQuery.resolveEntity(self, qctx, entity)
                   
    def applyWhereCondition(self, qctx):
        if (self.plugInTopicId != None):
            qctx.addAndWhere("ptr.plugInTopicId = :plugInTopicId")
            qctx.setInteger("plugInTopicId", self.plugInTopicId)
        if (self.createUserId != None):
            qctx.addAndWhere("ptr.createUserId = :createUserId")
            qctx.setInteger("createUserId", self.createUserId)        
            
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("ptr.plugInTopicReplyId DESC")
            
        if self.orderType == 1:
            qctx.addOrder("ptr.plugInTopicReplyId ASC")
