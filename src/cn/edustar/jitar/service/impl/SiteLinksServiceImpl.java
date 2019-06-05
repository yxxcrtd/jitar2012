package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.jitar.dao.SiteLinksDao;
import cn.edustar.jitar.pojos.SiteLinks;
import cn.edustar.jitar.service.SiteLinksService;

public class SiteLinksServiceImpl implements SiteLinksService {

	private SiteLinksDao siteLinksDao;

	public void delete(SiteLinks siteLinks) {		
		this.siteLinksDao.delete(siteLinks);
	}

	public SiteLinks getSiteLinks(int siteLinksId) {
		
		return this.siteLinksDao.getSiteLinks(siteLinksId);
	}

	public List<SiteLinks> getSiteLinksList(String objectType, int objectId) {
		
		return this.siteLinksDao.getSiteLinksList(objectType, objectId);
	}

	public void saveOrUpdate(SiteLinks siteLinks) {
		this.siteLinksDao.saveOrUpdate(siteLinks);

	}

	public void setSiteLinksDao(SiteLinksDao siteLinksDao) {
		this.siteLinksDao = siteLinksDao;
	}
}
