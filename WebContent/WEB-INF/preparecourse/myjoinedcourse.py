from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from site_config import SiteConfig
from cn.edustar.jitar.pojos import PrepareCourse
from message_out import MessagePrint
from base_action import BaseAction
from preparecourse_query import PrepareCourseQuery
from preparecourse_member_query import PrepareCourseMemberQuery

class myjoinedcourse(BaseAction):
    def __init__(self):
        self.params = ParamUtil(request)
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.sbj_svc = __jitar__.getSubjectService()
        self.printer = MessagePrint()
        self.out = response.getWriter()
        
    def execute(self):
        if self.loginUser == None:
            self.out.println(u"请先登录。")
            return
        
        if request.getMethod() == "POST":            
            return self.coursePost()
        else:        
            qry = PrepareCourseMemberQuery(""" pc.title, pc.prepareCourseId, pc.createDate,pc.metaSubjectId,pc.gradeId,pc.createUserId """)
            qry.userId = self.loginUser.userId
            pager = self.params.createPager()
            pager.itemName = u"备课"
            pager.itemUnit = u"个"
            pager.pageSize = 20
            pager.totalRows = qry.count()
            preparecourse_list = qry.query_map(pager)
            request.setAttribute("loginUser", self.loginUser)
            request.setAttribute("preparecourse_list", preparecourse_list)
            request.setAttribute("pager", pager)
            
            return "/WEB-INF/ftl/course/myjoinedcourse.ftl" 
    
    def coursePost(self):
        guids = self.params.safeGetIntValues("guid")
        if guids.size() < 1:
            ErrMsg = u"请先选择一个课程"
            self.out.write(ErrMsg)
            return
        for pcId in guids:
            self.pc_svc.deletePrepareCourseMember(pcId,self.loginUser.userId)
        
        response.sendRedirect("myjoinedcourse.py")
        return