package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.jitar.pojos.EvaluationContent;
import cn.edustar.jitar.pojos.EvaluationPlan;
import cn.edustar.jitar.pojos.EvaluationPlanTemplate;
import cn.edustar.jitar.pojos.EvaluationTemplate;
import cn.edustar.jitar.pojos.EvaluationResource;
import cn.edustar.jitar.pojos.EvaluationVideo;
import cn.edustar.jitar.pojos.EvaluationTemplateFields;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.Video;
import cn.edustar.data.DataTableField;

public interface EvaluationDao {
	
	public void saveOrUpdateEvaluationPlan(EvaluationPlan evaluationPlan);
	public int saveOrUpdateEvaluationPlanEx(EvaluationPlan evaluationPlan);
	
	public EvaluationPlan getEvaluationPlanById(int evaluationPlanId);
	
	public void saveOrUpdateEvaluationTemplate(EvaluationTemplate evaluationTemplate);
	
	public EvaluationTemplate getEvaluationTemplateById(int evaluationTemplateId);
	
	public void saveOrUpdateEvaluationContent(EvaluationContent evaluationContent);
	
	public EvaluationContent getEvaluationContentById(int evaluationContentId);
	public List<EvaluationContent> getEvaluationContents(int evaluationPlanId);
	
	public void deleteEvaluationPlanById(int evaluationPlanId);
	public void deleteEvaluationPlan(EvaluationPlan evaluationPlan);
	public void deleteEvaluationContentByEvaluationPlanId(int evaluationPlanId);
	public void deleteEvaluationContent(EvaluationContent evaluationContent);
	
	public void deleteEvaluationTemplateById(int evaluationTemplateId);
	public void deleteEvaluationTemplate(EvaluationTemplate evaluationTemplate);
	
	/**
	 * 删除某个模板的全部字段定义
	 * @param evaluationTemplateId
	 */
	public void deleteEvaluationTemplateFields(int evaluationTemplateId);
	/**
	 * 删除某个模板的字段定义
	 * @param fieldsId
	 */
	public void deleteEvaluationTemplateField(int fieldsId);
	/**
	 * 增加或修改
	 * @param evaluationTemplateField
	 */
	public void saveOrUpdateEvaluationTemplateField(EvaluationTemplateFields evaluationTemplateField);
	/**
	 * 得到某个模板的全部字段定义
	 * @param evaluationTemplateId
	 * @return
	 */
	public List<EvaluationTemplateFields> getEvaluationTemplateFields(int evaluationTemplateId);	
	/**
	 * 得到可以用的模板
	 * @return
	 */
	public List<EvaluationTemplate> getTemplates();
	/**
	 * 得到某个评课的模板 
	 * @param evaluationPlanId
	 * @return
	 */
	public List<EvaluationPlanTemplate> getEvaluationPlanTemplates(int evaluationPlanId);
	public List<EvaluationTemplate> getEvaluationTemplates(int evaluationPlanId);
	
	/**
	 * 增加评课使用的模板 
	 * @param evaluationPlanId
	 * @param templateId
	 */
	public void insertEvaluationPlanTemplates(int evaluationPlanId,int templateId);
	
	/**
	 * 删除评课使用的模板 
	 * @param evaluationPlanId
	 * @param templateId
	 */
	public void removeEvaluationPlanTemplates(int evaluationPlanId,int templateId);
	public void removeEvaluationPlanTemplates(int evaluationPlanId);
	/**
	 * 增加视频资源
	 * @param evaluationPlanId
	 * @param videoId
	 * @param videoTitle
	 */
	public void insertVideoToEvaluation(int evaluationPlanId,int videoId,String videoTitle,String flvThumbNailHref);
	/**
	 * 删除视频资源
	 * @param evaluationPlanId
	 * @param videoId
	 */
	public void removeVideoFromEvaluation(int evaluationPlanId,int videoId);
	public void removeVideosFromEvaluation(int evaluationPlanId);
	/**
	 * 得到评课的视频列表 
	 * @param evaluationPlanId
	 * @return
	 */
	public List<Video> getVideos(int evaluationPlanId);
	/**
	 * 得到审核过得视频列表
	 * @param evaluationPlanId
	 * @return
	 */
	public List<EvaluationVideo> getVideosAuditState(int evaluationPlanId);	
	/**
	 * 增加资源
	 * @param evaluationPlanId
	 * @param resourceId
	 * @param resourceTitle
	 */
	public void insertResourceToEvaluation(int evaluationPlanId,int resourceId,String resourceTitle,String resourceHref);
	/**
	 * 删除资源
	 * @param evaluationPlanId
	 * @param resourceId
	 */
	public void removeResourceFromEvaluation(int evaluationPlanId,int resourceId);
	public void removeResourcesFromEvaluation(int evaluationPlanId);
	
	/**
	 * 得到资源评课的资源列表
	 * @param evaluationPlanId
	 * @return
	 */
	public List<Resource> getResources(int evaluationPlanId);
	/**
	 * 得到审核过得资源列表
	 * @param evaluationPlanId
	 * @return
	 */
	public List<EvaluationResource> getResourcesAuditState(int evaluationPlanId);
		
}
