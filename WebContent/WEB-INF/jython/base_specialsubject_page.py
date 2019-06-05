from java.net import URLDecoder
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import SpecialSubject
from cn.edustar.jitar.model import ObjectType
from cn.edustar.jitar.service import PageKey
from cn.edustar.jitar.util import CommonUtil
from base_action import BaseAction
from cn.edustar.jitar.data import BaseQuery
from base_action import *

cache = __jitar__.cacheProvider.getCache('specialSubject')
ERROR = "/WEB-INF/ftl/course/course_error.ftl"

class SpecialSubjectQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.specialSubjectId = None
        self.objectType = None
        self.objectId = None
        self.k = self.params.getStringParam("k")
        request.setAttribute("k", self.k)        
        self.orderType = 0
        
    def initFromEntities(self, qctx):
        qctx.addEntity("SpecialSubject", "ss", "")
    
    def resolveEntity(self, qctx, entity):
        BaseQuery.resolveEntity(self, qctx, entity)      
      
    def applyWhereCondition(self, qctx):
        if self.specialSubjectId != None:
            qctx.addAndWhere("ss.specialSubjectId = :specialSubjectId")
            qctx.setInteger("specialSubjectId", self.specialSubjectId)
            
        if self.objectType != None:
            qctx.addAndWhere("ss.objectType = :objectType")
            qctx.setString("objectType", self.objectType)
            
        if self.objectId != None:
            qctx.addAndWhere("ss.objectId = :objectId")
            qctx.setInteger("objectId", self.objectId)
            
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("ss.specialSubjectId DESC")