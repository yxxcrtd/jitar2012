package cn.edustar.jitar.action;

/**
 * 后台管理首页
 */
@Deprecated
public class AdminIndexAction extends ManageBaseAction {
	/** serialVersionUID */
	private static final long serialVersionUID = -7255510490161856849L;

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	protected String execute(String cmd) throws Exception {
		// 必须要登录.
		if (isUserLogined() == false) return LOGIN;
		
		// 判定其具有管理权限.
		if (hasAdminRight() == false) return ERROR;
		
		if (cmd == null || cmd.length() == 0)
			return SUCCESS;
		
		if ("menu".equals(cmd))
			return "menu";
		else if ("main".equals(cmd))
			return "main";
		
		return null;
	}
}
