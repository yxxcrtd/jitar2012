from cn.edustar.jitar.util import ParamUtil, CommonUtil
from cn.edustar.jitar.data import *
from base_action import BaseAction
from cn.edustar.jitar.jython import BaseAdminAction
from preparecourse_query import PrepareCourseQuery
from cn.edustar.jitar.pojos import PrepareCourse

class admin_course_list(BaseAdminAction):
    def __init__(self):
        self.params = ParamUtil(request)
        self.pc_svc = __jitar__.getPrepareCourseService()  
    
    def execute(self):
        if self.loginUser == None:
            backUrl = request.getAttribute('javax.servlet.forward.request_uri')
            if backUrl == None or backUrl == "":backUrl = request.requestURI    
            response.sendRedirect(CommonUtil.getSiteUrl(request) + "login.jsp?redUrl=" + CommonUtil.urlUtf8Encode(CommonUtil.getSiteServer(request) + backUrl))
            return
        
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"没有管理权限.")
            return ActionResult.ERROR
        if request.getMethod() == "POST":            
            return self.coursePost()
        else: 
            qry = PrepareCourseQuery(""" pc.title, pc.prepareCourseId, pc.createDate,pc.metaSubjectId,pc.gradeId,pc.createUserId,u.loginName,u.trueName """)
            pager = self.params.createPager()
            pager.itemName = u"备课"
            pager.itemUnit = u"个"
            pager.pageSize = 20
            pager.totalRows = qry.count()
            preparecourse_list = qry.query_map(pager)
            request.setAttribute("preparecourse_list", preparecourse_list)
            request.setAttribute("pager", pager)
            response.setContentType("text/html; charset=UTF-8")
            return "/WEB-INF/ftl/course/admin_course_list.ftl"
    
    def coursePost(self):
        guids = self.params.safeGetIntValues("guid")
        if guids.size() < 1:
            ErrMsg = u"请先选择一个课程"
            self.out.write(ErrMsg)
            return
        for pcId in guids:
            self.pc_svc.deletePrepareCourse(pcId)
        
        response.sendRedirect("admin_course_list.py")
        return