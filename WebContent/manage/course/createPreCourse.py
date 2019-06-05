from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from site_config import SiteConfig
from cn.edustar.jitar.pojos import PrepareCourse
from java.text import SimpleDateFormat
from java.util import Date
from message_out import ShowError
from base_action import BaseAction
from cn.edustar.usermgr import BaseUser

class createPreCourse(SubjectMixiner, BaseAction):
    def __init__(self):
        self.params = ParamUtil(request)
        self.accessControlService = __spring__.getBean("accessControlService")
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        self.group_svc = __jitar__.getGroupService()
        self.printer = ShowError()
        self.groupId = 0
        self.group = None
        self.prepareCoursePlan = None
        
    def execute(self):

    
        if self.loginUser == None:
            self.printer.msg = u"请先<a href='../../login.jsp'>登录</a>，然后才能进行操作。"
            return self.printer.printError()
                
        baseUser = self.user_svc.getUserByUsername(self.loginUser.loginName)
        accessControlService = __spring__.getBean("accessControlService")
        if not(baseUser.role ==1 or baseUser.role == 3) and accessControlService.isSystemAdmin(self.loginUser) == False:
            self.printer.msg = u"只有管理员和老师身份才能创建备课。<br/><br/><a href='cocourses.action'>返回</a>"
            return self.printer.printError()
        
        self.prepareCourse = None
        self.prepareCourseId = self.params.getIntParam("prepareCourseId")
        request.setAttribute("loginUser", self.loginUser)
        
        if self.prepareCourseId > 0:
            self.prepareCourse = self.pc_svc.getPrepareCourse(self.prepareCourseId)
            # 检查是否是管理员
            if self.pc_svc.checkUserCanManagePreCourse(self.prepareCourseId,self.loginUser) == False:
                self.printer.msg = u"权限被拒绝。"
                return self.printer.printError()
                        
            if self.prepareCourse != None:
                request.setAttribute("prepareCourse", self.prepareCourse)
                g = self.pc_svc.getGroupOfPrepareCourse(self.prepareCourse.prepareCourseId)
                if g != None:
                    request.setAttribute("group", g)
            
        else:
            self.printer.msg = u"此处不能再创建集备了，信息不足。"
            return self.printer.printError()
    
            self.groupId = self.params.safeGetIntParam("groupId")
            self.group = self.group_svc.getGroup(self.groupId)            
            if self.group == None:
                self.printer.msg = u"现在创建集备，需要选择一个协作组。"
                return self.printer.printError()
            self.prepareCoursePlan = self.pc_svc.getDefaultPrepareCoursePlanOfGroup(self.group.groupId)
            if self.prepareCoursePlan == None:
                self.printer.msg = u"该协作组没有默认的备课计划，请到协作组里面进行创建。"
                return self.printer.printError()
            
        if request.getMethod() == "POST":
            return self.saveOrUpdatePrepareCourse()
        else:
            return self.getPrepareCourse()        
    
    def getPrepareCourse(self):
        site_config = SiteConfig()
        site_config.get_config()
        
        self.get_subject_list()
        self.get_grade_list()
        
        request.setAttribute("head_nav", "cocourses")
        
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/course/course_create.ftl"
    
    def saveOrUpdatePrepareCourse(self):
        if self.prepareCourse == None:
            self.prepareCourse = PrepareCourse()
            self.prepareCourse.setPrepareCoursePlanId(self.prepareCoursePlan.prepareCoursePlanId)
        else:
            # 只有admin 和 主备人进行修改
            if not(self.accessControlService.isSystemAdmin(self.loginUser) or self.loginUser.userId == self.prepareCourse.createUserId ):
                self.printer.msg = u"只有 admin 或者创建人、主备人才能进行修改。<br/><br/><a href='createPreCourse.py'>返回</a>"
                return self.printer.printError()
        
        pcTitle = self.params.safeGetStringParam("pcTitle")
        pcStartDate = self.params.safeGetStringParam("pcStartDate")
        pcEndDate = self.params.safeGetStringParam("pcEndDate")
        pcGradeId = self.params.getIntParamZeroAsNull("pcGrade")
        pcMetaSubjectId = self.params.getIntParamZeroAsNull("pcMetaSubject")
        pcDescription = self.params.safeGetStringParam("pcDescription")
        pcLeader = self.params.safeGetIntParam("pcLeader")     
        pcTags = self.params.safeGetStringParam("pcTags")
        
        if pcLeader == 0:
            user_leader = self.loginUser
        else:       
            user_leader = self.user_svc.getUserById(pcLeader)
            if user_leader == None:
                self.printer.msg = u"该用户不存在。<br/><br/><a href='createPreCourse.py'>返回</a>"
                return self.printer.printError()
        
        if pcGradeId == None or pcGradeId == 0:
            self.printer.msg = u"你必须选择一个学段。"
            return self.printer.printError()
        
        if pcMetaSubjectId == None or pcMetaSubjectId == 0:
            self.printer.msg = u"你必须选择一个学科。"
            return self.printer.printError()
        
        pcStartDateTime = SimpleDateFormat("yyyy-MM-dd").parse(pcStartDate)
        pcEndDateTime = SimpleDateFormat("yyyy-MM-dd").parse(pcEndDate)
        
        self.prepareCourse.setTitle(pcTitle)
        self.prepareCourse.setStartDate(pcStartDateTime)
        self.prepareCourse.setEndDate(pcEndDateTime)
        self.prepareCourse.setDescription(pcDescription)
        self.prepareCourse.setMetaSubjectId(int(pcMetaSubjectId))
        self.prepareCourse.setGradeId(int(pcGradeId))
        self.prepareCourse.setCreateUserId(self.loginUser.userId)
        self.prepareCourse.setCreateDate(Date())
        self.prepareCourse.setLockedDate(Date())
        self.prepareCourse.setLeaderId(user_leader.getUserId())
        self.prepareCourse.setLockedUserId(0)
        self.prepareCourse.setPrepareCourseEditId(0)
        self.prepareCourse.setTags(pcTags)        
        if self.prepareCourseId > 0:
            self.pc_svc.updatePrepareCourse(self.prepareCourse)
            self.printer.msg = u"您的 <span style='color:#f00'>" + pcTitle + u"</span> 修改成功。<br/><br/><a href='showPrepareCourse.py?prepareCourseId=" + str(self.prepareCourse.prepareCourseId) + "'>返回</a>"
            return self.printer.printError()
        else:
            self.pc_svc.createPrepareCourse(self.prepareCourse)
            self.printer.msg = u"您的 <span style='color:#f00'>" + pcTitle + u"</span> 创建成功。<br/><br/><a href='showPrepareCourse.py?prepareCourseId=" + str(self.prepareCourse.prepareCourseId) + "'>返回</a>"
            return self.printer.printError()

    def get_subject_list(self):
         self.putSubjectList()

    def get_grade_list(self):
        request.setAttribute("gradeId", self.params.getIntParamZeroAsNull("gradeId"))
        self.putGradeList()
