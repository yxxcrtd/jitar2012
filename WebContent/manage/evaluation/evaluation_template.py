from evaluation_query import *
from base_action import *

class evaluation_template(ActionResult, SubjectMixiner, EvaluationBase):
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        if self.canManage() == False:
            self.addActionError(u"需要系统内容管理员进行管理。")
            return self.ERROR
        cmd = self.params.safeGetStringParam("cmd")
        if cmd == "delete":
            self.delete()
        elif cmd == "enabled":
            self.enabled()
        elif cmd == "disabled":
            self.disabled()
            
        qry = EvaluationTemplateQuery("et.evaluationTemplateId, et.evaluationTemplateName, et.enabled")
        template_list = qry.query_map(qry.count())
        request.setAttribute("template_list",template_list)
        
        return "/WEB-INF/ftl/evaluation/evaluation_template.ftl"
    
    def delete(self):
        guids = self.params.safeGetIntValues("guid")
        evaluationService = __spring__.getBean("evaluationService")
        for g in guids:
            evaluationService.deleteEvaluationTemplateById(g)
            evaluationService.deleteEvaluationTemplateFields(g)
            
    def enabled(self):
        guids = self.params.safeGetIntValues("guid")
        evaluationService = __spring__.getBean("evaluationService")
        for g in guids:
            evaluationTemplate = evaluationService.getEvaluationTemplateById(g)
            if evaluationTemplate != None:
                evaluationTemplate.setEnabled(True)
                evaluationService.saveOrUpdateEvaluationTemplate(evaluationTemplate)
    def disabled(self):
        guids = self.params.safeGetIntValues("guid")
        evaluationService = __spring__.getBean("evaluationService")
        for g in guids:
            evaluationTemplate = evaluationService.getEvaluationTemplateById(g)
            if evaluationTemplate != None:
                evaluationTemplate.setEnabled(False)
                evaluationService.saveOrUpdateEvaluationTemplate(evaluationTemplate)