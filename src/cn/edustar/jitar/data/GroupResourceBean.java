package cn.edustar.jitar.data;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.service.GroupResourceQueryParam;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.ResourceQueryParam;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 获取群组资源列表的数据 bean.
 *
 *
 */
public class GroupResourceBean extends ResourceBean {
	/** 群组服务 */
	private GroupService group_svc;
	
	/** 查询参数 */
	private GroupResourceQueryParam gr_param = new GroupResourceQueryParam();
	
	/**
	 * 构造.
	 */
	public GroupResourceBean() {
		this.group_svc = JitarContext.getCurrentJitarContext().getGroupService();
	}

	/** 要查询的资源的所属群组, 缺省 = null 表示不限制 */
	public void setGroupId(String groupId) {
		gr_param.groupId = ParamUtil.safeParseIntegerWithNull(groupId);
	}
	
	/** 资源标识, 缺省 = null 表示不限制 */
	public void setResourceId(String resourceId) {
		gr_param.resourceId = ParamUtil.safeParseIntegerWithNull(resourceId);
	}

	/** 是否是群组精华资源, 缺省 = null 表示不限制 */
	public void setIsGroupBest(String isGroupBest) {
		gr_param.isGroupBest = ParamUtil.safeParseBooleanWithNull(isGroupBest, null);
	}

	/** 要查询的字段, 缺省只有少量字段 */
	public void setSelectFields(String fields) {
		gr_param.selectFields = fields;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.ResourceBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		// 得到查询参数和分页选项.
		GroupResourceQueryParam param = contextQueryParam2(host);
		Pager pager = getUsePager() ? getContextPager(host) : null; 
		DataTable resource_list = group_svc.getGroupResourceDataTable(param, pager);
		
		// 将数据设置到环境中.
		host.setData(getVarName(), resource_list);
		if (getUsePager())
			host.setData(getPagerName(), pager);
	}
	
	protected GroupResourceQueryParam contextQueryParam2(DataHost host) {
		ResourceQueryParam innerParam = super.contextQueryParam(host);
		this.gr_param.resourceQueryParam = innerParam;
		
		Group group = (Group)host.getContextObject("group");
		if (group != null) {
			int groupId = group.getGroupId();
			this.gr_param.groupId = groupId;
		}
		
		return this.gr_param;
	}
}
