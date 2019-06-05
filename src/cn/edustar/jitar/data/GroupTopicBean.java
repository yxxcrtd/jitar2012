package cn.edustar.jitar.data;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.service.BbsService;
import cn.edustar.jitar.service.BbsTopicQueryParam;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 群组论坛帖子基本数据获取, 缺省 varName = 'topic_list".
 *
 *
 */
public class GroupTopicBean extends AbstractPageDataBean {
	/** 论坛服务 */
	private BbsService bbs_svc;
	
	/** 查询参数 */
	private BbsTopicQueryParam param = new BbsTopicQueryParam();

	/**
	 * 构造.
	 */
	public GroupTopicBean() {
		super.setVarName("topic_list");
		this.bbs_svc = JitarContext.getCurrentJitarContext().getBbsService();
		param.topState = null;		// 缺省获取所有, 包括置顶的.
	}
	
	/** 论坛服务 */
	public void setBbsService(BbsService bbs_svc) {
		this.bbs_svc = bbs_svc;
	}
	
	/** 要查询的主题数量, 缺省 = 10; -1 表示不限制, 只在未提供 pager 参数时候生效 */
	public void setCount(int count) {
		param.count = count;
	} 
	
	/** 限制的版面标识参数, 缺省 = null 表示不限定 */
	public void setGroupId(String groupId) {
		param.groupId = ParamUtil.safeParseIntegerWithNull(groupId, null);
	}
	
	/** 是否精华, 缺省为null 表示所有文章; = true 表示获取精华的; = false 表示获取非精华的 */
	public void setBestType(String bestType) {
		param.bestType = ParamUtil.safeParseBooleanWithNull(bestType, null);
	}
	
	/** 删除状态, == null 表示不区分,缺省 = false 查询未删除的. */
	public void setDelState(String delState) {
		param.delState = ParamUtil.safeParseBooleanWithNull(delState, null) ;
	}
	
	/** 置顶状态, == null 表示不区分,缺省 = false 查询未删除的. */
	public void setTopState(String topState) {
		param.topState = ParamUtil.safeParseBooleanWithNull(topState, null);
	}
	
	
	/** 排序方式 
	 * (请使用ORDER_TYPE_XXX 常量) 排序方法，为1按发表时间，为2按点击数，为3按回复数. 缺省 = 1
	 * */
	public void setOrderType(int orderType) {
		param.orderType = orderType;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.AbstractDataBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		// 得到查询参数.
		BbsTopicQueryParam param = getQueryParam();
		Pager pager = getUsePager() ? getContextPager(host) : null;
		DataTable topic_list = bbs_svc.getTopicDataTable(param, pager);
		
		// 进行查询.
		host.setData(getVarName(), topic_list);
		if (getUsePager())
			host.setData(getPagerName(), pager);
	}

	/**
	 * 得到查询参数.
	 * @return
	 */
	public BbsTopicQueryParam getQueryParam() {
		return this.param;
	}
}
