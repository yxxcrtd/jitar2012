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

class show_preparecourse_common_edit(PrepareCoursePageService):
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
        
        
        page = self.getPrepareCoursePageWithCustomSkin(prepareCourse)
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1, "column":1, "title":u"备课基本信息", "module":"show_preparecourse_info", "ico":"", "data":""},
                   {"id": "placerholder1", "pageId":0, "columnIndex":2, "column":2, "title":"", "module":"placeholder", "ico":"", "data":""}
                  ]

        if self.prepareCourse.lockedUserId > 0 and self.prepareCourse.lockedUserId != self.loginUser.userId:
            user = self.user_svc.getUserById(self.prepareCourse.lockedUserId)
            if user == None:
                self.printer.write(u"此备课已经被 未知的人 签出，你暂时不能进行编辑该备课。")
            else:
                d = self.prepareCourse.lockedDate
                f = SimpleDateFormat(u"yyyy年M月d日 H点m分s秒")
                d = f.format(d)
                self.printer.write(u"此备课已经被 " + user.trueName + u" 于 " + d + u" 签出，你暂时不能进行编辑该备课。")
            return
        else:
            # 设置锁标记，
            if prepareCourse.contentType == None or (prepareCourse.contentType != 2 and prepareCourse.contentType != 3 and prepareCourse.contentType != 4):
                self.prepareCourse.setLockedUserId(self.loginUser.userId)
                self.prepareCourse.setLockedDate(Date())
                self.pc_svc.updatePrepareCourse(self.prepareCourse)
        request.setAttribute("widget_list", widgets)
        request.setAttribute("page", page)
        request.setAttribute("prepareCourse", prepareCourse)
        request.setAttribute("prepareCourseId", self.prepareCourseId)
        ed = EncryptDecrypt()
        strPrepareCourseId = ed.encrypt(str(self.prepareCourseId) + "|1")
        ed = None            
        request.setAttribute("EncryptPrepareCourseId", strPrepareCourseId)
        
        prepareCourseFileServer = request.getSession().getServletContext().getInitParameter("PrepareCourseFileServer")
        if prepareCourseFileServer != None and prepareCourseFileServer != "":
            request.setAttribute("prepareCourseFileServer", prepareCourseFileServer)
            request.setAttribute("userTicket", self.getUserTicket())
        if request.getMethod() == "POST":
            return self.saveOrUpdate()        
        
        return "/WEB-INF/ftl/course/show_preparecourse_common_edit.ftl"
    
    def saveOrUpdate(self):       
        commonContent = self.params.safeGetStringParam("commonContent")
        optype = self.params.getIntParam("optype")
        if optype == 0:            
            if commonContent == None or commonContent == "":
                self.printer.write(u"请输入共案内容。")
                return
            if self.prepareCourse.lockedUserId != self.loginUser.userId:
                user = self.user_svc.getUserById(self.prepareCourse.lockedUserId)
                if user == None:
                    self.printer.write(u"本次编辑已经被 未知的人 签出。")
                else:
                    self.printer.write(u"本次编辑已经被 " + user.trueName + u" 签出。")                
                return           
        
            self.prepareCourseEdit = PrepareCourseEdit()        
            self.prepareCourseEdit.setContent(commonContent)
            self.prepareCourseEdit.setEditDate(Date())
            self.prepareCourseEdit.setLockStatus(0)
            self.prepareCourseEdit.setEditUserId(self.loginUser.userId)
            self.prepareCourseEdit.setPrepareCourseId(self.prepareCourseId)        
            self.pc_svc.updatePrepareCourseEdit(self.prepareCourseEdit)
            
            self.prepareCourse.setLockedUserId(0)
            self.prepareCourse.setCommonContent(commonContent)
            self.prepareCourse.setPrepareCourseEditId(self.prepareCourseEdit.prepareCourseEditId)
            self.pc_svc.updatePrepareCourse(self.prepareCourse)
        else:
            self.prepareCourse.setLockedUserId(0)
            self.pc_svc.updatePrepareCourse(self.prepareCourse)
        operation_result = u"操作成功。"
        request.setAttribute("operation_result", operation_result)
        return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
    
    def getUserTicket(self):
        cookies = request.getCookies()
        if cookies == None:
            return None
        for c in cookies:
            if c.getName() == "CASEDUSTARUSERNAME":
                return c.getValue()
