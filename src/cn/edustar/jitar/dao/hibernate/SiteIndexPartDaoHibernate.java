package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import cn.edustar.jitar.dao.SiteIndexPartDao;
import cn.edustar.jitar.pojos.SiteIndexPart;

public class SiteIndexPartDaoHibernate extends BaseDaoHibernate implements SiteIndexPartDao {

    public void deleteSiteIndexPart(SiteIndexPart siteIndexPart) {
        this.getSession().delete(siteIndexPart);
    }

    @SuppressWarnings("unchecked")
    public List<SiteIndexPart> getSiteIndexPartList(Boolean displayState) {
        String queryString;
        if (displayState == null) {
            queryString = "FROM SiteIndexPart Order By moduleZone,moduleOrder";
        } else {
            if (displayState) {
                queryString = "FROM SiteIndexPart Where moduleDisplay = 1 Order By moduleZone,moduleOrder";
            } else {
                queryString = "FROM SiteIndexPart Where moduleDisplay = 0 Order By moduleZone,moduleOrder";
            }
        }

        return (List<SiteIndexPart>) this.getSession().createQuery(queryString).list();
    }

    public void saveOrUpdateSiteIndexPart(SiteIndexPart siteIndexPart) {
        this.getSession().saveOrUpdate(siteIndexPart);
    }

    public SiteIndexPart getSiteIndexPartById(int siteIndexPartId) {
        return (SiteIndexPart) this.getSession().get(SiteIndexPart.class, siteIndexPartId);
    }

    @SuppressWarnings("unchecked")
    public SiteIndexPart getSiteIndexPartByModuleName(String siteIndexModuleName) {
        String queryString = "FROM SiteIndexPart Where moduleName = ? And partType = 100";
        List<SiteIndexPart> sp = (List<SiteIndexPart>) this.getSession().createQuery(queryString).setString(0, siteIndexModuleName).list();
        if (sp.size() > 0)
            return sp.get(0);
        else
            return null;
    }    
}
