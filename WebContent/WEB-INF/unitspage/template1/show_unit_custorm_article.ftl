<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${unit.siteTitle?html}</title>
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/units/${unit.themeName?default('theme1')}/index.css" />  
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/units/${unit.themeName?default('theme1')}/dtree.css" />
  <script type="text/javascript" src="${SiteUrl}js/jitar/dtree.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
 </head>
<body>
<#include "/WEB-INF/unitspage/unit_total_header.ftl">

<div style='height:8px;font-size:0;'></div>

<div class='head_nav' style="width:1000px;left:50%; margin:0px 0 0 -500px; position:absolute;text-align:left;">
  <a href='index.py?unitId=${unit.unitId}'>首页</a> &gt; ${Page_Title!?html}
 </div>

<div style='padding:20px 0;width:1000px; left:50%; margin:0px 0 0 -500px; position:absolute;'>
  <#if article_list??>
		<ul class='item_ul'>
		<#list article_list as sa>
		<#if partType ==2>
		  <li><span>${sa.createDate?string('yyyy-MM-dd')}</span>
			<a href='${UnitRootUrl}py/showContent.py?articleId=${sa.contentSpaceArticleId}' target='_blank'>${sa.title!?html}</a>
		  </li>
		<#else>
		  <li><span><a href='${SiteUrl}go.action?userId=${sa.userId}'>${sa.trueName!?html}</a> ${sa.createDate?string('yyyy-MM-dd')}</span>
			<a href='${SiteUrl}showArticle.action?articleId=${sa.articleId}' target='_blank'>${sa.title!?html}</a>
		  </li>
		</#if>
		</#list>
		</ul>
	</#if>  
	<div style="clear: both;"></div> 
  <div class='pgr'><#include '/WEB-INF/ftl/pager.ftl' ></div>
<div style="clear: both;"></div>   
<#include "/WEB-INF/unitspage/unit_footer.ftl">
</div>
</body>
</html>