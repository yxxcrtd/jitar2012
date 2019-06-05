from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil, CommonUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCourseStage
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from preparecourse_member_query import PrepareCourseMemberQuery
from base_preparecourse_page import *

class show_preparecourse_common_abstract(PrepareCoursePageService):
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
        if prepareCourse.contentType == 2 or prepareCourse.contentType == 3 or prepareCourse.contentType == 4 or prepareCourse.contentType == 100:
            noHtmlContent = u"本共案为文档类型的，请点击查看。"
        else:
            noHtmlContent = CommonUtil.getTxtWithoutHTMLElement(prepareCourse.commonContent)
        request.setAttribute("prepareCourse",prepareCourse)        
        request.setAttribute("noHtmlContent",noHtmlContent)        
        response.setContentType("text/html; charset=UTF-8")      
        return "/WEB-INF/ftl/course/show_preparecourse_common_abstract.ftl"
        