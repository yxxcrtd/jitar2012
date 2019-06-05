<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${ContextPath}images/favicon.ico" />
  <link rel="shortcut icon" href="${ContextPath}images/favicon.ico" />
  <title><#include 'webtitle.ftl' ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}css/index.css" />
  <script src='js/jitar/core.js'></script>  
 </head>
 <body>
<#include 'site_head.ftl'>
<div style='height:8px;font-size:0;'></div>
<div class='containter'>
 <div style='padding:0 20px'>
  <div style='padding-top:10px;text-align:center;font-size:16px;font-weight:bold;'>${news.title!?html}</div>
    <#assign u = Util.userById(news.userId) >
  <div style='text-align:center;padding:10px;'>发布人: ${u.trueName!}, 发布时间：${news.createDate}, 浏览数: ${news.viewCount}</div>
  <div style='padding:10px 40px;font-size:14px;'>
  <#if news?? && news.picture != ""><div style="text-align:center;padding:10px"><img src="${news.picture!}" onload="CommonUtil.reFixImg(this,600,400);" /></div></#if>
  ${news.content!}
  </div>
 </div>
</div>

<#include 'footer.ftl' >

</body>
</html>
