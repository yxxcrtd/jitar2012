from evaluation_query import *
from base_action import *
from cn.edustar.jitar.action import ActionLink
class evaluations_content_show(EvaluationBase):
    def execute(self):
        evaluationContentId = self.params.safeGetIntParam("evaluationContentId")
        evaluationService = __spring__.getBean("evaluationService")
        evaluationContent = evaluationService.getEvaluationContentById(evaluationContentId);
        if evaluationContent == None:
            self.addActionError(u"无法加载评课内容。")
            return ActionResult.ERROR
                    
        request.setAttribute("evaluationContent", evaluationContent)
        return "/WEB-INF/ftl/evaluation/evaluations_content_show.ftl"
