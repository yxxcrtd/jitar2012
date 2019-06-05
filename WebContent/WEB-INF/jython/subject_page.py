# -*- coding: utf-8 -*-
from cn.edustar.jitar.jython import JythonBaseAction
from cn.edustar.jitar.util import ParamUtil, CommonUtil
from cn.edustar.jitar.data import BaseQuery
from cn.edustar.jitar.pojos import User, AccessControl
from java.lang import String
from cn.edustar.data.paging import PagingQuery
from user_subject_grade_query import UserSubjectGradeQuery

class BaseSubject(JythonBaseAction):
    ERROR = "/WEB-INF/ftl/Error.ftl"
    ACCESS_DENIED = "/WEB-INF/ftl/Error.ftl"
    ACCESS_ERROR = "/WEB-INF/ftl/mengv1/access_error.ftl"
    SUCCESS = "/WEB-INF/ftl/success.ftl"
    LOGIN = "/login.jsp"
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.userIds = ""
        self.unitId = self.params.getIntParamZeroAsNull("unitId")
        self.metaGradeId = self.params.safeGetIntParam("gradeId")
        self.metaSubjectId = self.params.safeGetIntParam("subjectId")
        self.subjectId = self.params.safeGetIntParam("id")
        self.subjectService = __spring__.getBean("subjectService")
        self.subjectCode = request.getAttribute("subjectCode")
        self.accessControlService = __spring__.getBean("accessControlService")
        
        if self.unitId != None and self.unitId != 0:
            request.setAttribute("unitId", self.unitId)
            
        if self.subjectService == None:
            self.write("Cann't load subjectService !")
            return
        self.subject = None
        self.subject = self.subjectService.getSubjectByMetaData(self.metaSubjectId, self.metaGradeId)
        if self.subject == None:
            self.subject = self.subjectService.getSubjectById(self.subjectId)
        if self.subject == None:
            self.subject = self.subjectService.getSubjectByCode(self.subjectCode)        

        if self.subject == None:
            # self.write("Object not be found !")
            self.write("Cann't load Subject Object !")
            return
        if self.isAdmin() == True or self.isContentAdmin() == True or self.isUserAdmin() == True:
            request.setAttribute("isAdmin", "")
        self.metaSubjectId = self.subject.metaSubject.msubjId
        self.metaGradeId = self.subject.metaGrade.gradeId
        self.subjectCode = self.subject.getSubjectCode()        
        
        self.subjectRootUrl = request.getAttribute("SubjectRootUrl")        
        if self.subjectRootUrl == None:
            configSubjectSiteRoot = request.getSession().getServletContext().getInitParameter("subjectUrlPattern")
            if configSubjectSiteRoot == None or configSubjectSiteRoot == "":
                self.subjectRootUrl = self.getCurrentSiteUrl(request) + "k/" + self.subject.subjectCode + "/"
            else:
                configSubjectSiteRoot = String(configSubjectSiteRoot)
                self.subjectRootUrl = configSubjectSiteRoot.replaceAll("\\{subjectCode\\}", self.subject.subjectCode)
        request.setAttribute("SubjectRootUrl", self.subjectRootUrl)
        cache = __jitar__.cacheProvider.getCache('sitenav')
        cache_k = "subject_nav_" + str(self.subject.subjectId)
        subjectSiteNavList = cache.get(cache_k)
        if subjectSiteNavList == None:
            subjectSiteNavList = __spring__.getBean("siteNavService").getAllSiteNav(False, 2, self.subject.subjectId)
            cache.put(cache_k, subjectSiteNavList)
        request.setAttribute("SubjectSiteNavList", subjectSiteNavList)
        
        #得到多学科用户
        qry2 = UserSubjectGradeQuery("usg.userId")
        qry2.subjectId = self.get_current_subjectId()
        qry2.gradeId = self.get_current_gradeId()
        qry2.FuzzyMatch = True #匹配学段，包括年级
        usg_list = qry2.query_map(qry2.count())
        if usg_list != None and len(usg_list)>0:
            for uu  in usg_list:
                self.userIds += str(uu["userId"]) + ","
        if self.userIds != "" and self.userIds.endswith(","):
            self.userIds = self.userIds[0:len(self.userIds)-1]
            
    def isAdmin(self):
        t = self.accessControlService.userIsSubjectAdmin(self.loginUser, self.subject)
        if t == True:
            request.setAttribute("isSystemAdmin", "1")
            request.setAttribute("isAdmin", "1")
        else:
            request.setAttribute("isSystemAdmin", "0")
        return t
    
    def isContentAdmin(self):
        t = self.accessControlService.userIsSubjectContentAdmin(self.loginUser, self.subject)
        if t == True:
            request.setAttribute("isContentAdmin", "1")
        else:
            request.setAttribute("isContentAdmin", "0")
        return t
    
    def isUserAdmin(self):
        t = self.accessControlService.userIsSubjectUserAdmin(self.loginUser, self.subject)
        if t == True:
            request.setAttribute("isUserAdmin", "1")
        else:
            request.setAttribute("isUserAdmin", "0")
        return t
    
    def clear_subject_cache(self):
        if self.subjectId != 0:
            cache = __jitar__.cacheProvider.getCache('subject')
            if cache == None:
                return
            cache_list = cache.getAllKeys()
            cache_key_head = "sbj" + str(self.subjectId)
            for c in cache_list:
                if c.split("_")[0] == cache_key_head:
                    cache.remove(c)
                            
    def getCurrentSiteUrl(self, request):
        return CommonUtil.getSiteUrl(request)
    
    #名师工作室.
    def get_famous_list(self):
        minNum = CommonUtil.convertRoundMinNumber(self.get_current_gradeId())
        maxNum = CommonUtil.convertRoundMaxNumber(self.get_current_gradeId())
        strOrderBy = "UserId DESC"
        strWhereClause = "(UserStatus=0 And UserType LIKE '%/1/%' "
        strWhere1 = strWhereClause + " And GradeId >= " + str(minNum) + " And GradeId<" + str(maxNum) + " And SubjectId=" + str(self.get_current_subjectId())
        strWhere1 += ")"
        strWhere2 = ""
        
        if self.userIds != "":
            strWhere2 = " And UserId In(" + self.userIds + ")"
        
        if strWhere2 != "":
            strWhere2 = strWhereClause + strWhere2 + ")"
        if strWhere2 == "":
            strWhereClause = strWhere1
        else:
            strWhereClause = strWhere1 + " Or " + strWhere2
        pagingService = __spring__.getBean("pagingService")
        pagingQuery = PagingQuery()
        pagingQuery.keyName = "UserId"
        pagingQuery.fetchFieldsName = "*"
        pagingQuery.spName = "findPagingUser"
        pagingQuery.tableName = "Jitar_User"
        pagingQuery.whereClause = strWhereClause
        pagingQuery.orderByFieldName  = strOrderBy
        pagingQuery.topCount = 6
        famous_list = pagingService.getPagingList(pagingQuery)
        return famous_list
        #request.setAttribute("famous_list", famous_list)
        
    # 学科带头人工作室.
    def get_expert_list(self):
        #qry = UserQuery("""  u.loginName, u.blogName, u.nickName,u.trueName, u.userIcon, u.blogIntroduce,u.articleCount, subj.subjectId """)
        #qry.isExpert = True
        #qry.FuzzyMatch = True
        #qry.metaSubjectId = self.get_current_subjectId()
        #qry.metaGradeId = self.get_current_gradeId()
        #expert_list = qry.query_map(3)
        
        minNum = CommonUtil.convertRoundMinNumber(self.get_current_gradeId())
        maxNum = CommonUtil.convertRoundMaxNumber(self.get_current_gradeId())
        strOrderBy = "UserId DESC"
        strWhereClause = "(UserStatus=0 And UserType LIKE '%/3/%' "
        strWhere1 = strWhereClause + " And GradeId >= " + str(minNum) + " And GradeId<" + str(maxNum) + " And SubjectId=" + str(self.get_current_subjectId())
        strWhere1 += ")"
        strWhere2 = ""
        
        if self.userIds != "":
            strWhere2 = " And UserId In(" + self.userIds + ")"
        
        if strWhere2 != "":
            strWhere2 = strWhereClause + strWhere2 + ")"
        if strWhere2 == "":
            strWhereClause = strWhere1
        else:
            strWhereClause = strWhere1 + " Or " + strWhere2
        pagingService = __spring__.getBean("pagingService")
        pagingQuery = PagingQuery()
        pagingQuery.keyName = "UserId"
        pagingQuery.fetchFieldsName = "*"
        pagingQuery.spName = "findPagingUser"
        pagingQuery.tableName = "Jitar_User"
        pagingQuery.whereClause = strWhereClause
        pagingQuery.orderByFieldName  = strOrderBy
        pagingQuery.topCount = 3
        expert_list = pagingService.getPagingList(pagingQuery)
        return expert_list
        #request.setAttribute("expert_list", expert_list)
        
    # 工作室访问排行.
    def get_hot_list(self, topCount):
        minNum = CommonUtil.convertRoundMinNumber(self.get_current_gradeId())
        maxNum = CommonUtil.convertRoundMaxNumber(self.get_current_gradeId())
        strOrderBy = "VisitCount DESC"
        strWhereClause = "(UserStatus=0 "
        strWhere1 = strWhereClause + " And GradeId >= " + str(minNum) + " And GradeId<" + str(maxNum) + " And SubjectId=" + str(self.get_current_subjectId())
        strWhere1 += ")"
        strWhere2 = ""
        
        if self.userIds != "":
            strWhere2 = " And UserId In(" + self.userIds + ")"
        
        if strWhere2 != "":
            strWhere2 = strWhereClause + strWhere2 + ")"
        if strWhere2 == "":
            strWhereClause = strWhere1
        else:
            strWhereClause = strWhere1 + " Or " + strWhere2
        pagingService = __spring__.getBean("pagingService")
        pagingQuery = PagingQuery()
        pagingQuery.keyName = "UserId"
        pagingQuery.fetchFieldsName = "*"
        pagingQuery.spName = "findPagingUser"
        pagingQuery.tableName = "Jitar_User"
        pagingQuery.whereClause = strWhereClause
        pagingQuery.orderByFieldName  = strOrderBy
        pagingQuery.topCount = topCount
        hot_list = pagingService.getPagingList(pagingQuery)
        return hot_list
    
    # 最新工作室排行.
    def get_new_list(self, topCount):
        minNum = CommonUtil.convertRoundMinNumber(self.get_current_gradeId())
        maxNum = CommonUtil.convertRoundMaxNumber(self.get_current_gradeId())
        strOrderBy = "UserId DESC"
        strWhereClause = "(UserStatus=0 "
        strWhere1 = strWhereClause + " And GradeId >= " + str(minNum) + " And GradeId<" + str(maxNum) + " And SubjectId=" + str(self.get_current_subjectId())
        strWhere1 += ")"
        strWhere2 = ""
        
        if self.userIds != "":
            strWhere2 = " And UserId In(" + self.userIds + ")"
        
        if strWhere2 != "":
            strWhere2 = strWhereClause + strWhere2 + ")"
        if strWhere2 == "":
            strWhereClause = strWhere1
        else:
            strWhereClause = strWhere1 + " Or " + strWhere2
        pagingService = __spring__.getBean("pagingService")
        pagingQuery = PagingQuery()
        pagingQuery.keyName = "UserId"
        pagingQuery.fetchFieldsName = "*"
        pagingQuery.spName = "findPagingUser"
        pagingQuery.tableName = "Jitar_User"
        pagingQuery.whereClause = strWhereClause
        pagingQuery.orderByFieldName  = strOrderBy
        pagingQuery.topCount = topCount
        hot_list = pagingService.getPagingList(pagingQuery)
        return hot_list
    
    # 推荐工作室.
    def get_rcmd_list(self, topCount):
        minNum = CommonUtil.convertRoundMinNumber(self.get_current_gradeId())
        maxNum = CommonUtil.convertRoundMaxNumber(self.get_current_gradeId())
        strOrderBy = "UserId DESC"
        strWhereClause = "(UserStatus=0 And UserType LIKE '%/2/%' "
        strWhere1 = strWhereClause + " And GradeId >= " + str(minNum) + " And GradeId<" + str(maxNum) + " And SubjectId=" + str(self.get_current_subjectId())
        strWhere1 += ")"
        strWhere2 = ""
        
        if self.userIds != "":
            strWhere2 = " And UserId In(" + self.userIds + ")"
        
        if strWhere2 != "":
            strWhere2 = strWhereClause + strWhere2 + ")"
        if strWhere2 == "":
            strWhereClause = strWhere1
        else:
            strWhereClause = strWhere1 + " Or " + strWhere2
        pagingService = __spring__.getBean("pagingService")
        pagingQuery = PagingQuery()
        pagingQuery.keyName = "UserId"
        pagingQuery.fetchFieldsName = "*"
        pagingQuery.spName = "findPagingUser"
        pagingQuery.tableName = "Jitar_User"
        pagingQuery.whereClause = strWhereClause
        pagingQuery.orderByFieldName  = strOrderBy
        pagingQuery.topCount = topCount
        hot_list = pagingService.getPagingList(pagingQuery)
        return hot_list
    
    # 得到学科教研员列表.
    def get_subject_comissioner(self):
        #qry = UserQuery(""" u.loginName, u.nickName, u.trueName,u.userIcon, u.blogName, u.createDate, 
        #                    u.myArticleCount, u.otherArticleCount, u.resourceCount, u.blogIntroduce,u.articleCount """)
        #qry.setSubjectCondition(self.subject)
        #qry.isComissioner = True
        #qry.FuzzyMatch = True
        #qry.metaSubjectId = self.get_current_subjectId()
        #qry.metaGradeId = self.get_current_gradeId()
        #comissioner_list = qry.query_map(6)
        
        minNum = CommonUtil.convertRoundMinNumber(self.get_current_gradeId())
        maxNum = CommonUtil.convertRoundMaxNumber(self.get_current_gradeId())
        strOrderBy = "VisitCount DESC"
        strWhereClause = "(UserStatus=0 And UserType LIKE '%/4/%' "
        strWhere1 = strWhereClause + " And GradeId >= " + str(minNum) + " And GradeId<" + str(maxNum) + " And SubjectId=" + str(self.get_current_subjectId())
        strWhere1 += ")"
        strWhere2 = ""
       
        if self.userIds != "":
            strWhere2 = " And UserId In(" + self.userIds + ")"
        
        if strWhere2 != "":
            strWhere2 = strWhereClause + strWhere2 + ")"
        if strWhere2 == "":
            strWhereClause = strWhere1
        else:
            strWhereClause = strWhere1 + " Or " + strWhere2
        pagingService = __spring__.getBean("pagingService")
        pagingQuery = PagingQuery()
        pagingQuery.keyName = "UserId"
        pagingQuery.fetchFieldsName = "*"
        pagingQuery.spName = "findPagingUser"
        pagingQuery.tableName = "Jitar_User"
        pagingQuery.whereClause = strWhereClause
        pagingQuery.orderByFieldName  = strOrderBy
        pagingQuery.topCount = 6
        comissioner_list = pagingService.getPagingList(pagingQuery)
        return comissioner_list
        #request.setAttribute("comissioner_list", comissioner_list)   
        
    def get_current_subjectId(self):
        subjectId = self.subject.metaSubject.msubjId
        request.setAttribute("subjectId" , subjectId)
        return subjectId    
    
    def get_current_gradeId(self):
        gradeId = self.subject.metaGrade.gradeId
        request.setAttribute("gradeId", gradeId)
        return gradeId
    

class NoCache:
    def get(self, key):
        return None
    def put(self, key):
        pass
    def put(self, key, ttl):
        pass
    def remove(self, key):
        pass
    def clear(self):
        pass
