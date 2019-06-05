package cn.edustar.push;

import java.util.Date;

/**
 * @author 孟宪会
 * 
 * 区县平台推送资源实体对象
 *
 */
public class MashupContent implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5020634266379232375L;
	private Integer mashupContentId;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 文档类型
	 */
	private String documentType;
	
	/**
	 * 区县平台根目录路径
	 */
	private String href;
	
	/**
	 * 推送日期
	 */
	private Date pushDate = new Date();
	
	/**
	 * 原资源作者
	 */
	private String author;
	
	/**
	 * 原资源机构
	 */
	private String unitName;
	private String unitTitle;
	
	/**
	 * 文档当前状态：0：正常，1：待审核
	 */
	private int mashupContentState;
	
	/**
	 * 原资源标识
	 */
	private Integer orginId;
	
	/**
	 * 推送人姓名
	 */
	private String pushUserName;
	
	/**
	 * 区县平台唯一标识
	 */
	private String platformGuid;
	
	/**
	 * 区县平台名称
	 */
	private String platformName;

	// Constructors

	/** default constructor */
	public MashupContent() {
	}

	

	// Property accessors

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return this.href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Date getPushDate() {
		return this.pushDate;
	}

	public void setPushDate(Date pushDate) {
		this.pushDate = pushDate;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}


	public String getUnitTitle() {
		return unitTitle;
	}



	public void setUnitTitle(String unitTitle) {
		this.unitTitle = unitTitle;
	}



	public int getMashupContentState() {
		return mashupContentState;
	}

	public void setMashupContentState(int mashupContentState) {
		this.mashupContentState = mashupContentState;
	}

	public Integer getOrginId() {
		return this.orginId;
	}

	public void setOrginId(Integer orginId) {
		this.orginId = orginId;
	}

	public String getPushUserName() {
		return this.pushUserName;
	}

	public void setPushUserName(String pushUserName) {
		this.pushUserName = pushUserName;
	}

	public Integer getMashupContentId() {
		return mashupContentId;
	}

	public void setMashupContentId(Integer mashupContentId) {
		this.mashupContentId = mashupContentId;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getPlatformGuid() {
		return platformGuid;
	}

	public void setPlatformGuid(String platformGuid) {
		this.platformGuid = platformGuid;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
}