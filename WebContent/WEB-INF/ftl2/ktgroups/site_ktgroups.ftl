<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 课题研究</title>
<#include "/WEB-INF/ftl2/common/favicon.ftl" />
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/index.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/gradesubject.js" type="text/javascript"></script>
</head>
<body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<!--协作组 Start-->
<div class="secMain mt25 clearfix">
    <div class="left">
        <#include 'kt_placard.ftl'>
        <#include 'kt_article.ftl'>
        <#include 'kt_resource.ftl'>
        <#include 'kt_photo.ftl'>
        <#include 'kt_video.ftl'>        
    </div>
    <!--页面右栏 Start-->
    <div class="right">
        <#include 'search.ftl'>
        
        <!--列表内容 Start-->
        <div class="secRightW mt3 border">
            <h3 class="h3Head textIn">课题列表</h3>
            <!--循环 Start-->
            <div class="cooperation clearfix" style="min-height:380px">
            <#if group_list?? ><#include 'kt_group_list.ftl'></#if>
            <#include '/WEB-INF/ftl2/pager.ftl'>
            </div>
            <!--循环 End-->
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize6" /></div>
        </div>
        <!--列表内容 End-->
    </div>
    <!--页面右栏 End-->
</div>
<!--协作组 End-->
<!--工作室 End-->
<#include '/WEB-INF/ftl2/footer.ftl'>
<script>
$(function(){
  //绑定分类点击
  $("#secSelectWrap3 li").each(function(){$(this).bind("click",function(){$("#secInput3").attr("searchtype",$(this).attr("searchtype"));});});
  //搜索的绑定处理放到search.ftl中 
});
</script>
<!--[if IE 6]>
<script src="${ContextPath}js/new/ie6.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/DD_belatedPNG.js" type="text/javascript"></script>
<script type="text/javascript">
    DD_belatedPNG.fix('.topSearch,.login a,.videoPlay,.videoPlayBg,.tx,.liW,.liX,.liP,.leftNavS li,.leftNavIcon,.leftNavIconH,.folder,.liFolder,.secList li,.secVideoPlay');
</script>
<![endif]-->
</body>
</html>
