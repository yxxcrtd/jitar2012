<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>${contentSpaceArticle.title!?html}</title>
<#include "/WEB-INF/ftl2/common/favicon.ftl" />
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/index.css" type="text/css">  
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
</head>
<body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<div style='height:8px;font-size:0;'></div>
<div class='containter' style="width:1100px;margin:0 auto;">
 <div style='padding:0 20px'>
  <div style='padding-top:10px;text-align:center;font-size:16px;font-weight:bold;'>${contentSpaceArticle.title!?html}</div>
    <#assign u = Util.userById(contentSpaceArticle.createUserId) >
  <div style='text-align:center;padding:10px;'>发布人: ${u.trueName!} &nbsp; 发布时间：${contentSpaceArticle.createDate?string("yyyy-MM-dd")} &nbsp; 浏览数: ${contentSpaceArticle.viewCount + 1}</div>
  <div style='padding:10px 40px;font-size:14px;' id="content">
  <#if contentSpaceArticle?? && contentSpaceArticle.pictureUrl?? && contentSpaceArticle.pictureUrl != ""><div style="text-align:center;padding:10px"><img src="${contentSpaceArticle.pictureUrl!}" /></div></#if>
  ${contentSpaceArticle.content!}
  </div>
 </div>
</div>
<#include '/WEB-INF/ftl2/footer.ftl' >
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
