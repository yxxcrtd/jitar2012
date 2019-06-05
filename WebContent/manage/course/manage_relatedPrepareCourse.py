from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCourseRelated
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from base_preparecourse_page import *

class manage_relatedPrepareCourse(PrepareCoursePageService):
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
                prepareCourseTopicIds = self.params.safeGetIntValues("guid")
                for id in prepareCourseTopicIds:
                    self.pc_svc.removeRelatedPrepareCourse(self.prepareCourseId,id)         
       
        pager = self.params.createPager()
        pager.itemName = u"备课"
        pager.itemUnit = u"个"       
        qry = PrepareRelatedQuery(""" pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,
                                    pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,pc.startDate,pc.endDate,
                                    pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,pc.recommendState
                                     """)
        qry.prepareCourseId = prepareCourse.prepareCourseId
        pager.totalRows = qry.count()
        course_list = qry.query_map(pager)
           
        request.setAttribute("pager",pager)
        request.setAttribute("preparecourse_list",course_list)    
        request.setAttribute("prepareCourse",prepareCourse)        
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/course/manage_relatedPrepareCourse.ftl"