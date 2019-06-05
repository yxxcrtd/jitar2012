<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="cn.edustar.jitar.pojos.User" %>
<%@ page import="cn.edustar.jitar.service.UserService" %>
<%@ page import="org.springframework.web.context.support.*"%>   
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="net.zdsoft.passport.demo.client.SessionManager" %>

<%
	// 此页面必须被CAS filter 过滤,保证已经登录CAS,可以获取到remoteUser
	String name = request.getParameter("username");
	System.out.println("用户输入的用户名：" + name);
	String pass = request.getParameter("password");
	System.out.println("用户输入的密码：" + pass);
	String ticket = request.getParameter("ticket");
	System.out.println("用户票证：" + ticket);
	
	// 一些非空，注入判断
	if(name == null || pass==null || name=="" || pass=="" ) {
		response.sendRedirect("./");
		return;
	}
	name = name.replaceAll("'","").trim();
	System.out.println("处理后的用户名：" + name);
 
	User user = null;
	ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(getServletContext());   
	UserService userservice = (UserService) ac.getBean("userService");
	user = userservice.getUserByLoginName(name);
	if(null == user) { // 登录验证失败		
		response.sendRedirect("./");
		return;
	}
	String ssoId = request.getRemoteUser();
	//user.setSsoId(ssoId);
	//client.updateUserSSOID(user);  // 更新映射
	session.setAttribute("CPUSER", user);
	response.sendRedirect("./");
	
	session.setAttribute("rand", null);
	session.setAttribute("jitar.login.userId", user.getUserId());
	session.setAttribute("jitar.login.loginName", user.getLoginName());
	session.setAttribute(User.SESSION_LOGIN_USERMODEL_KEY,user);
	session.setAttribute(SessionManager.PASSPORT_TICKET_KEY, ticket);
	SessionManager.putTicket(ticket, session);
%>


