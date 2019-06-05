from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse
from java.text import SimpleDateFormat
from java.util import Date
from message_out import MessagePrint
from base_action import *
from base_blog_page import *
from preparecourse_member_query import PrepareCourseMemberQuery
from base_preparecourse_page import PrepareCoursePageService
from action_query import ActionQuery

class show_preparecourse_index(PrepareCoursePageService, PageCheckMixiner):
    def __init__(self):    
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        self.sbj_svc = __jitar__.getSubjectService()
        self.act_svc = __jitar__.getActionService()
        self.page_svc = __jitar__.getPageService()        
        self.printer = MessagePrint()
        
    def execute(self):
        self.getBaseData()

        if self.prepareCourse == None:
            self.printer.addMessage(u"未能加载备课。请重新选择一次备课。")
            return self.printer.printMessage("cocourses.action","")
        
        if self.prepareCourse.status != 0:
            self.printer.addMessage(u"该备课状态处于非正常状态，目前无法进行访问。")
            return self.printer.printMessage("cocourses.action","")
        
        canVisitCourse = self.canView(self.prepareCourse)
        if canVisitCourse == False:
            self.printer.addMessage(u"只有该备课成员才能进行查看。")
            return self.printer.printMessage("cocourses.action","")        
        
        grade = self.sbj_svc.getGrade(self.prepareCourse.gradeId)
        metaSubject = self.sbj_svc.getMetaSubjectById(self.prepareCourse.metaSubjectId)
        
        page = self.getPrepareCoursePageWithCustomSkin(self.prepareCourse)

        if page == None:
            self.printer.addMessage(u"无法加载页面。")
            return self.printer.printMessage("cocourses.action","")
        
        self.widget_list = self.getPageWidgets(page)
        request.setAttribute("widget_list", self.widget_list)
        request.setAttribute("widgets", self.widget_list)
                
        request.setAttribute("page", page)
        if self.canManage(self.prepareCourse) == True:
            request.setAttribute("visitor_role", "admin")
        request.setAttribute("canVisitCourse", canVisitCourse)
        request.setAttribute("grade", grade)
        request.setAttribute("metaSubject", metaSubject)
        request.setAttribute("loginUser", self.loginUser) 
        request.setAttribute("prepareCourse", self.prepareCourse)
        request.setAttribute("head_nav", "cocourses")
        request.setAttribute("canAdmin",self.canAdmin())
                
        precs_list = self.pc_svc.getPrepareCourseStageList(self.prepareCourseId)
        self.pc_svc.countPrepareCourseData(self.prepareCourseId)
        self.pc_svc.addViewCount(self.prepareCourseId)        
        request.setAttribute("precoursestage_list", precs_list)
        return "/WEB-INF/ftl/course/show_preparecourse_index.ftl" 