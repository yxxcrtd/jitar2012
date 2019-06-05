from cn.edustar.jitar.data import BaseQuery
from cn.edustar.jitar.pojos import Grade
from cn.edustar.jitar.pojos import Subject
from cn.edustar.jitar.pojos import MetaSubject

class SubjectQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)       
        self.orderType = 0
        
    def initFromEntities(self, qctx):
        qctx.addEntity("Subject", "subj", "")
    
    def resolveEntity(self, qctx, entity):
        if "msubj" == entity:
            qctx.addEntity("MetaSubject","msubj", "subj.subjectId = msubj.msubjId")
        elif ("grad" == entity):
            qctx.addJoinEntity("subj", "subj.gradeId", "grad", "LEFT JOIN")
        else:
            BaseQuery.resolveEntity(self, qctx, entity);


    def applyOrderCondition(self, qctx): 
        if self.orderType == 0:
            qctx.addOrder("grad.gradeId DESC")
        
   