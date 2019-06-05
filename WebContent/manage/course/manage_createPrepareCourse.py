from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil, CommonUtil
from cn.edustar.jitar.pojos import PrepareCourse, PrepareCourseStage, PrepareCourseTopic, PrepareCourseTopicReply, Group, GroupMember
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from message_out import ShowError
from action_query import ActionQuery

from base_preparecourse_page import *

class manage_createPrepareCourse(SubjectMixiner, BaseAction,ActionResult):
    def __init__(self):
        self.params = ParamUtil(request)
        self.accessControlService = __spring__.getBean("accessControlService")
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        self.group_svc = __jitar__.getGroupService()
        self.printer = ShowError()
        self.prepareCourse = None
        self.group = None
        
    def execute(self):
        if self.loginUser == None:
            backUrl = request.getAttribute('javax.servlet.forward.request_uri')
            if backUrl == None or backUrl == "":backUrl = request.requestURI    
            response.sendRedirect(CommonUtil.getSiteUrl(request) + "login.jsp?redUrl=" + CommonUtil.urlUtf8Encode(CommonUtil.getSiteServer(request) + backUrl))
            return
     
        """
        ********************** 注意：本文件只提供编辑 ****************************************        
        """        
        self.prepareCourseId = self.params.safeGetIntParam("prepareCourseId")
        self.group = self.pc_svc.getGroupOfPrepareCourse(self.prepareCourseId)
        if self.group == None:
            actionErrors = [u"无法加载协作组信息。"]
            request.setAttribute("actionErrors", actionErrors)
            return self.ERROR
        
        # 判断当前用户是否是组内成员
        gm = self.group_svc.getGroupMemberByGroupIdAndUserId(self.group.groupId, self.loginUser.userId)
        accessControlService = __spring__.getBean("accessControlService")
        if gm == None:
            if accessControlService.isSystemAdmin(self.loginUser) == False:
                actionErrors = [u"做人要厚道，你不是本组的成员。"]
                request.setAttribute("actionErrors", actionErrors)
                return self.ERROR   
        if self.prepareCourseId > 0:
            self.prepareCourse = self.pc_svc.getPrepareCourse(self.prepareCourseId)
            if self.prepareCourse != None:
                if self.prepareCourse.createUserId != self.loginUser.userId and self.prepareCourse.leaderId != self.loginUser.userId:
                    actionErrors = [u"你没有权限继续操作。"]
                    request.setAttribute("actionErrors", actionErrors)
                    return self.ERROR
        else:                 
            if accessControlService.isSystemAdmin(self.loginUser) == False:
                if gm.getStatus() != GroupMember.STATUS_NORMAL:
                    actionErrors = [u"你目前的成员状态不正常，无法继续操作。"]
                    request.setAttribute("actionErrors", actionErrors)
                    return self.ERROR
                
            if accessControlService.isSystemAdmin(self.loginUser) == False:
                if gm.groupRole < GroupMember.GROUP_ROLE_VICE_MANAGER:
                    actionErrors = [u"权限不足，无法继续操作。"]
                    request.setAttribute("actionErrors", actionErrors)
                    return self.ERROR        
        
        request.setAttribute("loginUser", self.loginUser)
        
        if self.prepareCourseId > 0:
            self.prepareCourse = self.pc_svc.getPrepareCourse(self.prepareCourseId)
            if self.prepareCourse != None:
                request.setAttribute("prepareCourse", self.prepareCourse)                
                request.setAttribute("group", self.group)
        else:
            self.printer.msg = u"此处不能创建新备课、"
            return self.printer.printError()
            
        if request.getMethod() == "POST":
            return self.saveOrUpdatePrepareCourse()
        else:
            return self.getPrepareCourse()        
    
    def getPrepareCourse(self):
       
        self.get_subject_list()
        self.get_grade_list()
        
        request.setAttribute("head_nav", "cocourses")
        return "/WEB-INF/ftl/course/manage_createPrepareCourse.ftl"
    
    def saveOrUpdatePrepareCourse(self):
        if self.prepareCourse == None:
            self.prepareCourse = PrepareCourse()
        else:
            # 只有admin 和 主备人进行修改
            if not(self.accessControlService.isSystemAdmin(self.loginUser) or self.loginUser.userId == self.prepareCourse.createUserId or self.loginUser.userId == self.prepareCourse.leaderId):
                self.printer.msg = u"只有 admin 或者主备人才能进行修改。<br/><br/><a href='createPreCourse.py'>返回</a>"
                return self.printer.printError()            
        
        pcTitle = self.params.safeGetStringParam("pcTitle")
        pcStartDate = self.params.safeGetStringParam("pcStartDate")
        pcEndDate = self.params.safeGetStringParam("pcEndDate")
        #pcGradeId = self.params.getIntParamZeroAsNull("pcGrade")
        #pcMetaSubjectId = self.params.getIntParamZeroAsNull("pcMetaSubject")
        pcDescription = self.params.safeGetStringParam("pcDescription")
        pcLeader = self.params.safeGetIntParam("pcLeader")     
        pcTags = self.params.safeGetStringParam("pcTags")
        contentType = self.params.safeGetIntParam("contentType")
        
        if pcLeader == "":
            user_leader = self.loginUser
        else:       
            user_leader = self.user_svc.getUserById(pcLeader)
            if user_leader == None:
                self.printer.msg = u"该用户不存在。<br/><br/><a href='manage_createPrepareCourse.py'>返回</a>"
                return self.printer.printError()
        pcStartDateTime = None
        if pcStartDate != None and pcStartDate != '':
            pcStartDateTime = SimpleDateFormat("yyyy-MM-dd").parse(pcStartDate)
        pcEndDateTime = SimpleDateFormat("yyyy-MM-dd").parse(pcEndDate)
        self.prepareCourse.setTitle(pcTitle)
        if pcStartDateTime != None:
            self.prepareCourse.setStartDate(pcStartDateTime)
        self.prepareCourse.setEndDate(pcEndDateTime)
        self.prepareCourse.setDescription(pcDescription)
        #self.prepareCourse.setMetaSubjectId(int(pcMetaSubjectId))
        #self.prepareCourse.setGradeId(int(pcGradeId))
        self.prepareCourse.setCreateUserId(self.loginUser.userId)
        self.prepareCourse.setCreateDate(Date())
        self.prepareCourse.setLeaderId(user_leader.userId)
        self.prepareCourse.setTags(pcTags)
        if self.prepareCourse.contentType == 0:
            self.prepareCourse.setContentType(contentType)
            
        if self.prepareCourse.prepareCourseId == None:
            self.prepareCourse.setLockedDate(Date())
            self.prepareCourse.setLockedUserId(0)
            self.prepareCourse.setPrepareCourseEditId(0)            
            self.prepareCourse.setContentType(contentType)
            self.pc_svc.createPrepareCourse(self.prepareCourse)
        else:            
            self.pc_svc.updatePrepareCourse(self.prepareCourse)
        return ActionResult.SUCCESS
        #下面暂时不用 
        if self.prepareCourseId > 0:
            response.getWriter().write(u"您的 <span style='color:#f00'>" + pcTitle + u"</span> 修改成功.<a href='manage_createPrepareCourse.py?prepareCourseId=" + str(self.prepareCourse.prepareCourseId) + u"'>返回</a>")
            return
        else:
            response.sendRedirect("manage_pc.py?prepareCourseId=" + str(self.prepareCourse.prepareCourseId))
            self.printer.msg = u"您的 <span style='color:#f00'>" + pcTitle + u"</span> 创建成功。请继续添加参与人员和创建备课的阶段过程。<br/><br/><a href='showPrepareCourse.py?prepareCourseId=" + str(self.prepareCourse.prepareCourseId) + u"'>返回</a>"
            return self.printer.printError()
        
    def get_subject_list(self):
         self.putSubjectList()

    def get_grade_list(self):
        request.setAttribute("gradeId", self.params.getIntParamZeroAsNull("gradeId"))
        self.putGradeList()
