from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil, CommonUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse,Action
from java.text import SimpleDateFormat
from java.util import Date,Calendar,GregorianCalendar
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from base_preparecourse_page import *
from message_out import ShowError

class show_preparecourse_action_edit(PrepareCoursePageService):
    def __init__(self):
        self.printer = response.getWriter() 
        self.Action = None
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        self.act_svc = __jitar__.actionService
        
    def execute(self):
        self.getBaseData()
        """
        发起或者编辑活动的前提：
        1，用户必须先登录
        2，登录用户必须是管理员或者备课成员
        """
        
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的课程标识。")
            return
        self.prepareCourse = self.getBasePrepareCourse()
        if self.prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        
        # 如果有了prepareCourse对象，就可以使用show_preparecourse_ok_info了
        page = self.getPrepareCoursePageWithCustomSkin(self.prepareCourse)
        widgets = [                  
                   {"id": "placerholder1", "pageId":0, "columnIndex":2,"title":"","module":"placeholder", "ico":"", "data":""}
                  ]
        
        request.setAttribute("widget_list",widgets)
        request.setAttribute("page", page)
        request.setAttribute("prepareCourse",self.prepareCourse)
        request.setAttribute("prepareCourseId",self.prepareCourseId)        
       
        if self.loginUser == None:
            operation_result = u"请先<a href='../../../../login.jsp'>登录</a>。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
        self.actionId = self.params.getIntParam("actionId")
        if self.actionId == 0:
            operation_result = u"无效的活动标识。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"        
        
        self.action = self.act_svc.getActionById(self.actionId)
        if self.action == None:
            operation_result = u"无法加载活动。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl" 
        accessControlService = __spring__.getBean("accessControlService")
        if not(accessControlService.isSystemAdmin(self.loginUser) or self.action.createUserId == self.loginUser.userId):
            operation_result = u"你不是本活动的创建者或者管理员，不能进行修改。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
        if request.getMethod() == "POST":
            # 判断是否是合法的实体，如user,group，确认是否是合法
            # 修改这些字段不更新，不用判断
            #self.ownerId = self.get_owner_entity_id()
            #if self.ownerId == -1:
            #    self.out.print("您没有创建活动的权限")
            #    return
            cmd = self.params.getStringParam("cmd") 
            if cmd == "edit":
                return self.update_action()
            elif cmd == "deluser":
                return self.delUser()
            elif cmd == "inviteuser":
                return self.inviteUser()
            elif cmd == "printuser":
                return self.printUser()            
            else:                
                self.printer.addMessage(u"无效的命令。")
                return self.printer.printMessage("","back")       
        else:            
            action_user_list = self.act_svc.getActionUserWithDistUnit(self.action.actionId)            
            #得到活动参与人员列表
            #action_user_list = self.act_svc.getActionUserIdListByActionId(self.action.actionId)
            
            request.setAttribute("action_user_list", action_user_list)
            request.setAttribute("action", self.action)
            return self.show_action_edit()
        
        
    def show_action_edit(self):
        request.setAttribute("action", self.action)
        self.get_datetime_part("startDateTime", self.action.startDateTime)
        self.get_datetime_part("finishDateTime", self.action.finishDateTime)
        self.get_datetime_part("attendLimitDateTime", self.action.attendLimitDateTime)
        return "/WEB-INF/ftl/course/show_preparecourse_action_edit.ftl"
      
    def update_action(self):
        title = self.params.getStringParam("actionName")
        ownerId = self.prepareCourseId
        ownerType = "preparecourse"
        if title == None or title == "":
            operation_result = u"请输入活动名称。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        actionDescription = self.params.getStringParam("actionDescription")
        if len(actionDescription) == 0 :
            operation_result = u"请输入活动描述。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
        actionType = self.params.getIntParam("actionType")
        if actionType == None:
            operation_result = u"请选项活动类型。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"        
        
        visibility = self.params.getIntParam("actionVisibility")        
        if visibility == None:
            operation_result = u"请选项活动方式。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
        userLimit = self.params.getIntParam("actionUserLimit")
        if userLimit == None :
            operation_result = u"请输入活动的限制人数。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl" 
        
        actionStartDateTime = self.validate_input_datetime("actionStartDateTime")
        actionFinishDateTime = self.validate_input_datetime("actionFinishDateTime")
        attendLimitDateTime = self.validate_input_datetime("attendLimitDateTime")
        
        if DateUtil.compareDateTime(actionStartDateTime, actionFinishDateTime) > 0 :
            operation_result = u"活动开始日期不能大于结束日期。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
        self.action.title = title
        # 群组id，用户id，或者课程id 
        self.action.ownerId = ownerId
        self.action.ownerType = ownerType
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
        
        operation_result = u"活动修改成功。"
        request.setAttribute("operation_result",operation_result)
        return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
    
    
    def validate_input_datetime(self,varType):
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
            operation_result = u"请输入" + error_msg + u"日期的年份。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
        if actionStartDateTimeM.isdigit() == False:
            operation_result = u"请输入" + error_msg + u"日期的年份。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"        
           
        
        if int(actionStartDateTimeM) > 12 or int(actionStartDateTimeM) < 1 :
            operation_result = error_msg + u"日期的月份应当在1-12之间。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"

        
        if actionStartDateTimeD.isdigit() == False:
            operation_result = u"请输入" + error_msg + u"日期的天数"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
            
        
        if int(actionStartDateTimeD) > 31 or int(actionStartDateTimeD) < 0:
            operation_result = error_msg + u"日期的天数应当在1-31之间。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"

        
        if int(actionStartDateTimeH) > 23 or int(actionStartDateTimeH) < 0:
            operation_result = error_msg + u"日期的小时数应当在0-23之间。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"

        
        if int(actionStartDateTimeMM) > 59 or int(actionStartDateTimeMM) < 0:
            operation_result = error_msg + u"日期的分钟数应当在0-59之间。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
     
        strDate = actionStartDateTimeY + "-" + actionStartDateTimeM + "-" + actionStartDateTimeD + " " +  str(actionStartDateTimeH) + ":" + actionStartDateTimeMM + ":0"
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
        
    def delUser(self):
        action_user_users = self.params.getRequestParamValues("guid")                
        if action_user_users == None:
            operation_result = u"请选择一个用户。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
        for action_user_id in action_user_users:
            self.act_svc.delActionUserById(int(action_user_id))
        
        #更新统计数据
        self.act_svc.updateActionUserStatById(self.action.actionId) 
        response.sendRedirect("show_preparecourse_action_edit.py?actionId=" + str(self.action.actionId))
        
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
        response.addHeader("Content-Disposition","attachment;filename=ActionUser.xls")
        return "/WEB-INF/ftl/action/action_user_print.ftl"
    
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
                    self.act_svc.addActionUser(self.action.actionId,user,self.loginUser.userId)
        
        #更新统计数据
        self.act_svc.updateActionUserStatById(self.action.actionId)        
        if str_error != "":
            operation_result = u"邀请用户操作的结果" + str_error
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
        response.sendRedirect("show_preparecourse_action_edit.py?actionId=" + str(self.action.actionId))
        return