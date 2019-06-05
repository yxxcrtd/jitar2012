from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse
from java.text import SimpleDateFormat
from java.util import Date
from message_out import MessagePrint
from base_action import *
from base_blog_page import *
from preparecourse_member_query import PrepareCourseMemberQuery
from base_preparecourse_page import PrepareCoursePageService
from action_query import ActionQuery


class show_preparecourse_statis(PrepareCoursePageService):
    def __init__(self):
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        
    def execute(self):
        self.getBaseData()
        
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的课程标识。")
            return
        prepareCourse = self.getBasePrepareCourse()
        if prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        if self.canView(prepareCourse) == False:
            self.printer.write(u"您无权查看本内容。")
            return
        request.setAttribute("prepareCourse",prepareCourse)
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/course/show_preparecourse_statis.ftl"