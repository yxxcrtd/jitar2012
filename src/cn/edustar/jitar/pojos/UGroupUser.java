package cn.edustar.jitar.pojos;
// default package
import java.io.Serializable;
/**
 * UGroupUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UGroupUser implements Serializable {
	private static final long serialVersionUID = 5515800137859701789L;
	private Integer groupUserId;
	private Integer groupId;
	private Integer userId;
	private Integer managed=0;
	// Constructors

	/** default constructor */
	public UGroupUser() {
	}

	/** minimal constructor */
	public UGroupUser(Integer groupUserId) {
		this.groupUserId = groupUserId;
	}

	/** full constructor */
	public UGroupUser(Integer groupUserId, Integer groupId, Integer userId) {
		this.groupUserId = groupUserId;
		this.groupId = groupId;
		this.userId = userId;
	}

	// Property accessors

	public Integer getGroupUserId() {
		return this.groupUserId;
	}

	public void setGroupUserId(Integer groupUserId) {
		this.groupUserId = groupUserId;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getManaged(){
		return this.managed;
	}
	public void setManaged(Integer managed){
		this.managed=managed;
	}
	
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}