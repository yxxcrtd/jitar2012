package cn.edustar.jitar.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;

import cn.edustar.jitar.dao.OnLineDao;
import cn.edustar.jitar.pojos.UserOnLine;

/**
 * 在线DAO接口的实现
 *
 * @author Yang xinxin
 * @version 1.0.0 Mar 26, 2009 10:05:21 PM
 */
public class OnLineDaoHibernate extends BaseDaoHibernate implements OnLineDao {
	
	/** 根据用户名得到在线用户对象 */
	private static final String FIND_USERONLINE_BY_USERNAME = "FROM UserOnLine WHERE userName = ?";
	
	/** 删除所有超时的在线用户 */
	private static final String REMOVE_ALL_OUTTIME = "DELETE FROM UserOnLine WHERE onlineTime < ?";
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.OnLineDao#findOnLineUser(long)
	 */
	@SuppressWarnings("unchecked")
	public List<UserOnLine> findOnLineUser(final long optTime) {
		final Session session = this.getSession();
		return session.doReturningWork(new ReturningWork<List<UserOnLine>>(){
			public List<UserOnLine> execute(Connection connection) throws SQLException {
				Criteria c = session.createCriteria(UserOnLine.class);
				c.add(Restrictions.ge("onlineTime", new Long(optTime)));
				c.add(Restrictions.gt("userId", 0));
				return c.list();
			}
		});
	}
	
	/*
	 * Restrictions.eq		＝
	 * Restrictions.allEq	利用Map来进行多个等于的限制
	 * Restrictions.gt		＞
	 * Restrictions.ge		＞＝
	 * Restrictions.lt		＜
	 * Restrictions.le		＜＝
	 * Restrictions.between	BETWEEN
	 * Restrictions.like		LIKE
	 * Restrictions.in		in
	 * Restrictions.and		and
	 * Restrictions.or		or
	 * Restrictions.sqlRestriction	用SQL限定查询
	 */

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.OnLineDao#getOnLineUesrNum(long)
	 */
	@SuppressWarnings("unchecked")
	public long getOnLineUesrNum(final long optTime) {
		final Session session = this.getSession();
		return session.doReturningWork(new ReturningWork<Long>()  {
			public Long execute(Connection connection) throws SQLException {
			Criteria c = session.createCriteria(UserOnLine.class);
			c.setProjection(Projections.count("id"));
			c.add(Restrictions.ge("onlineTime", new Long(optTime)));
			c.add(Restrictions.gt("userId", 0));
			List<Long> list =  c.list();

			if (list == null || list.isEmpty() || list.size() == 0) {
				return 0l;
			} else {
				return Long.valueOf(list.get(0));
			}
			}
			});
		
	}

	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.OnLineDao#getOnLineGuestNum(long)
	 */
	@SuppressWarnings("unchecked")
	public long getOnLineGuestNum(final long optTime) {
		final Session session = this.getSession();		
		return session.doReturningWork(new ReturningWork<Long>()  {
			public Long execute(Connection connection) throws SQLException {
				Criteria c = session.createCriteria(UserOnLine.class);
				c.setProjection(Projections.count("id"));
				c.add(Restrictions.ge("onlineTime", new Long(optTime)));
				c.add(Restrictions.eq("userId", 0));
				List<Long> list = c.list();
				if (list == null || list.isEmpty()) {
					return 0L;
				} else {
					return Long.valueOf(list.get(0));
				}
			}
		});
		
	}
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.OnLineDao#saveUserOnLine(cn.edustar.jitar.pojos.UserOnLine)
	 */
	public void saveUserOnLine(UserOnLine userOnLine) {
		this.getSession().saveOrUpdate(userOnLine);
	}
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.OnLineDao#findUserOnLineByUserName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public UserOnLine findUserOnLineByUserName(String userName) {
		List<UserOnLine> list = this.getSession().createQuery(FIND_USERONLINE_BY_USERNAME).setString(0, userName).list();
		if (list == null || list.isEmpty()) {
			return null;
		} else {
			return (UserOnLine) list.get(0);
		}
	}
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.OnLineDao#removeUserOnLine(cn.edustar.jitar.pojos.UserOnLine)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void removeUserOnLine(final UserOnLine userOnLine) {
		final String sql = "DELETE FROM U_UserOnLine WHERE Id=?";					
				this.getSession().doWork(new Work() {
					public void execute(Connection connection)
							throws SQLException {
						PreparedStatement ps = connection.prepareStatement(sql);
						ps.setInt(1, userOnLine.getId());
						ps.execute();
					}
				});
		
		//以下的删除会报告错误？？ org.hibernate.StaleStateException: Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1
		//this.getSession().delete(userOnLine);
		//this.getSession().flush();
	}
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.OnLineDao#removeUserOnLineOutTime(long)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void removeUserOnLineOutTime(final long optTime) {
		final Session session = this.getSession();
		session.doWork(new Work() {
			public void execute(Connection connection)
					throws SQLException {
				Query query = session.createQuery(REMOVE_ALL_OUTTIME);
				query.setLong(0, optTime);
				query.executeUpdate();
			}
		});
	}

}
