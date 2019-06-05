<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title><#include '/WEB-INF/ftl/webtitle.ftl' ></title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}index.css" />
  <style type='text/css'>
	#platform a {display:block;float:left;width:224px;padding:6px;background:#efefef;margin:2px;text-align:center}
  </style> 
   <!--[if IE 6]>
	<style type='text/css'>
	#platform a {display:block;float:left;width:224px;padding:6px;background:#efefef;margin:1px}
	</style>
 <![endif]-->
  <script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
</head>
<body>
<#include '/WEB-INF/ftl/site_head.ftl'>
<div style='height:8px;font-size:0;'></div>
<div class='main'>

<div class='b1' style='height:auto'>
  <div class='b1_head'>
    <div class='b1_head_right'><a href='show_more_mashup.py?type=article'>更多…</a></div>
    <div class='b1_head_left'>&nbsp;<img src='${SiteThemeUrl}j.gif' />&nbsp;文章</div>
  </div>
  <div class='b1_content' style='text-align:left'>
	  <#if article_list??>
		<table class='border_table' cellpadding='4' cellspacing='1'>
		<#list article_list as a>
		<tr>
		<td style='width:100%'><img src='${SiteThemeUrl}bg10.gif' hspace='4' /> <a target='_blank' href='${a.href}push/show.py?type=article&amp;orginId=${a.orginId}&g=${mashupuser!?url}<#if mashupType??>&from=1</#if>'>${a.title!?html}</a></td>
		<td><nobr>${a.author!?html}</nobr></td>
		<td><nobr>${a.unitTitle!?html}</nobr></td>
		<td><nobr>${a.platformName!?html}</nobr></td>
		<td><nobr>${a.pushDate?string('yyyy-MM-dd HH:mm:ss')}</nobr></td>
		</tr>
		</#list>
		</table>
      </#if>
  </div>
</div>

<div style='clear:both;height:10px;'></div>

<div class='b1' style='height:auto'>
  <div class='b1_head'>
    <div class='b1_head_right'><a href='show_more_mashup.py?type=resource'>更多…</a></div>
    <div class='b1_head_left'>&nbsp;<img src='${SiteThemeUrl}j.gif' />&nbsp;资源</div>
  </div>
  <div class='b1_content' style='text-align:left'>
	  <#if resource_list??>
		<table class='border_table' cellpadding='4' cellspacing='1'>
		<#list resource_list as a>
		<tr>
		<td style='width:100%'><img src='${SiteThemeUrl}bg10.gif' hspace='4' /> <a target='_blank' href='${a.href}push/show.py?type=resource&amp;orginId=${a.orginId}&g=${mashupuser!?url}<#if mashupType??>&from=1</#if>'>${a.title!?html}</a></td>
		<td><nobr>${a.author!?html}</nobr></td>
		<td><nobr>${a.unitTitle!?html}</nobr></td>
		<td><nobr>${a.platformName!?html}</nobr></td>
		<td><nobr>${a.pushDate?string('yyyy-MM-dd HH:mm:ss')}</nobr></td>
		</tr>
		</#list>
		</table>
	   </#if>
  </div>
</div>

<div style='clear:both;height:10px;'></div>
<div class='b1' style='height:auto'>
  <div class='b1_head'>
    <div class='b1_head_right'><a href='show_more_mashup.py?type=blog'>更多…</a></div>
    <div class='b1_head_left'>&nbsp;<img src='${SiteThemeUrl}j.gif' />&nbsp;名师</div>
  </div>
  <div class='b1_content'>
  <table border='0' cellpadding='0' cellspacing='0' align='center'>
  <tr>
  <td align='center'>  
	 <#if blog_list??>
	    <#list blog_list as u> 
	      <div class='photolist' style='height:auto;width:110px;'>
	        <div>
	         <span style='display:block;width:96px;height:96px;background:#eee;margin:10px'><a style='visibility:hidden' href='${u.href}go.py?userId=${u.orginId}'><img onload='CommonUtil.reFixImg(this,96,96)' src='${u.href}${u.icon}' alt='${u.trueName!?html}' border='0' onerror="this.src='${SiteUrl}images/default.gif'" /></a></span>
	        </div>
	        <div class='phototext' style='padding-top:4px;'><a href='${u.href}go.py?userId=${u.orginId}'>${u.trueName!?html}</a></div>
	      </div>
	    </#list>
	 </#if>	 
  	</td>
  	</tr>
  	</table>
  </div>
</div>

<div style='clear:both;height:10px;'></div>
<div class='b1' style='height:auto'>
  <div class='b1_head'>
    <div class='b1_head_right'></div>
    <div class='b1_head_left'>&nbsp;<img src='${SiteThemeUrl}j.gif' />&nbsp;区县平台</div>
  </div>
  <div class='b1_content' id='platform' style='text-align:center'>
	  <div style='margin:auto;width:960px;'>
	  		<#list platfotm_list as p>
	  		  <a href='${p.platformHref}push/platform.py?g=${encUserGuid!?url}'>${p.platformName?html}</a>
	  		</#list>
		</div>
		<div style='clear:both;'></div>
  </div>
</div>
<#include "/WEB-INF/ftl/footer.ftl">		
</body>
</html>