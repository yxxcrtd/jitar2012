<%@page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
		<title>JVM</title>
	</head>

	<body>
		<%
			double total = (Runtime.getRuntime().totalMemory()) / (1024.0 * 1024);
			double max = (Runtime.getRuntime().maxMemory()) / (1024.0 * 1024);
			double free = (Runtime.getRuntime().freeMemory()) / (1024.0 * 1024);
			out.println("Java��������������ڴ棺" + max + " MB<br/>");
			out.println("Java�����ռ�õ��ڴ�����:" + total + " MB<br/>");
			out.println("Java������еĿ����ڴ棺" + free + " MB<br/>");
			out.println("Java�������ʵ�ʿ��õ��ڴ棺" + (max - total + free) + " MB<br/>");
		%>
	</body>
</html>
