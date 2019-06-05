<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.edustar.jitar.pojos.User"%>
<%@ page import="cn.edustar.jitar.service.UserService"%>
<%@ page import="org.springframework.web.context.support.*"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //System.out.println("request.getQueryString()="+request.getQueryString());
    /*
      User user = null;
      ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
      UserService userservice = (UserService) ac.getBean("userService");
      user = userservice.getUserByLoginName(ssoId);
      if (null != user) {
          session.setAttribute(User.SESSION_LOGIN_NAME_KEY, ssoId);
          session.setAttribute(User.SESSION_LOGIN_USERMODEL_KEY, user);
      }
    */
    String ru = request.getParameter("ruu");
    if (null == ru || "".equals(ru) || 0 == ru.length() || "null".equals(ru)) {
        if (session.getAttribute("__ruu") != null) {
            ru = session.getAttribute("__ruu").toString();
            session.setAttribute("__ruu", null);
        }
    }
    if (null == ru || "".equals(ru) || 0 == ru.length() || "null".equals(ru)) {
        response.sendRedirect("../index.py");
    } else {
        response.sendRedirect(ru);
    }
%>

