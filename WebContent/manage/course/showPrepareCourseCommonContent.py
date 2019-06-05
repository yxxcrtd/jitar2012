from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from site_config import SiteConfig
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCourseStage
from java.text import SimpleDateFormat
from java.util import Date
from message_out import MessagePrint
from base_action import BaseAction
from base_preparecourse_page import *

class showPrepareCourseCommonContent(PrepareCoursePageService):
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        self.printer = MessagePrint()
        
    def execute(self):
        site_config = SiteConfig()
        site_config.get_config()      
        
        self.prepareCourseId =  self.params.getIntParam("prepareCourseId")
        self.prepareCourse = self.pc_svc.getPrepareCourse(self.prepareCourseId)
        
        if self.prepareCourse == None:
            self.printer.addMessage(u"未能加载备课。请重新选择一次备课。")
            return self.printer.printMessage("manage/course/showPrepareCourse.py?prepareCourseId=" + str(self.prepareCourseId),"")
        
        qry = PrepareCourseMemberQuery(""" u.userId, u.userIcon, u.loginName,u.trueName,u.nickName""")  
        qry.prepareCourseId = self.prepareCourse.prepareCourseId
        user_list = qry.query_map()
        prepareCourseEdit_list = self.pc_svc.getPrepareCourseEditList(self.prepareCourseId)
        request.setAttribute("prepareCourseEdit_list", prepareCourseEdit_list)
        request.setAttribute("user_list",user_list)
        if self.loginUser != None:
            request.setAttribute("loginUser", self.loginUser)  
        request.setAttribute("prepareCourse", self.prepareCourse)
        request.setAttribute("head_nav", "cocourses")
        return "/WEB-INF/ftl/course/showPrepareCourseCommonContent.ftl"