<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>日历提醒管理</title>
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
<script type='text/javascript' src='${SiteUrl}js/jitar/core.js'></script>
</head>
<body>
<h2>日历提醒管理</h2>
<div class='pos'>
您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>&gt;&gt;我的日历提醒管理
</div>
<#if calendars?? >
<form method="post">
<table id="ListTable" class="listTable">
<thead>
<tr>
<th width="17px"><input type='checkbox' id='cxx' onclick="CommonUtil.SelectAll(this,'id')" /></th>
<th width="100%">提醒标题</th>
<th><nobr>提醒开始时间</nobr></th>
<th><nobr>提醒结束时间</nobr></th>
</tr>
</thead>
<tbody>
<#list calendars as calendar>
<tr>
<td><input type='checkbox' name='id' value='${calendar.id}' /></td>
<td><a target='_blank' href='${calendar.url}'>${calendar.title}</a></td>
<td><nobr>${calendar.eventTimeBegin?string('yyyy/MM/dd')}</nobr></td>
<td><nobr>${calendar.eventTimeEnd?string('yyyy/MM/dd')}</nobr></td>
</tr>
</#list>
</tbody>
</table>
<div style='padding:4px'>
<input class='button' type='button' value='全部选中' onclick='document.getElementById("cxx").click();CommonUtil.SelectAll(document.getElementById("cxx"),"id")' />
<input class='button' type='submit' value='删除选中' />
</div>
<#include ('../pager.ftl')>
</form>
</#if>
</body>
</html>