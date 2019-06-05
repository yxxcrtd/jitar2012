# coding=utf-8
from cn.edustar.jitar.data import BaseQuery
from cn.edustar.jitar.util import ParamUtil
from java.util import Calendar
from java.text import SimpleDateFormat
from base_action import *
from cn.edustar.jitar.jython import JythonBaseAction
from java.net import URLEncoder

class EvaluationBase(JythonBaseAction):
    def get_site_url(self):
        if request.getServerPort() == 80:
            return request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/"
        else:
            return request.getScheme() + "://" + request.getServerName() + ":" + str(request.getServerPort()) + request.getContextPath() + "/"

    def __init__(self):
        self.params = ParamUtil(request)
        if self.loginUser == None:
            response.sendRedirect(self.get_site_url() + "login.jsp?redUrl=" + URLEncoder.encode(self.get_site_url() + "evaluations.py","utf-8"))
    
    def canManage(self):
        if self.loginUser == None:
            return False
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == True:
            return True
        accessControlService = __spring__.getBean("accessControlService")
        return accessControlService.isSystemContentAdmin(self.loginUser) or accessControlService.isSystemAdmin(self.loginUser)
    
class EvaluationPlanQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.ValidPlan = None
        self.enabled = None
        self.orderType = 0
        self.listType = 1
        self.userId = 0
        self.title=None
        self.subjectId = None
        self.gradeId = None
        self.teacherName = None
             
    def initFromEntities(self, qctx):
        qctx.addEntity("EvaluationPlan", "ev", "")
        
        
    def resolveEntity(self, qctx, entity):
        if "subj" == entity:
            qctx.addJoinEntity("ev", "ev.metaSubject", "subj", "LEFT JOIN")
        elif ("grad" == entity):
            qctx.addJoinEntity("ev", "ev.grade", "grad", "LEFT JOIN")
        else:
            BaseQuery.resolveEntity(self, qctx, entity) 
            
    def applyWhereCondition(self, qctx):
        #print "--self.subjectId="+str(self.subjectId)
        if self.enabled != None:
            qctx.addAndWhere("ev.enabled = :enabled")
            qctx.setBoolean("enabled", self.enabled)
        if self.ValidPlan == True:
            nowDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())
            qctx.addAndWhere("(:nowDate >= ev.startDate) And (:nowDate <= ev.endDate)")
            qctx.setString("nowDate", nowDate)
            qctx.setString("nowDate", nowDate)
            
        if self.title !=None :
            newKey = self.title.replace("%","[%]").replace("_","[_]").replace("[","[[]")
            qctx.addAndWhere("ev.evaluationCaption LIKE :title")
            qctx.setString("title", "%" + newKey + "%")
            
        if self.subjectId != None:
            qctx.addAndWhere("ev.metaSubjectId = :subjectId")
            qctx.setInteger("subjectId", self.subjectId)
        
        if self.gradeId != None:
            qctx.addAndWhere("ev.metaGradeId = :gradeId")
            qctx.setInteger("gradeId", self.gradeId)
            
        if self.teacherName != None:
            tName = self.teacherName.replace("%","[%]").replace("_","[_]").replace("[","[[]")
            qctx.addAndWhere("ev.teacherName LIKE :teacherName")
            qctx.setString("teacherName", "%" + tName +"%")
                        
        if self.listType == 0:
            #已经完成的评课
            nowDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())
            qctx.addAndWhere(":nowDate > ev.endDate")
            qctx.setString("nowDate", nowDate)
            if self.userId>0:
               qctx.addAndWhere("ev.teacherId = :userId")
               qctx.setInteger("userId", self.userId) 
        elif self.listType == 1:
            #进行中的评课
            nowDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())
            qctx.addAndWhere("(:nowDate >= ev.startDate) And (:nowDate <= ev.endDate)")
            qctx.setString("nowDate", nowDate)
            qctx.setString("nowDate", nowDate)
            if self.userId>0:
               qctx.addAndWhere("ev.teacherId = :userId")
               qctx.setInteger("userId", self.userId) 
        elif self.listType == 2:
            #我发起的评课
            qctx.addAndWhere("ev.createrId=:userId")
            qctx.setInteger("userId", self.userId)    
        elif self.listType == 3:
            #我参与的评课
            qctx.addAndWhere(" ev.evaluationPlanId IN(SELECT ec.evaluationPlanId FROM EvaluationContent as ec WHERE ec.publishUserId=:userId)")
            qctx.setInteger("userId", self.userId)
                
            
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("ev.evaluationPlanId DESC")
        elif self.orderType == 1:
            qctx.addOrder("ev.evaluationPlanId DESC")
        elif self.orderType == 2:
            qctx.addOrder("ev.evaluationPlanId DESC")
            

class EvaluationTemplateQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.enabled = None
        self.orderType = 0
        
    def initFromEntities(self, qctx):
        qctx.addEntity("EvaluationTemplate", "et", "")
        
    def resolveEntity(self, qctx, entity):
        ##if "u" == entity:
        #    qctx.addEntity("User", "u", "ev.userId = u.userId")
        #else:
        BaseQuery.resolveEntity(self, qctx, entity) 
            
    def applyWhereCondition(self, qctx):
        if self.enabled != None:
            qctx.addAndWhere("et.enabled = :enabled")
            qctx.setBoolean("enabled", self.enabled)
            
    
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("et.evaluationTemplateId DESC")
            
            

class EvaluationContentQuery(BaseQuery,SubjectMixiner):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.evaluationPlanId = None
        self.publishUserId = None
        self.metaSubjectId = self.params.getIntParamZeroAsNull("subjectId")
        self.metaGradeId = self.params.getIntParamZeroAsNull("gradeId")
        self.orderType = 0
        self.gradelevel = self.params.getIntParamZeroAsNull("level")
        # 默认是精确匹配
        self.FuzzyMatch = True #模糊匹配
        
        request.setAttribute("subjectId",self.metaSubjectId)
        request.setAttribute("gradeId",self.metaGradeId)
        
    def initFromEntities(self, qctx):
        qctx.addEntity("EvaluationContent", "ec", "")
        
    def resolveEntity(self, qctx, entity):
        if "subj" == entity:
            qctx.addJoinEntity("ec", "ec.metaSubject", "subj", "LEFT JOIN")
        elif ("grad" == entity):
            qctx.addJoinEntity("ec", "ec.grade", "grad", "LEFT JOIN")
        ##if "u" == entity:
        #    qctx.addEntity("User", "u", "ev.userId = u.userId")
        else:
            BaseQuery.resolveEntity(self, qctx, entity) 
            
    def applyWhereCondition(self, qctx):
        if self.evaluationPlanId != None:
            qctx.addAndWhere("ec.evaluationPlanId = :evaluationPlanId")
            qctx.setInteger("evaluationPlanId", self.evaluationPlanId)
            
        if self.publishUserId != None:
            qctx.addAndWhere("ec.publishUserId = :publishUserId")
            qctx.setInteger("publishUserId", self.publishUserId)
            
        if self.metaSubjectId != None:
            qctx.addAndWhere("ec.metaSubjectId = :metaSubjectId")
            qctx.setInteger("metaSubjectId", self.metaSubjectId)
            
        if self.metaGradeId != None:
            if self.gradelevel != None:
                if self.gradelevel == 1:
                      qctx.addAndWhere("ec.metaGradeId = :gradeStartId")
                      qctx.setInteger("gradeStartId", self.metaGradeId)
                else:
                      if self.FuzzyMatch == False:
                            qctx.addAndWhere("ec.metaGradeId = :metaGradeId")
                            qctx.setInteger("metaGradeId", self.metaGradeId)
                      else:
                            qctx.addAndWhere("ec.metaGradeId >= :gradeStartId AND ec.metaGradeId < :gradeEndId")
                            qctx.setInteger("gradeStartId", self.calcGradeStartId(self.metaGradeId))
                            qctx.setInteger("gradeEndId", self.calcGradeEndId(self.metaGradeId))
            else:
                if self.FuzzyMatch == False:
                      qctx.addAndWhere("ec.metaGradeId = :metaGradeId")
                      qctx.setInteger("metaGradeId", self.metaGradeId)
                else:
                      qctx.addAndWhere("ec.metaGradeId >= :gradeStartId AND ec.metaGradeId < :gradeEndId")
                      qctx.setInteger("gradeStartId", self.calcGradeStartId(self.metaGradeId))
                      qctx.setInteger("gradeEndId", self.calcGradeEndId(self.metaGradeId))
            
    
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("ec.evaluationContentId DESC")