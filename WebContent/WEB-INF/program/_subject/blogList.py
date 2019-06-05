#encoding=utf-8
from subject_page import *
from user_query import UserQuery
from cn.edustar.data import Pager
from cn.edustar.jitar.util import CommonUtil

class blogList(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
    
    def execute(self):
        if self.subject == None:
            self.addActionError(u"无法加载指定的学科。")
            return self.ERROR
        
        self.templateName = "template1"
        if self.subject.templateName != None:
            self.templateName = self.subject.templateName
        minNum = CommonUtil.convertRoundMinNumber(self.get_current_gradeId())
        maxNum = CommonUtil.convertRoundMaxNumber(self.get_current_gradeId())
        strOrderBy = "UserId DESC"
        strWhereClause = "(UserStatus=0 "
        type = self.params.safeGetStringParam("type")
        #qry = UserQuery(""" u.loginName, u.articleCount, u.userIcon, u.blogName, u.nickName, u.trueName, u.blogIntroduce,u.createDate, u.visitCount, 
        #                u.articleCount, u.resourceCount, u.commentCount,u.userScore """)
        #qry.userStatus = 0
        #qry.FuzzyMatch = True
        #qry.metaSubjectId = self.get_current_subjectId()
        #qry.metaGradeId = self.get_current_gradeId()
        
        Page_Title = self.params.safeGetStringParam("title")
        if "comiss" == type:
            #qry.isComissioner = True
            strWhereClause += " And UserType LIKE '%/4/%' "
            if Page_Title == "":
                Page_Title = u"教研员工作室"
        elif "famous" == type:
            #qry.isFamous = True
            strWhereClause += " And UserType LIKE '%/1/%'"
            if Page_Title == "":
                Page_Title = u"名师工作室"
        elif "expert" == type:
            #qry.isFamous = True
            strWhereClause += " And UserType LIKE '%/3/%'"
            if Page_Title == "":
                Page_Title = u"学科带头人"
        elif "new" == type:
            if Page_Title == "":
                Page_Title = u"最新工作室"
        elif "hot" == type:
            strOrderBy = "VisitCount DESC"
            #qry.orderType = UserQuery.ORDER_TYPE_VISITCOUNT_DESC
            if Page_Title == "":
                Page_Title = u"热门工作室"
        elif "rcmd" == type:
            strWhereClause += " And UserType LIKE '%/2/%'"
            #qry.isRecommend = True
            if Page_Title == "":
                Page_Title = u"推荐工作室"      
        else:
            if Page_Title == "":
                Page_Title = u"工作室"
        
        strWhere1 = strWhereClause + " And GradeId >= " + str(minNum) + " And GradeId<" + str(maxNum) + " And SubjectId=" + str(self.get_current_subjectId())
        strWhere1 += ")"
        strWhere2 = ""
        #得到多学科用户
        qry2 = UserSubjectGradeQuery("usg.userId")
        qry2.subjectId = self.get_current_subjectId()
        qry2.gradeId = self.get_current_gradeId()
        qry2.FuzzyMatch = True #匹配学段，包括年级
        usg_list = qry2.query_map(qry2.count())
        userIds = ""
        if usg_list != None and len(usg_list)>0:
            for uu  in usg_list:
                userIds += str(uu["userId"]) + ","
        if userIds != "" and userIds.endswith(","):
            userIds = userIds[0:len(userIds)-1]
            strWhere2 = " And UserId In(" + userIds + ")"
        
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
        totalCount = self.params.safeGetIntParam("totalCount")
        pager = Pager()
        pager.setCurrentPage(self.params.safeGetIntParam("page", 1))
        pager.setPageSize(20)
        pager.setItemNameAndUnit(u"用户", u"个")
        pager.setUrlPattern(self.params.generateUrlPattern())
        if totalCount == 0:
            pager.setTotalRows(pagingService.getRowsCount(pagingQuery))
        else:
            pager.setTotalRows(totalCount)
            
        user_list = pagingService.getPagingList(pagingQuery, pager)
        request.setAttribute("Page_Title", Page_Title)
        request.setAttribute("user_list", user_list)
        request.setAttribute("pager", pager)
        request.setAttribute("subject", self.subject)        
        return "/WEB-INF/subjectpage/" + self.templateName + "/blogList.ftl"            
            
    def createPager(self):
        pager = self.params.createPager()
        pager.pageSize = 10
        pager.itemName = u"工作室"
        pager.itemUnit = u"个"
        return pager
    
    def get_current_subjectId(self):
        subjectId = self.subject.metaSubject.msubjId
        request.setAttribute("subjectId" , subjectId)
        return subjectId
    
    
    def get_current_gradeId(self):
        gradeId = self.subject.metaGrade.gradeId
        request.setAttribute("gradeId", gradeId)
        return gradeId
