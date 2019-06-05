package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.data.DataTableField;
import cn.edustar.jitar.dao.EvaluationDao;
import cn.edustar.jitar.pojos.EvaluationContent;
import cn.edustar.jitar.pojos.EvaluationPlan;
import cn.edustar.jitar.pojos.EvaluationPlanTemplate;
import cn.edustar.jitar.pojos.EvaluationResource;
import cn.edustar.jitar.pojos.EvaluationTemplate;
import cn.edustar.jitar.pojos.EvaluationTemplateFields;
import cn.edustar.jitar.pojos.EvaluationVideo;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.Video;
import cn.edustar.jitar.service.EvaluationService;

public class EvaluationServiceImpl implements EvaluationService {

	private EvaluationDao evaluationDao;

	public void saveOrUpdateEvaluationPlan(EvaluationPlan evaluationPlan) {
		this.evaluationDao.saveOrUpdateEvaluationPlan(evaluationPlan);

	}
	public int saveOrUpdateEvaluationPlanEx(EvaluationPlan evaluationPlan)
	{
		return this.evaluationDao.saveOrUpdateEvaluationPlanEx(evaluationPlan);
	}
	
	public EvaluationPlan getEvaluationPlanById(int evaluationPlanId) {

		return this.evaluationDao.getEvaluationPlanById(evaluationPlanId);
	}

	public void saveOrUpdateEvaluationTemplate(EvaluationTemplate evaluationTemplate)
	{
		this.evaluationDao.saveOrUpdateEvaluationTemplate(evaluationTemplate);
	}
	
	public EvaluationTemplate getEvaluationTemplateById(int evaluationTemplateId)
	{
		return this.evaluationDao.getEvaluationTemplateById(evaluationTemplateId);
	}
	
	public void saveOrUpdateEvaluationContent(EvaluationContent evaluationContent)
	{
		this.evaluationDao.saveOrUpdateEvaluationContent(evaluationContent);
	}
	
	public EvaluationContent getEvaluationContentById(int evaluationContentId)
	{
		return this.evaluationDao.getEvaluationContentById(evaluationContentId);
	}
	public List<EvaluationContent> getEvaluationContents(int evaluationPlanId)
	{
		return this.evaluationDao.getEvaluationContents(evaluationPlanId);
	}
	public void deleteEvaluationPlanById(int evaluationPlanId)
	{
		this.evaluationDao.deleteEvaluationPlanById(evaluationPlanId);
	}
	public void deleteEvaluationPlan(EvaluationPlan evaluationPlan)
	{
		this.evaluationDao.saveOrUpdateEvaluationPlan(evaluationPlan);
	}
	public void deleteEvaluationContentByEvaluationPlanId(int evaluationPlanId)
	{
		this.evaluationDao.deleteEvaluationContentByEvaluationPlanId(evaluationPlanId);
	}
	public void deleteEvaluationContent(EvaluationContent evaluationContent)
	{
		this.evaluationDao.deleteEvaluationContent(evaluationContent);
	}
	public void deleteEvaluationTemplateById(int evaluationTemplateId){
		this.evaluationDao.deleteEvaluationTemplateById(evaluationTemplateId);
	}
	public void deleteEvaluationTemplate(EvaluationTemplate evaluationTemplate)
	{
		this.evaluationDao.deleteEvaluationTemplate(evaluationTemplate);
	}
	
