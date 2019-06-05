package cn.edustar.jitar.model;

import cn.edustar.jitar.service.SiteNavigationService;

public class SubjectNavModel implements TemplateModelObject {
	private SiteNavigationService siteNavigationService;

	public String getVariableName() {
		this.siteNavigationService.renderSiteNavition();
		return "SubjectNav";
	}

	public void setSiteNavigationService(SiteNavigationService siteNavigationService) {
		this.siteNavigationService = siteNavigationService;
	}
	
	public SubjectNavModel() {
	}

}
