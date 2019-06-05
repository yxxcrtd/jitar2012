package cn.edustar.jitar.pojos;

import java.util.Date;

/**
 * CPrepareCoursePrivateComment entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrepareCoursePrivateComment implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -9068727676693426477L;
	private Integer prepareCoursePrivateCommentId;
	private String title;
	private Date createDate;
	private Integer commentedUserId;
	private Integer commentUserId;
	private Integer prepareCourseMemberId;
	private String content;
	private String referIp;
	private int prepareCourseId;

	// Constructors

	/** default constructor */
	public PrepareCoursePrivateComment() {
	}

	/** minimal constructor */
	public PrepareCoursePrivateComment(Integer prepareCoursePrivateCommentId,
			String title, Date createDate, Integer commentedUserId,
			Integer commentUserId, Integer prepareCourseMemberId, String content) {
		this.prepareCoursePrivateCommentId = prepareCoursePrivateCommentId;
		this.title = title;
		this.createDate = createDate;
		this.commentedUserId = commentedUserId;
		this.commentUserId = commentUserId;
		this.prepareCourseMemberId = prepareCourseMemberId;
		this.content = content;
	}

	/** full constructor */
	public PrepareCoursePrivateComment(Integer prepareCoursePrivateCommentId,
			String title, Date createDate, Integer commentedUserId,
			Integer commentUserId, Integer prepareCourseMemberId, String content,
			String referIp) {
		this.prepareCoursePrivateCommentId = prepareCoursePrivateCommentId;
		this.title = title;
		this.createDate = createDate;
		this.commentedUserId = commentedUserId;
		this.commentUserId = commentUserId;
		this.prepareCourseMemberId = prepareCourseMemberId;
		this.content = content;
		this.referIp = referIp;
	}

	// Property accessors

	public Integer getPrepareCoursePrivateCommentId() {
		return this.prepareCoursePrivateCommentId;
	}

	public void setPrepareCoursePrivateCommentId(
			Integer prepareCoursePrivateCommentId) {
		this.prepareCoursePrivateCommentId = prepareCoursePrivateCommentId;
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

	public Integer getCommentedUserId() {
		return this.commentedUserId;
	}

	public void setCommentedUserId(Integer commentedUserId) {
		this.commentedUserId = commentedUserId;
	}

	public Integer getCommentUserId() {
		return this.commentUserId;
	}

	public void setCommentUserId(Integer commentUserId) {
		this.commentUserId = commentUserId;
	}

	public Integer getPrepareCourseMemberId() {
		return this.prepareCourseMemberId;
	}

	public void setPrepareCourseMemberId(Integer prepareCourseMemberId) {
		this.prepareCourseMemberId = prepareCourseMemberId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReferIp() {
		return this.referIp;
	}

	public void setReferIp(String referIp) {
		this.referIp = referIp;
	}

	public int getPrepareCourseId() {
		return prepareCourseId;
	}

	public void setPrepareCourseId(int prepareCourseId) {
		this.prepareCourseId = prepareCourseId;
	}

}