from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCourseStage
from base_action import BaseAction
from base_preparecourse_page import *

class manage_pc(PrepareCoursePageService):
    def __init__(self):
        self.printer = response.getWriter()
        self.params = ParamUtil(request)
        self.pc_svc = __jitar__.getPrepareCourseService()        
        
    def execute(self):
        if self.loginUser == None:
            self.printer.write(u"请先登录。<a href='../../login.jsp'>转到登录页面</a>")
            return
            
        self.getBaseData()        
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的备课标识。")
            return        
        self.prepareCourse = self.getBasePrepareCourse()
        if self.prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        
        if self.canManage(self.prepareCourse) == False:
            self.printer.write(u"权限被拒绝。")
            return
        
        cmd = self.params.safeGetStringParam("cmd")
               
        request.setAttribute("prepareCourse",self.prepareCourse)       
        request.setAttribute("prepareCourseId",self.prepareCourseId)
        
        if cmd == "left":
            return self.show_left()
        elif cmd == "right":
            return self.show_right()
        else:
            response.setContentType("text/html; charset=UTF-8")
            return "/WEB-INF/ftl/course/manage_pc.ftl"
    
    def show_left(self):
        group = self.pc_svc.getGroupOfPrepareCourse(self.prepareCourseId)
        request.setAttribute("group",group)
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/course/manage_pc_left.ftl"
    
    def show_right(self):
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/course/manage_pc_right.ftl"