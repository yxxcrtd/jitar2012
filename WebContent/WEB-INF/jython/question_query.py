from cn.edustar.jitar.data import BaseQuery

class QuestionQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.questionId = None
        self.parentGuid = None
        self.createUserId = None
        self.orderType = 0
    
    def initFromEntities(self, qctx):
        qctx.addEntity("Question" , "q", "")
  
    def resolveEntity(self, qctx, entity):
        BaseQuery.resolveEntity(self, qctx, entity);

    def applyWhereCondition(self , qctx):
        if self.parentGuid != None:
            qctx.addAndWhere("q.parentGuid = :parentGuid")
            qctx.setString("parentGuid", self.parentGuid)
        if self.createUserId != None:
            qctx.addAndWhere("q.createUserId = :createUserId")
            qctx.setInteger("createUserId", self.createUserId)
    
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("q.questionId DESC")