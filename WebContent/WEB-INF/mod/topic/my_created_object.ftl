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

<div class='tab_outer'>
  <div id="article_" class='tab2'>
    <div class="cur"><a href='my_created_object.py'>我发起的讨论</a></div> 
    <div><a href='my_joined_object.py'>我参与的讨论</a></div> 
  </div>
  <div style="padding:10px">

<#if topic_list?? >
<form method="post" action='my_created_object.py'>
<table id="ListTable" class="listTable">
<thead>
<tr>
<th width="17px"><input type='checkbox' id='cxx' onclick="CommonUtil.SelectAll(this,'guid')" /></th>
<th width="100%">讨论标题</th>
<th><nobr>类型</nobr></th>
<th><nobr>创建时间</nobr></th>
</tr>
</thead>
<tbody>
<#list topic_list as t >
<tr>
<td><input type='checkbox' name='guid' value='${t.plugInTopicId}' /></td>
<td><a target='_blank' href='${SiteUrl}mod/topic/show_topic.action?guid=${t.parentGuid}&type=${t.parentObjectType}&topicId=${t.plugInTopicId}'>${t.title}</a></td>
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
<#include ('../pager.ftl')>
</form>
</#if>
</div>
</body>
</html>