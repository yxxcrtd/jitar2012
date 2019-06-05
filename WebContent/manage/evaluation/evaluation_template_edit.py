from java.util import Calendar
from java.text import SimpleDateFormat
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import EvaluationTemplate
from cn.edustar.jitar.pojos import EvaluationTemplateFields
from base_action import *
from evaluation_query import *

class evaluation_template_edit(ActionResult, SubjectMixiner, EvaluationBase):
    
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        if self.canManage() == False:
            self.addActionError(u"需要系统内容管理员进行管理。")
            return self.ERROR
        
        evaluationTemplateId = self.params.safeGetIntParam("evaluationTemplateId")        
        evaluationService = __spring__.getBean("evaluationService")
        evaluationTemplate = evaluationService.getEvaluationTemplateById(evaluationTemplateId)
        templateFieldList= evaluationService.getEvaluationTemplateFields(evaluationTemplateId)
        request.setAttribute("templateFieldList",templateFieldList)
        if request.getMethod() =="POST":
            templateName = self.params.safeGetStringParam("templateName")
            if templateName == None or templateName == "":
                self.addActionError(U"请输入模板名称。")
                return self.ERROR     
                           
            if evaluationTemplate == None:
                evaluationTemplate = EvaluationTemplate()
            evaluationTemplate.setEvaluationTemplateName(templateName)
            evaluationTemplate.setEnabled(True)
            evaluationService.saveOrUpdateEvaluationTemplate(evaluationTemplate)
            evaluationTemplateId=evaluationTemplate.evaluationTemplateId
            
            evaluationService.deleteEvaluationTemplateFields(evaluationTemplateId)
            fields = request.getParameterValues("fields")
            if fields!=None:
                for x in fields:
                    if x != "":
                        evaluationTemplateField=EvaluationTemplateFields()
                        evaluationTemplateField.setEvaluationTemplateId(evaluationTemplateId)
                        evaluationTemplateField.setFieldsCaption(x)
                        evaluationTemplateField.setFieldsName(x)
                        evaluationService.saveOrUpdateEvaluationTemplateField(evaluationTemplateField)
            self.addActionLink(u"返回列表", "evaluation_template.py")
            return self.SUCCESS
        if evaluationTemplate != None:
            request.setAttribute("evaluationTemplate",evaluationTemplate)
        return "/WEB-INF/ftl/evaluation/evaluation_template_edit.ftl"
        
        