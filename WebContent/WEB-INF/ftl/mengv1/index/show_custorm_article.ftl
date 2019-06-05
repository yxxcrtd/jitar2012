<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title><#include ('/WEB-INF/ftl/webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="css/common/common.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}index.css" />
  <script src='js/jitar/core.js'></script>  
 </head>
 <body>

<#include '/WEB-INF/ftl/site_head.ftl'>
<div style='height:8px;font-size:0;'></div>

<div class='head_nav'>
  <a href='index.action'>首页</a> &gt; ${Page_Title!?html}
 </div>
 
<#if article_list??>
<ul class='news_new_item_ul'>
  <#list article_list as sa>		  
	  <li><span><a href='${SiteUrl}go.action?userId=${sa.userId}'>${sa.trueName!?html}</a> ${sa.createDate?string('yyyy-MM-dd')}</span>
		<a href='${SiteUrl}showArticle.action?articleId=${sa.articleId}' target='_blank'>${sa.title!?html}</a>
	  </li>
	</#list>
</ul>
</#if>
  
<div class='pgr'><#include '/WEB-INF/ftl/inc/pager.ftl' ></div>
  
<div style="clear: both;"></div>   
<#include '/WEB-INF/ftl/footer.ftl'>
 </body>
</html>