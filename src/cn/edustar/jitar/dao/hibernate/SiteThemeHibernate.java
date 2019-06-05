package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import org.hibernate.Session;

import org.springframework.transaction.annotation.Transactional;

import cn.edustar.jitar.dao.SiteThemeDao;
import cn.edustar.jitar.pojos.SiteTheme;

@Transactional
public class SiteThemeHibernate extends BaseDaoHibernate implements SiteThemeDao {

	/**
	 * 获取当前设置的默认主题
	 */

	@SuppressWarnings("unchecked")
	public SiteTheme getCurrentSiteTheme() {
		String queryString = "From SiteTheme Where status = 1";

		Session session = this.getSession();
		List<SiteTheme> sites = (List<SiteTheme>) session.createQuery(queryString).list();

		if (sites.size() > 0) {
			return (SiteTheme) sites.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<SiteTheme> getAllTheme() {
		String queryString = "From SiteTheme";
		Session session = this.getSession();
		return (List<SiteTheme>) session.createQuery(queryString).list();
	}

	public SiteTheme getSiteThemeById(int siteThemeId) {
		Session session = this.getSession();
		return (SiteTheme) session.get(SiteTheme.class, siteThemeId);
	}

	public SiteTheme getSiteThemeByFolderName(String folder) {
		String queryString = "From SiteTheme Where folder = :folder";
		Session session = this.getSession();
		return (SiteTheme) session.createQuery(queryString).setString("folder", folder).uniqueResult();
	}

	public void deleteSiteTheme(SiteTheme siteTheme) {
		Session session = this.getSession();
		session.delete(siteTheme);
	}

	public void saveOrUpdateSiteTheme(SiteTheme siteTheme) {
		Session session = this.getSession();
		session.saveOrUpdate(siteTheme);
	}

	public void resetDefaultSiteTheme() {
		String queryString = "UPDATE SiteTheme Set status = 0";
		Session session = this.getSession();
		session.createQuery(queryString).executeUpdate();
	}

}
