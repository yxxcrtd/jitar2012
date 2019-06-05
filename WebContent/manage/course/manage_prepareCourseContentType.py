from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse, PrepareCourseStage, PrepareCourseTopic, PrepareCourseTopicReply, Group, GroupMember
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from message_out import ShowError
from action_query import ActionQuery

from base_preparecourse_page import *

class manage_prepareCourseContentType(SubjectMixiner, BaseAction):
    def __init__(self):
        self.params = ParamUtil(request)
        self.accessControlService = __spring__.getBean("accessControlService")
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        self.group_svc = __jitar__.getGroupService()
        self.prepareCourse = None
        self.group = None
        
    def execute(self):
        if self.loginUser == None:
            self.addActionError(u"请先<a href='../../login.jsp'>登录</a>，然后才能进行操作。")
            return self.ERROR
        
        self.prepareCourseId = self.params.safeGetIntParam("prepareCourseId")
        self.prepareCourse = self.pc_svc.getPrepareCourse(self.prepareCourseId)    
             
        if self.prepareCourse == None:
            self.addActionError(u"集被不存在。")
            return self.ERROR
        
        self.group = self.pc_svc.getGroupOfPrepareCourse(self.prepareCourseId)
        if self.group == None:
            self.addActionError(u"无法加载协作组信息。")
            return self.ERROR
        
        # 判断当前用户是否是组内成员
        accessControlService = __spring__.getBean("accessControlService")
        gm = self.group_svc.getGroupMemberByGroupIdAndUserId(self.group.groupId, self.loginUser.userId)
        if gm == None:
            if accessControlService.isSystemAdmin(self.loginUser) == False:
                self.addActionError(u"你不是本组的成员。")
                return self.ERROR
            
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            if gm.getStatus() != GroupMember.STATUS_NORMAL:
                self.addActionError(u"你目前的成员状态不正常，无法继续操作。")
                return self.ERROR
        
        print "===", self.loginUser
        
        if not(self.accessControlService.isSystemAdmin(self.loginUser) or gm.groupRole >= GroupMember.GROUP_ROLE_VICE_MANAGER or self.loginUser.userId == self.prepareCourse.createUserId or self.loginUser.userId == self.prepareCourse.leaderId):
            self.addActionError(u"权限不足，无法继续操作。只有 admin、协作组组长、副组长、集备创建者、主备人可以进行修改。")
            return self.ERROR
        
        
            
        if request.getMethod() == "POST":
            return self.saveOrUpdatePrepareCourse()
        else:
            request.setAttribute("loginUser", self.loginUser)
            request.setAttribute("prepareCourse", self.prepareCourse)
            return "/WEB-INF/ftl/course/manage_prepareCourseContentType.ftl"        
    
    def saveOrUpdatePrepareCourse(self):
        contentType = self.params.safeGetIntParam("contentType")
        if contentType != 1 and contentType != 2 and contentType != 3 and contentType != 4 and contentType != 100:
            self.addActionError(u"请选择正确的类型。")
            return self.ERROR
        self.prepareCourse.setContentType(contentType)
        self.pc_svc.updatePrepareCourse(self.prepareCourse)
        self.addActionMessage(u"保存成功。")
        return self.SUCCESS