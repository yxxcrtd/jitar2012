package cn.edustar.jitar.dao.hibernate;

import java.util.List;
import java.util.Map;

import cn.edustar.jitar.dao.StapleDao;
import cn.edustar.jitar.pojos.Staple;


/**
 * 用户博文分类数据访问的实现
 * 
 * @author mengxianhui
 * @deprecated - 不使用了
 */
public class StapleDaoHibernate extends BaseDaoHibernate 
		implements StapleDao {

	public StapleDaoHibernate() {
	}

	public void addStaple(Staple st) {
		this.getSession().save(st);
	}

	public Staple getStaple(Integer stapleId) {
		return (Staple) this.getSession().get(Staple.class, stapleId);
	}

	@SuppressWarnings("unchecked")
	public List<Staple> getAll() {
		String sql = "FROM Staple ORDER BY StapleId DESC";
		List<Staple> stapleList = (List<Staple>)this.getSession().createQuery(sql).list();
		return stapleList;
	}

	public void updateStaple(Staple staple) {		
		this.getSession().update(staple);
		this.getSession().flush();
	}
	
	public void deleteStaple(Staple staple)
	{
		this.getSession().delete(staple);
	}

	@SuppressWarnings("unchecked")
	public boolean stapleNameIsExist(String stapleName) {
		String sql = " from Staple where stapleName = '" + stapleName + "' and userId = 1";
		List<Staple> stapleList = (List<Staple>)this.getSession().createQuery(sql).list();
		return (stapleList.size() > 0);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.iface.StapleDao#getStapleNames(java.util.List)
	 */
	public Map<Integer, String> getStapleNames(List<Integer> stapleIds) {
		// 参数判断。
		if (stapleIds == null) return null;
		Map<Integer, String> result = new java.util.HashMap<Integer, String>();
		if (stapleIds.size() == 0) return result;
		
		// 组装 HQL IN (xxx) 子句中的 xxx。
		String id_hql = "";
		for (int i = 0; i < stapleIds.size(); ++i) {
			Integer id = stapleIds.get(i);
			if (id != null)
				id_hql += "," + id;
		}
		if (id_hql.length() == 0) return result;
		id_hql = id_hql.substring(1);		// 去掉最前面的一个 ','，结果为 '1,3,5' 的形式。
		
		// 查找出结果。
		String hql = "SELECT stapleId, stapleName FROM Staple WHERE cateId IN (" + id_hql + ")";
		@SuppressWarnings("unchecked")
		List<Object[]> list = (List<Object[]>)this.getSession().createQuery(hql).list();
		
		// 组装结果为 Map<Integer, String> 格式。 ? 也许组装可以让外面做?
		for (int i = 0; i < list.size(); ++i) {
			Object[] row = list.get(i);
			result.put((Integer)row[0], (String)row[1]);
		}
		
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.dao.iface.StapleDao#getUserStapleList(int)
	 */
	@SuppressWarnings("unchecked")
	public List<Staple> getUserStapleList(int userId) {
		String hql = "FROM Staple WHERE userId = " + userId + " ORDER BY orderNum ASC, stapleId ASC";
		return (List<Staple>)this.getSession().createQuery(hql).list();
	}
}
