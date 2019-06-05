<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>系统管理员管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <script type="text/javascript" src="../js/jitar/core.js"></script>
  <script type='text/javascript'>
  function doDeleteAction()
  {
    if(window.confirm("你真的要删除这些管理员吗？"))
    {
      document.getElementById("F2").submit();
    }
  }
  
  function doFilter(oSelect)
  {
    var url = "admin_unit_manager.py";
    url += "?objectType=" + oSelect.options[oSelect.selectedIndex].value;
    window.location.href=url;
  }
  </script>
</head>
<body>
<h2>全部系统管理员</h2>
<#if !f??><#assign f = 'trueName'></#if>
<form id="F1" action="?" method="get">
<div class='search'>
  关键字：<input type='text' name="k" value='${k!?html}' style="width: 100px;" onfocus="this.select();" onmouseover="this.focus();" />
  <select name='f'>
  <option value='trueName'<#if f=='trueName'> selected='selected'</#if>>真实姓名</option>
  <option value='loginName'<#if f=='loginName'> selected='selected'</#if>>登录名</option>
  </select>
<input type="submit" class="button" value=" 检  索 " />
</div>
</form>
<form id="F2" method="post">
<#if admin_list??>
<table class='listTable' cellspacing='1' id='listTable'>
<thead>
<tr style='text-align:left'>
<th style='width:17px'><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid");' id='chk' /></th>
<th style='width:50px'>头像</th>
<th>登录名</th>
<th>真实姓名</th>
<th>管理员所在的机构名称</th>
</tr>
</thead>
<#list admin_list as ul>
<#assign unit2=Util.unitById(ul.unitId)>
<tr>
<td>
<#if loginUser.userId != ul.accessControlId >
<input type='checkbox' name='guid' value='${ul.accessControlId}' />
</#if>
</td>
<td><img src="${SSOServerUrl +'upload/'+ ul.userIcon!''}" onerror="this.src='${ContextPath}images/default.gif'" width="50" /></td>
<td>${ul.loginName}</td>
<td><a href='${SiteUrl}go.action?userId=${ul.userId}' target='_blank'>${ul.trueName!?html}</a></td>
<td><a href='${SiteUrl}go.action?unitName=${unit2.unitName}' target='_blank'>${unit2.unitTitle!?html}</a></td>
</tr>
</#list>
</table>
<div style='padding:6px 0'>
<input type='button' onclick='doDeleteAction()' value='删除管理员' />
<input type='button' onclick='window.location.href="admin_systemadmin_manager_add.py"' value='添加管理员' />
<span style="color:red"> 请至少保留一个系统管理员，否则，将没法登录系统后台进行管理了。默认不能删除自己。</span>
</div>
<div class="pager">
<#if pager??><#include "/WEB-INF/ftl/inc/pager.ftl"></#if>
</#if>
</div>
</form>
</body>
</html>