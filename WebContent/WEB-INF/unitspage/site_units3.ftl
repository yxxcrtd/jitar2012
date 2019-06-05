<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>组织机构导航 <#include '/WEB-INF/ftl/webtitle.ftl' ></title>
  <link rel="icon" href="${SiteUrl}favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}favicon.ico" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}gallery.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}js/treeview/treeview.css" />
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/json2.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/treeview/treeview.js"></script>
  <link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
	<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
	<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/gradesubject_blogs.js" type="text/javascript"></script>
 </head>
<body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<div style='height:12px;font-size:0;'></div>
<div style='clear:both;height:8px;'></div>
<div style="padding-left:30px">
<#if rootUnit??>
<div style="margin-left:200px;" id="node${rootUnit.unitId}" class="treeview"><img src="${SiteUrl}js/treeview/base.gif"/><a href="${SiteUrl}go.action?unitName=${rootUnit.unitName}"><b>${rootUnit.unitTitle!?html}</b>（${rootUnit.siteTitle!?html}）</a></div>
<script type="text/javascript">
//var RootId = ${rootUnit.unitId};
var JITAR_ROOT = window.JITAR_ROOT || "${SiteUrl}";
//window.onload = Page_Init;
var tree = new TreeView("node${rootUnit.unitId}","units.action?cmd=ajax","${SiteUrl}js/treeview/");
tree.Init();
</script>
<#else>
没有根单位信息。
</#if>
</div>
<div style='clear:both;height:10px;'></div>
<#include '/WEB-INF/ftl2/footer.ftl' >
</body>
</html>