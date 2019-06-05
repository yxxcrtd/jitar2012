package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.jitar.pojos.SiteNav;

/**
 * @author 孟宪会
 *
 */
public interface SiteNavDao {

	/** 更新一个导航项目 */
	public void saveOrUpdateSiteNav(SiteNav siteNav);
	
	/** 删除一个导航项目 */
	public void deleteSiteNav(SiteNav siteNav);
	
	/** 编辑一个导航项目的外部链接方式 */
	public void setSiteNavIsExternalLink(SiteNav siteNav,boolean isExternalLink);
	
	/** 设置导航项目的显示状态 */
	public void setSiteNavDisplayState(SiteNav siteNav,boolean displayState);
	
	/** 得到给定类型、给定类型标识的全部导航条目，并根据是否显示属性过滤 */
	public List<SiteNav> getAllSiteNav(boolean showAll,int ownerType, int ownerId);
	
	/** 加载给定标识的导航项目 */
	public SiteNav getSiteNavById(int siteNavId);
	
	/** 删除某类导航的全部导航条目 */
	public void deleteSiteNavOfOwnerType(int ownerType, Integer ownerId, Boolean isExternalLink);
	
	/** 根据名称来得到，是为了防止重复添加 */
	public SiteNav getSiteNavByName(int ownerType, Integer ownerId, String siteNavName);
}