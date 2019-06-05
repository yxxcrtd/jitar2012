from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from base_preparecourse_page import *

class show_preparecourse_related(PrepareCoursePageService):
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
        
        qry = PrepareRelatedQuery(""" pc.createUserId,pc.leaderId,pc.memberCount,pc.articleCount,pc.resourceCount,
                                    pc.status,pc.actionCount,pc.topicCount,pc.topicReplyCount,pc.viewCount,pc.startDate,pc.endDate,
                                    pc.title, pc.prepareCourseId, pc.createDate, pc.metaSubjectId, pc.gradeId,pc.recommendState
                                     """)
        qry.prepareCourseId = self.prepareCourse.prepareCourseId
        course_list = qry.query_map(10)
        request.setAttribute("preparecourse_list",course_list)    
        request.setAttribute("prepareCourse",self.prepareCourse)
        return "/WEB-INF/ftl/course/show_preparecourse_related.ftl"
        
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
