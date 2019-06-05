# -*- coding: utf-8 -*-
from cn.edustar.jitar.data import BaseQuery

# 评论查询
class CommentQuery (BaseQuery):
    def __init__(self, selectFields):
      BaseQuery.__init__(self, selectFields)
      #发表评论的用户标识, 缺省 = null, 表示不限定
      self.userId = None
      #被评论的文章/资源的所属用户的标识,缺省 = null 表示不限定
      self.aboutUserId = None
      #被评论的对象类型, 缺省 = null 表示不限定; 一般和objectId 一起使用.
      self.objType = None
      #被评论的对象标识, 缺省 = null 表示不限定
      self.objId = None
      #审核标志; 缺省 = 1 表示获取审核通过的, = 0 表示获取未审核通过的; = null 表示不限定      self.audit = 1
      #排序方式, 0 - 按照 id 逆序排列,  1 - createDate 逆序排列
      self.orderType = 0
      # 查询关键字. 
      self.k = None    

      # 查询字段. 0 标题或内容  1用户
      self.f = "0"
      
      self.unitId = None
      self.commentType = None
      
    def initFromEntities(self, qctx):
      qctx.addEntity("Comment" , "cmt" , "")
    
    def resolveEntity(self, qctx, entity):
      if "u" == entity:
        if self.commentType == "aboutUser":
          qctx.addEntity("User", "u", "cmt.aboutUserId = u.userId")
        else:
          qctx.addEntity("User", "u", "cmt.userId = u.userId")
      elif "r" == entity:
        qctx.addEntity("Resource", "r", "cmt.objId = r.resourceId")
      elif "a" == entity:
        qctx.addEntity("Article", "a", "cmt.objId = a.articleId")
      elif "p" == entity:
        qctx.addEntity("Photo", "p", "cmt.objId = p.photoId")
      else:
        BaseQuery.resolveEntity(self, qctx, entity)
      
    # 提供 where 条件.
    def applyWhereCondition(self,qctx):
      if self.k != None and self.k != "":
        newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
        if self.f == "0":
          qctx.addAndWhere("(cmt.title LIKE :likeKey) OR (cmt.content LIKE :likeKey)")
          qctx.setString("likeKey", "%" + newKey + "%")
        elif self.f == "1":
          qctx.addAndWhere("(u.nickName LIKE :likeKey) OR (u.trueName LIKE :likeKey) OR (u.loginName LIKE :likeKey)")
          qctx.setString("likeKey", "%" + newKey + "%")
      if self.userId != None:
        qctx.addAndWhere("cmt.userId = :userId")
        qctx.setInteger("userId", self.userId)
      if self.aboutUserId != None:
        qctx.addAndWhere("cmt.aboutUserId = :aboutUserId")
        qctx.setInteger("aboutUserId", self.aboutUserId)
      if self.objType != None:
        qctx.addAndWhere("cmt.objType = :objType")
        qctx.setInteger("objType", self.objType)
      if self.objId != None:
        qctx.addAndWhere("cmt.objId = :objId")
        qctx.setInteger("objId", self.objId)
      if self.audit != None:
        qctx.addAndWhere("cmt.audit = :audit")
        qctx.setInteger("audit", self.audit)
      if self.unitId != None:
        qctx.addAndWhere("u.unitId = :unitId")
        qctx.setInteger("unitId", self.unitId)
        
    # 提供排序 order 条件.
    def applyOrderCondition(self, qctx):
      # 排序方式, 0 - commentId DESC, 1 - createDate
      if self.orderType == 0:  
        qctx.addOrder("cmt.id DESC")
      elif self.orderType == 1:
        qctx.addOrder("cmt.createDate DESC") 
      elif self.orderType == 3:  
        qctx.addOrder("cmt.id ASC")
      