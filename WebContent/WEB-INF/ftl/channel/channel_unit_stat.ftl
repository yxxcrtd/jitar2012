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
<h2>频道单位统计</h2>
<div style="text-align: center; width: 100%;">
<form method="POST" action="?">
<input name="cmd" type="hidden" value="stat" />
<input type="hidden" name="guid" value="${guid}" />
<input type="hidden" name="channelId" value="${channel.channelId}" />
开始时间：<input name="startDate" value="${startDate!}" id="start_Date" readonly="readonly"/>
结束时间：<input name="endDate" value="${endDate!}" id="end_Date" readonly="readonly" />
<input type="submit" class="button" value="检  索" onclick="this.form.cmd.value='stat'" />
<input type="button" class="button" value="导  出" onclick="this.form.cmd.value='export';this.form.submit();"/>
</form>
<table class="listTable" cellSpacing="1">
<thead>
<tr>
<th>机构名称</th>
<th>用户数</th>
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
<td>${t.unitTitle!}</td>
<td>${t.userCount}</td>
<td>${t.articleCount}</td>
<td>${t.resourceCount}</td>
<td>${t.videoCount}</td>
<td>${t.photoCount}</td>
</tr>
</#list>
</tbody>
<#else>
<tbody>
<td colspan="6" style="color:red;padding:10px">无数据</td>
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