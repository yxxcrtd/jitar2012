package cn.edustar.jitar.pojos;

import java.util.Date;

/**
 * SSpecialSubjectTopic entity.
 * 
 * @author MyEclipse Persistence Tools
 */

/**
 * @author 孟宪会
 * 
 */
public class SpecialSubjectTopic implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5057882987320947962L;
	private Integer specialSubjectTopicId;
	private int topicId;
	private String topicTitle;
	private Date createDate;
	private int specialSubjectId;

	// Constructors

	/** default constructor */
	public SpecialSubjectTopic() {
	}

	/** full constructor */
	public SpecialSubjectTopic(int topicId, String topicTitle, Date createDate,
			int specialSubjectId) {
		this.topicId = topicId;
		this.topicTitle = topicTitle;
		this.createDate = createDate;
		this.specialSubjectId = specialSubjectId;
	}

	public Integer getSpecialSubjectTopicId() {
		return specialSubjectTopicId;
	}

	public void setSpecialSubjectTopicId(Integer specialSubjectTopicId) {
		this.specialSubjectTopicId = specialSubjectTopicId;
	}

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
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

}