package cn.edustar.jitar.dao.hibernate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.jdbc.Work;
import org.springframework.transaction.annotation.Transactional;

import cn.edustar.jitar.dao.StatsCountDao;

@Transactional
public class StatsCountDaoHibernate extends BaseDaoHibernate
		implements
			StatsCountDao {

	public void statsUnit(final int unitId) {
		this.getSession().doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				CallableStatement cs = connection
						.prepareCall("{Call StatsUnit(?)}");
				cs.setInt(1, unitId);
				cs.execute();
			}
		});
	}

}
