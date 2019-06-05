# -*- coding: utf-8 -*-
from cn.edustar.jitar.data import BaseQuery
from cn.edustar.jitar.util import ParamUtil
from java.util import Calendar,Date
from java.text import SimpleDateFormat

class ActionQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.orderType = 0        
        self.createUserId = None
        self.ownerType = None
        self.ownerId = None
        self.status = None
        self.qryDate = None
        self.k = self.params.getStringParam("k")
        self.filter = self.params.getStringParam("filter")        
       
        sft = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")      
        self.nowDate = sft.format(Date())
                
    def initFromEntities(self, qctx):
        qctx.addEntity("Action", "act", "")
        
    def resolveEntity(self, qctx, entity):
        if "u" == entity:
            qctx.addEntity("User", "u", "act.createUserId = u.userId")
        else :
            BaseQuery.resolveEntity(self, qctx, entity);
            
    def applyWhereCondition(self, qctx):
        if (self.createUserId != None):
            qctx.addAndWhere("act.createUserId = :createUserId")
            qctx.setInteger("createUserId", self.createUserId)
               
        if (self.ownerType != None):
            qctx.addAndWhere("act.ownerType = :ownerType")
            qctx.setString("ownerType", self.ownerType)
            
        if (self.status != None):
            qctx.addAndWhere("act.status = :status")
            qctx.setInteger("status", self.status)
        
        if (self.ownerId != None):            
            qctx.addAndWhere("act.ownerId = :ownerId")
            qctx.setInteger("ownerId", self.ownerId)
            
        if self.qryDate != None:
            if self.qryDate == 0: #正在报名的活动
                qctx.addAndWhere("(:nowDate < act.attendLimitDateTime)")
                qctx.setString("nowDate", self.nowDate)
            if self.qryDate == 1: #正在进行的活动
                #print "debug =======",self.nowDate
                qctx.addAndWhere("(:nowDate >= act.startDateTime) And (:nowDate <= act.finishDateTime)")
                qctx.setString("nowDate", self.nowDate) 
                qctx.setString("nowDate", self.nowDate) 
            if self.qryDate == 2: #已经完成的活动
                qctx.addAndWhere("(:nowDate > act.finishDateTime)")
                qctx.setString("nowDate", self.nowDate)
            
        if self.k != None and self.k != "":
            newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
            if self.filter == None or self.filter == "":
                self.filter = "title"
            # wk，难道不能简写!!qctx.addAndWhere(":filter LIKE :keyword")
            if self.filter == "title":
                qctx.addAndWhere("act.title LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            elif self.filter == "description":
                qctx.addAndWhere("act.description LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            elif self.filter == "place":
                qctx.addAndWhere("act.place LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            elif self.filter == "loginName" :
                qctx.addAndWhere("u.loginName LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            elif self.filter == "trueName" :
                qctx.addAndWhere("u.trueName LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
                
        
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("act.actionId DESC")
        elif self.orderType == 1:
            qctx.addOrder("act.createDate DESC")