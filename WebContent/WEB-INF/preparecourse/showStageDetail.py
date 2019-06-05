from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from site_config import SiteConfig
from cn.edustar.jitar.pojos import PrepareCourse, PrepareCourseStage
from java.text import SimpleDateFormat
from java.util import Date
from message_out import MessagePrint
from base_action import BaseAction
from preparecourse_member_query import PrepareCourseMemberQuery


class showStageDetail(BaseAction):
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        self.printer = MessagePrint()
        
    def execute(self):
        if self.loginUser == None:            
            self.printer.addMessage(u"请先<a href='../../login.jsp'>登录</a>，然后才能操作")
            return self.printer.printMessage("login.jsp", "")
        
        self.prepareCourseId = self.params.getIntParam("prepareCourseId")
        self.prepareCourseStageId = self.params.getIntParam("stageId")
        
        self.prepareCourse = self.pc_svc.getPrepareCourse(self.prepareCourseId)
        self.prepareCourseStage = self.pc_svc.getPrepareCourseStage(self.prepareCourseStageId)
        
        if self.prepareCourse == None:
            self.printer.addMessage(u"未能加载备课。请重新选择一次备课。")
            return self.printer.printMessage("manage/course/showPrepareCourse.py?prepareCourseId=" + str(self.prepareCourseId), "")
        
        if self.prepareCourseStage == None:
            self.printer.addMessage(u"未能加载备课的当前阶段。")
            return self.printer.printMessage("manage/course/showPrepareCourse.py?prepareCourseId=" + str(self.prepareCourseId), "")
        
        request.setAttribute("loginUser", self.loginUser) 
        request.setAttribute("prepareCourseId", self.prepareCourseId) 
        request.setAttribute("prepareCourseStageId", self.prepareCourseStageId)
        
        if request.getMethod() == "POST":
            return self.saveOrUpdatePrepareCourse()
        else:
            return self.getPrepareCourse()
        
    def getPrepareCourse(self):
 
        qry = PrepareCourseMemberQuery("""  u.loginName,u.trueName,u.nickName,u.unitId, unit.unitName """)
        qry.prepareCourseId = self.prepareCourseId
        user_list = qry.query_map()
        request.setAttribute("user_list", user_list)        
        request.setAttribute("head_nav", "cocourses")
        return "/WEB-INF/ftl/course/course_stage_detail_show.ftl" 
    
    def saveOrUpdatePrepareCourse(self):
        stageComment = self.params.safeGetStringParam("stageComment")
        
        if stageComment == None or stageComment == "":
            self.printer.addMessage(u"请输入讨论内容。")
            return self.printer.printMessage("manage/course/createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId), "")   
        
        pcStageStartDate = SimpleDateFormat("yyyy-M-d").parse(stageStartDate)
        pcEndDateTime = SimpleDateFormat("yyyy-M-d").parse(stageEndDate)
        
        maxOrder = self.pc_svc.getMaxCourseStageOrderIndex(self.prepareCourseId)
        
        prepareCourseStage = PrepareCourseStage()
        prepareCourseStage.setPrepareCourseId(self.prepareCourseId)
        prepareCourseStage.setTitle(stageTitle)
        prepareCourseStage.setBeginDate(pcStageStartDate)
        prepareCourseStage.setFinishDate(pcEndDateTime)
        prepareCourseStage.setDescription(stageDescription)
        prepareCourseStage.setOrderIndex(maxOrder + 1)
        self.pc_svc.createPrepareCourseStage(prepareCourseStage)
    
        self.printer.addMessage(u"创建 " + prepareCourseStage.title + u" 阶段成功。")
        return self.printer.printMessage("manage/course/createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId), "")