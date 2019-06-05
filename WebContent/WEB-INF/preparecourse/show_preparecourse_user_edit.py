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

class show_preparecourse_user_edit(PrepareCoursePageService):
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
                   {"id": "1", "pageId":0, "columnIndex":1, "title":u"备课基本信息", "module":"show_preparecourse_info", "ico":"", "data":""},
                   {"id": "placerholder1", "pageId":0, "columnIndex":2, "title":"", "module":"placeholder", "ico":"", "data":""}
                  ]
        
        request.setAttribute("widget_list", widgets)
        request.setAttribute("page", page)
        request.setAttribute("prepareCourse", prepareCourse)
        request.setAttribute("prepareCourseId", self.prepareCourseId)        
        
        self.prepareCourseMember = self.pc_svc.getPrepareCourseMemberByCourseIdAndUserId(self.prepareCourseId, self.loginUser.userId)
        if self.prepareCourseMember == None:
            operation_result = u"您不是该课的成员，或者加载对象失败。"
            request.setAttribute("operation_result", operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"     
        
        if request.getMethod() == "POST":
            return self.saveOrUpdate()
        
        if self.prepareCourseMember.contentType == 0:
            return "/WEB-INF/ftl/course/show_preparecourse_user_edit_first.ftl"
            #if (self.pdf2swfPath != None and self.pdf2swfPath != "") or (self.PrepareCourseFileServer != None and self.PrepareCourseFileServer != ""):
            #    return "/WEB-INF/ftl/course/show_preparecourse_user_edit_first.ftl"
            #else:
            #    self.prepareCourseMember.contentType = 1
            #    self.pc_svc.updatePrepareCourseMember(self.prepareCourseMember)
            #    response.sendRedirect("show_preparecourse_user_edit.py")
                
        else:
            ed = EncryptDecrypt()
            strPrepareCourseId = ed.encrypt(str(self.prepareCourseId) + "|0")
            ed = None            
            request.setAttribute("EncryptPrepareCourseId", strPrepareCourseId)
            request.setAttribute("prepareCourseMember", self.prepareCourseMember)
            
            prepareCourseFileServer = request.getSession().getServletContext().getInitParameter("PrepareCourseFileServer")
            if prepareCourseFileServer != None and prepareCourseFileServer != "":
                request.setAttribute("prepareCourseFileServer", prepareCourseFileServer)
                request.setAttribute("userTicket", self.getUserTicket())
            
            return "/WEB-INF/ftl/course/show_preparecourse_user_edit.ftl"
            # elif self.prepareCourseMember.contentType == 2 or self.prepareCourseMember.contentType == 3:
            #    #return self.upload_private_prepare_course()
            #    response.sendRedirect("upload_private_prepare_course.py")
            # else:
            #    self.addActionError(u"不支持的个案文档类型。")
            #   return self.ACCESS_ERROR
    
    def saveOrUpdate(self):
        cmd = self.params.safeGetStringParam("cmd")
        if cmd == "contentype":
            contentype = self.params.safeGetIntParam("contentype")
            if contentype == 0:
                self.addActionError(u"请选择一种个案编写类型。")
                return self.ACCESS_ERROR
            else:
                self.prepareCourseMember.contentType = contentype
                self.pc_svc.updatePrepareCourseMember(self.prepareCourseMember)
                response.sendRedirect("show_preparecourse_user_edit.py")
                
        privateContent = self.params.safeGetStringParam("privateContent")        
        if privateContent == None or privateContent == "":
            operation_result = u"请输入备课内容。"
            request.setAttribute("operation_result", operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
   
        self.prepareCourseMember.setPrivateContent(privateContent)
        self.pc_svc.updatePrepareCourseMember(self.prepareCourseMember)

        operation_result = u"操作成功。"
        request.setAttribute("operation_result", operation_result)
        return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
    
    def getUserTicket(self):
        cookies = request.getCookies()
        if cookies == None:
            return None
        for c in cookies:
            # 以下判断存在不安全因素，统一用户接口修改完毕后，暂且这样实现
            #if c.getName() == "UserTicket":
            if c.getName() == "CASEDUSTARUSERNAME":                
                return c.getValue()
