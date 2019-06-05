package cn.edustar.jitar.pojos;
import java.util.Date;

public class PrepareCourseRelated  implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4783692220562731197L;
	private Integer prepareCourseRelatedId;
	private Integer relatedPrepareCourseId;
	private Integer prepareCourseId;
	private Integer userId;
	private Date createDate;

	// Constructors

	/** default constructor */
	public PrepareCourseRelated() {
	}

	/** full constructor */
	public PrepareCourseRelated(Integer relatedPrepareCourseId,
			Integer prepareCourseId, Integer userId,
			Date createDate, String videoTitle) {
		this.relatedPrepareCourseId=relatedPrepareCourseId;
		this.prepareCourseId = prepareCourseId;
		this.userId = userId;
		this.createDate = createDate;
	}

	// Property accessors

	public Integer getPrepareCourseRelatedId() {
		return this.prepareCourseRelatedId;
	}

	public void setPrepareCourseRelatedId(Integer prepareCourseRelatedId) {
		this.prepareCourseRelatedId = prepareCourseRelatedId;
	}

	public Integer getRelatedPrepareCourseId() {
		return this.relatedPrepareCourseId;
	}

	public void setRelatedPrepareCourseId(Integer relatedPrepareCourseId) {
		this.relatedPrepareCourseId = relatedPrepareCourseId;
	}


	public Integer getPrepareCourseId() {
		return this.prepareCourseId;
	}

	public void setPrepareCourseId(Integer prepareCourseId) {
		this.prepareCourseId = prepareCourseId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
