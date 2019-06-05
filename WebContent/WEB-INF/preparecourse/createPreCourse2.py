from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil, CommonUtil
from base_action import SubjectMixiner
from site_config import SiteConfig
from cn.edustar.jitar.pojos import PrepareCourse, PrepareCourseMember
from java.text import SimpleDateFormat
from java.util import Date
from message_out import MessagePrint
from base_action import BaseAction
from preparecourse_member_query import PrepareCourseMemberQuery

class createPreCourse2(SubjectMixiner, BaseAction):
    def __init__(self):
        self.params = ParamUtil(request)
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()
        self.printer = MessagePrint()
        
    def execute(self):
        if self.loginUser == None:
            self.printer.addMessage(u"请先<a href='../../login.jsp'>登录</a>，然后才能操作")
            return self.printer.printMessage("login.jsp", "")
        
        self.prepareCourseId = self.params.getIntParam("prepareCourseId")
        self.prepareCourse = self.pc_svc.getPrepareCourse(self.prepareCourseId)
        self.cmd = request.getParameter("cmd")
        
        if self.prepareCourse == None:
            self.printer.addMessage(u"未能加载备课。请重新选择一次备课。")
            return self.printer.printMessage("cocourses.action", "")
        
        # 只有 admin 和 创建者才能进行编辑
        accessControlService = __spring__.getBean("accessControlService")
        if not(accessControlService.isSystemAdmin(self.loginUser) or self.loginUser.loginUserId == self.prepareCourse.createUserId):
            self.printer.addMessage(u"只有管理员和创建者才能进行操作。")
            return self.printer.printMessage("manage/course/createPreCourse2.py?prepareCourseId=" + str(self.prepareCourseId), "")
        
        request.setAttribute("loginUser", self.loginUser) 
        request.setAttribute("prepareCourseId", self.prepareCourseId) 
        request.setAttribute("prepareCourse", self.prepareCourse) 
        
        if request.getMethod() == "POST":
            if self.cmd == "add":
                return self.saveOrUpdatePrepareCourse()
            elif self.cmd == "delete":
                return self.deleteUser()
            else:
                self.printer.addMessage(u"无效的命令。")
                return self.printer.printMessage("manage/course/createPreCourse2.py?prepareCourseId=" + str(self.prepareCourseId), "")
        else:
            return self.getPrepareCourse()
    
    
    def getPrepareCourse(self):
        site_config = SiteConfig()
        site_config.get_config()
        qry = PrepareCourseMemberQuery("""  u.userId, u.loginName,u.trueName,u.nickName,u.unitId, unit.unitName """)
        qry.prepareCourseId = self.prepareCourseId
        user_list = qry.query_map()
        request.setAttribute("user_list", user_list)
        request.setAttribute("head_nav", "cocourses")
        return "/WEB-INF/ftl/course/course_create2.ftl" 
    
    def deleteUser(self):
        user_list_delete = request.getParameterValues("guid")
        for u in user_list_delete:
            # 不能删除创建者
            if int(u) != self.prepareCourse.createUserId:
                self.pc_svc.deletePrepareCourseMember(self.prepareCourseId, int(u))
        
        self.printer.addMessage(u"删除用户成功。")
        return self.printer.printMessage("manage/course/createPreCourse2.py?prepareCourseId=" + str(self.prepareCourseId), "")
        
    def saveOrUpdatePrepareCourse(self):
        userLoginName = self.params.safeGetStringParam("userLoginName")
        if userLoginName == None or userLoginName == "":
            self.printer.addMessage(u"请输入一个用户的登录名。")
            return self.printer.printMessage("manage/course/createPreCourse2.py?prepareCourseId=" + str(self.prepareCourseId), "")
        
        # 判断用户是否真实存在，并是有效的用户。
        user = self.user_svc.getUserByLoginName(userLoginName)
        if user == None:
            self.printer.addMessage(u"您输入的用户不存在。")
            return self.printer.printMessage("manage/course/createPreCourse2.py?prepareCourseId=" + str(self.prepareCourseId), "")
        
        # 判断该用户是否已经在此备课中
        if self.pc_svc.checkUserInPreCourse(self.prepareCourseId, user.userId):
            self.printer.addMessage(u"该用户已经存在。")
            return self.printer.printMessage("manage/course/createPreCourse2.py?prepareCourseId=" + str(self.prepareCourseId), "")
        
        # 将用户加入到用户表中        
        pcm = PrepareCourseMember()
        pcm.setPrepareCourseId(self.prepareCourseId)
        pcm.setUserId(user.userId)
        pcm.setReplyCount(0)
        self.pc_svc.addPrepareCourseMember(pcm)
        self.printer.addMessage(u"将用户 " + user.trueName + u"（登录名为 " + user.loginName + u"） 添加到备课 " + self.prepareCourse.title + u" 成功。")
        return self.printer.printMessage("manage/course/createPreCourse2.py?prepareCourseId=" + str(self.prepareCourseId), "")