package cn.edustar.jitar.dao.hibernate;

import java.util.Date;
import java.util.List;

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

public class EvaluationDaoHibernate extends BaseDaoHibernate implements
		EvaluationDao {

	public void saveOrUpdateEvaluationPlan(EvaluationPlan evaluationPlan) {
		this.getSession().saveOrUpdate(evaluationPlan);
	}
	
	public int saveOrUpdateEvaluationPlanEx(EvaluationPlan evaluationPlan) {
		this.getSession().saveOrUpdate(evaluationPlan);
		return evaluationPlan.getEvaluationPlanId();
	}
	
	public EvaluationPlan getEvaluationPlanById(int evaluationPlanId) {

		return (EvaluationPlan)this.getSession().get(EvaluationPlan.class, evaluationPlanId);
	}

	public void saveOrUpdateEvaluationTemplate(EvaluationTemplate evaluationTemplate)
	{
		this.getSession().saveOrUpdate(evaluationTemplate);
	}
	
	public EvaluationTemplate getEvaluationTemplateById(int evaluationTemplateId) {

		return (EvaluationTemplate)this.getSession().get(EvaluationTemplate.class, evaluationTemplateId);
	}
	
	public void saveOrUpdateEvaluationContent(EvaluationContent evaluationContent)
	{
		this.getSession().saveOrUpdate(evaluationContent);
	}
	
	public EvaluationContent getEvaluationContentById(int evaluationContentId)
	{
		return (EvaluationContent)this.getSession().get(EvaluationContent.class, evaluationContentId);
	}
	@SuppressWarnings("unchecked")
    public List<EvaluationContent> getEvaluationContents(int evaluationPlanId)
	{
		String queryString = " FROM EvaluationContent Where evaluationPlanId = :evaluationPlanId";
		List<EvaluationContent> list = (List<EvaluationContent>)this.getSession().createQuery(queryString).setInteger("evaluationPlanId", evaluationPlanId).list();
		return list;		
	}	
	public void deleteEvaluationPlanById(int evaluationPlanId)
	{
		String queryString = "DELETE FROM EvaluationPlan Where evaluationPlanId = ?";
		this.getSession().createQuery(queryString).setInteger(0, evaluationPlanId).executeUpdate();
	}
	public void deleteEvaluationPlan(EvaluationPlan evaluationPlan)
	{
		this.getSession().delete(evaluationPlan);
	}
	public void deleteEvaluationContentByEvaluationPlanId(int evaluationPlanId)
	{
		String queryString = "DELETE FROM EvaluationContent Where evaluationPlanId = ?";
		this.getSession().createQuery(queryString).setInteger(0, evaluationPlanId).executeUpdate();
	}
	
	
	public void deleteEvaluationContent(EvaluationContent evaluationContent)
	{
		this.getSession().delete(evaluationContent);
	}
	
	public void deleteEvaluationTemplateById(int evaluationTemplateId)
	{
		String queryString = "DELETE FROM EvaluationTemplate Where evaluationTemplateId = ?";
		this.getSession().createQuery(queryString).setInteger(0, evaluationTemplateId).executeUpdate();
	}
	
	public void deleteEvaluationTemplate(EvaluationTemplate evaluationTemplate)
	{
		this.getSession().delete(evaluationTemplate);
	}
		
	/**
	 * 删除某个模板的全部字段定义
	 * @param evaluationTemplateId
	 */
	public void deleteEvaluationTemplateFields(int evaluationTemplateId)
	{
		String queryString = "DELETE FROM EvaluationTemplateFields Where evaluationTemplateId = ? ";
		this.getSession().createQuery(queryString).setInteger(0, evaluationTemplateId).executeUpdate();
		
	}
	/**
	 * 删除某个模板的字段定义
	 * @param fieldsId
	 */
	public void deleteEvaluationTemplateField(int fieldsId)
	{
		String queryString = "DELETE FROM EvaluationTemplateFields Where fieldsId = ? ";
		this.getSession().createQuery(queryString).setInteger(0, fieldsId).executeUpdate();
	}
	/**
	 * 增加或修改
	 * @param evaluationTemplateField
	 */
	public void saveOrUpdateEvaluationTemplateField(EvaluationTemplateFields evaluationTemplateField)
	{
		this.getSession().saveOrUpdate(evaluationTemplateField);
	}
	/**
	 * 得到某个模板的全部字段定义
	 * @param evaluationTemplateId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EvaluationTemplateFields> getEvaluationTemplateFields(int evaluationTemplateId)
	{
		String queryString = " FROM EvaluationTemplateFields Where evaluationTemplateId=?";
		List<EvaluationTemplateFields> list = (List<EvaluationTemplateFields>)this.getSession().createQuery(queryString).setInteger(0, evaluationTemplateId).list();
		return list;		
	}
	/**
	 * 得到可以用的模板
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EvaluationTemplate> getTemplates()
	{
		String queryString = " FROM EvaluationTemplate Where enabled=true";
		List<EvaluationTemplate> list = (List<EvaluationTemplate>)this.getSession().createQuery(queryString).list();
		return list;
	}
	/**
	 * 得到某个评课的模板 
	 * @param evaluationPlanId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EvaluationPlanTemplate> getEvaluationPlanTemplates(int evaluationPlanId)
	{
		String queryString = " FROM EvaluationPlanTemplate Where evaluationPlanId=?";
		List<EvaluationPlanTemplate> list = (List<EvaluationPlanTemplate>)this.getSession().createQuery(queryString).setInteger(0, evaluationPlanId).list();
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<EvaluationTemplate> getEvaluationTemplates(int evaluationPlanId)
	{
		String queryString = " FROM EvaluationTemplate Where enabled=True and EvaluationTemplateId In(Select evaluationTemplateId FROM EvaluationPlanTemplate Where evaluationPlanId=?)";
		List<EvaluationTemplate> list = (List<EvaluationTemplate>)this.getSession().createQuery(queryString).setInteger(0, evaluationPlanId).list();
		return list;
	}
	/**
	 * 增加评课使用的模板 
	 * @param evaluationPlanId
	 * @param templateId
	 */
	@SuppressWarnings("unchecked")
	public void insertEvaluationPlanTemplates(int evaluationPlanId,int templateId)
	{
		String queryString = " FROM EvaluationPlanTemplate Where evaluationPlanId = ? And evaluationTemplateId = ?";
		List<EvaluationPlanTemplate> list = (List<EvaluationPlanTemplate>)this.getSession().createQuery(queryString).setInteger(0, evaluationPlanId).setInteger(1, templateId).list();
		if(list.size() > 0)
			return;
		
		EvaluationPlanTemplate ept=new EvaluationPlanTemplate();
		ept.setEvaluationPlanId(evaluationPlanId);
		ept.setEvaluationTemplateId(templateId);
		this.getSession().save(ept);
		this.getSession().flush();		
	}
	
	/**
	 * 删除评课使用的模板 
	 * @param evaluationPlanId
	 * @param templateId
	 */
	public void removeEvaluationPlanTemplates(int evaluationPlanId,int templateId)
	{
		String queryString = "DELETE FROM EvaluationPlanTemplate Where evaluationPlanId = ? And evaluationTemplateId = ?";
		this.getSession().createQuery(queryString).setInteger(0, evaluationPlanId).setInteger(1, templateId).executeUpdate();
	}
	public void removeEvaluationPlanTemplates(int evaluationPlanId)
	{
		String queryString = "DELETE FROM EvaluationPlanTemplate Where evaluationPlanId = ?";
		this.getSession().createQuery(queryString).setInteger(0, evaluationPlanId).executeUpdate();
	}	
	/**
	 * 增加视频资源
	 * @param evaluationPlanId
	 * @param videoId
	 * @param videoTitle
	 */
	@SuppressWarnings("unchecked")
	public void insertVideoToEvaluation(int evaluationPlanId,int videoId,String videoTitle,String flvThumbNailHref)
	{
		String queryString = " FROM EvaluationVideo Where evaluationPlanId = ? And videoId = ?";
		List<EvaluationVideo> evs = (List<EvaluationVideo>)this.getSession().createQuery(queryString).setInteger(0, evaluationPlanId).setInteger(1, videoId).list();
		if(evs.size() > 0)
			return;
		
		EvaluationVideo ev=new EvaluationVideo();
		ev.setVideoId(videoId);
		ev.setEvaluationPlanId(evaluationPlanId);
		ev.setVideoTitle(videoTitle);
		ev.setCreateDate(new Date());
		ev.setFlvThumbNailHref(flvThumbNailHref);
		this.getSession().save(ev);
		this.getSession().flush();	
	}
	/**
	 * 删除视频资源
	 * @param evaluationPlanId
	 * @param videoId
	 */
	public void removeVideoFromEvaluation(int evaluationPlanId,int videoId)
	{
		String queryString = "DELETE FROM EvaluationVideo WHERE evaluationPlanId = ? And videoId = ?";
		this.getSession().createQuery(queryString).setInteger(0, evaluationPlanId).setInteger(1, videoId).executeUpdate();
	}
	public void removeVideosFromEvaluation(int evaluationPlanId)
	{
		String queryString = "DELETE FROM EvaluationVideo WHERE evaluationPlanId = ?";
		this.getSession().createQuery(queryString).setInteger(0, evaluationPlanId).executeUpdate();
	}	
	/**
	 * 得到评课的视频列表 
	 * @param evaluationPlanId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Video> getVideos(int evaluationPlanId)
	{
		String queryString = "SELECT v FROM EvaluationVideo a LEFT JOIN a.video v Where a.videoId=v.videoId and a.evaluationPlanId = ?";
		List<Video> evs = (List<Video>)this.getSession().createQuery(queryString).setInteger(0, evaluationPlanId).list();
		return evs;
	}
	/**
	 * 得到审核过得视频列表
	 * @param evaluationPlanId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EvaluationVideo> getVideosAuditState(int evaluationPlanId)
	{
		String queryString = "SELECT a FROM EvaluationVideo a LEFT JOIN a.video v Where a.videoId=v.videoId and v.auditState=0 and a.evaluationPlanId = ?";
		List<EvaluationVideo> evs = (List<EvaluationVideo>)this.getSession().createQuery(queryString).setInteger(0, evaluationPlanId).list();
		return evs;
	}
	/**
	 * 增加资源
	 * @param evaluationPlanId
	 * @param resourceId
	 * @param resourceTitle
	 */
	@SuppressWarnings("unchecked")
	public void insertResourceToEvaluation(int evaluationPlanId,int resourceId,String resourceTitle,String resourceHref)
	{
		String queryString = " FROM EvaluationResource Where evaluationPlanId = ? And resourceId = ?";
		List<EvaluationResource> ers = (List<EvaluationResource>)this.getSession().createQuery(queryString).setInteger(0, evaluationPlanId).setInteger(1, resourceId).list();
		if(ers.size() > 0)
			return;
		
		EvaluationResource er=new EvaluationResource();
		er.setResourceId(resourceId);
		er.setEvaluationPlanId(evaluationPlanId);
		er.setResourceTitle(resourceTitle);
		er.setCreateDate(new Date());
		er.setResourceHref(resourceHref);
		this.getSession().save(er);
		this.getSession().flush();		
	}
	/**
	 * 删除资源
	 * @param evaluationPlanId
	 * @param resourceId
	 */
	public void removeResourceFromEvaluation(int evaluationPlanId,int resourceId)
	{
		String queryString = "DELETE FROM EvaluationResource WHERE evaluationPlanId = ? And videoId = ?";
		this.getSession().createQuery(queryString).setInteger(0, evaluationPlanId).setInteger(1, resourceId).executeUpdate();
		
	}
	public void removeResourcesFromEvaluation(int evaluationPlanId)
	{
		String queryString = "DELETE FROM EvaluationResource WHERE evaluationPlanId = ? ";
		this.getSession().createQuery(queryString).setInteger(0, evaluationPlanId).executeUpdate();
		
	}	
	/**
	 * 得到资源评课的资源列表
	 * @param evaluationPlanId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Resource> getResources(int evaluationPlanId)
	{
		String queryString = "SELECT r FROM EvaluationResource a LEFT JOIN a.resource r Where a.resourceId=r.resourceId And a.evaluationPlanId = ?";
		List<Resource> evs = (List<Resource>)this.getSession().createQuery(queryString).setInteger(0, evaluationPlanId).list();
		return evs;		
	}
	@SuppressWarnings("unchecked")
	public List<EvaluationResource> getResourcesAuditState(int evaluationPlanId)
	{
		String queryString = "SELECT a FROM EvaluationResource a LEFT JOIN a.resource r Where a.resourceId=r.resourceId And r.auditState=0 and a.evaluationPlanId = ?";
		List<EvaluationResource> evs = (List<EvaluationResource>)this.getSession().createQuery(queryString).setInteger(0, evaluationPlanId).list();
		return evs;		
	}	
}
