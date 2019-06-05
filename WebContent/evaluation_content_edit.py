from evaluation_query import *
from cn.edustar.jitar.util import ParamUtil
from base_action import *
from com.alibaba.fastjson import JSONObject
from cn.edustar.jitar.pojos import EvaluationContent
from java.util import Date

class evaluation_content_edit(ActionExecutor, SubjectMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
        
    def execute(self):
        evaluationService = __spring__.getBean("evaluationService")
        evaluationPlanId = self.params.safeGetIntParam("evaluationPlanId")
        evaluationPlan = evaluationService.getEvaluationPlanById(evaluationPlanId)
        if evaluationPlan == None:
            self.addActionError(u"不能加载评课活动，无法进行评课。")
            return self.ERROR
        if evaluationPlan.enabled == False:
            self.addActionError(u"该评课活动没有启用，无法进行评课。")
            return self.ERROR
        #判断是否过期
        beginDate = evaluationPlan.startDate
        endDate = evaluationPlan.endDate
        nowDate = Date()
        if beginDate.compareTo(nowDate)>-1 or endDate.compareTo(nowDate) < 1:
            self.addActionError(u"该评课活动日期已过，无法进行评课。")
            return self.ERROR        
            
        if request.getMethod() == "POST":
            fieldcount = self.params.safeGetIntValues("fieldcount")
            title = self.params.safeGetStringParam("titleName")
            teacherName = self.params.safeGetStringParam("teacherName")
            metaGradeId = self.params.getIntParamZeroAsNull("gradeId")
            metaSubjectId = self.params.getIntParamZeroAsNull("subjectId")
            
            if title == "" or teacherName == "":
                self.addActionError(u"请输入评课名称和授课人。")
                return self.ERROR
            
            content = ""            
            for num in fieldcount:
                fname = self.params.safeGetStringParam("fieldname" + str(num))                
                fconntent = self.params.safeGetStringParam("fieldcontent" + str(num))
                if fname != "":
                    content += "{" + JSONObject.toString(fname, fconntent) + "},"
            if content != "":
                if content.endswith(","):
                    content = content[0:len(content)-1]
                content = "[" + content + "]"
            
            templateId = self.params.safeGetIntParam("templateId")
            evaluationContent = EvaluationContent()
            evaluationContent.setTitle(title)
            evaluationContent.setCourseTeacherName(teacherName)
            evaluationContent.setPublishUserId(self.loginUser.userId)
            evaluationContent.setPublishUserName(self.loginUser.trueName)
            evaluationContent.setMetaSubjectId(metaSubjectId)
            evaluationContent.setMetaGradeId(metaGradeId)
            evaluationContent.setEvaluationPlanId(evaluationPlanId)
            evaluationContent.setEvaluationTemplateId(templateId)
            evaluationContent.setPublishContent(content)
            evaluationService.saveOrUpdateEvaluationContent(evaluationContent)
            response.sendRedirect("evaluations.py")
                                    
        if self.params.existParam("templateId"):
            templateId = self.params.safeGetIntParam("templateId")            
            template = evaluationService.getEvaluationTemplateById(templateId)
            evaluationPlan = evaluationService.getEvaluationPlanById(evaluationPlanId)
            if evaluationPlan == None:
                self.addActionError(u"不能加载评课活动，无法进行评课。")
                return self.ERROR
            if template == None:
                self.addActionError(u"不能加载评课模板，无法进行评课。")
                return self.ERROR
            self.putSubjectList()
            self.putGradeList()
            request.setAttribute("template", template)
            request.setAttribute("evaluationPlan", evaluationPlan)
            return "/WEB-INF/ftl/evaluation/evaluation_content_edit.ftl"
            
        else:
            qry = EvaluationTemplateQuery("et.evaluationTemplateId, et.evaluationTemplateName")
            qry.enabled = True
            template_list = qry.query_map(qry.count())
            if len(template_list) < 1:
                self.addActionError(u"当前没有模板可供选择，无法进行评课。")
                return self.ERROR
            elif len(template_list) == 1:
                response.sendRedirect("evaluation_content_edit.py?evaluationPlanId=" + str(evaluationPlanId) + "&templateId=" + str(template_list[0]["evaluationTemplateId"]))
            else:
                request.setAttribute("template_list", template_list)
                request.setAttribute("evaluationPlanId", evaluationPlanId)
                return "/WEB-INF/ftl/evaluation/evaluation_content_edit_select_template.ftl"
