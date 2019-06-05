from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from site_config import SiteConfig
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCourseStage
from java.text import SimpleDateFormat
from java.util import Date
from message_out import MessagePrint
from base_action import BaseAction
from preparecourse_member_query import PrepareCourseMemberQuery

class createPreCourse3(BaseAction):
    def __init__(self):
        self.params = ParamUtil(request)
        self.accessControlService = __spring__.getBean("accessControlService")
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        self.printer = MessagePrint()
        
    def execute(self):
        if self.loginUser == None:            
            self.printer.addMessage(u"请先<a href='../../login.jsp'>登录</a>，然后才能操作")
            return self.printer.printMessage("login.jsp","")
        
        self.prepareCourseId =  self.params.getIntParam("prepareCourseId")
        self.prepareCourse = self.pc_svc.getPrepareCourse(self.prepareCourseId)
        
        if self.prepareCourse == None:
            self.printer.addMessage(u"未能加载备课。请重新选择一次备课。")
            return self.printer.printMessage("cocourses.action","")
        
        if not(self.accessControlService.isSystemAdmin(self.loginUser) or self.loginUser.userId == self.prepareCourse.createUserId):
            self.printer.addMessage(u"只有管理员 和 备课创建者才能进行编辑。")
            return self.printer.printMessage("manage/course/showPrepareCourse.py?prepareCourseId=" + str(self.prepareCourseId),"")
        
        request.setAttribute("loginUser", self.loginUser) 
        request.setAttribute("prepareCourseId", self.prepareCourseId) 
        request.setAttribute("prepareCourse", self.prepareCourse)
        cmd = self.params.safeGetStringParam("cmd")
        
        if request.getMethod() == "POST":
            if cmd == 'add':
                return self.saveOrUpdatePrepareCourse()
            elif cmd == 'delete':
                return self.deletePrepareCourseStage()
            elif cmd == 'order':
                return self.orderPrepareCourseStage()
            elif cmd == 'edit':
                return self.editPrepareCourseStage() 
            else:
                self.printer.addMessage(u"无效的命令。")
                return self.printer.printMessage("cocourses.action","")
        else:
            return self.getPrepareCourse()
    
    
    def getPrepareCourse(self):
        site_config = SiteConfig()
        site_config.get_config()
        prepareCourseStageId = self.params.getIntParam("prepareCourseStageId")
        if prepareCourseStageId > 0:
            prepareCourseStage = self.pc_svc.getPrepareCourseStage(prepareCourseStageId)
            if prepareCourseStage != None:
                request.setAttribute("prepareCourseStage", prepareCourseStage)
                request.setAttribute("prepareCourseStageId", prepareCourseStageId)
            
        request.setAttribute("head_nav", "cocourses")
        precs_list = self.pc_svc.getPrepareCourseStageList(self.prepareCourseId)
        request.setAttribute("precoursestage_list", precs_list)
        
        return "/WEB-INF/ftl/course/course_create3.ftl" 
    
    def deletePrepareCourseStage(self):
        stageIdArray = request.getParameterValues("guid")
        for stageId in stageIdArray:            
            if stageId.isdigit():
                self.pc_svc.deletePrepareCourseStage(int(stageId))
        
        self.printer.addMessage(u"删除 " + self.prepareCourse.title + u" 备课阶段成功。")
        return self.printer.printMessage("manage/course/createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId),"")
        
    def editPrepareCourseStage(self):
        prepareCourseStageId = self.params.getIntParam("prepareCourseStageId")
        if prepareCourseStageId > 0:
            prepareCourseStage = self.pc_svc.getPrepareCourseStage(prepareCourseStageId)
            if prepareCourseStage == None:
                self.printer.addMessage(u"加载备课阶段失败。")
                return self.printer.printMessage("manage/course/createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId),"")
        else:
            self.printer.addMessage(u"无效的标识。")
            return self.printer.printMessage("manage/course/createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId),"")
    
        stageTitle = self.params.safeGetStringParam("stageTitle")
        stageStartDate = self.params.safeGetStringParam("stageStartDate")
        stageEndDate = self.params.safeGetStringParam("stageEndDate")
        stageDescription = self.params.getStringParam("stageDescription")
        stageOrderIndex = self.params.getIntParam("stageOrderIndex")
        if stageTitle == None or stageTitle == "":
            self.printer.addMessage(u"请输入一个阶段名。")
            return self.printer.printMessage("manage/course/createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId),"")
        
        if stageStartDate == None or stageStartDate == "":
            self.printer.addMessage(u"请输入一个开始日期。")
            return self.printer.printMessage("manage/course/createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId),"")
        
        if stageEndDate == None or stageEndDate == "":
            self.printer.addMessage(u"请输入一个结束日期。")
            return self.printer.printMessage("manage/course/createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId),"")
        
        pcStageStartDate = SimpleDateFormat("yyyy-M-d").parse(stageStartDate)
        pcEndDateTime = SimpleDateFormat("yyyy-M-d").parse(stageEndDate)

        prepareCourseStage.setTitle(stageTitle)
        prepareCourseStage.setBeginDate(pcStageStartDate)
        prepareCourseStage.setFinishDate(pcEndDateTime)
        prepareCourseStage.setDescription(stageDescription)
        prepareCourseStage.setOrderIndex(stageOrderIndex)
        
        self.pc_svc.updatePrepareCourseStage(prepareCourseStage)
    
        self.printer.addMessage(u"修改 " + prepareCourseStage.title + u" 阶段成功。")
        return self.printer.printMessage("manage/course/createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId),"")
    def saveOrUpdatePrepareCourse(self):
        stageTitle = self.params.safeGetStringParam("stageTitle")
        stageStartDate = self.params.safeGetStringParam("stageStartDate")
        stageEndDate = self.params.safeGetStringParam("stageEndDate")
        stageDescription = self.params.getStringParam("stageDescription")
        
        if stageTitle == None or stageTitle == "":
            self.printer.addMessage(u"请输入一个阶段名。")
            return self.printer.printMessage("manage/course/createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId),"")
        
        if stageStartDate == None or stageStartDate == "":
            self.printer.addMessage(u"请输入一个开始日期。")
            return self.printer.printMessage("manage/course/createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId),"")
        
        if stageEndDate == None or stageEndDate == "":
            self.printer.addMessage(u"请输入一个结束日期。")
            return self.printer.printMessage("manage/course/createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId),"")
        
        pcStageStartDate = SimpleDateFormat("yyyy-M-d").parse(stageStartDate)
        pcEndDateTime = SimpleDateFormat("yyyy-M-d").parse(stageEndDate)
        
        maxOrder = self.pc_svc.getMaxCourseStageOrderIndex(self.prepareCourseId)
        
        prepareCourseStage = PrepareCourseStage()
        prepareCourseStage.setPrepareCourseId(self.prepareCourseId)
        prepareCourseStage.setTitle(stageTitle)
        prepareCourseStage.setBeginDate(pcStageStartDate)
        prepareCourseStage.setFinishDate(pcEndDateTime)
        prepareCourseStage.setDescription(stageDescription)
        prepareCourseStage.setOrderIndex(maxOrder+1)
        self.pc_svc.createPrepareCourseStage(prepareCourseStage)
    
        self.printer.addMessage(u"创建 " + prepareCourseStage.title + u" 阶段成功。")
        return self.printer.printMessage("manage/course/createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId),"")
    
    def orderPrepareCourseStage(self):
        stageIdArray = request.getParameterValues("stageId")
        for stageId in stageIdArray:
            orderIndex = self.params.safeGetStringParam("orderIndex" + stageId)
            if orderIndex.isdigit() and stageId.isdigit():
                self.pc_svc.updatePrepareCourseStageIndexOrder(int(stageId),int(orderIndex))

        
        self.printer.addMessage(u"修改 " + self.prepareCourse.title + u" 阶段顺序成功。")
        return self.printer.printMessage("manage/course/createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId),"")    
        