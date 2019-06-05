package cn.edustar.jitar.util;

import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.Resource;

/**
 * User, Group, GroupMember 等地方的信息提示的辅助对象.
 *
 *
 */
public class InfoUtil {
	private InfoUtil() { }
	
	/**
	 * 得到 GroupMember.status 中定义的状态的中文描述.
	 * @param gm_state
	 * @return
	 */
	public static String groupMemberStatusInfo(int gm_state) {
		switch (gm_state) {
		case GroupMember.STATUS_NORMAL: return "正常";
		case GroupMember.STATUS_WAIT_AUDIT: return "未审核通过";
		case GroupMember.STATUS_DELETING: return "从组中删除";
		case GroupMember.STATUS_LOCKED: return "在组中锁住";
		case GroupMember.STATUS_INVITING: return "邀请但未回应";
		default: return "未知(非法)状态";
		}
	}

	/**
	 * 得到 Group.status 中定义的状态的中文描述.
	 * @param g_status
	 * @return
	 */
	public static String groupStatusInfo(int g_status) {
		switch (g_status) {
		case Group.GROUP_STATE_NORMAL: return "正常";
		case Group.GROUP_STATE_WAIT_AUDIT: return "协作组未审核通过";
		case Group.GROUP_STATE_LOCKED: return "协作组被锁住(不能添加/修改其内容)";
		case Group.GROUP_STATE_DELETED: return "协作组已被删除";
		case Group.GROUP_STATE_HIDED: return "协作组被隐藏";
		default: return "未知(非法)状态";
		}
	}

	/**
	 * 得到某个共享模式的中文说明.
	 * @param shareMode
	 * @return
	 */
	public static String shareModeToString(int shareMode) {
		switch (shareMode) {
		case Resource.SHARE_MODE_FULL: return "完全共享";
		case Resource.SHARE_MODE_GROUP: return "组内共享";
		case Resource.SHARE_MODE_PRIVATE: return "私有";
		default: return "不支持的模式:" + String.valueOf(shareMode);
		}
	}
}
