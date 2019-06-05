package cn.edustar.jitar.pojos;

import java.util.Date;

/**
 * PPlugInTopicReply entity.
 * 
 * @author MyEclipse Persistence Tools
 */

/**
 * @author 孟宪会
 *
 */
public class PlugInTopicReply implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2061428886243105443L;
	// Fields

	private Integer plugInTopicReplyId;
	private Integer plugInTopicId;
	private String title;
	private Date createDate;
	private Integer createUserId;
	private String createUserName;
	private String addIp;
	private String replyContent;

	// Constructors

	/** default constructor */
	public PlugInTopicReply() {
	}

	/** minimal constructor */
	public PlugInTopicReply(Integer plugInTopicId, String title,
			Date createDate, Integer createUserId, String createUserName) {
		this.plugInTopicId = plugInTopicId;
		this.title = title;
		this.createDate = createDate;
		this.createUserId = createUserId;
		this.createUserName = createUserName;
	}

	/** full constructor */
	public PlugInTopicReply(Integer plugInTopicId, String title,
			Date createDate, Integer createUserId, String createUserName,
			String addIp, String replyContent) {
		this.plugInTopicId = plugInTopicId;
		this.title = title;
		this.createDate = createDate;
		this.createUserId = createUserId;
		this.createUserName = createUserName;
		this.addIp = addIp;
		this.replyContent = replyContent;
	}

	// Property accessors

	public Integer getPlugInTopicReplyId() {
		return this.plugInTopicReplyId;
	}

	public void setPlugInTopicReplyId(Integer plugInTopicReplyId) {
		this.plugInTopicReplyId = plugInTopicReplyId;
	}

	public Integer getPlugInTopicId() {
		return this.plugInTopicId;
	}

	public void setPlugInTopicId(Integer plugInTopicId) {
		this.plugInTopicId = plugInTopicId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public String getAddIp() {
		return this.addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	public String getReplyContent() {
		return this.replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

}