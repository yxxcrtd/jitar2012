<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<%
String serverUserLoginUrl = "";
String clientCode = "";
String clientLoginUrl = "";
String reciverAction = "";
if(request.getServletContext().getFilterRegistration("ssoFilter") != null)
{
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
    
	serverUserLoginUrl = SSOServerURL1 + request.getServletContext().getFilterRegistration("ssoFilter").getInitParameter("serverUserLoginUrl");
    clientCode =  request.getServletContext().getInitParameter("clientCode");
    clientLoginUrl = request.getServletContext().getFilterRegistration("ssoFilter").getInitParameter("clientLoginUrl");
    reciverAction = request.getServletContext().getFilterRegistration("ssoFilter").getInitParameter("reciverAction");
}


    if(request.getServerPort() == 80)
    {
    	reciverAction = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + reciverAction;
    	clientLoginUrl = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + clientLoginUrl;
    }
    else
    {
    	reciverAction = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + reciverAction;
    	clientLoginUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + clientLoginUrl;
    }   
%>
<html>
<style>
html,body {
  width: 100%
}

input {
  width: 80%
}
</style>
<body>
<div style="font-size: 24px">
<h2>用户登录</h2>
    <form method="GET" action="<%=serverUserLoginUrl%>">
        用户名：<input name="username" style="width:200px" value="admin" /><br /> 
        密码：<input type="password"  style="width:200px" name="password" value="" /><br />
   <input type="hidden" name="clientCode" value="<%=clientCode%>" /><br /> 
   <input type="hidden" name="backurl" value="<%=reciverAction %>" /><br /> 
   <input type="hidden" name="clientLoginUrl" value="<%=clientLoginUrl %>" /><br />
   <input type="submit" value="登录测试" style="width: auto;">
   </form>
</div>

</body>
</html>
