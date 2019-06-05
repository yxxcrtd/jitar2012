from evaluation_query import *
from cn.edustar.jitar.util import ParamUtil
from java.text import SimpleDateFormat
from base_action import *
from cn.edustar.jitar.pojos import EvaluationPlan
from java.util import Date

class evaluation_edit(ActionExecutor, SubjectMixiner):
    def __init__(self):
        self.params = ParamUtil(request)
        self.video_svc = __spring__.getBean("videoService")
        self.res_svc = __spring__.getBean("resourceService")
        self.evaluationService = __spring__.getBean("evaluationService")
        
    def execute(self):
        if self.loginUser == None:
            errDesc = u"请先<a href='login.jsp'>登录</a>，然后才能操作"
            response.getWriter().write(errDesc)
            return 
        #要编辑的ID    
        evaluationPlanId = self.params.getIntParam("evaluationPlanId")
        if evaluationPlanId==0:
            self.addActionError(u"缺少评课Id。")
            return self.ERROR               
        evaluationPlan=self.evaluationService.getEvaluationPlanById(evaluationPlanId)
        if evaluationPlan==None:
            self.addActionError(u"没有找到该评课。")
            return self.ERROR  
        
        request.setAttribute("evaluationPlan", evaluationPlan)    
        
        plantemplate_list =  self.evaluationService.getEvaluationPlanTemplates(evaluationPlanId)
        request.setAttribute("plantemplate_list", plantemplate_list)
        
        video_list=  self.evaluationService.getVideos(evaluationPlanId)
        resource_list=  self.evaluationService.getResources(evaluationPlanId)
        request.setAttribute("video_list", video_list)
        request.setAttribute("resource_list", resource_list)
        
        # 学段分类.
        self.get_grade_list()
        # 学科分类
        self.get_subject_list()            
        
        if request.getMethod() == "POST":
            teachDate = self.params.safeGetStringParam("teachDate")
            startDate = self.params.safeGetStringParam("startDate")
            endDate = self.params.safeGetStringParam("endDate")
            title = self.params.safeGetStringParam("titleName")
            metaGradeId = self.params.getIntParamZeroAsNull("gradeId")
            metaSubjectId = self.params.getIntParamZeroAsNull("subjectId")
            resIds = self.params.safeGetStringParam("resId")
            videoIds = self.params.safeGetStringParam("videoId")
                
            templateIds = self.params.safeGetStringParam("template")
            if title == "" :
                self.addActionError(u"请输入评课名称。")
                return self.ERROR
            try:
                td = SimpleDateFormat("yyyy-MM-dd").parse(teachDate)
            except:
                self.addActionError(U"输入的授课日期格式不正确，应当是: '年年年年-月月-日日' 格式")
                return self.ERROR
            try:
                sd = SimpleDateFormat("yyyy-MM-dd").parse(startDate)
            except:
                self.addActionError(U"输入的开始日期格式不正确，应当是: '年年年年-月月-日日' 格式")
                return self.ERROR
            try:
                ed = SimpleDateFormat("yyyy-MM-dd").parse(endDate)
            except:
                self.addActionError(U"输入的结束日期格式不正确，应当是: '年年年年-月月-日日' 格式")
                return self.ERROR
                
            #保存评课
            
            evaluationPlan.setEvaluationCaption(title)
            evaluationPlan.setMetaSubjectId(metaSubjectId)
            evaluationPlan.setMetaGradeId(metaGradeId)
            evaluationPlan.setStartDate(sd)
            evaluationPlan.setEndDate(ed)
            evaluationPlan.setEnabled(True)
            evaluationPlan.setTeachDate(td)
            evaluationPlan.setCreaterId(self.loginUser.userId)
            evaluationPlan.setCreaterName(self.loginUser.trueName)
            
            evaluationPlanId=self.evaluationService.saveOrUpdateEvaluationPlanEx(evaluationPlan)
            
            #保存模板
            self.evaluationService.removeEvaluationPlanTemplates(evaluationPlanId)
            if templateIds!="":
                arraytemplateIds=templateIds.split(',')
                for templateId in arraytemplateIds:
                       self.evaluationService.insertEvaluationPlanTemplates(evaluationPlanId,int(templateId))
            response.sendRedirect("evaluations.action?cmd=content&evaluationPlanId="+str(evaluationPlanId))
            
            #保存视频
            #print "videoIds="+videoIds
            self.evaluationService.removeVideosFromEvaluation(evaluationPlanId)
            if videoIds!=None and videoIds!="":
                arrarvideoId=videoIds.split(',')
                #print "arrarvideoId.count=",arrarvideoId.count
                for videoId in arrarvideoId:
                    #print "videoId=",videoId
                    video= self.video_svc.findById(int(videoId))
                    self.evaluationService.insertVideoToEvaluation(evaluationPlanId,int(videoId),video.title,video.flvThumbNailHref)
            #保存资源
            #print "resIds="+resIds
            self.evaluationService.removeResourcesFromEvaluation(evaluationPlanId)
            if resIds!=None and resIds!="":
                arrarResId=resIds.split(',')
                #print "arrarResId.count=",arrarResId.count
                for resId in arrarResId:
                    #print "resId=",resId
                    resource= self.res_svc.getResource(int(resId))
                    if resource!=None:
                        self.evaluationService.insertResourceToEvaluation(evaluationPlanId,int(resId),resource.title,resource.href)
                    else:
                        self.addActionError(u"没有找到资源 id= "+resId +"  resIds="+resIds+"  videoIds="+videoIds)
                        return self.ERROR                                        
            #成功返回
            response.sendRedirect("evaluations.py")

        template_list=self.evaluationService.getTemplates()
        if len(template_list) < 1:
            self.addActionError(u"当前没有模板可供选择，无法进行评课。")
            return self.ERROR
        else:
            request.setAttribute("template_list", template_list)
            return "/WEB-INF/ftl/evaluation/evaluation_edit.ftl"
    #学段
    def get_grade_list(self):
        request.setAttribute("gradeId", self.params.getIntParamZeroAsNull("gradeId"))
        self.putGradeList()
    # 学科列表.
    def get_subject_list(self):
        self.putSubjectList() 