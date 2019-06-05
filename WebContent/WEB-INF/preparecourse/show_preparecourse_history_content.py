from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil,CommonUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse, PrepareCourseEdit
from java.text import SimpleDateFormat
from java.util import Date
from java.io import File
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from base_preparecourse_page import *

class show_preparecourse_history_content(PrepareCoursePageService):
    def __init__(self):
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        
    def execute(self):          
        self.getBaseData()        
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
        
        prepareCourseEditId = self.params.getIntParam("prepareCourseEditId")
        if prepareCourseEditId == None or prepareCourseEditId == 0:
            self.printer.write(u"请选择一个共案历史记录。")
            return
        prepareCourseEdit = self.pc_svc.getPrepareCourseEdit(prepareCourseEditId)
        if prepareCourseEdit == None:
            self.printer.write(u"未能加载共案历史记录。")
            return
        
        page = self.getPrepareCoursePageWithCustomSkin(prepareCourse)
        widgets = [
                   {"id": "1", "pageId":0, "columnIndex":1, "title":u"备课基本信息", "module":"show_preparecourse_info", "ico":"", "data":""},
                   {"id": "placerholder1", "pageId":0, "columnIndex":2, "title":"", "module":"placeholder", "ico":"", "data":""},
                   {"id": "placerholder2", "pageId":0, "columnIndex":1, "title":"", "module":"placeholder", "ico":"", "data":""},
                   {"id": "placerholder3", "pageId":0, "columnIndex":1, "title":"", "module":"placeholder", "ico":"", "data":""}
                  ]
        qry = PrepareCourseMemberQuery(""" u.userId, u.userIcon, u.loginName,u.trueName,u.nickName""")  
        qry.prepareCourseId = self.prepareCourse.prepareCourseId
        user_list = qry.query_map()
        prepareCourseEdit_list = self.pc_svc.getPrepareCourseEditList(self.prepareCourseId)
        request.setAttribute("prepareCourseEdit_list", prepareCourseEdit_list)
        request.setAttribute("user_list", user_list)        
        request.setAttribute("page", page)
        request.setAttribute("widget_list", widgets)
        request.setAttribute("prepareCourse", prepareCourse)
        request.setAttribute("prepareCourseEdit", prepareCourseEdit)        
        if prepareCourse.contentType == 2 or prepareCourse.contentType == 3 or prepareCourse.contentType == 4 or prepareCourse.contentType == 5 or prepareCourse.contentType == 100:
            swf = prepareCourseEdit.content
            if swf == None:swf = ""
            if swf.find(".") > -1:
                swf = swf[0:swf.find(".")]
            
            prepareCourseFileServer = request.getSession().getServletContext().getInitParameter("PrepareCourseFileServer")
            pcFolder = CommonUtil.GetPrepareCourseFolder(request)
            if prepareCourseFileServer == None or prepareCourseFileServer == "":
                swfUrl = pcFolder[1] + str(self.prepareCourse.prepareCourseId) + "/"
                courseFileServer = CommonUtil.getSiteUrl(request)
            else:
                if prepareCourseFileServer.endswith("/") == False:prepareCourseFileServer += "/"
                courseFileServer = prepareCourseFileServer
                swfUrl = prepareCourseFileServer + "preparecoursefolder/" + str(self.prepareCourse.prepareCourseId) + "/"
            swfUrl = swfUrl + swf + ".swf"
            request.setAttribute("swfUrl", swfUrl)
            request.setAttribute("courseFileServer", courseFileServer)
            
        response.setContentType("text/html; charset=UTF-8")        
        return "/WEB-INF/ftl/course/show_preparecourse_history_content2.ftl"
