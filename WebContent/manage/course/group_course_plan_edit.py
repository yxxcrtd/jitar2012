from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner, BaseAction
from cn.edustar.jitar.pojos import PrepareCoursePlan, PrepareCourse, GroupMember, PrepareCourseMember
from java.util import Date
from java.text import SimpleDateFormat
from base_preparecourse_page import *
from cn.edustar.jitar.action import ActionLink
from group_member_query import GroupMemberQuery

class group_course_plan_edit(SubjectMixiner,BaseAction):
    def __init__(self):
        self.params = ParamUtil(request)
        self.prepareCoursePlan = None
        self.groupService = __jitar__.getGroupService()
        self.prepareCourseService = __jitar__.getPrepareCourseService()
        self.groupId = 0
        self.group = None
        self.prepareCoursePlanId = 0
    
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
        group_member = self.groupService.getGroupMemberByGroupIdAndUserId(self.groupId,self.loginUser.userId)
        if group_member == None:
            actionErrors = [u"你不是该组的成员。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        if group_member.getStatus() != GroupMember.STATUS_NORMAL:
            actionErrors = [u"你目前的成员状态不正常，无法继续操作。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        if group_member.groupRole < GroupMember.GROUP_ROLE_VICE_MANAGER:
            actionErrors = [u"权限不足，无法继续操作。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        self.prepareCoursePlanId = self.params.safeGetIntParam("prepareCoursePlanId")
        self.prepareCoursePlan = self.prepareCourseService.getPrepareCoursePlan(self.prepareCoursePlanId)
        if self.prepareCoursePlan == None:
            actionErrors = [u"未能成功加载备课计划对象。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR        
        
        cmd = self.params.safeGetStringParam("cmd")
        if request.getMethod() == "POST":
            if cmd == "add":
                return self.addCourse()
            elif cmd=="itemorder":
                return self.setItemOrder()
            elif cmd=="edit":
                return self.editPlan()
            else:
                print u"无效的命令"
        
        self.prepareCoursePlanId = self.params.safeGetIntParam("prepareCoursePlanId")
        if self.prepareCoursePlanId != 0:
            self.prepareCoursePlan = self.prepareCourseService.getPrepareCoursePlan(self.prepareCoursePlanId)
        
        if self.prepareCoursePlan == None:
            actionErrors = [u"未能成功加载备课计划。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
       
        qry = PrepareCourseQuery(""" pc.prepareCourseId, pc.title,pc.startDate,pc.endDate,pc.contentType,pc.description,pc.leaderId,pc.itemOrder,pc.status,pc.prepareCourseGenerated """)
        qry.prepareCoursePlanId = self.prepareCoursePlan.prepareCoursePlanId
        qry.orderType = 1
        qry.status = None
        qry.prepareCourseGenerated = None
        pc_list = qry.query_map(1000)
        self.get_grade_list()
        self.get_subject_list()
        request.setAttribute("group",self.group)
        request.setAttribute("prepareCoursePlan",self.prepareCoursePlan)
        if pc_list != None and len(pc_list) > 0:
            request.setAttribute("pc_list",pc_list)

        return "/WEB-INF/ftl/course/group_course_plan_edit.ftl"        
    
    def setItemOrder(self):
        pcid = self.params.safeGetIntValues("pcid")
        for pid in pcid:
            p = self.prepareCourseService.getPrepareCourse(pid)
            if p != None:
                p.setItemOrder(self.params.safeGetIntParam("itemorder" + str(pid)))
                self.prepareCourseService.updatePrepareCourse(p)
        response.sendRedirect("group_course_plan_edit.py?groupId=" + str(self.groupId) + "&prepareCoursePlanId=" + str(self.prepareCoursePlanId))
        
    def editPlan(self):
        if self.prepareCoursePlan == None:
            actionErrors = [u"要编辑的备课计划不能为 null 。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        title = self.params.getStringParam("plantitle")
        planstartdate = self.params.getStringParam("planstartdate")
        planenddate = self.params.getStringParam("planenddate")
        gradeId = self.params.safeGetIntParam("gradeId")
        subjectId = self.params.safeGetIntParam("subjectId")
        plancontent = self.params.getStringParam("plancontent")
        plandefault = self.params.getStringParam("plandefault")
        
        if plandefault == "":
            plandefault = False
        else:
            plandefault = True
            
        if title == None or title == "":
            actionErrors = [u"请输入备课计划标题。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        #if planstartdate == None or planstartdate == "":
        #    actionErrors = ["请输入备课计划开始时间。"]
        #    request.setAttribute("actionErrors",actionErrors)
        #    return self.ERROR
        
        if planenddate == None or planenddate == "":
            actionErrors = [u"请输入备课计划结束时间。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        #sd = Date()
        ed = Date()
        #try:
        #    sd  = SimpleDateFormat("yyyy-MM-dd").parse(planstartdate)
        #except:
        #    actionErrors = ["输入的日期格式不正确，应当是: '年年年年-月月-日日' 格式"]
        #    request.setAttribute("actionErrors",actionErrors)
        #    return self.ERROR
        
        try:
            ed  = SimpleDateFormat("yyyy-MM-dd").parse(planenddate)
        except:
            actionErrors = [u"输入的日期格式不正确，应当是: '年年年年-月月-日日' 格式"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        if gradeId == 0:
            actionErrors = [u"请输入学段、年级。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        if subjectId == 0:
            actionErrors = [u"请输入学科。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR        
        
        
        if self.prepareCoursePlan == None:
            self.prepareCoursePlan = PrepareCoursePlan()
        
        if plandefault == True:
            self.prepareCourseService.setGroupDefaultPrepareCoursePlan(False,self.group.groupId)
        
        self.prepareCoursePlan.setTitle(title)
        #self.prepareCoursePlan.setStartDate(sd)
        self.prepareCoursePlan.setEndDate(ed)
        self.prepareCoursePlan.setGroupId(self.group.groupId)
        self.prepareCoursePlan.setGradeId(gradeId)
        self.prepareCoursePlan.setSubjectId(subjectId)
        self.prepareCoursePlan.setPlanContent(plancontent)
        self.prepareCoursePlan.setDefaultPlan(plandefault)
        self.prepareCoursePlan.setCreateDate(Date())
        self.prepareCoursePlan.setCreateUserId(self.loginUser.userId)
        self.prepareCourseService.saveOrUpdatePrepareCoursePlan(self.prepareCoursePlan)
        
        return self.SUCCESS
        
    def saveOrUpdate(self):
        #准备数据
        title = self.params.getStringParam("plantitle")
        planstartdate = self.params.getStringParam("planstartdate")
        planenddate = self.params.getStringParam("planenddate")
        gradeId = self.params.safeGetIntParam("gradeId")
        subjectId = self.params.safeGetIntParam("subjectId")
        plancontent = self.params.getStringParam("plancontent")
        plandefault = self.params.getStringParam("plandefault")
        if plandefault == "":
            plandefault = False
        else:
            plandefault = True
            
        if title == None or title == "":
            actionErrors = [u"请输入备课计划标题。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        if planstartdate == None or planstartdate == "":
            actionErrors = [u"请输入备课计划开始时间。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        if planenddate == None or planenddate == "":
            actionErrors = [u"请输入备课计划结束时间。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        sd = Date()
        ed = Date()
        try:
            sd  = SimpleDateFormat("yyyy-MM-dd").parse(planstartdate)
        except:
            actionErrors = [u"输入的日期格式不正确，应当是: '年年年年-月月-日日' 格式"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        try:
            ed  = SimpleDateFormat("yyyy-MM-dd").parse(planenddate)
        except:
            actionErrors = [u"输入的日期格式不正确，应当是: '年年年年-月月-日日' 格式"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        if gradeId == 0:
            actionErrors = [u"请输入学段、年级。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        if subjectId == 0:
            actionErrors = [u"请输入学科。"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR        
        
        
        if self.prepareCoursePlan == None:
            self.prepareCoursePlan = PrepareCoursePlan()
        
        self.prepareCoursePlan.setTitle(title)
        self.prepareCoursePlan.setStartDate(sd)
        self.prepareCoursePlan.setEndDate(ed)
        self.prepareCoursePlan.setGroupId(self.group.groupId)
        self.prepareCoursePlan.setGradeId(gradeId)
        self.prepareCoursePlan.setSubjectId(subjectId)
        self.prepareCoursePlan.setPlanContent(plancontent)
        self.prepareCoursePlan.setDefaultPlan(plandefault)
        self.prepareCoursePlan.setCreateDate(Date())
        self.prepareCoursePlan.setCreateUserId(self.loginUser.userId)
        self.prepareCourseService.saveOrUpdatePrepareCoursePlan(self.prepareCoursePlan)      
        return self.SUCCESS
        
    #学段
    def get_grade_list(self):
        request.setAttribute("gradeId", self.params.getIntParamZeroAsNull("gradeId"))
        self.putGradeList()        
    
    # 学科.
    def get_subject_list(self):
        self.putSubjectList()
    
    def addCourse(self):        
        startDate = self.params.getStringParam("startDate")
        endDate = self.params.getStringParam("endDate")
        pctitle = self.params.getStringParam("pctitle")
        pcdesc = self.params.safeGetStringParam("pcdesc")
        pcleader = self.params.safeGetIntParam("pcLeader")
        pctags = self.params.getStringParam("pctags")
        pc_itemorder = self.params.safeGetIntParam("pc_itemorder")
        contentType = self.params.safeGetIntParam("contentType")
        if pctitle == None or pctitle == "":
            actionErrors = [u"请输入课题名称"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        if startDate == None or startDate == "":
            actionErrors = [u"请输入开始时间"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        sd = Date()
        ed = Date()
        try:
            sd  = SimpleDateFormat("yyyy-MM-dd").parse(startDate)
        except:
            actionErrors = [u"输入的开始日期格式不正确，应当是: '年年年年-月月-日日' 格式"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        if endDate == None or endDate == "":
            actionErrors = [u"请输入结束时间"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        try:
            ed  = SimpleDateFormat("yyyy-MM-dd").parse(endDate)
        except:
            actionErrors = [u"输入的结束日期格式不正确，应当是: '年年年年-月月-日日' 格式"]
            request.setAttribute("actionErrors",actionErrors)
            return self.ERROR
        
        if pcleader == 0:
            pcleader = self.loginUser.userId
            #actionErrors = [u"请输入主备人"]
            #request.setAttribute("actionErrors",actionErrors)
            #return self.ERROR               
        
        prepareCourse = PrepareCourse()
        prepareCourse.setTitle(pctitle)
        prepareCourse.setCreateUserId(self.loginUser.userId)
        prepareCourse.setLeaderId(pcleader)
        prepareCourse.setCreateDate(Date())
        prepareCourse.setStartDate(sd)
        prepareCourse.setEndDate(ed)
        prepareCourse.setDescription(pcdesc)
        prepareCourse.setCommonContent("")
        prepareCourse.setMetaSubjectId(self.prepareCoursePlan.getSubjectId())
        prepareCourse.setGradeId(self.prepareCoursePlan.getGradeId())
        prepareCourse.setTags(pctags)
        prepareCourse.setStatus(0)
        prepareCourse.setLockedDate(Date())
        prepareCourse.setLockedUserId(0)
        prepareCourse.setPrepareCourseEditId(0)
        prepareCourse.setItemOrder(pc_itemorder)
        prepareCourse.setPrepareCoursePlanId(self.prepareCoursePlan.prepareCoursePlanId)
        prepareCourse.setPrepareCourseGenerated(True)
        prepareCourse.setContentType(contentType)       
        self.prepareCourseService.createPrepareCourse(prepareCourse)
        
        # 得到协作组所有成员 
        qry = GroupMemberQuery("gm.userId")
        qry.memberStatus = 0
        qry.groupId = self.prepareCoursePlan.groupId
        user_list = qry.query_map(qry.count())
        
        pcmContentType = 0
        
        #对于创建者和主备人，需要更新下个人信息，主要是ContentType属性
        m = self.prepareCourseService.getPrepareCourseMemberByCourseIdAndUserId(prepareCourse.prepareCourseId,prepareCourse.createUserId)
        if m != None:
            m.setContentType(pcmContentType)
            self.prepareCourseService.updatePrepareCourseMember(m)
            
        if prepareCourse.createUserId != prepareCourse.leaderId:            
            m = self.prepareCourseService.getPrepareCourseMemberByCourseIdAndUserId(prepareCourse.prepareCourseId,prepareCourse.leaderId)
            if m != None:
                m.setContentType(pcmContentType)
                self.prepareCourseService.updatePrepareCourseMember(m)
        # 添加成员
        for u in user_list:
            if self.prepareCourseService.checkUserExistsInPrepareCourse(prepareCourse.prepareCourseId, u["userId"]) == False:
                m = PrepareCourseMember()
                m.setPrepareCourseId(prepareCourse.prepareCourseId)
                m.setUserId(u["userId"])
                m.setJoinDate(Date())
                m.setStatus(0)
                m.setReplyCount(0)
                m.setContentLastupdated(Date())
                m.setContentType(pcmContentType)
                self.prepareCourseService.addPrepareCourseMember(m)
        
        # 更新统计信息
        self.prepareCourseService.updatePrepareCourse(prepareCourse)
        self.prepareCourseService.countPrepareCourseData(prepareCourse.prepareCourseId)
                 
        actionLinks = ActionLink(u"返回","group_course_plan_edit.py?groupId=" + str(self.groupId) + "&prepareCoursePlanId=" + str(self.prepareCoursePlanId) + "&tmp=" + str(Date().hashCode()) ,"_self")
       
        request.setAttribute("actionLinks",[actionLinks])        
        return self.SUCCESS