package cn.edustar.jitar.pojos;

import java.io.Serializable;

public class UnitGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5453939896127482178L;
	
	private int groupId;
	private String groupTitle;
	private String groupName;
	private int unitId;
	
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getGroupTitle() {
		return groupTitle;
	}
	public void setGroupTitle(String groupTitle) {
		this.groupTitle = groupTitle;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getUnitId() {
		return unitId;
	}
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

}