	public void setEvaluationDao(EvaluationDao evaluationDao) {
		this.evaluationDao = evaluationDao;
	}
	/**
	 * 删除某个模板的全部字段定义
	 * @param evaluationTemplateId
	 */
	public void deleteEvaluationTemplateFields(int evaluationTemplateId){
		this.evaluationDao.deleteEvaluationTemplateFields(evaluationTemplateId);
	}
	/**
	 * 删除某个模板的字段定义
	 * @param fieldsId
	 */
	public void deleteEvaluationTemplateField(int fieldsId){
		this.evaluationDao.deleteEvaluationTemplateField(fieldsId);
	}
	/**
	 * 增加或修改
	 * @param evaluationTemplateField
	 */
	public void saveOrUpdateEvaluationTemplateField(EvaluationTemplateFields evaluationTemplateField){
		this.evaluationDao.saveOrUpdateEvaluationTemplateField(evaluationTemplateField);
	}
	/**
	 * 得到某个模板的全部字段定义
	 * @param evaluationTemplateId
	 * @return
	 */
	public List<EvaluationTemplateFields> getEvaluationTemplateFields(int evaluationTemplateId){
		return this.evaluationDao.getEvaluationTemplateFields(evaluationTemplateId);
	}
	/**
	 * 得到可以用的模板
	 * @return
	 */
	public List<EvaluationTemplate> getTemplates()
	{
		return this.evaluationDao.getTemplates();
	}
	/**
	 * 得到某个评课的模板 
	 * @param evaluationPlanId
	 * @return
	 */
	public List<EvaluationPlanTemplate> getEvaluationPlanTemplates(int evaluationPlanId)
	{
		return this.evaluationDao.getEvaluationPlanTemplates(evaluationPlanId);
	}
	public List<EvaluationTemplate> getEvaluationTemplates(int evaluationPlanId)
	{
		return this.evaluationDao.getEvaluationTemplates(evaluationPlanId);
	}
	/**
	 * 增加评课使用的模板 
	 * @param evaluationPlanId
	 * @param templateId
	 */
	public void insertEvaluationPlanTemplates(int evaluationPlanId,int templateId)
	{
		this.evaluationDao.insertEvaluationPlanTemplates(evaluationPlanId, templateId);
	}
	
	/**
	 * 删除评课使用的模板 
	 * @param evaluationPlanId
	 * @param templateId
	 */
	public void removeEvaluationPlanTemplates(int evaluationPlanId,int templateId)
	{
		this.evaluationDao.removeEvaluationPlanTemplates(evaluationPlanId, templateId);
	}
	public void removeEvaluationPlanTemplates(int evaluationPlanId)
	{
		this.evaluationDao.removeEvaluationPlanTemplates(evaluationPlanId);
	}	
	/**
	 * 增加视频资源
	 * @param evaluationPlanId
	 * @param videoId
	 * @param videoTitle
	 */
	public void insertVideoToEvaluation(int evaluationPlanId,int videoId,String videoTitle,String flvThumbNailHref)
	{
		this.evaluationDao.insertVideoToEvaluation(evaluationPlanId, videoId, videoTitle,flvThumbNailHref);
	}
	/**
	 * 删除视频资源
	 * @param evaluationPlanId
	 * @param videoId
	 */
	public void removeVideoFromEvaluation(int evaluationPlanId,int videoId)
	{
		this.evaluationDao.removeVideoFromEvaluation(evaluationPlanId, videoId);
	}
	public void removeVideosFromEvaluation(int evaluationPlanId)
	{
		this.evaluationDao.removeVideosFromEvaluation(evaluationPlanId);
	}
	/**
	 * 得到评课的视频列表 
	 * @param evaluationPlanId
	 * @return
	 */
	public List<Video> getVideos(int evaluationPlanId)
	{
		return this.evaluationDao.getVideos(evaluationPlanId);
	}
	/**
	 * 得到审核过得视频列表
	 * @param evaluationPlanId
	 * @return
	 */
	public List<EvaluationVideo> getVideosAuditState(int evaluationPlanId)
	{
		return this.evaluationDao.getVideosAuditState(evaluationPlanId);
	}
	/**
	 * 增加资源
	 * @param evaluationPlanId
	 * @param resourceId
	 * @param resourceTitle
	 */
	public void insertResourceToEvaluation(int evaluationPlanId,int resourceId,String resourceTitle,String resourceHref)
	{
		this.evaluationDao.insertResourceToEvaluation(evaluationPlanId, resourceId, resourceTitle,resourceHref);
	}
	/**
	 * 删除资源
	 * @param evaluationPlanId
	 * @param resourceId
	 */
	public void removeResourceFromEvaluation(int evaluationPlanId,int resourceId)
	{
		this.evaluationDao.removeResourceFromEvaluation(evaluationPlanId, resourceId);
	}
	public void removeResourcesFromEvaluation(int evaluationPlanId)
	{
		this.evaluationDao.removeResourcesFromEvaluation(evaluationPlanId);
	}
	/**
	 * 得到资源评课的资源列表
	 * @param evaluationPlanId
	 * @return
	 */
	public List<Resource> getResources(int evaluationPlanId){
		return this.evaluationDao.getResources(evaluationPlanId);
	}
	public List<EvaluationResource> getResourcesAuditState(int evaluationPlanId){
		return this.evaluationDao.getResourcesAuditState(evaluationPlanId);
	}	
	
}
