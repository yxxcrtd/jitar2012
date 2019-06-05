from cn.edustar.jitar.data import Command
from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner
from cn.edustar.jitar.pojos import PrepareCourse, PrepareCourseStage, PrepareCourseMember
from java.text import SimpleDateFormat
from java.util import Date
from base_action import *
from base_blog_page import *
from action_query import ActionQuery
from base_preparecourse_page import *

class manage_createPrepareCourse_member(PrepareCoursePageService):
    def __init__(self):
        self.params = ParamUtil(request)
        self.printer = response.getWriter()        
        self.pc_svc = __jitar__.getPrepareCourseService()
        self.user_svc = __jitar__.getUserService()        
        self.group = None        
        
    def execute(self): 
        self.getBaseData()
        self.prepareCourseId = self.params.safeGetIntParam("prepareCourseId")
        if self.prepareCourseId == 0:
            self.printer.write(u"无效的备课标识。")
            return
    
        self.prepareCourse = self.pc_svc.getPrepareCourse(self.prepareCourseId)
        if self.prepareCourse == None:
            self.printer.write(u"没有加载到所请求的备课。")
            return
        
        if self.canManage(self.prepareCourse) == False:
            self.printer.write(u"您无权管理本备课。")
            return
        
        self.group = self.pc_svc.getGroupOfPrepareCourse(self.prepareCourse.prepareCourseId)
        if self.group == None:
            self.printer.write(u"集备必须是在协作组之内。")
            return
        
        if request.getMethod() == "POST":
            cmd = self.params.safeGetStringParam("cmd")
            if cmd == "1":
                self.delete_member()
            if cmd == "2":                
                self.approve_member()
            if cmd == "3":
                self.unapprove_member()
            if cmd == "4":
                self.add_member()            
            if cmd == "7":
                self.add_creater_member()
            if cmd == "8":
                self.unlock_course()
            
        userkey = self.params.safeGetStringParam("userkey")
        #self.printer.print("userkey="+userkey)
        pager = self.params.createPager()
        pager.itemName = u"成员"
        pager.itemUnit = u"个"
        qry = PrepareCourseMemberQuery(""" pcm.replyCount, pcm.joinDate,pcm.status, pcm.privateContent, pcm.bestState, u.userId, u.userIcon, u.loginName,u.trueName,u.nickName,u.unitId, unit.unitName""")     
        qry.prepareCourseId = self.prepareCourse.prepareCourseId
        if userkey!=None:
            if userkey!='':
                qry.userkey=userkey
        pager.totalRows = qry.count()
        user_list = qry.query_map(pager)      
           
        request.setAttribute("pager", pager)
        request.setAttribute("user_list", user_list)
        request.setAttribute("group", self.group)  
        request.setAttribute("prepareCourse", self.prepareCourse)
        request.setAttribute("userkey", userkey)
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/course/manage_createPrepareCourse_member.ftl"
    
    def delete_member(self):
        member_userId = self.params.safeGetIntValues("userId")
        for id in member_userId:
            self.pc_svc.deletePrepareCourseMember(self.prepareCourseId, id)
        
    def approve_member(self):        
        member_userId = self.params.safeGetIntValues("userId")
        for id in member_userId:
            prepareCourseMember = self.pc_svc.getPrepareCourseMemberByCourseIdAndUserIdWithNoStatus(self.prepareCourseId, id)
            if prepareCourseMember != None:
                prepareCourseMember.setStatus(0)
                prepareCourseMember.setPrivateContent("")
                self.pc_svc.updatePrepareCourseMember(prepareCourseMember)
        
    def unapprove_member(self):
        member_userId = self.params.safeGetIntValues("userId")
        for id in member_userId:
            prepareCourseMember = self.pc_svc.getPrepareCourseMemberByCourseIdAndUserIdWithNoStatus(self.prepareCourseId, id)
            if prepareCourseMember != None:
                prepareCourseMember.setStatus(1)
                self.pc_svc.updatePrepareCourseMember(prepareCourseMember)
    
    def add_member(self):
        memberId = self.params.safeGetIntValues("memberId")
        for id in memberId:            
            user = self.user_svc.getUserById(id)
            if user != None:
                # 判断该用户是否已经在此备课中
                if self.pc_svc.checkUserInPreCourse(self.prepareCourseId, user.userId) == False:                
                    prepareCourseMember = PrepareCourseMember()
                    prepareCourseMember.setPrepareCourseId(self.prepareCourseId)
                    prepareCourseMember.setUserId(user.getUserId())
                    prepareCourseMember.setPrivateContent("")
                    prepareCourseMember.setJoinDate(Date())
                    prepareCourseMember.setStatus(0)
                    prepareCourseMember.setReplyCount(0)
                    prepareCourseMember.setContentLastupdated(Date())                
                    self.pc_svc.addPrepareCourseMember(prepareCourseMember)
            else:
                self.printer.write(u"<script>alert('你输入的用户名不存在。请核对后再输入。');</script>")
                return
            
    def add_creater_member(self):
        createUserId = self.params.safeGetIntParam("createUserId")
        user = self.user_svc.getUserById(createUserId)
        if user != None:
            prepareCourseMember = self.pc_svc.getPrepareCourseMemberByCourseIdAndUserId(self.prepareCourseId, user.getUserId())
            if prepareCourseMember == None:
                #添加一个成员
                prepareCourseMember = PrepareCourseMember()
                prepareCourseMember.setPrepareCourseId(self.prepareCourseId)
                prepareCourseMember.setUserId(user.getUserId())
                prepareCourseMember.setPrivateContent(None)
                prepareCourseMember.setJoinDate(Date())
                prepareCourseMember.setStatus(0)
                prepareCourseMember.setReplyCount(0)
                self.pc_svc.addPrepareCourseMember(prepareCourseMember)
            self.prepareCourse.setCreateUserId(user.getUserId())
            self.pc_svc.updatePrepareCourse(self.prepareCourse)   
            self.removePrepareCourseCache()
            response.sendRedirect("manage_createPrepareCourse_member.py?prepareCourseId=" + str(self.prepareCourseId))
        else:
            self.printer.write(u"<script>alert('你输入的登录名不存在。请核对后再输入。');</script>")
            return
        
    def unlock_course(self):
        unLockUserId = self.params.safeGetIntValues("unLockUserId")
        self.prepareCourse.setLockedUserId(0)
        self.pc_svc.updatePrepareCourse(self.prepareCourse)
        self.removePrepareCourseCache()

