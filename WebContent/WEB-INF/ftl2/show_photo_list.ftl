<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 分类图片</title>
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
      <h3 class="h3Head textIn">${category.name!?html}分类下的所有图片</h3>
        <div class="imageShow clearfix">
              <div>
                   <#if photo_list?size &lt; 3 || photo_list?size == 3>
                   	<#list photo_list as p>
				                <div style="float:left;margin:5px 5px;">
				                    <a  href='photos.action?cmd=detail&photoId=${p.photoId}' >
				                       <img src=${Util.GetimgUrl(Util.url(p.href!),'230x136')} width="230px" height="136px"  onerror="javascript:this.src='images/s8_default.jpg'"/>
				                       <!--<img src="skin/default/images/demoimg/230_136.jpg" />-->
				                    </a>
				                </div>
				     </#list>
				     
				     <#elseif (photo_list?size%4)==0>
                      <#assign rowCount = (photo_list?size/4?int)?number>
                      <#list 1..rowCount as r>
                        <#assign start = (r*4-4)?number>
                        <#assign end = start+3>
	                      <#list photo_list[start..end] as p>
				                <div style="float:left;margin:5px 5px;">
				                    <a  href='photos.action?cmd=detail&photoId=${p.photoId}'>
				                       <img src=${Util.GetimgUrl(Util.url(p.href!),'230x136')} width="230px" height="136px"  onerror="javascript:this.src='images/s8_default.jpg'"/>
				                       <!--<img src="skin/default/images/demoimg/230_136.jpg" />-->
				                    </a>
				                </div>
			              </#list>
		              </#list>
				 <#else>
				     <#assign rowCount = (photo_list?size/4)?int>
                      <#list 1..rowCount as r>
                        <#assign start = (r*4-4)?number>
                        <#assign end = start+3>
                         <#if photo_list?size &gt; 3>
	                      <#list photo_list[start..end] as p>
				                <div style="float:left;margin:5px 5px;">
				                    <a  href='photos.action?cmd=detail&photoId=${p.photoId}'>
				                       <img src=${Util.GetimgUrl(Util.url(p.href!),'230x136')} width="230px" height="136px"  onerror="javascript:this.src='images/s8_default.jpg'"/>
				                       <!--<img src="skin/default/images/demoimg/230_136.jpg" />-->
				                    </a>
				                </div>
				          </#list>
			            </#if>
		              </#list>
		              
		             <#assign surplus = (photo_list?size%4)?number>
				     <#assign start = (photo_list?size/4)?int?number*4 >
				     <#assign end = start+surplus>
				     <#list photo_list[start..end-1] as p>
				          <div style="float:left;margin:5px 5px;">
			                    <a  href='photos.action?cmd=detail&photoId=${p.photoId}'  >
			                       <img src=${Util.GetimgUrl(Util.url(p.href!),'230x136')} width="230px" height="136px" onerror="javascript:this.src='images/s8_default.jpg'"/>
			                       <!--<img src="skin/default/images/demoimg/230_136.jpg" />-->
			                    </a>
				          </div>
				     </#list>
		              
                   </#if>
				 
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

</html>