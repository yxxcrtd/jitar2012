<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.io.IOException" %>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="net.zdsoft.passport.demo.client.SessionManager" %>
<%@ page import="cn.edustar.jitar.pojos.User" %>
<%@ page import="cn.edustar.jitar.pojos.Unit" %>
<%@ page import="cn.edustar.usermgr.BaseUser"%>
<%@ page import="cn.edustar.jitar.service.UserService" %>
<%@ page import="cn.edustar.jitar.service.UnitService" %>
<%@ page import="net.zdsoft.passport.entity.Account" %>
<%@ page import="net.zdsoft.passport.entity.Score" %>
<%@ page import="net.zdsoft.passport.service.client.PassportClient" %>
<%@page import="org.springframework.web.context.support.*"%>   
<%@page import="org.springframework.context.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String ticket = request.getParameter("ticket");
String auth = request.getParameter("auth");
String input = request.getParameter("input");
String url = request.getParameter("url");
// 对参数进行校验
        if (StringUtils.isEmpty(ticket)) {
            response.getWriter().write("Passport ticket为空");
            return;
        }

        if (StringUtils.isEmpty(auth)) {
            response.getWriter().write("Passport校验码为空");
            return;
        }

        if (StringUtils.isEmpty(input)) {
            if (!PassportClient.getInstance().isValidVerifyAuth(ticket, url,
                    auth)) {
                response.getWriter().write("Passport校验码非法");
                return;
            }
        }
        else if (!PassportClient.getInstance().isValidVerifyAuth(ticket, url,
                input, auth)) {
            response.getWriter().write("Passport校验码非法");
            return;
        }

        // 获取ticket所属的Passport用户信息
        Account account = null;
        try {
            account = PassportClient.getInstance().checkTicket(ticket);
        }
        catch (Exception e) {
            response.getWriter().write(e.getMessage());
            return;
        }

        if (account == null) {
            response.getWriter().write("Passport ticket[" + ticket + "]对应的帐号不存在");
            return;
        }

        // 此处只是简单写一下, 实际使用中可以根据account的id从系统中获取关联的用户信息
        // 例如: User user = userService.getUserByAccountId(account.getId());
        String accountID=account.getId();
        String userName=account.getUsername();
        String nickname=account.getNickname();
        String realName=account.getRealName();
        String password=account.getPassword();
        
        User user=null;
        
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());   
		UserService userservice = (UserService)ctx.getBean("userService");
		//浙大用户如果已经删除了，本系统中的用户依然会存在
		//如此会出现这样的问题：
		//如果某个用户在浙大用户平台上注册后，登录系统，然后再在浙大用户平台上删除，又增加用户，再次登录，会出现loginName已经存在的错误
		//现在更改：先判断账号accountID是否存在，不存在，则判断登录名是否存在，如果不存在，则增加用户，如果登录名存在，则更新其账号accountID
		user=userservice.getUserByAccountId(accountID);      
        if(user==null)
        { 
        	if(nickname==null || nickname.length()==0){
	            response.getWriter().write("未给出用户"+realName+"登录名 或该登录名非法");
	            return;
        	}
        	else{
        		
        		User userByLogiName=userservice.getUserByLoginName(userName); 
        		if(userByLogiName==null)
        		{
        			//如果登录名不存在，则增加该用户
			        user = new User();
			        UnitService unitService = (UnitService)ctx.getBean("unitService");
					Unit unit = unitService.getRootUnit();
					int unitId = 1;
					String unitPathInfo = "/0/1/";
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
			        user.setUserStatus(0);
			        userservice.createUser(user);
        		}else{
        			//用户的登录名已经存在，则更新accountID
        			user = userByLogiName; 
        			user.setAccountId(accountID);
                    user.setNickName(nickname);
                    user.setTrueName(realName);
                    userservice.updateUser(user);
        		}
        		user=userservice.getUserByAccountId(accountID);
        	}
	    }
	    
		BaseUser baseUserInfo = new BaseUser();
		baseUserInfo.setId(user.getUserId());
		baseUserInfo.setUsername(user.getLoginName());
		baseUserInfo.setPassword(password);
		baseUserInfo.setGuid(user.getUserGuid());
        
		session.setAttribute("rand", null);
		session.setAttribute("jitar.login.userId", user.getUserId());
		session.setAttribute("jitar.login.loginName", user.getLoginName());
		
		//System.out.println("设置Session ["+User.SESSION_LOGIN_USERMODEL_KEY+"]=user:loginname="+user.getLoginName());
		
		session.setAttribute(User.SESSION_LOGIN_USERMODEL_KEY,user);
		/*
		Cookie cookie = new Cookie("UserTicket", ticket);
		cookie.setMaxAge(INT_SESSION_ABATE_TIME);
		String dynamicDomain = request.getSession().getServletContext().getInitParameter("dynamicDomain");
		if ("".equals(dynamicDomain) || null == dynamicDomain || dynamicDomain.length() < 0) {
			String sDomain = getDomain(request.getServerName());
			if (!sDomain.equals("") && !Character.isDigit(sDomain.charAt(0))) {
				cookie.setDomain(sDomain);
			}
		} else {
			cookie.setDomain(dynamicDomain);			
		}
		cookie.setPath("/");
		response.addCookie(cookie);
		*/
	        
        // 将当前登录的用户保存到session中
        //session.setAttribute(User.SESSION_LOGIN_NAME_KEY, user.getLoginName());
        
        // 将属于用户的ticket也记录到session中
        session.setAttribute(SessionManager.PASSPORT_TICKET_KEY, ticket);
        // 同时将ticket保存到SessionManager中,
        // 这样当此session失效的时候就可以通知Passport做ticket失效处理.
        // Passport会通知其他站点也对该用户做退出操作, 即实现“一退全退”.
        SessionManager.putTicket(ticket, session);

        // 如果登录时没有设置要返回的页面, 则跳转到登录系统后的默认页面
        // 否则跳转到指定的返回页面
        if (StringUtils.isEmpty(url)) {
            response.sendRedirect("/index.py");
        }
        else {
            response.sendRedirect(url);
        }        
%>
