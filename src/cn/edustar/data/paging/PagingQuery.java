package cn.edustar.data.paging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.hibernate.Query;
import org.hibernate.Session;

import cn.edustar.data.Pager;

/**
 * 分页查询的类，实现参数的传递和数据总数的查询
 * 
 * @author admin
 * 
 */
@SuppressWarnings("rawtypes")
public class PagingQuery {
	private boolean IsDebug = true;
	public String tableName;
	public String keyName = "";
	public String fetchFieldsName = "*";
	public String orderByFieldName = "";
	public String whereClause = "";

	public String spName = "";
	public int topCount = 20;

	public PagingQuery() {
	}
	

	public List getPagedList(Session session) {
		final String tableName = this.tableName;
		final String fetchFieldsName = this.fetchFieldsName;
		final String orderByFieldName = this.orderByFieldName;
		final String whereClause = this.whereClause;
		final String spName = this.spName;
		final int topCount = this.topCount;

		final Query query = session.getNamedQuery(spName);
		query.setString(0, tableName); // 表名
		query.setString(1, keyName); // 主键名
		query.setInteger(2, 1); // 当前页
		query.setInteger(3, topCount); // 每页记录数
		query.setString(4, fetchFieldsName);
		query.setString(5, orderByFieldName); // 排序字段。可以加DESC/ASC
		query.setString(6, whereClause);
		List list = null;
		try {
			list = query.list();
		} catch (Exception e) {
			if (IsDebug) {
				System.out.println(outputDate() + " 发生错误"	+ e.getLocalizedMessage());
				System.out.println(outputDate()	+ " getPagedList(HibernateTemplate hiber):tableName=" + tableName);
				System.out.println(outputDate()	+ " getPagedList(HibernateTemplate hiber):keyName="	+ keyName);
				System.out.println(outputDate()	+ " getPagedList(HibernateTemplate hiber):topCount=" + topCount);
				System.out.println(outputDate()	+ " getPagedList(HibernateTemplate hiber):fetchFieldsName="	+ fetchFieldsName);
				System.out.println(outputDate()	+ " getPagedList(HibernateTemplate hiber):orderByFieldName=" + orderByFieldName);
				System.out.println(outputDate() + " getPagedList(HibernateTemplate hiber):whereClause="	+ whereClause);
			}
		}

		return list;
	}

	public List getPagedList(Session session, Pager pager) {
		final String tableName = this.tableName;
		final String keyName = this.keyName;
		final String fetchFieldsName = this.fetchFieldsName;
		final String orderByFieldName = this.orderByFieldName;
		final String whereClause = this.whereClause;
		final String spName = this.spName;
		final Pager _pager = pager;

		Query query = session.getNamedQuery(spName);
		query.setString(0, tableName); // 表名
		query.setString(1, keyName); // 主键名
		query.setInteger(2, _pager.getCurrentPage()); // 当前页
		query.setInteger(3, _pager.getPageSize()); // 每页记录数
		query.setString(4, fetchFieldsName);
		query.setString(5, orderByFieldName); // 排序字段。可以加DESC/ASC
		query.setString(6, whereClause);
		List list = null;

		try {
			list = query.list();
		} catch (Exception e) {
			if (IsDebug) {
				System.out.println(outputDate() + " 发生错误"	+ e.getLocalizedMessage());
				System.out.println(outputDate()	+ " 来自List getPagedList(HibernateTemplate hiber, Pager pager):tableName="+ tableName);
				System.out.println(outputDate()	+ " 来自List getPagedList(HibernateTemplate hiber, Pager pager):keyName="	+ keyName);
				System.out.println(outputDate()	+ " 来自List getPagedList(HibernateTemplate hiber, Pager pager):_pager.getCurrentPage()=" + _pager.getCurrentPage());
				System.out.println(outputDate()	+ " 来自List getPagedList(HibernateTemplate hiber, Pager pager):_pager.getPageSize()=" + _pager.getPageSize());
				System.out.println(outputDate()	+ " 来自List getPagedList(HibernateTemplate hiber, Pager pager):fetchFieldsName="	+ fetchFieldsName);
				System.out.println(outputDate()	+ " 来自List getPagedList(HibernateTemplate hiber, Pager pager):orderByFieldName="+ orderByFieldName);
				System.out.println(outputDate()	+ " 来自List getPagedList(HibernateTemplate hiber, Pager pager):whereClause="	+ whereClause);
			}
		}
		return list;
	}

	public Integer getRowsCount(Session session) {
		final String tableName = this.tableName;
		final String whereClause = this.whereClause;

		String querySQL = "select count(*) from " + tableName;
		if (!whereClause.equals("")) {
			querySQL += " Where " + whereClause;
		}
		final Query query = session.createSQLQuery(querySQL);
		Integer ret = null;
		try {
			ret = (Integer) query.uniqueResult();
		} catch (Exception e) {
			if (IsDebug) {
				System.out.println(outputDate() + " 发生错误"	+ e.getLocalizedMessage());
				System.out.println(outputDate() + " getRowsCount方法：tableName="	+ tableName);
				System.out.println(outputDate()	+ " getRowsCount方法：whereClause=" + whereClause);
			}
		}

		return (Integer) ret;
	}

	private String outputDate() {
		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(calendar.getTime());
	}
	
	public PagingQuery(String tableName, String keyName,
			String fetchFieldsName, String orderByFieldName,
			String whereClause, String spName, int topCount) {
		super();
		this.tableName = tableName;
		this.keyName = keyName;
		this.fetchFieldsName = fetchFieldsName;
		this.orderByFieldName = orderByFieldName;
		this.whereClause = whereClause;
		this.spName = spName;
		this.topCount = topCount;
	}


	public void setIsDebug(boolean isDebug) {
		IsDebug = isDebug;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}


	public void setFetchFieldsName(String fetchFieldsName) {
		this.fetchFieldsName = fetchFieldsName;
	}


	public void setOrderByFieldName(String orderByFieldName) {
		this.orderByFieldName = orderByFieldName;
	}


	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}


	public void setSpName(String spName) {
		this.spName = spName;
	}


	public void setTopCount(int topCount) {
		this.topCount = topCount;
	}
	
	
}
