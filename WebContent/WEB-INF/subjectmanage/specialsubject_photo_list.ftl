<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script>
  function doPost(arg)
  {
  	document.getElementById('oForm').cmd.value=arg;
  	document.getElementById('oForm').submit();
  }
  </script>
  <script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
  <script src='${SiteUrl}js/subject/util.js' type='text/javascript'></script>
</head>
<body>
<h2>${specialSubject.title?html} 专题相册管理</h2>
<form method='POST' id='oForm'>
<input name='cmd' type='hidden' value='' />
<table class='listTable' cellspacing='1' id='listTable'>
<thead>
<tr>
<th><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid");SetRowColorByName();' id='chk' /></th>
<th style='width:100%'>图片标题</th>
<th><nobr>创建人</nobr></th>
<th><nobr>创建时间</nobr></th>
</tr>
</thead>
<#if photo_list??>
<#list photo_list as photo >
<#assign user = Util.userById(photo.userId)>
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${photo.photoId}' onclick='SetRowColor(event)' /></td>
<td>
<a href='${SiteUrl}${photo.href}' target='_blank'>${photo.title}</a></td>
<td><nobr><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a></nobr></td>
<td><nobr>${photo.createDate?string('yyyy-MM-dd')}</nobr></td>
</tr>
</#list>
</#if>
</table>
<#if pager??>
<#include "/WEB-INF/ftl/pager.ftl">
</#if>
<div style='padding:6px'>
  <input class='button' type='button' value='全部选中' onclick='${"chk"}.click();SetRowColorByName();' />
  <input class='button' type='button' value='从专题中删除' onclick="doPost('remove')" />
</div>
</form>
</body>
</html>