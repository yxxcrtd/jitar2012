package cn.edustar.data.hql;

import org.hibernate.Query;

/**
 * SQL 查询接口
 */
public interface SqlQuery {
	
	/**
	 * 获取 SQL
	 * 
	 * @return
	 */
	public String getSql();

	/**
	 * 初始化查询
	 * 
	 * @param query
	 */
	public void initQuery(Query query);
	
}
