from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCourseStage,PrepareCourseMember
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery

from base_preparecourse_page import *

class manage_createPrepareCourse_commoncourse(PrepareCoursePageService):
    def __init__(self):
        self.params = ParamUtil(request)
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        
    def execute(self): 
        self.getBaseData()
        self.prepareCourseId = self.params.getIntParam("prepareCourseId")
        if self.prepareCourseId == 0:
            self.printer.println(u"无效的备课标识。")
            return
       
        self.prepareCourse = self.getBasePrepareCourse()
        if self.prepareCourse == None:
            self.printer.println(u"没有加载到所请求的备课。")
            return
        
        if self.canManage(self.prepareCourse) == False:
            self.printer.println(u"您无权管理本备课。")
            return
        editId = self.params.getIntParam("editId")
        if editId != 0:
            prepareCourseEdit = self.pc_svc.getPrepareCourseEdit(editId)
            if prepareCourseEdit != None:
                self.prepareCourse.setPrepareCourseEditId(prepareCourseEdit.prepareCourseEditId)    
                self.prepareCourse.setCommonContent(prepareCourseEdit.content)
                self.pc_svc.updatePrepareCourse(self.prepareCourse)
                #清空缓存
                self.removePrepareCourseCache()
                response.sendRedirect("manage_createPrepareCourse_commoncourse.py?prepareCourseId=" + str(self.prepareCourseId))
        
        if request.getMethod() == "POST":
            cmd = self.params.safeGetStringParam("cmd")
            # print "cmd = ",cmd
            if cmd == "1":
                prepareCourseEditId = self.params.safeGetIntValues("prepareCourseEditId")
                for id in prepareCourseEditId:
                    if self.prepareCourse.prepareCourseEditId == id:
                        self.prepareCourse.setPrepareCourseEditId(0)
                        self.prepareCourse.setCommonContent("")
                        self.pc_svc.updatePrepareCourse(self.prepareCourse)
                        #清空缓存
                        self.removePrepareCourseCache()
                    self.pc_svc.deletePrepareCourseEdit(id)
                    
        edit_list = self.pc_svc.getPrepareCourseEditList(self.prepareCourse.prepareCourseId)        
        request.setAttribute("edit_list",edit_list)    
        request.setAttribute("prepareCourse",self.prepareCourse)
        
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/course/manage_createPrepareCourse_commoncourse.ftl"