<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>讨论管理</title>
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
<script type='text/javascript' src='${SiteUrl}js/jitar/core.js'></script>
</head>
<body>
<h2>讨论管理</h2>
<h4 style='background:#eee;padding:6px 2px;color:#00f'><a href='admin_list.py' style='color:red'>讨论</a> <a href='admin_list2.py'>讨论回复</a></h4>
<#if topic_list?? >
<form method="post">
<table id="ListTable" class="listTable">
<thead>
<tr>
<th width="17px"><input type='checkbox' id='cxx' onclick="CommonUtil.SelectAll(this,'guid')" /></th>
<th width="50%">讨论标题</th>
<th><nobr>创建者</nobr></th>
<th><nobr>类型</nobr></th>
<th><nobr>创建时间</nobr></th>
</tr>
</thead>
<tbody>
<#list topic_list as t >
<tr>
<td><input type='checkbox' name='guid' value='${t.plugInTopicId}' /></td>
<td><a target='_blank' href='${SiteUrl}mod/topic/show_topic.action?guid=${t.parentGuid}&type=${t.parentObjectType}&topicId=${t.plugInTopicId}'>${t.title}</a></td>
<td><nobr><a href='${SiteUrl}go.action?userId=${t.createUserId}'>${t.createUserName}</a></nobr></td>
<td><nobr>
<#switch t.parentObjectType>
  <#case "user">
	个人
     <#break>
  <#case "group">
     协作组
     <#break>
  <#case "netcourse">
     网络课程
     <#break>
  <#case "preparecourse">
     集体备课
     <#break>
  <#case "specialsubject">
     教研专题
     <#break>
  <#case "subject">
     学科
     <#break>
  <#default>
     未注册类型
</#switch></nobr>
</td>
<td><nobr>${t.createDate?string('yyyy/MM/dd')}</nobr></td>
</tr>
</#list>
</tbody>
</table>
<div style='padding:4px'>
<input class='button' type='button' value='全部选中' onclick='document.getElementById("cxx").click();CommonUtil.SelectAll(document.getElementById("cxx"),"guid")' />
<input class='button' type='submit' value='删除选中' />
</div>
<#include '../pager.ftl'>
</form>
</#if>
</body>
</html>