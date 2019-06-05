<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>列表</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />  
</head>
<body>
<h2>评课活动管理</h2>
<form method="post" action="evaluation.action">
<input type="hidden" name="cmd" value="list" />
<input type="hidden" name="listtype" value="plan" />
<table class='listTable' cellspacing='1'>
  <thead>
    <tr style="text-align:left">
      <th width="17"></th>
      <th>课题</th>
      <th>授课人</th>
      <th>学科/学段</th>
      <th>授课时间</th>
      <th>评课开始时间</th>
      <th>评课结束时间</th>
      <th>状态</th>
      <th>操作</th>
    </tr>
  </thead>
  <tbody>
  <#if evaluation_list??>
   <#list evaluation_list as ev>
   <tr>
    <td><input type="checkbox" name="guid" value="${ev.evaluationPlanId}"></td>
    <td><a href='${SiteUrl}evaluations.action?cmd=content&evaluationPlanId=${ev.evaluationPlanId}' target='_blank'>${ev.evaluationCaption}</a></td>
    <td><a href='${SiteUrl}go.action?userId=${ev.teacherId}' target='_blank'>${ev.teacherName!?html}</a></td>
    <td><nobr>${ev.msubjName!}/${ev.gradeName!}</nobr></td>
    <td><nobr>${ev.teachDate?string("yyyy年M月d日")}</nobr></td>
    <td><nobr>${ev.startDate?string("yyyy年M月d日")}</nobr></td>
    <td><nobr>${ev.endDate?string("yyyy年M月d日")}</nobr></td>
    <td><nobr>${ev.enabled?string("启用","<font style='color:red'>禁用</font>")}</nobr></td>
    <td><nobr><a href="${SiteUrl}evaluations.action?cmd=edit&evaluationPlanId=${ev.evaluationPlanId}" target="_blank">修改</a></nobr></td>
    </tr>
    </#list>
  </#if>
  </table>
<div class='pager' style="width:98%">
  <#include '/WEB-INF/ftl/inc/pager.ftl' >
</div>
<div style="padding-top:6px">
<input type="button" class="button" value="启用评课活动" onclick="this.form.cmd.value='enabled';this.form.submit();" />
<input type="button" class="button" value="禁用评课活动" onclick="this.form.cmd.value='disabled';this.form.submit();" />
<input type="button" class="button" value="删除评课活动及内容" onclick="if(window.confirm('你真的要删除吗？')){this.form.cmd.value='delete';this.form.submit();}" />
<!--
<input type="button" class="button" value="新一轮评课活动设置" onclick="window.location='${SiteUrl}manage/evaluation/evaluation_plan_edit.py'" />
-->
</div>
</form>
<div style="color:#f00">说明：删除评课活动，也将删除已经发布的评课内容。</div>
</body>
</html>