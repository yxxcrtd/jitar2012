package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.model.SiteUrlModel;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.Message;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.FriendService;
import cn.edustar.jitar.service.GroupMemberQueryParam;
import cn.edustar.jitar.service.MessageService;
import cn.edustar.jitar.service.UserService;

/**
 * 协作组成员显示和管理
 * 
 *
 */
public class GroupMemberAction extends BaseGroupAction {

	/** serialVersionUID */
	private static final long serialVersionUID = 8602659030298159886L;

	/** 用户服务 */
	private UserService user_svc;

	/** 好友服务 */
	private FriendService frd_svc;
	
	/** 短消息服务 */
	private MessageService messageService;

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	protected String execute(String cmd) throws Exception {
		// 以下操作需要有当前协作组.
		if (hasCurrentGroup() == false)
			return ERROR;
		if ("view_list".equals(cmd))
			return view_list();

		// 以下操作需要用户登录.
		if (isUserLogined() == false)
			return LOGIN;
		if ("join".equals(cmd))
			return join();

		// 以下操作需要是协作组成员.
		if (hasCurrentGroupAndMember() == false)
			return ERROR;
		if ("list".equals(cmd))
			return list();
		else if ("invite".equals(cmd))
			return invite();
		else if ("invite_user".equals(cmd))
			return invite_user();

		// 以下操作需要协作组管理员/副管理员权限.
		if (canManageMember() == false)
			return ERROR;
		
		if ("audit_member".equals(cmd))
			return audit_member();
		else if ("delete_member".equals(cmd))
			return delete_member();
		else if ("edit_member".equals(cmd))
			return edit_member();
		else if ("savemember".equals(cmd))
			return savemember();
		else if ("recover_member".equals(cmd))
			return recover_member();
		else if ("lock_member".equals(cmd))
			return lock_member();
		else if ("unlock_member".equals(cmd))
			return unlock_member();
		else if ("uninvite_member".equals(cmd))
			return uninvite_member();
		else if ("set_vice".equals(cmd))
			return set_vice();
		else if ("unset_vice".equals(cmd))
			return unset_vice();
		else if ("set_creator".equals(cmd))
			return set_creator();

		return unknownCommand(cmd);
	}

	/**
	 * 判定当前登录用户是否有权限管理协作组成员.
	 * 
	 * @return
	 */
	private boolean canManageMember() {
		if (isUserLogined() == false)
			return false;
		if (super.group_model == null)
			return false;
		if (super.group_member == null)
			return false;

		// 业务: 只有副管理员或管理员能够管理小组成员.
		if (group_member.getGroupRole() >= GroupMember.GROUP_ROLE_VICE_MANAGER)
			return true;

		addActionError("只有副管理员或管理员能够管理小组成员");
		return false;
	}

	/**
	 * 显示小组成员列表, 带分页, 在协作组对外表现页面使用.
	 * 
	 * @return
	 */
	private String view_list() {
		// 构造查询参数.
		GroupMemberQueryParam param = new GroupMemberQueryParam();
		param.groupId = group_model.getGroupId();
		param.orderType = GroupMemberQueryParam.ORDER_TYPE_GMID_DESC;

		Pager pager = getCurrentPager();
		pager.setItemNameAndUnit("成员", "名");

		// 查询数据.
		DataTable member_list = group_svc.getGroupMemberList(param, pager);

		super.setRequestAttribute("group", group_model);
		super.setRequestAttribute("member_list", member_list);
		super.setRequestAttribute("pager", pager);

		return "View_List";
	}

