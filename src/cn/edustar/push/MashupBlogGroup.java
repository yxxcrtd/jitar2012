package cn.edustar.push;

import java.util.Date;



/**
 * @author 孟宪会
 *
 */
public class MashupBlogGroup implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6656650370940063887L;
	private Integer mashupBlogGroupId;
	private Integer orginId;
	private String contentType;
	private String trueName;
	private String description;
	private String icon;
	private String subjectName;
	private String gradeName;
	private String unitName;
	private String unitTitle;
	private String href;
	private Integer unitId;
	private Integer gradeId;
	private Integer metaSubjectId;
	private String pushUserName;
	private String platformGuid;
	private String platformName;
	/**
	 * 推送日期
	 */
	private Date pushDate = new Date();
	
	

	/** 0:审核，1：待审核 */
	private int mashupBlogGroupState = 1;

	// Constructors

	

	/** default constructor */
	public MashupBlogGroup() {
	}

	// Property accessors

	public Integer getMashupBlogGroupId() {
		return this.mashupBlogGroupId;
	}

	public void setMashupBlogGroupId(Integer mashupBlogGroupId) {
		this.mashupBlogGroupId = mashupBlogGroupId;
	}

	public Integer getOrginId() {
		return this.orginId;
	}

	public void setOrginId(Integer orginId) {
		this.orginId = orginId;
	}

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getTrueName() {
		return this.trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSubjectName() {
		return this.subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getGradeName() {
		return this.gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
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

	public String getHref() {
		return this.href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Integer getUnitId() {
		return this.unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Integer getGradeId() {
		return this.gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public Integer getMetaSubjectId() {
		return this.metaSubjectId;
	}

	public void setMetaSubjectId(Integer metaSubjectId) {
		this.metaSubjectId = metaSubjectId;
	}



	public String getPushUserName() {
		return this.pushUserName;
	}

	public void setPushUserName(String pushUserName) {
		this.pushUserName = pushUserName;
	}

	public String getPlatformGuid() {
		return this.platformGuid;
	}

	public void setPlatformGuid(String platformGuid) {
		this.platformGuid = platformGuid;
	}

	public String getPlatformName() {
		return this.platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	
	public int getMashupBlogGroupState() {
		return mashupBlogGroupState;
	}

	public void setMashupBlogGroupState(int mashupBlogGroupState) {
		this.mashupBlogGroupState = mashupBlogGroupState;
	}
	public Date getPushDate() {
		return pushDate;
	}

	public void setPushDate(Date pushDate) {
		this.pushDate = pushDate;
	}
}