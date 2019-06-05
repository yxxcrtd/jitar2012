# -*- coding: utf-8 -*-
from java.net import URLDecoder
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import User
from cn.edustar.jitar.model import ObjectType
from cn.edustar.jitar.service import PageKey
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.data import BaseQuery
from base_action import *

class VoteQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.parentGuid = None
        self.parentObjectType = None       
        self.createUserId = None
        self.orderType = 0
        self.k = self.params.getStringParam("k")
        self.subjectId = self.params.getIntParamZeroAsNull("subjectId")
        self.gradeId = self.params.getIntParamZeroAsNull("gradeId")
        request.setAttribute("subjectId", self.subjectId)
        request.setAttribute("gradeId", self.gradeId)
        request.setAttribute("k", self.k)        
        
    def initFromEntities(self, qctx):
        qctx.addEntity("Vote", "vote", "")
             
    def resolveEntity(self, qctx, entity):
        BaseQuery.resolveEntity(qctx, entity);
                   
    def applyWhereCondition(self, qctx):
        if (self.parentGuid != None):
            qctx.addAndWhere("vote.parentGuid = :parentGuid")
            qctx.setString("parentGuid", self.parentGuid)
        if (self.parentObjectType != None):
            qctx.addAndWhere("vote.parentObjectType = :parentObjectType")
            qctx.setString("parentObjectType", self.parentObjectType)
        if (self.createUserId != None):
            qctx.addAndWhere("vote.createUserId = :createUserId")
            qctx.setInteger("createUserId", self.createUserId)
            
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("vote.voteId DESC")

class UserVoteQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.parentGuid = None
        self.parentObjectType = None       
        self.userId = None
        self.orderType = 0

    def initFromEntities(self, qctx):
        qctx.addEntity("VoteUser", "vu", "")
             
    def resolveEntity(self, qctx, entity):
        if "vote" == entity:
            qctx.addEntity("Vote", "vote", "vote.voteId = vu.voteId")       
        else :
            BaseQuery.resolveEntity(self, qctx, entity);
                   
    def applyWhereCondition(self, qctx):
        if self.parentGuid != None:
            qctx.addAndWhere("vote.parentGuid = :parentGuid")
            qctx.setString("parentGuid", self.parentGuid)
        if self.parentObjectType != None:
            qctx.addAndWhere("vote.parentObjectType = :parentObjectType")
            qctx.setString("parentObjectType", self.parentObjectType)
        if self.userId != None:
            qctx.addAndWhere("vu.userId = :userId")
            qctx.setInteger("userId", self.userId)
            
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("vu.voteUserId DESC")    