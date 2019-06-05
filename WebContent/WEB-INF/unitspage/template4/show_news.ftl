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

<div class='head_nav'>
  <a href='index.py?unitId=${unit.unitId}'>首页</a> &gt; 内容
 </div>

<div style='height:8px;font-size:0;'></div>
 
<div class='containter'>
 <div style='padding:0 20px'>
  <div style='padding-top:10px;text-align:center;font-size:16px;font-weight:bold;'>
    ${unitNews.title!?html}</div>
  <div style='text-align:center;padding:10px;'>发布时间：${unitNews.createDate}</div>
  <#if unitNews?? && unitNews.picture??>
  <div style='text-align:center;padding:20px 0'><img id="newsImg" src='${unitNews.picture}' /></div>
  </#if>
  <div style='padding:10px 40px;font-size:14px;line-height:150%;'>
    ${unitNews.content!}
  </div>
 </div>
</div>

<div style="clear: both;"></div>
<script>

function resizeNewsImage(){
 var img = document.getElementById("newsImg");
 if(img.width>600){
  img.width=600;
 }
}

function addLoadEvent(func) {
  var oldonload = window.onload;
  if (typeof window.onload != 'function') {
    window.onload = func;
  } else {
    window.onload = function() {
      if (oldonload) {
        oldonload();
      }
      func();
    }
  }
}
addLoadEvent(resizeNewsImage);

</script>
<#include "/WEB-INF/unitspage/unit_footer.ftl">
</body>
</html>