from java.util import Calendar
from java.text import SimpleDateFormat
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import EvaluationPlan
from base_action import *
from evaluation_query import *

class evaluation_plan_edit(ActionResult, SubjectMixiner, EvaluationBase):
    
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        if self.canManage() == False:
            self.addActionError(u"需要系统内容管理员进行管理。")
            return self.ERROR
        
        evaluationPlanId = self.params.safeGetIntParam("evaluationPlanId")
        today = Calendar.getInstance()
        request.setAttribute("currentYear", today.get(Calendar.YEAR))
        request.setAttribute("currentMonth", today.get(Calendar.MONTH))
        evaluationService = __spring__.getBean("evaluationService")
        evaluationPlan = evaluationService.getEvaluationPlanById(evaluationPlanId)
        if request.getMethod() == "POST":
            evaluationYear = self.params.safeGetIntParam("year")
            evaluationSemester = self.params.safeGetIntParam("semester")
            evaluationTimes = self.params.safeGetIntParam("times")
            startDate = self.params.safeGetStringParam("startDate")
            endDate = self.params.safeGetStringParam("endDate")
            enabled = self.params.safeGetIntParam("enabled")
            gradeId = self.params.getIntParamZeroAsNull("gradeId")
            subjectId = self.params.getIntParamZeroAsNull("subjectId")
            userCount = self.params.safeGetIntParam("userCount")
            startTime = self.params.safeGetIntParam("startTime")
            endTime = self.params.safeGetIntParam("endTime")
            # 规范数据
            if evaluationSemester != 0 :evaluationSemester = 1
            if enabled == 0:
                enabled = False
            else:
                enabled = True
                
            if startTime < 0 or startTime > 23:
                self.addActionError(U"开始时间超过了范围。")
                return self.ERROR
            if endTime < 0 or endTime > 23:
                self.addActionError(U"结束时间超过了范围。")
                return self.ERROR
            if startTime < 10:
                st = "0" + str(startTime)
            else:
                st = str(startTime)
            
            if endTime < 10:
                et = "0" + str(endTime)
            else:
                et = str(endTime)
                
            try:
                sd = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate + " " + st + ":00:00")
            except:
                self.addActionError(U"输入的开始日期格式不正确，应当是: '年年年年-月月-日日' 格式")
                return self.ERROR
            try:
                ed = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate + " " + et + ":00:00")
            except:
                self.addActionError(U"输入的结束日期格式不正确，应当是: '年年年年-月月-日日' 格式")
                return self.ERROR
            
            
            if evaluationPlan == None:
                evaluationPlan = EvaluationPlan()
            evaluationPlan.setEvaluationYear(evaluationYear)
            evaluationPlan.setEvaluationSemester(evaluationSemester)            
            evaluationPlan.setEvaluationTimes(evaluationTimes)
            evaluationPlan.setMetaSubjectId(subjectId)
            evaluationPlan.setMetaGradeId(gradeId)
            evaluationPlan.setStartDate(sd)
            evaluationPlan.setEndDate(ed)
            evaluationPlan.setUserCount(userCount)
            evaluationPlan.setEnabled(enabled)
            
            evaluationService.saveOrUpdateEvaluationPlan(evaluationPlan)
            return self.SUCCESS
        
        if evaluationPlan != None:
            request.setAttribute("evaluationPlan", evaluationPlan)
        self.putSubjectList()
        self.putGradeList()
        return "/WEB-INF/ftl/evaluation/evaluation_plan_edit.ftl"
