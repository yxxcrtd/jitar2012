<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>列表</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
</head>
<body>
<h2>评课活动统计</h2>

<table class='listTable' cellspacing='1'>
  <thead>
    <tr style="text-align:left">
      <th width="10%">学年</th>
      <th width="10%">学期</th>
      <th width="10%">轮次</th>
      <th width="10%">查看统计</th>
    </tr>
  </thead>
  <tbody>
  <#if !(evaluation_list??) || evaluation_list?size == 0>
    <tr>
    <td colspan="5">暂时没有评课活动</td>
    </tr>
   <#else>
   <#list evaluation_list as ev>
   <tr>
    <td>${ev.evaluationYear}</td>
    <td>${(ev.evaluationSemester==0)?string("上学期","下学期")}</td>
    <td>${ev.evaluationTimes}</td>
    <td><a href="evaluation_stats_show.py?evaluationPlanId=${ev.evaluationPlanId}">查看统计</a></td>
    </tr>
    </#list>
  </#if>
  </table>
<div class='pager'>
  <#include '/WEB-INF/ftl/inc/pager.ftl' >
</div>
</body>
</html>