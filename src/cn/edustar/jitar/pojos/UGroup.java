package cn.edustar.jitar.pojos;
// default package


/**
 * UGroup entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UGroup implements java.io.Serializable {

	private static final long serialVersionUID = 6257376911012947930L;
	private Integer groupId;
	private String groupName;
	private String groupInfo;

	// Constructors

	/** default constructor */
	public UGroup() {
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupInfo() {
		return groupInfo;
	}

	public void setGroupInfo(String groupInfo) {
		this.groupInfo = groupInfo;
	}

}