	/**
	 * 列出小组成员, 带分页, 显示状态.
	 * 
	 * @return
	 */
	private String list() {
		// 构造查询参数.
		GroupMemberQueryParam param = new GroupMemberQueryParam();
		param.groupId = group_model.getGroupId();
		param.orderType = GroupMemberQueryParam.ORDER_TYPE_GMID_DESC;
		// 状态过滤条件.
		Integer st = param_util.safeGetIntParam("st", null);
		super.setRequestAttribute("st", st);
		param.memberStatus = st;
		// 角色过滤条件.
		Integer r = param_util.safeGetIntParam("r", null);
		super.setRequestAttribute("r", r);
		param.groupRole = r;

		Pager pager = getCurrentPager();
		pager.setItemNameAndUnit("成员", "名");

		// 查询数据.
		DataTable member_list = group_svc.getGroupMemberList(param, pager);

		setRequestAttribute("member_list", member_list);
		setRequestAttribute("pager", pager);

		String uuid=group_svc.getGroupCateUuid(group_model);
		if(uuid.equals(CategoryService.GROUP_CATEGORY_GUID_KTYJ)){
			setRequestAttribute("isKtGroup", "1");
		}
		else if(uuid.equals(CategoryService.GROUP_CATEGORY_GUID_JTBK)){
			setRequestAttribute("isKtGroup", "2");
		}
		else{
			setRequestAttribute("isKtGroup", "0");
		}	
		
		return LIST_SUCCESS;
	}

	/**
	 * 加入协作组
	 * 
	 * @return
	 * @throws Exception
	 */
	private String join() throws Exception {
		
		// 登录验证
		if (isUserLogined() == false)
			return LOGIN;
		
		// 协作组是否有效?、是否存在?
		if (hasCurrentGroup() == false)
			return ERROR;

		// 得到当前自己的成员信息
		GroupMember member = group_svc.getGroupMemberByGroupIdAndUserId(group_model.getGroupId(), getLoginUser().getUserId());	
		if (member == null) {
			if (group_model.getJoinLimit() == Group.JOIN_LIMIT_ONLYINVITE) {
				addActionError("协作组：" + group_model.toDisplayString() + "，设置为邀请加入，您无法直接加入该协作组！");
				return ERROR;
			}
			group_svc.joinGroup(group_model._getGroupObject(), getLoginUser()._getUserObject());
			if (group_model.getJoinLimit() == Group.JOIN_LIMIT_NOLIMIT) {
				addActionMessage("您已经成功加入了协作组：" + group_model.toDisplayString() + "！");
				addActionLink("进入该协作组", "group.py?cmd=manage&groupId=" + group_model.getGroupId(), "_top");
			} else { // 申请加入
				// 给审核的人(如:系统管理员)发送消息
				Message message = new Message();
				message.setSendId(getLoginUser().getUserId()); // 消息的发送者
				message.setReceiveId(group_model.getCreateUserId()); // 消息的接收者
				message.setTitle(getLoginUser().getLoginName() + "[" + getLoginUser().getTrueName() + "]" + " 请求加入：'" + group_model.getGroupTitle() + "' 群组！");
				message.setContent("请接受我加入的申请，谢谢！<br /><br /><a href='../manage/group.py?cmd=req_me'>去看看我收到的申请</a>");
				messageService.sendMessage(message);
				// 显示的返回信息
				addActionMessage("您已经申请加入协作组：" + group_model.toDisplayString() + "，请等待该协作组管理员审核通过！");
			}
			addActionLink("访问该协作组", SiteUrlModel.getSiteUrl() + "go.action?groupId=" + group_model.getGroupId(), "_top");
			return SUCCESS;
		} else {
			switch (member.getStatus()) {
			case GroupMember.STATUS_NORMAL:
				addActionError("您已经加入了该协作组！");				
				break;
			case GroupMember.STATUS_WAIT_AUDIT:
				addActionError("您已经申请加入该协作组，正在等待该协作组管理员的审核！");
				break;
			case GroupMember.STATUS_DELETING:
				addActionError("您申请加入该协作组，但被从协作组中被删除, 为恢复成员关系请和协作组管理员联系！");
				break;
			case GroupMember.STATUS_LOCKED:
				addActionError("您已经加入该协作组，但处于锁定状态，为解锁请和协作组管理员联系！");
				break;
			case GroupMember.STATUS_INVITING:
				// 接受邀请
				group_svc.acceptGroupMemberInvite(group_member);
				addActionMessage("您已经成功加入了协作组：" + group_model.toDisplayString() + "！");
				addActionLink("进入该协作组", "group.py?cmd=manage&groupId=" + group_model.getGroupId(), "_top");
				addActionLink("访问该协作组", "../go.action?groupId=" + group_model.getGroupId(), "_top");
				break;
			default:
				addActionError("未知错误！请与系统管理员联系！");
				return ERROR;
			}
			return SUCCESS;
		}
	}

