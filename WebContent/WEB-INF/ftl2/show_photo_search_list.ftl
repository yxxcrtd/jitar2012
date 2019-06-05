<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 图片搜索</title>
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
</head>
<body>
<#include "site_head.ftl" />
<#if photo_list??&&(photo_list?size &gt; 0)>
<div class="main clearfix mt3">
<div class="imageSecond border">
<h3 class="h3Head textIn">图片搜索：${k!?html}</h3>
<div class="imageShow clearfix">
<div>
<#assign row = 4 />
<#assign col = 4 />
<#list 0..row as r>
	<#list 0..col-1 as c>
		<#if photo_list[r * col + c]??>
		<#assign p = photo_list[r * col + c] />
		<div style="float:left;margin:5px 5px;">
      <a  href='photos.action?cmd=detail&photoId=${p.photoId}' >
      	<img src=${Util.GetimgUrl(Util.url(p.href!),'230x136')} width="230" height="136"  onerror="this.src='images/s8_default.jpg'"/>
      </a>
      </div>
		</#if>
	</#list>
</#list>
</div>
</div>
<div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize9" /></div>
</div>
<#include 'pager.ftl'>
</div>
</#if>
<script src="${ContextPath}js/new/show_picture.js" type="text/javascript"></script>
<script src="${ContextPath}js/jquery.sgallery.js" type="text/javascript"></script>
<#include 'footer.ftl'>
<!--[if IE 6]>
<script src="${ContextPath}js/new/DD_belatedPNG.js" type="text/javascript"></script>
<script type="text/javascript">
  DD_belatedPNG.fix('.topSearch,.login a,.videoPlay,.videoPlayBg,.tx,.liW,.liX,.liP,.leftNavS li,.leftNavIcon,.leftNavIconH,.folder,.liFolder');
</script>
<![endif]-->

</body>
</html>