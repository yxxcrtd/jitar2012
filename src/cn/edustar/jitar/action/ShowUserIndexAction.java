
package cn.edustar.jitar.action;

import com.alibaba.fastjson.JSONObject;

import cn.edustar.jitar.pojos.Page;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.PageCheckMixiner;


/**
 * 显示用户工作室首页 这里主要是显示个人工作空间的功能模块 登录用户状态的验证,协作组状态的验证 计算用户的权限 把相关的数据放入用户到request对象中
 * 备模板中取值
 * 
 * @author renliang
 */
@SuppressWarnings("serial")
public class ShowUserIndexAction extends AbstractBasePageAction {

	private UserService userService = null;
	private User user = null;
	// 页面地址
	private String PAGE_FTL = "/WEB-INF/user/default/index.ftl";

	@Override
	protected String execute(String cmd) throws Exception {

		Object obj = request.getAttribute("UserName");
		String loginName = null != obj ? (String) obj : null;
		/*
		 * """ if self.loginUser == None:
		 * response.sendRedirect(request.getServletContext().getContextPath() +
		 * "/u/" + loginName + "/html/index.html") return if loginName !=
		 * loginUser.loginName:
		 * response.sendRedirect(request.getServletContext().getContextPath() +
		 * "/u/" + loginName + "/html/index.html") return
		 * 
		 * """
		 */
		// # 得到要工作室主人, 并验证用户状态.
		user = userService.getUserByLoginName(loginName);
		request.setAttribute("user", user);
		
		// #print "user = ", user
		// 用户状态的判断,状态不正确的用户不予显示首页
		if (!canVisitUser(user)) { //TODO 这里的ACCESS_ERROR 没有配置
			return "access_error";
		}
		// # 计数器 访问量计数器 访问的人数+1 在数据库中对应的 该用户的访问量+1 在数据库中对应User表VisitCount
		userService.addVisitCount(user.getUserId());
		
		// # 得到访问者对象, 并计算其身份.
		User visitor = super.getLoginUser();
		// 计算访客在用户首页的权限
		String visitor_role = calcVisitorRole(visitor, user);
		request.setAttribute("visitor", visitor);
		request.setAttribute("visitor_role", visitor_role);
		//#得到用户的首页.
		Page page = new PageCheckMixiner().getUserIndexPage(user);
		if (page == null) {
			return "sendNotFound()";
		}
		request.setAttribute("page", page);
		if (page.getCustomSkin() != null) {
			Object customSkin = JSONObject.parse(page.getCustomSkin());
			request.setAttribute("customSkin", customSkin);
		}

		// 显示该页面.
		request.setAttribute("ru",
				request.getScheme() + "://" + request.getServerName() + ":"
						+ request.getServerPort() + request.getContextPath());
		new PageCheckMixiner().getWidgetsAndReturn(page, PAGE_FTL);
		
		return "index";
	}

	// # 计算访客在用户首页的权限.
	private String calcVisitorRole(User visitor, User user2) {
		// # 缺省为访客.
		String role = "guest";
		// # 如果未登录或者访客被锁定等则身份为访客, 如果用户被删除按理说不能登录.
		if (visitor == null) {
			return role;
		}
		if (visitor.getUserStatus() != User.USER_STATUS_NORMAL) {
			return role;
		}
		// # 如果工作室主人状态不正常(如锁定), 则任何人都是访客, 包括工作室主人自己.
		if (user.getUserStatus() != User.USER_STATUS_NORMAL) {
			return role;
		}
		// # 如果是自己访问自己则角色是 'admin'
		if (user.getUserId() == visitor.getUserId()) {
			return "admin";
		}
		// # 否则都是访客.
		return role;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
