# -*- coding: utf-8 -*-
from cn.edustar.jitar.data import BaseQuery
from cn.edustar.jitar.util import ParamUtil

class SpecialSubjectArticleQuery (BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.specialSubjectId = None
        self.orderType = 0
        self.articleState = True
        self.k = self.params.getStringParam("k")
        self.f = self.params.getStringParam("f")
        if self.f == None:
            self.f = "title"
        if self.f == "":
            self.f = "title"
            
        request.setAttribute("k", self.k)
        request.setAttribute("f", self.f)
        
    def initFromEntities(self, qctx):
        qctx.addEntity("SpecialSubjectArticle", "ssa", "")
        
    def resolveEntity(self, qctx, entity):
        BaseQuery.resolveEntity(self,qctx, entity)
    
    def applyWhereCondition(self, qctx):
        if self.specialSubjectId != None:
            qctx.addAndWhere("ssa.specialSubjectId = :specialSubjectId")
            qctx.setInteger("specialSubjectId", self.specialSubjectId)
        if self.articleState != None:
            qctx.addAndWhere("ssa.articleState = :articleState")
            qctx.setBoolean("articleState", self.articleState)
        if self.k != None and self.k != '':
            newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
            if self.f == "title":
                qctx.addAndWhere("ssa.title LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            elif self.f == "uname":
                qctx.addAndWhere("ssa.loginName LIKE :keyword OR ssa.userTrueName LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            else:
                qctx.addAndWhere("ssa.title LIKE :keyword OR ssa.loginName LIKE :keyword OR ssa.loginName LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
    
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("ssa.specialSubjectArticleId DESC")