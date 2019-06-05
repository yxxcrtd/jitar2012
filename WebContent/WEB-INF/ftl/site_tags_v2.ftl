<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>标签中心 <#include ('webtitle.ftl')></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}tags.css" />
    <!--[if IE 6]>
	<style type='text/css'>
	.q_t_r {float:left;margin:0 10px;padding-top:15px;}
	</style>
 <![endif]-->  
  <script src='js/jitar/core.js'></script>
  <script src='js/jitar/dtree.js'></script>
 </head>
 <body>
<#include 'site_head.ftl'>
<div style='height:8px;font-size:0;'></div>
<table class='tag_table' cellspacing='1' cellpadding='3' style='table-layout:fixed;width:100%'>
  <tr>
    <th style='width:80%'>标签名称</th>
    <th style='width:10%'>引用次数</th>
    <th style='width:10%'>查看次数</th>
  </tr>
  <#if tag_list??>
  <#list tag_list as tag>
  	<tr>
  	<td><a class='tagLink' href='show_tag.action?tagId=${tag.tagId}'>${tag.tagName!?html}</a></td>
    <td>${tag.refCount}</td>
    <td>${tag.viewCount}</td>
	</tr>
  </#list>
  </#if>
</table>
<div style='text-align:center;padding:8px'><#include 'inc/pager.ftl'></div>

<#include 'footer.ftl' >

</body>
</html>