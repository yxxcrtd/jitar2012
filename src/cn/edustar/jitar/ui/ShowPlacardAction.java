package cn.edustar.jitar.ui;

import cn.edustar.jitar.action.AbstractPageAction;
import cn.edustar.jitar.pojos.Placard;
import cn.edustar.jitar.service.PlacardService;
import cn.edustar.jitar.util.ParamUtil;

import com.opensymphony.xwork2.ActionContext;

/**
 * 显示指定公告的页面.
 *
 *
 */
public class ShowPlacardAction extends AbstractPageAction{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1648919057049487774L;

	/** 公告服务 */
	private PlacardService pla_svc;
	
	/** 当前公告 */
	private Placard placard;
	
	/** 设置公告服务 */
	public void setPlacardService(PlacardService pla_svc) {
		this.pla_svc = pla_svc;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute(String cmd) {
		ParamUtil param_util = new ParamUtil(ActionContext.getContext().getParameters());
		//String cmd = param_util.safeGetStringParam("cmd");
		
		preparePlacard(param_util);
		super.prepareData();
		
		if ("debug".equals(cmd))
			return "debug";
		
		return SUCCESS;
	}
	
	/**
	 * 准备页面所用的公告对象.
	 * @param param_util
	 */
	private void preparePlacard(ParamUtil param_util) {
		int placardId = param_util.getIntParam("placardId");
		this.placard = pla_svc.getPlacard(placardId);
		super.setData("placard", placard);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.AbstractPageAction#getContextObject(java.lang.String)
	 */
	@Override
	public Object getContextObject(String name) {
		if ("placard".equals(name))
			return this.placard;
		return super.getContextObject(name);
	}
}
