package cn.edustar.jitar.pojos;

import java.util.Date;

/**
 * SSpecialSubjectPhoto entity.
 * 
 * @author MyEclipse Persistence Tools
 */

/**
 * @author 孟宪会
 * 
 */
public class SpecialSubjectPhoto implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3094252668980877167L;
	// Fields

	private Integer specialSubjectPhotoId;
	private int photoId;
	private String photoTitle;
	private Date createDate;
	private int specialSubjectId;
	private String photoUserName;
	private int photoUserId;

	// Constructors

	/** default constructor */
	public SpecialSubjectPhoto() {
	}

	public Integer getSpecialSubjectPhotoId() {
		return specialSubjectPhotoId;
	}

	public void setSpecialSubjectPhotoId(Integer specialSubjectPhotoId) {
		this.specialSubjectPhotoId = specialSubjectPhotoId;
	}

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public String getPhotoTitle() {
		return photoTitle;
	}

	public void setPhotoTitle(String photoTitle) {
		this.photoTitle = photoTitle;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getSpecialSubjectId() {
		return specialSubjectId;
	}

	public void setSpecialSubjectId(int specialSubjectId) {
		this.specialSubjectId = specialSubjectId;
	}

	public String getPhotoUserName() {
		return photoUserName;
	}

	public void setPhotoUserName(String photoUserName) {
		this.photoUserName = photoUserName;
	}

	public int getPhotoUserId() {
		return photoUserId;
	}

	public void setPhotoUserId(int photoUserId) {
		this.photoUserId = photoUserId;
	}

}