<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="alternate" type="application/rss+xml" title="Recent Changes" href="${SiteUrl}rss.py?type=article" />
  <#if SiteConfig ??>
  <meta name="keywords" content="${SiteConfig.site.keyword!}" /> 
  </#if>
  <title><#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}index.css" />
 </head>
 <body>
<#include 'site_head.ftl'>
<div style='height:8px;font-size:0;'></div>

<div class='containter'>
 <div style='padding:0 20px'>
  <div style='padding-top:10px;text-align:center;font-size:16px;font-weight:bold;color:red'>网站订阅分类</div>

  <div style='padding:20px 60px;font-size:16px;line-height:150%;text-align:left;'>
  
  <div><img src='${SiteUrl}images/rss2.gif' hspace='4' align='absmiddle' /><a href='${SiteUrl}rss.py?type=article'>订阅网站文章</a>：<a href='${SiteUrl}rss.py?type=article'>${SiteUrl}rss.py?type=article</a></div>
  <div><img src='${SiteUrl}images/rss2.gif' hspace='4' align='absmiddle' /><a href='${SiteUrl}rss.py?type=resource'>订阅网站资源</a>：<a href='${SiteUrl}rss.py?type=resource'>${SiteUrl}rss.py?type=resource</a></div>
  <div><img src='${SiteUrl}images/rss2.gif' hspace='4' align='absmiddle' /><a href='${SiteUrl}rss.py?type=photo'>订阅网站图片</a>：<a href='${SiteUrl}rss.py?type=photo'>${SiteUrl}rss.py?type=photo</a></div>
  <div><img src='${SiteUrl}images/rss2.gif' hspace='4' align='absmiddle' /><a href='${SiteUrl}rss.py?type=topic'>订阅网站协作组话题</a>：<a href='${SiteUrl}rss.py?type=topic'>${SiteUrl}rss.py?type=topic</a></div>
  </div>
 </div>
</div>

<#include 'footer.ftl'>
</body>
</html>

