from user_query import UserQuery
from cn.edustar.jitar.pojos import User, AccessControl
from subject_page import *
from cn.edustar.data.paging import PagingQuery
from cn.edustar.jitar.util import CommonUtil
from user_subject_grade_query import UserSubjectGradeQuery
from cn.edustar.data import Pager

class subjectuser(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.userService = __spring__.getBean("userService")
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN        

        if self.isAdmin() == False and self.isUserAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        if request.getMethod() == "POST":
            self.clear_subject_cache()
            self.save_post()
        self.list_user()
        
        request.setAttribute("cmd", self.params.safeGetStringParam("cmd"))
        request.setAttribute("subject", self.subject)
        return "/WEB-INF/subjectmanage/subjectuser.ftl"
        
    def list_user(self):
        strWhereClause = "(1=1 "
        
        k = self.params.safeGetStringParam("k")
        f = self.params.safeGetStringParam("f")
        ustate = self.params.safeGetStringParam("ustate")
        if ustate == "":
            ustate = "-1"
        #qry = UserQuery(""" u.userId, u.loginName, u.nickName, u.trueName, u.userIcon, u.userStatus, 
        #                   u.email, u.subjectId, u.gradeId, u.createDate,
        #                   u.isExpert ,u.isFamous,u.isRecommend,u.isComissioner,u.unitId 
        #                """)
        #qry.orderType = 0
        if ustate == "0":
            strWhereClause += " And UserStatus = 0"
            #qry.userStatus = 0
        elif ustate == "1":
            strWhereClause += " And UserStatus = 1"
            #qry.userStatus = 1
        elif ustate == "2":
            strWhereClause += " And UserStatus = 2"
            #qry.userStatus = 2
        elif ustate == "3":
            strWhereClause += " And UserStatus = 3"
            #qry.userStatus = 3
        else:
            print ""
            #qry.userStatus = None
        if k != "":
            #qry.f = f
            #qry.k = k
            request.setAttribute("k", k)
            request.setAttribute("f", f)
            if f == "loginName":
                strWhereClause += " And LoginName LIKE '%" + k + "%'"
            elif f == "trueName":
                strWhereClause += " And TrueName LIKE '%" + k + "%'"
        
        minNum = CommonUtil.convertRoundMinNumber(self.get_current_gradeId())
        maxNum = CommonUtil.convertRoundMaxNumber(self.get_current_gradeId())
        strOrderBy = "UserId DESC"
        
        strWhere1 = strWhereClause + " And GradeId >= " + str(minNum) + " And GradeId<" + str(maxNum) + " And SubjectId=" + str(self.get_current_subjectId())
        strWhere1 += ")"
        strWhere2 = ""
        #得到多学科用户
        self.userIds = ""
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
        print totalCount
        request.setAttribute("ustate", ustate)
        request.setAttribute("pager", pager)       
        request.setAttribute("user_list" , user_list)
        
    def save_post(self):
        cmd = self.params.safeGetStringParam("cmd")
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            user = self.userService.getUserById(g)
            if user != None:
                if cmd == "removefromsubject":
                    self.removefromsubject(user)
                elif cmd == "content_admin":
                    self.set_admin(user, "content")
                elif cmd == "uncontent_admin":       
                    self.set_admin(user, "uncontent")
                elif cmd == "user_admin":
                    self.set_admin(user, "user")
                elif cmd == "unuser_admin":
                    self.set_admin(user, "unuser")
                elif cmd == "unaudit":
                    self.unaudit(user)
                elif cmd == "audit":
                    self.audit(user)
                elif cmd == "unDelSel":
                    self.unDelSel(user)
    def removefromsubject(self,user):
        #print "从学科用户中删除用户....."
        usgs=self.userService.getUserSubjectGradeListByUserId(user.userId);
        for usg in usgs:
            #print "usg.subjectId="+str(usg.subjectId)
            #print "当前学科是self.subject.subjectId="+str(self.subject.subjectId)
            #print "当前学科是self.subject.metaSubjectId="+str(self.subject.metaSubject.msubjId)
            if self.subject.metaSubject.msubjId==usg.subjectId:
                #print "删除usg"  
                self.userService.deleteUserSubjectGrade(usg)
        if self.subject.subjectId==user.subjectId:
            user.setSubjectId(None)
            user.setGradeId(None)
            self.userService.updateUser(user)
        
    def unDelSel(self,user):
        if user.userStatus == User.USER_STATUS_DELETED:
            return True
        self.userService.updateUserStatus(user, User.USER_STATUS_DELETED)
        
    def audit(self,user):
        if user.userStatus != User.USER_STATUS_WAIT_AUTID:
            #self.addActionError(u"用户 " + user.toDisplayString() + u" 状态不是等待审核, 不能进行审核通过操作.")
            return False
        self.userService.updateUserStatus(user, User.USER_STATUS_NORMAL)
        
    def unaudit(self,user):
        if user.userStatus == User.USER_STATUS_WAIT_AUTID:
            #self.addActionError(u"用户 " + user.toDisplayString() + u" 状态不是等待审核, 不能进行审核通过操作.")
            return True
        self.userService.updateUserStatus(user, User.USER_STATUS_WAIT_AUTID)

    def get_current_subjectId(self):
        subjectId = self.subject.metaSubject.msubjId
        return subjectId    
    
    def get_current_gradeId(self):
        gradeId = self.subject.metaGrade.gradeId
        return gradeId
                    
    def set_admin(self, user, admin_type):
        accessControlService = __spring__.getBean("accessControlService")
        if admin_type == "content":
            if accessControlService.checkUserAccessControlIsExists(user.userId, AccessControl.OBJECTTYPE_SUBJECTCONTENTADMIN, self.subject.subjectId) == False:
                accessControl = AccessControl()
                accessControl.setUserId(user.userId)
                accessControl.setObjectType(AccessControl.OBJECTTYPE_SUBJECTCONTENTADMIN)
                accessControl.setObjectId(self.subject.subjectId)
                accessControl.setObjectTitle(self.subject.subjectName)
                accessControlService.saveOrUpdateAccessControl(accessControl)
        elif admin_type == "uncontent":                
            if accessControlService.checkUserAccessControlIsExists(user.userId, AccessControl.OBJECTTYPE_SUBJECTCONTENTADMIN, self.subject.subjectId) == True:
                accessControlService.deleteAccessControlByUserIdObjectTypeObjectId(user.userId, AccessControl.OBJECTTYPE_SUBJECTCONTENTADMIN, self.subject.subjectId)
        elif admin_type == "user":                
            if accessControlService.checkUserAccessControlIsExists(user.userId, AccessControl.OBJECTTYPE_SUBJECTUSERADMIN, self.subject.subjectId) == False:
                accessControl = AccessControl()
                accessControl.setUserId(user.userId)
                accessControl.setObjectType(AccessControl.OBJECTTYPE_SUBJECTUSERADMIN)
                accessControl.setObjectId(self.subject.subjectId)
                accessControl.setObjectTitle(self.subject.subjectName)
                accessControlService.saveOrUpdateAccessControl(accessControl)
        elif admin_type == "unuser":                
            if accessControlService.checkUserAccessControlIsExists(user.userId, AccessControl.OBJECTTYPE_SUBJECTUSERADMIN, self.subject.subjectId) == True:
                accessControlService.deleteAccessControlByUserIdObjectTypeObjectId(user.userId, AccessControl.OBJECTTYPE_SUBJECTUSERADMIN, self.subject.subjectId)
        
