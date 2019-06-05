<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>推送名师审核</title>
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <script type='text/javascript' src='${SiteUrl}js/jitar/core.js'></script>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <script type='text/javascript'>
 function doPost(st)
 {
   document.getElementById('oForm').cmd.value=st;
   document.getElementById('oForm').submit();
 }
  </script>
 </head>
<body>
<h2>推送名师审核</h2>
<#if content_list??>
<form action='admin_mashup_famous_list.py' style='text-align:right;padding-bottom:6px'>
关键字：<input name='k' value="${k!?html}" />
<select name='f'>
<option value=''>过滤条件</option>
<option value='title'<#if f?? && f == 'title'> selected='selected'</#if>>文章标题</option>
<option value='author'<#if f?? && f == 'author'> selected='selected'</#if>>文章作者</option>
<option value='unitName'<#if f?? && f == 'unitName'> selected='selected'</#if>>作者机构</option>
<option value='pushUserName'<#if f?? && f == 'pushUserName'> selected='selected'</#if>>文章推送人</option>
<option value='platformName'<#if f?? && f == 'platformName'> selected='selected'</#if>>区县平台名称</option>
</select>
<input type='submit' value='搜  索' />
</form>
<form id='oForm' method='POST' action='${SiteUrl}mashup/admin_mashup_famous_list.py'>
<input name='cmd' type='hidden' value='' />
<table class='listTable' cellspacing="1">
<thead>
<tr>
<th style='width:20px;text-align:left;'><input type='checkbox' id='chk' onclick='CommonUtil.SelectAll(this,"guid")' /></th>
<th>头像</th>
<th>姓名</th>
<th>学科</th>
<th>学段</th>
<th>机构</th>
<th>平台名称</th>
<th>推送时间</th>
<th>推送人</th>
<th>状态</th>
</tr>
</thead>
<#list content_list as user>
<tr>
<td><input type='checkbox' name='guid' value='${user.mashupBlogGroupId}' /></td>
<td><a href='${user.href}go.py?userId=${user.orginId}' target='_blank'><img src='${user.href}${user.icon}' border='0' width='48' /></a></td>
<td><a href='${user.href}go.py?userId=${user.orginId}' target='_blank'>${user.trueName}</a>
</td>
<td>${user.subjectName!?html}</td>
<td>${user.gradeName!?html}</td>
<td>${user.unitTitle!?html}</td>
<td>${user.platformName!?html}</td>
<td>${user.pushDate?string('yyyy-MM-dd HH:mm:ss')}</td>
<td>${user.pushUserName!?html}</td>
<td>
<#if user.mashupBlogGroupState == 0>
已审核
<#elseif user.mashupBlogGroupState == 1>
<span style='color:red'>待审核</span>
<#elseif user.mashupBlogGroupState == 2>
<span style='color:red'>待删除</span>
</#if>
</td>
</tr>
<#if user.description?? && user.description !=''>
<tr>
<td colspan='11'>简介：${user.description!}</td>
</tr>
</#if>
</#list>
</table>
<div style='text-align:center'>
  <#include '/WEB-INF/ftl/inc/pager.ftl' >
</div>
<input type='button' class='button' value='全部选中' onclick='document.getElementById("chk").click();CommonUtil.SelectAll(document.getElementById("chk"),"guid")' />
<input type='button' class='button' value=' 审  核 ' onclick='doPost("approve")' />
<input type='button' class='button' value='取消审核' onclick='doPost("unapprove")' />
<input type='button' class='button' value='彻底删除 ' onclick='doPost("delete")' />
<!--
<input type='button' value='设为待删除 ' onclick='doPost("todelete")' />
-->
</form>
<#else>
没有需要推送的文章。
</#if>
</body>
</html>