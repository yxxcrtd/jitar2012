from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from site_config import SiteConfig
from cn.edustar.jitar.pojos import PrepareCourse,PrepareCourseStage
from java.text import SimpleDateFormat
from java.util import Date
from message_out import ShowError
from base_action import BaseAction
from base_preparecourse_page import *

class createPreCourse3(BaseAction):
    def __init__(self):
        self.params = ParamUtil(request)
        self.accessControlService = __spring__.getBean("accessControlService")
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        self.printer = ShowError()
        
    def execute(self):
        if self.loginUser == None:
            request.setAttribute("msg",u"请先<a href='../../login.jsp'>登录</a>，然后才能操作")
            return self.printer.printError()
        
        self.prepareCourseId =  self.params.getIntParam("prepareCourseId")
        self.prepareCourse = self.pc_svc.getPrepareCourse(self.prepareCourseId)
        
        if self.prepareCourse == None:
            errDesc = u"未能加载备课。请重新选择一次备课。<br /><br /><a href='cocourses.action'>返回</a>"
            self.printer.msg = errDesc            
            return self.printer.printError()
        
        if not(self.accessControlService.isSystemAdmin(self.loginUser) or self.loginUser.userId == self.prepareCourse.createUserId or self.loginUser.userId == self.prepareCourse.leaderId):
            errDesc = u"只有管理员 和 备课创建者、主备人才能进行编辑。。<br /><br /><a href='../../p/" + str(self.prepareCourseId) + "/0/'>返回</a>"
            self.printer.msg = errDesc            
            return self.printer.printError()
        
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
                errDesc = u"无效的命令。<br /><br /><a href='cocourses.action'>返回</a>"
                self.printer.msg = errDesc            
                return self.printer.printError()
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
        
        errDesc = u"删除 " + self.prepareCourse.title + u" 备课流程成功。<br /><br /><a href='createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId) + "'>返回</a>"                                                                                                                               
        self.printer.msg = errDesc            
        return self.printer.printError()
        
    def editPrepareCourseStage(self):
        prepareCourseStageId = self.params.getIntParam("prepareCourseStageId")
        if prepareCourseStageId > 0:
            prepareCourseStage = self.pc_svc.getPrepareCourseStage(prepareCourseStageId)
            if prepareCourseStage == None:
                errDesc = u"加载备课流程失败。<br /><br /><a href='createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId) + "'>返回</a>"                                                                                                                               
                self.printer.msg = errDesc            
                return self.printer.printError()
        else:
            errDesc = u"无效的标识。<br /><br /><a href='createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId) + "'>返回</a>"                                                                                                                               
            self.printer.msg = errDesc            
            return self.printer.printError()
     
        stageTitle = self.params.safeGetStringParam("stageTitle")
        stageStartDate = self.params.safeGetStringParam("stageStartDate")
        stageEndDate = self.params.safeGetStringParam("stageEndDate")
        stageDescription = self.params.getStringParam("stageDescription")
        stageOrderIndex = self.params.getIntParam("stageOrderIndex")
        if stageTitle == None or stageTitle == "":
            errDesc = u"请输入一个流程名。<br /><br /><a href='createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId) + "'>返回</a>"                                                                                                                               
            self.printer.msg = errDesc            
            return self.printer.printError()
        if stageStartDate == None or stageStartDate == "":
            errDesc = u"请输入一个开始日期。<br /><br /><a href='createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId) + "'>返回</a>"                                                                                                                               
            self.printer.msg = errDesc            
            return self.printer.printError()
         
        if stageEndDate == None or stageEndDate == "":
            errDesc = u"请输入一个结束日期。<br /><br /><a href='createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId) + "'>返回</a>"                                                                                                                               
            self.printer.msg = errDesc            
            return self.printer.printError()
        
        pcStageStartDate = SimpleDateFormat("yyyy-MM-dd").parse(stageStartDate)
        pcEndDateTime = SimpleDateFormat("yyyy-MM-dd").parse(stageEndDate)

        prepareCourseStage.setTitle(stageTitle)
        prepareCourseStage.setBeginDate(pcStageStartDate)
        prepareCourseStage.setFinishDate(pcEndDateTime)
        prepareCourseStage.setDescription(stageDescription)
        prepareCourseStage.setOrderIndex(stageOrderIndex)
        
        self.pc_svc.updatePrepareCourseStage(prepareCourseStage)
        errDesc = u"修改 " + prepareCourseStage.title + u" 流程成功。<br /><br /><a href='createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId) + "'>返回</a>"                                                                                                                               
        self.printer.msg = errDesc 
        return self.printer.printError()
    
    def saveOrUpdatePrepareCourse(self):
        stageTitle = self.params.safeGetStringParam("stageTitle")
        stageStartDate = self.params.safeGetStringParam("stageStartDate")
        stageEndDate = self.params.safeGetStringParam("stageEndDate")
        stageDescription = self.params.getStringParam("stageDescription")
        
        if stageTitle == None or stageTitle == "":
            errDesc = u"请输入一个阶段名。<br /><br /><a href='createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId) + "'>返回</a>"                                                                                                                               
            self.printer.msg = errDesc            
            return self.printer.printError()
        
        if stageStartDate == None or stageStartDate == "":
            errDesc = u"请输入一个开始日期。。<br /><br /><a href='createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId) + "'>返回</a>"                                                                                                                               
            self.printer.msg = errDesc            
            return self.printer.printError()
        
        if stageEndDate == None or stageEndDate == "":
            errDesc = u"请输入一个结束日期。<br /><br /><a href='createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId) + "'>返回</a>"                                                                                                                               
            self.printer.msg = errDesc            
            return self.printer.printError()
        
        pcStageStartDate = SimpleDateFormat("yyyy-MM-dd").parse(stageStartDate)
        pcEndDateTime = SimpleDateFormat("yyyy-MM-dd").parse(stageEndDate)
        
        maxOrder = self.pc_svc.getMaxCourseStageOrderIndex(self.prepareCourseId)
        
        prepareCourseStage = PrepareCourseStage()
        prepareCourseStage.setPrepareCourseId(self.prepareCourseId)
        prepareCourseStage.setTitle(stageTitle)
        prepareCourseStage.setBeginDate(pcStageStartDate)
        prepareCourseStage.setFinishDate(pcEndDateTime)
        prepareCourseStage.setDescription(stageDescription)
        prepareCourseStage.setOrderIndex(maxOrder+1)
        self.pc_svc.createPrepareCourseStage(prepareCourseStage)
    
        errDesc = u"创建 " + prepareCourseStage.title + u" 流程成功。<br /><br /><a href='createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId) + "'>返回</a>"                                                                                                                               
        self.printer.msg = errDesc            
        return self.printer.printError()
     
    def orderPrepareCourseStage(self):
        stageIdArray = request.getParameterValues("stageId")
        for stageId in stageIdArray:
            orderIndex = self.params.safeGetStringParam("orderIndex" + stageId)
            if orderIndex.isdigit() and stageId.isdigit():
                self.pc_svc.updatePrepareCourseStageIndexOrder(int(stageId),int(orderIndex))

        errDesc = u"修改 " + self.prepareCourse.title + u" 流程顺序成功。<br /><br /><a href='createPreCourse3.py?prepareCourseId=" + str(self.prepareCourseId) + "'>返回</a>"                                                                                                                               
        self.printer.msg = errDesc            
        return self.printer.printError()