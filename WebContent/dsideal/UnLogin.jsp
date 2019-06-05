<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
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
   <%
	String errorCode = "";
	String prompt = "";
	if(request.getParameter("errorCode")!=null){
		errorCode = (String)request.getParameter("errorCode");
		if("1".equals(errorCode)){
			prompt = "用户名不存在!";
		}else if("2".equals(errorCode)){
			prompt = "用户名和密码不匹配!";
		}else if("3".equals(errorCode)){
			prompt = "用户无权限访问该系统!";
		}else if("4".equals(errorCode)){
			prompt = "票据已经失效,请重新登录!";
		}else{
			prompt = "服务器端票据错误!";
		}
	%>
	<font color="red"></font>
	<%
	}
	 %>
   
    <form id="formlogin" method="post" name="formlogin" action="<%=dsssoserver %>/remotelogin">
    <div class="lhj_dl111">
        <span class="style1">用户名：</span>
        <input class="psw" name="username" /><span class="style1"> 密码：</span>
        <input class="psw" type="password" name="password" />
        <input class="login" value="登录" type="submit" name="textfield2" />
        <input value="<%=siteserverurl %>/dsideal/Login.jsp" type="hidden" name="targetService" />
        <input value="<%=siteserverurl %>/dsideal/UnLogin.jsp" type="hidden" name="loginFailURL" />
        <font color="black">
            <%=prompt %></font></div>
    </form>
  </body>
</html>
