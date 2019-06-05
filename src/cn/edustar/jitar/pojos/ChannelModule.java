package cn.edustar.jitar.pojos;

import java.io.Serializable;

public class ChannelModule implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1337083587430436680L;

	public ChannelModule() {
	}

	private int moduleId;
	private String displayName;
	private int channelId;
	private String moduleType;
	private String content;
	private int listItemCount;
	private String contentTemplate;
	private String pageType;
	private Integer showCount;
	private String template;

	private Integer cateId;
	private String cateItemType;
	private String unitType;
	
	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getListItemCount() {
		return listItemCount;
	}

	public void setListItemCount(int listItemCount) {
		this.listItemCount = listItemCount;
	}

	public String getContentTemplate() {
		return contentTemplate;
	}

	public void setContentTemplate(String contentTemplate) {
		this.contentTemplate = contentTemplate;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public Integer getShowCount() {
		return this.showCount;
	}

	public void setShowCount(Integer showCount) {
		this.showCount = showCount;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

	public String getCateItemType() {
		return cateItemType;
	}

	public void setCateItemType(String cateItemType) {
		this.cateItemType = cateItemType;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
}
