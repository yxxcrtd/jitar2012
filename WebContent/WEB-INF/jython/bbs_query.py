# -*- coding: utf-8 -*-
from cn.edustar.jitar.data import BaseQuery
from cn.edustar.jitar.pojos import Topic

# 协作组话题查询.
class TopicQuery(BaseQuery):
   def __init__(self, selectFields):
      BaseQuery.__init__(self, selectFields)
      self.userId = None    
      self.groupId = None
      self.isDeleted = False        # 缺省只获取未删除的.
      
      # 要查询的主题数量, 缺省 = 10; -1 表示不限制, 只在未提供 pager 参数时候生效.
      self.count = 10
      # 协作组标识, = null 表示不限制
      self.groupId = None
      # 主题标识 = null 表示不限定
      self.topicId = None
      # 发贴的用户表示, 缺省 = null 表示不限制.
      self.userId = None
      # 是否精华,缺省为 null 表示所有主题; = true 表示获取精华的; = false 表示获取非精华的.
      self.bestType = None
      #  删除状态, == null 表示不区分, 缺省 = false 查询未删除的.
      self.delState = False
      # 置顶状态, == null 表示不区分; = true 表示获取置顶的; = false 表示获取非置顶的.
      self.topState = False
      # 默认排序为1 - 按时间 DEAC. 0- 按topicId排序. 2- 按viewCount DESC. 3-按replyCount DESC 
      self.orderType = 1
      
   def initFromEntities(self, qctx):
     qctx.addEntity("Topic" , "t" , "");
     
   def resolveEntity(self, qctx, entity):
     if "u" == entity:
       qctx.addEntity("User", "u", "t.userId = u.userId")
     elif "g" == entity:
       qctx.addEntity("Group", "g", "t.groupId = g.groupId") 
     else:
       BaseQuery.resolveEntity(self, qctx, entity);
      
    # 提供 where 条件.
   def applyWhereCondition(self , qctx):
     
     if self.userId != None:
       qctx.addAndWhere("t.userId = :userId")
       qctx.setInteger("userId", self.userId)
     if self.topicId != None:
       qctx.addAndWhere("t.topicId = :topicId")
       qctx.setInteger("topicId", self.topicId)
     if self.groupId != None:
       qctx.addAndWhere("t.groupId =:groupId")
       qctx.setInteger("groupId", self.groupId)
     if self.bestType != None:
       qctx.addAndWhere("t.isBest = :bestType")
       qctx.setInteger("bestType", self.bestType)
     if self.delState != None:
       qctx.addAndWhere("t.isDeleted = :delState")
       qctx.setBoolean("delState", self.delState)
     if self.topState != None:
       qctx.addAndWhere("t.isTop = :topState")
       qctx.setBoolean("topState", self.topState)
       
   # 提供排序 order 条件.
   def applyOrderCondition(self, qctx):
     # 排序方式, 0 - commentId DESC, 1 - createDate,缺省为1,  2 - viewCount, 3 - replyCount.
     if self.orderType == 0:  
       qctx.addOrder("t.topicId DESC")
     elif self.orderType == 1:
       qctx.addOrder("t.createDate DESC")
     elif self.orderType == 2:
       qctx.addOrder("t.viewCount DESC")
     elif self.orderType == 3: 
       qctx.addOrder("t.replyCount DESC")
