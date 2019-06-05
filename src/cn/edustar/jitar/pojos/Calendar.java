package cn.edustar.jitar.pojos;

import java.util.Date;

public class Calendar implements java.io.Serializable {

	// Fields

	/** serialVersionUID */
	private static final long serialVersionUID = 3045605571230584251L;
	
	private Integer id;
	private String objectGuid;
	private String objectType;
	private Date eventTimeBegin;
	private Date eventTimeEnd;
	private String title;
	private String url;
	private Date createTime;

	// Constructors

	/** default constructor */
	public Calendar() {
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getObjectGuid() {
		return this.objectGuid;
	}

	public void setObjectGuid(String objectGuid) {
		this.objectGuid = objectGuid;
	}

	public String getObjectType() {
		return this.objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public Date getEventTimeBegin() {
		return this.eventTimeBegin;
	}

	public void setEventTimeBegin(Date eventTime) {
		this.eventTimeBegin = eventTime;
	}
	public Date getEventTimeEnd() {
		return this.eventTimeEnd;
	}

	public void setEventTimeEnd(Date eventTime) {
		this.eventTimeEnd = eventTime;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}