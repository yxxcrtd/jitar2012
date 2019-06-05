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
  
  function addNew()
  {
  	window.location.href = 'links_add.py?id=${subject.subjectId}'
  }
  </script>
  <script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
</head>
<body>
<h2>
学科站点友情链接管理
</h2>
<form method='post' style='padding-left:20px'>
<input name='cmd' type='hidden' value='' />
<#if links??>
<table class='listTable' cellspacing='1'>
<thead>
<tr>
<th><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid")' id='chk' /></th>
<th style='width:100%'>链接名称</th>
<th><nobr>操作</nobr></th>
</tr>
</thead>
<#list links as link >
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${link.linkId}' /></td>
<td><a href='${link.linkHref}' target='_blank'>${link.linkTitle}</a></nobr></td>
<td><nobr><a href='links_add.py?id=${subject.subjectId}&linkId=${link.linkId}'>修改</a></nobr>
</td>
</tr>
</#list>
</table>
</#if>
<#if pager??>
<#include "/WEB-INF/ftl/pager.ftl">
</#if>
<div style='padding:6px'>
  <input class='button' type='button' value='全部选中' onclick='${"chk"}.click();' />
  <input class='button' type='button' value='添加链接' onclick="addNew()" />
  <input class='button' type='button' value='删除选择' onclick="if(confirm('你真的要删除吗？')){doPost('delete')}" />
</div>
</form>
</body>
</html>