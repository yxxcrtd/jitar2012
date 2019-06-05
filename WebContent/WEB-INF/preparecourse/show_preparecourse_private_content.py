from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse, PrepareCourseStage
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
#from preparecourse_member_query import PrepareCourseMemberQuery
from base_preparecourse_page import *

class show_preparecourse_private_content(PrepareCoursePageService):
    def __init__(self):
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        
    def execute(self):  
        self.getBaseData()
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的备课标识。")
            return
        
        prepareCourse = self.getBasePrepareCourse()
        if prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        if self.canView(prepareCourse) == False:
            self.printer.write(u"您无权查看本内容。")
            return
        qry = PrepareCourseMemberQuery(""" u.userId, u.userIcon, u.loginName,u.trueName,u.nickName,pcm.contentLastupdated,pcm.privateContent,pcm.bestState """)
        qry.prepareCourseId = self.prepareCourseId
        qry.orderType = 9
        qry.privateContentExist = True
        qry.status = 0 
        member_list = qry.query_map(10)
        
        request.setAttribute("prepareCourse", prepareCourse)
        request.setAttribute("member_list", member_list)
        request.setAttribute("prepareCourseStageId", self.prepareCourseStageId)        
        
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/course/show_preparecourse_private_content.ftl"
        
