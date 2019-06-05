package cn.edustar.jitar.model;

import cn.edustar.jitar.service.ConfigService;

/**
 * 表示教研站点配置的对象模型, 从该模型中能够获得 name, title, url 等配置信息.  *
 * 
 */
public class SiteModel implements TemplateModelObject {
	private ConfigService cfg_svc;
	
	public String getVariableName() {
		return "Site";
	}
	
	public void setConfigService(ConfigService cfg_svc) {
		this.cfg_svc = cfg_svc;
	}
	
	/**
	 * ${Site.name} - 得到站点名字, 实际是从 config['site.name'] 中获得.
	 * @return
	 */
	public String getName() {
		return cfg_svc.getConfigure().getStringValue("site.name");
	}
	
	/**
	 * ${Site.title} - 得到站点标题, 实际是从 config['site.title'] 中获得.
	 * @return
	 */
	public String getTitle() {
		return cfg_svc.getConfigure().getStringValue("site.title");
	}
	
	public String getFooter() {
        return cfg_svc.getConfigure().getStringValue("site.copyright");
    }
	
	public Boolean getSiteAutoHtml() {
		return cfg_svc.getConfigure().getBoolValue("site.auto.html",true);
	}
	
	/**
	 * ${Site.url} - 得到站点地址, 一般用于生成 rss.
	 */
	/*public String getUrl() {
		return cfg_svc.getConfigure().getStringValue("site.url");
	}*/
}
