package cn.edustar.jitar.pojos;

import java.util.Date;

/**
 * PVoteResult entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class VoteResult implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6058880180182263477L;
	private Integer voteResultId;
	private Integer voteQuestionAnswerId;
	private Date createDate;
	private Integer userId;

	// Constructors

	public VoteResult(Integer voteQuestionAnswerId, Date createDate,Integer userId) {
		this.voteQuestionAnswerId = voteQuestionAnswerId;
		this.createDate = createDate;
		this.userId = userId;
	}


	/** default constructor */
	public VoteResult() {
	}


	// Property accessors

	public Integer getVoteResultId() {
		return this.voteResultId;
	}

	public void setVoteResultId(Integer voteResultId) {
		this.voteResultId = voteResultId;
	}

	public Integer getVoteQuestionAnswerId() {
		return this.voteQuestionAnswerId;
	}

	public void setVoteQuestionAnswerId(Integer voteQuestionAnswerId) {
		this.voteQuestionAnswerId = voteQuestionAnswerId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}