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
    
    <title>My JSP 'clientLogin.jsp' starting page</title>
    <style type="text/css">
        .style1
        {
            font-family: 微软雅黑;
        }
    </style>
  </head>
  <script type="text/javascript">
	function login(){
	 var myDate = new Date();
	  var user = document.getElementById('username').value;
	  var password=document.getElementById('password').value;
	  document.getElementById('logintime').value=myDate.getFullYear()+"-"+myDate.getMonth()+"-"+myDate.getDate()+"-"+myDate.getHours()+":"+myDate.getMinutes()+":"+myDate.getSeconds();
	  var logintime=document.getElementById('logintime').value;
  	  document.getElementById('token').src = 'logintest.jsp?username='+user+'&password='+password+'&logintime='+logintime;
	}
	  function queryData(){
	  var user = document.getElementById('username').value;
	  var password=document.getElementById('password').value;
	  var logintime=document.getElementById('logintime').value;
	  	var userid= document.getElementById('userid').value;
 		document.getElementById('queryData').src = 'queryData.jsp?userid='+userid+'&username='+user+'&password='+password+'&logintime='+logintime;
	}
  </script>



  <body>
  <!-- 统一认证开始 -->
   <form id="form1" runat="server">
    <table cellspacing="0" cellpadding="0" width="950" align="center" border="0">
        <tbody>
            <tr>
                <td height="64">
                    <table cellspacing="0" cellpadding="0" width="950" align="center" border="0">
                        <tbody>
                            <tr>
                                <td height="64">
                                    <div style="position: relative; height: 64px">
                                        <img src="image/xiaoqing2_2.jpg" width="950" height="209" border="0" usemap="#Map"><div class="head_iframe">
                                             <iframe style="width: 600px; height: 30px;" allowtransparency="yes" frameborder="0"
                                              src="<%=dsssoserver %>/iframelogin?targetService=<%=siteserverurl %>/dsideal/Login.jsp&loginFailURL=<%=siteserverurl %>/dsideal/UnLogin.jsp" ></iframe>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
        </tbody>
    </table>
    </form>
  <!-- 统一认证结束 -->
    <br>
    <br>
    <br>
    
  <!-- 数据共享开始 -->
    <table cellspacing="0" cellpadding="0" width="950" align="center" border="0">
    <tbody>
            <tr>
                <td height="64">
                   <span class="style1" >Username&nbsp;&nbsp;</span>
  <input type="text" value="renshi" id="username"><br>
  <span class="style1">password&nbsp;&nbsp;</span>
 &nbsp; <input id="password" type="text" value="123456"><br>
  <span class="style1" >LoginTime&nbsp;&nbsp;</span>
  <input id="logintime" type="text" ><br>
  
   <button onclick="login()" >Login Test</button> 
   <iframe width="300" height="60" src="" id="token" ></iframe><br>  
   <span class="style1">USER_ID&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> 
   <input id="userid" type="text" style="width:300px" value="3907E9D9-FF98-4F96-BEAC-4DABDBD77F66"><br>
   <button onclick="queryData()" >QueryData Test</button> 
   <iframe width="500" height="60" src="" id="queryData" ></iframe><br> 
                </td>
            </tr>
        </tbody>
    </table>
  
  <!-- 数据共享结束 -->
   
   </body>
</html>
