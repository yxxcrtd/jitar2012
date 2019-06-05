from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse, PrepareCourseStage, PrepareCourseMember
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from base_preparecourse_page import *


class select_course_member(PrepareCoursePageService):
    def __init__(self):
        self.params = ParamUtil(request)
        self.printer = response.getWriter()
        self.pc_svc = __jitar__.getPrepareCourseService()
        
    def execute(self):
        self.getBaseData()
        self.prepareCourseId = self.params.safeGetIntParam("prepareCourseId")
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的备课标识。")
            return
        self.prepareCourse = self.pc_svc.getPrepareCourse(self.prepareCourseId)
        if self.prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        
        qry = PrepareCourseMemberQuery(""" pcm.replyCount, pcm.joinDate,pcm.status, pcm.privateContent, pcm.bestState, u.userId, u.userIcon, u.loginName,u.trueName,u.nickName,u.unitId, unit.unitName""")     
        qry.prepareCourseId = self.prepareCourseId
        qry.status = 0        
        user_list = qry.query_map(qry.count())
        request.setAttribute("user_list", user_list)
        request.setAttribute("prepareCourse", self.prepareCourse)
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/course/select_course_member.ftl"