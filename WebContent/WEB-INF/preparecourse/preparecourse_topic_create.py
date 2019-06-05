from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCourseStage,PrepareCourseTopic
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from message_out import ShowError
from base_blog_page import *
from action_query import ActionQuery
from preparecourse_member_query import PrepareCourseMemberQuery
from base_preparecourse_page import *

class preparecourse_topic_create(PrepareCoursePageService):
    def __init__(self):
        self.params = ParamUtil(request)
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        
    def execute(self):
        self.getBaseData()
        
        response.setContentType("text/html; charset=UTF-8")
        
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的课程标识。")
            return
        self.prepareCourse = self.getBasePrepareCourse()
        if self.prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        if self.canView(self.prepareCourse) == False:
            self.printer.write(u"您无权查看本内容。")
            return
        
        currentStage = self.pc_svc.getPrepareCourseStage(self.prepareCourseStageId)
        if currentStage == None:
            self.printer.write(u"无效的流程标识。")
            return       
        
        if request.getMethod() == "POST":
            title = self.params.getStringParam("topic_title")
            content = self.params.getStringParam("topicContent")
            if title == None or title == "":
                showError = ShowError()
                showError.msg = u"请输入标题。<a href='preparecourse_topic_create.py'>返回</a>"
                return showError.printError()
            if content == None or content == "":
                showError = ShowError()
                showError.msg = u"请输入讨论内容。<a href='preparecourse_topic_create.py'>返回</a>"
                return showError.printError()
            
            prepareCourseTopic = PrepareCourseTopic()
            prepareCourseTopic.setPrepareCourseId(self.prepareCourseId)
            prepareCourseTopic.setPrepareCourseStageId(self.prepareCourseStageId)
            prepareCourseTopic.setTitle(title)
            prepareCourseTopic.setContent(content)
            prepareCourseTopic.setUserId(self.loginUser.userId)
            prepareCourseTopic.setCreateDate(Date())
            self.pc_svc.savePrepareCourseTopic(prepareCourseTopic)
            return_url = request.getContextPath() + "/p/" + str(self.prepareCourseId) + "/" + str(self.prepareCourseStageId) + "/"
            response.sendRedirect(return_url)
            return        
            
        page = self.getPrepareCoursePageWithCustomSkin(self.prepareCourse)
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1,"title":u"备课基本信息","module":"show_preparecourse_info", "ico":"", "data":""},
                   {"id": "placerholder1", "pageId":0, "columnIndex":2,"title":"","module":"placeholder", "ico":"", "data":""}
                  ]
        request.setAttribute("widgets",widgets)
        request.setAttribute("widget_list",widgets)
        request.setAttribute("page", page)
        request.setAttribute("prepareCourse",self.prepareCourse)
        request.setAttribute("currentStage",currentStage)
        
        response.setContentType("text/html; charset=UTF-8")        
        return "/WEB-INF/ftl/course/preparecourse_topic_create.ftl"
    