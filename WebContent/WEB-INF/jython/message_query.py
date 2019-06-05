# coding=utf-8
from cn.edustar.jitar.data import BaseQuery
# 短消息查询.
class MessageQuery(BaseQuery):
    ORDER_TYPE_ID_DESC = 0
    ORDER_TYPE_SENDTIME_DESC = 1
    
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.sendId = None      # 消息发送者标识, 缺省不限定.
        self.receiveId = None   # 消息接收者标识, 缺省不限定.
        
        self.isDel = False      # 删除标识(删除至回收站) 默认为false, 表示查询未删除的.
        self.isSenderDel = None # 发送者删除消息标识, 缺省为 None 不限定.
        self.orderType = MessageQuery.ORDER_TYPE_ID_DESC  # 缺省按照 id 逆序排列.
        
    def initFromEntities(self, qctx):
        qctx.addEntity("Message" , "msg", "")
      
    def resolveEntity(self, qctx, entity):
        if "send" == entity:      # 关联到接收者.
            qctx.addJoinEntity("msg", "msg.sender" , "send", "LEFT JOIN")
        elif "recv" == entity:    # 关联到发送者.
            qctx.addJoinEntity("msg", "msg.receiver" , "recv", "LEFT JOIN")
        else:
            BaseQuery.resolveEntity(self, qctx, entity);
          
    def applyWhereCondition(self , qctx):
        if self.sendId != None:
            qctx.addAndWhere("msg.sendId = :sendId")
            qctx.setInteger("sendId", self.sendId)
        if self.receiveId != None:
            qctx.addAndWhere("msg.receiveId = :receiveId")
            qctx.setInteger("receiveId", self.receiveId)
        if self.isDel != None:
            qctx.addAndWhere("msg.isDel = :isDel")
            qctx.setBoolean("isDel", self.isDel) 
        if self.isSenderDel != None:
            qctx.addAndWhere("msg.isSenderDel = :isSenderDel")
            qctx.setBoolean("isSenderDel", self.isSenderDel)
        
    def applyOrderCondition(self, qctx):
        if self.orderType == MessageQuery.ORDER_TYPE_ID_DESC:
            qctx.addOrder("msg.id DESC")
        elif self.orderType == MessageQuery.ORDER_TYPE_SENDTIME_DESC:
            qctx.addOrder("msg.sendTime DESC")
