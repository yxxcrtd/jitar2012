package cn.edustar.jitar.pojos;
// default package

/**
 * UGroupPower entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UGroupPower implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1787548682327725761L;
	private Integer groupPowerId;
	private Integer groupId;
	private Integer uploadArticleNum;
	private Integer uploadResourceNum;
	private Integer uploadDiskNum;
	private Integer videoConference;   //是否有视频会议权限
	
	// Constructors

	/** default constructor */
	public UGroupPower() {
	}

	/** minimal constructor */
	public UGroupPower(Integer groupPowerId) {
		this.groupPowerId = groupPowerId;
	}

	/** full constructor */
	public UGroupPower(Integer groupPowerId, Integer groupId,
			Integer uploadArticleNum, Integer uploadResourceNum,
			Integer uploadDiskNum,Integer videoConference) {
		this.groupPowerId = groupPowerId;
		this.groupId = groupId;
		this.uploadArticleNum = uploadArticleNum;
		this.uploadResourceNum = uploadResourceNum;
		this.uploadDiskNum = uploadDiskNum;
		this.videoConference = videoConference;
	}

	// Property accessors

	
	public Integer getGroupPowerId() {
		return this.groupPowerId;
	}

	public void setGroupPowerId(Integer groupPowerId) {
		this.groupPowerId = groupPowerId;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getUploadArticleNum() {
		return this.uploadArticleNum;
	}

	public void setUploadArticleNum(Integer uploadArticleNum) {
		this.uploadArticleNum = uploadArticleNum;
	}

	public Integer getUploadResourceNum() {
		return this.uploadResourceNum;
	}

	public void setUploadResourceNum(Integer uploadResourceNum) {
		this.uploadResourceNum = uploadResourceNum;
	}

	public Integer getUploadDiskNum() {
		return this.uploadDiskNum;
	}

	public void setUploadDiskNum(Integer uploadDiskNum) {
		this.uploadDiskNum = uploadDiskNum;
	}

	public Integer getVideoConference() {
		return this.videoConference;
	}

	public void setVideoConference(Integer videoConference) {
		this.videoConference = videoConference;
	}	
}