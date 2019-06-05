package cn.edustar.jitar.service.impl;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ServletContextAware;

import cn.edustar.jitar.dao.SiteNavDao;
import cn.edustar.jitar.pojos.SiteNav;
import cn.edustar.jitar.service.SiteNavService;

@Transactional
public class SiteNavServiceImpl implements SiteNavService, ServletContextAware {

	private ServletContext servlet_ctxt;
	private SiteNavDao siteNavDao;

	public SiteNavDao getSiteNavDao() {
		return siteNavDao;
	}

	public void setSiteNavDao(SiteNavDao siteNavDao) {
		this.siteNavDao = siteNavDao;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servlet_ctxt = servletContext;
	}

	public ServletContext getServletContext() {
		return this.servlet_ctxt;
	}

	
	public void init() {
		this.setNewSiteNav(0,0);
	}

	public void deleteSiteNav(SiteNav siteNav) {
		this.siteNavDao.deleteSiteNav(siteNav);

	}

	public void saveOrUpdateSiteNav(SiteNav siteNav) {
		this.siteNavDao.saveOrUpdateSiteNav(siteNav);

	}

	public void setSiteNavDisplayState(SiteNav siteNav, boolean displayState) {
		this.siteNavDao.setSiteNavDisplayState(siteNav, displayState);
	}

	public void setSiteNavIsExternalLink(SiteNav siteNav, boolean isExternalLink) {
		this.siteNavDao.setSiteNavIsExternalLink(siteNav, isExternalLink);

	}

	public List<SiteNav> getAllSiteNav(boolean showAll,int ownerType, int ownerId) {
		return this.siteNavDao.getAllSiteNav(showAll, ownerType, ownerId);
	}
	
	public void setNewSiteNav(int ownerType,int ownerId) {
		//只更新网站的页面导航
		List<SiteNav> siteNavList = this.siteNavDao.getAllSiteNav(false,0,0);
		if (siteNavList != null) {
			String platformType = this.servlet_ctxt.getInitParameter("platformType");
			if(platformType == null || !platformType.equals("1")){
				for(int i = siteNavList.size()-1;i>-1;i--){
					SiteNav sn = (SiteNav)siteNavList.get(i);
					if(sn.getSiteNavUrl().indexOf("mashup/show_mashup.py") > -1){
						siteNavList.remove(i);
					}
				}
			}
			this.servlet_ctxt.setAttribute("SiteNavList", siteNavList);
		}
	}
	
	public SiteNav getSiteNavById(int siteNavId){
		return this.siteNavDao.getSiteNavById(siteNavId);
	}
	/** 根据名称来得到，是为了防止重复添加 */
    public SiteNav getSiteNavByName(int ownerType, Integer ownerId, String siteNavName){
        return this.siteNavDao.getSiteNavByName(ownerType, ownerId, siteNavName);
    }
    
	/** 删除某类导航的全部导航条目 */
	public void deleteSiteNavOfOwnerType(int ownerType, Integer ownerId, Boolean isExternalLink){
		this.siteNavDao.deleteSiteNavOfOwnerType(ownerType, ownerId, isExternalLink);
	}
}
