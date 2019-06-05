<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 视频教研</title>
<#include "/WEB-INF/ftl2/common/favicon.ftl" />
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/ajaxGetContentVideoMeeting.js" type="text/javascript"></script>
<script>
var ownerType = ""; //左边tree选择
var k = ""; //关键字
var filter = ""; //查询类别选项
var showType = "${showType!'ready'}"; //标签类别
var urlPattern = "sitevideomeeting.action?cmd=ajax&ownerType=" + ownerType + "&showType=" + showType + "&filter=" + filter + "&page={page}&k=" + encodeURIComponent(k) + "&t=" + (new Date()).getTime();

//每次重新设置参数值
function getUrl(){
 urlPattern = "sitevideomeeting.action?cmd=ajax&ownerType=" + ownerType + "&showType=" + showType + "&filter=" + filter + "&page={page}&k=" + encodeURIComponent(k) + "&t=" + (new Date()).getTime();
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

function confirm_delete() {
  return window.confirm('你是否确定删除?');
}

</script>
</head>
<body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<!--活动正文 Start-->
<div class="main mt25 clearfix">
    <#include '/WEB-INF/ftl2/videomeeting/search.ftl'>
        <!--列表内容 Start-->
        <div class="secContentW mt3 border">
            <h3 class="h3Head articleH3">
                <a href="javascript:void(0);" class="sectionTitle<#if showType="ready"> active</#if>" showType="ready">即将开始<span></span></a>
                <a href="javascript:void(0);" class="sectionTitle<#if showType="running"> active</#if>" showType="running">正在进行<span></span></a>
                <a href="javascript:void(0);" class="sectionTitle<#if showType="finish"> active</#if>" showType="finish">已经结束<span></span></a>
                <#if bManage>
                    <a href="sitevideomeeting.action?cmd=add" class="initiate">&nbsp;&nbsp;新增视频教研</a>
                </#if>
        <div style="float:right;display:inline;">
        </div>
            </h3>
            <!--循环-->
            <div class="listCont" id="xx">
                <ul>
                    <li class="listContTitle">
                        <div class="listContTitleBg">
                            <span style="width:600px">会议名称</span>
                            <span style="width:100px">开始时间</span>
                            <span style="width:100px">结束时间</span>
                            <span style="width:120px">操作</span>
                        </div>
                    </li>
                </ul>
            </div>
            <!--循环-->
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize9" /></div>
        </div>
        <!--列表内容 End-->
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
