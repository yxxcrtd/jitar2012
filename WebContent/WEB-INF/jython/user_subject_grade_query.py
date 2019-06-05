# -*- coding: utf-8 -*-
from cn.edustar.jitar.data import BaseQuery
from base_action import SubjectMixiner

class UserSubjectGradeQuery(BaseQuery, SubjectMixiner):
    def __init__(self, selectFields):        
        BaseQuery.__init__(self, selectFields)
        self.subjectId = None
        self.gradeId = None
        self.userId = None
        self.FuzzyMatch = None
        self.orderType = 0
        
    def initFromEntities(self, qctx):
        qctx.addEntity("UserSubjectGrade" , "usg", "")
    
    def resovleEntity(self, qctx, entity):
        BaseQuery.resolveEntity(self, qctx, entity)
    
    def applyWhereCondition(self, qctx):
        if self.subjectId != None:
            qctx.addAndWhere("usg.subjectId = :subjectId")
            qctx.setInteger("subjectId", self.subjectId)
         
        if self.gradeId != None:
            if self.FuzzyMatch == None or self.FuzzyMatch == False:           
                qctx.addAndWhere("usg.gradeId = :gradeId")
                qctx.setInteger("gradeId", self.gradeId)
            else:
                qctx.addAndWhere("usg.gradeId >= :gradeStartId And usg.gradeId < :gradeEndId")
                qctx.setInteger("gradeStartId", self.calcGradeStartId(self.gradeId))
                qctx.setInteger("gradeEndId", self.calcGradeEndId(self.gradeId))
                
        if self.userId != None:
            qctx.addAndWhere("usg.userId = :userId")
            qctx.setInteger("userId", self.userId)
            
    def applyOrderCondition(self, qctx):
        # 排序方式, 0 - placardId DESC
        if self.orderType == 0:
            qctx.addOrder("usg.userSubjectGradeId DESC")