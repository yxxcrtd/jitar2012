#encoding=utf-8
from cn.edustar.jitar.data import BaseQuery
from cn.edustar.jitar.pojos import LeaveWord

class LeaveWordQuery(BaseQuery):
    
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.objId = None      # 用户或者群组的标识.
        self.objType = None   # 用户或者群组的留言
        self.orderType = 0  # 缺省按照 id 逆序排列.
        
        
    def initFromEntities(self, qctx):
        qctx.addEntity("LeaveWord" , "lwd", "")

    def applyWhereCondition(self , qctx):
        if self.objId != None:
            qctx.addAndWhere("lwd.objId = :objId")
            qctx.setInteger("objId", self.objId)
        if self.objType != None:
            qctx.addAndWhere("lwd.objType = :objType")
            qctx.setInteger("objType", self.objType)

    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("lwd.id DESC")
