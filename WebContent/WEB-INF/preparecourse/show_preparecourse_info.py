from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from preparecourse_member_query import PrepareCourseMemberQuery
from base_preparecourse_page import PrepareCoursePageService

class show_preparecourse_info(PrepareCoursePageService):
    def __init__(self):
        self.printer = response.getWriter()
        
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
        
        request.setAttribute("loginUser",self.loginUser)
        request.setAttribute("canManege",str(self.canManage(self.prepareCourse)))
        request.setAttribute("prepareCourse",self.prepareCourse)
        request.setAttribute("grade",self.getPrepareCourseGrade(self.prepareCourse))
        request.setAttribute("gradeLevel",self.getPrepareCourseGradeLevel(self.prepareCourse))
        request.setAttribute("subject",self.getPrepareCourseMetaSubject(self.prepareCourse))
        if self.loginUser != None:
            request.setAttribute("isMember",str(self.isPrepareCourseMember()))
        return "/WEB-INF/ftl/course/show_preparecourse_info.ftl"
        
        #以下是块注释例子的两个例子        
        """
        response.getWriter().print("<li>Hello</li>")
        """
        if 0:
            if request.getQueryString() != None:
                response.getWriter().write("<li>getQueryString = " + request.getQueryString() + "</li>")
            if request.getPathInfo() != None:
                response.getWriter().write("<li>getPathInfo = " + request.getPathInfo() + "</li>")
            if request.getContextPath() != None:
                response.getWriter().write("<li>getPathInfo = " + request.getContextPath() + "</li>")
            if request.getPathTranslated() != None:
                response.getWriter().write("<li>getPathTranslated = " + request.getPathTranslated() + "</li>")
            if request.getRequestURI() != None:
                response.getWriter().write("<li>getRequestURI = " + request.getRequestURI() + "</li>")   
            if request.getServletPath() != None:
                response.getWriter().write("<li>getServletPath = " + request.getServletPath() + "</li>")
            if request.getRequestURL() != None:
                response.getWriter().write("<li>getRequestURL = " + str(request.getRequestURL()) + "</li>")