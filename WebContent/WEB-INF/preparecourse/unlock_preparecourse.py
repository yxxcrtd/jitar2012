from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil, EncryptDecrypt, CommonUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse, PrepareCourseStage, PrepareCourseEdit
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from base_preparecourse_page import *

class unlock_preparecourse(PrepareCoursePageService):
    def __init__(self):
        self.printer = response.getWriter() 
        self.prepareCourseEdit = None
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()

    def execute(self):
        self.getBaseData()        
        response.setContentType("text/html; charset=UTF-8")
        if self.loginUser == None:
            backUrl = request.getAttribute('javax.servlet.forward.request_uri')
            if backUrl == None or backUrl == "":backUrl = request.requestURI    
            response.sendRedirect(CommonUtil.getSiteUrl(request) + "login.jsp?redUrl=" + CommonUtil.urlUtf8Encode(CommonUtil.getSiteServer(request) + backUrl))
            return
        
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的课程标识。")
            return
        prepareCourse = self.getBasePrepareCourse()
        if prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        if self.canView(prepareCourse) == False:
            self.printer.write(u"您无权查看本内容。")
            return
        
        if self.isPrepareCourseMember() == False:
            self.printer.write(u"您无权编辑备课内容。")
            return
        
        # 判断锁定人是否是当前用户
        if prepareCourse.lockedUserId == None or prepareCourse.lockedUserId == 0:
            self.printer.write(u"当前没有被锁定。")
            return
        if prepareCourse.lockedUserId != self.loginUser.userId:
            self.printer.write(u"当前集备的锁定者不是您本人。锁定者userId = " + str(prepareCourse.lockedUserId))
            return
        prepareCourse.setLockedUserId(0)
        self.pc_svc.updatePrepareCourse(prepareCourse)
        response.sendRedirect("../../0/")
