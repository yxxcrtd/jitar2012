<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title><#include 'webtitle.ftl' ></title>
  <link rel="stylesheet" type="text/css" href="css/common/common.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}index.css" />
  <script src='js/jitar/core.js'></script>  
</head>
<body>
<#include 'site_head.ftl'>
<div style='height:8px;font-size:0;'></div>

<div class='head_nav'>
  <a href='index.action'>首页</a> &gt; 教研动态
 </div>

<#if news_list??>
    <ul class='news_new_item_ul'>
      <#list news_list as news>
        <li style="padding:2px 0 4px 10px"><span>${news.createDate?string('yyyy-MM-dd')}</span><#if news?? && news.picture != ""><font style="color:red">[图]</font></#if><a href='showNews.action?newsId=${news.newsId}' target='_blank'>${news.title!?html}</a></li> 
      </#list>
    </ul>
  </#if>
<div class='pgr'><#include 'inc/pager.ftl' ></div>
<div style="clear: both;"></div>   
<#include 'footer.ftl'>
</body>
</html>

