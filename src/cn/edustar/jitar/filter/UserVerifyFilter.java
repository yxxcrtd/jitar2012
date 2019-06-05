package cn.edustar.jitar.filter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.zdsoft.passport.demo.client.SessionManager;
import net.zdsoft.passport.entity.Account;
import net.zdsoft.passport.service.client.PassportClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.UserOnLine;
import cn.edustar.jitar.service.OnLineService;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.usermgr.BaseUser;

/**
 * 统一用户验证的过滤器
 * 
 * @author Yang XinXin
 * @version 1.0.0, 2008-05-21 09:35:18
 */
public class UserVerifyFilter implements Filter {

	/**
	 * 日志
	 */
	private static final Log log = LogFactory.getLog(UserVerifyFilter.class);

	/**
	 * 配置对象
	 */
	@SuppressWarnings("unused")
	private FilterConfig config;

	/**
	 * 用户服务
	 */
	private UserService userService;

	/**
	 * 在线服务
	 */
	private OnLineService onlineService;
	
	private String userMgrUrl = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		WebApplicationContext app_ctxt = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());

		// 从 spring factory 中获取 UserService
		this.userService = (UserService) app_ctxt.getBean("userService");
		this.onlineService = (OnLineService) app_ctxt.getBean("onlineService");
		//log.info("统一用户验证过滤器加载成功，验证地址：" + UserMgrModel.getUserMgrUrl());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp,FilterChain chain) throws IOException, ServletException {
		verifyUser((HttpServletRequest) req);
		if (chain == null || req == null || resp == null) {
			return;
		}
		chain.doFilter(req, resp);
	}

	public static final String getUserTicketFromCookie(	HttpServletRequest request) {
		String userTicket = "";
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("UserTicket".equalsIgnoreCase(cookie.getName())) {
					userTicket = cookie.getValue();
					break;
				}
			}
		}
		return userTicket == null ? "" : userTicket;
	}

	/**
	 * 验证用户登录
	 * 
	 * @param request
	 * @throws ServletException
	 */
	private void verifyUser(HttpServletRequest request) throws ServletException {
		// 判断配置，是否是浙大的统一用户
		ServletContext sc = request.getSession().getServletContext();
		String passportURL = sc.getInitParameter("passportURL") == null?null:PassportClient.getInstance().getPassportURL();

		if (passportURL == null)
			passportURL = "";

		if ("http://".equals(passportURL))
			passportURL = "";

		boolean bIsAdminLoginName = false;

		if (!("".equals(passportURL))) {
			// System.out.println("浙大用户,开始验证是否是admin");
			try {
				// 得到 Cookie 中的票证
				String userTicket = getUserTicketFromCookie(request);
				if (null == userTicket || "".equals(userTicket) || 0 == userTicket.length()) {
					// System.out.println("userTicket=NULL");
				} else {// 准备向统一用户服务器发送验证请求.
					// 新版本统一用户
//					Client client = new Client();
//					userMgrUrl = UserMgrModel.getUserMgrUrl();
//					if(userMgrUrl.length() == 0){
//						userMgrUrl = CommonUtil.getLocalHostSiteIP(request) + JitarConst.USERMGR_SITENAME +  "/";
//					}
//					client.setUserMgrUrl(userMgrUrl);
//					try {
//						client.Get(request);
//						if (null == client.getUsername() || client.getUsername().length() == 0) {
//							log.info("getUsername 没得到");
//						} else {
//							if ("admin".equals(client.getUsername())) {
//								bIsAdminLoginName = true;
//							}
//						}
//					} catch (XmlRpcException e) {
//						throw new ServletException(e);
//					}
				}
				/*
				 * XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
				 * // 配置客户端 //
				 * System.out.println("userTicket Url "+UserMgrModel.
				 * getUserMgrUrl() // + "xmlrpc"); config.setServerURL(new
				 * URL(UserMgrModel.getUserMgrUrl() + "xmlrpc")); // 执行Servlet
				 * config.setEnabledForExtensions(true);
				 * 
				 * XmlRpcClient client = new XmlRpcClient(); //
				 * 创建一个新的XmlRpcClient对象 client.setTransportFactory(new
				 * XmlRpcCommonsTransportFactory( client));
				 * client.setConfig(config); // 绑定到上面的配置对象
				 * 
				 * try { Object[] params = new Object[] { userTicket,
				 * request.getRemoteAddr() }; Object result = (Object)
				 * client.execute("UserService.getUserByTicket", params); //
				 * 执行XML-RPC调用 // log.info(result); if (result == null) { //
				 * System.out.println("result=NULL"); } else { //
				 * System.out.println
				 * ("result user="+request.getSession().getAttribute
				 * (User.SESSION_LOGIN_NAME_KEY)); BaseUser baseUser =
				 * (BaseUser) result; if
				 * ("admin".equals(baseUser.getUsername())) { bIsAdminLoginName
				 * = true; } } } catch (Exception e) { System.out.println("Err="
				 * + e.getMessage()); }
				 */

			} catch (Exception e) {
				System.out.println("Err=" + e.getMessage());
			}
		}

		// 如果不是浙大统一用户，或者虽然是浙大统一用户，但登录名是admin,则仍然使用我们自己的统一用户
		if ("".equals(passportURL) || (bIsAdminLoginName == true)) {
//			String ticket = Client.getUserTicketFromCookie(request);
//			System.out.println("获取客户端的Ticket：" + ticket);
//			if (null == ticket || "".equals(ticket) || 0 == ticket.length()) {
//				User.clearLoginSession(request.getSession());
//				request.removeAttribute("jitar.login.loginName");
//				return;
//			}
			HttpSession session = request.getSession(false);
//			if (null != session) {
//				String session_ticket = (String) session.getAttribute(User.SESSION_USER_TICKET);
//				if (ticket.equalsIgnoreCase(session_ticket)) {
//					if (null == request.getSession().getAttribute("jitar.login.loginName")) {
//						User.clearLoginSession(request.getSession());
//						request.removeAttribute("jitar.login.loginName");
//					}
//					return;
//				}
//			}
//			if (session == null) {
//				System.out.println(".............................");
//				session = request.getSession(true);
//			}
//			User.clearLoginSession(session);

			// 新版本统一用户
			//Client client = new Client();
			//String userMgrUrl = request.getServletContext().getFilterRegistration("CASValidationFilter").getInitParameter("casServerUrlPrefix");
			//log.info("（获取用户总数）统一用户地址：" + userMgrUrl);
			//client.setUserMgrUrl(userMgrUrl);
			
			//System.out.println("用户名：" + session.getAttribute("jitar.login.loginName"));
			//System.out.println("用户对象：" + session.getAttribute("loginUser"));
			
//			try {
//				client.Get(request);
//				if (null == client.getUsername() || client.getUsername().length() == 0) {
//					session.setAttribute(User.SESSION_USER_TICKET, ticket);
//					return;
//				}
//				
//				log.info("统一用户服务器返回的登录用户为：" + client.getUsername());
//				User user = userService.getUserByLoginName(client.getUsername());
//				if (null == user) {
//					user = createUser(client);
//				} else {
//					if (client.getBaseUser().getUsn() != user._getUserObject().getUsn()) {
//						user = updateUser(client, user._getUserObject());
//					}
//				}
//				BaseUser bu = client.getBaseUser();
//				session.setAttribute(User.SESSION_USER_TICKET, ticket);
//				session.setAttribute(User.SESSION_LOGIN_NAME_KEY,client.getUsername());
//				session.setAttribute(User.SESSION_LOGIN_USERMODEL_KEY, user);
//				session.setAttribute(User.SESSION_LOGIN_GENUSER_KEY, bu);
				
//				String products = request.getServletContext().getInitParameter("products");
//				if (null != products && !"".equals(products) && 0 < products.length()) {
//					session.setAttribute("list", products.split(","));
//				}
				
//				long nowTime = System.currentTimeMillis();
//				SimpleDateFormat sdf = new SimpleDateFormat("",	Locale.SIMPLIFIED_CHINESE);
//				sdf.applyPattern("yyyy年MM月dd日 HH时mm分ss秒");
//				UserOnLine userOnLine = new UserOnLine();
//				userOnLine.setUserId(user.getUserId());
//				userOnLine.setUserName(user.getLoginName());
//				userOnLine.setOnlineTime(nowTime);
//				onlineService.saveUserOnLine(userOnLine);
//			} catch (XmlRpcException e) {
//				throw new ServletException(e);
//			}
		} else {
			// 是浙大的统一用户
			String ticketZD = (String)request.getSession().getAttribute(SessionManager.PASSPORT_TICKET_KEY);
			// System.out.println("ticketZD="+ticketZD);
			if (ticketZD == null || ticketZD.length() == 0) {
				User.clearLoginSession(request.getSession());
				request.removeAttribute("jitar.login.loginName");
				return;
			}
			HttpSession session = request.getSession(false);
			if (session != null) {
				String session_ticket = (String) session.getAttribute(User.SESSION_USER_TICKET);

				if (ticketZD.equalsIgnoreCase(session_ticket)) {
					if (request.getSession().getAttribute("jitar.login.loginName") == null) {
						// 从SessionManager中清除ticket
						SessionManager.removeTicket(ticketZD);
						// 清除ticket是为了在session失效的时候不再重复通知Passport
						session.removeAttribute(SessionManager.PASSPORT_TICKET_KEY);
						session.removeAttribute(net.zdsoft.passport.demo.dto.User.KEY);

						User.clearLoginSession(request.getSession());
						request.removeAttribute("jitar.login.loginName");
					}
					return;
				}
			}
			if (session == null) {
				session = request.getSession(true);
			}
			User.clearLoginSession(session);

			// 获取ticket所属的Passport用户信息
			Account account = null;
			try {
				account = PassportClient.getInstance().checkTicket(ticketZD);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

			if (account == null) {
				return;
			}
			String accountID = account.getId();
			String userName = account.getUsername();
			String nickname = account.getNickname();
			String realName = account.getRealName();
			String password = account.getPassword();

			User user = null;

			ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
			UserService userservice = (UserService) ctx.getBean("userService");
			user = userservice.getUserByAccountId(accountID);
			if (user == null) {
				user = new User();
				UnitService unitService = (UnitService)ctx.getBean("unitService");
				Unit unit = unitService.getRootUnit();
				int unitId = 1;
				String unitPathInfo = "/1/";
				if(unit != null && unit.getUnitPathInfo() != null)
				{
					unitId = unit.getUnitId();
					unitPathInfo = unit.getUnitPathInfo();
				}
				user.setUnitId(unitId);
				user.setUnitPathInfo(unitPathInfo);
				user.setAccountId(accountID);
				user.setLoginName(userName);
				user.setNickName(nickname);
				user.setTrueName(realName);
				userservice.createUser(user);
				user = userservice.getUserByAccountId(accountID);
			}
			BaseUser baseUser = new BaseUser();
			baseUser.setId(user.getUserId());
			baseUser.setUsername(user.getLoginName());
			baseUser.setPassword(password);
			baseUser.setGuid(user.getUserGuid());

			session.setAttribute(User.SESSION_USER_TICKET, ticketZD);
			session.setAttribute(User.SESSION_LOGIN_NAME_KEY,user.getLoginName());
			session.setAttribute(User.SESSION_LOGIN_USERMODEL_KEY, user);
			// session.setAttribute(User.SESSION_LOGIN_GENUSER_KEY,
			// baseUserInfo);
			session.setAttribute(User.SESSION_LOGIN_GENUSER_KEY, null);
			long nowTime = System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("",	Locale.SIMPLIFIED_CHINESE);
			sdf.applyPattern("yyyy年MM月dd日 HH时mm分ss秒");
			UserOnLine userOnLine = new UserOnLine();
			userOnLine.setUserId(user.getUserId());
			userOnLine.setUserName(user.getLoginName());
			userOnLine.setOnlineTime(nowTime);
			onlineService.saveUserOnLine(userOnLine);

			// 将属于用户的ticket也记录到session中
			request.getSession().setAttribute(SessionManager.PASSPORT_TICKET_KEY, ticketZD);
			// 同时将ticket保存到SessionManager中,
			// 这样当此session失效的时候就可以通知Passport做ticket失效处理.
			// Passport会通知其他站点也对该用户做退出操作, 即实现“一退全退”.
			SessionManager.putTicket(ticketZD, request.getSession());
		}
	}

	/**
	 * 按照 XmlRpcUser 中的信息创建本地用户
	 * 
	 * @param xru
	 * @return
	 * @throws ServletException
	 */
//	private User createUser(Client client) throws ServletException {
//		User user_obj = new User();
//		copyUserInfo(user_obj, client.getBaseUser());
//		setNewUserInfo(user_obj);
//		userService.createUser(user_obj);
//		User user = userService.getUserByLoginName(client.getUsername(), false);
//		if (user == null) {
//			throw new ServletException("无法获得用户：" + client.getUsername()
//					+ "，的信息");
//		}
//		log.info("本地用户：" + client.getUsername() + "，创建成功");
//		return user;
//	}

	/**
	 * 按照 XmlRpcUser 中的信息更新本地用户信息
	 * 
	 * @param xru
	 * @param user_obj
	 * @return
	 */
//	private User updateUser(Client client, User user_obj) {
//		copyUserInfo(user_obj, client.getBaseUser());
//		userService.updateUser(user_obj);
//		User user = userService.getUserByLoginName(client.getUsername(), false);
//		return user;
//	}

	/**
	 * 从BaseUserInfo中复制用户信息
	 * 
	 * @param user
	 * @param bui
	 */
	private void copyUserInfo(User user, BaseUser bu) {
		user.setUserGuid(bu.getGuid());
		user.setLoginName(bu.getUsername());
		user.setTrueName(bu.getTrueName());
		user.setEmail(bu.getEmail());
		user.setUserStatus(null == bu.getStatus() ? 0 : bu.getStatus());
		user.setUsn(bu.getUsn());
		user.setPositionId(3);
	}

	/**
	 * 新建用户的时候设置一些字段的缺省值
	 * 
	 * @param user
	 */
	private void setNewUserInfo(User user) {
		user.setVirtualDirectory("u");
		user.setUserFileFolder(user.getLoginName());
		user.setBlogName(user.getNickName() + " 的博客");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		//
	}

}
