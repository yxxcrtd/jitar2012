package cn.edustar.jitar.jython;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.edustar.jitar.JitarRequestContext;
import cn.edustar.jitar.action.ActionInterface;
import cn.edustar.jitar.action.ActionLink;
import cn.edustar.jitar.action.CompatTask;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.util.WebUtil;

/**
 * Jython 脚本使用的 BaseAction, 取代原来的 BaseAdminAction.
 *
 *
 */
@SuppressWarnings("unchecked")
public class JythonBaseAction implements ActionInterface {
	
	public static final Log log = LogFactory.getLog(JythonBaseAction.class);
	
	
	public String execute()
	{
		return "";
	}
	
	/**
	 * 得到当前请求对象.
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return (HttpServletRequest)JitarRequestContext.getRequestContext().getRequest();
	}
	
	/**
	 * 得到当前响应对象.
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return (HttpServletResponse)JitarRequestContext.getRequestContext().getResponse();
	}

	/**
	 * 得到 session
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		return getRequest().getSession();
	}
	
	/**
	 * 得到 application
	 * 
	 * @return
	 */
	public ServletContext getApplication() {
		return JitarRequestContext.getRequestContext().getServletContext();
	}

	/**
	 * 得到当前登录用户
	 * 
	 * @return
	 */
	public User getLoginUser() {
		User user = WebUtil.getLoginUser(getSession());
		if (user == null) {
		} else {
		}
		return user;
	}
	
	/**
	 * 给 actionErrors 中添加一条错误
	 * 
	 * @param error
	 */
	public void addActionError(String error) {
		getActionErrors().add(error);
	}
	
	/**
	 * 给 actionMessages 中添加一条消息.
	 * @param msg
	 */
	public void addActionMessage(String msg) {
		getActionMessages().add(msg);
	}
	
	/**
	 * 给 actionLinks 中添加一个链接.
	 * @param alink
	 */
	public void addActionLink(ActionLink alink) {
		getActionLinks().add(alink);
	}
	
	/**
	 * 添加一个链接地址.
	 * @param text - 链接文字.
	 * @param link - 链接地址.
	 */
	public void addActionLink(String text, String link) {
		this.addActionLink(new ActionLink(text, link));
	}
	
	/**
	 * 添加一个链接地址.
	 * @param text - 链接文字.
	 * @param link - 链接地址.
	 * @param target - 目标窗口.
	 */
	public void addActionLink(String text, String link, String target) {
		this.addActionLink(new ActionLink(text, link, target));
	}
	
	// ======================================================================

	/**
	 * 得到错误集合.
	 */
	public List getActionErrors() {
		List errors = (List)getRequest().getAttribute("actionErrors");
		if (errors == null) {
			errors = new ArrayList();
			getRequest().setAttribute("actionErrors", errors);
		}
		return errors;
	}

	/**
	 * 得到消息集合.
	 * @return
	 */
	public List getActionMessages() {
		List msgs = (List)getRequest().getAttribute("actionMessages");
		if (msgs == null) {
			msgs = new ArrayList();
			getRequest().setAttribute("actionMessages", msgs);
		}
		return msgs;
	}
	
	/**
	 * 得到链接集合.
	 * @return
	 */
	public List getActionLinks() {
		List links = (List)getRequest().getAttribute("actionLinks");
		if (links == null) {
			links = new ArrayList();
			getRequest().setAttribute("actionLinks", links);
		}
		return links;
	}
	
	/**
	 * 判断是否有任何错误信息?
	 * @return
	 */
	public boolean hasActionErrors() {
		return getActionErrors().size() > 0;
	}
	
	/**
	 * 判断是否有任何错误信息? 等同于 hasActionErrors() 方法.
	 * @return
	 */
	public boolean hasErrors() {
		return this.hasActionErrors();
	}
	
	/**
	 * 判断是否有任何消息信息?
	 * @return
	 */
	public boolean hasActionMessages() {
		return getActionMessages().size() > 0;
	}
	
	/**
	 * 是否有任何 actionLinks ?  .
	 * @return
	 */
	public boolean hasActionLinks() {
		List links = getActionLinks();
		return (links == null || links.size() == 0) ? false : true;
	}
	
	/**
	 * 清除所有错误信息.
	 */
	public void clearActionError() {
		getActionErrors().clear();
	}

	/**
	 * 返回 request.getHeader("Referer"), 其表示前一个页面.
	 * 
	 * @return
	 */
	public String getRefererHeader() {
		return getRequest().getHeader("Referer");
	}

	/**
	 * 添加一个缺省的 [返回] 链接.
	 * <li>首先尝试从参数中获取 '__referer' 的地址.
	 * <li>从 HTTP Header 中获取 Referer.
	 * <li>使用 window.history.back().
	 */
	public void addDefaultReturnActionLink() {
		
		String referer = getRequest().getParameter("__referer");
		if (referer == null || referer.length() == 0)
			referer = getRequest().getHeader("Referer");
		if (referer == null || referer.length() == 0)
			addActionLink(ActionLink.HISTORY_BACK);
		else
			addActionLink(new ActionLink("返回", referer));
	}

	// === 用户,协作组验证 ====.
	
	/**
	 * 是否能够显示指定用户的页面, 在访问该用户的工作室各个页面的时候要调用此方法进行检查.
	 */
	public boolean canVisitUser(User user) {
		return CompatTask.canVisitUser(user, this);
	}

	/**
	 * 判断指定用户能否在个人面板中更改信息, 被锁定的用户不能更改自己的任何信息.
	 * @param user
	 * @return
	 */
	public boolean canManageBlog(User user) {
		return CompatTask.canManageBlog(user, this);
	}
	
	/**
	 * 判断当前登录用户是否能够访问指定的协作组.
	 * @param group
	 * @return
	 */
	public boolean canVisitGroup(Group group) {
		return CompatTask.canVisitGroup(group, this);
	}
	
	/**
	 * 判断指定用户能否进入指定的协作组, 在此之前必须调用过 canVisitGroup().
	 * @param group
	 * @param user
	 * @param gm
	 * @param ai
	 * @return
	 */
	public boolean canEnterGroup(Group group, User user, GroupMember gm) {
		return CompatTask.canEnterGroup(group, user, gm, this);
	}

	/**
	 * 判断指定用户能否给指定协作组中发表内容, 必须在此之前调用过 canEnterGroup().
	 * @param group
	 * @param user
	 * @param gm
	 * @return
	 */
	public boolean canPubToGroup(Group group, User user, GroupMember gm) {
		return CompatTask.canPubToGroup(group, user, gm, this); 
	}

	/**
	 * 判断当前登录用户能否进行管理操作, 
	 * @return
	 */
	public boolean canAdmin() {
		return CompatTask.canAdmin(getLoginUser(), this);
	}
	
	public void write(String msg) throws Exception {
		HttpServletResponse res = this.getResponse();
		res.setCharacterEncoding("utf-8");
		res.getWriter().print(msg);
	}
}
