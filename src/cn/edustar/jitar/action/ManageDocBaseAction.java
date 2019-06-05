package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.DataTable;
import cn.edustar.jitar.pojos.Channel;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.GroupMemberQueryParam;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.util.InfoUtil;

/**
 * 可以给'Article.action,Resource.action'提供相同功能的基类
 * 
 *
 */
public abstract class ManageDocBaseAction extends ManageBaseAction {
	
	/**
	 * 将个人加入的协作组, 且有权限在该组中发表内容的协作组列出来. 能发布内容意味着协作组状态正常(包括隐藏), 协作组成员状态正常.
	 * 
	 * @remark 放置到 request 中的对象名字为 'joined_groups'. 同时把参数中的 groupId 取出来放到 request 中 'groupId'
	 */
	protected void putJoinedGroupToRequest(GroupService group_svc) {
		/*
		 * 等同于查询下列查询. SELECT g.groupId, g.groupTitle, gm.groupRole, gm.status +
		 * FROM GroupMember gm, Group g " + WHERE gm.groupId = g.groupId AND
		 * gm.userId = :userId AND gm.status = 正常 AND g.groupState = 可向其发布内容.
		 */
		GroupMemberQueryParam param = new GroupMemberQueryParam();
		param.userId = getLoginUser().getUserId();
		param.fieldList = "g.groupId, g.groupTitle, gm.groupRole, gm.status";
		param.count = -1;
		param.memberStatus = GroupMember.STATUS_NORMAL;
		param.groupCanPubTo = Boolean.TRUE;
		param.orderType = GroupMemberQueryParam.ORDER_TYPE_GMID_ASC;
		DataTable joined_groups = group_svc.getGroupMemberList(param, null);
		setRequestAttribute("joined_groups", joined_groups);
		int groupId = param_util.getIntParam("groupId");
		setRequestAttribute("groupId", groupId);
	}
	
	/**
	 * 检查指定协作组状态是否允许发布内容到该组
	 * 
	 * @param group
	 * @return 返回 true 表示允许, 返回 false 表示不允许.
	 */
	protected boolean checkGroupState(Group group) {
		if (group == null) {
			addActionError("协作组不存在.");
			return false;
		}
		switch (group.getGroupState()) {
		case Group.GROUP_STATE_NORMAL:
			break;
		case Group.GROUP_STATE_WAIT_AUDIT:
			addActionError("协作组 " + group.toDisplayString() + " 尚未审核通过，不能向该协作组中发布文章、资源、主题等内容.");
			return false;
		case Group.GROUP_STATE_LOCKED:
			addActionError("协作组 " + group.toDisplayString() + " 已被管理员锁定，因此不能向该协作组中发布文章、资源、主题等内容.");
			return false;
		case Group.GROUP_STATE_DELETED:
			addActionError("协作组 " + group.toDisplayString() + " 已被管理员删除，因此不能向该协作组中发布文章、资源、主题等内容.");
			return false;
		case Group.GROUP_STATE_HIDED:
			break; // 隐藏的协作组可以发.
		default:
			addActionError("协作组：" + group.toDisplayString() + "，状态未知，status = " + group.getGroupState() + "，不能向该协作组中发布文章、资源、主题等内容.");
			addActionError("请联系系统管理员以解决此问题！");
			return false;
		}
		return true;
	}

	/**
	 * 检查用户 user 在协作组 group 中的成员状态
	 * 
	 * @param gm
	 * @param group
	 * @param user
	 * @return 返回 false 表示不能发布东西, 返回 true 表示可以发布
	 */
	protected boolean checkGroupMemberState(GroupMember gm, Group group, User user) {
		if (gm == null) {
			addActionError("您不是协作组：" + group.toDisplayString() + "，的成员，因此不能向该协作组中发布文章、资源、主题等内容！");
			return false;
		}
		if (gm.getStatus() != GroupMember.STATUS_NORMAL) {
			addActionError("您在协作组：" + group.toDisplayString() + "，的成员状态为： "	+ InfoUtil.groupMemberStatusInfo(gm.getStatus()) + "，不能向该协作组中发布文章、资源、主题等内容！");
			return false;
		}
		return true;
	}

}
