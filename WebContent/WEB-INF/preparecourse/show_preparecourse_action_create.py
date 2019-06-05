from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil, CommonUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse, Action, ActionUser
from java.text import SimpleDateFormat
from java.util import Date, Calendar, GregorianCalendar
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from base_preparecourse_page import *
from message_out import ShowError

class show_preparecourse_action_create(PrepareCoursePageService):
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
        if self.loginUser == None:
            backUrl = request.getAttribute('javax.servlet.forward.request_uri')
            if backUrl == None or backUrl == "":backUrl = request.requestURI    
            response.sendRedirect(CommonUtil.getSiteUrl(request) + "login.jsp?redUrl=" + CommonUtil.urlUtf8Encode(CommonUtil.getSiteServer(request) + backUrl))
            return
        
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的课程标识。")
            return
        self.prepareCourse = self.getBasePrepareCourse()
        if self.prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return        
        now=Date()
        if self.prepareCourse.startDate > now :
            self.printer.write(u"备课时间还没开始，不允许增加活动 。")
            return
          
        if self.prepareCourse.endDate < now :
            self.printer.write(u"备课已经结束，不允许增加活动 。")
            return
        # 如果有了prepareCourse对象，就可以使用show_preparecourse_ok_info了
        page = self.getPrepareCoursePageWithCustomSkin(self.prepareCourse)
        widgets = [                  
                   {"id": "placerholder1", "pageId":0, "columnIndex":2, "title":"", "module":"placeholder", "ico":"", "data":""}
                  ]
        
        request.setAttribute("widget_list", widgets)
        request.setAttribute("page", page)
        request.setAttribute("prepareCourse", self.prepareCourse)
        request.setAttribute("prepareCourseId", self.prepareCourseId)
        
        if self.isPrepareCourseMember() == False:
            operation_result = u"不是本课程的成员。"
            request.setAttribute("operation_result", operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
        if request.getMethod() == "POST":
            return self.save_action()
        else:
            return self.show_action_edit()
        
    def show_action_edit(self):
        now=Date()
        sdf = SimpleDateFormat("yyyy")
        year= int(sdf.format(now))
        sdf = SimpleDateFormat("M")
        month= int(sdf.format(now))
        sdf = SimpleDateFormat("d")
        day= int(sdf.format(now))
        request.setAttribute("default_year", year)
        request.setAttribute("default_month", month)
        request.setAttribute("default_day", day)
        return "/WEB-INF/ftl/course/show_preparecourse_action_create.ftl"
    
    def save_action(self):
        title = self.params.getStringParam("actionName")
        ownerId = self.prepareCourseId  
        ownerType = "preparecourse"
        if title == None or title == "":
            operation_result = u"请输入活动名称。"
            request.setAttribute("operation_result", operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        actionDescription = self.params.getStringParam("actionDescription")
        if len(actionDescription) == 0 :
            operation_result = u"请输入活动描述。"
            request.setAttribute("operation_result", operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
        actionType = self.params.getIntParam("actionType")
        if actionType == None:
            operation_result = u"请选项活动类型。"
            request.setAttribute("operation_result", operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
        
        visibility = self.params.getIntParam("actionVisibility")        
        if visibility == None:
            operation_result = u"请选项活动方式。"
            request.setAttribute("operation_result", operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"

        
        userLimit = self.params.getIntParam("actionUserLimit")
        if userLimit == None :
            operation_result = u"请输入活动的限制人数。"
            request.setAttribute("operation_result", operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
 
        
        actionStartDateTime = self.validate_input_datetime("actionStartDateTime")
        actionFinishDateTime = self.validate_input_datetime("actionFinishDateTime")
        attendLimitDateTime = self.validate_input_datetime("attendLimitDateTime")
        
        if DateUtil.compareDateTime(actionStartDateTime, actionFinishDateTime) > 0 :
            operation_result = u"活动开始日期不能大于结束日期。"
            request.setAttribute("operation_result", operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"

        
        self.action = Action()
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
        
        # 将创建者插入到活动用户表内        
        actionUser = ActionUser()
        actionUser.setActionId(self.action.actionId)
        actionUser.setUserId(self.loginUser.userId)
        actionUser.setAttendUserCount(1)
        actionUser.setIsApprove(1)
        actionUser.setStatus(1)       
        self.act_svc.addActionUser(actionUser)
        
        
        # 插入活动的用户表
        self.inviteUser = self.params.safeGetIntValues("inviteUserId")
        return self.addInviteUser()
        
    def addInviteUser(self):
        strErr = ""
        for u in self.inviteUser:
            user = self.user_svc.getUserById(u)
            if user == None:
                strErr = strErr + u + ","
            else:
                self.act_svc.addActionUser(self.action.actionId, user, self.loginUser.userId)
        if strErr != "":
            operation_result = u"活动创建成功。但以下用户没有添加成功：<br/>" + strErr           
        else:
            operation_result = u"活动创建成功。"
        request.setAttribute("operation_result", operation_result)
        return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
    
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
            operation_result = u"请输入" + error_msg + u"日期的年份。"
            request.setAttribute("operation_result", operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
        if actionStartDateTimeM.isdigit() == False:
            operation_result = u"请输入" + error_msg + u"日期的年份。"
            request.setAttribute("operation_result", operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
        if int(actionStartDateTimeM) > 12 or int(actionStartDateTimeM) < 1 :
            operation_result = error_msg + u"日期的月份应当在1-12之间。"
            request.setAttribute("operation_result", operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
        if actionStartDateTimeD.isdigit() == False:
            operation_result = u"请输入" + error_msg + u"日期的天数"
            request.setAttribute("operation_result", operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
            
        
        if int(actionStartDateTimeD) > 31 or int(actionStartDateTimeD) < 0:
            operation_result = error_msg + u"日期的天数应当在1-31之间。"
            request.setAttribute("operation_result", operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"

        
        if int(actionStartDateTimeH) > 23 or int(actionStartDateTimeH) < 0:
            operation_result = error_msg + u"日期的小时数应当在0-23之间。"
            request.setAttribute("operation_result", operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"

        
        if int(actionStartDateTimeMM) > 59 or int(actionStartDateTimeMM) < 0:
            operation_result = error_msg + u"日期的分钟数应当在0-59之间。"
            request.setAttribute("operation_result", operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
     
        strDate = actionStartDateTimeY + "-" + actionStartDateTimeM + "-" + actionStartDateTimeD + " " + str(actionStartDateTimeH) + ":" + actionStartDateTimeMM + ":0"
        actionStartDateTime = SimpleDateFormat("yyyy-M-d H:m:s").parse(strDate)
        return actionStartDateTime
