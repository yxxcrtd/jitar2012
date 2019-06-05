<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@page import="java.net.URLEncoder"%>
<%

String loginUrl = "";
if( request.getServerPort() == 80)
{
	loginUrl = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/";
}
else
{
	loginUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
}
loginUrl = loginUrl + "manage/eduyunlogin?key=";
loginUrl = URLEncoder.encode(loginUrl, "utf-8");
response.sendRedirect("http://www.eduyun.cn/index.php?m=user&c=member&a=login&backurl=" + loginUrl);
%>