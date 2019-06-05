<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${unit.siteTitle?html}</title>
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />  
  <link rel='stylesheet' type='text/css' href='${SiteUrl}js/datepicker/calendar.css' />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/units/${unit.themeName?default('theme1')}/index.css" />
  <script type="text/javascript" src='${SiteUrl}js/jitar/core.js'></script>
  <script type='text/javascript' src='${SiteUrl}js/datepicker/calendar.js'></script>
 </head>
<body>

<#include "/WEB-INF/unitspage/unit_header.ftl">
<#include "/WEB-INF/unitspage/unit_nav.ftl">

<div id='container'>
<h3>[placeholder_title]</h3>
[placeholder_content]
</div>
<div style='clear:both;height:8px;'></div>
<#if unit.footerContent??>
${unit.footerContent}
</#if>
</body>
</html>