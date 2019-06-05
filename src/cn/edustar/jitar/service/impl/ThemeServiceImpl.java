package cn.edustar.jitar.service.impl;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

import cn.edustar.jitar.model.ThemeModel;
import cn.edustar.jitar.service.ThemeService;


/**
 * 模板方案的缺省实现。
 * 
 *
 *
 */
public class ThemeServiceImpl implements ThemeService, ServletContextAware {
	/** WebApp ServletContext 环境对象。 */
	private ServletContext servlet_ctxt;

	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servlet_ctxt = servletContext;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.ThemeService#getUserDefaultTheme()
	 */
	public ThemeModel getUserDefaultTheme() {
		ThemeModel theme = new ThemeModel();
		String theme_url = "/WEB-INF/user/default/";
		String theme_path = servlet_ctxt.getRealPath(theme_url);
		try {
			theme.init(theme_path, "/WEB-INF/user/default/");
		} catch (IOException ex) {
			// TODO: logger.error
			// TODO: make user know ???
		}
		
		// TODO: cache the theme and how to reload.
		return theme;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.ThemeService#clearCache()
	 */
	public void clearCache() {
		// TODO: if using cache, the clear it.
	}

}
