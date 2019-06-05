<!doctype html><html itemscope="itemscope" itemtype="http://schema.org/WebPage">
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /><meta itemprop="image" content="${SiteUrl}images/favicon.png">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0">  
<meta name="publishtime" content="${Util.today()?string("yyyy-MM-dd HH:mm:ss")}" />
<title>教研平台首页</title>
</head>

<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css" />
<link rel="stylesheet" href="${SiteThemeUrl}css/index.css" type="text/css" />
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript" charset="utf-8"></script>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript" charset="utf-8"></script>

<!--[if IE 6]>
<script src="${SiteUrl}js/jitar/ie6.js" type="text/javascript"></script>
<script src="${SiteUrl}js/jitar/DD_belatedPNG.js" type="text/javascript"></script>
<script type="text/javascript">
	DD_belatedPNG.fix('.login a,.videoPlay,.videoPlayBg,.tx');
</script>
<![endif]-->
<body>
<noscript>
<h1>本网站需要启用 JavaScript</h1>
<p>当前浏览器不支持 JavaScript 或阻止了脚本。<br/><br/>若要查看您的浏览器是否支持 JavaScript 或允许使用脚本，请查看浏览器联机帮助。</p>
</noscript>
<!--公用头部 Start-->
<#include 'site_head.ftl'>
<!--公用头部 End-->
<div class="main mt25 clearfix">
  <div class="left">
	<!-- 教研新闻 教研动态 -->
	<#include 'mengv1/index/jitar_news.ftl'/>
	<!--end-->
	<!--文章 start-->
	<#include 'mengv1/index/article_tabs.ftl'/>
	<!--文章 End-->
	<!--工作室-->
	<#include 'mengv1/index/user_show.ftl'/>
	<!--工作室end-->
	<div class="right">
	    <!--公告-->
	  	<#include 'mengv1/index/placard_show.ftl'/>
	  	<!--end-->
	  	<!--资源-->
	  	<#include 'mengv1/index/resource_tabs.ftl' >
	  	<!--end-->
	  	<!--最新活动-->
	  	<#include 'mengv1/index/actions_show.ftl' >
	  	<!--end-->
  	</div> 
  </div>
</div>
<!--热门图片-->
<#include 'v2/index/photo_show.ftl' >
<!--end-->
<!--教研专题-->
<div class="main clearfix mt10">
   <div class="left">
	  <#include 'mengv1/index/specialsubject_show.ftl' >
	  <#include 'mengv1/index/famous_teacher.ftl'><!--这里是 之前名师工作室,学科带头人,研修之星,教研员的合并-->
	  <#include 'mengv1/index/video_show.ftl' ><!--最新视频,最热视频-->
   </div>
   <div class="right">
	     <#include 'mengv1/index/group_show.ftl' ><!--推荐协作组/优秀团队-->
	     <#include 'mengv1/index/school_show.ftl' ><!--活跃机构-->
	     <#include 'mengv1/index/site_stat.ftl' ><!--站点统计-->
   </div>
</div>
<#include "footer.ftl">
<!--end-->