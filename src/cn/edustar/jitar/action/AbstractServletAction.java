package cn.edustar.jitar.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ServletContextAware;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.util.WebUtil;

import com.opensymphony.xwork2.ActionContext;

/**
 * 增强了获取'servlet_ctxt,request,response'方法的'Action'基类实现
 * 'spring'负责注入'servelt_ctxt'
 * 'struts'负责注入'request,response'
 */
public abstract class AbstractServletAction extends BaseAction implements ServletRequestAware, ServletResponseAware, ServletContextAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6034343726322315800L;

	/** 日志 */
	protected static final Logger log = LoggerFactory.getLogger(AbstractServletAction.class);

	/** Web 应用环境 */
	protected ServletContext servlet_ctxt;
	
	/** 请求对象 */
	protected HttpServletRequest request;

	/** 响应对象 */
	protected HttpServletResponse response;
	
	/** cmd = add/update 表示编辑操作的成功返回 */
	public static final String EDIT_SUCCESS = "Edit_Success";

	/** cmd = list 表示显示列表操作的成功返回 */
	public static final String LIST_SUCCESS = "List_Success";
	
	
	public static final String ADMIN_LIST_SUCCESS = "Admin_List_Success";
	

	/** cmd = add 添加操作默认的成功返回, "Add_Success" */
	public static final String ADD_SUCCESS = "Add_Success";

	/** cmd = upd 修改操作默认的成功返回, "Update_Success" */
	public static final String UPDATE_SUCCESS = "Update_Success";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servlet_ctxt = servletContext;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(javax.servlet.http.HttpServletRequest)
	 */
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * 得到当前 Struts ActionContext 对象
	 * 
	 * @return
	 */
	public ActionContext getActionContext() {
		return ActionContext.getContext();
	}

	/**
	 * 得到当前 Action 的 Request 对象
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return this.request;
	}

	/**
	 * 得到当前 Action 的 Session 对象
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		return this.request.getSession();
		
	}

	/**
	 * 实现获取 ServletContext 的方法
	 * 
	 * @return
	 */
	public ServletContext getServletContext() {
		return this.servlet_ctxt;
	}

	/**
	 * 判定当前是否有用户登录了,判定依据是：'session.getAttribute(User.SESSION_LOGIN_USERID_KEY)'非空
	 * 
	 * @return
	 */
	public boolean isUserLogined() {
		return getLoginUser() != null;
	}

	/**
	 * 得到当前登录用户的登录名,如果当前没有用户登录则返回 null, 推荐现在使用函数 getLoginUser()
	 * 
	 * @return
	 */
	public String getLoginUserName() {
		User user = getLoginUser();
		return user == null ? null : user.getLoginName();
	}

	/**
	 * 得到当前登录用户, 登录用户在 统一用户认证 Filter 里面被设置
	 * 
	 * @return 如果没有则返回 null
	 */
	public User getLoginUser() {
		return WebUtil.getLoginUser(getSession());
	}

	/**
	 * 清除登录状态，一般在修改了用户信息之后使用，将强迫过滤器重新加载用户信息
	 */
	protected void clearLoginSession() {
		WebUtil.clearLoginSession(getSession());
	}
	
	/**
	 * 在 actionErrors 中设置 '不支持的命令: $cmd', 然后返回 ERROR
	 * 
	 * @param cmd
	 * @return
	 */
	protected String unknownCommand(String cmd) {
		super.addActionError("不支持的命令: " + cmd);
		return ERROR;
	}
	
	/**
	 * 根据pageId清除widget缓存
	 * @param key
	 */
	public void removePageId(int key){
		JitarContext.getCurrentJitarContext().getCacheProvider().getCache(String.valueOf(key)).remove(String.valueOf(key));
	}

	/**
	 * 将一个属性设置到 request 对象中，一般用于传递给下一个处理 servlet 或 freemarker 模板
	 * 
	 * @param key
	 * @param val
	 */
	public void setRequestAttribute(String key, Object val) {
		request.setAttribute(key, val);
	}

	/**
	 * 返回 request.getHeader("Referer"), 其表示前一个页面
	 * 
	 * @return
	 */
	public String getRefererHeader() {
		return request.getHeader("Referer");
	}

	/**
	 * 把 referer header 信息放到 request 环境中
	 */
	public void putRefererToRequest() {
		this.setRequestAttribute("referer", getRefererHeader());
	}

	/**
	 * 添加一个缺省的 [返回] 链接
	 * <li>首先尝试从参数中获取 '__referer' 的地址
	 * <li>从 HTTP Header 中获取 Referer.
	 * <li>使用 window.history.back().
	 */
	public void addDefaultReturnActionLink() {
		String referer = request.getParameter("__referer");
		if (referer == null || referer.length() == 0)
			referer = request.getHeader("Referer");
		if (referer == null || referer.length() == 0)
			super.addActionLink(ActionLink.HISTORY_BACK);
		else
			super.addActionLink(new ActionLink("返回", referer));
	}

	/**
	 * 是否能够显示指定用户的页面, 在访问该用户的工作室各个页面的时候要调用此方法进行检查
	 */
	public boolean canVisitUser(User user) {
		return CompatTask.canVisitUser(user, this);
	}

	/**
	 * 判断指定用户能否在个人面板中更改信息, 被锁定的用户不能更改自己的任何信息
	 * 
	 * @param user
	 * @return
	 */
	public boolean canManageBlog(User user) {
		return CompatTask.canManageBlog(user, this);
	}
	
	/**
	 * 判断当前登录用户是否能够访问指定的协作组
	 * 
	 * @param group
	 * @return
	 */
	public boolean canVisitGroup(Group group) {
		return CompatTask.canVisitGroup(group, this);
	}
	
	/**
	 * 判断指定用户能否进入指定的协作组, 在此之前必须调用过 canVisitGroup()
	 * 
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
	 * 判断指定用户能否给指定协作组中发表内容, 必须在此之前调用过 canEnterGroup()
	 * 
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
	 * 
	 * @return
	 */
	public boolean canAdmin() {
		return CompatTask.canAdmin(getLoginUser(), this);
	}
	
}
