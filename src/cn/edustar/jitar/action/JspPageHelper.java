package cn.edustar.jitar.action;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import net.zdsoft.passport.demo.client.SessionManager;
import net.zdsoft.passport.service.client.PassportClient;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.UserOnLine;
import cn.edustar.jitar.service.OnLineService;
import cn.edustar.jitar.util.WebUtil;

/**
 * 给 JSP 页面提供一些辅助用函数
 * 
 *
 */
public class JspPageHelper {

	/** page_ctxt */
	private final PageContext page_ctxt;

	/** 在线服务 */
	private OnLineService onlineService;

	/**
	 * 构造函数
	 * 
	 * @param page_ctxt
	 */
	public JspPageHelper(PageContext page_ctxt) {
		this.page_ctxt = page_ctxt;
	}

	/**
	 * 判断用户是否登录了
	 * 
	 * @return
	 */
	public boolean isUserLogined() {
		HttpSession session = page_ctxt.getSession();
		if (session == null) {
			return false;
		}

		String loginName = (String) session
				.getAttribute(User.SESSION_LOGIN_NAME_KEY);
		if (loginName == null) {
			return false;
		}

		return loginName.length() > 0;
	}

	/**
	 * 得到当前登录用户对象
	 * 
	 * @return
	 */
	public User getLoginUser() {
		return WebUtil.getLoginUser(page_ctxt.getSession());
	}

	/**
	 * 从 Spring 配置中得到指定名字的 bean
	 * 
	 * @param name
	 * @return
	 */
	public Object getBean(String name) {
		WebApplicationContext app_ctxt = WebApplicationContextUtils
				.getWebApplicationContext(page_ctxt.getServletContext());
		return app_ctxt.getBean(name);
	}

	/**
	 * 退出登录
	 */
	public void logout() {
		String passportURL = page_ctxt.getServletContext().getInitParameter("passportURL") == null ? null : PassportClient.getInstance().getPassportURL();
		if (passportURL == null)
			passportURL = "";
		if ("http://".equals(passportURL))
			passportURL = "";
		if (!("".equals(passportURL))) {
			// 如果存在浙大的统一用户，则先清除浙大的统一用户信息
			try {
				String ticket_zd = (String) page_ctxt.getSession()
						.getAttribute(SessionManager.PASSPORT_TICKET_KEY);
				// 从SessionManager中清除ticket
				SessionManager.removeTicket(ticket_zd);
				// 清除ticket是为了在session失效的时候不再重复通知Passport
				page_ctxt.getSession().removeAttribute(
						SessionManager.PASSPORT_TICKET_KEY);
				page_ctxt.getSession().removeAttribute(
						net.zdsoft.passport.demo.dto.User.KEY);
				// page_ctxt.getSession().invalidate();
			} catch (Exception ex) {
			}
		}

		// 继续执行我们公司的统一用户清除代码
		HttpSession session = page_ctxt.getSession();
		if (null == session)
			return;
		// 得到我们持有的票证, 让统一用户使其过期
		String ticket = (String) session.getAttribute(User.SESSION_USER_TICKET);
		if (ticket != null && ticket.length() > 0) {

		}
		// 退出在线
		try {
			WebApplicationContext app_ctxt = WebApplicationContextUtils
					.getWebApplicationContext(page_ctxt.getServletContext());
			this.onlineService = (OnLineService) app_ctxt
					.getBean("onlineService");
			if (onlineService != null) {
				String userName = WebUtil.getLoginUser(session)
						._getUserObject().getLoginName();
				if (!"".equals(userName) || userName.length() > 0) {
					UserOnLine userOnLine = onlineService
							.findUserOnLineByUserName(userName);
					if (userOnLine != null) {
						this.onlineService.removeUserOnLine(userOnLine);
					}
				}
			}
		} catch (Exception ex) {
		}

		// 清除 session 中的登录状态
		User.clearLoginSession(session);

		// 使 session 失效
		session.invalidate();
	}
}