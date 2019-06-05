<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>组织机构导航 <#include '/WEB-INF/ftl/webtitle.ftl' ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}gallery.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/index/dtree.css" />
  <style type='text/css'>
	div.gly_content a {display:block;float:left;width:224px;padding:6px;background:#efefef;margin:2px}
  </style>
 <!--[if IE 6]>
	<style type='text/css'>
	div.gly_content a {display:block;float:left;width:224px;padding:6px;background:#efefef;margin:1px}
	</style>
 <![endif]-->
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
 </head>
<body>
<#include '/WEB-INF/ftl/site_head.ftl'>
<div style='height:12px;font-size:0;'></div>
<div style='clear:both;height:8px;'></div>
<div class='gly'>
<#if rootUnit??>
  <div class='gly_head'>
    <div class='gly_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' />&nbsp;${rootUnit.unitTitle!?html}</div>
  </div>
</#if>
  <div class='gly_content' style='text-align:center'>
  	<div style='margin:auto;width:960px;'>
  		<#list childUnitList as uu>
  		<#if UnitUrlPattern??>
  		  <a href='${UnitUrlPattern.replace('{unitName}',uu.unitName)}'>${uu.unitTitle!?html}</a>
  		<#else>
  		  <a href='${SiteUrl}d/${uu.unitName}/'>${uu.unitTitle!?html}</a>
  		</#if>
  		</#list>
	</div>
  	<div style='clear:both;'></div>
  </div>
</div>
<div style='clear:both;height:10px;'></div>
<#include '/WEB-INF/ftl/footer.ftl' >
</body>
</html>