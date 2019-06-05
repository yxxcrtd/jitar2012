<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="net.zdsoft.passport.service.client.PassportClient" %>
<%@page import="cn.edustar.jitar.model.UserMgrModel"%>
<%
int serverPort = request.getServerPort();
// 系统登录后的首页
String redUrl = request.getScheme() + "://" + request.getServerName()
        + (serverPort == 80 ? "" : ":" + serverPort)
        + request.getContextPath() + "/index.py";
        
String userMgrUrl = UserMgrModel.getUserMgrUrl();
//response.getWriter().println("<li>"+userMgrUrl);        
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN" xml:lang="zh-CN">
<head>
<title>登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="/css/common/common.css" />
<script type="text/javascript" language="javascript" src="script/md5.js"></script>
<script type="text/javascript" language="javascript" src="script/sha1.js"></script>
<script type="text/javascript" language="javascript">
<!--
function fullEscape(text) { 
  var encodedHtml = encodeURIComponent(text);
  return encodedHtml;
}

// 显示登录的loading条
function showLoading() {
  var loadingPanel = document.getElementById("loadingPanel");
  if (loadingPanel == null) {
    var el = document.createElement("div");
    el.setAttribute("id", "loadingPanel");
    el.style.cssText = "font-family:Verdana;font-size:12px;border:1px solid #00CC00;background-color:#A4FFA4;padding:2px;position:absolute;right:2px;top:1px;height:16px;z-index:10000";
    el.innerHTML = "正在加载...";
    document.body.appendChild(el);
    loadingPanel = el;
  }
  else {
    loadingPanel.style.display = "block";
  }
}

// 显示登录出错提示的回调方法
function processError(e) {
  var errorDiv = document.getElementById("error");
  if (errorDiv) {
    errorDiv.style.display = "block";
    errorDiv.innerHTML = "<ul><li>" + e + "</li></ul>";
  }
  document.getElementById("loadingPanel").style.display = "none";
  document.getElementById("submitBtn").disabled = false;
}
 function login()
  {
  	  var username = fullEscape(document.getElementById("username").value);
  	  if(username=="admin")
  	  {
  	  	login2();
  	  }
  	  else
  	  {
  	  	login1();
  	  }
  }
  function login2()
  {
  	//alert("admin用户请从首页登录框登录！");
  	//self.document.location.href='/index.py';
		  var username = fullEscape(document.getElementById("username").value);
		  var password = document.getElementById("password").value;
		  var scriptLoginURL = "adminlogin.jsp";
		  scriptLoginURL += "?username=" + username;
		  scriptLoginURL += "&password="+ password;
		  scriptLoginURL += "&userurl=<%=userMgrUrl%>login.action";
		  scriptLoginURL += "&v=" + new Date().getTime();
		  window.open(scriptLoginURL,"_top");  	
  }
function login1() {
  // 控制重复提交
  document.getElementById("submitBtn").disabled = true;
  // 显示loading条
  showLoading();

  var username = fullEscape(document.getElementById("username").value);
  var password = document.getElementById("password").value;
  var scriptLoginURL = "<%=PassportClient.getInstance().getPassportURL()%>/scriptLogin";
  scriptLoginURL += "?action=login";
  scriptLoginURL += "&username=" + username;
  scriptLoginURL += "&server=<%=PassportClient.getInstance().getServerId()%>";
  scriptLoginURL += "&root=<%="".equals(request.getContextPath()) ? "1" : "0"%>";
  if (document.getElementById("password").value != "") {
    scriptLoginURL += "&password=" + hex_md5(password) + hex_sha1(password);
  }
  scriptLoginURL += "&verifyCode=" + document.getElementById("verifyCode").value;
  scriptLoginURL += "&url=<%=redUrl%>";
  scriptLoginURL += "&v=" + new Date().getTime();

  // 动态创建登录脚本元素, 这样做的目的是因为firefox不支持多次载入某个已经存在的script元素.
  // 如果已经存在登录脚本元素, 则先删除元素
  var scriptLogin = document.getElementById("scriptLogin");
  if (scriptLogin) {
    scriptLogin.parentNode.removeChild(scriptLogin);
  }

  // 创建登录脚本元素
  scriptLogin = document.createElement("script");
  scriptLogin.id = "scriptLogin";
  scriptLogin.type = "text/javascript";
  scriptLogin.src = scriptLoginURL;
  document.body.appendChild(scriptLogin);
}

	function refresh() {
			document.getElementById("verifyImage").src = '<%=PassportClient.getInstance().getPassportURL()%>/verifyImage?v=' + new Date();
		}

//-->
</script>
</head>
<body>
<table border=1 width="100%" height="100%">
  <tr>
  	<td align="center">
	  <div id="error" style="display:none;text-align:center; "></div>
	  <div style='text-align:center;'>用户名：<input type="text" id="username" name="username" style="width:150px" /></div>
	  <div style='text-align:center;'>密　码：<input type="password" id="password" name="password" style="width:150px" /></div>
	  <div style='text-align:center;' id="verifyField" class="text" style="display:block;">验证码：<input id="verifyCode" type="text" size="10" />&nbsp;&nbsp;<a href="#" onClick="refresh()"><img id="verifyImage" border="0" align="middle" alt="验证码" src="<%=PassportClient.getInstance().getPassportURL()%>/verifyImage?v=<%=System.currentTimeMillis()%>"/></a></div>
	  <br/>
	  <div style='text-align:center;'><input type="button" id="submitBtn" name="submitBtn" value=" 登录 " onclick="login()" />
	  <input type="reset" id="resetBtn" name="resetBtn" value=" 重设" />
	  </div>
  	</td>
  </tr>
</table>  	
<script language="javascript" type="text/javascript" src="<%=PassportClient.getInstance().getPassportURL()%>/scriptLogin?action=init&server=<%=PassportClient.getInstance().getServerId()%>&type=3&v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
