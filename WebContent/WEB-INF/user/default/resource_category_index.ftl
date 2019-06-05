<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>资源 ${user.blogName!?html} - ${category.name!?html}</title>
      <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
      <link id='skin' rel="stylesheet" type="text/css" href="${SiteUrl}css/skin/${page.skin!'skin1'}/skin.css" />
      <!-- 布局模板 -->
      <link rel="stylesheet" type="text/css" href="${SiteUrl}css/layout/layout_2.css" />
      <!-- ToolTip -->
      <link rel="stylesheet" type="text/css" href="${SiteUrl}css/tooltip/tooltip.css" />
      
      <#include '../user_script.ftl' >
    <script>
    var category = {id: ${category.id}, name: '${category.name!?js_string}' };
    </script>
    <script src='${SiteUrl}js/jitar/core.js'></script>
    <script src='${SiteUrl}js/jitar/lang.js'></script>
    <script type="text/javascript" src="${SiteUrl}js/jitar/login.js"></script>
  </head> 
  <body>
  <#-- 无功能按钮的子页面 -->
  <#include ('childpage.ftl') >
  <div id='progressBar' style='position:absolute;width:200px;left:600px;top:8px;border:1px solid red;display:none;background:yellow;float:right;z-index:100'>正在加载……</div>
  <div id='header'>
	<div id='blog_name'><span>${user.blogName!?html}</span></div>
  </div>
  <div id='navbar'><#include ('navbar.ftl') ></div>    
    <#-- 调用页面指定的布局模式 -->
    <#include ('../../layout/layout_2.ftl') >

    <script>
      App.start();
    </script>
	<div id="subMenuDiv"></div>
	<#-- 原来这里是 include loginui.ftl  -->
	<script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>
	<script type="text/javascript" src="${SiteUrl}js/jitar/msgtip.js"></script>
  </body>
</html>
