from base_action import *
from cn.edustar.jitar.util import ParamUtil, CommonUtil,FileCache
from cn.edustar.jitar.pojos import UserSubjectGrade, User
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.model import Configure
from cn.edustar.jitar.pojos import User

class user_subject_grade_edit(ActionExecutor):
    def __init__(self):
        self.params = ParamUtil(request)
        self.userService = __jitar__.userService
    def execute(self):
        user = self.loginUser
        if user == None:
            response.getWriter().write(u"请先登录。")
            return
                
        cmd = self.params.safeGetStringParam("cmd")
        gradeId = self.params.getIntParamZeroAsNull("gradeId")
        subjectId = self.params.getIntParamZeroAsNull("subjectId")
        if cmd == None or cmd == "":
            response.getWriter().write(u"无效的命令。")
            return
        
        if cmd.startswith("delete_"):
            userSubjectGradeId = cmd.replace("delete_", "")
            if userSubjectGradeId == "" or CommonUtil.isInteger(userSubjectGradeId) == False:
                response.getWriter().write(u"标识不是整数。")
                return
            usg = self.userService.getUserSubjectGradeById(int(userSubjectGradeId))
            if usg != None:
                self.userService.deleteUserSubjectGrade(usg)
            user = self.userService.getUserById(user.userId, False)
            self.unAuditUser(user)
            response.getWriter().write(u"success")
            return
        if cmd == "edit":            
            if self.validSubjectGrade(gradeId,subjectId) == False:
                response.getWriter().write(u"你选择的学科不存在。")
                return
            usgList = self.userService.getAllUserSubjectGradeListByUserId(user.userId)
            if usgList == None or len(usgList) == 0:
                response.getWriter().write(u"没有该用户的学段/学科记录？这种情况一般不会发生。")
                return
            for usg in usgList:
                if usg.subjectId == subjectId and usg.gradeId == gradeId:
                    response.getWriter().write(u"您已经是该学段/学科的成员了，请勿重复添加。")
                    return
                if usg.subjectId == subjectId and usg.gradeId != None and gradeId != None:
                    strG1 = str(usg.gradeId)
                    strG2 = str(gradeId)
                    if strG1[0:1] == strG2[0:1]:
                        response.getWriter().write(u"您已经是该学段/学科的成员了，没有必要再添加。")
                        return
                    
            user = self.userService.getUserById(user.userId, False)
            if user.subjectId == subjectId and user.gradeId == gradeId:
                response.getWriter().write(u"您已经是该学段/学科了，无需再次修改。")
                return
            user.subjectId = subjectId
            user.gradeId = gradeId
            self.userService.updateUser(user)
            self.unAuditUser(user)
            response.getWriter().write("success")
            
        if cmd == "add":            
            if self.validSubjectGrade(gradeId,subjectId) == False:
                response.getWriter().write(u"你选择的学科不存在。")
                return
            usgList = self.userService.getAllUserSubjectGradeListByUserId(user.userId)
            if usgList == None or len(usgList) == 0:
                response.getWriter().write(u"没有该用户的学段/学科记录？这种情况一般不会发生。")
                return
            for usg in usgList:
                if usg.subjectId == subjectId and usg.gradeId == gradeId:
                    response.getWriter().write(u"您已经是该学段/学科的成员了，请勿重复添加。")
                    return
                if usg.subjectId == subjectId and usg.gradeId != None and gradeId != None:
                    strG1 = str(usg.gradeId)
                    strG2 = str(gradeId)
                    if strG1[0:1] == strG2[0:1]:
                        response.getWriter().write(u"您已经是该学段/学科的成员了，没有必要再添加。")
                        return
            userSubjectGrade = UserSubjectGrade()
            userSubjectGrade.userId = user.userId
            userSubjectGrade.subjectId = subjectId
            userSubjectGrade.gradeId = gradeId
            self.userService.saveOrUpdateUserSubjectGrade(userSubjectGrade)
            user = self.userService.getUserById(user.userId, False)
            self.unAuditUser(user)
            response.getWriter().write("success")
            
    def validSubjectGrade(self, gradeId, subjectId):
        if gradeId == None and subjectId == None:
            return True
        subjectService = __jitar__.subjectService
        if gradeId != None and subjectId != None:
            gradeId1 = CommonUtil.convertRoundMinNumber(gradeId)
            sl = subjectService.getSubjectByMetaGradeSubjectId(gradeId1, subjectId)
            if sl == None:
                return False
        elif gradeId != None:
            sl = subjectService.getGrade(gradeId)
            if sl == None:
                return False
        elif subjectId != None:
            sl = subjectService.getSubjectByMetaSubjectId(subjectId)
            if sl == None:
                return False
        return True
    
    def unAuditUser(self,user):
        #admin就除外了，否则无法进行审核了
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == True:
            return
        configService = __jitar__.configService
        # 查看配置是否需要进行允许修改学段/学科            
        config = configService.getConfigure()
        needAudit = config.getBoolValue(Configure.PROFILE_UPDATE_SUBJECT_NEEDAUDIT, False) or config.getBoolValue(Configure.PROFILE_UPDATE_GRADE_NEEDAUDIT, False)             
        if needAudit:
            self.userService.updateUserStatus(user, User.USER_STATUS_WAIT_AUTID)  # 待审核
            session.removeAttribute(User.SESSION_LOGIN_NAME_KEY)
            session.removeAttribute(User.SESSION_LOGIN_USERMODEL_KEY)
        self.deleteUserCache(user)

    def deleteUserCache(self, user):
        fc = FileCache()
        fc.deleteUserAllCache(user.loginName)
        fc = None