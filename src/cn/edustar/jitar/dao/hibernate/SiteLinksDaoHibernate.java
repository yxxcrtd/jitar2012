package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import cn.edustar.jitar.dao.SiteLinksDao;
import cn.edustar.jitar.pojos.SiteLinks;

public class SiteLinksDaoHibernate extends BaseDaoHibernate implements SiteLinksDao {
	
	public void delete(SiteLinks siteLinks) {
		this.getSession().delete(siteLinks);
	}

	@SuppressWarnings("unchecked")
	public List<SiteLinks> getSiteLinksList(String objectType, int objectId){
		String queryString;
		queryString  = "FROM SiteLinks Where objectType = :objectType And objectId = :objectId Order By linkId DESC";
		return (List<SiteLinks>)this.getSession().createQuery(queryString).setString("objectType", objectType).setInteger("objectId", objectId).list();
	}

	public void saveOrUpdate(SiteLinks siteLinks) {
		this.getSession().saveOrUpdate(siteLinks);

	}
	public SiteLinks getSiteLinks(int siteLinksId)
	{
		return (SiteLinks)this.getSession().get(SiteLinks.class, siteLinksId);
	}

}
