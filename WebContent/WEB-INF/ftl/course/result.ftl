<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>信息提示</title>
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="alternate" type="application/rss+xml" title="Recent Changes" href="/rss.py?type=article" />
  <title></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}index.css" />
 </head> 
 <body>
<#include '/WEB-INF/ftl/site_head.ftl' >
<div style='padding-top:10px;text-align:center;font-size:24px;font-weight:bold;color:red'>网站访问提示</div>
<div style='padding:20px 300px;font-size:12px'>
<#if msg?? >
${msg}
</#if>
 </div>
<div style="clear: both;"></div>   
<#include '/WEB-INF/ftl/footer.ftl'>
 </body>
</html>