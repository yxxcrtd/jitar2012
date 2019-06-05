<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript" src='${SiteUrl}js/jitar/core.js'></script>
</head>
<h2>调查投票管理</h2>
<#if vote_list?? >
<form method='post'>
<table class='listTable' cellspacing='1'>
<thead>
<tr>
<th width="17px"><input type='checkbox' id='cxx' onclick="CommonUtil.SelectAll(this,'q_guid')" /></th>
<th width="100%">调查投票名称</th>
<th><nobr>创建人</nobr></th>
<th><nobr>创建时间</nobr></th>
<th><nobr>截止时间</nobr></th>
<th><nobr>操作</nobr></th>
</tr>
</thead>
<tbody>
<#list vote_list as vote >
<tr>
<td><input type='checkbox' name='q_guid' value='${vote.voteId}' /></td>
<td><a target='_blank' href='${SiteUrl}mod/vote/showresult.action?guid=${vote.parentGuid}&type=${vote.parentObjectType}&voteId=${vote.voteId}'>${vote.title}</a></td>
<td><nobr><a target='_blank href='${SiteUrl}go.actopn?userId=${vote.createUserId}'>${vote.createUserName}</a></nobr></td>
<td><nobr>${vote.createDate?string('yyyy/MM/dd')}</nobr></td>
<td><nobr>${vote.endDate?string('yyyy/MM/dd')}</nobr></td>
<td><nobr>
<a href='${SiteUrl}mod/vote/modify_vote.action?guid=${vote.parentGuid}&type=${vote.parentObjectType}&voteId=${vote.voteId}'>修改</a> | 
<a href='${SiteUrl}mod/vote/modi_order.py?guid=${vote.parentGuid}&type=${vote.parentObjectType}&voteId=${vote.voteId}'>调顺序</a>
</nobr></td>
</tr>
</#list>
</tbody>
</table>
<div style='padding:4px'>
<input class='button' type='button' value='全部选中' onclick='document.getElementById("cxx").click();CommonUtil.SelectAll(document.getElementById("cxx"),"q_guid")' />
<input class='button' type='submit' value='删除选中' />
</div>
<#include '../pager.ftl'>
</form>
</#if>
</body>
</html>