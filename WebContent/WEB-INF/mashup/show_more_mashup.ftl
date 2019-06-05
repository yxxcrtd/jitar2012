<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title><#include '/WEB-INF/ftl/webtitle.ftl' ></title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />  
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}index.css" />
  <script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
</head>
<body>
<#include '/WEB-INF/ftl/site_head.ftl'>
<div style='height:8px;font-size:0;'></div>
<div style='height:8px;font-size:0;'></div>
<div class='main'>
	<#if article_list??>
	<h3 style='text-align:center'>全部${pager.itemName}</h3>
	<table class='border_table' cellpadding='4' cellspacing='1'>
	<#list article_list as a>
	<tr>
	<td style='width:100%'><img src='${SiteThemeUrl}bg10.gif' hspace='4' /> <a target='_blank' href='${a.href}push/show.py?type=${documentType}&amp;orginId=${a.orginId}&g=${mashupuser!?url}<#if mashupType??>&from=1</#if>'>${a.title!?html}</a></td>
	<td><nobr>${a.author!?html}</nobr></td>
	<td><nobr>${a.unitName!?html}</nobr></td>
	<td><nobr>${a.platformName!?html}</nobr></td>
	<td><nobr>${a.pushDate?string('yyyy-MM-dd HH:mm:ss')}</nobr></td>
	</tr>
	</#list>
	</table>
	<div style='text-align:center;padding:10px 0'><#include "/WEB-INF/ftl/inc/pager.ftl"></div>
	</#if>
	
	<#if blog_list??>
	<h3 style='text-align:center'>全部${pager.itemName}</h3>
	<table class="lastlist" cellspacing="1">
	<tr>
		<th><nobr></nobr></th>    
		<th><nobr>学科</nobr></th>
		<th><nobr>学段</nobr></th>
		<th><nobr>机构</nobr></th>
		<th><nobr>平台</nobr></th>
	</tr>
	<#list blog_list as user>
	<tr>
		<td class="list_title">
			<table width="100%">
			<tr>
			<td width="64">
			<img src="${user.href}${user.icon}" width='64' height='64' border='0' onerror="this.src='${SiteUrl}images/default.gif'" />
			</td>
			<td align='left' valign='top'>
			<div style="line-height: 16px;">姓名：<a href='${user.href}go.py?userId=${user.orginId}'>${user.trueName}</a></div>
 			<div style="line-height: 16px;">简介：${user.description!}</div>
			</td>
			</tr>
			</table>
 		 </td>
 		 <td><nobr>${user.subjectName!}</nobr></td>
 		 <td><nobr>${user.gradeName!}</nobr></td>
 		 <td><nobr>${user.unitTitle!}</nobr></td>
 		 <td><nobr><a href='${user.href}' target='_blank'>${user.platformName!}</a></nobr></td>
 		</tr>
 	</#list>
 	</table>
 	<div style='text-align:center;padding:10px 0'><#include "/WEB-INF/ftl/inc/pager.ftl"></div>
 	</#if>
	
</div>	
<#include "/WEB-INF/ftl/footer.ftl">		
</body>
</html>