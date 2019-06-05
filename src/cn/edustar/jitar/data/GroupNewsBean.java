package cn.edustar.jitar.data;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.GroupNewsQueryParam;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 获得群组新闻的 bean, 缺省 varName = 'news_list'.
 *
 *
 */
public class GroupNewsBean extends AbstractPageDataBean {
	/** 查询参数 */
	private GroupNewsQueryParam param = newGroupNewsQueryParam();
	
	/** 群组服务 */
	private GroupService group_svc;
	
	/**
	 * 构造.
	 */
	public GroupNewsBean() {
		super.setVarName("news_list");
		super.setItemName("新闻");
		super.setItemUnit("条");
		this.group_svc = JitarContext.getCurrentJitarContext().getGroupService();
	}
	
	/**
	 * 实例化一个 GroupNewsQueryParam.
	 * @return
	 */
	protected GroupNewsQueryParam newGroupNewsQueryParam() {
		return new GroupNewsQueryParam();
	}
	
	/**
	 * 得到查询参数.
	 * @return
	 */
	public GroupNewsQueryParam getQueryParam() {
		return this.param;
	}
	
	/** 群组服务 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}

	/** 查询条数, 一般在未指定 pager 的时候使用 */
	public void setCount(int count) {
		param.count = count;
	}
	
	/** 限定群组标识, 缺省 = null 表示不限制 */
	public void setGroupId(String groupId) {
		param.groupId = ParamUtil.safeParseIntegerWithNull(groupId, null);
	}
	
	/** 发布用户标识, 缺省 = null 表示不限制 */
	public void setUserId(String userId) {
		param.userId = ParamUtil.safeParseIntegerWithNull(userId, null);
	}
	
	/** 要获取的对象状态, 缺省 = GroupNews.NEWS_STATUS_NORMAL 获取正常状态的; = null 则不限制 */
	public void setStatus(String status) {
		param.status = ParamUtil.safeParseIntegerWithNull(status, null);
	}
	
	/** 要获取的类型, 缺省 = null 表示不限制 */
	public void setNewsType(String newsType) {
		param.newsType = ParamUtil.safeParseIntegerWithNull(newsType, null);
	}

	/** 是否要求有图片, 缺省 = null 表示不限制 */
	public void setHasPicture(String val) {
		param.hasPicture = ParamUtil.safeParseBooleanWithNull(val, null);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.AbstractDataBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		// 得到查询参数和分页选项.
		GroupNewsQueryParam param = getQueryParam();
		Group group = (Group)host.getContextObject("group");
		if (group != null)
			param.groupId = group.getGroupId();
		
		User user = (User)host.getContextObject("user");
		if (user != null)
			param.userId = user.getUserId();
		
		Pager pager = getUsePager() ? getContextPager(host) : null;
		DataTable news_list = group_svc.getGroupNewsDataTable(param, pager);
		
		host.setData(getVarName(), news_list);
		if (getUsePager())
			host.setData(getPagerName(), pager);
	}
}
