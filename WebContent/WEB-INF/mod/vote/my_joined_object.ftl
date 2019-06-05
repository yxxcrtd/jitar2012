<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>调查投票管理</title>
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
<script type='text/javascript' src='${SiteUrl}js/jitar/core.js'></script>
</head>
<body>
<h2>调查投票管理</h2>
<div class='tab_outer'>
  <div id="article_" class='tab2'>
    <div><a href='my_created_object.py'>我发起的投票</a></div> 
    <div class="cur"><a href='my_joined_object.py'>我参与的投票</a></div> 
  </div>
  <div style="padding:10px">
  
<#if vote_list?? >
<form method="post">
<table id="ListTable" class="listTable">
<thead>
<tr>
<th width="100%">调查投票名称</th>
<th><nobr>类型</nobr></th>
<th><nobr>创建时间</nobr></th>
<th><nobr>截止时间</nobr></th>
</tr>
</thead>
<tbody>
<#list vote_list as vote >
<tr>
<td><a target='_blank' href='${SiteUrl}mod/vote/showresult.action?guid=${vote.parentGuid}&type=${vote.parentObjectType}&voteId=${vote.voteId}'>${vote.title}</a></td>
<td><nobr>
<#switch vote.parentObjectType>
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
  <#case "unit">
     机构
   <#break>
  <#default>
     未注册类型
</#switch></nobr>
</td>
<td><nobr>${vote.createDate?string('yyyy/MM/dd')}</nobr></td>
<td><nobr>${vote.endDate?string('yyyy/MM/dd')}</nobr></td>
</tr>
</#list>
</tbody>
</table>
<#include '../pager.ftl'>
</form>
</#if>
</div>
</body>
</html>