package cn.edustar.jitar.ui;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.PythonAction;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.StatService;

/**
 * 显示一个群组的 action, 提供给 python 脚本派生用.
 *
 *
 */
public class ShowGroupPythonAction extends PythonAction {
	/** serialVersionUID */
	private static final long serialVersionUID = -8009562650594940432L;

	/**
	 * 构造.
	 */
	public ShowGroupPythonAction() {
		
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.PythonAction#beforeExecute()
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected String beforeExecute() {
		String result = super.beforeExecute();
		if (result != null) return result;
		
		if (getGroup() != null) {
			// 理论上一定要有 group.
			int createUserId = getGroup().getCreateUserId();
			User group_creator = getUserService().getUserById(createUserId);
			super.setData("group_creator", group_creator);
		}
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.ui.GeneralAction#afterExecute()
	 */
	@Override
	protected void afterExecute() {
		// 增加当前群组的访问计数.
		incGroupVisitCount();
		
		// TODO: 增加当前访问者在当前群组的访问次数.
	}
	
	// 增加当前群组的访问计数.
	@SuppressWarnings("deprecation")
	private void incGroupVisitCount() {
		Group group = super.getGroup();
		if (group == null) return;
		
		StatService stat_svc = JitarContext.getCurrentJitarContext().getStatService();
		stat_svc.incGroupVisitCount(group._getGroupObject());
	}
}
