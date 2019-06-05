from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from base_preparecourse_page import PrepareCoursePageService

class show_preparecourse_member(PrepareCoursePageService):
    def __init__(self):
        
        self.printer = response.getWriter()        
        
    def execute(self):
        self.getBaseData()
        
        response.setContentType("text/html; charset=UTF-8")
        if self.prepareCourseId == 0:
            actionErrors = [u"无效的课程标识。"]
            request.setAttribute("actionErrors", actionErrors)            
            return self.ERROR
        prepareCourse = self.getBasePrepareCourse()
        if prepareCourse == None:
            actionErrors = [u"没有加载到所请求的备课。"]
            request.setAttribute("actionErrors", actionErrors)            
            return self.ERROR
        
        if self.canView(prepareCourse) == False:
            actionErrors = [u"您无权查看本内容。"]
            request.setAttribute("actionErrors", actionErrors)            
            return self.ERROR
                    
        qry = PrepareCourseMemberQuery(""" pcm.replyCount, pcm.joinDate, u.userId, u.userIcon, u.loginName,u.trueName,u.nickName,u.unitId, unit.unitName """)
        qry.prepareCourseId = self.prepareCourseId
        qry.status = 0
        member_list = qry.query_map(10)
        
        request.setAttribute("prepareCourse", prepareCourse)
        request.setAttribute("member_list", member_list)
        request.setAttribute("prepareCourseStageId", self.prepareCourseStageId)
        
        return "/WEB-INF/ftl/course/show_preparecourse_member.ftl"
