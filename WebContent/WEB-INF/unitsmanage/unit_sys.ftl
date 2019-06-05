<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script>
  function doPost(arg)
  {
  	document.forms[0].cmd.value=arg;
  	document.forms[0].submit();
  }
  </script>
</head>
<body>
<h2>
系统模块管理
</h2>
<form method='post' style='padding-left:20px'>
<input name='cmd' type='hidden' value='' />
<#if webpart_list??>
<table class='listTable' cellspacing='1'>
<thead>
<tr>
<th></th>
<th>模块名称</th>
<th>显示名称</th>
<th>可见性</th>
</tr>
</thead>
<#list webpart_list as webpart >
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${webpart.unitWebpartId}' /></td>
<td>${webpart.moduleName}</td>
<td>
<input type='hidden' name='unitWebpartId' value="${webpart.unitWebpartId}" />
<input name='displayName${webpart.unitWebpartId}' value="${webpart.displayName!?html}" />
</td>
<td><#if webpart.visible >显示<#else>隐藏</#if></td> 
</tr>
</#list>
</table>
</#if>
<div style='padding:6px'>
	<input class='button' type='button' value='设为显示' onclick="doPost('visible')" />
	<input class='button' type='button' value='设为隐藏' onclick="doPost('hidden')" />
	<input class='button' type='button' value='修改显示名称' onclick="doPost('setDisplayName')" />
</div>
</form>
</body>
</html>
