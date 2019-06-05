<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script type='text/javascript'>
  function addLink()
  {
  	window.location.href='add_links.py?unitId=${unit.unitId}';
  }
  </script>
</head>
<body>
<h2>
友情链接管理
</h2>
<form method='post' style='padding-left:20px'>
<#if links??>
<table class='listTable' cellspacing='1'>
<#list links as link>
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${link.linkId}' /></td>
<td>${link.linkName}</td>
<td><a href='${link.linkAddress}' target='_blank'>${link.linkAddress}</a></td>
<td><a href='add_links.py?unitId=${unit.unitId}&amp;linkId=${link.linkId}'>修改</a></td>
</tr>
</#list>
</table>
</#if>
<div style='padding:6px'>
	<input class='button' type='button' value='添加链接' onclick='addLink()' />
	<input class='button' type='submit' value='删除链接' onclick='return confirm("您真的要删除吗？")' />
</div>
</form>
</body>
</html>
