package cn.edustar.jitar.data;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.service.GroupQueryParam;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 群组数据访问基本 bean, 缺省 varName = 'group_list'.
 *
 *
 */
public class GroupBean extends AbstractPageDataBean {
	/** 群组查询参数 */
	protected GroupQueryParam param = newGroupQueryParam();
	
	/** 群组服务 */
	protected GroupService group_svc;
	
	/**
	 * 构造.
	 */
	public GroupBean() {
		super.setVarName("group_list");
		super.setItemName("协作组");
		super.setItemUnit("个");
		this.group_svc = JitarContext.getCurrentJitarContext().getGroupService();
	}
	
	/**
	 * 构造一个新的 GroupQueryParam 对象.
	 * @return
	 */
	protected GroupQueryParam newGroupQueryParam() {
		return new GroupQueryParam();
	}

	/** 群组服务 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}
	
	/** 查询最多记录数, -1 表示不限制; 只在未指定查询分页参数时候生效, 缺省 = 10 */
	public void setCount(int count) {
		param.count = count;
	}
	
	/** 创建群组的用户标识。 缺省 = null 表示不限定此条件。 */
	public void setCreateUserId(String createUserId) {
		param.createUserId = ParamUtil.safeParseIntegerWithNull(createUserId, null);
	}
	
	/** 审核状态，缺省为 true; false 表示获取 未审核的, true 表示获取审核通过的_
	 *   对应 group.groupState 属性 (Group.GROUP_STATE_WAIT_AUDIT)
	 */
	public void setAuditState(String audit) {
		param.audit_state = ParamUtil.safeParseBooleanWithNull(audit, null);
	}
	
	/** 是否优秀团队群组, 缺省 = null 表示不限制 */
	public void setBestGroup(String bestGroup) {
		param.bestGroup = ParamUtil.safeParseBooleanWithNull(bestGroup, null);
	}
	
	/** 是否推荐协作组, 缺省 = null 表示不限制 */
	public void setIsRecommend(String isRecommend) {
		param.isRecommend = ParamUtil.safeParseBooleanWithNull(isRecommend, null);
	}

	/** 分类标识, 缺省 = null. 设置 useCategoryId 才生效. */
	public void setCategoryId(String categoryId) {
		param.categoryId = ParamUtil.safeParseIntegerWithNull(categoryId, null);
	}
	
	/** 是否限定分类标识 */
	public void setUseCategoryId(boolean useCategoryId) {
		param.useCategoryId = useCategoryId;
	}
	
	/** 要查询的群组的所属学科, 设置 useSubjectId 才生效. 缺省 = null */
	public void setSubjectId(String subjectId) {
		param.subjectId = ParamUtil.safeParseIntegerWithNull(subjectId, null);
	}
	
	/** 是否限制查询所属学科, 缺省 = false */
	public void setUseSubjectId(String useSubjectId) {
		param.useSubjectId = ParamUtil.safeParseBoolean(useSubjectId, false);
	}
	
	/** 排序方式, 缺省为 0 表示 id 逆序排列  */
	public void setOrderType(int orderType) {
		param.orderType = orderType;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.AbstractDataBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		// 得到查询参数和分页选项.
		GroupQueryParam param = contextQueryParam(host);
		Pager pager = getUsePager() ? getContextPager(host) : null;
		List<Group> group_list = group_svc.getGroupList(param, pager);
		
		host.setData(getVarName(), group_list);
		if (getUsePager())
			host.setData(getPagerName(), pager);
	}
	
	/**
	 * 得到群组查询条件.
	 * @return
	 */
	public GroupQueryParam getQueryParam() {
		return this.param;
	}
	
	/**
	 * 得到环境绑定的查询参数.
	 * @param host
	 * @return
	 */
	protected GroupQueryParam contextQueryParam(DataHost host) {
		return this.getQueryParam();
	}
}
