<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${user.blogName!(user.trueName! + ' 的工作室')?html}</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
  <link id='skin' rel="stylesheet" type="text/css" href="${SiteUrl}css/skin/${page.skin!'default'}/skin.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/layout/layout_${(page.layoutId!'1')}.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/tooltip/tooltip.css" />  		
  <script type='text/javascript' src='${SiteUrl}js/jitar/core.js'></script>
  <script type='text/javascript' src='${SiteUrl}js/jitar/lang.js'></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/Drag.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/tooltip.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/login.js"></script>
  <script type='text/javascript' src='${SiteUrl}jython/get_plugin_module.py'></script>
  <#include '../user_script.ftl' >
  <link rel="icon" href="${SSOServerUrl +'upload/'+user.userIcon!"images/default.gif"}" />
  <link rel="shortcut icon" href="${SSOServerUrl +'upload/'+user.userIcon!"images/default.gif"}" />
</head>	
<body>
    
	<#include 'func.ftl' >
	<div id='progressBar' style='position:absolute;width:200px;left:600px;top:8px;border:1px solid red;display:none;background:yellow;float:right;z-index:100'>正在加载……</div>
	<div id='header'>
		<div id='blog_name'><span>${user.blogName!?html}</span></div>
	</div>
	<#--<div id='navbar'><#include ('navbar.ftl') ></div> -->
	<#-- 调用页面指定的布局模式 -->
	<#include ('../../layout/layout_' + (page.layoutId!'1') + '.ftl') >
	<div id='page_footer'></div>
	<script>App.start();</script>
	<div id="subMenuDiv"></div>
	<script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>
	<#-- 原来这里是 include loginui.ftl  -->
	<#if UserUrlPattern??>
	<script type="text/javascript" src="${UserUrlPattern.replace('{loginName}',user.loginName)}js/jitar/msgtip.js"></script>
	<#else>
	<script type="text/javascript" src="${SiteUrl}js/jitar/msgtip.js"></script>
	</#if>
</body>
</html>
