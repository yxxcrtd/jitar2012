<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<%
    String logoutUrl = "";
    String clientCode = "";
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

    if (null != application.getFilterRegistration("ssoFilter")) {
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
        clientCode =  request.getServletContext().getInitParameter("clientCode");
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
        
    } else {
        request.getSession().invalidate();
        response.sendRedirect(basePath + "index.action");
    }
%>
