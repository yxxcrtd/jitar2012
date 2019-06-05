package cn.edustar.jitar.data;

import cn.edustar.data.DataRow;
import cn.edustar.data.DataTable;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.service.GroupMemberQueryParam;

/**
 * 给清华使用
 *
 *
 */
public class GroupMemberExBean extends GroupMemberBean {
	/** 是否获取加入的群组信息 */
	private boolean joinedGroups = true;
	
	/** 是否获取加入的群组信息 */
	public void setJoinedGroups(boolean v) {
		this.joinedGroups = v;
	}

	@Override
	protected void processMemberList(DataTable member_list) {
		if (joinedGroups == false) return;
		
		// 获取回来的数据 schema = id, userId, status, groupRole, joinDate, 
		//    topicCount, actionCount, articleCount, replyCount, loginName, 
		//    nickName, email, userIcon
		addJoinGroupInfo(member_list);
	}
	
	/**
	 * 给 member_list DataTable 添加一个新列, 内容是获取的 加入的群组的信息.
	 *   加入的群组的 object 也是一个 DataTable, schema = [groupId, groupTitle, joinDate, visitCount]
	 * @param member_list
	 */
	private void addJoinGroupInfo(DataTable member_list) {
		member_list.getSchema().addColumn("joinedGroups");
		
		GroupMemberQueryParam param = new GroupMemberQueryParam();
		param.count = 5;
		param.fieldList = "gm.groupId, gm.joinDate, g.groupTitle, 0 as visitCount";
		param.memberStatus = GroupMember.STATUS_NORMAL;
		
		for (int i = 0; i < member_list.size(); ++i) {
			DataRow row = member_list.get(i);
			Integer userId = (Integer)row.get("userId");
			param.userId = userId;
			DataTable joinedGroups = group_svc.getGroupMemberList(param, null);
			row.set("joinedGroups", joinedGroups);
		}
	}
}
