package cn.edustar.jitar.ui;

import com.opensymphony.xwork2.ActionContext;

import cn.edustar.jitar.action.AbstractPageAction;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 通用用户列表页
 * 
 *
 */
public class UserListAction extends AbstractPageAction {

	/** serialVersionUID */
	private static final long serialVersionUID = 2044361444613375537L;

	/** 参数对象 */
	private ParamUtil param_util;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute(String cmd) {
		this.param_util = new ParamUtil(ActionContext.getContext().getParameters());

		super.prepareData();

		//String cmd = param_util.safeGetStringParam("cmd");
		if ("debug".equals(cmd))
			return "debug";

		return SUCCESS;
	}
	
}
