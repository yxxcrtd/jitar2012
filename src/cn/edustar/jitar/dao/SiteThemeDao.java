package cn.edustar.jitar.dao;

import cn.edustar.jitar.pojos.SiteTheme;
import java.util.List;

public interface SiteThemeDao  {
	public SiteTheme getCurrentSiteTheme();
	public List<SiteTheme> getAllTheme();
	public SiteTheme getSiteThemeById(int siteThemeId);
	public SiteTheme getSiteThemeByFolderName(String folder);
	public void deleteSiteTheme(SiteTheme siteTheme);
	public void saveOrUpdateSiteTheme(SiteTheme siteTheme);
	public void resetDefaultSiteTheme();
}
