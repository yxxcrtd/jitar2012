<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 视频频道</title>
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
</head>
<body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<div class="secMain border">
<h3 class="h3Head textIn">${t!?html}</h3>
<div class="secVideo clearfix" style="min-height:240px">
<ul class="secVideoList">
<#if video_list??>
  <#if video_list.size() &gt; 0 >  
  <#list video_list as p>
        <li>
            <a title="${p.title}" href="${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}"><img border=0 src="${p.flvThumbNailHref!}" width="140" height="90" onerror="this.src='${ContextPath}images/default_video.png'"/></a>
            <a title="${p.title}" href="${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}">${Util.getCountedWords(p.title!?html,8,1)}</a></a>
            <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}" class="secVideoPlay"></a>
            <div class="secVideoBg"></div>
        </li>
  </#list>
  </#if>
</#if>
</ul>
</div>
<#include '/WEB-INF/ftl2/pager.ftl'>
</div>
<#include '/WEB-INF/ftl2/footer.ftl'>
<!--[if IE 6]>
<script src="js/ie6.js" type="text/javascript"></script>
<script src="js/DD_belatedPNG.js" type="text/javascript"></script>
<script type="text/javascript">
    DD_belatedPNG.fix('.topSearch,.login a,.videoPlay,.videoPlayBg,.tx,.liW,.liX,.liP,.leftNavS li,.leftNavIcon,.leftNavIconH,.folder,.liFolder,.secList li,.secVideoPlay');
</script>
<![endif]-->
</body>
</html>
