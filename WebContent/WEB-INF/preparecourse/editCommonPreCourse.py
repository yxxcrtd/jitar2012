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

class editCommonPreCourse(BaseAction):
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.printer = MessagePrint()
        
    def execute(self):
        if self.loginUser == None:            
            self.printer.addMessage(u"请先<a href='../../login.jsp'>登录</a>，然后才能操作")
            return self.printer.printMessage("login.jsp","")
        site_config = SiteConfig()
        site_config.get_config()        

        self.prepareCourseId =  self.params.getIntParam("prepareCourseId")
        self.prepareCourse = self.pc_svc.getPrepareCourse(self.prepareCourseId)        
        
        if self.prepareCourse == None:
            self.printer.addMessage(u"未能加载备课。请重新选择一次备课。")
            return self.printer.printMessage("manage/course/showPrepareCourse.py?prepareCourseId=" + str(self.prepareCourseId),"")
        
        if self.canAdmin() == "false":
            self.printer.addMessage(u"只有 管理员 和 主备人（备课创建者）才可以修改。")
            return self.printer.printMessage("manage/course/showPrepareCourse.py?prepareCourseId=" + str(self.prepareCourseId),"")
          
        if request.getMethod() == "POST":
            return self.saveOrUpdate()
            
        request.setAttribute("loginUser", self.loginUser) 
        request.setAttribute("prepareCourse", self.prepareCourse)
        request.setAttribute("head_nav", "cocourses")
        return "/WEB-INF/ftl/course/common_course_edit.ftl"
        
        
    def saveOrUpdate(self):       
        commonContent = self.params.safeGetStringParam("commonContent")
        
        if commonContent == None or commonContent == "":
            self.printer.addMessage(u"请输入共案内容。")
            return self.printer.printMessage("manage/course/showPrepareCourse.py?prepareCourseId=" + str(self.prepareCourseId),"")
   
        self.prepareCourse.setCommonContent(commonContent)
        self.pc_svc.updatePrepareCourse(self.prepareCourse)

        self.printer.addMessage(u"操作成功。")
        self.printer.msgDesc = ""
        return self.printer.printMessage("manage/course/showPrepareCourse.py?prepareCourseId=" + str(self.prepareCourseId),"")

    def canAdmin(self):
        accessControlService = __spring__.getBean("accessControlService")
        if self.loginUser.userId == self.prepareCourse.createUserId or accessControlService.isSystemAdmin(self.loginUser):
            return "true"
        else:
            return "false"