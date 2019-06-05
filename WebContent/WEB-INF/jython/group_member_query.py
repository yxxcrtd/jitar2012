# coding=utf-8
from cn.edustar.jitar.data import BaseQuery
from cn.edustar.jitar.pojos import Group
from cn.edustar.jitar.pojos import GroupMember

class GroupMemberQuery (BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.userId = None
        self.groupId = None
        self.orderType = 0
        
        #/** 该成员在本群组内的状态：0 – 正常可用状态；1 – 申请后待审核； 2 – 待删除；3 – 锁定，4 – 邀请未回应； */
        self.memberStatus = None
        
        #/** 群组状态：0正常，1待审核，2锁定，3待删除，4隐藏 (使用 GROUP_STATE_XXX 常量) */
        self.groupStatus = None
    
    def initFromEntities(self, qctx):
        qctx.addEntity("GroupMember", "gm", "")
    
    def resolveEntity(self, qctx, entity):
        if entity == "g":
            qctx.addEntity("Group", "g", "g.groupId = gm.groupId")
        elif entity == "u":
            qctx.addEntity("User", "u", "u.userId = gm.userId")
        else :
            BaseQuery.resolveEntity(self, qctx, entity);


    def applyWhereCondition(self, qctx):
        if self.userId != None:
            qctx.addAndWhere("gm.userId = :userId")
            qctx.setInteger("userId", self.userId)
        if self.groupId != None:
            qctx.addAndWhere("gm.groupId = :groupId")
            qctx.setInteger("groupId", self.groupId)
        if self.memberStatus != None:
            qctx.addAndWhere("gm.status = :status")
            qctx.setInteger("status", self.memberStatus)        
        if self.groupStatus != None:
            qctx.addAndWhere("g.groupState = :groupState")
            qctx.setInteger("groupState", self.groupStatus)


    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("gm.id DESC")
        if self.orderType == 1:
            qctx.addOrder("gm.articleCount DESC")
        if self.orderType == 2:
            qctx.addOrder("u.userId")
            
