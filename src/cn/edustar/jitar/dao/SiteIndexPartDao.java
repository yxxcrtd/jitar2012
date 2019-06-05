package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.jitar.pojos.SiteIndexPart;

public interface SiteIndexPartDao {

	public void saveOrUpdateSiteIndexPart(SiteIndexPart siteIndexPart);
	public void deleteSiteIndexPart(SiteIndexPart siteIndexPart);
	public List<SiteIndexPart> getSiteIndexPartList(Boolean displayState);
	public SiteIndexPart getSiteIndexPartById(int siteIndexPartId);
	public SiteIndexPart getSiteIndexPartByModuleName(String siteIndexModuleName);

}