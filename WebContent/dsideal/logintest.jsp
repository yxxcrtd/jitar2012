<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="java.sql.*"%>
    <%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.cxf.jaxws.JaxWsProxyFactoryBean"%>


<%@page import="com.dsideal.ws.shareclient.impl.DataEXResultSet"%>
<%@page import="com.dsideal.ws.shareclient.impl.DataEXToken"%>

<%@page import="com.dsideal.ws.shareclient.impl.WSDataEXShare"%>
<%@page import="com.dsideal.ws.shareclient.impl.DataEXQueryRequest"%>
<%@page import="com.dsideal.ws.shareclient.impl.DataEXQueryResult"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
//新建一个查询请求
		DataEXQueryRequest queryRequest = new DataEXQueryRequest();
		System.out.print("11111");
		//创建web服务代理工厂
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		//设置要调用的web服务服务端发布地址
		factory.setAddress("http://10.10.8.29:8840/DataEX_share/webservice/dataEX_share");
		//设置要调用的web服务
		factory.setServiceClass(WSDataEXShare.class); 
		//生成web服务接口对象
		WSDataEXShare share = (WSDataEXShare)factory.create();
		//初始化登陆信息
		DataEXToken dataEX_Token = new DataEXToken();
		
		 %>
</head>
<body>

<%=share.login(request.getParameter("username") , request.getParameter("password"), request.getParameter("logintime"))%>
</body>
</html>