from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from site_config import SiteConfig
from cn.edustar.jitar.pojos import PrepareCourse
from java.text import SimpleDateFormat
from java.util import Date
from message_out import MessagePrint
from base_action import BaseAction
from preparecourse_member_query import PrepareCourseMemberQuery
from action_query import ActionQuery

class showPrepareCourse(BaseAction):
    def __init__(self):
        self.params = ParamUtil(request)
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        self.sbj_svc = __jitar__.getSubjectService()
        self.act_svc = __jitar__.getActionService()
        
        self.printer = MessagePrint()
        
    def execute(self):
        if self.loginUser == None:            
            self.printer.addMessage(u"请先<a href='../../login.jsp'>登录</a>，然后才能操作")
            return self.printer.printMessage("login.jsp", "")
        
        self.prepareCourseId = self.params.getIntParam("prepareCourseId")
        self.prepareCourse = self.pc_svc.getPrepareCourse(self.prepareCourseId)
        
        if self.prepareCourse == None:
            self.printer.addMessage(u"未能加载备课。请重新选择一次备课。")
            return self.printer.printMessage("cocourses.action", "")
        
        canVisitCourse = self.isCourseMember()
        if canVisitCourse == "false":
            self.printer.addMessage(u"只有该备课成员才能进行查看。")
            return self.printer.printMessage("cocourses.action", "")
        
        
        grade = self.sbj_svc.getGrade(self.prepareCourse.gradeId)
        metaSubject = self.sbj_svc.getMetaSubjectById(self.prepareCourse.metaSubjectId)
        
        request.setAttribute("canVisitCourse", canVisitCourse)
        request.setAttribute("grade", grade)
        request.setAttribute("metaSubject", metaSubject)
        request.setAttribute("loginUser", self.loginUser) 
        request.setAttribute("prepareCourse", self.prepareCourse)
        request.setAttribute("head_nav", "cocourses")
        request.setAttribute("canAdmin", self.canAdmin())
        self.getActionList()
        return self.getPrepareCourse()
    
    def getPrepareCourse(self):
        site_config = SiteConfig()
        site_config.get_config()
        
        qry = PrepareCourseMemberQuery(""" pcm.replyCount, u.userId, u.loginName,u.trueName,u.nickName,u.unitId, unit.unitName """)
        qry.prepareCourseId = self.prepareCourseId
        user_list = qry.query_map()
        request.setAttribute("user_list", user_list)
        
        precs_list = self.pc_svc.getPrepareCourseStageList(self.prepareCourseId)
        request.setAttribute("precoursestage_list", precs_list)        
        
        return "/WEB-INF/ftl/course/course_show.ftl" 
    
    def getActionList(self):
        qry = ActionQuery(""" act.title, act.createDate, act.actionId, act.ownerId, act.ownerType, act.createUserId, act.actionType,
                              act.description, act.userLimit, act.startDateTime,act.finishDateTime, act.attendLimitDateTime, act.place,
                              act.status, act.visibility, act.attendCount
                            """)
        qry.ownerType = "course"
        qry.status = 0
        qry.ownerId = self.prepareCourseId
        action_list = qry.query_map()
        request.setAttribute("action_list", action_list)      
    
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