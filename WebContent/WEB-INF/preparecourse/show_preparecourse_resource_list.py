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


class show_preparecourse_resource_list(PrepareCoursePageService):
    def __init__(self):
        self.params = ParamUtil(request)
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()        
        
    def execute(self): 
        self.getBaseData()
        
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的备课标识。")
            return
        
        if self.prepareCourseStageId == 0:
            self.printer.write(u"无效的备课流程标识。")
            return
        
        prepareCourse = self.getBasePrepareCourse()
        if prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        if self.canView(prepareCourse) == False:
            self.printer.write(u"您无权查看本内容。")
            return

        if self.prepareCourseStageId == 0:
            currentStage = self.pc_svc.getCurrentPrepareCourseStage(self.prepareCourseId)            
        else:
            currentStage = self.pc_svc.getPrepareCourseStage(self.prepareCourseStageId)
            
        
        if currentStage == None:
            self.printer.write(u"无法加载流程。")
            return
        
        if currentStage.prepareCourseId != self.prepareCourseId:
            self.printer.write(u"加载的流程不是当前备课的流程。")
            return
            
        pager = self.params.createPager()
        pager.itemName = u"资源"
        pager.itemUnit = u"个"       
        qry = PrepareResourceQuery(""" pcr.userId,pcr.resourceId,pcr.resourceTitle,pcr.createDate """)
        qry.prepareCourseStageId = currentStage.prepareCourseStageId
        pager.totalRows = qry.count()
        resource_list = qry.query_map(pager)    
        
        page = self.getPrepareCoursePageWithCustomSkin(prepareCourse)
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1,"title":u"备课基本信息","module":"show_preparecourse_info", "ico":"", "data":""},
                   {"id": "placerholder1", "pageId":0, "columnIndex":2,"title":"","module":"placeholder", "ico":"", "data":""}
                  ]
        request.setAttribute("widgets",widgets)
        request.setAttribute("widget_list",widgets)
        request.setAttribute("page", page)   
           
        request.setAttribute("pager",pager) 
        request.setAttribute("resource_list",resource_list)
        request.setAttribute("prepareCourse",prepareCourse)
        request.setAttribute("currentStage",currentStage)
        
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/course/show_preparecourse_stage_resource_list.ftl"