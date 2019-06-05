from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil, CommonUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse,Action,ActionUser,ActionReply
from java.text import SimpleDateFormat
from java.util import Date,Calendar,GregorianCalendar
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from base_preparecourse_page import *
from action_reply_query import ActionReplyQuery

class show_preparecourse_action_delete(PrepareCoursePageService):
    def __init__(self):
        self.printer = response.getWriter() 
        self.Action = None
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        self.act_svc = __jitar__.actionService
        self.frd_svc = __jitar__.friendService
        self.grp_svc = __jitar__.groupService
    
    def execute(self):
        if self.loginUser == None:
            self.printer.write(u"请先<a href='../../../../login.jsp'>登录</a>。")
            return
        
        self.getBaseData()
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的课程标识。")
            return
        self.prepareCourse = self.getBasePrepareCourse()
        if self.prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return 
        
        actionId = self.params.getIntParam("actionId")
        actionReplyId = self.params.getIntParam("actionReplyId")
        
        if actionId == None or actionReplyId == None :
            operation_result = u"缺少标识。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
        self.action = self.act_svc.getActionById(actionId)
        if self.action == None:
            operation_result = u"该活动不存在。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) or self.action.createUserId == self.loginUser.userId:
            self.act_svc.deleteActionReply(actionReplyId)
            response.sendRedirect("show_preparecourse_action_content.py?actionId=" + str(actionId))
        else:
            operation_result = u"你不是该活动的发起人，无法进行删除。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"