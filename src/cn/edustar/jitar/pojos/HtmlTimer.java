package cn.edustar.jitar.pojos;

import java.util.Date;

public class HtmlTimer implements java.io.Serializable {

	private static final long serialVersionUID = -7252388298038707215L;
	private Integer id;
	private Integer objectId;
	private Integer objectType;
	private Date lastUpdate = new Date();

	public HtmlTimer() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public Integer getObjectType() {
		return objectType;
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
