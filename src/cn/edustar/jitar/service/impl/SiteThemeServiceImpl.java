package cn.edustar.jitar.service.impl;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

import cn.edustar.jitar.dao.SiteThemeDao;
import cn.edustar.jitar.pojos.SiteTheme;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.SiteThemeService;

/**
 * 网站样式路径服务类。
 * @author mxh
 *
 */
public class SiteThemeServiceImpl implements SiteThemeService, ServletContextAware {

	private SiteThemeDao siteThemeDao;
	private CacheService cacheService;
	private ServletContext servlet_ctxt;
	
	
	public void init() {
		this.getCurrentSiteTheme();
	}

	public String getSiteThemeUrl() {
		String siteThemeUrl = "";
		if (cacheService.get("siteTheme") != null) {
			siteThemeUrl = cacheService.get("siteTheme").toString();
		} else {
			SiteTheme siteTheme = this.siteThemeDao.getCurrentSiteTheme();
			if (siteTheme == null) {
				siteThemeUrl = this.servlet_ctxt.getContextPath() + "/skin/default/";
			} else {
				siteThemeUrl = this.servlet_ctxt.getContextPath() + "/skin/" + siteTheme.getFolder() + "/";
			}
			cacheService.put("siteTheme", siteThemeUrl);
			}
			return siteThemeUrl;
	}

	public SiteTheme getCurrentSiteTheme() {
		SiteTheme siteTheme = this.siteThemeDao.getCurrentSiteTheme();
		if (siteTheme == null) {
			// 以下几行代码有副作用
			// siteTheme = new SiteTheme();
			// siteTheme.setFolder("index");
			// siteTheme.setTitle("默认主题");
			// 确保此文件夹不被删除
			servlet_ctxt.setAttribute("SiteThemeUrl",this.servlet_ctxt.getContextPath() + "/skin/default/");
		} else {
			servlet_ctxt.setAttribute("SiteThemeUrl",this.servlet_ctxt.getContextPath() + "/skin/" + siteTheme.getFolder() + "/");
		}
		 servlet_ctxt.setAttribute("SiteTheme", siteTheme);
		// 直接加载Url里面，上面的方法暂时作废
		return siteTheme;
	}

	public void resetDefaultSiteTheme() {
		cacheService.remove("siteTheme");
		this.siteThemeDao.resetDefaultSiteTheme();
		servlet_ctxt.setAttribute("SiteThemeUrl",this.servlet_ctxt.getContextPath() + "/skin/default/");
	}

	public List<SiteTheme> getAllTheme() {
		return this.siteThemeDao.getAllTheme();
	}

	public SiteTheme getSiteThemeById(int siteThemeId) {
		return this.siteThemeDao.getSiteThemeById(siteThemeId);
	}

	public SiteTheme getSiteThemeByFolderName(String folder) {
		return this.siteThemeDao.getSiteThemeByFolderName(folder);
	}

	public void deleteSiteTheme(SiteTheme siteTheme) {
		cacheService.remove("siteTheme");
		this.siteThemeDao.deleteSiteTheme(siteTheme);
	}

	public void saveOrUpdateSiteTheme(SiteTheme siteTheme) {
		cacheService.remove("siteTheme");
		this.siteThemeDao.saveOrUpdateSiteTheme(siteTheme);
	}

	public SiteThemeDao getSiteThemeDao() {
		return siteThemeDao;
	}

	public void setSiteThemeDao(SiteThemeDao siteThemeDao) {
		this.siteThemeDao = siteThemeDao;
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servlet_ctxt = servletContext;
	}

	public ServletContext getServletContext() {
		return this.servlet_ctxt;
	}
}
