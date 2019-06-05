package cn.edustar.jitar.manage.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.jdbc.Work;

import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.hibernate.BaseDaoHibernate;
import cn.edustar.jitar.manage.UserStatManage;
import cn.edustar.jitar.pojos.UserStat;
import cn.edustar.jitar.query.UserStatQueryParam;

/**
 * @author Yang XinXin
 * @version 1.0.0, 2009-09-04 15:32:58
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class UserStatManageImpl extends BaseDaoHibernate implements	UserStatManage {
	public List<UserStat> getUserStatList(UserStatQueryParam param, Pager pager) {
		QueryHelper query = param.createQuery();		
		if (pager == null)
			return query.queryData(this.getSession(), -1, param.count);
		else
			return query.queryDataAndTotalCount(this.getSession(), pager);
	}

	public List<UserStat> getUserListByLoginName(String loginName) {
		return this.getSession().createQuery("from UserStat where loginName like '%" + loginName + "%'").list();
	}

	public UserStat getUserStatById(int userId) {
		List list = getSession().createQuery("FROM UserStat WHERE userId = :userId").setInteger("userId", userId).list();
		if (list != null && list.size() >= 1) {
			return (UserStat) list.get(0);
		}
		return null;
	}

	public List<UserStat> getUserStatList() {
		return this.getSession().createQuery("FROM UserStat").list();
	}


	public void statAllUser() {
		this.getSession().doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				CallableStatement cs = connection.prepareCall("{call statAllUser()}");
				cs.executeUpdate();
			}
		});
	}

	public void updateUserStat(final String key, final String beginDate,
			final String endDate, final int subjectId, final int gradeId,
			final  int unitId, final int teacherType, String...statGuid) {
			final String sg;
			if(statGuid !=null && statGuid.length > 0)
			{
				sg = statGuid[0];
			}
			else
			{
				sg = "";
			}
			this.getSession().doWork(new Work() {
				public void execute(Connection connection) throws SQLException {
				CallableStatement cs = connection.prepareCall("{call statAllUserStat(?, ?, ?, ?, ?, ?, ?, ?)}");
				cs.setString(1, key);
				cs.setString(2, beginDate);
				cs.setString(3, endDate);
				cs.setInt(4, subjectId);
				cs.setInt(5, gradeId);
				cs.setInt(6, unitId);
				cs.setInt(7, teacherType);
				cs.setString(8, sg);				
				cs.executeUpdate();
				}
		});
	}
}
