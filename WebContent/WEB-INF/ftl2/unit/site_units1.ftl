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
<div class="mt3 border">
 <h3 class="h3Head textIn"><#if rootUnit??>${rootUnit.unitTitle!?html}</#if></h3>
   <div class="clearfix">
    <div style="background:#fff;padding:10px 0 10px 20px">
    
    <#list childUnitList as uu>
     <a style="display:block;width:220px;margin:6px;background:#dedede;text-align:center;padding:4px;float:left" href='${SiteUrl}go.action?unitName=${uu.unitName}'>${uu.unitTitle!?html}</a>
    </#list>
      <div class="clearfix"></div>
      </div>
      <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" style="width:100%"></div>
   </div>
   
</div>
</div>
</div>
<#include '/WEB-INF/ftl2/footer.ftl' >
</body>
</html>