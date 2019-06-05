#查询并显示模板的字段输入部分
from evaluation_query import *
from cn.edustar.jitar.util import ParamUtil
from java.text import SimpleDateFormat
from base_action import *
from cn.edustar.jitar.pojos import EvaluationPlan
from cn.edustar.jitar.pojos import EvaluationContent
from java.util import Date

class evaluationFieldsContent(ActionExecutor, SubjectMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
        self.video_svc = __spring__.getBean("videoService")
        self.res_svc = __spring__.getBean("resourceService")
        self.evaluationService = __spring__.getBean("evaluationService")
        self.finish=0
        
    def execute(self):
        templateId = self.params.getIntParam("templateId")
        if templateId>0:
            field_list=self.evaluationService.getEvaluationTemplateFields(templateId)
            request.setAttribute("field_list", field_list)
            return "/WEB-INF/ftl/evaluation/fieldcontent.ftl"
            
                