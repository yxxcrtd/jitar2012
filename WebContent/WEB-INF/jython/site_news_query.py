# -*- coding: utf-8 -*-
from cn.edustar.jitar.data import BaseQuery
from base_action import *  

# 新闻查询.
class SiteNewsQuery(BaseQuery, SubjectMixiner):
  def __init__(self, selectFields):
    BaseQuery.__init__(self, selectFields)
    
    # 新闻标识, 默认为空, 表示不限制.    self.newsId = None
    # 查询条数, 一般在未指定 pager 的时候使用.    #self.count = 10
    # 限定学科标识, 缺省 = 0 表示获取站点的; = null 表示不限制.    self.subjectId = None
    # 发布用户标识, 缺省 = null 表示不限制.
    self.userId = None
    # 要获取的对象状态, 缺省 = 0, 表示或缺正常状态的; = null 表示不限制.    self.status  = 0
    # 要获取的类型, 缺省 = null 表示不限制.    self.newsType = None
    # 是否要求有图片, 缺省 = null 表示不限制, True 有, False 没有.    self.hasPicture = None
    # 要查询的关键字, 缺省 = null 表示不限制.    self.k = None
    # 排序方式, 缺省 = 0 按照创建时间逆序排列. = 1,按viewCount DESC.  = 2, 按newsId 逆序.
    self.orderType = 0

  def initFromEntities(self, qctx):
    qctx.addEntity("SiteNews" , "snews" , "")
    
  def resolveEntity(self, qctx, entity):
    if "user" == entity:
      qctx.addEntity("User" , "u", "snews.userId = u.userId")
    else:
      BaseQuery.resolveEntity(self, qctx, entity);
    
  def applyWhereCondition(self, qctx):
    if self.userId != None:
      qctx.addAndWhere("snews.userId = :userId")
      qctx.setInteger("userId", self.userId)
    if self.subjectId != None:
      qctx.addAndWhere("snews.subjectId = :subjectId")
      qctx.setInteger("subjectId", self.subjectId)
    if self.status != None:
      qctx.addAndWhere("snews.status = :status")
      qctx.setInteger("status", self.status)
    if self.newsType != None:
      qctx.addAndWhere("snews.newsType = :newsType")
      qctx.setInteger("newsType", self.newsType)
    if self.hasPicture != None:
      if self.hasPicture == True:
        qctx.addAndWhere("snews.picture IS NOT NULL AND snews.picture <> ''")
      else:
        qctx.addAndWhere("snews.picture IS NULL OR snews.picture = ''")
    if self.k != None:
      newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
      qctx.addAndWhere("(snews.title LIKE :likeKey)")
      qctx.setString("likeKey", "%" + newKey + "%")        
    
  def applyOrderCondition(self, qctx):
    if self.orderType == 0:
      qctx.addOrder("snews.newsId DESC")
    if self.orderType == 1:
      qctx.addOrder("snews.viewCount DESC")
    if self.orderType == 2:
      qctx.addOrder("snews.createDate DESC") 


class GroupNewsQuery(BaseQuery):
  def __init__(self, selectFields):
    BaseQuery.__init__(self, selectFields)
    
    
    # 新闻标识, 默认为空, 表示不限制
    self.newsId = None
    # 查询条数, 一般在未指定 pager 的时候使用
    #self.count = 10
    # 限定群组标识, 缺省 = 0 表示获取站点的; = null 表示不限制
    self.groupId = None
    # 发布用户标识, 缺省 = null 表示不限制 
    self.userId = None
    # 要获取的对象状态, 缺省 = 0, 表示或缺正常状态的; = null 表示不限制
    self.status  = 0
    # 要获取的类型, 缺省 = null 表示不限制
    self.newsType = None
    # 是否要求有图片, 缺省 = null 表示不限制
    self.picture = None
    # 要查询的关键字, 缺省 = null 表示不限制
    self.k = None
    # 排序方式, 缺省 = 0 按照创建时间逆序排列. = 1,按viewCount DESC.  = 2, 按newsId 逆序.
    self.orderType = 0
    
    
  def initFromEntities(self, qctx):
   qctx.addEntity("GroupNews" , "gnews" , "");
   
  def resolveEntity(self, qctx, entity):
    if "user" == entity:
      qctx.addEntity("Group" , "g", "gnews.groupId = g.groupId")
    else:
      BaseQuery.resolveEntity(self, qctx, entity);
      
  def applyWhereCondition(self, qctx):
    if self.userId != None:
      qctx.addAndWhere("gnews.userId = :userId")
      qctx.setInteger("userId", self.userId)
    if self.groupId != None:
      qctx.addAndWhere("gnews.groupId = :groupId")
      qctx.setInteger("groupId", self.groupId)
    if self.status != None:
      qctx.addAndWhere("gnews.status = :status")
      qctx.setInteger("status", self.status)
    if self.newsType != None:
      qctx.addAndWhere("gnews.newsType = :newsType")
      qctx.setInteger("newsType", self.newsType)
    if self.picture != None:
      qctx.addAndWhere("gnews.picture = :picture")
      qctx.setInteger("picture", self.picture)
    if self.k != None:
      newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
      qctx.addAndWhere("(gnews.title LIKE :likeKey)")
      qctx.setString("likeKey", "%" + newKey + "%")
      
  
  def applyOrderCondition(self, qctx):
    if self.orderType == 0:
      qctx.addOrder("gnews.createDate DESC")
    if self.orderType == 1:
      qctx.addOrder("gnews.viewCount DESC")
    if self.orderType == 2:
      qctx.addOrder("gnews.newsId DESC")