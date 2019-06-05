<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 组织机构导航</title>
  <link rel="icon" href="${SiteUrl}favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}favicon.ico" />

  <link rel="stylesheet" type="text/css" href="${SiteUrl}js/treeview/treeview.css" />
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/json2.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/treeview/treeview.js"></script>
  <link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
	<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
	<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
 </head>
<body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<div class="main mt25 clearfix">
<div class="moreList border">
<h3 class="h3Head textIn"><span class="moreHead">机构导航</span></h3>
<div class="moreContent" style="min-height:320px">
    <#if rootUnit??>
    <div id="node${rootUnit.unitId}" class="treeview"><img src="${SiteUrl}js/treeview/base.gif"/><a href="<#if configSiteRoot??>${configSiteRoot}<#else>${SiteUrl}go.action?unitName=${rootUnit.unitName}</#if>"><b>${rootUnit.unitTitle!?html}</b>（${rootUnit.siteTitle!?html}）</a></div>
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
</div>
</div>
<#include '/WEB-INF/ftl2/footer.ftl' >
</body>
</html>