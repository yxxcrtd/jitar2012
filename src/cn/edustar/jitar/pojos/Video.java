package cn.edustar.jitar.pojos;

import java.io.Serializable;

import cn.edustar.jitar.util.CommonUtil;

/**
 * 视频对象
 * 
 * @author Yang xinxin
 * @version 1.0.0 Apr 21, 2009 10:21:51 AM
 */
public class Video extends Document implements Serializable, Cloneable {

	/** serialVersionUID */
	private static final long serialVersionUID = 7303969957277544163L;

	/** 上传的IP地址 */
	private String addIp;

	/** 下载次数 */
	private int downloadCount;

	/** 视频的原始链接地址 */
	private String href = "";

	/** FLV 文件地址 */
	private String flvHref;

	/** FLV 缩略图地址 */
	private String flvThumbNailHref;

	/** 视频的类型：原创 = 1 = true；(默认)转载 = 0 = false */
	private boolean typeState;
	private Integer categoryId;
	private Integer unitId=0;
	private Integer gradeId=0;
	private Integer subjectId=0;
	private Integer specialSubjectId;
	
		
	private Integer userCateId;
	
	/** Hibernate 映射所需字段, 外面不需要访问 */
	private Category sysCate;
	
	/** 视频转换状态 */
	private short status;

	/** Hibernate 映射所需字段, 外面不需要访问 */
	public Category getSysCate() {
		return this.sysCate;
	}

	/** Hibernate 映射所需字段, 外面不需要访问 */
	public void setSysCate(Category sysCate) {
		this.sysCate = sysCate;
	}
	
	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	
	public Integer getSpecialSubjectId() {
		return specialSubjectId;
	}

	public void setSpecialSubjectId(Integer specialSubjectId) {
		this.specialSubjectId = specialSubjectId;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	/** Default Constructor */
	public Video() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Video{id = " + getId() + ", title = " + getTitle() + ", user = " + getUserId() + "}";
	}

	/**
	 * 得到友好显示的字符串
	 * 
	 * @return
	 */
	public String toDisplayString() {
		return "'" + CommonUtil.htmlEncode(this.getTitle()) + "'(id=" + this.getVideoId() + ")";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return PojoHelper.hashCode(this.getObjectUuid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		
		if (o == null || !(o instanceof Video)) {
			return false;
		}

		Video other = (Video) o;
		return this.getObjectUuid().equalsIgnoreCase(other.getObjectUuid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Video clone() throws CloneNotSupportedException {
		return (Video) super.clone();
	}

	// Get and set
	/** 视频标识 == id */
	public int getVideoId() {
		return super.getId();
	}

	/** 视频标识 == id */
	public void setVideoId(int id) {
		super.setId(id);
	}
	
	/** 上传的IP地址 */
	public String getAddIp() {
		return this.addIp;
	}

	/** 上传的IP地址 */
	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	/** 下载次数 */
	public int getDownloadCount() {
		return downloadCount;
	}

	/** 下载次数 */
	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}

	/** 视频的原始链接地址 */
	public String getHref() {
		return href;
	}

	/** 视频的链接地址 */
	public void setHref(String href) {
		this.href = href;
	}

	/** FLV 文件地址 */
	public String getFlvHref() {
		return flvHref;
	}

	/** FLV 文件地址 */
	public void setFlvHref(String flvHref) {
		this.flvHref = flvHref;
	}

	/** FLV 缩略图地址 */
	public String getFlvThumbNailHref() {
		return flvThumbNailHref;
	}

	/** FLV 缩略图地址 */
	public void setFlvThumbNailHref(String flvThumbNailHref) {
		this.flvThumbNailHref = flvThumbNailHref;
	}

	/** 视频的类型：原创 = 1 = true；(默认)转载 = 0 = false */
	public boolean getTypeState() {
		return this.typeState;
	}

	/** 视频的类型：原创 = 1 = true；(默认)转载 = 0 = false */
	public void setTypeState(boolean typeState) {
		this.typeState = typeState;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getUserCateId() {
		return userCateId;
	}

	public void setUserCateId(Integer userCateId) {
		this.userCateId = userCateId;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}
	
}
