package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.edustar.jitar.dao.SiteNavDao;
import cn.edustar.jitar.pojos.SiteNav;

/**
 * @author 孟宪会
 *
 */
@Transactional
public class SiteNavDaoHibernate extends BaseDaoHibernate implements SiteNavDao {
	public void saveOrUpdateSiteNav(SiteNav siteNav) {
		this.getSession().saveOrUpdate(siteNav);
		this.getSession().flush();
	}

	public void deleteSiteNav(SiteNav siteNav) {
		this.getSession().delete(siteNav);
	}

	public void setSiteNavIsExternalLink(SiteNav siteNav, boolean isExternalLink) {
		String queryString = "UPDATE SiteNav Set isExternalLink =:isExternalLink Where siteNavId =:siteNavId ";
		this.getSession().createQuery(queryString).setBoolean("isExternalLink", isExternalLink).setInteger("siteNavId", siteNav.getSiteNavId()).executeUpdate();
	}

	public void setSiteNavDisplayState(SiteNav siteNav, boolean displayState) {
		String queryString = "UPDATE SiteNav Set siteNavIsShow =:siteNavIsShow Where siteNavId =:siteNavId";
		this.getSession().createQuery(queryString).setBoolean("siteNavIsShow", displayState).setInteger("siteNavId", siteNav.getSiteNavId()).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<SiteNav> getAllSiteNav(boolean showAll,int ownerType, int ownerId){
		String queryString;
		if(showAll)
			queryString= "FROM SiteNav Where ownerType=:ownerType And ownerId =:ownerId Order BY siteNavItemOrder ASC";
		else
			queryString= "FROM SiteNav Where siteNavIsShow = 1 And ownerType=:ownerType And ownerId =:ownerId Order BY siteNavItemOrder ASC";
		return (List<SiteNav>)this.getSession().createQuery(queryString).setInteger("ownerType", ownerType).setInteger("ownerId", ownerId).list();
	}
	
	public SiteNav getSiteNavById(int siteNavId){
		return (SiteNav)this.getSession().get(SiteNav.class, siteNavId);
	}
	
	
	/** 根据名称来得到，是为了防止重复添加 */
    @SuppressWarnings("unchecked")
    public SiteNav getSiteNavByName(int ownerType, Integer ownerId, String siteNavName){
       String queryString= "FROM SiteNav Where ownerType=:ownerType And ownerId =:ownerId And siteNavName = :siteNavName";
       List<SiteNav> sn = (List<SiteNav>)this.getSession().createQuery(queryString).setInteger("ownerType", ownerType).setInteger("ownerId", ownerId).setString("siteNavName", siteNavName).list();
       if(sn == null || sn.size() == 0){
           return null;
       }
       else
       {
           return (SiteNav)sn.get(0);
       }
    }
	
	/** 删除某类导航的全部导航条目 */
	public void deleteSiteNavOfOwnerType(int ownerType, Integer ownerId, Boolean isExternalLink){
		String queryString;
		if(isExternalLink == null)
		{
			if(ownerId == null){
				queryString= "DELETE FROM SiteNav Where ownerType=:ownerType";
				this.getSession().createQuery(queryString).setInteger("ownerType", ownerType).executeUpdate();
		}
			else{
				queryString= "DELETE FROM SiteNav Where ownerType=:ownerType And ownerId =:ownerId";
				this.getSession().createQuery(queryString).setInteger("ownerType", ownerType).setInteger("ownerId", ownerId).executeUpdate();
			}
		}
		else
		{
			if(isExternalLink == true)
			{
				if(ownerId == null){
					queryString= "DELETE FROM SiteNav Where ownerType=:ownerType And isExternalLink = 1";
					this.getSession().createQuery(queryString).setInteger("ownerType", ownerType).executeUpdate();
				}
				else{
					queryString= "DELETE FROM SiteNav Where ownerType=:ownerType And ownerId =:ownerId And isExternalLink = 1";
					this.getSession().createQuery(queryString).setInteger("ownerType", ownerType).setInteger("ownerId", ownerId).executeUpdate();
				}
			}
			else
			{
				if(ownerId == null){
					queryString= "DELETE FROM SiteNav Where ownerType=:ownerType And isExternalLink = 0";
					this.getSession().createQuery(queryString).setInteger("ownerType",ownerType).executeUpdate();
				}
				else{
					queryString= "DELETE FROM SiteNav Where ownerType=:ownerType And ownerId =:ownerId And isExternalLink = 0";
					this.getSession().createQuery(queryString).setInteger("ownerType", ownerType).setInteger("ownerId", ownerId).executeUpdate();
				}
			}
			
		}
	}
}