	/**
	 * 邀请他人加入协作组.
	 * 
	 * @return
	 */
	private String invite() {
		// 得到当前登录用户的好友, 使得可以从下拉列表中选择要邀请的人.
		Object friend_list = frd_svc.getFriendList(getLoginUser().getUserId());
		setRequestAttribute("friend_list", friend_list);

		return "Invite_User";
	}

	/**
	 * 邀请加入协作组.
	 * 
	 * @return
	 */
	private String invite_user() {
		// 得到参数.
		String userIds = param_util.safeGetStringParam("userId");
		if (userIds == null || userIds.length() == 0) {
			addActionError("未选择用户.");
			return ERROR;
		}
		String userNames = "";
		String[] aUserId = userIds.split(",");
		for(int i=0;i<aUserId.length;i++){
			String sUserId = aUserId[i];
			if(sUserId.length()>0){
				int userId = Integer.parseInt(sUserId); 
				User user = user_svc.getUserById(userId);
				if(user!=null)
				{
					// 查看用户是否已经是成员了.
					GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(group_model.getGroupId(), userId);
					if(gm==null)
					{
						// 邀请此用户.
						gm = new GroupMember();
						gm.setGroupId(group_model.getGroupId());
						gm.setUserId(userId);
						gm.setGroupRole(GroupMember.GROUP_ROLE_MEMBER);
						gm.setStatus(GroupMember.STATUS_INVITING);
						gm.setInviterId(getLoginUser().getUserId());
		
						// 邀请成员.
						group_svc.inviteGroupMember(gm);
						// TODO: 向该用户发送一条短消息比较好.
						
						if(userNames.length()==0){
							userNames = user.getTrueName();
						}else{
							userNames =userNames + "," + user.getTrueName();
						}
						
					}
				}
		        				
			}
		}


		addActionLink("返回协作组", "../go.action?groupId=" + group_model.getGroupId());
		addActionMessage("已经向用户 " + userNames + " 发出了邀请.");
		return SUCCESS;
	}

	private String savemember()
	{
		int id = param_util.getIntParam("gmId");
		if (id == 0) {
			addActionError("缺少参数gmId.");
			return ERROR;
		}
		GroupMember gm=group_svc.getGroupMemberById(id);
		if(gm==null){
			addActionError("没有找到成员");
			return ERROR;
		}
		gm.setTeacherUnit( param_util.safeGetStringParam("teacherUnit"));
		gm.setTeacherXL(param_util.safeGetStringParam("teacherXL"));
		gm.setTeacherXW(param_util.safeGetStringParam("teacherXW"));
		gm.setTeacherYJZC(param_util.safeGetStringParam("teacherYJZC"));
		gm.setTeacherZYZW(param_util.safeGetStringParam("teacherZYZW"));
		group_svc.updateGroupMember(gm);
		addActionLink("返回成员列表", "groupMember.action?cmd=list&groupId=" + group_model.getGroupId(), "_self");
		addActionMessage("用户信息更新完成！");
		return SUCCESS;
		
	}
	/**
	 * 编辑成员信息
	 * @return
	 */
	private String edit_member(){
		// 当前用户参数
		if (getCurrentUserIds() == false)
			return ERROR;
		int uid=this.user_ids.get(0);
		
		GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(group_model.getGroupId(), uid);
		
		User user=user_svc.getUserById(uid);
		Unit unit=user_svc.getUnitByUser(user);
		setRequestAttribute("gm", gm);
		setRequestAttribute("user", user);
		setRequestAttribute("unit", unit);
		setRequestAttribute("group", group_model);
		String uuid=group_svc.getGroupCateUuid(group_model);
		if(uuid.equals(CategoryService.GROUP_CATEGORY_GUID_KTYJ)){
			setRequestAttribute("isKtGroup", "1");
		}
		else if(uuid.equals(CategoryService.GROUP_CATEGORY_GUID_JTBK)){
			setRequestAttribute("isKtGroup", "2");
		}
		else{
			setRequestAttribute("isKtGroup", "0");
		}	
		
		return "edit_member";
		
	}
	/**
	 * 审核通过用户的加入申请
	 * 
	 * @return
	 */
	private String audit_member() {
		
		// 当前用户参数
		if (getCurrentUserIds() == false)
			return ERROR;

		// 循环为每个用户进行审核操作
		int audit_num = 0;
		for (Integer uid : user_ids) {
			// 得到用户状态数据
			GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(group_model.getGroupId(), uid);
			if (gm == null) {
				super.addActionError("在协作组：" + group_model.toDisplayString() + "，中没有成员用户(标识=" + uid + ")");
			} else if (gm.getStatus() == GroupMember.STATUS_WAIT_AUDIT) {
				// 状态 = STATUS_WAIT_AUDIT 才能进行审核
				group_svc.auditGroupMember(gm);
				++audit_num;
			} else {
				super.addActionError("在协作组：" + group_model.toDisplayString() + "，中标识为：" + uid + "，的用户状态不是未审核！");
			}
		}

		// 批量审核完成之后，更新协作组用户数量
		group_svc.updateGroupMemberCount(group_model.getGroupId());

		if (audit_num == 0) {
			this.addActionMessage("没有任何成员用户被审核！");
		} else {
			this.addActionMessage("成功审核：" + audit_num + " 个用户！");
		}
		return SUCCESS;
	}

