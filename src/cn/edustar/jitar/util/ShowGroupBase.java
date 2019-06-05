package cn.edustar.jitar.util;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import cn.edustar.jitar.action.AbstractServletAction;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.GroupService;

//得到协作组必要信息的辅助类
@SuppressWarnings("serial")
public class ShowGroupBase extends AbstractServletAction {
	HttpServletRequest request = ServletActionContext.getRequest();
	private GroupService groupService = null;
	String visitor_role = "guest";
	User visitor = null;
	Group group = null;
	GroupMember groupMember = null;

	public boolean getGroupInfo(String groupName){
		//得到当前登录用户.
	    visitor = WebUtil.getLoginUser(request.getSession(false));
	    request.setAttribute("visitor", visitor);
	    
	    //得到协作组信息, 并验证协作组是否能被访问.
	    group = groupService.getGroupMayCached(groupName);
	    request.setAttribute("group", group);
	    if (!canVisitGroup(group)){
	    	return false;
	    }
	    //计算当前登录用户身份, 以及其在协作组中的角色.
	    if (visitor != null){
	    		      request.setAttribute("group_member",  get_group_member());
	    }
	    visitor_role = calcVisitorRole();
	    		
	    request.setAttribute("visitor_role", visitor_role);
	    
	    //TODO: 隐藏的协作组处理问题.
		return true;
	}

	/*
	 * # 计算指定访客 self.group_member 在协作组 self.group 的角色 . # 可能返回 'guest',
	 * 'member', 'admin'.
	 */

	public String calcVisitorRole() {
		// # 缺省为访客.
		String role = "guest";
		// 如果未登录或者访客被锁定等则身份为访客, 如果用户被删除按理说不能登录.
		if (visitor == null || groupMember == null) {
			return role;
		}
		if (visitor.getUserStatus() != User.USER_STATUS_NORMAL) {
			return role;
		}
		// 如果协作组非正常, 则任何人都是访客.
		if (group.getGroupState() != Group.GROUP_STATE_NORMAL) {
			return role;
		}
		// 得到访客在协作组的身份.
		GroupMember gm = groupMember;
		// 如果不是组员则返回访客身份.
		if (gm == null) {
			return role;
		}
		// 组员状态不正常则返回访客身份.
		if (gm.getStatus() != GroupMember.STATUS_NORMAL) return role;

		// 副管理员及以上身份返回为管理员, 否则返回组员身份.
		if (gm.getGroupRole() >= GroupMember.GROUP_ROLE_VICE_MANAGER) {
			role = "admin";
		} else {
			role = "member";
		}
		return role;
	}

	// 得到 self.visitor 在 self.group 中的身份信息
	public GroupMember get_group_member() {
		if (visitor == null) {
			return null;
		}
		groupMember = groupService.getGroupMemberByGroupIdAndUserId(
				group.getGroupId(), visitor.getUserId());
		return groupMember;
	}
}
