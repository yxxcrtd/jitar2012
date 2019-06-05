# script for UserQuery
from cn.edustar.jitar.data import BaseQuery
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command

class ChatMsgQuery (BaseQuery):

  def __init__(self, selectFields):
    BaseQuery.__init__(self, selectFields)
    self.params = ParamUtil(request)
    self.roomId = None            
        
  def initFromEntities(self, qctx):
    qctx.addEntity("ChatMessage", "chatmsg", "")
    
  def applyWhereCondition(self, qctx):
    if self.roomId != None:
      qctx.addAndWhere("chatmsg.roomId = :roomId")
      qctx.setString("roomId", self.roomId)
      request.setAttribute("roomId", self.roomId)

  def applyOrderCondition(self, qctx):
      qctx.addOrder("chatmsg.sendDate desc")

