package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import cn.edustar.jitar.dao.ConfigDao;
import cn.edustar.jitar.pojos.Config;

/**
 * 配置数据库访问实现
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Feb 17, 2009 11:22:35 AM
 */
public class ConfigDaoHibernate extends BaseDaoHibernate implements ConfigDao {

	/** 得到所有'boolean'型的数据 */
	private static final String GET_SYSTEM_CONFIG_LIST = "FROM Config C WHERE C.type = 'boolean'";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.iface.ConfigDao#getConfigList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Config> getConfigList(String itemType) {
		String hql = "FROM Config WHERE itemType = :itemType";
		return (List<Config>) this.getSession().createQuery(hql).setString("itemType", itemType).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ConfigDao#getConfigById(int)
	 */
	public Config getConfigById(int id) {
		return (Config) this.getSession().get(Config.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ConfigDao#getConfigByItemTypeAndName(java.lang.String,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Config getConfigByItemTypeAndName(String itemType, String name) {
		String hql = "FROM Config WHERE itemType = :itemType AND name = :name";
		List<Config> ol = this.getSession().createQuery(hql).setString("itemType", itemType).setString("name", name).list();
		if(ol == null || ol.size() == 0) return null;
		return (Config)ol.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ConfigDao#createConfig(cn.edustar.jitar.pojos.Config)
	 */
	public void createConfig(Config config) {
		this.getSession().save(config);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ConfigDao#updateConfig(cn.edustar.jitar.pojos.Config)
	 */
	public void updateConfig(Config config) {
		this.getSession().update(config);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.ConfigDao#deleteConfig(cn.edustar.jitar.pojos.Config)
	 */
	public void deleteConfig(Config config) {
		this.getSession().delete(config);
	}
	
	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.ConfigDao#getSysConfigList()
	 */
	@SuppressWarnings("unchecked")
	public List<Config> getSysConfigList() {
		return this.getSession().createQuery(GET_SYSTEM_CONFIG_LIST).list();
	}

	@Override
	public void evict(Object object) {
		this.getSession().evict(object);
	}

	@Override
	public void flush() {
		this.getSession().flush();
	}
	
}