	/**
	 * 删除加入协作组的用户. 根据不同状态有所不同. NORMAL - 设置删除状态 DELETING WAIT_AUDIT - 实际删除掉
	 * (审核不通过) DELETING - 无操作
	 * 
	 * LOCKED - 设置删除状态 DELETING INVITING - 实际删除掉 (取消邀请) 其它非法、未知状态 - 实际删除掉
	 * 
	 * @return
	 */
	private String delete_member() {
		// 得到协作组和当前用户参数.
		if (getCurrentUserIds() == false)
			return ERROR;

		// 循环为每个用户进行删除.
		int delete_num = 0, destroy_num = 0;
		for (Integer uid : user_ids) {
			// 得到用户状态数据.
			GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(
					group_model.getGroupId(), uid);
			if (gm == null) {
				super.addActionError("在协作组：" + group_model.toDisplayString() + " 中没有找到成员用户(标识=" + uid + ")");
				continue;
			}

			// 业务验证哪些成员不能删、以及判断权限问题.
			if (gm.getGroupRole() == GroupMember.GROUP_ROLE_MANAGER) {
				addActionError("不能删除协作组管理员");
				continue;
			}

			// 副管理员不能删除副管理员.
			if (gm.getGroupRole() == GroupMember.GROUP_ROLE_VICE_MANAGER
					&& group_member.getGroupRole() == GroupMember.GROUP_ROLE_VICE_MANAGER) {
				addActionError("您不能删除和您同级的管理员");
				continue;
			}

			switch (gm.getStatus()) {
			case GroupMember.STATUS_NORMAL:
			case GroupMember.STATUS_LOCKED:
				// 设置删除状态.
				group_svc.deleteGroupMember(gm);
				++delete_num;
				break;
			case GroupMember.STATUS_WAIT_AUDIT:
			case GroupMember.STATUS_INVITING:
			default:
				// 直接删除该记录.
				group_svc.destroyGroupMember(gm);
				++destroy_num;
				break;
			case GroupMember.STATUS_DELETING:
				// 不做任何事情.
				addActionError("在协作组：" + group_model.toDisplayString() + " 中成员id=" + uid + " 已经被删除");
				break;
			}
		}

		// 批量操作完成之后，更新协作组用户数量.
		group_svc.updateGroupMemberCount(group_model.getGroupId());

		// 组装显示提示信息.
		if (delete_num == 0 && destroy_num == 0) {
			addActionMessage("没有任何成员用户被删除！");
		} else {
			addActionMessage("共有：" + (delete_num + destroy_num) + " 个成员被删除");
		}

		return SUCCESS;
	}

