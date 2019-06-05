<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/login/index.jsp";
String	username= request.getParameter("username");
String	password= request.getParameter("password");
String userurl= request.getParameter("userurl");


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body onload="form1.submit();">
   <form name="form1" method="post" action="<%=userurl %>" target="_top">
	<input name='username' id="login_username" type='hidden' value="<%=username %>" />
	<input name='password' type='hidden' value="<%=password %>"/>
	<input name='vercode' type='hidden' value="v" />
	<input name='redUrl' type='hidden' value="<%=basePath %>"/>
	
   </form>
  </body>
</html>
