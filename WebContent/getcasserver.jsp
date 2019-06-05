<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.net.URLEncoder"%>
<%
String url=request.getServletContext().getFilterRegistration("CAS-Authentication-Filter").getInitParameter("casServerUrlPrefix");
if(url==null || url.length()==0){
    url = request.getRequestURL().toString();
}
%>
<%=url%>