	/**
	 * 恢复被删除的协作组成员. delete_member() 的反操作 (不完全相反, 有些状态 delete_member 直接删除记录了)
	 * 
	 * @return
	 */
	private String recover_member() {
		// 得到协作组和当前用户参数.
		if (getCurrentUserIds() == false)
			return ERROR;

		// 循环为每个用户进行恢复.
		int recover_num = 0;
		for (Integer uid : user_ids) {
			// 得到用户状态数据.
			GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(
					group_model.getGroupId(), uid);
			if (gm == null) {
				super.addActionError("在协作组：" + group_model.toDisplayString() + " 中没有找到成员用户(标识=" + uid + ")");
				continue;
			}

			if (gm.getStatus() != GroupMember.STATUS_DELETING) {
				addActionError("成员标识 = " + gm.getUserId() + " 未被删除, 不能执行恢复操作");
			} else {
				group_svc.recoverGroupMember(gm);
				++recover_num;
			}
		}

		// 批量审核完成之后，更新协作组用户数量.
		group_svc.updateGroupMemberCount(group_model.getGroupId());

		// 组装显示提示信息.
		if (recover_num == 0) {
			addActionMessage("没有任何成员用户被恢复");
		} else {
			addActionMessage("共 " + recover_num + " 个成员被恢复");
		}

		return SUCCESS;
	}

	/**
	 * 锁定协作组成员.
	 * 
	 * @return
	 */
	private String lock_member() {
		// 得到协作组和当前用户参数.
		if (getCurrentUserIds() == false)
			return ERROR;

		// 循环为每个用户进行锁定.
		int lock_num = 0;
		for (Integer uid : user_ids) {
			// 得到用户状态数据.
			GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(
					group_model.getGroupId(), uid);
			if (gm == null) {
				super.addActionError("在协作组：" + group_model.toDisplayString() + " 中没有找到成员用户(标识=" + uid + ")");
				continue;
			}

			// 业务验证哪些成员不能删、以及判断权限问题.
			if (gm.getGroupRole() == GroupMember.GROUP_ROLE_MANAGER) {
				addActionError("不能锁定协作组管理员");
				continue;
			}
			// 副管理员不能锁定副管理员.
			if (gm.getGroupRole() == GroupMember.GROUP_ROLE_VICE_MANAGER && group_member.getGroupRole() == GroupMember.GROUP_ROLE_VICE_MANAGER) {
				addActionError("您不能锁定和您同级的管理员");
				continue;
			}

			switch (gm.getStatus()) {
			case GroupMember.STATUS_NORMAL:
			case GroupMember.STATUS_LOCKED:
				// 设置锁定状态.
				group_svc.lockGroupMember(gm);
				++lock_num;
				break;
			default:
				// 不做任何事情.
				addActionError("在协作组 " + group_model.toDisplayString() + " 中成员id=" + uid + " 因为当前状态非正常, 不能执行锁定操作");
				break;
			}
		}

		// 批量操作完成之后，更新协作组用户数量.
		group_svc.updateGroupMemberCount(group_model.getGroupId());

		// 组装显示提示信息.
		if (lock_num == 0) {
			addActionMessage("没有任何成员用户被锁定");
		} else {
			addActionMessage("共 " + lock_num + " 个成员被锁定");
		}

		return SUCCESS;
	}

	/**
	 * 解锁用户, 和 lock_member() 互为反操作.
	 * 
	 * @return
	 */
	private String unlock_member() {
		// 得到协作组和当前用户参数.
		if (getCurrentUserIds() == false)
			return ERROR;

		// 循环为每个用户进行锁定.
		int unlock_num = 0;
		for (Integer uid : user_ids) {
			// 得到用户状态数据.
			GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(group_model.getGroupId(), uid);
			if (gm == null) {
				super.addActionError("在协作组：" + group_model.toDisplayString() + " 中没有找到成员用户(标识=" + uid + ")");
				continue;
			}

			// 业务验证哪些成员不能删、以及判断权限问题.
			if (gm.getGroupRole() == GroupMember.GROUP_ROLE_MANAGER) {
				addActionError("不能解锁协作组管理员(管理员也不能锁定)");
				continue;
			}

			switch (gm.getStatus()) {
			case GroupMember.STATUS_NORMAL:
			case GroupMember.STATUS_LOCKED:
				// 设置锁定状态.
				group_svc.unlockGroupMember(gm);
				++unlock_num;
				break;
			default:
				// 不做任何事情.
				addActionError("在协作组 " + group_model.toDisplayString() + " 中成员id="
						+ uid + " 因为当前状态非正常, 不能执行解锁操作");
				break;
			}
		}

		// 批量操作完成之后，更新协作组用户数量.
		group_svc.updateGroupMemberCount(group_model.getGroupId());

		// 组装显示提示信息.
		if (unlock_num == 0) {
			addActionMessage("没有任何成员用户被解锁");
		} else {
			addActionMessage("共 " + unlock_num + " 个成员被解锁");
		}

		return SUCCESS;
	}

