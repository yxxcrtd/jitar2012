package com.winupon.action;
import cn.edustar.jitar.action.ManageBaseAction;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.ParamUtil;

public class LoginAction extends ManageBaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 395066695058529026L;
	private int userStatus;
	private ParamUtil param_util;
	private String username;
	private String password;
	//private int serverId=423701;
	//private String serverPassword="UT5ZNZR8JQDF6KY6KUEU7U8FQVRJ7AZZ";
	@SuppressWarnings("unused")
	private String redUrl;
	
	/** 用户服务 */
	private UserService userService;
	
	@Override
	protected String execute(String cmd) throws Exception {
		this.param_util = new ParamUtil(getActionContext().getParameters());
		redUrl = param_util.safeGetStringParam("redUrl");
		username= param_util.safeGetStringParam("username");
		password= param_util.safeGetStringParam("password");
		//if(!("".equals(username)))
		//{
		//	User user= userService.getUserByLoginName(username);
		//}
		return INPUT;	
		/*
		Map<String, Object> session = ActionContext.getContext().getSession();
		String ver = (String) session.get("rand");
		session.put("rand", null);
		this.param_util = new ParamUtil(getActionContext().getParameters());
		redUrl = param_util.safeGetStringParam("redUrl");
		//用户登录
		User user=null;
		String urlAddr="http://gs.edu88.com:10082/client/login.htm";
		HttpClient client = new HttpClient();
		HttpState initialState = new HttpState();
		String UserTicket = "";
		Cookie[] cookie = request.getCookies();
		org.apache.commons.httpclient.Cookie postCookie = null;
		UTF8PostMethod method = new UTF8PostMethod(urlAddr);
		method.setParameter("server", String.valueOf(serverId));
		method.setParameter("username", username);
		String auth=serverPassword+""+serverId+username+password;
		auth=MD5.toMD5(auth);
		method.setParameter("auth", auth);

		System.out.println("登录地址："+urlAddr);
		System.out.println("server="+serverId);
		System.out.println("username="+username);
		System.out.println("auth="+auth);
		try
		{
			client.executeMethod(method);
		}
		catch(Exception ex)
		{
			this.addActionError(ex.getMessage());
			return INPUT;			
			
		}
		String result = method.getResponseBodyAsString();
		//method.releaseConnection();
		System.out.println("result="+result);
		//this.addActionError(result);
		
		return INPUT;
		
		/*
		if (user == null) {
			this.addActionError(this.getText("usermgr.validation.error"));
			return INPUT;
		}
		userStatus = user.getUserStatus();
		switch (userStatus) {
		case 0:
			break;
		case 1:
			this.addActionError(this.getText("usermgr.user.status.audit"));
			return INPUT;
		case 2:
			this.addActionError(this.getText("usermgr.user.status.deleted"));
			return INPUT;
		default:
			this.addActionError(this.getText("usermgr.user.status.unknown"));
			return INPUT;
		}
		//if (equalPassword(user.getUserPassword(), password) == false) {
		//	this.addActionError(this.getText("usermgr.validation.error"));
		//	return INPUT;
		//}
		//TicketInfo ticket = userService.createUserTicket(user, request.getRemoteAddr());
		//writeClientCookie(user, ticket);
		//updateLoginInfo(user);
		session.put("jitar.login.userId", user.getUserId());
		session.put("jitar.login.loginName", username);
		request.setAttribute("username", username);
		request.setAttribute("user", user);
		return "Login_Success";	
		*/
	}
	/*
	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	*/	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}
	
	/** 用户服务 */
	public UserService getUserService() {
		return this.userService;
	}
	
	/** 用户服务 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}	
}
