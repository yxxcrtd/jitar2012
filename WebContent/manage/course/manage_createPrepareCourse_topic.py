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

class manage_createPrepareCourse_topic(PrepareCoursePageService):
    def __init__(self):
        self.params = ParamUtil(request)
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        
    def execute(self): 
        self.getBaseData()
        self.prepareCourseId = self.params.getIntParam("prepareCourseId")
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的备课标识。")
            return        
       
        prepareCourse = self.getBasePrepareCourse()
        if prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        
        if self.canManage(prepareCourse) == False:
            self.printer.write(u"您无权管理本备课。")
            return
        
        if request.getMethod() == "POST":
            cmd = self.params.safeGetStringParam("cmd")
            if cmd == "delete":
                prepareCourseTopicId = self.params.safeGetIntValues("prepareCourseTopicId")
                for id in prepareCourseTopicId:
                    self.pc_svc.deletePrepareCourseTopic(id)         
       
        pager = self.params.createPager()
        pager.itemName = u"讨论"
        pager.itemUnit = u"个"       
        qry = PrepareTopicQuery(""" pct.title,pct.userId,pct.createDate,pct.prepareCourseTopicId,pct.prepareCourseStageId,pcs.title as pcsTitle """)
        qry.prepareCourseId = prepareCourse.prepareCourseId
        pager.totalRows = qry.count()
        topic_list = qry.query_map(pager)
           
        request.setAttribute("pager",pager)
        request.setAttribute("topic_list",topic_list)    
        request.setAttribute("prepareCourse",prepareCourse)
        
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/course/manage_createPrepareCourse_topic.ftl"