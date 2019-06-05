package cn.edustar.jitar.action;

import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.GroupService;

/**
 * 提供给包括群组的管理做为基类.
 * 
 *
 */
public abstract class BaseGroupAction extends ManageBaseAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2178105460259872521L;
	
	/** 群组服务 */
	protected GroupService group_svc;
	
	protected ArticleService art_svc;
	
	/** 群组服务的set方法 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}
	
	public void setArticleService(ArticleService art_svc) {
		this.art_svc = art_svc;
	}

	/** 当前所在管理的群组 */
	protected Group group_model;

	/** 当前登录用户在协作组中的成员信息, 和权限有关 */
	protected GroupMember group_member;

	/**
	 * 在执行 execute(cmd) 之前执行, BaseGroupAction 试图获取当前协作组和登录用户在协作组中的身分信息.
	 */
	@Override
	protected String beforeExecute() {
		// 得到 groupId 参数
		Integer groupId = param_util.safeGetIntParam("groupId");
		if (groupId == null || groupId.intValue() == 0)
			return null;

		// 得到群组对象模型并放到 request 中
		this.group_model = group_svc.getGroupMayCached(groupId);
		if (this.group_model == null)
			return null;
		setRequestAttribute("group", group_model);

		// 得到当前登录用户在该协作组的身份信息并放到 request 中
		if (isUserLogined() == false)
			return null;
		this.group_member = fetchCurrentGroupMember();
		setRequestAttribute("group_member", group_member);

		return null;
	}

	/**
	 * 得到当前登录用户在当前协作组中的身分信息, 并强制修正创建者是管理员的问题.
	 * 
	 * @return 业务：协作组创建者总是协作组管理员, 且状态不能被修改(如锁定、被删除等). 也许还有问题,
	 *         如果该协作组创建者用户本身被删除、锁定, 则需要系统管理员来干预了.
	 */
	private final GroupMember fetchCurrentGroupMember() {
		GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(group_model.getGroupId(), getLoginUser().getUserId());
		if (getLoginUser().getUserId() == group_model.getCreateUserId()) {
			// 如果自己是协作组创建者, 但未找到协作组成员信息, 则现在修正它.
			if (gm == null) {
				gm = new GroupMember();
				gm.setGroupId(group_model.getGroupId());
				gm.setUserId(getLoginUser().getUserId());
				gm.setGroupRole(GroupMember.GROUP_ROLE_MANAGER);
				gm.setStatus(GroupMember.STATUS_NORMAL);
				group_svc.createGroupMember(gm);
			} else if (gm.getStatus() != GroupMember.STATUS_NORMAL || gm.getGroupRole() != GroupMember.GROUP_ROLE_MANAGER || gm.getGroupRole() != GroupMember.GROUP_ROLE_VICE_MANAGER) {
				gm.setGroupRole(GroupMember.GROUP_ROLE_MANAGER);
				gm.setStatus(GroupMember.STATUS_NORMAL);
				group_svc.updateGroupMember(gm);
			}
		}
		return gm;
	}

	/**
	 * 判断是否当前具有有效的协作组参数及协作组存在?
	 * 
	 * @return 返回'true'表示有;返回'false'表示没有,并且在'actionError'中添加了相应的错误信息
	 */
	protected boolean hasCurrentGroup() {
		// 得到'groupId'参数
		Integer groupId = param_util.safeGetIntParam("groupId");
		if (groupId == null || groupId.intValue() == 0) {
			addActionError("未给出要管理的协作组标识, 请确定您是从有效的链接进入的");
			return false;
		}
		// 得到群组对象模型
		if (this.group_model == null) {
			addActionError("未找到指定标识 = " + groupId + " 的协作组, 请确定您是从有效的链接进入的");
			return false;
		}
		return true;
	}

	/**
	 * 判断当前登录用户是否是协作组成员.
	 * 
	 * @return
	 */
	protected boolean isGroupMember() {
		// 判断是否登录?
		if (isUserLogined() == false)
			return false;

		if (this.group_model == null)
			return false;
		if (this.group_member == null) {
			addActionError("您还不是协作组 " + group_model._getGroupObject().toDisplayString() + " 的成员, 不能进入该协作组.");
			return false;
		}

		// 是否未审核?
		switch (this.group_member.getStatus()) {
		case GroupMember.STATUS_NORMAL:
			break;
		case GroupMember.STATUS_WAIT_AUDIT:
			addActionError("您的协作组成员身份尚未审核通过, 不能进入协作组. 请联系协作组管理员审核您的成员身份.");
			return false;
		case GroupMember.STATUS_DELETING:
			addActionError("您在该协作组已经被删除, 不能进入该协作组.");
			return false;
		case GroupMember.STATUS_LOCKED: // 能进, 但是不能发东西.
			return true;
		case GroupMember.STATUS_INVITING:
			addActionError("您已经被邀请加入该协作组但您尚未同意该邀请.");
			return false;
		default:
			addActionError("未知的协作组成员状态 " + group_member.getStatus() + ", 不能进入协作组.");
			return false;
		}
		return true;
	}

	/**
	 * 是否有当前协作组参数, 并且当前登录用户是协作组成员.
	 * 
	 * @return true 表示是; false 表示要么没有协作组参数, 要么未登录, 要么不是该协作组成员.
	 */
	protected boolean hasCurrentGroupAndMember() {
		// 判断是否登录?
		if (isUserLogined() == false)
			return false;

		// 是否有当前协作组?
		if (hasCurrentGroup() == false)
			return false;

		// 判断协作组是否有效.
		switch (this.group_model.getGroupState()) {
		case Group.GROUP_STATE_DELETED:
			addActionError("协作组: " + group_model.getGroupTitle() + " 已经被管理员删除, 不能进入该协作组.");
			return false;
		case Group.GROUP_STATE_WAIT_AUDIT:
			addActionError("协作组: " + group_model.getGroupTitle() + " 尚未通过审核, 不能进入该协作组.");
			return false;
			// 其它能够访问, 但是有部分东西受限制.
		}
		return this.isGroupMember();
	}
	
}
