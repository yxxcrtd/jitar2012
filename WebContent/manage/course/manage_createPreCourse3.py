from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil, DateUtil
from base_action import SubjectMixiner
from site_config import SiteConfig
from cn.edustar.jitar.pojos import PrepareCourse, PrepareCourseStage
from java.text import SimpleDateFormat
from java.util import Date
from message_out import ShowError
from base_action import BaseAction


class manage_createPreCourse3(BaseAction):
    def __init__(self):
        self.params = ParamUtil(request)
        self.accessControlService = __spring__.getBean("accessControlService")
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        self.printer = ShowError()
        
    def execute(self):
        if self.loginUser == None:
            errDesc = u"请先<a href='../../login.jsp'>登录</a>，然后才能操作"
            response.getWriter().write(errDesc)
            return
        
        self.prepareCourseId = self.params.getIntParam("prepareCourseId")
        self.prepareCourse = self.pc_svc.getPrepareCourse(self.prepareCourseId)
         
        if self.prepareCourse == None:
            errDesc = u"未能加载备课。请重新选择一次备课。"
            response.getWriter().write(errDesc)
            return
        
        if not(self.accessControlService.isSystemAdmin(self.loginUser) or self.loginUser.userId == self.prepareCourse.createUserId or self.loginUser.userId == self.prepareCourse.leaderId):
            errDesc = u"只有管理员 和 备课创建者、主备人才能进行编辑。"
            response.getWriter().write(errDesc)
            return
        
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
                errDesc = u"无效的命令。"
                response.getWriter().write(errDesc)
                return
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
        
        return "/WEB-INF/ftl/course/manage_course_create3.ftl" 
    
    def deletePrepareCourseStage(self):
        stageIdArray = request.getParameterValues("guid")
        if stageIdArray == None:return
        for stageId in stageIdArray:            
            if stageId.isdigit():
                self.pc_svc.deletePrepareCourseStage(int(stageId))
        
        errDesc = u"删除 " + self.prepareCourse.title + u" 备课流程成功。"                                                                                                                               
        response.getWriter().write(errDesc)
        return
        
    def editPrepareCourseStage(self):
        prepareCourseStageId = self.params.getIntParam("prepareCourseStageId")
        if prepareCourseStageId > 0:
            prepareCourseStage = self.pc_svc.getPrepareCourseStage(prepareCourseStageId)
            if prepareCourseStage == None:
                errDesc = u"加载备课流程失败。"                                                                                                                               
                response.getWriter().write(errDesc)
                return
        else:
            errDesc = u"无效的标识。"                                                                                                                               
            response.getWriter().write(errDesc)
            return
     
        stageTitle = self.params.safeGetStringParam("stageTitle")
        stageStartDate = self.params.safeGetStringParam("stageStartDate")
        stageEndDate = self.params.safeGetStringParam("stageEndDate")
        stageDescription = self.params.getStringParam("stageDescription")
        stageOrderIndex = self.params.getIntParam("stageOrderIndex")
        if stageTitle == None or stageTitle == "":
            errDesc = u"请输入一个流程名。<a href='#' onclick='window.history.back();return false;'>返回</a>"                                                                                                                               
            response.getWriter().write(errDesc)
            return
        
        if stageStartDate == None or stageStartDate == "":
            errDesc = u"请输入一个开始日期。<a href='#' onclick='window.history.back();return false;'>返回</a>"                                                                                                                               
            response.getWriter().write(errDesc)
            return
         
        if stageEndDate == None or stageEndDate == "":
            errDesc = u"请输入一个结束日期。<a href='#' onclick='window.history.back();return false;'>返回</a>"                                                                                                                               
            response.getWriter().write(errDesc)
            return
        stageStartDate = stageStartDate.replace("—", "-")
        stageEndDate = stageEndDate.replace("—", "-")
        pcStageStartDate = SimpleDateFormat("yyyy-MM-dd").parse(stageStartDate)
        pcEndDate = SimpleDateFormat("yyyy-MM-dd").parse(stageEndDate)
        if DateUtil.compareDateTime(pcStageStartDate, pcEndDate) > 0 :
            errDesc = u"结束时间不能早于开始时间。<a href='#' onclick='window.history.back();return false;'>返回</a>"                                                                                                                               
            response.getWriter().write(errDesc)
            return        
        
        prepareCourseStage.setTitle(stageTitle)
        prepareCourseStage.setBeginDate(pcStageStartDate)
        prepareCourseStage.setFinishDate(pcEndDate)
        prepareCourseStage.setDescription(stageDescription)
        prepareCourseStage.setOrderIndex(stageOrderIndex)
        
        self.pc_svc.updatePrepareCourseStage(prepareCourseStage)
        errDesc = u"修改 " + prepareCourseStage.title + u" 流程成功。"                                                                                                                               
        response.getWriter().write(errDesc)
        return
    
    def saveOrUpdatePrepareCourse(self):
        stageTitle = self.params.safeGetStringParam("stageTitle")
        stageStartDate = self.params.safeGetStringParam("stageStartDate")
        stageEndDate = self.params.safeGetStringParam("stageEndDate")
        stageDescription = self.params.getStringParam("stageDescription")
        
        if stageTitle == None or stageTitle == "":
            errDesc = u"请输入一个流程名。"                                                                                                                               
            response.getWriter().write(errDesc)           
            return
        
        if stageStartDate == None or stageStartDate == "":
            errDesc = u"请输入一个开始日期。"                                                                                                                               
            response.getWriter().write(errDesc)           
            return
        
        if stageEndDate == None or stageEndDate == "":
            errDesc = u"请输入一个结束日期。"                                                                                                                               
            response.getWriter().write(errDesc)           
            return
        
        stageStartDate = stageStartDate.replace("—", "-")
        stageEndDate = stageEndDate.replace("—", "-")
        pcStageStartDate = SimpleDateFormat("yyyy-MM-dd").parse(stageStartDate)
        pcEndDate = SimpleDateFormat("yyyy-MM-dd").parse(stageEndDate)
        if DateUtil.compareDateTime(pcStageStartDate, pcEndDate) > 0 :
            errDesc = u"结束时间不能早于开始时间。<a href='#' onclick='window.history.back();return false;'>返回</a>"                                                                                                                               
            response.getWriter().write(errDesc)
            return
        maxOrder = self.pc_svc.getMaxCourseStageOrderIndex(self.prepareCourseId)
        
        prepareCourseStage = PrepareCourseStage()
        prepareCourseStage.setPrepareCourseId(self.prepareCourseId)
        prepareCourseStage.setTitle(stageTitle)
        prepareCourseStage.setBeginDate(pcStageStartDate)
        prepareCourseStage.setFinishDate(pcEndDate)
        prepareCourseStage.setDescription(stageDescription)
        prepareCourseStage.setOrderIndex(maxOrder + 1)
        self.pc_svc.createPrepareCourseStage(prepareCourseStage)
    
        errDesc = u"创建 " + prepareCourseStage.title + u" 流程成功。"                                                                                                                               
        response.getWriter().write(errDesc)           
        return
     
    def orderPrepareCourseStage(self):
        stageIdArray = request.getParameterValues("stageId")
        for stageId in stageIdArray:
            orderIndex = self.params.safeGetStringParam("orderIndex" + stageId)
            if orderIndex.isdigit() and stageId.isdigit():
                self.pc_svc.updatePrepareCourseStageIndexOrder(int(stageId), int(orderIndex))

        errDesc = u"修改 " + self.prepareCourse.title + u" 流程顺序成功。"                                                                                                                               
        response.getWriter().write(errDesc)
        return
