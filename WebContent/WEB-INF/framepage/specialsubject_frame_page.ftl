<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>
<html xmlns='http://www.w3.org/1999/xhtml'>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<title>教研专题 - <#include '/WEB-INF/ftl/webtitle.ftl' ></title>
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/tooltip/tooltip.css" />
<link rel='stylesheet' type='text/css' href='${SiteUrl}js/datepicker/calendar.css' />
<link rel="stylesheet" type="text/css" href="${SiteThemeUrl}special.css" />
<script type='text/javascript'>
//<![CDATA[
var ContainerObject = {'type':'specialsubject','guid':'${objectGuid}'};
var JITAR_ROOT = '${SiteUrl}'; 
var USERMGR_ROOT = '';
//]]>
</script>
<script type='text/javascript' src='${SiteUrl}js/jitar/lang.js'></script>
<script type='text/javascript' src='${SiteUrl}js/jitar/core.js'></script>
<script type='text/javascript' src='${SiteUrl}jython/get_plugin_module.py'></script>
<script type="text/javascript" src="${SiteUrl}js/jitar/tooltip.js"></script>
<script type='text/javascript' src='${SiteUrl}js/datepicker/calendar.js'></script>
</head>
<body>
<#include "/WEB-INF/ftl/site_head.ftl" >
<div style='height:8px;font-size:0;'></div>
<#if specialSubject?? >
<#if specialSubject.logo?? >
<div><img src='${specialSubject.logo}' /></div>
<#else>
<div class='default_logo'><div class='inner'>${specialSubject.title}</div></div>
</#if>
</#if>
<div style='height:8px;font-size:0;'></div>
<h3>[placeholder_title]</h3>
[placeholder_content]
<div style='height:8px;font-size:0;'></div>
<#include '/WEB-INF/ftl/footer.ftl' >
</body>
</html>