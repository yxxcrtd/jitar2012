package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.jitar.dao.SiteIndexPartDao;
import cn.edustar.jitar.pojos.SiteIndexPart;
import cn.edustar.jitar.service.SiteIndexPartService;

public class SiteIndexPartServiceImpl implements SiteIndexPartService {

    public SiteIndexPartDao siteIndexPartDao;

    public void setSiteIndexPartDao(SiteIndexPartDao siteIndexPartDao) {
        this.siteIndexPartDao = siteIndexPartDao;
    }

    public void deleteSiteIndexPart(SiteIndexPart siteIndexPart) {
        this.siteIndexPartDao.deleteSiteIndexPart(siteIndexPart);
    }

    public List<SiteIndexPart> getSiteIndexPartList(Boolean displayState) {
        return this.siteIndexPartDao.getSiteIndexPartList(displayState);
    }

    public void saveOrUpdateSiteIndexPart(SiteIndexPart siteIndexPart) {
        this.siteIndexPartDao.saveOrUpdateSiteIndexPart(siteIndexPart);
    }

    public SiteIndexPart getSiteIndexPartByModuleName(String siteIndexModuleName) {
        return this.siteIndexPartDao.getSiteIndexPartByModuleName(siteIndexModuleName);
    }

    public SiteIndexPart getSiteIndexPartById(int siteIndexPartId) {
        return this.siteIndexPartDao.getSiteIndexPartById(siteIndexPartId);
    }

}
