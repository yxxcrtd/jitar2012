package cn.edustar.data.hql;

import java.util.Date;

import org.hibernate.Query;

/**
 * 查询参数记忆器
 */
public class QueryParam {
	
	/**
	 * 参数名字
	 */
	public String paramName;

	/** 设置器名字，如 setString, setInt, setBoolean. */
	public String setterName;

	/** 参数值. */
	public Object paramValue;

	/**
	 * 构造.
	 */
	public QueryParam() {
	}

	/**
	 * 构造.
	 * 
	 * @param paramName
	 * @param setterName
	 * @param paramValue
	 */
	public QueryParam(String paramName, String setterName, Object paramValue) {
		this.paramName = paramName;
		this.setterName = setterName;
		this.paramValue = paramValue;
	}

	public String toString() {
		return "QueryParam{" + this.setterName + ": " + this.paramName + "=" + this.paramValue + "}";
	}

	/**
	 * 向 Query 中设置参数.
	 * 
	 * @param query
	 */
	public void setParam(Query query) {
		if ("setString".equals(setterName))
			query.setString(paramName, (String) paramValue);
		else if ("setInteger".equals(setterName))
			query.setInteger(paramName, (Integer) paramValue);
		else if ("setDate".equals(setterName))
			query.setDate(paramName, (Date) paramValue);
		else if ("setBoolean".equals(setterName))
			query.setBoolean(paramName, (Boolean) paramValue);
		else if ("setTimestamp".equals(setterName))
			query.setTimestamp(paramName, (Date) paramValue);
		else
			query.setParameter(paramName, paramValue);
	}
	
}
