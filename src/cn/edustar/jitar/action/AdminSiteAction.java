package cn.edustar.jitar.action;

import cn.edustar.jitar.service.ConfigService;

/**
 * 系统站点配置管理
 */
@Deprecated
public class AdminSiteAction extends ManageBaseAction {
	
	/** serialVersionUID */
	private static final long serialVersionUID = -2977164903707994464L;

	/** 配置服务 */
	@SuppressWarnings("unused")
	private ConfigService conf_svc;

	/** 配置服务 */
	public void setConfigService(ConfigService conf_svc) {
		this.conf_svc = conf_svc;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	protected String execute(String cmd) throws Exception {
		
		if (cmd == null || cmd.length() == 0) cmd = "list";
		if ("list".equals(cmd))
			return list();
		
		return unknownCommand(cmd);
	}
	
	private String list() {
		return LIST_SUCCESS;
	}
	
}
