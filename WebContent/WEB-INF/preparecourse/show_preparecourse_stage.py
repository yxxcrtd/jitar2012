from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCourseStage
from java.text import SimpleDateFormat
from java.util import Date
from cn.edustar.jitar.util import DateUtil
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from preparecourse_member_query import PrepareCourseMemberQuery
from base_preparecourse_page import PrepareCoursePageService

class show_preparecourse_stage(PrepareCoursePageService):
    def __init__(self): 
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        
    def execute(self):
        self.getBaseData()
        response.setContentType("text/html; charset=UTF-8")       

        prepareCourse = self.getBasePrepareCourse()
        if prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        if self.canView(prepareCourse) == False:
            self.printer.write(u"您无权查看本内容。")
            return        
        
        pc_stage_list = self.pc_svc.getPrepareCourseStageList(self.prepareCourseId)        
        request.setAttribute("pc_stage_list",pc_stage_list)
        request.setAttribute("canManege",str(self.canManage(prepareCourse)))
        request.setAttribute("prepareCourse",prepareCourse)
        if self.prepareCourseStageId > 0:
            request.setAttribute("prepareCourseStageId",self.prepareCourseStageId)
            prepareCourseStage = self.pc_svc.getPrepareCourseStage(self.prepareCourseStageId)
            request.setAttribute("prepareCourseStage",prepareCourseStage)
        else:
            request.setAttribute("prepareCourseStageId",0)
        
        if pc_stage_list == None:
            return "/WEB-INF/ftl/course/show_preparecourse_stage.ftl"
        
        currentStage = -1
        jj = 0
        for x in pc_stage_list:
            if DateUtil.todayBetween(x.beginDate, x.finishDate):
                currentStage = jj
            jj = jj + 1
        request.setAttribute("currentStage",currentStage)
        
        return "/WEB-INF/ftl/course/show_preparecourse_stage.ftl"