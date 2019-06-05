<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 活动</title>
<#include "/WEB-INF/ftl2/common/favicon.ftl" />
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/ajaxGetContent.js" type="text/javascript"></script>
<script>
var ownerType = ""; //左边tree选择
var k = ""; //关键字
var filter = ""; //查询类别选项
var showType = "${showType!'all'}"; //标签类别
var urlPattern = "actions.action?cmd=ajax&ownerType=" + ownerType + "&showType=" + showType + "&filter=" + filter + "&page={page}&k=" + encodeURIComponent(k) + "&t=" + (new Date()).getTime();

//每次重新设置参数值
function getUrl(){
 urlPattern = "actions.action?cmd=ajax&ownerType=" + ownerType + "&showType=" + showType + "&filter=" + filter + "&page={page}&k=" + encodeURIComponent(k) + "&t=" + (new Date()).getTime();
}

//页面首次加载，绑定一些事件
$(document).ready(function () {
    //搜索部分事件
    $(".secSearchSelect a").each(function () {
        $(this).bind("click", function () { filter = $(this).attr("filter"); });
    });
    $(".secSearchBtn").bind("click", function () {
        k = $("#k").val();
        getUrl();
        ajaxGetContent(1);
    });

    //左边Tree事件
    $(".leftNav a").each(function () {
        $(this).bind("click", function () {
            $("#k").val("");
            $("#secInput2").val("");
            ownerType = $(this).attr("ownerType");
            getUrl();
            ajaxGetContent(1);
        });
    });

    //tab 分类事件
    $(".sectionTitle").each(function () {
        $(this).bind("click", function () {
            $(".sectionTitle").each(function () { $(this).removeClass("active"); });
            $(this).addClass("active");
            showType = $(this).attr("showType");
            getUrl();
            ajaxGetContent(1);
        });
    });

    //加载第一页数据
     ajaxGetContent(1);
});
</script>
</head>
<body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<!--活动正文 Start-->
<div class="main mt25 clearfix">
    <div class="left">
    <#include '/WEB-INF/ftl2/action/tree.ftl'>
    </div>
    <!--页面右栏 Start-->
    <div class="right">
    <#include '/WEB-INF/ftl2/action/search.ftl'>
        <!--列表内容 Start-->
        <div class="secRightW mt3 border">
            <h3 class="h3Head articleH3">
                <a href="javascript:void(0);" class="sectionTitle<#if showType="all"> active</#if>" showType="all">全部活动<span></span></a>
                <a href="javascript:void(0);" class="sectionTitle<#if showType="running"> active</#if>" showType="running">正在报名的活动<span></span></a>
                <a href="javascript:void(0);" class="sectionTitle<#if showType="finish"> active</#if>" showType="finish">已经结束的活动<span></span></a>
            </h3>
            <!--循环-->
            <div class="listCont" id="showContent">
              正在加载数据…………
            </div>
            <!--循环-->
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize6" /></div>
        </div>
        <!--列表内容 End-->
    </div>
    <!--页面右栏 End-->
</div>
<!--活动 End-->
<#include '/WEB-INF/ftl2/footer.ftl'>

<!--[if IE 6]>
<script src="${ContextPath}js/new/DD_belatedPNG.js" type="text/javascript"></script>
<script type="text/javascript">
  DD_belatedPNG.fix('.topSearch,.login a,.videoPlay,.videoPlayBg,.tx,.liW,.liX,.liP,.leftNavS li,.leftNavIcon,.leftNavIconH,.folder,.liFolder');
</script>
<![endif]-->

</body>
</html>
