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
 </head>
<body>
<#include "/WEB-INF/unitspage/unit_total_header.ftl">

<div style='height:8px;font-size:0;'></div>

<div style='padding:20px 0'>
<div class='containter'>
 <div style='padding:0 20px'>
  <div style='padding-top:10px;text-align:center;font-size:16px;font-weight:bold;'>
    ${contentSpaceArticle.title!?html}</div>
  <#assign user = Util.userById(contentSpaceArticle.createUserId)>
  <div style='text-align:center;padding:10px;'><#if user??>作者：${user.trueName?html}&nbsp;&nbsp;&nbsp;&nbsp; </#if>发布时间：${contentSpaceArticle.createDate}&nbsp;&nbsp;&nbsp;&nbsp; 查看次数：${contentSpaceArticle.viewCount}</div>
  <#if contentSpaceArticle.pictureUrl?? && contentSpaceArticle.pictureUrl != ''>
  <div style='text-align:center;padding:20px 0'><img src='${contentSpaceArticle.pictureUrl}' /></div>
  </#if>
  <div style='padding:10px 40px;font-size:14px;line-height:150%;'>
    ${contentSpaceArticle.content!}
  </div>
 </div>
</div> 
</div>
<div style="clear: both;"></div>   
<#include "/WEB-INF/unitspage/unit_footer.ftl">
</body>
</html>