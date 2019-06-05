package cn.edustar.jitar.service;

import java.util.List;

import javax.servlet.ServletContext;


import cn.edustar.jitar.pojos.SiteTheme;

public interface SiteThemeService {
	
	/** 站点配置键的名字, 在模板中通过 ${SiteTheme} 即可访问到. */
	public SiteTheme getCurrentSiteTheme();
	public List<SiteTheme> getAllTheme();
	public SiteTheme getSiteThemeById(int siteThemeId);
	public SiteTheme getSiteThemeByFolderName(String folder);
	public void deleteSiteTheme(SiteTheme siteTheme);
	public void saveOrUpdateSiteTheme(SiteTheme siteTheme);
	public ServletContext getServletContext();
	public void resetDefaultSiteTheme();
	public String getSiteThemeUrl();
	public void init();
}
