package cn.edustar.jitar.data;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.edustar.jitar.JitarContext;

/**
 * 能够执行指定 HQL 语句的 bean.
 * 
 *
 * 
 */
public class QueryBean {
	/** 数据库访问对象 */
	private Session session = null;

	/** 要执行的 HQL 语句 */
	private String hql;

	/**
	 * 缺省构造.
	 */
	public QueryBean() {
		SessionFactory sf = null;

		sf = JitarContext.getCurrentJitarContext().getSpringContext()
				.getBean("sessionFactory", SessionFactory.class);
		try {
			this.session = sf.getCurrentSession();
		} catch (Exception ex) {
			try {
				this.session = sf.openSession();
			} catch (Exception ex2) {
			}
		}
	}

	/**
	 * 使用指定的查询语句构造一个 QueryBean 的新实例.
	 * 
	 * @param hql
	 */
	public QueryBean(String hql) {
		this();
		this.hql = hql;
	}


	/**
	 * 执行查询并返回结果.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List open() {
		return this.session.createQuery(hql).list();
	}

}