	/**
	 * 取消邀请.
	 * 
	 * @return
	 */
	private String uninvite_member() {
		// 得到协作组和当前用户参数.
		if (getCurrentUserIds() == false)
			return ERROR;

		// 循环为每个用户进行锁定.
		int uninv_num = 0;
		for (Integer uid : user_ids) {
			// 得到用户状态数据.
			GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(
					group_model.getGroupId(), uid);
			if (gm == null) {
				super.addActionError("在协作组 " + group_model.toDisplayString()
						+ " 中没有找到成员用户(标识=" + uid + ")");
				continue;
			}

			// 业务验证哪些成员不能删、以及判断权限问题.
			if (gm.getGroupRole() == GroupMember.GROUP_ROLE_MANAGER) {
				addActionError("不能锁定管理员.");
				continue;
			}

			switch (gm.getStatus()) {
			case GroupMember.STATUS_INVITING:
				group_svc.uninviteGroupMember(gm);
				++uninv_num;
				break;
			default:
				// 不做任何事情.
				addActionError("在协作组 " + group_model.toDisplayString() + " 中成员id="
						+ uid + " 因为当前状态非等待邀请回复, 不能执行取消邀请操作.");
				break;
			}
		}

		// 批量操作完成之后，更新协作组用户数量.
		group_svc.updateGroupMemberCount(group_model.getGroupId());

		// 组装显示提示信息.
		if (uninv_num == 0) {
			addActionMessage("没有任何用户被取消邀请");
		} else {
			addActionMessage("共有 " + uninv_num + " 个用户被取消了邀请");
		}

		return SUCCESS;
	}

	/**
	 * 设置一个或多个成员为副管理员.
	 * 
	 * @return
	 */
	private String set_vice() {
		// 得到协作组和当前用户参数.
		if (getCurrentUserIds() == false)
			return ERROR;
		if (group_member == null
				|| group_member.getGroupRole() != GroupMember.GROUP_ROLE_MANAGER) {
			addActionError("只有协作组管理员才具有设置为/取消副管理员的操作权限.");
			return ERROR;
		}

		// 循环操作.
		int oper_count = 0;
		for (Integer userId : this.user_ids) {
			User user = user_svc.getUserById(userId);
			if (user == null) {
				addActionError("指定标识为 " + userId + " 的用户不存在.");
				continue;
			}
			// 得到用户状态数据.
			GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(
					group_model.getGroupId(), userId);
			if (gm == null) {
				addActionError("用户 " + user.toDisplayString()
						+ " 不是协作组成员, 不能将其设置为副管理员.");
				continue;
			}

			if (gm.getGroupRole() == GroupMember.GROUP_ROLE_MANAGER) {
				addActionError("不能设置(降级)管理员为副管理员.");
				continue;
			}

			// 执行操作.
			gm.setGroupRole(GroupMember.GROUP_ROLE_VICE_MANAGER);
			gm.setStatus(GroupMember.STATUS_NORMAL);
			group_svc.updateGroupMember(gm);
			++oper_count;
			addActionMessage("协作组成员 " + user.toDisplayString() + " 成功被设置为副管理员.");
		}

		return SUCCESS;
	}

