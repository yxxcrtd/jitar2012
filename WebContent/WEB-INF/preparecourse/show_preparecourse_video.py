from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCourseStage
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from base_preparecourse_page import *

class show_preparecourse_video(PrepareCoursePageService):
    def __init__(self):
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        
    def execute(self):  
        self.getBaseData()
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的备课标识。")
            return
        
        prepareCourse = self.getBasePrepareCourse()
        if prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        if self.canView(prepareCourse) == False:
            self.printer.write(u"您无权查看本内容。")
            return
        qry = PrepareCourseVideoQuery(""" u.userId,u.loginName,u.trueName,u.nickName,pcv.videoTitle,pcv.videoId,v.flvThumbNailHref,v.flvHref """)
        qry.prepareCourseId = self.prepareCourseId
        qry.orderType =0
        pcv_list = qry.query_map(4)
        
        request.setAttribute("prepareCourse",prepareCourse)
        request.setAttribute("pcv_list", pcv_list)
        request.setAttribute("prepareCourseStageId", self.prepareCourseStageId)        
        
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/course/show_preparecourse_video.ftl"
        