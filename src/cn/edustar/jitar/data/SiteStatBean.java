package cn.edustar.jitar.data;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.SiteStat;
import cn.edustar.jitar.service.StatService;

/**
 * 获得站点统计信息的 bean, 缺省 varName = 'site_stat'.
 * 
 *
 */
public class SiteStatBean extends AbstractDataBean {
	
	/** 统计服务 */
	private StatService stat_svc;

	/** 统计服务的set方法 */
	public void setStatService(StatService stat_svc) {
		this.stat_svc = stat_svc;
	}

	/**
	 * 构造函数
	 */
	public SiteStatBean() {
		super.setVarName("site_stat");
		this.stat_svc = JitarContext.getCurrentJitarContext().getStatService();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.data.AbstractDataBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		SiteStat site_stat = stat_svc.getSiteStat();
		host.setData(getVarName(), site_stat);
	}
}
