# coding=UTF-8
from cn.edustar.jitar.data import BaseQuery
from base_action import *
from java.util import Calendar,Date
from java.text import SimpleDateFormat

class PrepareCourseMemberQuery(BaseQuery):
    
    ORDER_TYPE_ID_DESC = 0

    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.prepareCourseId = None
        self.userId = None
        self.privateContentExist=None       #是否只显示有内容的个案 默认None,全部显示 True,查询有内容的,False 查询无内容的
        self.stage = None #集备执行的阶段，正在进行running；已经完成finishaed；还未进行will
        sft = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")      
        self.nowDate = sft.format(Date())
        
    def initFromEntities(self, qctx):
        qctx.addEntity("PrepareCourseMember", "pcm", "")
    
    def resolveEntity(self, qctx, entity):
        if "u" == entity:
            qctx.addEntity("User", "u", "pcm.userId = u.userId")
        elif "pc" == entity:
            qctx.addEntity("PrepareCourse", "pc", "pcm.prepareCourseId = pc.prepareCourseId")
        elif entity == "subj":
            qctx.addJoinEntity("u", "u.subject", "subj", "LEFT JOIN")
        elif entity == "grad":
            qctx.addJoinEntity("u", "u.grade", "grad", "LEFT JOIN")
        elif entity == "unit":
            qctx.addJoinEntity("u", "u.unit", "unit", "LEFT JOIN")
        elif entity == "sc":
            qctx.addJoinEntity("u", "u.sysCate", "sc", "LEFT JOIN")
        else:
            BaseQuery.resolveEntity(self, qctx, entity)
            
    def applyWhereCondition(self, qctx):
        if (self.prepareCourseId != None):
            qctx.addAndWhere("pcm.prepareCourseId = :prepareCourseId")
            qctx.setInteger("prepareCourseId", self.prepareCourseId)
        if (self.userId != None):
            qctx.addAndWhere("pcm.userId = :userId")
            qctx.setInteger("userId", self.userId)
        if (self.privateContentExist != None):    
            if (self.privateContentExist == True):
                qctx.addAndWhere("DATALENGTH(pcm.privateContent)> :V")
                qctx.setInteger("V", 0)
            if (self.privateContentExist == False):     
                qctx.addAndWhere("DATALENGTH(pcm.privateContent)= :V")
                qctx.setInteger("V", 0)
        if self.stage != None:
            if "running" == self.stage:
                qctx.addAndWhere("(:stage >= pc.startDate And :stage <= pc.endDate)")
                qctx.setString("stage",self.nowDate)
            if "finished" == self.stage:
                qctx.addAndWhere("(pc.endDate < :stage)")
                qctx.setString("stage",self.nowDate)
            if "new" == self.stage:
                qctx.addAndWhere("(pc.startDate > :stage)")
                qctx.setString("stage",self.nowDate)            
            if "notfinished" == self.stage:
                qctx.addAndWhere("(pc.endDate >= :stage)")
                qctx.setString("stage",self.nowDate)
                