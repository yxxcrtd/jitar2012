package cn.edustar.jitar.tags;

import java.util.List;

import cn.edustar.jitar.dao.hibernate.BaseDaoHibernate;

/**
 * HqlTag 中执行 hql 语句的辅助对象.
 *
 *
 */
public class HibernateHqlExecutor extends BaseDaoHibernate {
	@SuppressWarnings("unchecked")
	public List find(String hql) {
		return this.getSession().createQuery(hql).list();
	}
}
