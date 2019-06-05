from evaluation_query import *
from base_action import *
class evaluations(EvaluationBase,SubjectMixiner):
    def __init__(self):                
        self.params = ParamUtil(request)
        
    def execute(self):
        type = self.params.getStringParam("type")
        if type == "" or type == None:
            type = "doing"
        k = self.params.getStringParam("k")
        kperson= self.params.getStringParam("kperson")
        subjectId = self.params.getIntParamZeroAsNull("subjectId")    
        gradeId = self.params.getIntParamZeroAsNull("gradeId")
        request.setAttribute("gradeId", gradeId)
        request.setAttribute("type", type)
        request.setAttribute("subjectId", subjectId)
        request.setAttribute("k", k)
        request.setAttribute("kperson", kperson)
        
        subjectService = __jitar__.subjectService
        subject_list = subjectService.getMetaSubjectList()
        outHtml = ""
        for s in subject_list:
            msid = s.getMsubjId()
            outHtml = outHtml + "d.add(" + str(msid) + ",0,'" + s.getMsubjName() + "','evaluations.py?subjectId=" + str(msid) + "','','_middle');"
            gradeIdList = subjectService.getMetaGradeListByMetaSubjectId(msid)
                             
            if gradeIdList != None:
                for gid in gradeIdList:
                    outHtml = outHtml + "d.add(" + str(msid) + str(gid.getGradeId()) + "," + str(msid) + ",'" + gid.getGradeName() + "','evaluations.py?subjectId=" + str(msid) + "&gradeId=" + str(gid.getGradeId()) + "&target=child','','_self');"
                
                    gradeLevelList = subjectService.getGradeLevelListByGradeId(gid.getGradeId())
                    for glevel in gradeLevelList:
                        outHtml = outHtml + "d.add(" + str(msid) + str(gid.getGradeId()) + str(glevel.getGradeId()) + "," + str(msid) + str(gid.getGradeId()) + ",'" + glevel.getGradeName() + "','evaluations.py?subjectId=" + str(msid) + "&gradeId=" + str(glevel.getGradeId()) + "&level=1','','_self');"

        request.setAttribute("outHtml", outHtml)
        act = self.params.safeGetStringParam("act")
        if act == "":act = "list"
        if act == "delete":
            self.delete()
        
        pager = self.params.createPager()
        pager.itemName = u"评课"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        
        qry = EvaluationPlanQuery("ev.evaluationPlanId, ev.evaluationCaption,ev.metaSubjectId, ev.metaGradeId, ev.startDate, ev.endDate, ev.teacherId, ev.teacherName, ev.teachDate, ev.createrId,ev.enabled,subj.msubjName, grad.gradeName")
        if type == "doing":
            qry.userId=0
            qry.listType=1
        elif type == "finished":
            qry.userId=0
            qry.listType=0
        elif type == "mine":
            if self.loginUser == None:
                errDesc = u"请先<a href='login.jsp'>登录</a>，然后才能操作"
                response.getWriter().write(errDesc)
                return    
            qry.userId=self.loginUser.userId
            qry.listType=2
        elif type == "done":
            if self.loginUser == None:
                errDesc = u"请先<a href='login.jsp'>登录</a>，然后才能操作"
                response.getWriter().write(errDesc)
                return    
            qry.userId=self.loginUser.userId
            qry.listType=3
        else:
            qry.listType=1
        #查询条件
        #print "subjectId="+str(subjectId)
        qry.enabled=True
        qry.title=k
        qry.subjectId=subjectId
        qry.teacherName=kperson
        qry.gradeId=gradeId
        pager.totalRows = qry.count()
        plan_list = qry.query_map(pager)
        request.setAttribute("pager", pager)
        request.setAttribute("plan_list", plan_list)
        
        # 学段分类.
        self.get_grade_list()
        # 学科分类
        self.get_subject_list()
        
        return "/WEB-INF/ftl/evaluation/evaluations.ftl"
    #学段
    def get_grade_list(self):
        request.setAttribute("gradeId", self.params.getIntParamZeroAsNull("gradeId"))
        self.putGradeList()
    # 学科列表.
    def get_subject_list(self):
        self.putSubjectList() 
    
    def delete(self):
        evaluationService = __spring__.getBean("evaluationService")
        actId=self.params.safeGetIntValues("actId")
        for g in actId:
            evaluationService.deleteEvaluationContentByEvaluationPlanId(g)
            evaluationService.deleteEvaluationPlanById(g)
            evaluationService.removeResourcesFromEvaluation(g)
            evaluationService.removeVideosFromEvaluation(g)
            evaluationService.removeEvaluationPlanTemplates(g)
            
    #进行中的评课      
    def get_doing_list(self):
        qry = EvaluationPlanQuery("ev.evaluationPlanId, ev.evaluationYear, ev.evaluationSemester, ev.evaluationTimes, ev.metaSubjectId, ev.metaGradeId, ev.startDate, ev.endDate, ev.userCount, ev.enabled")
        qry.ValidPlan = True
        qry.enabled = True
        plan_list = qry.query_map(qry.count())
        if len(plan_list) > 0:
            request.setAttribute("plan_list", plan_list)  
    #我参与的评课        
    def get_done_list(self):
        pager = self.params.createPager()
        pager.itemName = u"评课"
        pager.itemUnit = u"个"
        pager.pageSize = 20        
        qry = EvaluationContentQuery("ec.evaluationContentId, ec.title, ec.courseTeacherName, ec.publishUserName, ec.metaSubjectId, ec.metaGradeId, subj.msubjName, grad.gradeName, ec.createDate")
        qry.publishUserId = self.loginUser.userId
        pager.totalRows = qry.count()
        content_list = qry.query_map(pager)
        request.setAttribute("pager", pager)
        request.setAttribute("content_list", content_list)                       