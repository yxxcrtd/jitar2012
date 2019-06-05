<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理协作组</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}js/datepicker/calendar.css" />  
  <script src='${SiteUrl}js/datepicker/calendar.js' type="text/javascript"></script>
  <script src='${SiteUrl}js/jitar/core.js' type="text/javascript"></script>
  <script type="text/javascript">
  function SetForm(cmdvalue)
  {
    if(window.confirm('你真的要这么做吗？'))
    {
  	  document.getElementById('ff').cmd.value = cmdvalue;
  	  document.getElementById('ff').submit();
  	}
  }

  
  </script>  
</head>
<body>
<#include '/WEB-INF/ftl/group/group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='${SiteUrl}manage/group.py?cmd=home&amp;groupId=${group.groupId}'>协作组管理首页</a>
  &gt;&gt; <span>管理集备计划</span>
</div>
<#if plan_list??>
<form method='post' id='ff'>
<table class='listTable' cellspacing="1">
<thead>
<tr>
<td style='width:17px'><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid")' id='rdo' /></td>
<td>标题</td>
<td>开始日期</td>
<td>结束日期</td>
<td>创建日期</td>
<td>创建人</td>
<td>默认</td>
</tr>
</thead>
<#list plan_list as p>
<tr>
<td>
<#if !p.defaultPlan>
<input type='checkbox' name='guid' value='${p.prepareCoursePlanId}' />
</#if>
</td>
<td><a href='group_course_plan_edit.py?groupId=${group.groupId}&prepareCoursePlanId=${p.prepareCoursePlanId}'>${p.title?html}</a></td>
<td><nobr>${p.startDate?string('yyyy-MM-dd')}</nobr></td>
<td><nobr>${p.endDate?string('yyyy-MM-dd')}</nobr></td>
<td><nobr>${p.createDate?string('yyyy-MM-dd')}</nobr></td>
<td><nobr><a href='${SiteUrl}go.action?userId=${p.userId}' target='_blank'>${p.trueName?html}</a></nobr></td>
<td><input type='radio' name='defaultplan' value='${p.prepareCoursePlanId}' ${p.defaultPlan?string('checked="checked"','')} onclick='SetForm("defaultvalue")'/></td>
</tr>
</#list>
</table>
<div style='xxxxtext-align:right;'>
<input type='button' value='全部选择' class='button' onclick='document.getElementById("rdo").click();CommonUtil.SelectAll(document.getElementById("rdo"),"guid")' />
<input type='button' value=' 删  除 ' class='button' onclick='SetForm("delete")' />
<span style='color:red'>注意：删除计划将删除该计划下的所有集备及其相关的内容。</span>
</div>
<input type='hidden' name='cmd' value='' />
</form>
<#include ('/WEB-INF/ftl/course/pager.ftl')>
<#else>
没有备课计划，请创建一个<a href='group_course_plan_new.py?groupId=${group.groupId}'>创建备课计划</a>
</#if>

</body>
</html>