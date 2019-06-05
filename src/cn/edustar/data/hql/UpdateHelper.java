package cn.edustar.data.hql;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;

/**
 * 更新辅助类
 * 
 * @author Yang XinXin
 */
public class UpdateHelper extends HqlHelperBase {

	/**
	 * 更新语句
	 */
	public String updateClause = "";

	/**
	 * 构造函数
	 */
	public UpdateHelper() {
		// 
	}

	/**
	 * 构造
	 * 
	 * @param updateClause
	 */
	public UpdateHelper(String updateClause) {
		this.updateClause = updateClause;
	}

	/**
	 * 执行更新并返回更新数量
	 * 
	 * @param hiber
	 * @return
	 */
	public int executeUpdate(final Session session) {
		return session.doReturningWork(new ReturningWork<Integer>(){
			public Integer execute(Connection connection)
					throws SQLException {
				Query query = session.createQuery(updateClause);
				initQuery(query);
				return query.executeUpdate();
			}			
		});	
	}
}
