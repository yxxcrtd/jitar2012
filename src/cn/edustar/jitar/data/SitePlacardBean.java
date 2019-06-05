package cn.edustar.jitar.data;

import java.util.List;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Placard;
import cn.edustar.jitar.service.PlacardService;

/**
 * 得到站点公告的数据对象, 缺省 varName = 'site_placard_list'.
 *
 *
 */
public class SitePlacardBean extends AbstractDataBean {
	/** 公告服务 */
	private PlacardService pla_svc;
	
	/** 获取的公告条数, 缺省 = 8 */
	private int count = 8;

	/**
	 * 构造.
	 */
	public SitePlacardBean() {
		super.setVarName("site_placard_list");
		this.pla_svc = JitarContext.getCurrentJitarContext().getPlacardService();
	}
	
	/** 公告服务 */
	public void setPlacardService(PlacardService pla_svc) {
		this.pla_svc = pla_svc;
	}

	/** 获取的公告条数, 缺省 = 8 */
	public void setCount(int count) {
		this.count = count;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.AbstractDataBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		// 得到最新 count 条系统公告.
		List<Placard> placard_list = pla_svc.getRecentPlacard(
				ObjectType.OBJECT_TYPE_SYSTEM, 0, count);
		host.setData(getVarName(), placard_list);
	}
}
