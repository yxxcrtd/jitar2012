# -*- coding: utf-8 -*-
from java.net import URLDecoder
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import User, PrepareCourse, PrepareCourseMember, Page, PrepareCoursePlan
from cn.edustar.jitar.model import ObjectType
from cn.edustar.jitar.service import PageKey
from cn.edustar.jitar.util import CommonUtil
from com.alibaba.fastjson import JSONObject
from base_action import BaseAction
from java.util import Calendar, Date
from java.text import SimpleDateFormat
from cn.edustar.jitar.data import BaseQuery
from base_action import *

page_svc = __jitar__.pageService
pc_svc = __jitar__.prepareCourseService
cache = __jitar__.cacheProvider.getCache('prepareCourse')
sub_svc = __jitar__.subjectService

ERROR = "/WEB-INF/ftl/course/course_error.ftl"

class PrepareCoursePageService(BaseAction):    
    def __init__(self):
        self.params = ParamUtil(request)
        self.prepareCourseId = 0
        self.prepareCourseStageId = 0
        self.prepareCourse = None
        self.group = None
        
    def getBaseData(self):
        self.params = ParamUtil(request)
        self.prepareCourseId = request.getAttribute("prepareCourseId")
        if self.prepareCourseId == None or self.prepareCourseId == "":
            self.prepareCourseId = self.params.getIntParam("prepareCourseId")
            
        self.prepareCourseStageId = request.getAttribute("prepareCourseStageId")
        if self.prepareCourseStageId == None or self.prepareCourseStageId == "":
            self.prepareCourseStageId = self.params.getIntParam("prepareCourseStageId")
        
        if self.prepareCourseId == None or self.prepareCourseId == "" or str(self.prepareCourseId).isdigit() == False:
            self.prepareCourseId = "0"
        self.prepareCourseId = int(self.prepareCourseId)
        if self.prepareCourseStageId == None or self.prepareCourseStageId == "" or str(self.prepareCourseStageId).isdigit() == False:
            self.prepareCourseStageId = "0"
        self.prepareCourseStageId = int(self.prepareCourseStageId)
        self.prepareCourse = self.getBasePrepareCourse()
        if self.prepareCourse != None:
            self.group = pc_svc.getGroupOfPrepareCourse(self.prepareCourse.prepareCourseId)
            if self.group != None:
                request.setAttribute("group", self.group)
        
    def getBasePrepareCourse(self):
        cache_key = "prepareCourse_" + str(self.prepareCourseId)
        prepareCourse = None#cache.get(cache_key)
        if prepareCourse == None:
            prepareCourse = pc_svc.getPrepareCourse(self.prepareCourseId)
            if prepareCourse != None:
                cache.put(cache_key, prepareCourse)        
        return prepareCourse
    
    def removePrepareCourseCache(self):
        cache_key = "prepareCourse_" + str(self.prepareCourseId)
        cache.remove(cache_key)
        
    def getPrepareCoursePageWithCustomSkin(self, prepareCourse):
        cache_key = "prepareCourse_page_" + str(prepareCourse.prepareCourseId)
        page = cache.get(cache_key)
        if page == None:
            index_pk = PageKey(ObjectType.OBJECT_TYPE_PREPARECOURSE, prepareCourse.prepareCourseId, 'index')
            page = page_svc.getPageByKey(index_pk)
            if page != None:
                cache.put(cache_key, page)
            #清空测试
            #cache.remove(cache_key)            
        if page == None:            
            page = self.createPrepareCourseIndexPage(prepareCourse)     #添加一个页面 
            
        if page.customSkin != None:
            customSkin = JSONObject.parse(page.customSkin)
            request.setAttribute("customSkin", customSkin)           
        return page
    
    def getPrepareCourseGrade(self, prepareCourse):
        return sub_svc.getGrade(prepareCourse.gradeId)
    
    def getPrepareCourseGradeLevel(self, prepareCourse):
        return sub_svc.getGradeLevel(prepareCourse.gradeId)
    
    def getPrepareCourseMetaSubject(self, prepareCourse):
        return sub_svc.getMetaSubjectById(prepareCourse.metaSubjectId)
    
    def createPrepareCourseIndexPage(self, prepareCourse):
        page = pc_svc.createPrepareCoursePageWidthWidgets(prepareCourse)
        return page
    
    def canView(self, prepareCourse):
        return True
        if prepareCourse == None:
            return False
        
        if self.loginUser == None:
            return False
        if self.canManage(prepareCourse):
            return True
        if self.isCreator(prepareCourse):
            return True
        if self.isLeader(prepareCourse):
            return True
        
        return self.isPrepareCourseMember()       
    
    def isPrepareCourseMember(self):
        if self.loginUser == None:
            return False
        return pc_svc.checkUserInPreCourse(self.prepareCourse.prepareCourseId, self.loginUser.userId) 
        
    def canManage(self, prepareCourse):
        if prepareCourse == None:
            return False
        if self.loginUser == None:
            return False
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == True:
            return True
        if self.isCreator(prepareCourse) or self.isLeader(prepareCourse):
            return True
        return False
    
    def isCreator(self, prepareCourse):
        if prepareCourse == None:
            return False
        if self.loginUser == None:
            return False
        if prepareCourse.createUserId == self.loginUser.userId:
            return True
        else:
            return False
    def canAdmin(self):
        return self.canManage(self.prepareCourse)
    
    
    def isLeader(self, prepareCourse):
        if prepareCourse == None:
            return False
        if self.loginUser == None:
            return False
        if prepareCourse.leaderId == self.loginUser.userId:
            return True
        else:
            return False
    
class PrepareCourseQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.prepareCourseId = None
        self.createUserId = None
        self.k = self.params.getStringParam("k")    #查询关键字
        self.ktype = self.params.getStringParam("ktype") #查询类型[关键字对应的类型]
        if self.ktype == None:
            self.ktype = "1"    #默认关键字查询标题
        self.unit = self.params.getStringParam("unit")  #主备人所属机构
        self.course_BeginDate = self.params.getStringParam("course_BeginDate")
        self.course_EndDate = self.params.getStringParam("course_EndDate")
        self.subjectId = self.params.getIntParamZeroAsNull("subjectId") 
        self.gradeId = self.params.getIntParamZeroAsNull("gradeId")
        request.setAttribute("subjectId", self.subjectId)
        request.setAttribute("gradeId", self.gradeId)
        request.setAttribute("k", self.k)
        request.setAttribute("ktype", self.ktype)
        request.setAttribute("unit", self.unit)
        request.setAttribute("course_BeginDate", self.course_BeginDate)
        request.setAttribute("course_EndDate", self.course_EndDate)
        self.orderType = 0
        self.status = None
        self.stage = None   #集备执行的阶段，正在进行running；已经完成finishaed；还未进行will ;recommend 推荐的
        self.containChild = None  #准确学科的查询
        self.prepareCoursePlanId = None
        self.prepareCourseGenerated = True
        self.custormAndWhere = None #自定义条件查询
        
        sft = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")      
        self.nowDate = sft.format(Date())
        
    def initFromEntities(self, qctx):
        qctx.addEntity("PrepareCourse", "pc", "")
    
    def resolveEntity(self, qctx, entity):
        if "u" == entity:
            qctx.addEntity("User", "u", "pc.createUserId = u.userId")#备课发起人
        elif "ul" == entity:
            qctx.addEntity("User", "ul", "pc.leaderId = ul.userId")#备课主备人
        else:
            BaseQuery.resolveEntity(self, qctx, entity)
            
    def applyWhereCondition(self, qctx):
        if self.custormAndWhere != None:
            qctx.addAndWhere(self.custormAndWhere)
        if self.status != None:
            qctx.addAndWhere("pc.status = :status")
            qctx.setInteger("status", self.status)
        if self.prepareCoursePlanId != None:
            qctx.addAndWhere("pc.prepareCoursePlanId = :prepareCoursePlanId")
            qctx.setInteger("prepareCoursePlanId", self.prepareCoursePlanId)
        if self.prepareCourseGenerated != None:
            qctx.addAndWhere("pc.prepareCourseGenerated = :prepareCourseGenerated")
            qctx.setBoolean("prepareCourseGenerated", self.prepareCourseGenerated)
        
        if self.stage != None:
            if "running" == self.stage:
                qctx.addAndWhere("(:stage >= pc.startDate And :stage <= pc.endDate)")
                qctx.setString("stage", self.nowDate)
            if "finished" == self.stage:
                qctx.addAndWhere("(pc.endDate < :stage)")
                qctx.setString("stage", self.nowDate)
            if "new" == self.stage:
                qctx.addAndWhere("(pc.startDate > :stage)")
                qctx.setString("stage", self.nowDate)            
            if "notfinished" == self.stage:
                qctx.addAndWhere("(pc.endDate >= :stage)")
                qctx.setString("stage", self.nowDate)
            if "recommend" == self.stage:
                qctx.addAndWhere("(pc.recommendState = :stage)")
                qctx.setInteger("stage", 1)
                
        if self.prepareCourseId != None:
            qctx.addAndWhere("pc.prepareCourseId = :prepareCourseId")
            qctx.setInteger("prepareCourseId", self.prepareCourseId)
        if self.createUserId != None:
            qctx.addAndWhere("pc.createUserId = :createUserId")
            qctx.setInteger("createUserId", self.createUserId)
        if self.k != None and self.k != '':
            newKey = self.k.replace("'","''").replace("%","[%]").replace("_","[_]").replace("[","[[]")
            if self.ktype == None or self.ktype == '':
                qctx.addAndWhere("pc.title LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            elif self.ktype == "1":#搜索标题
                qctx.addAndWhere("pc.title LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            elif self.ktype == "2":    #搜索发起人
                qctx.addAndWhere("u.nickName LIKE :keyword OR u.trueName LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            elif self.ktype == "3":    #搜索主备人
                qctx.addAndWhere("ul.nickName LIKE :keyword OR ul.trueName LIKE :keyword")
                qctx.setString("keyword", "%" + newKey + "%")
            elif self.ktype == "4":    #搜索主备人所属机构
                qctx.addAndWhere("ul.unit.unitTitle LIKE :keyword ")
                qctx.setString("keyword", "%" + newKey + "%")
        if self.course_BeginDate != None and self.course_BeginDate != '':
            qctx.addAndWhere("pc.startDate >= :startdate")
            qctx.setString("startdate", self.course_BeginDate)
        if self.course_EndDate != None and self.course_EndDate != '':
            qctx.addAndWhere("pc.startDate <= :enddate")
            qctx.setString("enddate", self.course_EndDate)
        if self.subjectId != None:
            qctx.addAndWhere("pc.metaSubjectId = :subjectId")
            qctx.setInteger("subjectId", self.subjectId)
        if self.containChild != None:
            if self.containChild == True:
                if self.gradeId != None:
                    qctx.addAndWhere("pc.gradeId >= :gradeIdMin And pc.gradeId < :gradeIdMax")
                    qctx.setInteger("gradeIdMin", self.convertRoundMinNumber(self.gradeId))
                    qctx.setInteger("gradeIdMax", self.convertRoundMaxNumber(self.gradeId))
            else:
                if self.gradeId != None:
                    qctx.addAndWhere("pc.gradeId = :gradeId")
                    qctx.setInteger("gradeId", self.gradeId)
        else:
            if self.gradeId != None:
                qctx.addAndWhere("pc.gradeId >= :gradeIdMin And pc.gradeId < :gradeIdMax")
                qctx.setInteger("gradeIdMin", self.convertRoundMinNumber(self.gradeId))
                qctx.setInteger("gradeIdMax", self.convertRoundMaxNumber(self.gradeId))
            
    def applyOrderCondition(self, qctx):
        if self.orderType == 0: #id
            qctx.addOrder("pc.prepareCourseId DESC")
        elif self.orderType == 1: # ItemOrder
            qctx.addOrder("pc.itemOrder ASC")
        else:
            qctx.addOrder("pc.prepareCourseId DESC")

    # 将学段是否是整数，例如: 3100 -》 3000
    def convertRoundMinNumber(self, intV):
        if intV == None:
            return 0    
        strV = str(intV)
        if strV.isdigit() == False:
            return 0    
        intStrLen = len(strV)        
        if intStrLen < 2:
            return intV
        strPad = "0"
        for i in range(2, intStrLen):
            strPad = strPad + "0"
            
        strV = strV[0:1] + strPad
        return int(strV)

    # 将学段格式化成整数，例如: 3100 -》 4000
    def convertRoundMaxNumber(self, intV):
        if intV == None:
            return 0    
        strV = str(intV)
        if strV.isdigit() == False:
            return 0    
        intStrLen = len(strV)        
        if intStrLen < 2:
            return intV
        strPad = "0"
        for i in range(2, intStrLen):
            strPad = strPad + "0"
        strV = str(int(strV[0:1]) + 1) + strPad
        return int(strV)


class PrepareArticleQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.prepareCourseId = None
        self.prepareCourseStageId = None
        self.articleState = True
        self.k = self.params.getStringParam("k")
        self.subjectId = self.params.getIntParamZeroAsNull("subjectId")
        self.gradeId = self.params.getIntParamZeroAsNull("gradeId")
        request.setAttribute("subjectId", self.subjectId)
        request.setAttribute("gradeId", self.gradeId)
        request.setAttribute("k", self.k)
        
        self.orderType = 0
        
    def initFromEntities(self, qctx):
        qctx.addEntity("PrepareCourseArticle", "pca", "")
    
    def resolveEntity(self, qctx, entity):
        if "pcs" == entity:
            qctx.addEntity("PrepareCourseStage", "pcs", "pcs.prepareCourseStageId = pca.prepareCourseStageId")
        else:
            BaseQuery.resolveEntity(self, qctx, entity);
      
      
    def applyWhereCondition(self, qctx):
        if (self.prepareCourseStageId != None):
            qctx.addAndWhere("pca.prepareCourseStageId = :prepareCourseStageId")
            qctx.setInteger("prepareCourseStageId", self.prepareCourseStageId)
        if (self.prepareCourseId != None):
            qctx.addAndWhere("pca.prepareCourseId = :prepareCourseId")
            qctx.setInteger("prepareCourseId", self.prepareCourseId)
        if self.articleState != None:
            qctx.addAndWhere("pca.articleState = :articleState")
            qctx.setBoolean("articleState", self.articleState)
            
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("pca.prepareCourseArticleId DESC")
            
class PrepareResourceQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.prepareCourseId = None
        self.prepareCourseStageId = None
        self.k = self.params.getStringParam("k")
        self.subjectId = self.params.getIntParamZeroAsNull("subjectId")
        self.gradeId = self.params.getIntParamZeroAsNull("gradeId")
        request.setAttribute("subjectId", self.subjectId)
        request.setAttribute("gradeId", self.gradeId)
        request.setAttribute("k", self.k)
        
        self.orderType = 0
        
    def initFromEntities(self, qctx):
        qctx.addEntity("PrepareCourseResource", "pcr", "")
             
    def resolveEntity(self, qctx, entity):
        if "r" == entity:
            qctx.addEntity("Resource", "r", "r.resourceId = pcr.resourceId")
        elif "pcs" == entity:
            qctx.addEntity("PrepareCourseStage", "pcs", "pcr.prepareCourseStageId = pcs.prepareCourseStageId")
        else:
            BaseQuery.resolveEntity(qctx, entity);

    def applyWhereCondition(self, qctx):
        if (self.prepareCourseStageId != None):
            qctx.addAndWhere("pcr.prepareCourseStageId = :prepareCourseStageId")
            qctx.setInteger("prepareCourseStageId", self.prepareCourseStageId)
        if (self.prepareCourseId != None):
            qctx.addAndWhere("pcr.prepareCourseId = :prepareCourseId")
            qctx.setInteger("prepareCourseId", self.prepareCourseId)
            
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("pcr.prepareCourseResourceId DESC")
    
    
class PrepareTopicQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.prepareCourseId = None
        self.prepareCourseStageId = None       
        self.userId = None
        self.orderType = 0
        
    def initFromEntities(self, qctx):
        qctx.addEntity("PrepareCourseTopic", "pct", "")
             
    def resolveEntity(self, qctx, entity):
        if "pcs" == entity:
            qctx.addEntity("PrepareCourseStage", "pcs", "pct.prepareCourseStageId = pcs.prepareCourseStageId")
        else:
            BaseQuery.resolveEntity(qctx, entity);
                   
    def applyWhereCondition(self, qctx):
        if (self.prepareCourseStageId != None):
            qctx.addAndWhere("pct.prepareCourseStageId = :prepareCourseStageId")
            qctx.setInteger("prepareCourseStageId", self.prepareCourseStageId)
        if (self.prepareCourseId != None):
            qctx.addAndWhere("pct.prepareCourseId = :prepareCourseId")
            qctx.setInteger("prepareCourseId", self.prepareCourseId)
        if (self.userId != None):
            qctx.addAndWhere("pct.userId = :userId")
            qctx.setInteger("userId", self.userId)
            
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("pct.prepareCourseTopicId DESC")
   
class PrepareRelatedQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.prepareCourseId = None
        self.userId = None
        self.orderType = 0
        
    def initFromEntities(self, qctx):
        qctx.addEntity("PrepareCourseRelated", "pcr", "")
             
    def resolveEntity(self, qctx, entity):
        if "pc" == entity:
            qctx.addEntity("PrepareCourse", "pc", "pc.prepareCourseId = pcr.relatedPrepareCourseId")
        else:
            BaseQuery.resolveEntity(qctx, entity);
                   
    def applyWhereCondition(self, qctx):
        if (self.prepareCourseId != None):
            qctx.addAndWhere("pcr.prepareCourseId = :prepareCourseId")
            qctx.setInteger("prepareCourseId", self.prepareCourseId)
        if (self.userId != None):
            qctx.addAndWhere("pcr.userId = :userId")
            qctx.setInteger("userId", self.userId)
            
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("pcr.relatedPrepareCourseId DESC")
            
class PrepareTopicReplyQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.prepareCourseId = None
        self.prepareCourseTopicId = None
        self.prepareCourseStageId = None       
        self.userId = None
        self.orderType = 0
        
    def initFromEntities(self, qctx):
        qctx.addEntity("PrepareCourseTopicReply", "pctr", "")   
                  
    def resolveEntity(self, qctx, entity):
        if "pct" == entity:
            qctx.addEntity("PrepareCourseTopic", "pct", "pct.prepareCourseTopicId = pctr.prepareCourseTopicId")
        else:
            BaseQuery.resolveEntity(qctx, entity);
            
    def applyWhereCondition(self, qctx):
        if (self.prepareCourseStageId != None):
            qctx.addAndWhere("pctr.prepareCourseStageId = :prepareCourseStageId")
            qctx.setInteger("prepareCourseStageId", self.prepareCourseStageId)
        if (self.prepareCourseId != None):
            qctx.addAndWhere("pctr.prepareCourseId = :prepareCourseId")
            qctx.setInteger("prepareCourseId", self.prepareCourseId)
        if (self.prepareCourseTopicId != None):
            qctx.addAndWhere("pctr.prepareCourseTopicId = :prepareCourseTopicId")
            qctx.setInteger("prepareCourseTopicId", self.prepareCourseTopicId)
        if (self.userId != None):
            qctx.addAndWhere("pctr.userId = :userId")
            qctx.setInteger("userId", self.userId)
            
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("pctr.prepareCourseTopicReplyId ASC")
            
            
class PrepareCourseMemberQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.prepareCourseId = None
        self.userId = None
        self.userkey = None
        self.orderType = 0
        self.status = None
        self.privateContentExist = None   #个案内容是否为空  True查询有内容的，False查询空的
        
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
        if self.status != None:
            qctx.addAndWhere("pcm.status = :status")
            qctx.setInteger("status", self.status)
        if (self.userkey != None):
            qctx.addAndWhere('(u.trueName Like :kk Or u.loginName Like :kk)')
            qctx.setString('kk', '%' + self.userkey + '%')
        if self.privateContentExist != None:
            if self.privateContentExist == True:
                qctx.addAndWhere("DATALENGTH(pcm.privateContent) > :Content")   #SQLServer中Len函数对 nText Text Image等无效，改用DATALENGTH
                qctx.setInteger("Content", 0)
            if self.privateContentExist == False:
                qctx.addAndWhere("DATALENGTH(pcm.privateContent) = :Content")   #SQLServer中Len函数对 nText Text Image等无效
                qctx.setInteger("Content", 0)        
                    
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("pcm.prepareCourseMemberId DESC")
        if self.orderType == 1: #按最后更新日期
            qctx.addOrder("pcm.contentLastupdated DESC")
        if self.orderType == 9: #按精华排序
            qctx.addOrder("pcm.bestState DESC")

class PrepareCourseVideoQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.prepareCourseId = None
        self.userId = None
        self.orderType = 0
        
    def initFromEntities(self, qctx):
        qctx.addEntity("PrepareCourseVideo", "pcv", "")
    
    def resolveEntity(self, qctx, entity):
        if "u" == entity:
            qctx.addEntity("User", "u", "pcv.userId = u.userId")
        elif "pc" == entity:
            qctx.addEntity("PrepareCourse", "pc", "pcv.prepareCourseId = pc.prepareCourseId")
        elif "v" == entity:
            qctx.addEntity("Video", "v", "pcv.videoId = v.videoId")
        else:
            BaseQuery.resolveEntity(self, qctx, entity)
            
    def applyWhereCondition(self, qctx):
        if (self.prepareCourseId != None):
            qctx.addAndWhere("pcv.prepareCourseId = :prepareCourseId")
            qctx.setInteger("prepareCourseId", self.prepareCourseId)
        if (self.userId != None):
            qctx.addAndWhere("pcv.userId = :userId")
            qctx.setInteger("userId", self.userId)
                    
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("pcv.prepareCourseVideoId DESC")
        if self.orderType == 1: #按最后更新日期
            qctx.addOrder("pcv.createDate DESC")
                        
class PrepareCoursePrivateCommentQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.prepareCourseId = None
        self.commentedUserId = None
        self.commentUserId = None
        self.orderType = 0
    
    def initFromEntities(self, qctx):
        qctx.addEntity("PrepareCoursePrivateComment", "pcpc", "")
    
    def resolveEntity(self, qctx, entity):
        if "u" == entity:
            qctx.addEntity("User", "u", "pcpc.commentedUserId = u.userId")
        elif "pc" == entity:
            qctx.addEntity("PrepareCourse", "pc", "pcpc.prepareCourseId = pc.prepareCourseId")       
        else:
            BaseQuery.resolveEntity(self, qctx, entity)
            
    def applyWhereCondition(self, qctx):
        if (self.prepareCourseId != None):
            qctx.addAndWhere("pcpc.prepareCourseId = :prepareCourseId")
            qctx.setInteger("prepareCourseId", self.prepareCourseId)
        if (self.commentedUserId != None):
            qctx.addAndWhere("pcpc.commentedUserId = :commentedUserId")
            qctx.setInteger("commentedUserId", self.commentedUserId)
        if (self.commentUserId != None):
            qctx.addAndWhere("pcpc.commentUserId = :commentUserId")
            qctx.setInteger("commentUserId", self.commentUserId)

    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("pcpc.prepareCoursePrivateCommentId ASC")
            
            
class PrepareCoursePlanQuery(BaseQuery):
    def __init__(self, selectFields):
        BaseQuery.__init__(self, selectFields)
        self.params = ParamUtil(request)
        self.groupId = None
        self.defaultPlan = None  
        self.k = self.params.getStringParam("k")
        self.subjectId = self.params.getIntParamZeroAsNull("subjectId")
        self.gradeId = self.params.getIntParamZeroAsNull("gradeId")
        request.setAttribute("subjectId", self.subjectId)
        request.setAttribute("gradeId", self.gradeId)
        request.setAttribute("k", self.k)        
        self.orderType = 0
        
    def initFromEntities(self, qctx):
        qctx.addEntity("PrepareCoursePlan", "pcp", "")
    
    def resolveEntity(self, qctx, entity):
        if "u" == entity:
            qctx.addEntity("User", "u", "pcp.createUserId = u.userId")
        else:
            BaseQuery.resolveEntity(qctx, entity);
      
      
    def applyWhereCondition(self, qctx):
        if (self.groupId != None):
            qctx.addAndWhere("pcp.groupId = :groupId")
            qctx.setInteger("groupId", self.groupId)
        if self.defaultPlan != None:
            qctx.addAndWhere("pcp.defaultPlan = :defaultPlan")
            qctx.setBoolean("defaultPlan", self.defaultPlan)
            
    def applyOrderCondition(self, qctx):
        if self.orderType == 0:
            qctx.addOrder("pcp.prepareCoursePlanId DESC")
