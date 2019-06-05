package cn.edustar.jitar.pojos;

import java.io.Serializable;

public class Channel implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3384640519883248368L;
	private int channelId;
	private String channelGuid = java.util.UUID.randomUUID().toString().toLowerCase();
	private String title;
	private String skin;
	private String headerTemplate;
	private String footerTemplate;
	private String indexPageTemplate;
	private String logo;
	private String cssStyle;
	public Channel() {
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public String getChannelGuid() {
		return channelGuid;
	}

	public void setChannelGuid(String channelGuid) {
		this.channelGuid = channelGuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getHeaderTemplate() {
		return headerTemplate;
	}

	public void setHeaderTemplate(String headerTemplate) {
		this.headerTemplate = headerTemplate;
	}

	public String getFooterTemplate() {
		return footerTemplate;
	}

	public void setFooterTemplate(String footerTemplate) {
		this.footerTemplate = footerTemplate;
	}

	public String getIndexPageTemplate() {
		return indexPageTemplate;
	}

	public void setIndexPageTemplate(String indexPageTemplate) {
		this.indexPageTemplate = indexPageTemplate;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

}