	/**
	 * 取消副管理员.
	 * 
	 * @return
	 */
	private String unset_vice() {
		// 得到协作组和当前用户参数.
		if (getCurrentUserIds() == false)
			return ERROR;
		if (group_member == null
				|| group_member.getGroupRole() != GroupMember.GROUP_ROLE_MANAGER) {
			addActionError("只有协作组管理员才具有设置为/取消副管理员的操作权限.");
			return ERROR;
		}

		// 循环操作.
		int oper_count = 0;
		for (Integer userId : this.user_ids) {
			User user = user_svc.getUserById(userId);
			if (user == null) {
				addActionError("指定标识为：" + userId + " 的用户不存在.");
				continue;
			}
			// 得到用户状态数据.
			GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(
					group_model.getGroupId(), userId);
			if (gm == null) {
				addActionError("用户：" + user.toDisplayString() + " 不是协作组成员, 不能对其执行取消副管理员操作.");
				continue;
			}

			if (gm.getGroupRole() == GroupMember.GROUP_ROLE_MANAGER) {
				addActionError("不能设置(降级)管理员为副管理员.");
				continue;
			}
			if (gm.getGroupRole() != GroupMember.GROUP_ROLE_VICE_MANAGER) {
				addActionError("协作组成员：" + user.toDisplayString() + " 不是副管理员, 不能执行取消副管理员操作.");
				continue;
			}

			// 执行操作.
			gm.setGroupRole(GroupMember.GROUP_ROLE_MEMBER);
			gm.setStatus(GroupMember.STATUS_NORMAL);
			group_svc.updateGroupMember(gm);
			++oper_count;
			addActionMessage("取消 " + user.toDisplayString() + " 的副管理员操作执行成功.");
		}

		return SUCCESS;
	}

	/**
	 * 转让协作组给别人, 只能选择一个目标用户.
	 * 
	 * @return
	 */
	private String set_creator() {
		// 得到协作组和当前用户参数.
		if (group_model.getCreateUserId() != getLoginUser().getUserId()) {
			addActionError("只有协作组创建者才能执行转让操作.");
			return ERROR;
		}

		// 转让只能转给一个人.
		Integer userId = param_util.getIntParamZeroAsNull("userId");
		if (userId == null) {
			addActionError("未给出要转让给的用户参数.");
			return ERROR;
		}
		if (userId.equals(getLoginUser().getUserId())) {
			addActionError("不能将协作组转让给自己, 该操作无意义.");
			return ERROR;
		}

		// 得到用户并验证.
		User user = user_svc.getUserById(userId, false);
		if (user == null) {
			addActionError("未能找到标识为 " + userId + " 的用户.");
			return ERROR;
		}
		// 得到用户状态数据.
		GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(group_model.getGroupId(), userId);
		if (gm == null) {
			addActionError("用户：" + user.toDisplayString() + " 不是协作组成员, 无法将协作组转让给该用户.");
			return ERROR;
		}

		// 执行操作.
		group_svc.transferGroupToMember(group_model._getGroupObject(), gm);

		addActionMessage("协作组 " + group_model.toDisplayString() + " 已经成功转让给了：" + user.toDisplayString());

		return SUCCESS;
	}

	/** 在成员管理中提交的用户标识参数集合, param key = uid */
	private List<Integer> user_ids;

	
	/**
	 * 得到参数 uid 并根据 uid 得到当前要管理的成员用户标识，放到 user_ids 成员变量中
	 *
	 * @return
	 */
	private boolean getCurrentUserIds() {
		this.user_ids = param_util.safeGetIntValues("userId");
		if (user_ids == null || user_ids.size() == 0 || (user_ids.size() == 1 && user_ids.get(0).intValue() == 0)) {
			this.addActionError("没有选择任何要操作的用户");
			return false;
		}
		return true;
	}
	
	// Get and set
	/** 用户服务的set方法 */
	public void setUserService(UserService user_svc) {
		this.user_svc = user_svc;
	}

	/** 好友服务的set方法 */
	public void setFriendService(FriendService frd_svc) {
		this.frd_svc = frd_svc;
	}
	
	/** 短消息服务的set方法 */
	public void setMessageService(MessageService messageService) {
		this.messageService  = messageService;
	}
	
}
