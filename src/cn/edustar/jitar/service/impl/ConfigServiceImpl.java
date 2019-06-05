package cn.edustar.jitar.service.impl;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

import cn.edustar.jitar.dao.ConfigDao;
import cn.edustar.jitar.model.Configure;
import cn.edustar.jitar.pojos.Config;
import cn.edustar.jitar.service.ConfigService;

/**
 * 教研系统配置信息的服务实现 * 
 * @author Yang Xinxin
 * @version 1.0.0 Feb 17, 2009 11:24:50 AM
 */
public class ConfigServiceImpl implements ConfigService, ServletContextAware {
	
	/** WebApp ServletContext */
	private ServletContext servlet_ctxt;
	
	/** 配置使用的数据库访问实现 */
	private ConfigDao conf_dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servlet_ctxt = servletContext;
	}

	/** 配置使用的数据库访问实现 */
	public void setConfigDao(ConfigDao conf_dao) {
		this.conf_dao = conf_dao;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.ConfigService#getConfigure()
	 */
	public Configure getConfigure() {
		// 1. 从 servlet context 中获取当前配置对象.
		Configure config = (Configure)servlet_ctxt.getAttribute(CONFIG_KEY_NAME);
		if (config != null) return config;
		
		// 2. 如果没有则现在立刻加载, 并保存在 ServletContext 里面.
		return loadSystemConfig();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ConfigService#getConfigById(int)
	 */
	public Config getConfigById(int id) {
		return conf_dao.getConfigById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ConfigService#getConfigByItemTypeAndName(java.lang.String, java.lang.String)
	 */
	public Config getConfigByItemTypeAndName(String itemType, String name) {
		return conf_dao.getConfigByItemTypeAndName(itemType, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ConfigService#createConfig(cn.edustar.jitar.pojos.Config)
	 */
	public void createConfig(Config config) {
		this.conf_dao.createConfig(config);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ConfigService#updateConfig(cn.edustar.jitar.pojos.Config)
	 */
	public void updateConfig(Config config) {
		this.conf_dao.updateConfig(config);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ConfigService#deleteConfig(cn.edustar.jitar.pojos.Config)
	 */
	public void deleteConfig(Config config) {
		this.conf_dao.deleteConfig(config);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ConfigService#reloadConfig()
	 */
	public void reloadConfig() {
		this.loadSystemConfig();
	}

	/**
	 * 立刻从数据库中加载配置，并放到'servletContext'里面
	 * 
	 * @return
	 */
	private synchronized Configure loadSystemConfig() {
		List<Config> conf_list = conf_dao.getConfigList("jitar");
		Configure config = new Configure("jitar", conf_list);
		
		servlet_ctxt.setAttribute(CONFIG_KEY_NAME, config);
		return config;
	}
	
	/**
	 * 得到所有'boolean'型的数据
	 *
	 * @return
	 */
	public List<Config> getSysConfigList() {
		return conf_dao.getSysConfigList();
	}
	
}
