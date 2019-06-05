package com.dsideal.Bruce.clienttest;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.UserService;

import dsidealsso.CustomerHandler;

public class CustomerHandlerImp implements CustomerHandler {
	public void hanle(ServletRequest request, ServletResponse response,Assertion assertion) {
		  
		System.out.println("得到东师理想用户？");
		
		HttpServletRequest temprequest=(HttpServletRequest)request;
		//temprequest.getSession().setAttribute("realname", assertion.getPrincipal().getAttributes().get("realname"));
		//temprequest.getSession().setAttribute("sso_userid", assertion.getPrincipal().getAttributes().get("sso_userid"));
		//temprequest.getSession().setAttribute("userid", assertion.getPrincipal().getAttributes().get("userid"));
		//temprequest.getSession().setAttribute("username", assertion.getPrincipal().getAttributes().get("username"));
		//temprequest.getSession().setAttribute("orgid", assertion.getPrincipal().getAttributes().get("orgid"));
		//temprequest.getSession().setAttribute("deptid", assertion.getPrincipal().getAttributes().get("deptid"));
		
		
		AttributePrincipal attributePrincipal = (AttributePrincipal)assertion.getPrincipal();
		Map <String,Object>map = attributePrincipal.getAttributes();
		String userid= String.valueOf(map.get("userid"));
		String sso_userid= String.valueOf(map.get("sso_userid")); 
		String usernme = String.valueOf(map.get("username"));
		String orgid = String.valueOf(map.get("orgid"));
		String deptid = String.valueOf(map.get("deptid")); 
		String realname = String.valueOf(map.get("realname"));
		
		System.out.println("userid="+userid);
		System.out.println("sso_userid="+sso_userid);
		System.out.println("usernme="+usernme);
		System.out.println("orgid="+orgid);
		System.out.println("deptid="+deptid);
		System.out.println("realname="+realname);
		
		//sso_userid  = assertion.getPrincipal().getAttributes().get("userid").toString();
		String userName = assertion.getPrincipal().getAttributes().get("realname").toString();
		String loginName = assertion.getPrincipal().getAttributes().get("username").toString();
		
		System.out.println("userName="+userName);
		System.out.println("loginName="+loginName);

		
		User user = null;
		ServletContext sc = request.getServletContext();
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sc);
		UserService userService = (UserService) ac.getBean("userService");
		user = userService.getUserByLoginName(loginName);

		if (null == user) {
			user = new User();
			user.setAccountId(sso_userid);
			user.setLoginName(loginName);
			user.setUserStatus(0);
			user.setUnitId(1);
			user.setUsn(1);
			user.setPositionId(3);
			user.setVirtualDirectory("u");
			user.setUserFileFolder(loginName);
			user.setBlogName(userName + " 的博客");
			user.setTrueName(realname);
			user.setNickName(realname);
			user.setUnitPathInfo("/1/");
			userService.createUser(user);
		}
		
		temprequest.getSession().setAttribute(User.SESSION_LOGIN_NAME_KEY, loginName);
		temprequest.getSession().setAttribute(User.SESSION_LOGIN_USERMODEL_KEY, user);
	}

}
