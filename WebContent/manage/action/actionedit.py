from base_blog_page import *
from base_action import BaseAction
from cn.edustar.jitar.pojos import Action
from cn.edustar.jitar.util import ParamUtil
from site_config import SiteConfig
from cn.edustar.jitar.util import DateUtil
from java.util import Calendar, GregorianCalendar, Locale
from java.text import SimpleDateFormat
from message_out import MessagePrint

class actionedit(BaseAction, RequestMixiner, ResponseMixiner, PageCheckMixiner):
    
    grp_svc = __jitar__.groupService    
    act_svc = __jitar__.actionService
    user_svc = __jitar__.userService
    pc_svc = __jitar__.getPrepareCourseService()
    
    def __init__(self):        
        self.ERROR_FTL = "/WEB-INF/ftl/action/error.ftl"
        self.printer = MessagePrint()
        self.subjectService = __spring__.getBean("subjectService")
        self.accessControlService = __spring__.getBean("accessControlService")
        
    def execute(self):
        self.ErrMsg = ""
        self.params = ParamUtil(request)
        self.out = response.writer
        if self.loginUser == None:
            self.printer.addMessage(u"请先<a href='../../login.jsp'>登录</a>，然后才能编辑活动")
            return self.printer.printMessage("login.jsp", "")
        
        # 导航数据
        site_config = SiteConfig()
        site_config.get_config()

        # 活动必须属于一个类别，如user,group,course等
        self.actionId = self.params.getIntParam("actionId")        
        if self.actionId == None or self.actionId == 0:
            self.addActionLink(u"返回活动列表",CommonUtil.getSiteUrl(request) + "actions.py")
            self.addActionError(u"缺少标识。")
            return self.ERROR
        self.act_svc.updateActionUserStatById(self.actionId)
        self.action = self.act_svc.getActionById(self.actionId)
        if self.action == None:
            self.addActionLink(u"返回活动列表",CommonUtil.getSiteUrl(request) + "actions.py")
            self.addActionError(u"加载活动信息失败。")
            return self.ERROR
        
        #学科内容管理员权限
        self.contentAdmin = False
        #学科管理员权限
        self.manageAdmin = False
        if self.action.ownerType == "subject":
            self.subjectId = self.action.ownerId 
            self.subject = self.subjectService.getSubjectById(self.subjectId)
            #print "self.subjectId=",self.subjectId
            #print "self.subject=",self.subject
            #print "self.loginUser=",self.loginUser
            self.contentAdmin = self.accessControlService.userIsSubjectContentAdmin(self.loginUser, self.subject)
            self.manageAdmin = self.accessControlService.userIsSubjectAdmin(self.loginUser, self.subject)
            #print "self.contentAdmin=",self.contentAdmin
            #print "self.manageAdmin=",self.manageAdmin
        
        if self.contentAdmin == True or self.manageAdmin == True:
            #是内容管理员或管理员
            #print "是内容管理员或管理员"
            bManage = True
        else:        
            if not(self.loginUser.userId == self.action.createUserId or self.loginUser.loginName == "admin"):
                self.addActionLink(u"返回活动列表",CommonUtil.getSiteUrl(request) + "manage/action/showAction.py?actionId=" + str(self.action.actionId))
                self.addActionError(u"只有活动的创建者,系统管理员,或者内容管理员才可以修改活动。")
                return self.ERROR       
        if self.printer.hasError == True:
            return self.printer.printMessage("", "back")            
        if request.getMethod() == "POST":
            # 判断是否是合法的实体，如user,group，确认是否是合法
            # 修改这些字段不更新，不用判断
            #self.ownerId = self.get_owner_entity_id()
            #if self.ownerId == -1:
            #    self.out.print("您没有创建活动的权限")
            #    return
            cmd = self.params.getStringParam("cmd") 
            if cmd == "edit":
                return self.actionPost()
            elif cmd == "deluser":
                return self.delUser()
            elif cmd == "inviteuser":
                return self.inviteUser()
            elif cmd == "printuser":
                return self.printUser()            
            else:
                self.addActionError(u"无效的命令。")
                return self.ERROR
        else:
            
            action_user_list = self.act_svc.getActionUserWithDistUnit(self.action.actionId)            
            #得到活动参与人员列表
            #action_user_list = self.act_svc.getActionUserIdListByActionId(self.action.actionId)
            
            request.setAttribute("action_user_list", action_user_list)
            request.setAttribute("action", self.action)
            return self.actionGet()        
    
    def actionGet(self):
        self.get_datetime_part("startDateTime", self.action.startDateTime)
        self.get_datetime_part("finishDateTime", self.action.finishDateTime)
        self.get_datetime_part("attendLimitDateTime", self.action.attendLimitDateTime)
        cal = Calendar.getInstance()
        request.setAttribute("year", cal.get(Calendar.YEAR))
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/action/action_edit.ftl"
    
    def actionPost(self):
        request.setAttribute("RedUrl", "manage/action/actionedit.py?actionId=" + str(self.action.actionId))        
        title = self.params.getStringParam("actionName")
        if len(title) == 0 :            
            self.printer.addMessage(u"请输入标题。")
        
        actionType = self.params.getIntParam("actionType")
        if actionType == None:            
            self.printer.addMessage(u"请选项活动类型。")
        
        visibility = self.params.getIntParam("actionVisibility")        
        if visibility == None:            
            self.printer.addMessage(u"请选项活动方式。")
        
        userLimit = self.params.getIntParam("actionUserLimit")
        if userLimit == None :            
            self.printer.addMessage(u"请输入活动的限制人数。")
        
        actionStartDateTime = self.validate_input_datetime("actionStartDateTime")
        actionFinishDateTime = self.validate_input_datetime("actionFinishDateTime")
        attendLimitDateTime = self.validate_input_datetime("attendLimitDateTime")
        
        if DateUtil.compareDateTime(actionStartDateTime, actionFinishDateTime) > 0 :            
            self.printer.addMessage(u"活动开始日期不能大于结束日期。")
            
        if DateUtil.compareDateTime(attendLimitDateTime, actionFinishDateTime) > 0 :            
            self.printer.addMessage(u"活动报名截止日期不能大于结束日期。")
            
        if DateUtil.compareDateTime(actionStartDateTime, attendLimitDateTime) > 0 :            
            self.printer.addMessage(u"活动开始日期不能大于活动报名截止日期。")
            
        if self.printer.hasError == True:
            return self.printer.printMessage("", "back") 

        self.action.title = title
        self.action.actionType = actionType
        self.action.visibility = int(visibility)
        self.action.description = self.params.getStringParam("actionDescription")  
        self.action.place = self.params.getStringParam("actionPlace")  
        self.action.userLimit = int(userLimit)
        self.action.startDateTime = actionStartDateTime
        self.action.finishDateTime = actionFinishDateTime
        self.action.attendLimitDateTime = attendLimitDateTime
        #print "action =", action
        self.act_svc.saveAction(self.action)
        response.setContentType("text/html; charset=UTF-8")
        return self.printer.printMessage("manage/action/actionedit.py?actionId=" + str(self.action.actionId), "") 
      

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
            self.printer.addMessage(u"输入的" + error_msg + u"日期的年份。")
        
        if actionStartDateTimeM.isdigit() == False:            
            self.printer.addMessage(u"输入的活" + error_msg + u"日期的月份。")
        
        if int(actionStartDateTimeM) > 12 or int(actionStartDateTimeM) < 1 :            
            self.printer.addMessage(error_msg + u"日期的月份应当在1-12之间。")
        
        if actionStartDateTimeD.isdigit() == False:            
            self.printer.addMessage(u"输入的" + error_msg + u"日期的天数。")
        
        if int(actionStartDateTimeD) > 31 or int(actionStartDateTimeD) < 0:            
            self.printer.addMessage(error_msg + u"日期的天数应当在1-31之间。")
        
        if int(actionStartDateTimeH) > 23 or int(actionStartDateTimeH) < 0:            
            self.printer.addMessage(error_msg + u"日期的小时数应当在0-23之间。")
            
        if int(actionStartDateTimeMM) > 59 or int(actionStartDateTimeMM) < 0:            
            self.printer.addMessage(error_msg + u"日期的分钟数应当在0-59之间。")        
        
        strDate = actionStartDateTimeY + "-" + actionStartDateTimeM + "-" + actionStartDateTimeD + " " + str(actionStartDateTimeH) + ":" + actionStartDateTimeMM + ":0"
        actionStartDateTime = SimpleDateFormat("yyyy-M-d H:m:s").parse(strDate)
        return actionStartDateTime
        
    def get_datetime_part(self, varType, varDateTime):
        c = Calendar.getInstance()
        c.setTime(varDateTime)        
        request.setAttribute(varType + "Y", c.get(Calendar.YEAR))
        request.setAttribute(varType + "M", c.get(Calendar.MONTH) + 1)
        request.setAttribute(varType + "D", c.get(Calendar.DAY_OF_MONTH))
        if c.get(Calendar.AM_PM) == Calendar.PM:
            request.setAttribute(varType + "H", c.get(Calendar.HOUR) + 12)
        else:
            request.setAttribute(varType + "H", c.get(Calendar.HOUR))
        request.setAttribute(varType + "MM", c.get(Calendar.MINUTE))
                
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
                elif  accessControlService.userIsSubjectContentAdmin(self.loginUser, subject) == True:
                    #self.loginUser.subjectId == subject.metaSubject.msubjId and self.loginUser.gradeId == subject.metaSubject.metaGrade.gradeId:
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
    
    def inviteUser(self):
        str_error = ""
        invite_users = self.params.safeGetIntValues("inviteUserId")
        for invite_userId in invite_users:
            #print "user_loginName = ", user_loginName
            user = self.user_svc.getUserById(invite_userId)
            if user == None:
                str_error = str_error + u"<li>用户" + invite_userId + u"不是本系统存在的用户。</li>"
            else:
                if self.act_svc.userIsInAction(user.getUserId(), self.action.actionId):
                    str_error = str_error + u"<li>用户" + user.trueName + u"已经是该活动的成员。</li>"
                else:
                    self.act_svc.addActionUser(self.action.actionId, user, self.loginUser.userId)
        
        #更新统计数据
        self.act_svc.updateActionUserStatById(self.action.actionId)        
        if str_error != "":            
            self.printer.addMessage(str_error)

        return self.printer.printMessage("manage/action/actionedit.py?actionId=" + str(self.action.actionId), "")
        
        
    def delUser(self):
        action_user_users = self.params.getRequestParamValues("guid")                
        if action_user_users == None:
            self.printer.addMessage(u"请选择一个用户。")
            return self.printer.printMessage("", "back") 
        for action_user_id in action_user_users:
            self.act_svc.delActionUserById(int(action_user_id))
        
        #更新统计数据
        self.act_svc.updateActionUserStatById(self.action.actionId) 
        return self.printer.printMessage("manage/action/actionedit.py?actionId=" + str(self.action.actionId), "")
        
    def printUser(self):
        request.setCharacterEncoding("utf-8")
        action_user_list = self.act_svc.getActionUserWithDistUnit(self.action.actionId)
        request.setAttribute("action_user_list", action_user_list)
        request.setAttribute("action", self.action)
        response.reset()
        response.setContentType("application/vnd.ms-excel")
        response.setHeader("Content-Type", "application/vnd.ms-excel; charset=GB2312")
        #response.setCharacterEncoding("GB2312")
        #response.setLocale(Locale.SIMPLIFIED_CHINESE)
        response.addHeader("Content-Disposition", "attachment;filename=ActionUser.xls")
        return "/WEB-INF/ftl/action/action_user_print.ftl"
    
