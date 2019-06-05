<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - ${news.title!?html}</title>
  <#include "/WEB-INF/ftl2/common/favicon.ftl" />
  <link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
	<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">  
  <script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
  <script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
  <script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
 </head>
 <body>
<#include 'site_head.ftl'>
<div style='height:8px;font-size:0;'></div>

<div class='containter' style="width:1000px;margin:0 auto;background:#fff">
 <div style='padding:0 20px'>
  <div style='padding-top:10px;text-align:center;font-size:16px;font-weight:bold;'>${news.title!?html}</div>
    <#assign u = Util.userById(news.userId) >
  <div style='text-align:center;padding:10px;'>发布人: ${u.trueName!} 发布时间：${news.createDate} 浏览数: ${news.viewCount + 1}</div>
  <div style='padding:10px 40px;font-size:14px;' id="content">
  <#if news?? && news.picture != ""><div style="text-align:center;padding:10px"><img id="contentImage" src="${news.picture!}" /></div></#if>
  ${news.content!}
  <br/><br/>
  </div>
 </div>
</div>
<#include 'footer.ftl' >
<script>
$(function(){
$("#content img").each(function(){
 if($(this).width() > 800){
  $(this).css("width","800px");
 }
});
});
</script>
</body>
</html>
