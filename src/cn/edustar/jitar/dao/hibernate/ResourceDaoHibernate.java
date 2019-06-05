package cn.edustar.jitar.dao.hibernate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.ResourceDao;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.service.ResourceQueryParam;
import cn.edustar.jitar.service.UserResourceQueryParam;

/**
 * 资源DAO的实现
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 29, 2008 2:50:20 PM
 */
public class ResourceDaoHibernate extends BaseDaoHibernate implements ResourceDao {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ResourceDao#getResource(int)
	 */
	public Resource getResource(int resourceId) {
		return (Resource) getSession().get(Resource.class, resourceId);
	}

	@SuppressWarnings("unchecked")
	public List<Resource> getTodayResources(int userId){
		String hql = "FROM Resource WHERE userId = :userId and Year(createDate) = :year and Month(createDate) = :month and Day(createDate) = :day";
		Date date=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String syear= sdf.format(date);
        sdf = new SimpleDateFormat("M");
        String smonth= sdf.format(date);
        sdf = new SimpleDateFormat("d");
        String sday= sdf.format(date);
		int year=Integer.parseInt(syear);
		int month=Integer.parseInt(smonth);
		int day=Integer.parseInt(sday);
		return getSession().createQuery(hql)
				.setInteger("userId", userId)
				.setInteger("year", year)
				.setInteger("month", month)
				.setInteger("day", day)
				.list();
				
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ResourceDao#createResource(cn.edustar.jitar.pojos.Resource)
	 */
	public void createResource(Resource resource) {
		this.getSession().save(resource);
		this.getSession().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ResourceDao#saveResource(cn.edustar.jitar.pojos.Resource)
	 */
	public void updateResource(Resource resource) {
		this.getSession().update(resource);
		this.getSession().flush();
		String hql = "UPDATE PrepareCourseResource SET resourceTitle = :resourceTitle WHERE resourceId = :resourceId";
		this.getSession().createQuery(hql)
		.setString("resourceTitle", resource.getTitle())
		.setInteger("resourceId", resource.getResourceId())
		.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ResourceDao#deleteResource(cn.edustar.jitar.pojos.Resource)
	 */
	public void deleteResource(Resource resource) {
		this.getSession().delete(resource);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ResourceDao#getResourceList(cn.edustar.jitar.service.ResourceQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Resource> getResourceList(ResourceQueryParam param, Pager pager) {
		QueryHelper query = param.createQuery();
		if (pager == null) {
			return query.queryData(getSession(), -1, param.count);
		} else {
			return query.queryDataAndTotalCount(getSession(), pager);
		}
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.ResourceDao#getResourceDataTable(cn.edustar.jitar.service.UserResourceQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getResourceDataTable(UserResourceQueryParam param, Pager pager) {
		QueryHelper query = param.createQuery();
		if (pager == null)
			return query.queryData(this.getSession(), -1, param.count);
		else
			return query.queryDataAndTotalCount(this.getSession(), pager);
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ResourceDao#updateResourceDeleteStatus(int, boolean)
	 */
	public int updateResourceDeleteStatus(int resourceId, boolean delState) {
		String hql = "UPDATE Resource SET delState = :delState WHERE resourceId = :resourceId";
		return this.getSession().createQuery(hql).setBoolean("delState", delState).setInteger("resourceId", resourceId).executeUpdate();//(hql, new Object[] { delState, resourceId });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ResourceDao#updateResourceShareMode(int, int)
	 */
	public int updateResourceShareMode(int resourceId, int shareMode) {
		String hql = "UPDATE Resource SET shareMode = :shareMode WHERE resourceId = :resourceId";
		return getSession().createQuery(hql).setInteger("shareMode", shareMode).setInteger("resourceId", resourceId).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ResourceDao#updateResourceCategory(cn.edustar.jitar.pojos.Resource)
	 */
	public void updateResourceCategory(Resource resource) {
		String hql = "UPDATE Resource SET sysCateId = :sysCateId, userCateId = :userCateId WHERE resourceId = :resourceId";
		this.getSession().createQuery(hql).setParameter("sysCateId", resource.getSysCateId()).setParameter("userCateId", resource.getUserCateId()).setInteger("resourceId", resource.getId()).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ResourceDao#updateResourceCommentCount(int, int)
	 */
	public int updateResourceCommentCount(int resourceId, int commentCount) {
		String hql = "UPDATE Resource SET commentCount = :commentCount WHERE resourceId = :resourceId";
		return getSession().createQuery(hql).setInteger("commentCount", commentCount).setInteger("resourceId", resourceId).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ResourceDao#incResourceCommentCount(int)
	 */
	public void incResourceCommentCount(int resourceId) {
		String hql = "SELECT COUNT(*) FROM Comment WHERE objType = 12 AND objId = :objId";
		int count = 0;
		Object c = this.getSession().createQuery(hql).setInteger("objId", resourceId).uniqueResult();
		if (c == null) {
			count = 0;
		} else {
			count = Integer.valueOf(c.toString());
		}
		
		hql = "UPDATE Resource SET commentCount = :commentCount WHERE resourceId = :resourceId";
		this.getSession().createQuery(hql).setInteger("commentCount", count).setInteger("resourceId", resourceId).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ResourceDao#batchClearResourceUserCategory(int)
	 */
	public int batchClearResourceUserCategory(int userCateId) {
		String hql = "UPDATE Resource SET userCateId = NULL WHERE userCateId = :userCateId";		
		return this.getSession().createQuery(hql).setInteger("userCateId", userCateId).executeUpdate();
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ResourceDao#updateResourceSysCate(cn.edustar.jitar.pojos.Resource, java.lang.Integer)
	 */
	public void updateResourceSysCate(Resource resource, Integer sysCateId) {
		String hql = "UPDATE Resource SET sysCateId = :sysCateId WHERE resourceId =:resourceId";
		this.getSession().createQuery(hql).setParameter("sysCateId", sysCateId).setInteger("resourceId",  resource.getResourceId()).executeUpdate();
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ResourceDao#increaseViewCount(int)
	 */
	public void increaseViewCount(int resourceId) {
		String hql = "UPDATE Resource SET viewCount = viewCount + 1 WHERE resourceId = :resourceId";
		this.getSession().createQuery(hql).setInteger("resourceId", resourceId).executeUpdate();
	}

	public void increaseViewCount(int resourceId, int count){
	    String hql = "UPDATE Resource SET viewCount = viewCount + :viewCount WHERE resourceId = :resourceId";
        this.getSession().createQuery(hql).setInteger("viewCount", count).setInteger("resourceId", resourceId).executeUpdate();
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ResourceDao#deleteResourceFromPrepareCourseStage(int)
	 */
	public void deleteResourceFromPrepareCourseStage(int resourceId) {
		String hql = "DELETE FROM PrepareCourseResource WHERE resourceId = :resourceId";
		this.getSession().createQuery(hql).setInteger("resourceId", resourceId).executeUpdate();
	}
	
	/* 返回当前用户的已经上传的资源总数＋照片总数之和（以后有需求再添加其他空间总和）
	 *
	 * @see cn.edustar.jitar.dao.ResourceDao#totalResourceSize(int)
	 */
	public int totalResourceSize(int userId) {
		int resourceFileSize = 0;
		int photoFileSize = 0;
		Object o = this.getSession().createQuery("SELECT SUM(fsize) FROM Resource WHERE ((userId = :userId) AND delState = '0'))").setInteger("userId", userId).uniqueResult();
		if(o != null ) resourceFileSize = Integer.valueOf(o.toString()).intValue();
		o  = this.getSession().createQuery("SELECT SUM(size) FROM Photo WHERE ((userId = :userId) AND delState = '0'))").setInteger("userId", userId).uniqueResult();
		if(o != null ) photoFileSize = Integer.valueOf(o.toString()).intValue();
		return resourceFileSize + photoFileSize;
	}
	
	public void setPushState(Resource resource, int pushState)
	{
		String queryString = "UPDATE Resource SET pushState = :pushState WHERE resourceId = :resourceId";
		this.getSession().createQuery(queryString).setInteger("pushState", pushState).setInteger("resourceId", resource.getResourceId()).executeUpdate();//(queryString, new Object[]{ pushState, });
	}
	
	/** 得到用户的全部资源 */
	@SuppressWarnings("unchecked")
	public List<Resource> getUserResourceOfAll(int userId)
	{
		String queryString="FROM Resource Where userId=:userId";
		return (List<Resource>)this.getSession().createQuery(queryString).setInteger("userId", userId).list();
	}

	@Override
	public void evict(Object object) {
		this.getSession().evict(object);
	}

	@Override
	public void flush() {
		this.getSession().flush();
	}
}
