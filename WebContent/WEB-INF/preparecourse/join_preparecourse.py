from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil, CommonUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse, PrepareCourseStage, PrepareCourseMember
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from preparecourse_member_query import PrepareCourseMemberQuery
from base_preparecourse_page import *

class join_preparecourse(PrepareCoursePageService):
    def __init__(self):
        self.params = ParamUtil(request)
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()        
        
    def execute(self): 
        self.getBaseData()
        
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的备课标识。")
            return
        if self.loginUser == None:
            backUrl = request.getAttribute('javax.servlet.forward.request_uri')
            if backUrl == None or backUrl == "":backUrl = request.requestURI    
            response.sendRedirect(CommonUtil.getSiteUrl(request) + "login.jsp?redUrl=" + CommonUtil.urlUtf8Encode(CommonUtil.getSiteServer(request) + backUrl))
            return
        
        prepareCourse = self.getBasePrepareCourse()
        if prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        
        page = self.getPrepareCoursePageWithCustomSkin(prepareCourse)
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1, "title":u"备课基本信息", "module":"show_preparecourse_info", "ico":"", "data":""},
                   {"id": "placerholder1", "pageId":0, "columnIndex":2, "title":"", "module":"placeholder", "ico":"", "data":""}
                  ]
        request.setAttribute("widgets", widgets)
        request.setAttribute("widget_list", widgets)
        request.setAttribute("page", page)
        request.setAttribute("prepareCourse", prepareCourse)
        
        # 检查是否是成员
        isExist = self.pc_svc.checkUserInPreCourse(prepareCourse.prepareCourseId, self.loginUser.userId)
        
        if isExist == True:
            show_result2 = u"您已经是备课  " + prepareCourse.title + u" 的成员。"
            request.setAttribute("show_result2", show_result2)
        
        if request.getMethod() == "POST":            
            content = self.params.safeGetStringParam("content")            
            if content == None or content == "":
                show_result1 = u"请输入申请内容。"
                request.setAttribute("show_result1", show_result1)
            else:
                prepareCourseMember = PrepareCourseMember()
                prepareCourseMember.setPrepareCourseId(prepareCourse.prepareCourseId)
                prepareCourseMember.setUserId(self.loginUser.userId)
                prepareCourseMember.setReplyCount(0)
                prepareCourseMember.setJoinDate(Date())
                prepareCourseMember.setStatus(1)
                prepareCourseMember.setPrivateContent(content)
                prepareCourseMember.setContentLastupdated(Date())
                self.pc_svc.addPrepareCourseMember(prepareCourseMember)

                show_result2 = u"您的申请已经提交，请等待审核。"
                request.setAttribute("show_result2", show_result2)
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/course/join_preparecourse.ftl"
    
