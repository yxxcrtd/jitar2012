package cn.edustar.jitar.pojos;

import java.util.Date;

/**
 * UPunishScore entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class UPunishScore implements java.io.Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -8325156410056659531L;
	
	private Integer id;
	private Integer userId;
	private Integer objType;
	private Integer objId;
	private String objTitle;
	private Date punishDate=new Date();
	/**负分为罚分 正分为加分*/
	private Float score;
	private String reason;
	private int createUserId;
	private String createUserName;

	// Constructors

	/** default constructor */
	public UPunishScore() {
	}

	/** minimal constructor */
	public UPunishScore(Integer userId, Integer objId, Date punishDate,
			Float score) {
		this.userId = userId;
		this.objId = objId;
		this.punishDate = punishDate;
		this.score = score;
	}

	/** full constructor */
	public UPunishScore(Integer userId, Integer objType, Integer objId,
			String objTitle, Date punishDate, Float score, String reason) {
		this.userId = userId;
		this.objType = objType;
		this.objId = objId;
		this.objTitle = objTitle;
		this.punishDate = punishDate;
		this.score = score;
		this.reason = reason;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getObjType() {
		return this.objType;
	}

	public void setObjType(Integer objType) {
		this.objType = objType;
	}

	public Integer getObjId() {
		return this.objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public String getObjTitle() {
		return this.objTitle;
	}

	public void setObjTitle(String objTitle) {
		this.objTitle = objTitle;
	}

	public Date getPunishDate() {
		return this.punishDate;
	}

	public void setPunishDate(Date punishDate) {
		this.punishDate = punishDate;
	}

	public Float getScore() {
		return this.score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

}