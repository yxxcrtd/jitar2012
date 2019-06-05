package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import cn.edustar.jitar.dao.ResTypeDao;
import cn.edustar.jitar.pojos.ResType;

/**
 * 资源类型使用 Hibernate 的数据访问对象的实现
 */
public class ResTypeDaoHibernate extends BaseDaoHibernate implements ResTypeDao {
	
	/* (non-Javadoc)
	 * @see cn.edustar.jitar.dao.hibernate.JtrResTypeDao#createResType(cn.edustar.jitar.pojos.JtrResType)
	 */
	public void createResType(ResType transientInstance) {
		log.debug("saving ResType instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public  void delResType(ResType jtrResType)
	{
		getSession().delete(jtrResType);
	}
	
	/* (non-Javadoc)
	 * @see cn.edustar.jitar.dao.hibernate.JtrResTypeDao#getResType()
	 */
	public ResType getResType(int resTypeId) {
		return (ResType) getSession()
				.get(ResType.class, resTypeId);
	}
	/* (non-Javadoc)
	 * @see cn.edustar.jitar.dao.hibernate.JtrResTypeDao#getChildJtrResTypes()
	 */
	@SuppressWarnings("unchecked")
	public List<ResType> getChildResTypes(Integer parent)
	{
		String hql = "";
		hql = "FROM ResType " +
					" WHERE tcParent=? " +
					" ORDER BY tcSort, tcId";
	
		return getSession().createQuery(hql).setParameter(0, parent).list();
	}
	/* (non-Javadoc)
	 * @see cn.edustar.jitar.dao.hibernate.JtrResTypeDao#getChildJtrResTypes()
	 */
	@SuppressWarnings("unchecked")
	public List<ResType> getResTypes()
	{
		String hql = "";
		hql = "FROM ResType " +
					" " +
					" ORDER BY tcSort, tcId";
	
		return getSession().createQuery(hql).list();
	}
	
	public ResType getResTypeByNameAndParentId(String resName, Integer parentId)
	{
		String queryString = "FROM ResType WHERE tcTitle = :tcTitle And tcParent = :tcParent";
		return (ResType)this.getSession().createQuery(queryString).setString("tcTitle", resName).setParameter("tcParent", parentId).uniqueResult();
	}
}
