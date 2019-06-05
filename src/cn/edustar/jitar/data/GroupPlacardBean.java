package cn.edustar.jitar.data;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.service.GroupPlacardQueryParam;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 获得群组公告的数据对象.
 *
 *
 */
public class GroupPlacardBean extends AbstractPageDataBean {
	/** 群组服务 */
	private GroupService group_svc;
	
	/** 查询条件 */
	private GroupPlacardQueryParam param = new GroupPlacardQueryParam();
	
	/**
	 * 构造.
	 */
	public GroupPlacardBean() {
		super.setVarName("placard_list");
		this.group_svc = JitarContext.getCurrentJitarContext().getGroupService();
	}

	/** 群组服务 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}
	
	/** 缺省获得的公告数量. */
	public void setCount(int count) {
		param.count = count;
	}

	/** 要获取的公告的所属群组标识, 缺省 = null 表示不限制 */
	public void setGroupId(String groupId) {
		param.groupId = ParamUtil.safeParseIntegerWithNull(groupId);
	}
	
	/** 要获取的公告的发布者标识, 缺省 = null 表示不限制 */
	public void setUserId(String userId) {
		param.userId = ParamUtil.safeParseIntegerWithNull(userId);
	}
	
	/** 是否获取隐藏的公告, 缺省 = false; = null 表示不限制; = true 表示获取隐藏的公告 */
	public void setIsHide(String isHide) {
		param.isHide = ParamUtil.safeParseBooleanWithNull(isHide, Boolean.FALSE);
	}
	
	/** 排序方式 */
	public void setOrderType(int orderType) {
		param.orderType = orderType;
	}

	/**
	 * 根据环境计算查询参数.
	 * @param host
	 * @return
	 */
	protected GroupPlacardQueryParam contextQueryParam(DataHost host) {
		GroupPlacardQueryParam param = getQueryParam();
		Group group = (Group)host.getContextObject("group");
		if (group != null)
			param.groupId = group.getGroupId();
		
		return param;
	}
	
	/**
	 * 得到查询参数.
	 * @return
	 */
	public GroupPlacardQueryParam getQueryParam() {
		return this.param;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.AbstractDataBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		// 得到查询参数和分页参数.
		GroupPlacardQueryParam param = contextQueryParam(host);
		Pager pager = getUsePager() ? getContextPager(host) : null;

		// 得到数据.
		DataTable placard_list = group_svc.getGroupPlacardDataTable(param, pager);
		
		host.setData(getVarName(), placard_list);
		if (getUsePager())
			host.setData(getPagerName(), pager);
	}
}
