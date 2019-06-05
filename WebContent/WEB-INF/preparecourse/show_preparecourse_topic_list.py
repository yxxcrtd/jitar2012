from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCourseStage
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from preparecourse_member_query import PrepareCourseMemberQuery
from base_preparecourse_page import *

class show_preparecourse_topic_list(PrepareCoursePageService):
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
        
        currentStage = self.pc_svc.getPrepareCourseStage(self.prepareCourseStageId)
        if currentStage == None:
            self.printer.write(u"无效的流程标识。")
            return      
        
        pager = self.params.createPager()
        pager.itemName = u"讨论"
        pager.itemUnit = u"个"       
        qry = PrepareTopicQuery(""" pct.prepareCourseTopicId, pct.userId,pct.title,pct.createDate """)
        qry.prepareCourseStageId = currentStage.prepareCourseStageId        
        pager.totalRows = qry.count()
        topic_list = qry.query_map(pager)    
        
        page = self.getPrepareCoursePageWithCustomSkin(prepareCourse)
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1,"title":u"备课基本信息","module":"show_preparecourse_info", "ico":"", "data":""},
                   {"id": "placerholder1", "pageId":0, "columnIndex":2,"title":"","module":"placeholder", "ico":"", "data":""}
                  ]
        request.setAttribute("widgets",widgets)
        request.setAttribute("widget_list",widgets)
        request.setAttribute("page", page)   
           
        request.setAttribute("pager",pager) 
        request.setAttribute("topic_list",topic_list)
        request.setAttribute("prepareCourse",prepareCourse)
        request.setAttribute("currentStage",currentStage)        
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/course/show_preparecourse_stage_topic_list.ftl"