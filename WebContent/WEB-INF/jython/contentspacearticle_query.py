from cn.edustar.jitar.data import BaseQuery
from base_action import *

class ContentSpaceArticleQuery (BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.hasPicture = None
        self.contentSpaceId = None
        self.ownerType = None
        self.ownerId = None
        self.orderType = 0
        self.k = self.params.getStringParam("k")
        self.f = self.params.getStringParam("f")
        if self.f == None or self.f == "":
            self.f = "title"
        request.setAttribute("k", self.k)
        request.setAttribute("f", self.f)
    
    def initFromEntities(self, qctx):        
        qctx.addEntity("ContentSpaceArticle", "csa", "")
    
    def resolveEntity(self, qctx, entity):
        if "cs" == entity:
            qctx.addEntity("ContentSpace", "cs", "csa.contentSpaceId = cs.contentSpaceId")
        else:
            BaseQuery.resolveEntity(self, qctx, entity);

    def applyWhereCondition(self, qctx):
        if self.contentSpaceId != None:
            qctx.addAndWhere("(cs.contentSpaceId = :contentSpaceId OR cs.parentPath LIKE '%/"+str(self.contentSpaceId)+"/%')")
            qctx.setInteger("contentSpaceId", self.contentSpaceId)
        if self.ownerType != None:
            qctx.addAndWhere("cs.ownerType = :ownerType")
            qctx.setInteger("ownerType", self.ownerType)
        if self.ownerId != None:
            qctx.addAndWhere("cs.ownerId = :ownerId")
            qctx.setInteger("ownerId", self.ownerId)
        if self.hasPicture != None:
            if self.hasPicture == True:
                qctx.addAndWhere("cs.pictureUrl IS NOT NULL AND cs.pictureUrl <> ''")
            else:
                qctx.addAndWhere("cs.pictureUrl IS NULL OR cs.pictureUrl = ''")
            
            
        if self.k != None and self.f != None:
            newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
            if self.f == "createUserId":
                if self.k.isdigit():
                    qctx.addAndWhere("csa.createUserId = :createUserId")
                    qctx.setInteger("createUserId", int(newKey))
            else:
                qctx.addAndWhere("csa." + self.f + " LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
                
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:  
            qctx.addOrder("csa.contentSpaceArticleId DESC")
