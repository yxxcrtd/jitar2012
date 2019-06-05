package cn.edustar.data.hql;

import java.util.ArrayList;
import java.util.Date;
import org.hibernate.Query;

/**
 * QueryHelper 等辅助类的基类。其提供命名参数的记忆功能
 */
public abstract class HqlHelperBase {
	
	/**
	 * 查询条件记忆容器
	 */
	private ArrayList<QueryParam> query_param = new ArrayList<QueryParam>();
	
	/** 记忆一个字符串参数 */
	public HqlHelperBase setString(String paramName, String paramValue) {
		QueryParam p = new QueryParam(paramName, "setString", paramValue);
		query_param.add(p);
		return this;
	}
	
	/** 记忆一个布尔参数. */
	public HqlHelperBase setBoolean(String paramName, boolean paramValue) {
		QueryParam p = new QueryParam(paramName, "setBoolean", paramValue);
		query_param.add(p);
		return this;
	}
	
	/** 记忆一个整数参数. */
	public HqlHelperBase setInteger(String paramName, int paramValue) {
		QueryParam p = new QueryParam(paramName, "setInteger", paramValue);
		query_param.add(p);
		return this;
	}
	
	/**
	 * 记忆一个日期型参数
	 * 
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	public HqlHelperBase setDate(String paramName, Date paramValue) {
		QueryParam p = new QueryParam(paramName, "setDate", paramValue);
		query_param.add(p);
		return this;
	}
	
	/**
	 * 记忆一个日期型参数2, 采用 setTimestamp 方法
	 * @param paramName
	 * @param paramValue
	 */
	public HqlHelperBase setTimestamp(String paramName, Date paramValue) {
		QueryParam p = new QueryParam(paramName, "setTimestamp", paramValue);
		query_param.add(p);
		return this;
	}


	/** 根据记忆中的查询条件，初始化 query */
	public void initQuery(Query query) {
		for (int i = 0; i < query_param.size(); ++i)
			query_param.get(i).setParam(query);
	}
	
}
