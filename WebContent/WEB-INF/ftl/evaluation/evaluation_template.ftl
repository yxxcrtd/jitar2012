<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>列表</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />  
  <script language='javascript'>
  </script>
</head>
<body>
<h2>评课模板管理</h2>
<form method="post" action="evaluation.action">
<input type="hidden" name="cmd" value="list" />
<input type="hidden" name="listtype" value="template" />
<table class='listTable' cellspacing='1' >
  <thead>
    <tr style="text-align:left">
      <th width="17"></th>
      <th>模板名称</th>
      <th>是否启用</th>
      <th>操作</th>
    </tr>
  </thead>
  <tbody>
  <#if !(template_list??) || template_list?size == 0>
    <tr>
    <td colspan="5">暂时没有模板，<a href="${SiteUrl}manage/evaluation/evaluation_template_edit.py">现在就创建一个</a>。</td>
    </tr>
  <#else>
   <#list template_list as et>
   <tr style="vertical-align:top;">
    <td><input type="checkbox" name="guid" value="${et.evaluationTemplateId}"></td>
    <td>${et.evaluationTemplateName}</td>
    <td>${et.enabled?string("启用","<font style='color:red'>禁用</font>")}</td>
    <td><a href="evaluation.action?cmd=edit&listtype=template&evaluationTemplateId=${et.evaluationTemplateId}">修改</a></td>
    </tr>
    </#list>
  </#if>
  </table>
<div style="padding-top:6px">
<input type="button" class="button" value="启用模板" onclick="this.form.cmd.value='enabled';this.form.submit();" />
<input type="button" class="button" value="禁用模板" onclick="this.form.cmd.value='disabled';this.form.submit();" />
<input type="button" class="button" value="删除评课活动模板" onclick="if(window.confirm('你真的要删除吗？')){this.form.cmd.value='delete';this.form.submit();}" />
<input type="button" class="button" value="新建评课活动模板" onclick="window.location='${SiteUrl}manage/evaluation.action?cmd=edit&listtype=template&evaluationTemplateId=0'" />
</div>
</body>
</html>