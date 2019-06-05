package cn.edustar.jitar.pojos;

import java.io.Serializable;

/**
 * 群组新闻实体对象.
 *
 *
 */
public class GroupNews extends News implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = 1680637591108139733L;
	
	/** 所属群组标识 */
	private int groupId;
	/** 所属群组标识 */
	public int getGroupId() {
		return groupId;
	}

	/** 所属群组标识 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
}
