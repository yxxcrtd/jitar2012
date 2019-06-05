package cn.edustar.jitar.dao.hibernate;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseDaoHibernate {
	
	private SessionFactory sessionFactory;

	public Logger log = LoggerFactory.getLogger(BaseDaoHibernate.class);
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public Session getSession() {
		Session session = null;
		try
		{
			session = sessionFactory.getCurrentSession();
		}
		catch(HibernateException ex)
		{
			log.error("getCurrentSession() 不能得到，可能需要修改代码了。错误信息：" + ex.getLocalizedMessage());
			session = sessionFactory.openSession();
		}
		
		return session;
	}
}
