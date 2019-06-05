from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from site_config import SiteConfig
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCourseStage
from java.text import SimpleDateFormat
from java.util import Date
from message_out import MessagePrint
from base_action import BaseAction
from preparecourse_member_query import PrepareCourseMemberQuery

class showCommonPreCourse(BaseAction):
    def __init__(self):
        self.params = ParamUtil(request)
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.printer = MessagePrint()
        
    def execute(self):
        site_config = SiteConfig()
        site_config.get_config()
        
        if self.loginUser == None:            
            self.printer.addMessage(u"请先<a href='../../login.jsp'>登录</a>，然后才能操作")
            return self.printer.printMessage("login.jsp","")
        
        self.prepareCourseId =  self.params.getIntParam("prepareCourseId")
        self.prepareCourse = self.pc_svc.getPrepareCourse(self.prepareCourseId)
        if self.prepareCourse == None:
            self.printer.addMessage(u"无法加载当前备课。该备课可能不存在。")
            return self.printer.printMessage("manage/course/showPrepareCourse.py?prepareCourseId=" + str(self.prepareCourseId),"")
        
        if self.isCourseMember() == "false":
            self.printer.addMessage(u"只有管理员和备课组成员才可以查看。")
            return self.printer.printMessage("manage/course/showPrepareCourse.py?prepareCourseId=" + str(self.prepareCourseId),"")        
        
        request.setAttribute("prepareCourse", self.prepareCourse)
        return "/WEB-INF/ftl/course/common_course_show.ftl" 
        
    # 当前登录用户是否是管理员
    def canAdmin(self):
        accessControlService = __spring__.getBean("accessControlService")
        if self.loginUser.userId == self.prepareCourse.createUserId or accessControlService.isSystemAdmin(self.loginUser):
            return "true"
        else:
            return "false"

    # 当前登录用户是否是该备课的成员
    def isCourseMember(self):
        ismember = "false"
        if self.loginUser == None:
            return ismember
        if self.canAdmin() == "true":
            return "true"
        ismember = self.pc_svc.checkUserInPreCourse(self.prepareCourse.prepareCourseId, self.loginUser.userId)
        return ismember