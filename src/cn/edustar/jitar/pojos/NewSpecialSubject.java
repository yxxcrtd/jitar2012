package cn.edustar.jitar.pojos;

import java.util.Date;

/**
 * TNewSpecialSubject entity.
 * 
 * @author MyEclipse Persistence Tools
 */

/**
 * @author 孟宪会
 *
 */
public class NewSpecialSubject implements java.io.Serializable {

	/**
	 * 对应到候选主题提交
	 */
	private static final long serialVersionUID = 4631929913823455593L;
	// Fields

	private Integer newSpecialSubjectId;
	private String newSpecialSubjectTitle;
	private String newSpecialSubjectContent;
	private Integer createUserId;
	private String createUserName;
	private Date createDate;
	private String addIp;

	// Constructors

	/** default constructor */
	public NewSpecialSubject() {
	}

	/** minimal constructor */
	public NewSpecialSubject(String newSpecialSubjectTitle,
			Integer createUserId, String createUserName, Date createDate) {
		this.newSpecialSubjectTitle = newSpecialSubjectTitle;
		this.createUserId = createUserId;
		this.createUserName = createUserName;
		this.createDate = createDate;
	}

	/** full constructor */
	public NewSpecialSubject(String newSpecialSubjectTitle,
			String newSpecialSubjectContent, Integer createUserId,
			String createUserName, Date createDate, String addIp) {
		this.newSpecialSubjectTitle = newSpecialSubjectTitle;
		this.newSpecialSubjectContent = newSpecialSubjectContent;
		this.createUserId = createUserId;
		this.createUserName = createUserName;
		this.createDate = createDate;
		this.addIp = addIp;
	}

	// Property accessors

	public Integer getNewSpecialSubjectId() {
		return this.newSpecialSubjectId;
	}

	public void setNewSpecialSubjectId(Integer newSpecialSubjectId) {
		this.newSpecialSubjectId = newSpecialSubjectId;
	}

	public String getNewSpecialSubjectTitle() {
		return this.newSpecialSubjectTitle;
	}

	public void setNewSpecialSubjectTitle(String newSpecialSubjectTitle) {
		this.newSpecialSubjectTitle = newSpecialSubjectTitle;
	}

	public String getNewSpecialSubjectContent() {
		return this.newSpecialSubjectContent;
	}

	public void setNewSpecialSubjectContent(String newSpecialSubjectContent) {
		this.newSpecialSubjectContent = newSpecialSubjectContent;
	}

	public Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return this.createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getAddIp() {
		return this.addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

}