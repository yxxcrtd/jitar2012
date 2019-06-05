package cn.edustar.jitar.query.sitefactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.jdbc.Work;

import cn.edustar.jitar.dao.hibernate.BaseDaoHibernate;


public class GeneratorSevenDaysCommentArticleServiceImpl extends BaseDaoHibernate implements GeneratorSevenDaysCommentArticleService {

	public void DeleteOldViewCount()
	{
		String queryString = "DELETE FROM ViewCount WHERE DateDiff(day,viewDate,getDate()) > 120";
		//删除2月前的历史无用数据
		//死锁
		this.getSession().createQuery(queryString).executeUpdate();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void UpdateSevenDaysCommentArticle() {
		this.getSession().doWork(new Work() {
			public void execute(Connection connection)
					throws SQLException {
				CallableStatement cs = connection.prepareCall("{Call Get7DaysCommentArticle()}");
				cs.execute();
				cs.close();
			}
		});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void UpdateSevenDaysViewCountArticle() {
		this.getSession().doWork(new Work() {
			public void execute(Connection connection)
					throws SQLException {
				CallableStatement cs = connection
						.prepareCall("{Call Get7DaysViewCountArticle()}");
				cs.execute();
				cs.close();
			}
		});
	}
}
