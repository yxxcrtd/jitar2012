package cn.edustar.jitar.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.JitarContext;

/**
 * 自定义查询支持 bean, 缺省 varName = 'result', pagerName = 'pager'.
 *
 *
 */
public class FreeQueryBean extends AbstractPageDataBean {
	/** 数据库访问对象 */
	private Session session;
	
	/**
	 * 构造.
	 */
	public FreeQueryBean() {
		super.setVarName("result");
		try {
			SessionFactory sf = (SessionFactory)JitarContext.getCurrentJitarContext()
				.getSpringContext().getBean("sessionFactory");
			this.setSession(sf.getCurrentSession());
		} catch (Exception ex) {
			// ignore
		}
	}
	
	/** 设置数据库访问对象 */
	public void setSession(Session session) {
		this.session = session;
	}
	
	/** 查询对象 */
	private QueryHelper query = new QueryHelper();

	/** 获得的第一条记录的地址, 缺省 = -1 表示不限制 */
	private int firstResult = -1;

	/** 最多获取记录数量, 缺省 = -1 表示不限制 */
	private int maxResults = -1;
	
	/** SELECT 子句 */
	public void setSelectClause(String selectClause) {
		query.selectClause = selectClause;
	}
	
	/** FROM 子句。 */
	public void setFromClause(String fromClause) {
		query.fromClause = fromClause;
	}
	
	/** WHERE 子句。 */
	public void setWhereClause(String whereClause) {
		query.whereClause = whereClause;
	}
	
	/** ORDER BY 子句。 */
	public void setOrderClause(String orderClause) {
		query.orderClause = orderClause;
	}

	/** GROUP BY 子句。 */
	public void setGroupbyClause(String groupbyClause) {
		query.groupbyClause = groupbyClause;
	}
	
	/** HAVING 子句 */
	public void setHavingClause(String havingClause) {
		query.havingClause = havingClause;
	}

	/** 获得的第一条记录的地址, 缺省 = -1 表示不限制 */
	public int getFirstResult() {
		return this.firstResult;
	}

	/** 获得的第一条记录的地址, 缺省 = -1 表示不限制 */
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	/** 最多获取记录数量, 缺省 = -1 表示不限制 */
	public int getMaxResults() {
		return this.maxResults;
	}

	/** 最多获取记录数量, 缺省 = -1 表示不限制 */
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.DataBean#prepareData(cn.edustar.jitar.data.DataHost)
	 */
	public void doPrepareData(DataHost host) {
		Pager pager = getUsePager() ? getContextPager(host) : null;
		if (pager == null) {
			Object result = query.queryData(session, firstResult, maxResults);
			host.setData(getVarName(), result);
		} else {
			Object result = query.queryData(session, pager);
			host.setData(getVarName(), result);
			host.setData(getPagerName(), pager);
		}
	}
}
