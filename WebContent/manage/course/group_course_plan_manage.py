from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner, BaseAction
from base_preparecourse_page import PrepareCoursePlanQuery
from cn.edustar.jitar.pojos import PrepareCoursePlan, GroupMember
from java.util import Date
from java.text import SimpleDateFormat

class group_course_plan_manage(SubjectMixiner,BaseAction):
    def __init__(self):
        self.params = ParamUtil(request) 
        self.groupService = __jitar__.getGroupService()
        self.prepareCourseService = __jitar__.getPrepareCourseService()
        self.groupId = 0
        self.group = None
    
    def execute(self):
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
        
        # 判断当前用户是否是组内成员
        gm = self.groupService.getGroupMemberByGroupIdAndUserId(self.groupId, self.loginUser.userId)
        if gm == None:
            actionErrors = [u"做人要厚道，你不是本组的成员。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        if gm.getStatus() != GroupMember.STATUS_NORMAL:
            actionErrors = [u"你目前的成员状态不正常，无法继续操作。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        if gm.groupRole < GroupMember.GROUP_ROLE_VICE_MANAGER:
            actionErrors = [u"权限不足，无法继续操作。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        cmd = self.params.safeGetStringParam("cmd")
        if request.getMethod() == "POST":
            if cmd == "delete":
                return self.deletePlan()
            elif cmd == "defaultvalue":
                return self.setDefaultValue()
            else:
                print "do nothing"
        
        qry = PrepareCoursePlanQuery("pcp.prepareCoursePlanId, pcp.title,pcp.createDate,pcp.defaultPlan,pcp.startDate,pcp.endDate,u.userId,u.trueName,pcp.createDate")
        qry.groupId = self.groupId
        pager = self.params.createPager()
        pager.itemName = u"计划"
        pager.itemUnit = u"个"
        pager.totalRows = qry.count()
        plan_list = qry.query_map(pager)
        request.setAttribute("pager",pager)
        request.setAttribute("plan_list",plan_list) 
        
        request.setAttribute("group",self.group)
        return "/WEB-INF/ftl/course/group_course_plan_manage.ftl"
        
    def deletePlan(self):
        planid = self.params.safeGetIntValues("guid")
        for pid in planid:
            prepareCourseList = self.prepareCourseService.getPrepareCourseListOfPlan(pid)
            for p in prepareCourseList:
                self.prepareCourseService.deletePrepareCourse(p.prepareCourseId)
            self.prepareCourseService.deletePrepareCoursePlan(pid)
        return self.SUCCESS
    
    def setDefaultValue(self):
        defaultplan = self.params.safeGetIntParam("defaultplan")
        plan = self.prepareCourseService.getPrepareCoursePlan(defaultplan)
        if plan == None:
            actionErrors = [u"你设置的备课计划不存在。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        self.prepareCourseService.setGroupDefaultPrepareCoursePlan(False,self.groupId)
        plan.setDefaultPlan(True)
        self.prepareCourseService.saveOrUpdatePrepareCoursePlan(plan)
        return self.SUCCESS
    