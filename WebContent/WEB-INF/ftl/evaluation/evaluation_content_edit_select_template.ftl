<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}index.css" />
  <title>评课 <#include '/WEB-INF/ftl/webtitle.ftl'></title>
 </head>
 <body>
<#include '/WEB-INF/ftl/site_head.ftl'>
<div style='height:8px;font-size:0;'></div>

<div style="padding-left:30%">
<h2>要进行评课，请点击下面的模板名称选择模板</h2>
<#list template_list as t>
<div style="padding:6px"><a href="?evaluationPlanId=${evaluationPlanId}&templateId=${t.evaluationTemplateId}">${t.evaluationTemplateName!?html}</a></div>
</#list>
</div>

<#include '/WEB-INF/ftl/footer.ftl' >

</body>
</html>