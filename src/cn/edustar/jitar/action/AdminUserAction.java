package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.PrivilegeManager;

/**
 * 管理员 后台用户管理.
 * 
 * @author Yang XinXin
 * @version 1.0.0 Mar 10, 2008 9:03:31 AM
 * @deprecated 现在功能已经完全由 admin_user.py 实现了, 原始代码请在 SourceSafe 里面找.
 */
@SuppressWarnings("unused")
@Deprecated
public class AdminUserAction extends BaseUserAction {
	/** serialVersionUID */
	private static final long serialVersionUID = -925547945367218214L;

	/** 后台管理权限检查服务 */
	private PrivilegeManager priv_mgr;
	
	/** 要操作的 'userId' 集合 */
	private List<Integer> user_ids;

	/**
	 * 缺省构造.
	 */
	public AdminUserAction() {
		
	}
	
	/** 后台管理权限检查服务 */
	public void setPrivilegeManager(PrivilegeManager priv_mgr) {
		this.priv_mgr = priv_mgr;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	public String execute(String cmd) throws Exception {
		addActionError("过时不使用此类了");
		return ERROR;
	}
	
	/**
	 * 编辑/修改指定标识的用户信息.
	 * @return
	 * @throws Exception
	 */
	private String edit() throws Exception {
		int userId = param_util.getIntParam("userId");
		if (userId == 0) {
			addActionError("未给出要修改的用户标识或标识为 0");
			return ERROR;
		}
		User user_model = getUserService().getUserById(userId, false);
		if (user_model == null) {
			addActionError("未找到指定标识的用户: " + userId);
			return ERROR;
		}
		
		// TODO: 验证权限, 看是否有权限修改该用户信息.
		setRequestAttribute("user", user_model._getUserObject());
		
		putMetaSubjectList();
		putGradeList();
		
		// TODO: put blog_cate
		
		return "Edit_Profile";
	}
}
