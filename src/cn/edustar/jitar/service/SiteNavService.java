package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.jitar.pojos.SiteNav;

/**
 * @author 孟宪会
 *
 */
public interface SiteNavService {
	public void saveOrUpdateSiteNav(SiteNav siteNav);
	public void deleteSiteNav(SiteNav siteNav);
	public void setSiteNavIsExternalLink(SiteNav siteNav, boolean isExternalLink);
	public void setSiteNavDisplayState(SiteNav siteNav,boolean displayState);
	public List<SiteNav> getAllSiteNav(boolean showAll,int ownerType, int ownerId);
	public void setNewSiteNav(int ownerType,int ownerId);
	public SiteNav getSiteNavById(int siteNavId);
	/** 根据名称来得到，是为了防止重复添加 */
    public SiteNav getSiteNavByName(int ownerType, Integer ownerId, String siteNavName);
	/** 删除某类导航的全部导航条目 */
	public void deleteSiteNavOfOwnerType(int ownerType, Integer ownerId, Boolean isExternalLink);
	public void init();
}
