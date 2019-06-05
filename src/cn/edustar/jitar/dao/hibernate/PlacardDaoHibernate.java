package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.PlacardDao;
import cn.edustar.jitar.pojos.Placard;
import cn.edustar.jitar.service.PlacardQueryParam;
import cn.edustar.jitar.service.PlacardQueryParamEx;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 用户公告数据库访问实现
 * 
 *
 */
public class PlacardDaoHibernate extends BaseDaoHibernate implements PlacardDao {
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.PlacardDao#getPlacard(int)
	 */
	public Placard getPlacard(int placardId) {
		return (Placard)getSession().get(Placard.class, placardId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.PlacardDao#savePlacard(cn.edustar.jitar.pojos.Placard)
	 */
	public void savePlacard(Placard placard) {
		if (placard.getId() > 0) {
			this.getSession().merge(placard);
			this.getSession().flush();
		}
		else if (placard.getId() == 0) {
			this.getSession().save(placard);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.PlacardDao#deletePlacard(cn.edustar.jitar.pojos.Placard)
	 */
	public void deletePlacard(Placard placard) {
		getSession().delete(placard);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.PlacardDao#deletePlacardForObject(int, int)
	 */
	public void deletePlacardByObject(int objectType, int objectId) {
		String hql = "DELETE FROM Placard WHERE objType = ? AND objId = ?";
		getSession().createQuery(hql).setInteger(0, objectType).setInteger(1, objectId).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.PlacardDao#updatePlacardHideState(cn.edustar.jitar.pojos.Placard, boolean)
	 */
	public void updatePlacardHideState(Placard placard, boolean hide) {
		String hql = "UPDATE Placard " +
			" SET hide = ? " +
			" WHERE id = ? ";
		getSession().createQuery(hql).setBoolean(0, hide).setInteger(1, placard.getId()).executeUpdate();
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.PlacardDao#getPlacardList(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<Placard> getPlacardList(int obj_type, int obj_id, boolean includeHide) {
		String hql = "FROM Placard " +
			" WHERE objType = ? " + 
			"   AND objId = ? "  +
			(includeHide ? "" : " AND hide = false ") +
			" ORDER BY createDate DESC, id DESC";
		return getSession().createQuery(hql).setInteger(0, obj_type).setInteger(1, obj_id).list();
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.PlacardDao#getPlacardList(cn.edustar.jitar.service.PlacardQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Placard> getPlacardList(PlacardQueryParam param, Pager pager) {
		QueryHelper query = param.createQuery();
		if (pager == null)
			return query.queryData(getSession(), -1, param.count);
		else
			return query.queryDataAndTotalCount(getSession(), pager);
	}

	public List getPlacardList(PlacardQueryParamEx param, Pager pager) {
		QueryHelper query = param.createQuery();
		if (pager == null)
			return query.queryData(getSession(), -1, param.count);
		else
			return query.queryDataAndTotalCount(getSession(), pager);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.PlacardDao#getPlacardCount(int, int, boolean)
	 */
	public int getPlacardCount(int obj_type, int obj_id, boolean includeHide) {
		String hql = "SELECT COUNT(*) " +
			" WHERE objType = ? " + 
			"   AND objId = ? "  +
			(includeHide ? "" : " AND hide = false ") +
			" ORDER BY createDate DESC, id DESC";
		return getSession().createQuery(hql).setInteger(0, obj_type).setInteger(1, obj_id).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.iface.UserPlacardDao#getRecentUserPlacard(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<Placard> getRecentPlacard(int obj_type, int obj_id, int count) {
		String hql = "FROM Placard " +
			" WHERE objType = " + obj_type +
			"   AND objId = " + obj_id + 
			"   AND hide = false " +
			" ORDER BY createDate DESC, id DESC";
		
		//return getSession().createQuery(hql).setFirstResult(count).list(); //代码错！！！
		//setFirstResult表示从查询得到的结果集的第几条开始获取，setMaxResults表示获取多少条数据
		return getSession().createQuery(hql).setFirstResult(0).setMaxResults(count).list();
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.iface.PlacardDao#getMultiRecentPlacard(int, java.util.List, int)
	 */
	@SuppressWarnings("unchecked")
	public List<Placard> getMultiRecentPlacard(int obj_type, List<Integer> obj_ids, int count) {
		String hql = "FROM Placard " +
			" WHERE objType = " + obj_type +
			"   AND objId IN " + CommonUtil.toSqlInString(obj_ids) + 
			"   AND hide = false " +
			" ORDER BY createDate DESC, id DESC";
		
		//return getSession().createQuery(hql).setFirstResult(count).list();//代码错！！！
		//setFirstResult表示从查询得到的结果集的第几条开始获取，setMaxResults表示获取多少条数据
		return getSession().createQuery(hql).setFirstResult(0).setMaxResults(count).list();
	}
}
