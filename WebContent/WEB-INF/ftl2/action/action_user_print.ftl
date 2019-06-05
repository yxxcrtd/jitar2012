<html xmlns:v="urn:schemas-microsoft-com:vml"
xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB2312">
<meta name="ProgId" content="Excel.Sheet">
<style>
.X1{mso-number-format:"\@";}
</style>
</head>
<body>
<#if action_user_list?? >
<table border="1">
<tr>
<th>序号</th>
<th>登录名</th>
<th>真实姓名</th>
<th>加入时间</th>
<th>状态</th>
<th>所在机构</th>
<th>用户留言</th>
</tr>
<#list action_user_list as user>
 <tr style='background:#FFF'>
 <td>${user_index + 1}</td>
 <td>${user.loginName}</td>
 <td>${user.trueName!}</td>
 <td class="X1">${user.actionUserCreateDate!?string('yyyy-MM-dd HH:mm')}</td>
 <td>
  <#if user.actionUserStatus == 0 >
  未回复
  <#elseif user.actionUserStatus ==1 >
  已参加
  <#elseif user.actionUserStatus ==2 >
  已退出
  <#elseif user.actionUserStatus ==3 >
  已请假
  <#else>
  未定义
  </#if>
 </td>
 <td>${user.unitTitle!}</td>
 <td>${user.actionUserDescription!?html}</td>
</tr>
</#list>
</table>
</#if>
</body>
</html>