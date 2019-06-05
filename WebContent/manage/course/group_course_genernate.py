from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner, BaseAction
from cn.edustar.jitar.pojos import PrepareCoursePlan, PrepareCourse
from java.util import Date
from java.text import SimpleDateFormat
from base_preparecourse_page import *
from group_member_query import GroupMemberQuery

class group_course_genernate(SubjectMixiner,BaseAction):
    def __init__(self):
        self.params = ParamUtil(request)        
        self.groupService = __jitar__.getGroupService()
        self.prepareCourseService = __jitar__.getPrepareCourseService()
        self.groupId = 0
        self.prepareCoursePlanId = 0
        self.prepareCourseId = 0
        self.group = None        
        self.prepareCourse = None
        self.prepareCoursePlan = None
    
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
        # print "self.prepareCourseId,", self.prepareCourseId
        self.prepareCourse = self.prepareCourseService.getPrepareCourse(self.prepareCourseId)
        if self.prepareCourse == None:
            actionErrors = [u"未能成功加载集体备课对象。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        # 得到成员，加入成员表中
        qry = GroupMemberQuery("gm.userId")
        qry.groupId = self.groupId
        user_list = qry.query_map()
        for u in user_list:
            if self.prepareCourseService.checkUserExistsInPrepareCourse(self.prepareCourseId, u["userId"]) == False:
                m = PrepareCourseMember()
                m.setPrepareCourseId(self.prepareCourseId)
                m.setUserId(u["userId"])
                m.setJoinDate(Date())
                m.setStatus(0)
                m.setReplyCount(0)
                m.setContentLastupdated(Date())
                self.prepareCourseService.addPrepareCourseMember(m)
        
        # 更新统计信息
        self.prepareCourse.setPrepareCourseGenerated(True)
        self.prepareCourseService.updatePrepareCourse(self.prepareCourse)
        self.prepareCourseService.countPrepareCourseData(self.prepareCourse.prepareCourseId)
        response.sendRedirect("group_course_plan_edit.py?groupId=" + str(self.groupId) + "&prepareCoursePlanId=" + str(self.prepareCoursePlanId))