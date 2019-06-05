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

class manage_createPrepareCourse_reply(PrepareCoursePageService):
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
        
        if request.getMethod() == "POST":
            cmd = self.params.safeGetStringParam("cmd")
            if cmd == "delete":
                prepareCourseTopicReplyId = self.params.safeGetIntValues("prepareCourseTopicReplyId")
                for id in prepareCourseTopicReplyId:
                    self.pc_svc.deletePrepareCourseTopicReply(self.prepareCourseId,id)
       
        pager = self.params.createPager()
        pager.itemName = u"回复"
        pager.itemUnit = u"个"
        qry = PrepareTopicReplyQuery(""" pctr.prepareCourseTopicReplyId, pctr.prepareCourseTopicId, pctr.prepareCourseStageId, pctr.title, pctr.createDate, pctr.userId,
                                    pctr.content,pct.title as pctTitle
                                    """)
       
        qry.prepareCourseId = self.prepareCourse.prepareCourseId
        pager.totalRows = qry.count()
        reply_list = qry.query_map(pager)      
           
        request.setAttribute("pager",pager)
        request.setAttribute("reply_list",reply_list)    
        request.setAttribute("prepareCourse",self.prepareCourse)
        
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/course/manage_createPrepareCourse_reply.ftl"
    