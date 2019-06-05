from cn.edustar.jitar.util import ParamUtil
from evaluation_query import *
from base_action import *

class evaluation_stats_show(ActionResult, SubjectMixiner, EvaluationBase):
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        if self.canManage() == False:
            self.addActionError(u"需要系统内容管理员进行管理。")
            return self.ERROR
        evaluationPlanId = self.params.safeGetIntParam("evaluationPlanId")
        if evaluationPlanId == None or evaluationPlanId == 0:
            self.addActionError(u"请选择一次评课活动。")
            return self.ERROR
        
        evaluationService = __spring__.getBean("evaluationService")
        evaluationPlan = evaluationService.getEvaluationPlanById(evaluationPlanId)
        if evaluationPlan == None:
            self.addActionError(u"无法加载评课活动。")
            return self.ERROR
        
        qry = EvaluationContentQuery("ec.evaluationContentId")
        qry.evaluationPlanId = evaluationPlanId
        qry.FuzzyMatch = None        
        request.setAttribute("count", qry.count())
        self.putSubjectList()
        self.putGradeList()
        request.setAttribute("evaluationPlanId", evaluationPlanId)
        request.setAttribute("evaluationPlan", evaluationPlan)
        return "/WEB-INF/ftl/evaluation/evaluation_stats_show.ftl"
