from cn.edustar.jitar.data import BaseQuery
from cn.edustar.jitar.util import ParamUtil

class MashupContentQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        
        self.params = ParamUtil(request)
        self.orderType = 0        
        self.documentType = None
        self.author = None
        self.unitName = None
        self.unitTitle = None
        self.mashupContentState = 0
        self.pushUserName = None
        self.platformName = None
        self.platformGuid = None
        
        self.k = self.params.getStringParam("k")
        self.f = self.params.getStringParam("f")
        request.setAttribute("k", self.k)
        request.setAttribute("f", self.f)
        
    def initFromEntities(self, qctx):
        qctx.addEntity("MashupContent", "mc", "")
        
    def resolveEntity(self, qctx, entity):
        BaseQuery.resolveEntity(self, qctx, entity);
        
    def applyWhereCondition(self, qctx):
        if self.documentType != None:
            qctx.addAndWhere("mc.documentType = :documentType")
            qctx.setString("documentType", self.documentType)
               
        if self.author != None:
            qctx.addAndWhere("mc.author = :author")
            qctx.setString("author", self.author)
            
        if self.unitName != None:
            qctx.addAndWhere("mc.unitName = :unitName")
            qctx.setString("unitName", self.unitName)
        
        if self.unitTitle != None:
            qctx.addAndWhere("mc.unitTitle = :unitTitle")
            qctx.setString("unitTitle", self.unitTitle)
            
        if self.platformName != None:
            qctx.addAndWhere("mc.platformName = :platformName")
            qctx.setString("platformName", self.platformName)
            
        if self.platformGuid != None:
            qctx.addAndWhere("mc.platformGuid = :platformGuid")
            qctx.setString("platformGuid", self.platformGuid)
        
        if self.mashupContentState != None:            
            qctx.addAndWhere("mc.mashupContentState = :mashupContentState")
            qctx.setInteger("mashupContentState", self.mashupContentState)
        
        if self.pushUserName != None:
            qctx.addAndWhere("mc.pushUserName = :pushUserName")
            qctx.setString("pushUserName", self.pushUserName)
            
        if self.k != None and self.k != "":
            newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
            if self.f == None or self.f == "":
                self.f = "title"
            if self.f == "title":
                qctx.addAndWhere("mc.title LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            elif self.f == "unitName":
                qctx.addAndWhere("mc.unitName LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            elif self.f == "unitTitle":
                qctx.addAndWhere("mc.unitTitle LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            elif self.f == "author":
                qctx.addAndWhere("mc.author LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            elif self.f == "pushUserName":
                qctx.addAndWhere("mc.pushUserName LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            elif self.f == "platformName":
                qctx.addAndWhere("mc.platformName LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
                
        
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("mc.mashupContentId DESC")
        elif self.orderType == 1:
            qctx.addOrder("mc.mashupContentState DESC")
            
            
class MashupBlogGroupQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        
        self.params = ParamUtil(request)
        self.orderType = 0        
        self.contentType = None
        self.unitName = None
        self.unitTitle = None
        self.mashupBlogGroupState = 0
        self.pushUserName = None
        self.platformName = None
        self.platformGuid = None
        
        self.k = self.params.getStringParam("k")
        self.f = self.params.getStringParam("f")
        request.setAttribute("k", self.k)
        request.setAttribute("f", self.f)
        
    def initFromEntities(self, qctx):
        qctx.addEntity("MashupBlogGroup", "mbg", "")
        
    def resolveEntity(self, qctx, entity):
        BaseQuery.resolveEntity(self, qctx, entity);
        
    def applyWhereCondition(self, qctx):
        if self.contentType != None:
            qctx.addAndWhere("mbg.contentType = :contentType")
            qctx.setString("contentType", self.contentType)

            
        if self.unitName != None:
            qctx.addAndWhere("mbg.unitName = :unitName")
            qctx.setString("unitName", self.unitName)
            
        if self.platformName != None:
            qctx.addAndWhere("mbg.platformName = :platformName")
            qctx.setString("platformName", self.platformName)
            
        if self.platformGuid != None:
            qctx.addAndWhere("mbg.platformGuid = :platformGuid")
            qctx.setString("platformGuid", self.platformGuid)
        
        if self.mashupBlogGroupState != None:            
            qctx.addAndWhere("mbg.mashupBlogGroupState = :mashupBlogGroupState")
            qctx.setInteger("mashupBlogGroupState", self.mashupBlogGroupState)
        
        if self.pushUserName != None:
            qctx.addAndWhere("mbg.pushUserName = :pushUserName")
            qctx.setString("pushUserName", self.pushUserName)
            
        if self.k != None and self.k != "":
            newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
            if self.f == None or self.f == "":
                self.f = "trueName"
            if self.f == "trueName":
                qctx.addAndWhere("mbg.trueName LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            elif self.f == "unitName":
                qctx.addAndWhere("mbg.unitName LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            elif self.f == "pushUserName":
                qctx.addAndWhere("mbg.pushUserName LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            elif self.f == "platformName":
                qctx.addAndWhere("mbg.platformName LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")                
        
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("mbg.mashupBlogGroupId DESC")
        elif self.orderType == 1:
            qctx.addOrder("newid()")
