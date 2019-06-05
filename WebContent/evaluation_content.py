from evaluation_query import *
from cn.edustar.jitar.util import ParamUtil
from java.text import SimpleDateFormat
from base_action import *
from com.alibaba.fastjson import JSONObject
from cn.edustar.jitar.pojos import EvaluationPlan
from cn.edustar.jitar.pojos import EvaluationContent
from java.util import Date

class evaluation_content(ActionExecutor, SubjectMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
        self.video_svc = __spring__.getBean("videoService")
        self.res_svc = __spring__.getBean("resourceService")
        self.evaluationService = __spring__.getBean("evaluationService")
        self.finish=0
        
    def execute(self):
        evaluationPlanId = self.params.getIntParam("evaluationPlanId")
        if evaluationPlanId==0:
            self.addActionError(u"缺少评课Id。")
            return self.ERROR               
        evaluationPlan=self.evaluationService.getEvaluationPlanById(evaluationPlanId)
        if evaluationPlan==None:
            self.addActionError(u"没有找到该评课。")
            return self.ERROR  
        
        #判断是否过期,过期的按照完成状态来显示
        #print "evaluationPlanId="+str(evaluationPlanId)
        
        beginDate = evaluationPlan.startDate
        endDate = evaluationPlan.endDate
        nowDate = Date()
        if beginDate.compareTo(nowDate)>-1 or endDate.compareTo(nowDate) < 1:
            self.finish=1
        request.setAttribute("finish", self.finish)
             
        if self.finish==0:        
            if self.loginUser == None:
                errDesc = u"请先<a href='login.jsp'>登录</a>，然后才能进行评课"
                response.getWriter().write(errDesc)
                return 
        
        request.setAttribute("evaluationPlan", evaluationPlan)    
        
        plantemplate_list =  self.evaluationService.getEvaluationTemplates(evaluationPlanId)
        request.setAttribute("plantemplate_list", plantemplate_list)
        
        video_list=  self.evaluationService.getVideosAuditState(evaluationPlanId)
        resource_list=  self.evaluationService.getResourcesAuditState(evaluationPlanId)
        request.setAttribute("video_list", video_list)
        request.setAttribute("resource_list", resource_list)
        
        
        metaSubjectId=evaluationPlan.metaSubjectId
        metaGradeId=evaluationPlan.metaGradeId
        self.subject = self._get_subj_svc().getSubjectByMetaData(metaSubjectId,metaGradeId)
        self.grade = self._get_subj_svc().getGrade(metaGradeId)
        if self.subject != None:
            msubjName=self.subject.subjectName
        if self.grade != None: 
            gradeName=self.grade.gradeName
        request.setAttribute("msubjName", msubjName)
        request.setAttribute("gradeName", gradeName)   
        
        #print "request.getMethod()="+request.getMethod()
         
        if request.getMethod() == "POST":
            templateId = self.params.getIntParam("tempId")
            print "templateId="+str(templateId)
            field_list=self.evaluationService.getEvaluationTemplateFields(templateId)
            content = ""
            for f in field_list: 
                fname = self.params.safeGetStringParam("fieldname" + str(f.fieldsId))                
                fconntent = self.params.safeGetStringParam("fieldcontent" + str(f.fieldsId))
                if fname != "":
                    content += "{" + JSONObject.toString(fname, fconntent) + "},"
            if content != "":
                if content.endswith(","):
                    content = content[0:len(content)-1]
                content = "[" + content + "]"
            #保存评课内容
            evaluationContent=EvaluationContent()
            evaluationContent.setPublishUserId(self.loginUser.userId)
            evaluationContent.setPublishUserName(self.loginUser.trueName)
            evaluationContent.setEvaluationPlanId(evaluationPlanId)
            evaluationContent.setEvaluationTemplateId(templateId)
            evaluationContent.setPublishContent(content)
            self.evaluationService.saveOrUpdateEvaluationContent(evaluationContent)

        content_list=self.evaluationService.getEvaluationContents(evaluationPlanId)
        request.setAttribute("content_list", content_list)
        return "/WEB-INF/ftl/evaluation/evaluation_content.ftl"
            
