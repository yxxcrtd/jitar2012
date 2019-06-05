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
		dataEX_Token.setLoginTime(request.getParameter("logintime"));
		dataEX_Token.setUserName(request.getParameter("username"));
		dataEX_Token.setUserToken(share.login(request.getParameter("username"),request.getParameter("password"), request.getParameter("logintime")));
		queryRequest.setDataEXToken(dataEX_Token);
		
		//初始化查询的表名
		queryRequest.setDataEXQueryObject("T_BASE_USER");
		//初始化查询条件
		queryRequest.setDataEXQueryCondition("where ID='"+request.getParameter("userid")+"'");
		//调用查询操作
		DataEXQueryResult result = share.queryData(queryRequest,0);
		
		 %>
</head>
<body>

<%int x=result.getDataEXResultSet().getDataStructure().size(); 
for(int i=0;i<x;i++)
{
	String xx=result.getDataEXResultSet().getDataStructure().get(i);
	String yy=result.getDataEXResultSet().getDataResult().getDataRow().get(0).getData().get(i);

%>
<%=xx %>:<%=yy %><br>
<%
}
%>

</body>
</html>