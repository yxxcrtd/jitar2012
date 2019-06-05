<!doctype html>
<html itemscope="itemscope" itemtype="http://schema.org/WebPage">
<head>
<meta charset="utf-8">
<meta itemprop="image" content="${ContextPath}images/favicon.png">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0">  
<meta name="publishtime" content="${Util.today()?string("yyyy-MM-dd HH:mm:ss")}" />
<title><#if productName??>${productName!}<#else><#include '/WEB-INF/ftl2/common/site_title.ftl' /></#if></title>
<#include "/WEB-INF/ftl2/common/favicon.ftl" />
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css" />
<link rel="stylesheet" href="${SiteThemeUrl}css/index.css" type="text/css" />
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript" charset="utf-8"></script>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<noscript>
<h1>本网站需要启用 JavaScript</h1>
<p>当前浏览器不支持 JavaScript 或阻止了脚本。<br/><br/>若要查看您的浏览器是否支持 JavaScript 或允许使用脚本，请查看浏览器联机帮助。</p>
</noscript>
<!--公用头部 Start-->
<#include 'site_head.ftl'>
<!--公用头部 End-->

<#include "/WEB-INF/ftl2/index/custom_part_macro.ftl" />

<div class="main mt25 clearfix">
  <#if show_custom_part1??><@showCustomPart partList = show_custom_part1 /></#if>
  <div class="left">
	<!-- 教研新闻 教研动态 -->
	<#include 'index/jitar_news.ftl'/>
	<!--end-->
	<!--文章 start-->
	<#include 'index/article_tabs.ftl'/>
	<!--文章 End-->
	<!--工作室-->
	<#include 'index/user_show.ftl'/>
	<!--工作室end-->
	<div class="right">
	    <!--公告-->
	  	<#include 'index/placard_show.ftl'/>
	  	<!--end-->
	  	<!--资源-->
	  	<#include 'index/resource_tabs.ftl' >
	  	<!--end-->
	  	<!--最新活动-->
	  	<#include 'index/actions_show.ftl' >
	  	<!--end-->
  	</div> 
  </div>
</div>

<#if show_custom_part2??><@showCustomPart partList = show_custom_part2 /></#if>

<!--热门图片-->
<#include 'index/photo_show.ftl' >
<!--end-->
<#if show_custom_part3??><@showCustomPart partList = show_custom_part3 /></#if>

<!--教研专题-->
<div class="main clearfix mt10">
   <div class="left">
	   <#include 'index/specialsubject_show.ftl' >
	 </div>
	 <div class="right">
	   <#include 'index/group_show.ftl' ><!--推荐协作组/优秀团队-->
	 </div>
	 <#if show_custom_part4??><@showCustomPart partList = show_custom_part4 /></#if>
	 <div class="left">
	  <#include 'index/famous_teacher.ftl'><!--这里是 之前名师工作室,学科带头人,研修之星,教研员的合并-->
	  <#include 'index/video_show.ftl' ><!--最新视频,最热视频-->
   </div>
   <div class="right">
	   <#include 'index/school_show.ftl' ><!--活跃机构-->
	   <#include 'index/site_stat.ftl' ><!--站点统计-->
   </div>
</div>
<#if show_custom_part5??><@showCustomPart partList = show_custom_part5 /></#if>
<#include "/WEB-INF/ftl2/footer.ftl">
<#include "/WEB-INF/ftl2/common/ie6.ftl">
<script src="${ContextPath}js/new/imgScroll.js"></script>
<!--end-->

<!-- 辅助功能 -->
<#if returnFlagMsg?? && returnFlagMsg != "" >
<script type="text/javascript">
 $(function(){alert("${returnFlagMsg!?js_string}");}); 
</script>
</#if>
<script>
function ExpandCollapseCustomPart(eleId)
{
  var ele = document.getElementById(eleId);
  if(ele.getAttribute("myHeight") == "")
    {
      ele.setAttribute('myHeight',ele.parentNode.style.height);
      ele.parentNode.style.height = ele.offsetHeight + 'px';
    }
  else
    {
      ele.parentNode.style.height = ele.getAttribute("myHeight");
      ele.setAttribute('myHeight',"");
    }
};
</script>
</body>
</html>