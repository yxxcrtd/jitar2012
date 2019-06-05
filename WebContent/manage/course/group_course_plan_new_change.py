from cn.edustar.jitar.util import ParamUtil
from base_action import SubjectMixiner, BaseAction
from cn.edustar.jitar.pojos import PrepareCoursePlan, GroupMember, PrepareCourseMember
from java.util import Date
from java.text import SimpleDateFormat
from group_member_query import GroupMemberQuery

class group_course_plan_new_change(SubjectMixiner,BaseAction):
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
        
        # 判断当前用户是否是组内成员
        gm = self.groupService.getGroupMemberByGroupIdAndUserId(self.groupId, self.loginUser.userId)
        if gm == None:
            actionErrors = [u"你不是本组的成员。"]
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
        
        if request.getMethod() == "POST":
            return self.saveOrUpdate()
        
        self.prepareCoursePlanId = self.params.safeGetIntParam("prepareCoursePlanId")
        if self.prepareCoursePlanId != 0:
            self.prepareCoursePlan = self.prepareCourseService.getPrepareCoursePlan(self.prepareCoursePlanId)        
        
        # 学科分类.
        self.get_subject_list()
        # 学段分类.
        self.get_grade_list()
        
        request.setAttribute("group",self.group)
        request.setAttribute("prepareCoursePlan",self.prepareCoursePlan)
        return "/WEB-INF/ftl/course/group_course_plan_new_change.ftl"
        
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
        
        if plandefault == True:
            self.prepareCourseService.setGroupDefaultPrepareCoursePlan(False,self.group.groupId)
        
        #print "调试1：",self.prepareCoursePlan.prepareCoursePlanId
        
        # 验证课题数据
        #course_count = self.params.safeGetIntParam("course_count")
        #if course_count == 0:
        #    actionErrors = [u"致命的错误。"]
        #    request.setAttribute("actionErrors",actionErrors)
        #    return self.ERROR        
        
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
        # 注意：新改的只创建集备计划，新建集备在集备管理里面
        self.addActionMessage(u"保存集备计划成功。")
        self.addActionLink(u"创建该备课计划下的备课","group_course_plan_edit.py?groupId=" + str(self.group.groupId) + "&prepareCoursePlanId=" + str(self.prepareCoursePlan.prepareCoursePlanId))
        self.addActionLink(u"创建另外一个集备计划","group_course_plan_new_change.py?groupId=" + str(self.group.groupId))
        return self.SUCCESS
        
        
        # 得到协作组所有成员 
        qry = GroupMemberQuery("gm.userId")
        qry.memberStatus = 0
        qry.groupId = self.groupId
        user_list = qry.query_map(qry.count())        
                
        for row in range(1, course_count + 1):
            c_title = self.params.safeGetStringParam("title_" + str(row))
            c_leaderId = self.params.safeGetIntParam("leader_" + str(row))
            c_start = self.params.safeGetStringParam("start_" + str(row))
            c_end = self.params.safeGetStringParam("end_" + str(row))
            c_order = self.params.safeGetIntParam("order_" + str(row))
            c_content = self.params.safeGetStringParam("content_" + str(row))
            c_tag = self.params.safeGetStringParam("tag_" + str(row))
            if c_title.strip() != "":
                if c_leaderId == 0:
                    c_leaderId = self.loginUser.userId
                
                if c_start == None or c_start == "":
                    actionErrors = [u"请输入备课开始时间。"]
                    request.setAttribute("actionErrors",actionErrors)
                    return self.ERROR
                
                if c_end == None or c_end == "":
                    actionErrors = [u"请输入备课结束时间。"]
                    request.setAttribute("actionErrors",actionErrors)
                    return self.ERROR
                
                if c_content == None or c_content == "":
                    c_content = plancontent
                
                csd = Date()
                ced = Date()
                try:
                    csd  = SimpleDateFormat("yyyy-MM-dd").parse(c_start)
                except:
                    actionErrors = [u"输入的日期格式不正确，应当是: '年年年年-月月-日日' 格式"]
                    request.setAttribute("actionErrors",actionErrors)
                    return self.ERROR        
                try:
                    ced  = SimpleDateFormat("yyyy-MM-dd").parse(c_end)
                except:
                    actionErrors = [u"输入的日期格式不正确，应当是: '年年年年-月月-日日' 格式"]
                    request.setAttribute("actionErrors",actionErrors)
                    return self.ERROR
                
                prepareCourse = PrepareCourse()
                prepareCourse.setTitle(c_title)
                prepareCourse.setCreateUserId(self.loginUser.userId)
                prepareCourse.setLeaderId(c_leaderId)
                prepareCourse.setCreateDate(Date())
                prepareCourse.setStartDate(csd)
                prepareCourse.setEndDate(ced)
                prepareCourse.setDescription(c_content)
                prepareCourse.setCommonContent(None)
                prepareCourse.setMetaSubjectId(self.prepareCoursePlan.getSubjectId())
                prepareCourse.setGradeId(self.prepareCoursePlan.getGradeId())
                prepareCourse.setTags(c_tag)
                prepareCourse.setStatus(0)
                prepareCourse.setLockedDate(Date())
                prepareCourse.setLockedUserId(0)
                prepareCourse.setPrepareCourseEditId(0)
                prepareCourse.setItemOrder(c_order)
                prepareCourse.setPrepareCoursePlanId(self.prepareCoursePlan.prepareCoursePlanId)
                prepareCourse.setPrepareCourseGenerated(True)                
                self.prepareCourseService.createPrepareCourse(prepareCourse)
                
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
                        self.prepareCourseService.addPrepareCourseMember(m)
                
                # 更新统计信息
                self.prepareCourseService.updatePrepareCourse(prepareCourse)
                self.prepareCourseService.countPrepareCourseData(prepareCourse.prepareCourseId)
                
                #self.prepareCourseService.deletePrepareCoursePlan(self.prepareCoursePlan.prepareCoursePlanId)
        
        return self.SUCCESS
        
    #学段
    def get_grade_list(self):
        request.setAttribute("gradeId", self.params.getIntParamZeroAsNull("gradeId"))
        self.putGradeList()        
    
    # 学科.
    def get_subject_list(self):
        self.putSubjectList()
