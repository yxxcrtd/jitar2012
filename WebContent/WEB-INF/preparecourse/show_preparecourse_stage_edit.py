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
from message_out import ShowError

class show_preparecourse_stage_edit(PrepareCoursePageService):
    def __init__(self):
        self.printer = ShowError()   
        self.prepareCourseEdit = None
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        self.sub_svc = __jitar__.subjectService
        
    def execute(self):
        self.getBaseData()
        
        response.setContentType("text/html; charset=UTF-8")
        if self.loginUser == None:
            actionErrors = [u"请先登录。<a href='../../../../login.jsp'>登录页面</a>。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        if self.prepareCourseId == 0:
            actionErrors = [u"无效的课程标识。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        self.prepareCourse = self.getBasePrepareCourse()
        if self.prepareCourse == None:
            actionErrors = [u"没有加载到所请求的备课。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        if self.canManage(self.prepareCourse) == False:
            actionErrors = [u"您无权管理本备课。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
       
        page = self.getPrepareCoursePageWithCustomSkin(self.prepareCourse)
        widgets = [                  
                   {"id": "placerholder1", "pageId":0, "columnIndex":2,"title":"","module":"placeholder", "ico":"", "data":""}
                  ]
        
        request.setAttribute("widget_list",widgets)
        request.setAttribute("page", page)
        request.setAttribute("prepareCourse",self.prepareCourse)
        request.setAttribute("prepareCourseId",self.prepareCourseId)
        
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
            return self.show_stage_edit()
        
        
    def show_stage_edit(self):
        if self.prepareCourseStageId > 0:
            prepareCourseStage = self.pc_svc.getPrepareCourseStage(self.prepareCourseStageId)
            if prepareCourseStage != None:
                request.setAttribute("prepareCourseStage", prepareCourseStage)
                request.setAttribute("prepareCourseStageId", self.prepareCourseStageId)
                
        precs_list = self.pc_svc.getPrepareCourseStageList(self.prepareCourseId)
        request.setAttribute("precoursestage_list", precs_list)
        return "/WEB-INF/ftl/course/show_preparecourse_stage_edit.ftl"
    
    def deletePrepareCourseStage(self):
        stageIdArray = request.getParameterValues("guid")
        for stageId in stageIdArray:            
            if stageId.isdigit():
                self.pc_svc.deletePrepareCourseStage(int(stageId))
        
        operation_result = u"删除 " + self.prepareCourse.title + " 备课流程成功。"                                                                                                                               
        request.setAttribute("operation_result", operation_result)          
        return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
    def editPrepareCourseStage(self):
        prepareCourseStageId = self.params.getIntParam("prepareCourseStageId")
        if prepareCourseStageId > 0:
            prepareCourseStage = self.pc_svc.getPrepareCourseStage(prepareCourseStageId)
            if prepareCourseStage == None:
                operation_result = u"加载备课流程失败。"                                                                                                                               
                request.setAttribute("operation_result", operation_result)          
                return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        else:
            operation_result = u"无效的标识。"                                                                                                                               
            request.setAttribute("operation_result", operation_result)          
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
     
        stageTitle = self.params.safeGetStringParam("stageTitle")
        stageStartDate = self.params.safeGetStringParam("stageStartDate")
        stageEndDate = self.params.safeGetStringParam("stageEndDate")
        stageDescription = self.params.getStringParam("stageDescription")
        stageOrderIndex = self.params.getIntParam("stageOrderIndex")
        if stageTitle == None or stageTitle == "":
            operation_result = u"请输入一个流程名。"                                                                                                                               
            request.setAttribute("operation_result", operation_result)          
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        if stageStartDate == None or stageStartDate == "":
            operation_result = u"请输入一个开始日期。"                                                                                                                               
            request.setAttribute("operation_result", operation_result)          
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
         
        if stageEndDate == None or stageEndDate == "":
            operation_result = u"请输入一个结束日期。"                                                                                                                               
            request.setAttribute("operation_result", operation_result)          
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
        pcStageStartDate = SimpleDateFormat("yyyy-MM-dd").parse(stageStartDate)
        pcEndDateTime = SimpleDateFormat("yyyy-MM-dd").parse(stageEndDate)

        prepareCourseStage.setTitle(stageTitle)
        prepareCourseStage.setBeginDate(pcStageStartDate)
        prepareCourseStage.setFinishDate(pcEndDateTime)
        prepareCourseStage.setDescription(stageDescription)
        prepareCourseStage.setOrderIndex(stageOrderIndex)
        
        self.pc_svc.updatePrepareCourseStage(prepareCourseStage)
        operation_result = u"修改 " + prepareCourseStage.title + " 流程成功。"                                                                                                                               
        request.setAttribute("operation_result", operation_result)          
        return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
    
    def saveOrUpdatePrepareCourse(self):
        stageTitle = self.params.safeGetStringParam("stageTitle")
        stageStartDate = self.params.safeGetStringParam("stageStartDate")
        stageEndDate = self.params.safeGetStringParam("stageEndDate")
        stageDescription = self.params.getStringParam("stageDescription")
        
        if stageTitle == None or stageTitle == "":
            operation_result = u"请输入一个流程名。"                                                                                                                               
            request.setAttribute("operation_result", operation_result)          
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
        if stageStartDate == None or stageStartDate == "":
            operation_result = u"请输入一个开始日期。"                                                                                                                               
            request.setAttribute("operation_result", operation_result)          
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
        if stageEndDate == None or stageEndDate == "":
            operation_result = u"请输入一个结束日期。"                                                                                                                               
            request.setAttribute("operation_result", operation_result)          
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
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
    
        operation_result = u"创建 " + prepareCourseStage.title + " 流程成功。"                                                                                                                               
        request.setAttribute("operation_result", operation_result)          
        return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
     
    def orderPrepareCourseStage(self):
        stageIdArray = request.getParameterValues("stageId")
        for stageId in stageIdArray:
            orderIndex = self.params.safeGetStringParam("orderIndex" + stageId)
            if orderIndex.isdigit() and stageId.isdigit():
                self.pc_svc.updatePrepareCourseStageIndexOrder(int(stageId),int(orderIndex))

        operation_result = u"修改 " + self.prepareCourse.title + " 流程顺序成功。"                                                                                                                               
        request.setAttribute("operation_result", operation_result)          
        return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"