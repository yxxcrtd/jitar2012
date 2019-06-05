<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <link rel='stylesheet' type='text/css' href='${SiteUrl}js/datepicker/calendar.css' />
  <script type='text/javascript' src='${SiteUrl}js/datepicker/calendar.js'></script>
</head>
<body>
<h2>频道用户统计</h2>
<div style="text-align: center; width: 100%;">
<form method="POST" action="?">
<input name="cmd" type="hidden" value="stat" />
<input type="hidden" name="guid" value="${guid}" />
<input type="hidden" name="channelId" value="${channel.channelId}" />
关键字：
<input type="text" size="16" name="k" value="${k!?html}" />
开始时间：<input name="startDate" value="${startDate!}" id="start_Date" readonly="readonly" />
结束时间：<input name="endDate" value="${endDate!}" id="end_Date" readonly="readonly" />
<#if !(f??)><#assign f = '0'></#if>
<select name="f">
<option value="" ${(f == '')?string('selected', '')}>检索过滤条件</option>
<option value="0" ${(f == '0')?string('selected', '')}>用户登录名</option>
<option value="1" ${(f == '1')?string('selected', '')}>用户真实姓名</option>
<option value="2" ${(f == '2')?string('selected', '')}>用户机构真实名称</option>
</select>
<input type="submit" class="button" value="检  索" onclick="this.form.cmd.value='stat'" />
<input type="button" class="button" value="导  出" onclick="this.form.cmd.value='export';this.form.submit();"/>
</form>
<table class="listTable" cellSpacing="1">
<thead>
<tr>
<th>用户登录名</th>
<th>用户真实姓名</th>
<th>机构名称</th>
<th>文章数</th>
<th>资源数</th>
<th>视频数</th>
<th>图片数</th>
</tr>
</thead>
<#if stat_list?? >
<tbody>
<#list stat_list as t>
<tr>
<td><a href="${SiteUrl}go.action?loginName=${t.loginName}" target="_blank">${t.loginName}</a></td>
<td><a href="${SiteUrl}go.action?profile=${t.loginName}" target="_blank">${t.userTrueName}</a></td>
<td>${t.unitTitle!}</td>
<td>${t.articleCount}</td>
<td>${t.resourceCount}</td>
<td>${t.videoCount}</td>
<td>${t.photoCount}</td>
</tr>
</#list>
</tbody>
<#else>
<tbody>
<td colspan="7" style="color:red;padding:10px">无数据</td>
</tbody>
</#if>
</table>
<div style="width: 100%; text-align: right; margin: 3px auto 3px;">
<#include "/WEB-INF/ftl/inc/pager.ftl">
</div>
</div>
<script>
calendar.set("start_Date");
calendar.set("end_Date");
</script>
</body>
</html>