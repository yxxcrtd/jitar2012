package cn.edustar.jitar.dao.hibernate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;

import cn.edustar.jitar.dao.WebSiteManageDao;
import cn.edustar.jitar.pojos.BackYear;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.User;

public class WebSiteManageDaoHibernate extends BaseDaoHibernate implements
		WebSiteManageDao {

	public void saveOrUpdateBackYear(BackYear backYear) {
		this.getSession().saveOrUpdate(backYear);
	}

	@SuppressWarnings("unchecked")
	public List<BackYear> getBackYearList(String backYearType) {
		String queryString = "From BackYear Where backYearType = :backYearType Order By backYearId DESC";
		List<BackYear> list = (List<BackYear>) this.getSession().createQuery(queryString).setString("backYearType",backYearType).list();
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List getArticleYearList() {
		String queryString = "SELECT DISTINCT YEAR(CreateDate) AS ArticleYear FROM Article ORDER BY YEAR(CreateDate)";
		List list = this.getSession().createQuery(queryString).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public Long getYearArticleCount(int year) {
		String queryString = "SELECT COUNT(*) FROM Article WHERE YEAR(createDate) = " + year;
		List<Object> result = this.getSession().createQuery(queryString).list();
		if(result == null || result.size() == 0)
		return 0l;
		else
			return Long.valueOf(result.get(0).toString()).longValue();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Long getSubejctYearArticleCount(int year,int subjectId,int gradeId)
	{
		final int y = year;
		final int sid = subjectId;
		final int gid = gradeId;
		
		final Long ret =  this.getSession().doReturningWork(new ReturningWork<Long>() {
						public Long execute(Connection connection)
								throws SQLException {
							CallableStatement cs = connection.prepareCall("{?=Call CountYearArticleSubject(?,?,?)}");
							cs.registerOutParameter(1,java.sql.Types.DOUBLE); 
							cs.setInt(2, y);
							cs.setInt(3, sid);
							cs.setInt(4, gid);
							cs.execute();
							return cs.getLong(1);
						}
					});
		return Long.valueOf(ret.toString()).longValue();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Long getUnitYearArticleCount(int year,int unitId)
	{
		final int y = year;
		final int uId = unitId;
		final Long ret = this.getSession().doReturningWork(new ReturningWork<Long>() {
				public Long execute(Connection connection)
						throws SQLException {
					CallableStatement cs = connection.prepareCall("{?=Call CountYearArticleUnit(?,?)}");
					cs.registerOutParameter(1,java.sql.Types.INTEGER); 
					cs.setInt(2, y);
					cs.setInt(3, uId);
					cs.execute();
					return cs.getLong(1);
				}				
			});
		return Long.valueOf(ret.toString()).longValue();
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	public void statYearArticleCount(int year) {
		final int y = year;

		this.getSession().doWork(new Work() {
							public void execute(Connection connection)
									throws SQLException {
								CallableStatement cs = connection.prepareCall("{Call CountYearArticle(?)}");
								cs.setInt(1, y);
								cs.execute();
							}
						});
	}
	
	public void updateUnitInfo(int userId, int unitId)
	{
		Unit unit = (Unit)this.getSession().get(Unit.class, unitId);
		if(unit == null) return;
		User user = (User)this.getSession().get(User.class, userId);
		if(user == null) return;
		if(user.getUnitId() != unitId) return;
		final int uid = userId;
		final int utId = unitId;
		final String utPath = unit.getUnitPathInfo();
		
		this.getSession().doWork(new Work() {
							public void execute(Connection connection)
									throws SQLException {
								CallableStatement cs = connection
										.prepareCall("{Call UpdateUnitInfo(?,?,?)}");
								cs.setInt(1, uid);
								cs.setInt(2, utId);
								cs.setString(3, utPath);
								cs.execute();
							}
						});
	}
	
	public void slpitArticleTable(int year) {
		final int y = year;	
		this.getSession().doWork(new Work() {
							public void execute(Connection connection)
									throws SQLException {
								CallableStatement cs = connection
										.prepareCall("{Call CopyData(?)}");
								cs.setInt(1, y);
								cs.execute();
							}
						});
	}
}