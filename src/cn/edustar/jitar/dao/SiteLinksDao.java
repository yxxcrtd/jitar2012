package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.jitar.pojos.SiteLinks;

public interface SiteLinksDao {

	/**
	 * 得到全部的友情链接
	 * @param objectType
	 * @return
	 */
	public List<SiteLinks> getSiteLinksList(String objectType, int objectId);
	
	/**
	 * 更新一个友情链接
	 * @param siteLinks
	 */
	public void saveOrUpdate(SiteLinks siteLinks);
	
	/**
	 * 删除一个友情链接
	 * @param siteLinks
	 */
	public void delete(SiteLinks siteLinks);
	
	/**
	 * 加载一个对象
	 * @param siteLinksId
	 * @return
	 */
	public SiteLinks getSiteLinks(int siteLinksId);
	
}
