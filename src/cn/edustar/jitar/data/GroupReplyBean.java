package cn.edustar.jitar.data;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.service.BbsReplyQueryParam;
import cn.edustar.jitar.service.BbsService;

/**
 * 群组论坛回复基本 bean, 缺省 varName = 'reply_list'
 *
 *
 */
public class GroupReplyBean extends AbstractPageDataBean {
	/** 论坛服务 */
	private BbsService bbs_svc;
	
	/** 查询参数 */
	private BbsReplyQueryParam param = new BbsReplyQueryParam();
	
	/**
	 * 构造.
	 */
	public GroupReplyBean() {
		super.setVarName("reply_list");
		this.bbs_svc = JitarContext.getCurrentJitarContext().getBbsService();
	}

	/** 论坛服务 */
	public void setBbsService(BbsService bbs_svc) {
		this.bbs_svc = bbs_svc;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.AbstractDataBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		// 获得最新回复的参数.
		BbsReplyQueryParam param = getQueryParam();
		Pager pager = getUsePager() ? getContextPager(host) : null;
		DataTable reply_list = bbs_svc.getReplyDataTable(param, pager);
		
		// 设置数据.
		host.setData(getVarName(), reply_list);
		if (getUsePager())
			host.setData(getPagerName(), pager);
	}
	
	/**
	 * 得到查询参数.
	 * @return
	 */
	public BbsReplyQueryParam getQueryParam() {
		return this.param;
	}
}
