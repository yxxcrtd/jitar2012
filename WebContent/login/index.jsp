<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.edustar.jitar.pojos.User"%>
<%@ page import="cn.edustar.jitar.service.UserService"%>
<%@ page import="org.springframework.web.context.support.*"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="org.jasig.cas.client.util.CasConst"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

if  ( null != application.getFilterRegistration("CAS-Authentication-Filter") || null !=application.getFilterRegistration("filterchainproxy")){
    Boolean EnableDebug = false;
    //System.out.println("request.getQueryString()="+request.getQueryString());

    String ssoId = request.getRemoteUser();
    //System.out.println("登录后的ssoId:" + ssoId);

    if (null != ssoId || !"".equals(ssoId) || 0 < ssoId.length()) {
        session.setAttribute("ssoId", ssoId);

        User user = null;
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        UserService userservice = (UserService) ac.getBean("userService");
        user = userservice.getUserByLoginName(ssoId);
        if (null != user) {
            session.setAttribute(User.SESSION_LOGIN_NAME_KEY, ssoId);
            session.setAttribute(User.SESSION_LOGIN_USERMODEL_KEY, user);
        }
    }

    String ru = request.getParameter("ruu");
    if (EnableDebug){
           System.out.println("login/index.jsp中Query ruu=" + ru);  
    }
    if (null == ru || "".equals(ru) || 0 == ru.length() || "null".equals(ru)) {
        if (session.getAttribute("__ruu") != null) {
            ru = session.getAttribute("__ruu").toString();
            if (EnableDebug){
                System.out.println("login/index.jsp中Session __ruu=" + ru);  
            }
            session.setAttribute("__ruu", null);
        }
    }
    if (null == ru || "".equals(ru) || 0 == ru.length() || "null".equals(ru)) {
        response.sendRedirect("../index.action");
    } else {
        response.sendRedirect(ru);
    }
    }else{
        
        response.sendRedirect(basePath + "index.action");       
    }
%>

