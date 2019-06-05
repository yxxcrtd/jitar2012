from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCourseStage,PrepareCourseTopic,PrepareCourseTopicReply
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from message_out import ShowError
from action_query import ActionQuery
from preparecourse_member_query import PrepareCourseMemberQuery
from base_preparecourse_page import *


class show_preparecourse_stage_topic_detail(PrepareCoursePageService):
    def __init__(self):
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        
    def execute(self):
        self.getBaseData()
        
        prepareCourseTopicId = self.params.getIntParam("prepareCourseTopicId")        
        
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的课程标识。")
            return
        prepareCourse =self.getBasePrepareCourse()
        if prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        
        if prepareCourseTopicId == None or prepareCourseTopicId == 0:
            self.printer.write(u"无效的讨论标识。")
            return
        
        prepareCourseTopic = self.pc_svc.getPrepareCourseTopic(prepareCourseTopicId)
        if prepareCourseTopic == None:
            self.printer.write(u"不能加载讨论对象。")
            return
        
        if self.canView(prepareCourse) == False:
            self.printer.write(u"您无权查看本内容。")
            return        
        
        currentStage = self.pc_svc.getPrepareCourseStage(self.prepareCourseStageId)
        if currentStage == None:
            self.printer.write(u"无效的流程标识。")
            return
        
        if request.getMethod() == "POST":
            title = self.params.getStringParam("replyTitle") 
            content = self.params.getStringParam("replyContent")
            if title == None or title == "":
                showError = ShowError()
                showError.msg = u"请输入标题。<a href='show_preparecourse_stage_topic_detail.py?prepareCourseTopicId=" + str(prepareCourseTopicId) + u"'>返回</a>"
                return showError.printError()
            if content == None or content == "":
                showError = ShowError()
                showError.msg = u"请输入讨论内容。<a href='show_preparecourse_stage_topic_detail.py?prepareCourseTopicId=" + str(prepareCourseTopicId) + u"'>返回</a>"
                return showError.printError()
            
            prepareCourseTopicReply = PrepareCourseTopicReply()
            prepareCourseTopicReply.setTitle(title)
            prepareCourseTopicReply.setCreateDate(Date())
            prepareCourseTopicReply.setUserId(self.loginUser.userId)
            prepareCourseTopicReply.setContent(content)
            prepareCourseTopicReply.setPrepareCourseId(prepareCourseTopic.prepareCourseId)
            prepareCourseTopicReply.setPrepareCourseStageId(prepareCourseTopic.prepareCourseStageId)
            prepareCourseTopicReply.setPrepareCourseTopicId(prepareCourseTopic.prepareCourseTopicId)
            self.pc_svc.savePrepareCourseTopicReply(prepareCourseTopicReply)
            return_url = request.getContextPath() + "/p/" + str(self.prepareCourseId) + "/" + str(self.prepareCourseStageId) + "/py/show_preparecourse_stage_topic_detail.py?prepareCourseTopicId=" + str(prepareCourseTopic.prepareCourseTopicId)
            response.sendRedirect(return_url)
            return
        
        pager = self.params.createPager()
        pager.itemName = u"讨论"
        pager.itemUnit = u"个"       
        qry = PrepareTopicReplyQuery(""" pctr.prepareCourseTopicReplyId, pctr.userId,pctr.title,pctr.createDate, pctr.content """)
        qry.prepareCourseTopicId = prepareCourseTopicId
        pager.totalRows = qry.count()
        topic_reply_list = qry.query_map(pager)    
        
        page = self.getPrepareCoursePageWithCustomSkin(prepareCourse)
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1,"title":u"备课基本信息","module":"show_preparecourse_info", "ico":"", "data":""},
                   {"id": "placerholder1", "pageId":0, "columnIndex":2,"title":"","module":"placeholder", "ico":"", "data":""}
                  ]
        request.setAttribute("widgets",widgets)
        request.setAttribute("widget_list",widgets)
        request.setAttribute("page", page)   
           
        request.setAttribute("pager",pager)
        request.setAttribute("prepareCourseTopicId",prepareCourseTopicId)        
        request.setAttribute("topic_reply_list",topic_reply_list)
        request.setAttribute("prepareCourse",prepareCourse)
        request.setAttribute("currentStage",currentStage)
        request.setAttribute("prepareCourseTopic",prepareCourseTopic)    
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/course/show_preparecourse_stage_topic_detail.ftl"