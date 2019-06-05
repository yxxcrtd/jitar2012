from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner, BaseAction
from cn.edustar.jitar.pojos import PrepareCoursePlan, PrepareCourse, GroupMember
from java.util import Date
from java.text import SimpleDateFormat
from base_preparecourse_page import *
from group_member_query import GroupMemberQuery

class group_course_delete(SubjectMixiner,BaseAction):
    def __init__(self):
        self.params = ParamUtil(request)
        self.prepareCoursePlan = None
        self.groupService = __jitar__.getGroupService()
        self.prepareCourseService = __jitar__.getPrepareCourseService()
        self.groupId = 0
        self.group = None
        self.prepareCoursePlanId = 0
        self.prepareCourseId = 0
        self.prepareCourse = None
    
    def execute(self):        
        # 权限：只要是组员都可以进行生成。
        if self.loginUser == None:
            actionErrors = [u"请先登录。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        self.groupId = self.params.safeGetIntParam("groupId")
        self.group = self.groupService.getGroup(self.groupId)
        if self.group == None:
            actionErrors = [u"未能成功加载协作组。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        self.prepareCoursePlanId = self.params.safeGetIntParam("prepareCoursePlanId")
        self.prepareCoursePlan = self.prepareCourseService.getPrepareCoursePlan(self.prepareCoursePlanId)
        if self.prepareCoursePlan == None:
            actionErrors = [u"未能成功加载备课计划对象。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        self.prepareCourseId = self.params.safeGetIntParam("prepareCourseId")
        self.prepareCourse = self.prepareCourseService.getPrepareCourse(self.prepareCourseId)
        if self.prepareCourse == None:
            actionErrors = [u"未能成功加载集体备课对象。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        group_member = self.groupService.getGroupMemberByGroupIdAndUserId(self.groupId,self.loginUser.userId)
        if group_member == None:
            actionErrors = [u"你不是该组的成员。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        if group_member.getStatus() != GroupMember.STATUS_NORMAL:
            actionErrors = [u"你目前的成员状态不正常，无法继续操作。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        self.prepareCourseService.deletePrepareCourse(self.prepareCourse.prepareCourseId)
        response.sendRedirect("group_course_plan_edit.py?groupId=" + str(self.groupId) + "&prepareCoursePlanId=" + str(self.prepareCoursePlanId))