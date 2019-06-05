package cn.edustar.jitar.pojos;

import java.util.Date;

/**
 * ResourceViewCountId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ResourceViewCountId implements java.io.Serializable {

	// Fields

	/**  */
	private static final long serialVersionUID = 6874366583084355034L;
	private Integer resourceId;
	private String title;
	private String href;
	private Date createDate;
	private Integer fsize;
	private Integer downloadCount;
	private String loginName;
	private String nickName;
	private String msubjName;
	private String gradeName;
	private String scName;
	private Integer viewCount;
	/**
	 * 组织机构属性
	 */
	private String unitPath;
	
	/**
	 * 共享属性
	 */
	private int sharedDepth;
	// Constructors

	/** default constructor */
	public ResourceViewCountId() {
	}

	/** minimal constructor */
	public ResourceViewCountId(Integer resourceId, String title, String href,
			Date createDate, Integer fsize, Integer downloadCount, String loginName) {
		this.resourceId = resourceId;
		this.title = title;
		this.href = href;
		this.createDate = createDate;
		this.fsize = fsize;
		this.downloadCount = downloadCount;
		this.loginName = loginName;
	}

	/** full constructor */
	public ResourceViewCountId(Integer resourceId, String title, String href,
			Date createDate, Integer fsize, Integer downloadCount,
			String loginName, String nickName, String msubjName, String gradeName,
			String scName, Integer viewCount) {
		this.resourceId = resourceId;
		this.title = title;
		this.href = href;
		this.createDate = createDate;
		this.fsize = fsize;
		this.downloadCount = downloadCount;
		this.loginName = loginName;
		this.nickName = nickName;
		this.msubjName = msubjName;
		this.gradeName = gradeName;
		this.scName = scName;
		this.viewCount = viewCount;
	}

	// Property accessors

	public Integer getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

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

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getFsize() {
		return this.fsize;
	}

	public void setFsize(Integer fsize) {
		this.fsize = fsize;
	}

	public Integer getDownloadCount() {
		return this.downloadCount;
	}

	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMsubjName() {
		return this.msubjName;
	}

	public void setMsubjName(String msubjName) {
		this.msubjName = msubjName;
	}

	public String getGradeName() {
		return this.gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getScName() {
		return this.scName;
	}

	public void setScName(String scName) {
		this.scName = scName;
	}

	public Integer getViewCount() {
		return this.viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ResourceViewCountId))
			return false;
		ResourceViewCountId castOther = (ResourceViewCountId) other;

		return ((this.getResourceId() == castOther.getResourceId()) || (this
				.getResourceId() != null
				&& castOther.getResourceId() != null && this.getResourceId()
				.equals(castOther.getResourceId())))
				&& ((this.getTitle() == castOther.getTitle()) || (this.getTitle() != null
						&& castOther.getTitle() != null && this.getTitle().equals(
						castOther.getTitle())))
				&& ((this.getHref() == castOther.getHref()) || (this.getHref() != null
						&& castOther.getHref() != null && this.getHref().equals(
						castOther.getHref())))
				&& ((this.getCreateDate() == castOther.getCreateDate()) || (this
						.getCreateDate() != null
						&& castOther.getCreateDate() != null && this.getCreateDate()
						.equals(castOther.getCreateDate())))
				&& ((this.getFsize() == castOther.getFsize()) || (this.getFsize() != null
						&& castOther.getFsize() != null && this.getFsize().equals(
						castOther.getFsize())))
				&& ((this.getDownloadCount() == castOther.getDownloadCount()) || (this
						.getDownloadCount() != null
						&& castOther.getDownloadCount() != null && this
						.getDownloadCount().equals(castOther.getDownloadCount())))
				&& ((this.getLoginName() == castOther.getLoginName()) || (this
						.getLoginName() != null
						&& castOther.getLoginName() != null && this.getLoginName()
						.equals(castOther.getLoginName())))
				&& ((this.getNickName() == castOther.getNickName()) || (this
						.getNickName() != null
						&& castOther.getNickName() != null && this.getNickName()
						.equals(castOther.getNickName())))
				&& ((this.getMsubjName() == castOther.getMsubjName()) || (this
						.getMsubjName() != null
						&& castOther.getMsubjName() != null && this.getMsubjName()
						.equals(castOther.getMsubjName())))
				&& ((this.getGradeName() == castOther.getGradeName()) || (this
						.getGradeName() != null
						&& castOther.getGradeName() != null && this.getGradeName()
						.equals(castOther.getGradeName())))
				&& ((this.getScName() == castOther.getScName()) || (this
						.getScName() != null
						&& castOther.getScName() != null && this.getScName().equals(
						castOther.getScName())))
				&& ((this.getViewCount() == castOther.getViewCount()) || (this
						.getViewCount() != null
						&& castOther.getViewCount() != null && this.getViewCount()
						.equals(castOther.getViewCount())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getResourceId() == null ? 0 : this.getResourceId().hashCode());
		result = 37 * result
				+ (getTitle() == null ? 0 : this.getTitle().hashCode());
		result = 37 * result
				+ (getHref() == null ? 0 : this.getHref().hashCode());
		result = 37 * result
				+ (getCreateDate() == null ? 0 : this.getCreateDate().hashCode());
		result = 37 * result
				+ (getFsize() == null ? 0 : this.getFsize().hashCode());
		result = 37
				* result
				+ (getDownloadCount() == null ? 0 : this.getDownloadCount()
						.hashCode());
		result = 37 * result
				+ (getLoginName() == null ? 0 : this.getLoginName().hashCode());
		result = 37 * result
				+ (getNickName() == null ? 0 : this.getNickName().hashCode());
		result = 37 * result
				+ (getMsubjName() == null ? 0 : this.getMsubjName().hashCode());
		result = 37 * result
				+ (getGradeName() == null ? 0 : this.getGradeName().hashCode());
		result = 37 * result
				+ (getScName() == null ? 0 : this.getScName().hashCode());
		result = 37 * result
				+ (getViewCount() == null ? 0 : this.getViewCount().hashCode());
		return result;
	}

	public String getUnitPath() {
		return unitPath;
	}

	public void setUnitPath(String unitPath) {
		this.unitPath = unitPath;
	}

	public int getSharedDepth() {
		return sharedDepth;
	}

	public void setSharedDepth(int sharedDepth) {
		this.sharedDepth = sharedDepth;
	}

}