<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
</head>
<body>
<h2>学科页面模板管理</h2>
<form method='post'>
<table class='listTable' cellspacing='1'>
<tr>
<td style='width:70px'>模板1：</td><td><input type='radio' name='tmpl' value='template1' <#if subject.templateName?? && subject.templateName =='template1'>checked='checked' </#if>/>
<img src='${SiteUrl}images/layout/layout_1.gif' />
</td>
</tr>
<tr>
<td style='width:70px'>模板2：</td><td><input type='radio' name='tmpl' value='template2' <#if subject.templateName?? && subject.templateName =='template2'>checked='checked' </#if>/>
<img src='${SiteUrl}images/layout/layout_2.gif' />
</td>
</tr>
<tr>
<td style='width:70px'>模板3：</td><td><input type='radio' name='tmpl' value='template3' <#if subject.templateName?? && subject.templateName =='template3'>checked='checked' </#if>/>
<img src='${SiteUrl}images/layout/layout_3.gif' />
</td>
</tr>
<tr>
<td style='width:70px'>模板4：</td><td><input type='radio' name='tmpl' value='template4' <#if subject.templateName?? && subject.templateName =='template4'>checked='checked' </#if>/>
<img src='${SiteUrl}images/layout/layout_5.gif' />
</td>
</tr>
<tr>
<td></td><td><input class='button' type='submit' value=' 保  存 ' /></td>
</tr>
</table>
</form>
</body>
</html>