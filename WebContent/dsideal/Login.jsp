<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="org.jasig.cas.client.authentication.AttributePrincipal"%>
<%@page import="java.net.URLEncoder" %>
<%
String dsssoserverLogin = "";
String dsssoserver = "";
if(request.getServletContext().getFilterRegistration("filterchainproxy") != null)
{
    if (request.getServletContext().getFilterRegistration("filterchainproxy").getClassName() == "dsidealsso.FilterChainProxy")
    {
        dsssoserverLogin = request.getServletContext().getFilterRegistration("filterchainproxy").getInitParameter("casServerLoginUrl");
        dsssoserver = request.getServletContext().getFilterRegistration("filterchainproxy").getInitParameter("casServerUrlPrefix");
    }
}
String siteserverurl="";
if (request.getServerPort() == 80)
{
    siteserverurl = "http://" + request.getServerName() + request.getContextPath();
}
else
{
    siteserverurl = "http://" + request.getServerName() + ":" + request.getServerPort() +request.getContextPath();
}
%>
<html>
  <head>
<title></title>
    <style type="text/css">
        body
        {
            background: transparent;
            padding: 0;
            margin: 0;
        }
        .psw
        {
            width: 80px;
            height: 15px;
            border: #c2c0c0 1px solid;
            margin-right: 5px;
            _margin-top: 3px;
        }
        .login, .reset
        {
            width: 64px;
            height: 20px;
            border: 0;
            color: #FFF;
            background: #a60606;
            line-height: 20px;
            text-align: center;
            font-size: 12px;
            margin-top: 4px;
        }
        .login
        {
            margin-right: 5px;
        }
        .lhj_dl111
        {
            font-size: 12px;
            margin-right: 0px;
            color: #fff;
        }
        
        .style1
        {
            color: #000000;
        }
    </style>
  </head>
  
	<body>
		
		
    <form id="formlogin" method="post" name="formlogin" action="<%=dsssoserver %>/logout?service=<%=siteserverurl %>/dsideal/UnLogin.jsp" style="margin: 0; padding: 0">
    <div class="lhj_dl111">
        <span class="style1">
            <% out.println(request.getSession().getAttribute("realname"));%>：您好！</span>&nbsp;
        <input class="login" value="注销" type="submit" name="textfield2" />
        <input value="<%=siteserverurl %>/dsideal/Login.jsp" type="hidden" name="targetService" />
        <input value="<%=siteserverurl %>/dsideal/UnLogin.jsp" type="hidden" name="loginFailURL" />
    </div>
    </form>
    
    

	</body>
</html>
