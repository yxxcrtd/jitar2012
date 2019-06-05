from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCourseStage,PrepareCourseEdit
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from base_preparecourse_page import *

class show_preparecourse_user_content_type(PrepareCoursePageService):
    def __init__(self):
        self.printer = response.getWriter()
        self.user_svc = __jitar__.getUserService()
        
    def execute(self):
        self.getBaseData()        
        response.setContentType("text/html; charset=UTF-8")
        if self.loginUser == None:
            self.printer.write(u"请先登录。<a href='../../../../login.jsp'>登录页面</a>")
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
                   {"id": "1", "pageId":0, "columnIndex":1,"title":u"备课基本信息","module":"show_preparecourse_info", "ico":"", "data":""},
                   {"id": "placerholder1", "pageId":0, "columnIndex":2,"title":"","module":"placeholder", "ico":"", "data":""}
                  ]
        
        request.setAttribute("widget_list",widgets)
        request.setAttribute("page", page)
        request.setAttribute("prepareCourse",prepareCourse)
        request.setAttribute("prepareCourseId",self.prepareCourseId)        
        
        self.prepareCourseMember = pc_svc.getPrepareCourseMemberByCourseIdAndUserId(self.prepareCourseId, self.loginUser.userId)
        if self.prepareCourseMember == None:
            operation_result = u"您不是该课的成员，或者加载对象失败。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"     
        
        if request.getMethod() == "POST":
            return self.saveOrUpdate()        
        request.setAttribute("prepareCourseMember",self.prepareCourseMember)        
        return "/WEB-INF/ftl/course/show_preparecourse_user_content_type.ftl"
    
    def saveOrUpdate(self):
        contentype = self.params.safeGetIntParam("contentype")
        if contentype == 0:
            self.addActionError(u"请选择一种个案编写类型。")
            return self.ACCESS_ERROR

        self.prepareCourseMember.contentType = contentype
        pc_svc.updatePrepareCourseMember(self.prepareCourseMember)

        operation_result = u"修改成功。"
        request.setAttribute("operation_result",operation_result)
        return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"