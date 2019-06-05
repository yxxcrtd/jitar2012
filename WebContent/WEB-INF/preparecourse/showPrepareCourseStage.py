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


class showPrepareCourseStage(BaseAction):
    def __init__(self):
        self.params = ParamUtil(request)
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        self.printer = MessagePrint()
        
    def execute(self):
        if self.loginUser == None:            
            self.printer.addMessage(u"请先<a href='../../login.jsp'>登录</a>，然后才能操作")
            return self.printer.printMessage("login.jsp","")
        site_config = SiteConfig()
        site_config.get_config()
        

        self.prepareCourseId =  self.params.getIntParam("prepareCourseId")
        self.prepareCourseStageId =  self.params.getIntParam("stageId")
        self.prepareCourse = self.pc_svc.getPrepareCourse(self.prepareCourseId)
        self.prepareCourseStage = self.pc_svc.getPrepareCourseStage(self.prepareCourseStageId)
        if self.prepareCourse == None:
            self.printer.addMessage(u"未能加载备课。请重新选择一次备课。")
            return self.printer.printMessage("cocourses.action","")
        
        if self.prepareCourseStage == None:
            self.printer.addMessage(u"未能加载备课阶段，请选择一个阶段。")
            return self.printer.printMessage("cocourses.action","")
        
        if self.isCourseMember() == "false":
            self.printer.addMessage(u"只有管理员和备课组成员才可以查看。")
            return self.printer.printMessage("manage/course/showPrepareCourse.py?prepareCourseId=" + str(self.prepareCourseId),"")
        
        if request.getMethod() == "POST":
            self.saveOrUpdate()
            
        request.setAttribute("loginUser", self.loginUser) 
        request.setAttribute("prepareCourse", self.prepareCourse)
        request.setAttribute("prepareCourseStage", self.prepareCourseStage)
        request.setAttribute("head_nav", "cocourses")
        request.setAttribute("prepareCourseStageId", self.prepareCourseStageId)
        request.setAttribute("prepareCourseId", self.prepareCourseId) 
        
        precs_list = self.pc_svc.getPrepareCourseStageList(self.prepareCourseId)        
        pcs_rpl_list = self.pc_svc.getPrepareCourseStageReplyList(self.prepareCourseStageId)
        #print "prepareCourseStageId",self.prepareCourseStageId
        #print "pcs_rpl_list",pcs_rpl_list
        request.setAttribute("precoursestage_list", precs_list)
        request.setAttribute("pcs_rpl_list", pcs_rpl_list)             
    
        return "/WEB-INF/ftl/course/course_stage_show.ftl" 
        
        
    def saveOrUpdate(self):
        # 判断是否是当前的备课，判断是否是成员
        
        stagePostTitle = self.params.safeGetStringParam("stagePostTitle")
        stagePostComment = self.params.safeGetStringParam("stagePostComment")
        if stagePostTitle == None or stagePostTitle == "":
            self.printer.addMessage(u"请输入讨论标题。")
            return self.printer.printMessage("manage/course/showPrepareCourseStage.py?stageId=" + str(self.prepareCourseStageId) + "&prepareCourseId=" + str(self.prepareCourseId),"")
        
        if stagePostComment == None or stagePostComment == "":
            self.printer.addMessage(u"请输入讨论内容。")
            return self.printer.printMessage("manage/course/showPrepareCourseStage.py?stageId=" + str(self.prepareCourseStageId) + "&prepareCourseId=" + str(self.prepareCourseId),"")
                
        prepareCourseStageReply = PrepareCourseStageReply()        
        prepareCourseStageReply.setPrepareCourseStageId(self.prepareCourseStageId)
        prepareCourseStageReply.setPrepareCourseId(self.prepareCourseId)
        prepareCourseStageReply.setTitle(stagePostTitle)
        prepareCourseStageReply.setUserId(self.loginUser.userId)
        prepareCourseStageReply.setCreateDate(Date())
        prepareCourseStageReply.setContent(stagePostComment)
        self.pc_svc.createPrepareCourseStageReply(prepareCourseStageReply)
        self.pc_svc.updateReplyCount(self.prepareCourseId,self.loginUser.userId)
        #return "/WEB-INF/ftl/course/course_stage_show.ftl" 
    
    # 当前登录用户是否是管理员
    def canAdmin(self):
        accessControlService = __spring__.getBean("accessControlService")
        if self.loginUser.userId == self.prepareCourse.createUserId or accessControlService.isSystemAdmin(self.loginUser):
            return "true"
        else:
            return "false"

    # 当前登录用户是否是该备课的成员
    def isCourseMember(self):
        ismember = "false"
        if self.loginUser == None:
            return ismember
        if self.canAdmin() == "true":
            return "true"
        ismember = self.pc_svc.checkUserInPreCourse(self.prepareCourse.prepareCourseId, self.loginUser.userId)
        return ismember