package cn.edustar.jitar.data;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.edustar.data.hql.HqlHelperBase;
import cn.edustar.data.hql.SqlQuery;
import cn.edustar.jitar.JitarContext;

/**
 * Command 基类.
 *
 *
 */
class AbstractCommand extends HqlHelperBase implements SqlQuery {
	private String sql = "";
	
	/** 返回的模式字符串, 可能为 null */
	private String schema = null;

	/**
	 * 缺省构造函数.
	 */
	public AbstractCommand() {
		
	}
	
	/**
	 * 使用指定 sql 语句构造.
	 * @param sql
	 */
	public AbstractCommand(String sql) {
		this.sql = sql;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.data.hql.SqlQuery#getSql()
	 */
	public String getSql() {
		return this.sql;
	}
	
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.data.hql.SqlQuery#initQuery(org.hibernate.Query)
	 */
	public void initQuery(Query q) {
		super.initQuery(q);
	}

	
	/**
	 * 得到 schema 字符串.
	 * @return
	 */
	protected String calcSchemaString() {
		if (this.schema == null || this.schema.length() == 0)
			return tryParseSchema();
		return this.schema;
	}
	
	// 至少需要语句中有 select from 两个关键字, 且不识别复杂的 SQL 语法.
	private String tryParseSchema() {
		List<String> token_list = new ArrayList<String>();
		
		StringTokenizer parser = new StringTokenizer(sql);
		while (parser.hasMoreTokens()) {
			String token = parser.nextToken();
			token_list.add(token);
		}
		
		// find 'SELECT', 'FROM'
		int s1 = findToken(token_list, "SELECT");
		int f2 = findToken(token_list, "FROM");
		
		if (s1 < 0 || f2 < 0) return "";
		
		// 合并 s1-f2 区间所有项目
		String fields = concat(token_list, s1+1, f2);
		return fields;
	}
	
	private static int findToken(List<String> token_list, String token) {
		for (int i = 0; i < token_list.size(); ++i) {
			if (token_list.get(i).equalsIgnoreCase(token))
				return i;
		}
		return -1;
	}
	
	private static String concat(List<String> token_list, int start, int end) {
		String result = "";
		for (int i = start; i < end; ++i)
			result += token_list.get(i) + " ";
		return result;
	}

	/**
	 * 得到一个数据库连接.
	 * @return
	 */
	protected Database getDatabase() {
		return new Database();
	}

	/**
	 * 辅助用的类.
	 *
	 *
	 */
	protected class Database {
		private SessionFactory sf;
		public Database() {
			JitarContext jtr = JitarContext.getCurrentJitarContext();
			sf = jtr.getSessionFactory();
		}
		@SuppressWarnings("unchecked")
		public List executeQuery(SqlQuery query, int firstResult, int maxResults) {
			Session session = sf.openSession();
			try {
				Query q = session.createQuery(query.getSql());
				// SQLQuery q = session.createSQLQuery(query.getSql());
				query.initQuery(q);
				if (firstResult > -1)
					q.setFirstResult(firstResult);
				if (maxResults > -1)
					q.setMaxResults(maxResults);
				return q.list();
			} finally {
				session.close();
			}
		}
		
		@SuppressWarnings("unchecked")
		public List executeSQLQuery(SqlQuery query) {
			Session session = sf.openSession();
			try {
				SQLQuery q = session.createSQLQuery(query.getSql());
				query.initQuery(q);
				return q.list();
			} finally {
				session.close();
			}
		}
		public Object uniqueResult(SqlQuery query) {
			Session session = sf.openSession();
			try {
				Query q = session.createQuery(query.getSql());
				query.initQuery(q);
				return q.uniqueResult();
			} finally {
				session.close();
			}
		}
		public int executeUpdate(SqlQuery query) {
			Session session = sf.openSession();
			try {
				Query q = session.createQuery(query.getSql());
				query.initQuery(q);
				return q.executeUpdate();
			} finally {
				session.close();
			}
		}
	}
}
