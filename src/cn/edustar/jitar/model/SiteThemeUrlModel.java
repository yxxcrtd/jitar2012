package cn.edustar.jitar.model;

import cn.edustar.jitar.service.SiteThemeService;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

/**
 * 网站样式路径。
 * @author mxh
 *
 */
public class SiteThemeUrlModel implements TemplateScalarModel, TemplateModelObject{

    private SiteThemeService siteThemeService;
	public String getVariableName() {
		return "SiteThemeUrl";
	}

	public String getAsString() throws TemplateModelException {
		return getSiteThemeUrl();
	}

	private String getSiteThemeUrl() {
		return this.siteThemeService.getSiteThemeUrl();
	}

	public void setSiteThemeService(SiteThemeService siteThemeService) {
		this.siteThemeService = siteThemeService;
	}
	public String toString() {
        return getSiteThemeUrl();
    }
}
