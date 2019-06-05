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
<table class='tag_table' cellspacing='1' cellpadding='0' style='table-layout:fixed'>
  <tr>
    <td class='tag_table_left'>所有标签列表</td>
    <td class='tag_table_right'>
<#if tag_list??>
    <ul class='SidebarTagCloud'>
	<#list tag_list as tag>
	 <li class='Tag${tag.fontSize}'><a class='tagLink' href='${SiteUrl}showTag.action?tagId=${tag.tagId}' title='引用次数: ${tag.refCount}, 查看次数: ${tag.viewCount}' >${tag.tagName!?html}</a></li>
	</#list>
	</ul>
</#if>
    </td>
	</tr>
</table>

<#include ('footer.ftl') >

</body>
</html>