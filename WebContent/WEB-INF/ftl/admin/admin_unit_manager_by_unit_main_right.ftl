<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
  <script type='text/javascript'>
  function doDeleteAction()
  {
   if(window.confirm("你真的要删除这些管理员吗？"))
   {
    document.getElementById("F1").submit();
   }
  }
  </script>
 </head>
 <body>
 <h2>${unit.unitTitle?html} 的机构管理员</h2>
<form method="POST" id="F1">
<#if user_list??>
<table class='listTable' cellspacing='1' id='listTable'>
<thead>
<tr>
<th style='width:17px'><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid");' id='chk' /></th>
<th>登录名</th>
<th>真实姓名</th>
<th>管理级别</th>
<th>管理员所在的机构</th>
</tr>
</thead>
<#list user_list as ul>
<#assign unit2=Util.unitById(ul.unitId)>
<tr>
<td><input type='checkbox' name='guid' value='${ul.accessControlId}' /></td>
<td>${ul.loginName}</td>
<td><a href='${SiteUrl}go.action?userId=${ul.userId}' target='_blank'>${ul.trueName!?html}</a></td>
<td>${ObjectType[ul.objectType]}</td>
<td><a href='${SiteUrl}go.action?unitName=${unit2.unitName}' target='_blank'>${unit2.unitTitle!?html}</a></td>
</tr>
</#list>
</table>
</#if>
</form>
 <div>
 <input type='button' value='删除管理员' onclick='doDeleteAction()' />
 <input type='button' value='添加管理员' onclick='window.location.href="admin_unit_manager_add.py?unitId=${unit.unitId}"' />
 </div>
</body>
</html>