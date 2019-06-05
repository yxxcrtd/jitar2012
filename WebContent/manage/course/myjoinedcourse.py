from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from site_config import SiteConfig
from cn.edustar.jitar.pojos import PrepareCourse
from message_out import MessagePrint
from base_action import BaseAction
from base_preparecourse_page import *

class myjoinedcourse(PrepareCoursePageService):
    def __init__(self):
        self.params = ParamUtil(request)
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.sbj_svc = __jitar__.getSubjectService()
        self.printer = MessagePrint()
        self.out = response.getWriter()
        
    def execute(self):
        if self.loginUser == None:
            self.out.println(u"<a href='../../login.jsp' target='_top'>请先登录。</a>")
            return
        request.setAttribute("userid", self.loginUser.userId)
        if request.getMethod() == "POST":            
            return self.coursePost()
        else:
            self.getBaseData() 
            qry = PrepareCourseMemberQuery(" pc.title, pc.prepareCourseId, pc.createDate,pc.metaSubjectId,pc.gradeId,pc.createUserId,pc.leaderId,pcm.contentType ")
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
