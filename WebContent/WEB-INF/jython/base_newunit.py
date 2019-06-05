from cn.edustar.jitar.data import BaseQuery

class NewUnitQuery(BaseQuery):
    
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.pathInfoType = None
        self.unitPathInfo = None        
        self.parentId = None
        self.k = None
        self.orderType = 0
        self.isTry = None #只用来进行测试
        
        
    def initFromEntities(self, qctx):
        qctx.addEntity("NewUnit", "newunit", "")
    
    def resolveEntity(self, qctx, entity):
        BaseQuery.resolveEntity(self, qctx, entity);
        
    def applyWhereCondition(self, qctx):
        if self.unitPathInfo != None:
            qctx.addAndWhere("newunit.unitPathInfo LIKE :unitPathInfo")
            qctx.setString("unitPathInfo", "%" + self.unitPathInfo + "%")
    
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("newunit.newUnitId DESC")
            
            
class NewArticleQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.pathInfoType = None
        self.unit = None
        self.k = None
        self.orderType = 0
        self.isTry = None #只用来进行测试
        
    def initFromEntities(self, qctx):
        qctx.addEntity("NewArticle", "newArticle", "")
    
    def resolveEntity(self, qctx, entity):
        BaseQuery.resolveEntity(self, qctx, entity);
        
    def applyWhereCondition(self, qctx):
        if self.isTry != None:
            qctx.addAndWhere("charindex(:unitPathInfo,newArticle.unitPathInfo) =1")
            qctx.setString("unitPathInfo", self.unitPathInfo)
            return
        
        
        if self.unit != None:                
            if self.pathInfoType == None:                
                qctx.addAndWhere("newArticle.unitPathInfo LIKe :unitPathInfo")
                qctx.setString("unitPathInfo", "%" + self.unit.unitPathInfo + "%")
            elif self.pathInfoType == "push":
                #下级推送到本机构的
                qctx.addAndWhere("newArticle.unitPathInfo LIKe :unitPathInfo and unitId <> :unitId")
                qctx.setString("unitPathInfo", "%/" + str(self.unit.newUnitId) + "/%")
                qctx.setInteger("unitId", self.unit.newUnitId)
            elif self.pathInfoType == "owner":
                #下级推送到本机构的
                qctx.addAndWhere("newArticle.unitId = :unitId")
                qctx.setInteger("unitId", self.unit.newUnitId)
            else:
                qctx.addAndWhere("newArticle.unitPathInfo LIKe :unitPathInfo")
                qctx.setString("unitPathInfo", "%" + self.unit.unitPathInfo + "%")
                
    
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("newArticle.articleId DESC")

class NewArticleQuery2(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.pathInfoType = None
        self.unit = None
        self.k = None
        self.orderType = 0
        self.isTry = None #只用来进行测试
        
    def initFromEntities(self, qctx):
        qctx.addEntity("NewArticle", "newArticle", "")
    
    def resolveEntity(self, qctx, entity):
        BaseQuery.resolveEntity(self, qctx, entity);
        
    def applyWhereCondition(self, qctx):
        if self.isTry != None:
            qctx.addAndWhere("charindex(:unitPathInfo,newArticle.unitPathInfo) =1")
            qctx.setString("unitPathInfo", self.unitPathInfo)
            return        
        
        if self.unit != None:                
            if self.pathInfoType == None:
                #不过滤，只要是包含在本机构的都算
                qctx.addAndWhere("newArticle.unitPathInfo LIKe :unitPathInfo")
                qctx.setString("unitPathInfo", "%/" + str(self.unit.newUnitId) + "/%")
                if self.approvePath != None:
                    qctx.addAndWhere("newArticle.approvePath LIKe :approvePath")
                    qctx.setString("approvePath", "%/" + self.approvePath + "/%")
                    
            elif self.pathInfoType == "push":
                #下级推送到本机构的
                qctx.addAndWhere("newArticle.unitPathInfo LIKe :unitPathInfo and unitId <> :unitId")
                qctx.setString("unitPathInfo", "%/" + str(self.unit.newUnitId) + "/%")
                qctx.setInteger("unitId", self.unit.newUnitId)
            elif self.pathInfoType == "owner":
                #得到自己机构的文章
                qctx.addAndWhere("newArticle.unitId = :unitId")
                qctx.setInteger("unitId", self.unit.newUnitId)
            else:
                qctx.addAndWhere("newArticle.unitPathInfo LIKe :unitPathInfo")
                qctx.setString("unitPathInfo", "%" + self.unit.unitPathInfo + "%")
                
    
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("newArticle.articleId DESC")
            
class NewUserQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.pathInfoType = None
        self.unitPathInfo = None
        self.k = None
        self.orderType = 0
        
    def initFromEntities(self, qctx):
        qctx.addEntity("NewUser", "newUser", "")
    
    def resolveEntity(self, qctx, entity):
        BaseQuery.resolveEntity(self, qctx, entity);
        
    def applyWhereCondition(self, qctx):
        if self.unitPathInfo != None:
            qctx.addAndWhere("newUser.unitPathInfo LIKE :unitPathInfo")
            qctx.setString("unitPathInfo", "%" + self.unitPathInfo + "%")
    
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("newUser.newUserId DESC")
