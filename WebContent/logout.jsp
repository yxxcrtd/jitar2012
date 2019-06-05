<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<%
    String serverName = "";
    String logoutUrl = "";
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

    if (null != application.getFilterRegistration("CAS-Authentication-Filter") || null != application.getFilterRegistration("filterchainproxy")) {
        if (null != application.getFilterRegistration("CAS-Authentication-Filter")) {
            serverName = application.getFilterRegistration("CAS-Authentication-Filter").getInitParameter("serverName");
            logoutUrl = application.getFilterRegistration("CAS-Authentication-Filter").getInitParameter("casServerLoginUrl");
        } else if (null != application.getFilterRegistration("filterchainproxy")) {
            serverName = application.getFilterRegistration("filterchainproxy").getInitParameter("serverName");
            logoutUrl = application.getFilterRegistration("filterchainproxy").getInitParameter("casServerLoginUrl");
        }

        if (logoutUrl == null || logoutUrl.length() == 0) {
            logoutUrl = org.jasig.cas.client.util.CasConst.getInstance().getCasServerLoginUrl();
        }
        if (logoutUrl.endsWith("login/")) {
            logoutUrl = logoutUrl.substring(0, logoutUrl.length() - 6);
        }
        if (logoutUrl.endsWith("login")) {
            logoutUrl = logoutUrl.substring(0, logoutUrl.length() - 5);
        }
        String returnService = request.getParameter("ru");
        if (returnService == null) {
            returnService = request.getParameter("redUrl");
        }
        if (returnService == null) {
            returnService = request.getHeader("referer");

        }
        if (returnService == null) {
            returnService = request.getRequestURL().toString();
            returnService = returnService.substring(0, returnService.lastIndexOf("/"));
        }

        if (null == returnService || "".equals(returnService) || 0 == returnService.length() || "null".equals(returnService)) {
            if (null != session.getAttribute("__ru")) {
                returnService = session.getAttribute("__ru").toString();
                session.setAttribute("__ru", null);
            }
        }
        request.getSession().invalidate();
        if (serverName == null && serverName.length() == 0) {
            serverName = returnService;
        }
        //System.out.println("返回的退出前的地址：" + returnService);
        if (null == returnService || "".equals(returnService) || 0 == returnService.length() || "null".equals(returnService)) {
            response.sendRedirect(basePath + "index.py");
        } else {
            response.sendRedirect(logoutUrl + "/logout?service=" + URLEncoder.encode(serverName, "UTF-8") + "&ru=" + URLEncoder.encode(returnService, "UTF-8"));
        }
    } if (null != application.getFilterRegistration("ssoFilter")) {
        String SSOServerURL = request.getServletContext().getInitParameter("SSOServerURL");
        String SSOServerURL1 = SSOServerURL;
        String SSOServerURL2 = SSOServerURL;
        if(SSOServerURL.indexOf(";")>-1){
            String[] arrayUrl = SSOServerURL.split("\\;");
            SSOServerURL1 = arrayUrl[0];
            SSOServerURL2 = arrayUrl[1];
        }         
        if(SSOServerURL1.endsWith("/")){
        	SSOServerURL1 = SSOServerURL1.substring(0, SSOServerURL1.length()-1);
        }       
        logoutUrl = SSOServerURL1 + application.getFilterRegistration("ssoFilter").getInitParameter("serverLogoutUrl");
        String clientCode =  request.getServletContext().getInitParameter("clientCode");
        String reciverAction=application.getFilterRegistration("ssoFilter").getInitParameter("reciverAction");
        
        //对教研的返回 地址，加上 http://**
        if(!reciverAction.startsWith("http://")){
            if(reciverAction.startsWith("/")){
                reciverAction = reciverAction.substring(1);
            }
            reciverAction = basePath + reciverAction;
        }
        
        String returnService = request.getParameter("ru");
        if (returnService == null) {
            returnService = request.getParameter("redUrl");
        }
        if (returnService == null) {
            returnService = request.getHeader("referer");
        }
        if (returnService == null) {
            returnService = request.getRequestURL().toString();
            returnService = returnService.substring(0, returnService.lastIndexOf("/"));
        }

        if (null == returnService || "".equals(returnService) || 0 == returnService.length() || "null".equals(returnService)) {
            if (null != session.getAttribute("__ru")) {
                returnService = session.getAttribute("__ru").toString();
                session.setAttribute("__ru", null);
            }
        }
        
        request.getSession().invalidate();
        
        String ssoLogoutUrl = logoutUrl + "?clientCode="+clientCode+"&backurl="+URLEncoder.encode(reciverAction,"UTF-8");
        
        //System.out.println(""+ssoLogoutUrl);
        
        response.sendRedirect(ssoLogoutUrl);        
    }else {
        request.getSession().invalidate();
        response.sendRedirect(basePath + "index.action");
    }
%>
