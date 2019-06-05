<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.edustar.jitar.model.SiteUrlModel"%>
<%@page import="cn.edustar.jitar.model.UserMgrModel"%>
<%@page import="cn.edustar.jitar.action.JspPageHelper"%>
<%@page import="java.util.UUID"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="cn.edustar.jitar.pojos.User"%>
<%!protected void cleanSession(HttpServletRequest _request) {
		HttpSession session = _request.getSession();
		session.setAttribute(User.SESSION_USER_TICKET, null);
		session.setAttribute(User.SESSION_LOGIN_NAME_KEY, null);
		session.setAttribute(User.SESSION_LOGIN_USERMODEL_KEY, null);
	}
	private String getDomain(String url) {
		String returnDomain = "";
		Boolean isIP = true;
		if (url.contains(".")) {
			String[] tempArray = url.split("\\.");
			for (int i = 0; i < tempArray.length; i++) {
				try {
					Integer.parseInt(tempArray[i]);
				} catch (NumberFormatException e) {
					isIP = false;
					break;
				}
			}
			if (isIP) {
				returnDomain = "";
			} else {
				switch (tempArray.length) {
					case 2 :
						returnDomain = url;
						break;
					default :
						returnDomain = url.substring(url.indexOf(".") + 1);
						break;
				}
			}
		} else {
			returnDomain = "";
		}
		return returnDomain;
	}
	protected void destroyCookies(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		try {
			if (null != cookies) {
				for (Cookie cookie : cookies) {
					if ("UserTicket".equals(cookie.getName())) {
						cookie.setMaxAge(Integer.MIN_VALUE);
						cookie.setValue(null);
						cookie.setPath("/");
						String domain = getDomain(request.getServerName());
						if (!"".equals(domain) && !Character.isDigit(domain.charAt(0))) {
							cookie.setDomain("." + domain);
						}
						response.addCookie(cookie);
					}
				}
			}
		} catch (Exception e) {
		}
	}%>
<%
	JspPageHelper helper = new JspPageHelper(pageContext);
	helper.logout();

	destroyCookies(request, response);
	cleanSession(request);
	String loginUrl = "";
	if (request.getServerPort() == 80) {
		loginUrl = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/";
	} else {
		loginUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()	+ "/";
	}
	//loginUrl = loginUrl + "manage/eduyunlogin?key=";
	loginUrl = URLEncoder.encode(loginUrl, "utf-8");
	response.sendRedirect("http://www.eduyun.cn/index.php?m=user&c=member&a=logout&backurl=" + loginUrl);
%>
