package cn.edustar.jitar.pojos;

import java.util.Date;

/**
 * 问题解答实体对象 
 * @author 孟宪会
 * 
 */

public class QuestionAnswer implements java.io.Serializable {

	// 成员

	/**
	 * 成员定义
	 */
	private static final long serialVersionUID = -1097968005239412250L;
	
	/** 解答标识 */
	private Integer answerId;
	
	/** 问题对象标识 */
	private Integer questionId;
	
	/**问题解答的时间 */
	private Date createDate;
	
	/** 问题解答者的用户标识 */
	private Integer answerUserId;
	
	/** 问题解答者的用户显示名 */
	private String answerUserName;
	
	/** 问题解答内容 */
	private String answerContent;
	
	/** 问题解答者的 ip 标识 */
	private String addIp;

	// 构造器

	/** 默认的构造器 constructor */
	public QuestionAnswer() {
	}

	// 属性访问器

	/** 获取解答的标识 */
	public Integer getAnswerId() {
		return this.answerId;
	}

	/** 设置解答的标识 */
	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}

	
	/** 获取 问题对象标识 */
	public Integer getQuestionId() {
		return questionId;
	}

	/** 设置问题对象标识 */
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	/** 获取解答的时间 */
	public Date getCreateDate() {
		return this.createDate;
	}

	/** 设置解答的时间 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** 获取解答用户的标识 */
	public Integer getAnswerUserId() {
		return this.answerUserId;
	}

	/** 设置解答用户的标识 */
	public void setAnswerUserId(Integer answerUserId) {
		this.answerUserId = answerUserId;
	}

	/** 获取解答用户的显示名 */
	public String getAnswerUserName() {
		return this.answerUserName;
	}

	/** 设置解答用户的显示名 */
	public void setAnswerUserName(String answerUserName) {
		this.answerUserName = answerUserName;
	}
	
	/** 获取解答的内容描述 */
	public String getAnswerContent() {
		return this.answerContent;
	}

	/** 设置解答的内容描述 */
	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	/** 获取解答者的 IP 标识 */
	public String getAddIp() {
		return this.addIp;
	}

	/** 获取解答者的 IP 标识 */
	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

}