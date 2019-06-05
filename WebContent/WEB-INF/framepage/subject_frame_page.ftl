<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${subject.subjectName?html} - 学科</title>
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <#if theme??>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/subject/${theme}/index.css" />
  <#else>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/index.css" />
  </#if>  
   <link rel='stylesheet' type='text/css' href='${SiteUrl}js/datepicker/calendar.css' />
   <script type='text/javascript' src='${SiteUrl}js/jitar/core.js'></script>
   <script type='text/javascript' src='${SiteUrl}js/datepicker/calendar.js'></script>
 </head>
<body>
<#include "/WEB-INF/subjectpage/subject_header.ftl">
<#include "/WEB-INF/subjectpage/subject_nav.ftl">

<div id='container'>
<h3 style='text-align:center'>[placeholder_title]</h3>
[placeholder_content]
</div>

<#include "/WEB-INF/subjectpage/subject_footer.ftl">
</body>
</html>

