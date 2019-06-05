from base_blog_page import *
from base_action import BaseAction
from cn.edustar.jitar.pojos import Action, ActionUser
from cn.edustar.jitar.util import ParamUtil
from site_config import SiteConfig
from cn.edustar.jitar.util import DateUtil
from java.util import Calendar, GregorianCalendar
from java.text import SimpleDateFormat
from message_out import MessagePrint
from cn.edustar.jitar.pojos import PrepareCourse

class createAction(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    
    grp_svc = __jitar__.groupService
    user_svc = __jitar__.userService
    pc_svc = __jitar__.getPrepareCourseService()
    
    def __init__(self):        
        self.act_svc = __jitar__.actionService  
        self.ERROR_FTL = "/WEB-INF/ftl/action/error.ftl"
        self.MsgPrint = MessagePrint()
    
    def execute(self):
        self.params = ParamUtil(request)
        self.out = response.writer
        if self.loginUser == None:
            self.MsgPrint.addMessage(u"请先<a href='../../login.jsp'>登录</a>，然后才能创建活动")            
            return self.MsgPrint.printMessage("login.jsp", "back")
        
        site_config = SiteConfig()
        site_config.get_config()

        # 活动必须属于一个类别，如user,group,course等
        self.ownerId = self.params.getIntParam("ownerId")     
        self.ownerType = self.params.getStringParam("ownerType")
        
        if self.ownerId == None or self.ownerId == 0:
            self.MsgPrint.addMessage(u"缺少类别标识。")
            self.MsgPrint.hasError = True
        if self.ownerType == None or self.ownerType == "":
            self.MsgPrint.hasError = True
            self.MsgPrint.addMessage(u"缺少类别名称。")        
        
        #限定活动的范围，
        if self.ownerType != "user" and self.ownerType != "group" and self.ownerType != "preparecourse" and self.ownerType != "subject":
            self.MsgPrint.hasError = True
            self.MsgPrint.addMessage(u"活动类别必须是个人、协作组、集备、学科。")
            
        if self.MsgPrint.hasError == True:
            return self.MsgPrint.printMessage("actions.py.py", "back")
        
        self.ownerId = self.get_owner_entity_id()
        
        if self.ownerId == -1:
            self.MsgPrint.addMessage(u"您没有创建活动的权限。")
            self.MsgPrint.hasError = True

        if self.MsgPrint.hasError == True:
            return self.MsgPrint.printMessage("", "back")
        
        request.setAttribute("ownerId", self.ownerId)
        request.setAttribute("ownerType", self.ownerType)
        
        if request.getMethod() == "POST":
            return self.actionPost()
        else:
            return self.actionGet()        
    
    def actionGet(self):
        response.setContentType("text/html; charset=UTF-8")
        cal = Calendar.getInstance()
        request.setAttribute("year", cal.get(Calendar.YEAR))
        return "/WEB-INF/ftl/action/action_create.ftl"
    
    def actionPost(self):
        title = self.params.getStringParam("actionName")
        if len(title) == 0 :
            self.MsgPrint.hasError = True
            self.MsgPrint.addMessage(u"请输入标题。")
        actionDescription = self.params.getStringParam("actionDescription")
        if len(actionDescription) == 0 :
            self.MsgPrint.hasError = True
            self.MsgPrint.addMessage(u"请输入活动描述。")            
        
        actionType = self.params.getIntParam("actionType")
        if actionType == None:
            self.MsgPrint.hasError = True
            self.MsgPrint.addMessage(u"请选项活动类型。")
        
        visibility = self.params.getIntParam("actionVisibility")        
        if visibility == None:
            self.MsgPrint.hasError = True
            self.MsgPrint.addMessage(u"请选项活动方式。")
        
        userLimit = self.params.getIntParam("actionUserLimit")
        if userLimit == None :
            self.MsgPrint.hasError = True
            self.MsgPrint.addMessage(u"请输入活动的限制人数。")
        
        actionStartDateTime = self.validate_input_datetime("actionStartDateTime")
        actionFinishDateTime = self.validate_input_datetime("actionFinishDateTime")
        attendLimitDateTime = self.validate_input_datetime("attendLimitDateTime")
        
        if DateUtil.compareDateTime(actionStartDateTime, actionFinishDateTime) > 0 :
            self.MsgPrint.hasError = True
            self.MsgPrint.addMessage(u"活动开始日期不能大于结束日期。")
        
        if DateUtil.compareDateTime(attendLimitDateTime, actionFinishDateTime) > 0 :
            self.MsgPrint.hasError = True
            self.MsgPrint.addMessage(u"活动报名截止日期不能大于结束日期。")
        
        if self.MsgPrint.hasError == True:
            return self.MsgPrint.printMessage("", "back")
        
        self.action = Action()
        self.action.title = title
        # 群组id，用户id，或者课程id 
        self.action.ownerId = int(self.ownerId)
        self.action.ownerType = self.ownerType
        self.action.createUserId = self.loginUser.userId
        self.action.actionType = actionType
        self.action.visibility = int(visibility)
        self.action.description = actionDescription
        self.action.place = self.params.getStringParam("actionPlace")
        self.action.userLimit = int(userLimit)
        self.action.startDateTime = actionStartDateTime
        self.action.finishDateTime = actionFinishDateTime
        self.action.attendLimitDateTime = attendLimitDateTime
        self.act_svc.addAction(self.action)
        
        # 将创建者插入到活动用户表内        
        actionUser = ActionUser()
        actionUser.setActionId(self.action.actionId)
        actionUser.setUserId(self.loginUser.userId)
        actionUser.setAttendUserCount(1)
        actionUser.setIsApprove(1)
        actionUser.setStatus(1)       
        self.act_svc.addActionUser(actionUser)
        
        #插入活动的用户表
        self.inviteUser = self.params.safeGetIntValues("inviteUserId")
        self.addInviteUser()
                
        response.setContentType("text/html; charset=UTF-8")
        self.MsgPrint.hasError = False
        #self.MsgPrint.addMessage()
        return self.MsgPrint.printMessage("manage/action/showAction.py?actionId=" + str(self.action.actionId), "")

    def validate_input_datetime(self, varType):
        error_msg = ""
        if varType == "actionStartDateTime":
            error_msg = u"活动开始"
        elif varType == "actionFinishDateTime":
            error_msg = u"活动结束"
        elif varType == "attendLimitDateTime":
            error_msg = u"活动报名截止" 
        else:
            pass
            
        actionStartDateTimeY = self.params.getStringParam(varType + "Y")
        actionStartDateTimeM = self.params.getStringParam(varType + "M")
        actionStartDateTimeD = self.params.getStringParam(varType + "D")
        actionStartDateTimeH = self.params.getStringParam(varType + "H")
        actionStartDateTimeMM = self.params.getStringParam(varType + "MM")
        if actionStartDateTimeY.isdigit() == False:
            self.MsgPrint.hasError = True
            self.MsgPrint.addMessage(u"输入的" + error_msg + u"日期的年份。")
        
        if actionStartDateTimeM.isdigit() == False:
            self.MsgPrint.hasError = True
            self.MsgPrint.addMessage(u"输入的" + error_msg + u"日期的年份。") 
        
        if int(actionStartDateTimeM) > 12 or int(actionStartDateTimeM) < 1 :
            self.MsgPrint.hasError = True
            self.MsgPrint.addMessage(error_msg + u"日期的月份应当在1-12之间。")
        
        if actionStartDateTimeD.isdigit() == False:
            self.MsgPrint.hasError = True
            self.MsgPrint.addMessage(u"输入的" + error_msg + u"日期的天数。")
        
        if int(actionStartDateTimeD) > 31 or int(actionStartDateTimeD) < 0:
            self.MsgPrint.hasError = True
            self.MsgPrint.addMessage(error_msg + u"日期的天数应当在1-31之间。")
        
        if int(actionStartDateTimeH) > 23 or int(actionStartDateTimeH) < 0:
            self.MsgPrint.hasError = True
            self.MsgPrint.addMessage(error_msg + u"日期的小时数应当在0-23之间。")
        
        if int(actionStartDateTimeMM) > 59 or int(actionStartDateTimeMM) < 0:
            self.MsgPrint.hasError = True
            self.MsgPrint.addMessage(error_msg + u"日期的分钟数应当在0-59之间。")
        strDate = actionStartDateTimeY + "-" + actionStartDateTimeM + "-" + actionStartDateTimeD + " " + str(actionStartDateTimeH) + ":" + actionStartDateTimeMM + ":0"
        actionStartDateTime = SimpleDateFormat("yyyy-M-d H:m:s").parse(strDate)
        return actionStartDateTime
        
    def get_owner_entity_id(self):
        if self.ownerType == "user":
            # 只能登录用户创建自己的活动
            user_svc = __jitar__.userService
            user = user_svc.getUserById(self.ownerId)
            if user == None:
                return -1
            else:
                if user.userId == self.loginUser.userId:
                    return user.userId
                else:
                    return -1
        elif self.ownerType == "group":
            # 群组管理员可以创建自己群组的活动
            group = self.grp_svc.getGroup(self.ownerId)
            if group == None:
                return -1
            else:
                if self.get_user_is_group_admin(group) == "admin":
                    return group.groupId
                else:
                    return -1
        elif self.ownerType == "preparecourse":
            # 集体备课：管理员，所有参与人员可以创建            
            prepareCourse = self.pc_svc.getPrepareCourse(self.ownerId)
            if prepareCourse == None:
                return -1
            else:
                if prepareCourse.createUserId != None and prepareCourse.createUserId == self.loginUser.userId:
                    return prepareCourse.prepareCourseId
                if prepareCourse.leaderId != None and prepareCourse.leaderId == self.loginUser.userId:
                    return prepareCourse.prepareCourseId
                return -1
        elif self.ownerType == "subject":
            #学科活动
            sub_svc = __spring__.getBean("subjectService")
            accessControlService = __spring__.getBean("accessControlService")
            subject = sub_svc.getSubjectById(self.ownerId)
            if subject == None:
                return -1
            else:
                if self.loginUser.loginName == "admin":
                    return subject.subjectId
                elif  accessControlService.userIsSubjectContentAdmin(self.loginUser, subject) == True or accessControlService.userIsSubjectAdmin(self.loginUser, subject) == True:
                    return subject.subjectId
                else:
                    return -1                
        else:
            return -1
    
    # 判断用户舒服是有群组管理权限
    def get_user_is_group_admin(self, grp):
        visitor_role = "guest"       
        gm = self.grp_svc.getGroupMemberByGroupIdAndUserId(grp.groupId, self.loginUser.userId)
        if gm != None and gm.getStatus() == 0 and gm.getGroupRole() >= 800:
            visitor_role = "admin"
        
        return visitor_role
            
    def addInviteUser(self):
        strErr = ""
        for u in self.inviteUser:
            user = user_svc.getUserById(u)
            if user == None:
                strErr = strErr + u + u","
            else:
                self.act_svc.addActionUser(self.action.actionId, user, self.loginUser.userId)
        request.setAttribute("errMsg", strErr)
        return
