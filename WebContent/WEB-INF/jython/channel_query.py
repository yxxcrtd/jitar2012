# -*- coding: utf-8 -*-
from cn.edustar.jitar.data import BaseQuery
from cn.edustar.jitar.util import ParamUtil

class ChannelArticleQuery (BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.channelId = None
        self.articleState = True
        self.channelCate = None
        self.orderType = 0
        self.custormAndWhereClause = None
        self.k = self.params.getStringParam("k")
        self.f = self.params.getStringParam("f")
        if self.f == None:
            self.f = "title"
        if self.f == "":
            self.f = "title"
            
        request.setAttribute("k", self.k)
        request.setAttribute("f", self.f)
        
    def initFromEntities(self, qctx):
        qctx.addEntity("ChannelArticle", "ca", "")
        
    def resolveEntity(self, qctx, entity):
        BaseQuery.resolveEntity(self,qctx, entity)
    
    def applyWhereCondition(self, qctx):
        if self.channelId != None:
            qctx.addAndWhere("ca.channelId = :channelId")
            qctx.setInteger("channelId", self.channelId)
        if self.custormAndWhereClause != None:
            qctx.addAndWhere(" " + self.custormAndWhereClause + " ")
        if self.channelCate != None:
            qctx.addAndWhere("ca.channelCate = :channelCate")
            qctx.setString("channelCate", self.channelCate)
        if self.articleState != None:
            qctx.addAndWhere("ca.articleState = :articleState")
            qctx.setBoolean("articleState", self.articleState)
        if self.k != None and self.k != '':
            newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
            if self.f == "title":
                qctx.addAndWhere("ca.title LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            elif self.f == "uname":
                qctx.addAndWhere("ca.loginName LIKE :keyword OR ca.userTrueName LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            else:
                qctx.addAndWhere("ca.title LIKE :keyword OR ca.loginName LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
    
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("ca.channelArticleId DESC")

class ChannelPhotoQuery (BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.channelId = None
        self.photoId = None
        self.channelCate = None
        self.orderType = 0
        self.custormAndWhereClause = None
        self.k = self.params.getStringParam("k")
        self.f = self.params.getStringParam("f")
        if self.f == None:
            self.f = "title"
        if self.f == "":
            self.f = "title"
            
        request.setAttribute("k", self.k)
        request.setAttribute("f", self.f)
        
    def initFromEntities(self, qctx):
        qctx.addEntity("ChannelPhoto", "cp", "")
        
    def resolveEntity(self, qctx, entity):
        if entity == "photo":
            qctx.addJoinEntity("cp", "cp.photo", "photo","LEFT JOIN")
        elif "user" == entity:
            qctx.addJoinEntity("cp", "cp.user", "user","LEFT JOIN")
        elif "unit" == entity:
            qctx.addJoinEntity("cp", "cp.unit", "unit","LEFT JOIN")
        else:
            BaseQuery.resolveEntity(self, qctx, entity);
    
    def applyWhereCondition(self, qctx):
        qctx.addAndWhere("photo.auditState = :auditState")
        qctx.setInteger("auditState", 0)
        qctx.addAndWhere("photo.isPrivateShow = :isPrivateShow")
        qctx.setBoolean("isPrivateShow", False)
        qctx.addAndWhere("photo.delState = :delState")
        qctx.setBoolean("delState", False)
        if self.channelId != None:
            qctx.addAndWhere("cp.channelId = :channelId")
            qctx.setInteger("channelId", self.channelId)
        if self.custormAndWhereClause != None:
            qctx.addAndWhere(" " + self.custormAndWhereClause + " ")
        if self.channelCate != None:
            qctx.addAndWhere("cp.channelCate = :channelCate")
            qctx.setString("channelCate", self.channelCate)
        if self.k != None and self.k != '':
            newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
            if self.f == "uname":
                qctx.addAndWhere("user.trueName LIKE :keyword ")
                qctx.setString("keyword", "%" + newKey + "%")
            elif self.f == "unitTitle":
                qctx.addAndWhere("unit.unitTitle LIKE :keyword ")
                qctx.setString("keyword", "%" + newKey + "%")
            else:
                qctx.addAndWhere("photo.title LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
    
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("cp.channelPhotoId DESC")

class ChannelVideoQuery (BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.channelId = None
        self.channelCate = None
        self.orderType = 0
        self.custormAndWhereClause = None
        self.k = self.params.getStringParam("k")
        self.f = self.params.getStringParam("f")
        if self.f == None:
            self.f = "title"
        if self.f == "":
            self.f = "title"
            
        request.setAttribute("k", self.k)
        request.setAttribute("f", self.f)
        
    def initFromEntities(self, qctx):
        qctx.addEntity("ChannelVideo", "cv", "")
        
    def resolveEntity(self, qctx, entity):
        if entity == "video":
            qctx.addJoinEntity("cv", "cv.video", "video","LEFT JOIN")
        elif "user" == entity:
            qctx.addJoinEntity("cv", "cv.user", "user","LEFT JOIN")
        elif "unit" == entity:
            qctx.addJoinEntity("cv", "cv.unit", "unit","LEFT JOIN")
        else:
            BaseQuery.resolveEntity(self,qctx, entity)
    
    def applyWhereCondition(self, qctx):
        qctx.addAndWhere("video.auditState = :auditState")
        qctx.setInteger("auditState", 0)
        if self.channelId != None:
            qctx.addAndWhere("cv.channelId = :channelId")
            qctx.setInteger("channelId", self.channelId)
        if self.custormAndWhereClause != None:
            qctx.addAndWhere(" " + self.custormAndWhereClause + " ")
        if self.channelCate != None:
            qctx.addAndWhere("cv.channelCate = :channelCate")
            qctx.setString("channelCate", self.channelCate)
        if self.k != None and self.k != '':
            newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
            if self.f == "uname":
                qctx.addAndWhere("user.trueName LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            elif self.f == "unitTitle":
                qctx.addAndWhere("unit.unitTitle LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            else:
                qctx.addAndWhere("video.title LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
    
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("cv.channelVideoId DESC")

class ChannelResourceQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.channelId = None
        self.channelCate = None
        self.orderType = 0
        self.custormAndWhereClause = None
        self.k = self.params.getStringParam("k")
        self.f = self.params.getStringParam("f")
        if self.f == None:
            self.f = "title"
        if self.f == "":
            self.f = "title"
            
        request.setAttribute("k", self.k)
        request.setAttribute("f", self.f)
        
    def initFromEntities(self, qctx):
        qctx.addEntity("ChannelResource", "cr", "")
        
    def resolveEntity(self, qctx, entity):
        if entity == "resource":
            qctx.addJoinEntity("cr", "cr.resource", "resource","LEFT JOIN")
        elif "user" == entity:
            qctx.addJoinEntity("cr", "cr.user", "user","LEFT JOIN")
        elif "unit" == entity:
            qctx.addJoinEntity("cr", "cr.unit", "unit","LEFT JOIN")
        else:
            BaseQuery.resolveEntity(self,qctx, entity)
    
    def applyWhereCondition(self, qctx):
        qctx.addAndWhere("resource.auditState = :auditState")
        qctx.setInteger("auditState", 0)
        qctx.addAndWhere("resource.delState = :delState")
        qctx.setBoolean("delState", False)
        qctx.addAndWhere("resource.shareMode > :shareMode")
        qctx.setInteger("shareMode", 500)
        if self.channelId != None:
            qctx.addAndWhere("cr.channelId = :channelId")
            qctx.setInteger("channelId", self.channelId)
        if self.custormAndWhereClause != None:
            qctx.addAndWhere(" " + self.custormAndWhereClause + " ")
        if self.channelCate != None:
            qctx.addAndWhere("cr.channelCate = :channelCate")
            qctx.setString("channelCate", self.channelCate)        
        if self.k != None and self.k != '':
            newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")            
            if self.f == "uname":
                qctx.addAndWhere("user.trueName LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            elif self.f == "unitTitle":
                qctx.addAndWhere("unit.unitTitle LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            else:
                qctx.addAndWhere("resource.title LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
                
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("cr.channelResourceId DESC")
            
class ChannelUserQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.channelId = None
        self.userStatus = 0
        self.orderType = 0
        self.custormAndWhereClause = None
        self.k = self.params.getStringParam("k")
        self.f = self.params.getStringParam("f")
        if self.f == None:
            self.f = "title"
        if self.f == "":
            self.f = "title"
            
        request.setAttribute("k", self.k)
        request.setAttribute("f", self.f)
        
    def initFromEntities(self, qctx):
        qctx.addEntity("ChannelUser", "cu", "")
        
    def resolveEntity(self, qctx, entity):
        if "user" == entity:
            qctx.addJoinEntity("cu", "cu.user", "user", "LEFT JOIN")
        elif "channel" == entity:
            qctx.addJoinEntity("cu", "cu.channel", "channel", "LEFT JOIN")
        else:
            BaseQuery.resolveEntity(self, qctx, entity);
    
    def applyWhereCondition(self, qctx):
        if self.userStatus != None:
            qctx.addAndWhere("user.userStatus = :userStatus")
            qctx.setInteger("userStatus", self.userStatus)
        if self.channelId != None:
            qctx.addAndWhere("cu.channelId = :channelId")
            qctx.setInteger("channelId", self.channelId)
        if self.k != None and self.k != "":
            newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")            
            if self.f == 'email':
                qctx.addAndWhere("user.email LIKE :keyword")
            elif self.f == 'trueName':
                qctx.addAndWhere("user.trueName LIKE :keyword")
            elif self.f == 'tags':
                qctx.addAndWhere("user.userTags LIKE :keyword")
            elif self.f == 'intro':
                qctx.addAndWhere("user.blogName LIKE :keyword OR user.blogIntroduce LIKE :keyword")
            elif self.f == 'unitTitle':
                qctx.addAndWhere("cu.unitTitle LIKE :keyword")
            elif self.f == 'loginName':
                qctx.addAndWhere("user.loginName LIKE :keyword")
            elif self.f == "name":
                qctx.addAndWhere("cu.userId LIKE :keyword OR user.loginName LIKE :keyword OR user.trueName LIKE :keyword")
            else :  
                qctx.addAndWhere("user.userId LIKE :keyword OR user.loginName LIKE :keyword ")
            qctx.setString("keyword", "%" + newKey + "%")
        
        if self.custormAndWhereClause != None:
            qctx.addAndWhere(" " + self.custormAndWhereClause + " ")
    
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("cu.channelUserId DESC")
            
class ChannelGroupQuery (BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.channelId = None
        self.orderType = 0
        
    def initFromEntities(self, qctx):
        qctx.addEntity("Group", "g", "")
        qctx.addEntity("ChannelUser", "cu", "g.createUserId = cu.userId")
    #def resolveEntity(self, qctx, entity):
        
    
    def applyWhereCondition(self, qctx):
        qctx.addAndWhere("g.groupState = 0")
        if self.channelId != None:
            qctx.addAndWhere("cu.channelId = :channelId")
            qctx.setInteger("channelId", self.channelId)
    
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("g.groupId DESC")
                      
class ChannelUserStatQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.channelId = None
        self.statGuid = None
        self.orderType = 0
        self.k = self.params.safeGetStringParam("k")
        self.f = self.params.safeGetStringParam("f")        
        
        request.setAttribute("k", self.k)
        request.setAttribute("f", self.f)
        
    def initFromEntities(self, qctx):
        qctx.addEntity("ChannelUserStat", "cus", "")
        
    def resolveEntity(self, qctx, entity):
        BaseQuery.resolveEntity(self, qctx, entity);
    
    def applyWhereCondition(self, qctx):
        if self.channelId != None:
            qctx.addAndWhere("cus.channelId = :channelId")
            qctx.setInteger("channelId", self.channelId)
        if self.statGuid != None:
            qctx.addAndWhere("cus.statGuid = :statGuid")
            qctx.setString("statGuid", self.statGuid)
            
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("cus.id ASC")
            
class ChannelUnitStatQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.channelId = None
        self.statGuid = None
        self.orderType = 0
        
    def initFromEntities(self, qctx):
        qctx.addEntity("ChannelUnitStat", "cuns", "")
        
    def resolveEntity(self, qctx, entity):
        BaseQuery.resolveEntity(self, qctx, entity);
    
    def applyWhereCondition(self, qctx):
        if self.channelId != None:
            qctx.addAndWhere("cuns.channelId = :channelId")
            qctx.setInteger("channelId", self.channelId)
        if self.statGuid != None:
            qctx.addAndWhere("cuns.statGuid = :statGuid")
            qctx.setString("statGuid", self.statGuid)
            
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("cuns.id ASC")