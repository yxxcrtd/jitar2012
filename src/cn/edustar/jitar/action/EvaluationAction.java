package cn.edustar.jitar.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarRequestContext;
import cn.edustar.jitar.pojos.EvaluationPlan;
import cn.edustar.jitar.pojos.EvaluationTemplate;
import cn.edustar.jitar.pojos.EvaluationTemplateFields;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.EvaluationPlanQuery;
import cn.edustar.jitar.service.EvaluationService;
import cn.edustar.jitar.service.EvaluationTemplateQuery;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 评课的后台管理
 * 
 * /manage/evaluation/evaluation_plan.py
 * /manage/evaluation/evaluation_template.py
 * /manage/evaluation/evaluation_plan_edit.py
 * 
 * @author baimindong
 *
 */
public class EvaluationAction extends ManageBaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7575026872958841507L;
	private SubjectService subjectService;
	private EvaluationService evaluationService;
	
	@Override
	protected String execute(String cmd) throws Exception {
		if(!this.isUserLogined()){
			addActionError("请先登录！");
			addActionLink("登录", getSiteUrl()+"login.jsp","_self");
			return ERROR;
		}
		
        if (canManage() == false){
            addActionError("需要系统内容管理员进行管理。");
            return ERROR;		
        }
        
        String type = param_util.getStringParam("listtype");
        if(type == null || type.length() == 0){type = "";}
        if(cmd == null || cmd.length() == 0){cmd = "list";}
        if(cmd.equals("delete") && type.equals("plan")){
        	deletePlan();
        	listPlan();
        	return "plan";
        }else if(cmd.equals("enabled") && type.equals("plan")){
        	enabledPlan(true);
        	listPlan();
        	return "plan";
        }else if(cmd.equals("disabled") && type.equals("plan")){
        	enabledPlan(false);
        	listPlan();
        	return "plan";
        }else if(cmd.equals("list") && type.equals("plan")){
        	listPlan();
        	return "plan";
        }else if(cmd.equals("listplan")){
        	listPlan();
        	return "plan";
        }else if(cmd.equals("delete") && type.equals("template")){
        	deleteTemplate();
        	listTemplate();
        	return "template";
        }else if(cmd.equals("enabled") && type.equals("template")){
        	enabledTemplate(true);
        	listTemplate();
        	return "template";
        }else if(cmd.equals("disabled") && type.equals("template")){
        	enabledTemplate(false);
        	listTemplate();
        	return "template";
        }else if(cmd.equals("list") && type.equals("template")){
        	listTemplate();
        	return "template";
        }else if(cmd.equals("listtemplate")){
        	listTemplate();
        	return "template";
        }else if(cmd.equals("edit") && type.equals("template")){
        	editTemplate();
        	return "edittemplate";
        }else if(cmd.equals("save") && type.equals("template")){
        	return saveTemplate();
        }else if(cmd.equals("edit") && type.equals("plan")){
        	editPlan();
        	return "edit";
        }else if(cmd.equals("save") && type.equals("plan")){
        	return savePlan();
        }else{
        	addActionError("未知的cmd参数:"+cmd);
        	return ERROR;
        }
	}
	
	private String saveTemplate(){
        Integer evaluationTemplateId = param_util.safeGetIntParam("evaluationTemplateId");        
        EvaluationTemplate evaluationTemplate = evaluationService.getEvaluationTemplateById(evaluationTemplateId);
        List<EvaluationTemplateFields> templateFieldList= evaluationService.getEvaluationTemplateFields(evaluationTemplateId);
        request.setAttribute("templateFieldList",templateFieldList);
        String templateName = param_util.safeGetStringParam("templateName");
        if(templateName == null || templateName.length() == 0 ){
            addActionError("请输入模板名称。");
            return ERROR;     
        }
        if(evaluationTemplate == null){
            evaluationTemplate = new EvaluationTemplate();
        }
        evaluationTemplate.setEvaluationTemplateName(templateName);
        evaluationTemplate.setEnabled(true);
        evaluationService.saveOrUpdateEvaluationTemplate(evaluationTemplate);
        evaluationTemplateId=evaluationTemplate.getEvaluationTemplateId();
        evaluationService.deleteEvaluationTemplateFields(evaluationTemplateId);
        String[] fields = request.getParameterValues("fields");
        if(fields!=null){
        	for(int i=0;i<fields.length;i++){
        		String x = fields[i];
                if(x.length() > 0){
                	EvaluationTemplateFields evaluationTemplateField=new EvaluationTemplateFields();
                    evaluationTemplateField.setEvaluationTemplateId(evaluationTemplateId);
                    evaluationTemplateField.setFieldsCaption(x);
                    evaluationTemplateField.setFieldsName(x);
                    evaluationService.saveOrUpdateEvaluationTemplateField(evaluationTemplateField);
                }
        	}
        }
        addActionLink("返回列表", "evaluation/evaluation_template.py");
        return SUCCESS;
	}
	
	private void editTemplate(){
        Integer evaluationTemplateId = param_util.safeGetIntParam("evaluationTemplateId");        
        EvaluationTemplate evaluationTemplate = evaluationService.getEvaluationTemplateById(evaluationTemplateId);
        List<EvaluationTemplateFields> templateFieldList= evaluationService.getEvaluationTemplateFields(evaluationTemplateId);
        request.setAttribute("templateFieldList",templateFieldList);
        if(evaluationTemplate != null){
            request.setAttribute("evaluationTemplate",evaluationTemplate);
        }
	}
	private String savePlan(){
        Integer evaluationPlanId = param_util.safeGetIntParam("evaluationPlanId");
        Calendar today = Calendar.getInstance();
        //Date todayDate = calendar.getTime();
        request.setAttribute("currentYear", today.get(Calendar.YEAR));
        request.setAttribute("currentMonth", today.get(Calendar.MONTH));
        EvaluationPlan evaluationPlan = evaluationService.getEvaluationPlanById(evaluationPlanId);
		
        Integer evaluationYear = param_util.safeGetIntParam("year");
        Integer evaluationSemester = param_util.safeGetIntParam("semester");
        Integer evaluationTimes = param_util.safeGetIntParam("times");
        String startDate = param_util.safeGetStringParam("startDate");
        String endDate = param_util.safeGetStringParam("endDate");
        Integer enabled = param_util.safeGetIntParam("enabled");
        Integer gradeId = param_util.getIntParamZeroAsNull("gradeId");
        Integer subjectId = param_util.getIntParamZeroAsNull("subjectId");
        Integer userCount = param_util.safeGetIntParam("userCount");
        Integer startTime = param_util.safeGetIntParam("startTime");
        Integer endTime = param_util.safeGetIntParam("endTime");
        //# 规范数据
        String st = "",et="";
        if(evaluationSemester != 0){evaluationSemester = 1;}
        if (startTime < 0 || startTime > 23){
            addActionError("开始时间超过了范围。");
            return ERROR;
        }
        if(endTime < 0 || endTime > 23){
            addActionError("结束时间超过了范围。");
            return ERROR;
        }
        if(startTime < 10){
            st = "0" + startTime;
        }else{
            st = "" + startTime;
        }
        
        if(endTime < 10){
            et = "0" + endTime;
        }else{
            et = "" + endTime;
        }
        Date sd=null,ed=null;    
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            sd = sdf.parse(startDate + " " + st + ":00:00");
        }catch(Exception ex){
            addActionError("输入的开始日期格式不正确，应当是: '年年年年-月月-日日' 格式");
            return ERROR;
        }
        try{
            ed = sdf.parse(endDate + " " + et + ":00:00");
        }catch(Exception ex){
            addActionError("输入的结束日期格式不正确，应当是: '年年年年-月月-日日' 格式");
            return ERROR;
        }
        
        if(evaluationPlan == null){
            evaluationPlan = new EvaluationPlan();
        }
        //evaluationPlan.setEvaluationYear(evaluationYear);
        //evaluationPlan.setEvaluationSemester(evaluationSemester);            
        //evaluationPlan.setEvaluationTimes(evaluationTimes);
        evaluationPlan.setMetaSubjectId(subjectId);
        evaluationPlan.setMetaGradeId(gradeId);
        evaluationPlan.setStartDate(sd);
        evaluationPlan.setEndDate(ed);
        //evaluationPlan.setUserCount(userCount);
        //evaluationPlan.setEnabled(enabled);
        
        evaluationService.saveOrUpdateEvaluationPlan(evaluationPlan);
        return SUCCESS;
	}
	
	private void editPlan(){
        Integer evaluationPlanId = param_util.safeGetIntParam("evaluationPlanId");
        Calendar today = Calendar.getInstance();
        //Date todayDate = calendar.getTime();
        request.setAttribute("currentYear", today.get(Calendar.YEAR));
        request.setAttribute("currentMonth", today.get(Calendar.MONTH));
        EvaluationPlan evaluationPlan = evaluationService.getEvaluationPlanById(evaluationPlanId);
        if(evaluationPlan != null){
            request.setAttribute("evaluationPlan", evaluationPlan);
        }
        putSubjectList();
        putGradeList();
        
	}
	
    /**
     * 把元学科信息添加到请求对象中。
     */
    public void putSubjectList() {
        request.setAttribute("subject_list", subjectService.getMetaSubjectList());
    }

    /**
     * 把元学段信息添加到请求对象中。
     */
    public void putGradeList() {
        request.setAttribute("grade_list", this.subjectService.getGradeList());
    }

    
	private void listTemplate(){
		EvaluationTemplateQuery qry = new EvaluationTemplateQuery("et.evaluationTemplateId, et.evaluationTemplateName, et.enabled");
        List template_list = qry.query_map(qry.count());
        request.setAttribute("template_list",template_list);
	}
	
	private void enabledTemplate(boolean tf){
		List<Integer> guids = param_util.safeGetIntValues("guid");
        for(Integer g : guids){
        	EvaluationTemplate evaluationTemplate = evaluationService.getEvaluationTemplateById(g);
            if(evaluationTemplate != null){
                evaluationTemplate.setEnabled(tf);
                evaluationService.saveOrUpdateEvaluationTemplate(evaluationTemplate);
            }
        }
	}
	
	private void deleteTemplate(){
		List<Integer> guids = param_util.safeGetIntValues("guid");
        for(Integer g : guids){
            evaluationService.deleteEvaluationTemplateById(g);
            evaluationService.deleteEvaluationTemplateFields(g);
        }
   }
	private void listPlan(){
		EvaluationPlanQuery qry = new EvaluationPlanQuery("ev.evaluationPlanId, ev.evaluationCaption, ev.teacherId,ev.teacherName, ev.createrId,ev.createrName,ev.metaSubjectId, ev.metaGradeId, subj.msubjName, grad.gradeName,ev.startDate, ev.endDate, ev.enabled, ev.teachDate");
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("评课活动", "个");
		pager.setPageSize(20);
        pager.setTotalRows(qry.count());
        List evaluation_list = (List)qry.query_map(pager);
        request.setAttribute("pager", pager);
        request.setAttribute("evaluation_list", evaluation_list);		
	}
	
	private void enabledPlan(boolean tf){
		List<Integer> guids = param_util.safeGetIntValues("guid");
        for(Integer g : guids){
        	EvaluationPlan evaluationPlan = evaluationService.getEvaluationPlanById(g);
            if(evaluationPlan != null){
                evaluationPlan.setEnabled(tf);
                evaluationService.saveOrUpdateEvaluationPlan(evaluationPlan);
            }
        }
	}
	private void deletePlan(){
        List<Integer> guids = param_util.safeGetIntValues("guid");
        for(Integer g : guids){
            evaluationService.deleteEvaluationContentByEvaluationPlanId(g);
            evaluationService.deleteEvaluationPlanById(g);
            evaluationService.removeResourcesFromEvaluation(g);
            evaluationService.removeVideosFromEvaluation(g);
            evaluationService.removeEvaluationPlanTemplates(g);
        }
	}
    public boolean canManage(){
        if(getLoginUser() == null){
            return false;
        }
        AccessControlService accessControlService =(AccessControlService) JitarRequestContext.getRequestContext().getJitarContext().getSpringContext().getBean("accessControlService");
        return accessControlService.isSystemContentAdmin(getLoginUser()) || accessControlService.isSystemAdmin(getLoginUser());
    }
    
    public String getSiteUrl(){
        return CommonUtil.getSiteUrl(request);
    }	
	public SubjectService getSubjectService() {
		return subjectService;
	}
	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}
	public EvaluationService getEvaluationService() {
		return evaluationService;
	}
	public void setEvaluationService(EvaluationService evaluationService) {
		this.evaluationService = evaluationService;
	}

}
