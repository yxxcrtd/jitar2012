from cn.edustar.jitar.data import BaseQuery
from cn.edustar.jitar.pojos import Friend


class FriendQuery (BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.userId = None    
        self.isBlack = False
        self.orderType = 0
    
    def initFromEntities(self, qctx):
        qctx.addEntity("Friend", "frd", "")
    
    def resolveEntity(self, qctx, entity):
        if entity == "u" :
            qctx.addEntity("User", "u", "frd.friendId = u.userId")
        else :
            BaseQuery.resolveEntity(qctx, entity);


    def applyWhereCondition(self, qctx):
        if self.userId != None:
            qctx.addAndWhere("frd.userId = :userId")
            qctx.setInteger("userId", self.userId)
            #print "self.userId = ", self.userId
        if self.isBlack != None:
            qctx.addAndWhere("frd.isBlack = :isBlack")
            qctx.setInteger("isBlack", self.isBlack)

    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("frd.id DESC")