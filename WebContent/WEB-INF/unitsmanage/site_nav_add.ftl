<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理</title>
 <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
</head>
<body>
<form method='post' id='oForm'>
<h2>机构网站导航定制</h2>
<#if siteNav??>
	<table class="listTable" cellSpacing="1">
	<tr>
	<td style='width:100px'>导航名称：</td><td><input name='siteName' value='${siteNav.siteNavName?html}' style='width:300px' /></td>
	</tr>
	<#if siteNav.isExternalLink>
	<tr>
	<td>导航地址：</td><td><input name='siteUrl' value='${siteNav.siteNavUrl?html}' style='width:300px' /></td>
	</tr>
	</#if>
	<tr>
	<td>是否显示：</td><td>
	<label>
	<input type='radio' name='siteShow' <#if siteNav.siteNavIsShow>checked='checked' </#if>value='1' />显示
	</label>
	<label>
	<input type='radio' name='siteShow' <#if !siteNav.siteNavIsShow>checked='checked' </#if>value='0' />隐藏
	</label>
	</td>
	</tr>
	<tr>
	<td>显示顺序：</td><td><input name='siteOrder' value='${siteNav.siteNavItemOrder}' /></td>
	</tr>
	</table>
<#else>
	<table class="listTable" cellSpacing="1">
	<tr>
	<td style='width:100px'>导航名称：</td><td><input name='siteName' value='' style='width:300px' /></td>
	</tr>
	<tr>
	<td>导航地址：</td><td><input name='siteUrl' value='' style='width:300px' /></td>
	</tr>
	<tr>
	<td>是否显示：</td><td>
	<label>
	<input type='radio' name='siteShow' checked='checked' value='1' />显示
	</label>
	<label>
	<input type='radio' name='siteShow' value='0' />隐藏
	</label>
	</td>
	</tr>	
	<tr>
	<td>显示顺序：</td><td><input name='siteOrder' value='0' /></td>
	</tr>
	</table>
</#if>
<div style='padding:4px 0'>
<input type='submit' value=' 保  存 ' class='button' />
</form>
</body>
</html>