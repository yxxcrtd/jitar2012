<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<%
String unitId = request.getParameter("unitId");
if(unitId == null){
    unitId = "";
}
%>
<html>
<style>
html,body,div {
  width: 100%;color:#f00;font-size:24px;
}
</style>
<body>

<div>
你要进入教研平台，你所在的机构必须在教研平台里面进行注册，否则，不能使用教研平台。<br/>
<br/>
机构 <%=unitId %> 在教研系统中不存在，如果是新安装的本系统，请<a href="<%=request.getContextPath()%>/addRootUnit.action">执行初始化根机构</a>，或者通知管理员添加一个机构。<br/><br/>
第一个注册的用户将默认作为系统管理员。
</div>
</body>
</html>
