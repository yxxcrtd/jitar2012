from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCourseStage,PrepareCourseMember
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery

from base_preparecourse_page import *

class manage_leader(PrepareCoursePageService):
    def __init__(self):
        self.params = ParamUtil(request)
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        
    def execute(self): 
        self.getBaseData()
        self.prepareCourseId = self.params.getIntParam("prepareCourseId")
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的备课标识。")
            return
    
        self.prepareCourse = self.pc_svc.getPrepareCourse(self.prepareCourseId)
        if self.prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        
        if self.canManage(self.prepareCourse) == False:
            self.printer.write(u"您无权管理本备课。")
            return
        
        cmd = self.params.safeGetStringParam("cmd")
        leaderId = self.params.getIntParam("leaderId")
        if self.pc_svc.checkUserInPreCourse(self.prepareCourseId, leaderId) == False:
            self.printer.write(u"你选择的用户不属于备课：" + self.prepareCourse.getTitle())
            return
            
        if cmd == "add":
            self.prepareCourse.setLeaderId(leaderId)
            self.pc_svc.updatePrepareCourse(self.prepareCourse)
        if cmd == "delete":
            self.prepareCourse.setLeaderId(self.prepareCourse.createUserId)
            self.pc_svc.updatePrepareCourse(self.prepareCourse)
        
        response.sendRedirect("manage_createPrepareCourse_member.py?prepareCourseId=" + str(self.prepareCourseId))