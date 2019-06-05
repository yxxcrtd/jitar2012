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
from cn.edustar.usermgr import BaseUser

class show_preparecourse_edit(PrepareCoursePageService):
    def __init__(self):
        self.printer = response.getWriter()        
        self.prepareCourseEdit = None
        self.accessControlService = __spring__.getBean("accessControlService")
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        self.sub_svc = __jitar__.subjectService
        
    def execute(self):
        self.getBaseData()        
        response.setContentType("text/html; charset=UTF-8")
        if self.loginUser == None:
            self.printer.write(u"请先登录。<a href='../../../../login.jsp'>登录页面</a>")
            return
        
        baseUser = self.user_svc.getUserByUsername(self.loginUser.loginName)       
        accessControlService = __spring__.getBean("accessControlService") 
        if not(baseUser.role ==1 or baseUser.role == 3) and accessControlService.isSystemAdmin(self.loginUser) == False:
            self.printer.write(u"只有管理员和老师身份才能创建备课。<br/><br/><a href='../../../../cocourses.action'>返回</a>")
            return
        
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的课程标识。")
            return
        prepareCourse = self.getBasePrepareCourse()
        if prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        if self.canManage(prepareCourse) == False:
            self.printer.write(u"您无权管理本备课。")
            return
        
       
        page = self.getPrepareCoursePageWithCustomSkin(prepareCourse)
        widgets = [                  
                   {"id": "placerholder1", "pageId":0, "columnIndex":2,"title":"","module":"placeholder", "ico":"", "data":""}
                  ]
        
        request.setAttribute("widget_list",widgets)
        request.setAttribute("page", page)
        request.setAttribute("prepareCourse",prepareCourse)
        request.setAttribute("prepareCourseId",self.prepareCourseId)        
        
        self.prepareCourseMember = self.pc_svc.getPrepareCourseMemberByCourseIdAndUserId(self.prepareCourseId, self.loginUser.userId)
        if self.prepareCourseMember == None:
            operation_result = u"您不是该课的成员，或者加载对象失败。"
            request.setAttribute("operation_result",operation_result)
            return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"
        
        request.setAttribute("prepareCourseMember", self.prepareCourseMember)
        
        if request.getMethod() == "POST":
            return self.saveOrUpdatePrepareCourse()
        
        self.get_subject_list()
        self.get_grade_list()
        
        return "/WEB-INF/ftl/course/show_preparecourse_edit.ftl"
    
    
    def saveOrUpdatePrepareCourse(self):
        if self.prepareCourse == None:
            self.prepareCourse = PrepareCourse()
        else:
            # 只有admin 和 主备人进行修改
            if not(self.accessControlService.isSystemAdmin(self.loginUser) or self.loginUser.userId == self.prepareCourse.createUserId ):
                return self.getReturnInfo(u"只有 admin 或者创建人、主备人才能进行修改。")
        
        pcTitle = self.params.safeGetStringParam("pcTitle")
        pcStartDate = self.params.safeGetStringParam("pcStartDate")
        pcEndDate = self.params.safeGetStringParam("pcEndDate")
        pcGradeId = self.params.getIntParamZeroAsNull("pcGrade")
        pcMetaSubjectId = self.params.getIntParamZeroAsNull("pcMetaSubject")
        pcDescription = self.params.safeGetStringParam("pcDescription")
        pcLeader = self.params.safeGetIntParam("pcLeader")     
        pcTags = self.params.safeGetStringParam("pcTags")
        
        if pcLeader == "":
            user_leader = self.loginUser
        else:       
            user_leader = self.user_svc.getUserById(pcLeader)
            if user_leader == None:
                return self.getReturnInfo(u"该用户不存在。")
        
        if pcGradeId == None or pcGradeId == 0:
            return self.getReturnInfo(u"你必须选择一个学段。")
        
        if pcMetaSubjectId == None or pcMetaSubjectId == 0:
            return self.getReturnInfo(u"你必须选择一个学科。")
        
        pcStartDateTime = SimpleDateFormat("yyyy-MM-d").parse(pcStartDate)
        pcEndDateTime = SimpleDateFormat("yyyy-MM-d").parse(pcEndDate)
        
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
            return self.getReturnInfo(u"您的 <span style='color:#f00'>" + pcTitle + u"</span> 修改成功。")
        else:
            self.pc_svc.createPrepareCourse(self.prepareCourse)
            return self.getReturnInfo(u"您的 <span style='color:#f00'>" + pcTitle + u"</span> 创建成功。")


    def get_subject_list(self):
        subject_list = self.sub_svc.getMetaSubjectList()
        request.setAttribute("subject_list", subject_list)

    def get_grade_list(self):
        request.setAttribute("gradeId", self.params.getIntParamZeroAsNull("gradeId"))
        grade_list = self.sub_svc.getGradeList()
        request.setAttribute("grade_list", grade_list);
        
    def getReturnInfo(self,strMesage):
        request.setAttribute("operation_result",strMesage)
        return "/WEB-INF/ftl/course/show_preparecourse_ok_info.ftl"