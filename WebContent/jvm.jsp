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
			out.println("Java虚拟机的最大可用内存：" + max + " MB<br/>");
			out.println("Java虚拟机占用的内存总数:" + total + " MB<br/>");
			out.println("Java虚拟机中的空闲内存：" + free + " MB<br/>");
			out.println("Java虚拟机中实际可用的内存：" + (max - total + free) + " MB<br/>");
		%>
	</body>
</html>
