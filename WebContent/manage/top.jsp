<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.edustar.jitar.pojos.User"%>
<%@ page import="cn.edustar.jitar.action.JspPageHelper"%>
<%
  JspPageHelper helper = new JspPageHelper(pageContext);
  User user = helper.getLoginUser();
  if(user != null)
  {
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>用户管理后台</title>
		<link rel="stylesheet" href="../css/manage.css" type="text/css" />
		<link rel="stylesheet" href="../css/manage/left.css" type="text/css" />
		<style type="text/css">
		html,body {
			overflow: hidden;
		}
		.hr {
		  border-bottom:1px solid #E5E5E5;
  	}
		</style>
	</head>
	
	<body style="padding: 4px; padding-bottom: 2px">
		<div style="display:none; float: left; font-weight: bold; line-height: 20px">
			当前位置：
			<a href="right.jsp" target="mainframe">管理首页</a> &gt;&gt;
			<span id="topnav"></span>
		</div>
		<div style="float: left;">&nbsp; 
		  <b>欢迎您：<%=user.getTrueName() %>！</b>
		</div>
		<div style="float: right">		
      <a href="admin.py" target='_top'>系统管理首页</a>    
      <a href="../manage/" target="_top">工作室管理</a>
      <a href='message.py?cmd=inbox' target='mainframe'>消息</a>
			<a href='../go.action?loginName=<%=user.getLoginName()%>' target='_blank'>我的首页</a>
      <a href="../" target="_blank">网站首页</a>			 
			<a href='../logout2.jsp?ru=<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>' target='_top'>退出系统</a>
      <a href='../help.action' target='mainframe'>帮助</a> 
		</div>
		<div style='clear:both;' class='hr'></div>
	</body>
</html>
<%} 
  else
  {
	  String loginurl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/login2.jsp";
	  %>
	  <a href='<%=loginurl%>' target="_top">login</a>
	  <%
  }
%>