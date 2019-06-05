from cn.edustar.jitar.util import ParamUtil
from evaluation_query import *
from base_action import *

class evaluation_plan(ActionResult, SubjectMixiner, EvaluationBase):
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.canManage() == False:
            self.addActionError(u"需要系统内容管理员进行管理。")
            return self.ERROR
            
        cmd = self.params.safeGetStringParam("cmd")
        if cmd == "":cmd = "list"
        if cmd == "delete":
            self.delete()
        elif cmd == "enabled":
            self.enabled()
        elif cmd == "disabled":
            self.disabled()
            
        self.list()
        return "/WEB-INF/ftl/evaluation/evaluation_plan.ftl"
    
    def list(self):
        qry = EvaluationPlanQuery("ev.evaluationPlanId, ev.evaluationCaption, ev.teacherId,ev.teacherName, ev.createrId,ev.createrName,ev.metaSubjectId, ev.metaGradeId, subj.msubjName, grad.gradeName,ev.startDate, ev.endDate, ev.enabled, ev.teachDate")
        pager = self.params.createPager()
        pager.itemName = u"评课活动"
        pager.itemUnit = u"个"
        pager.totalRows = qry.count()
        pager.pageSize = 20
        evaluation_list = qry.query_map(pager)
        request.setAttribute("pager", pager)
        request.setAttribute("evaluation_list", evaluation_list)
        
    def delete(self):
        guids = self.params.safeGetIntValues("guid")
        evaluationService = __spring__.getBean("evaluationService")
        for g in guids:
            evaluationService.deleteEvaluationContentByEvaluationPlanId(g)
            evaluationService.deleteEvaluationPlanById(g)
            evaluationService.removeResourcesFromEvaluation(g)
            evaluationService.removeVideosFromEvaluation(g)
            evaluationService.removeEvaluationPlanTemplates(g)
                        
    def enabled(self):
        guids = self.params.safeGetIntValues("guid")
        evaluationService = __spring__.getBean("evaluationService")
        for g in guids:
            evaluationPlan = evaluationService.getEvaluationPlanById(g)
            if evaluationPlan != None:
                evaluationPlan.setEnabled(True)
                evaluationService.saveOrUpdateEvaluationPlan(evaluationPlan)
                
    def disabled(self):
        guids = self.params.safeGetIntValues("guid")
        evaluationService = __spring__.getBean("evaluationService")
        for g in guids:
            evaluationPlan = evaluationService.getEvaluationPlanById(g)
            if evaluationPlan != None:
                evaluationPlan.setEnabled(False)
                evaluationService.saveOrUpdateEvaluationPlan(evaluationPlan)