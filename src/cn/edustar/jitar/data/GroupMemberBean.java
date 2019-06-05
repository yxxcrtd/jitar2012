package cn.edustar.jitar.data;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.service.GroupMemberQueryParam;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 查询群组成员的数据对象.
 *
 *
 */
public class GroupMemberBean extends AbstractPageDataBean {
	/** 查询参数 */
	protected GroupMemberQueryParam param = newGroupMemberQueryParam();
	
	/** 群组服务 */
	protected GroupService group_svc;
	
	/**
	 * 构造.
	 */
	public GroupMemberBean() {
		super.setVarName("member_list");
		super.setItemName("成员");
		super.setItemUnit("名");
		this.group_svc = JitarContext.getCurrentJitarContext().getGroupService();
		param.memberStatus = GroupMember.STATUS_NORMAL;
	}
	
	/** 查询的记录数量, 一般在未指定 pager 的时候使用 */
	public void setCount(int count) {
		param.count = count;
	}
	
	/** getGroupMemberList() 方法返回的字段, 缺省包括用户和群组成员信息 */
	public void setFieldList(String fieldList) {
		param.fieldList = fieldList;
	}
	
	/** 查找该组的成员, 缺省 = null 表示不限定 */
	public void setGroupId(String groupId) {
		param.groupId = ParamUtil.safeParseIntegerWithNull(groupId);
	}
	
	/** 要查找的用户标识, 缺省 = null 表示不限定 */
	public void setUserId(String userId) {
		param.userId = ParamUtil.safeParseIntegerWithNull(userId);
	}
	
	/** 要查找的成员状态, 缺省 = null 表示不限制 */
	public void setMemberStatus(String memberStatus) {
		param.memberStatus = ParamUtil.safeParseIntegerWithNull(memberStatus);
	}
	
	/** 要查找的成员角色, 缺省 = null 表示不限制 */
	public void setGroupRole(String groupRole) {
		param.groupRole = ParamUtil.safeParseIntegerWithNull(groupRole);
	}
	
	/** 是否要查找管理员, 包括正管理员和副管理员, 缺省 = null 表示不限制, 一般不和 groupRole 条件合用 */
	public void setGroupManager(String groupManager) {
		param.groupManager = ParamUtil.safeParseBooleanWithNull(groupManager, null);
	}
	
	/** 排序方式, 缺省 = 0 表示按照 gm.id 逆序排列 */
	public void setOrderType(int orderType) {
		param.orderType = orderType;
	}

	protected GroupMemberQueryParam newGroupMemberQueryParam() {
		return new GroupMemberQueryParam();
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.AbstractDataBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		// 得到查询参数和分页选项.
		GroupMemberQueryParam param = getQueryParam(host);
		Pager pager = getUsePager() ? getContextPager(host) : null;
		DataTable member_list = group_svc.getGroupMemberList(param, pager);
		
		processMemberList(member_list);
		
		// 设置数据到环境中.
		host.setData(getVarName(), member_list);
		if (getUsePager())
			host.setData(getPagerName(), pager);
	}
	
	/**
	 * 给派生类重载用, 对 member_list 进行处理.
	 * @param member_list
	 */
	protected void processMemberList(DataTable member_list) {
		
	}
	
	/**
	 * 得到查询参数.
	 * @param host
	 * @return
	 */
	protected GroupMemberQueryParam getQueryParam(DataHost host) {
		Group group = (Group)host.getContextObject("group");
		if (group != null) {
			param.groupId = group.getGroupId();
		}

		return this.param;
	}
}
