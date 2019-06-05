from cn.edustar.jitar.data import BaseQuery

class JitarColumnNewsQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)        
        self.columnId = None
        self.userId = None
        self.hasPicture = None
        self.k = None
        self.orderType = 0
        
    def initFromEntities(self, qctx):
        qctx.addEntity("JitarColumnNews" , "jcn", "")
        
    def resovleEntity(self, qctx, entity):
        BaseQuery.resolveEntity(self, qctx, entity)
        
    def applyWhereCondition(self, qctx):
        if self.columnId != None:
            qctx.addAndWhere("jcn.columnId = :columnId")
            qctx.setInteger("columnId", self.columnId)
        if self.userId != None:
            qctx.addAndWhere("jcn.userId = :userId")
            qctx.setInteger("userId", self.userId)
        if self.hasPicture != None:
            if self.hasPicture == True:
                qctx.addAndWhere("jcn.picture IS NOT NULL AND jcn.picture <> ''")
            else:
                qctx.addAndWhere("jcn.picture IS NULL OR jcn.picture = ''")
        if self.k != None:
            newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
            qctx.addAndWhere("(jcn.title LIKE :likeKey)")
            qctx.setString("likeKey", "%" + newKey + "%")
      
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("jcn.columnNewsId DESC")    
