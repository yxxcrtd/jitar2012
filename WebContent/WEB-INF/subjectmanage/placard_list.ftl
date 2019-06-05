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
  	window.location.href = 'placard_add.py?id=${subject.subjectId}'
  }
  </script>
  <script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
</head>
<body>
<h2>
学科公告管理
</h2>
<form method='post' style='padding-left:20px'>
<input name='cmd' type='hidden' value='' />
<#if subject_placard_list??>
<table class='listTable' cellspacing='1'>
<thead>
<tr>
<th><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid")' id='chk' /></th>
<th style='width:100%'>公告标题</th>
<th><nobr>发布人</nobr></th>
<th><nobr>发布时间</nobr></th>
<th><nobr>状态</nobr></th>
<th><nobr>操作</nobr></th>
</tr>
</thead>
<#list subject_placard_list as news >
<#assign user = Util.userById(news.userId)>
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${news.id}' /></td>
<td>
<#if SubjectUrlPattern??>
<a href='${SubjectUrlPattern.replace('{subjectCode}',subject.subjectCode)}py/showSubjectPlacard.py?id=${subject.subjectId}&placardId=${news.id}' target='_blank'>${news.title}</a>
<#else>
<a href='${SiteUrl}k/${subject.subjectCode}/py/showSubjectPlacard.py?id=${subject.subjectId}&placardId=${news.id}' target='_blank'>${news.title}</a>
</#if>
</td>
<td><nobr><#if user?? && user !=""><a href='${SiteUrl}go.action?loginName=${user.loginName!}' target='_blank'>${user.trueName!}</a></#if></nobr></td>
<td><nobr>${news.createDate?string('yyyy-MM-dd')}</nobr></td>
<td><nobr>
<#if news.hide?? && !news.hide >
显示
<#else>
<span style='color:#f00'>隐藏</span>
</#if>
</nobr>
</td>
<td><nobr><a href='placard_add.py?id=${subject.subjectId}&placardId=${news.id}'>修改</a></nobr>
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
  <input class='button' type='button' value='发布公告' onclick="addNew()" />
  <input class='button' type='button' value='设为显示' onclick="doPost('approve')" />
  <input class='button' type='button' value='设为隐藏' onclick="doPost('unapprove')" />  
  <input class='button' type='button' value='删除选择' onclick="if(confirm('你真的要删除吗？')){doPost('delete')}" />
</div>
</form>
</body>
</html>