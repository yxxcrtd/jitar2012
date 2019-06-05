from evaluation_query import *

class evaluations_right(EvaluationBase):
    def execute(self):
        qry = EvaluationPlanQuery("ev.evaluationPlanId, ev.evaluationYear, ev.evaluationSemester, ev.evaluationTimes, ev.metaSubjectId, ev.metaGradeId, ev.startDate, ev.endDate, ev.userCount, ev.enabled")
        qry.ValidPlan = True
        qry.enabled = True
        plan_list = qry.query_map(qry.count())
        if len(plan_list) > 0:
            request.setAttribute("plan_list", plan_list)
        return "/WEB-INF/ftl/evaluation/evaluations_right.ftl"