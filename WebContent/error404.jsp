<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// 如果传递的时候带返回地址，则直接取，这是最好的方法
	String url = request.getRequestURL().toString();
	url = url.substring(0, url.lastIndexOf("/")) + "/";
%>
<jsp:include page="head.jsp"></jsp:include>
<div class="secMain border">
	<div style="padding: 0 20px">
		<div style="padding-top: 10px; text-align: center; font-size: 24px; font-weight: bold; color: red">
			网站访问提示
		</div>
		<div
			style="padding: 0 20px; font-size: 16px; line-height: 130%; text-align: center; padding: 20px 0;">
			当您访问本网站的时候出现本页面，说明您访问的地址在服务器上是不存在的，请检查您的输入是否正确。
			<br />
			<br />
			另外注意：地址栏输入的字符是要区分大小写的。
			<br />
			<br />
			<a style="font-weight: bold;" href="<%=url%>">返回网站首页</a>
		</div>
	</div>
</div>
<div class='footer'>
<div>
  <div style='padding:20px;padding-left:160px;'>

  </div>
</div>
</body>
</html>
