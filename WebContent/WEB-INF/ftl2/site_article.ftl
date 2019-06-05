<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 文章</title>
<#include "/WEB-INF/ftl2/common/favicon.ftl" />
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/gradesubject.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/ajaxGetContent.js" type="text/javascript"></script>
<script type="text/javascript">
var categoryId = ${categoryId!0}; //分类
var k = "${k!}"; //关键字
var gradeId = ${gradeId!0}; //学段
var subjectId = ${subjectId!0}; //学科
var type="${type?default('new')}"; //标签类别

var urlPattern = "articles.action?cmd=ajax&categoryId=" + categoryId + "&subjectId=" + subjectId + "&gradeId=" + gradeId + "&type=" + type + "&page={page}&k=" + encodeURIComponent(k) + "&t=" + (new Date()).getTime();

//每次重新设置参数值
function getUrl(){
 urlPattern = "articles.action?cmd=ajax&categoryId=" + categoryId + "&subjectId=" + subjectId + "&gradeId=" + gradeId + "&type=" + type + "&page={page}&k=" + encodeURIComponent(k) + "&t=" + (new Date()).getTime();
}

//初始化
$(function(){
  //tree绑定，不进行关键字搜索
  $(".leftNav a").each(function(){
  $(this).bind("click",function(){
   k="";
   $("#k").val("");
   //重置学科、学段、分类的选择
   $("#secInput1").val("");
   $("#secInput2").val("");
   $("#secInput3").val("");
   gradeId=0;
   subjectId=0;
   categoryId = $(this).attr("categoryId");
   ajaxGetContent(1);
  });
  });
  
  //绑定分类点击
  $("#secSelectWrap3 li a").each(function(){$(this).bind("click",function(){$("#secInput3").attr("categoryId",$(this).attr("categoryId"));});});
  
  //搜索绑定 
  $(".secSearchBtn").bind("click",function(){ 
  k=$("#k").val();;
  gradeId=$("#secInput1").attr("gradeId");
  subjectId=$("#secInput2").attr("subjectId");
  categoryId=$("#secInput3").attr("categoryId");
  //alert("gradeId=" + gradeId + ",subjectId=" + subjectId + ",categoryId=" + categoryId);
  getUrl();
  //alert(urlPattern)
  ajaxGetContent(1);
  //去除树的显示高亮。
  $(".leftNavBg1").css("display","none");
  });
  
  //tab绑定
   $(".sectionTitle").each(function () {
        $(this).bind("click", function () {
            $(".sectionTitle").each(function () { $(this).removeClass("active"); });
            $(this).addClass("active");
            type = $(this).attr("showType");
            ajaxGetContent(1);
        });
    });  
  ajaxGetContent(1);
});
</script>
</head>
<body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<!--工作室正文 Start-->
<div class="main mt25 clearfix">
    <div class="left">
    <#include '/WEB-INF/ftl2/article/tree.ftl'>
    </div>
    <!--页面右栏 Start-->
    <div class="right">
    <#include '/WEB-INF/ftl2/article/search.ftl'>
        <!--列表内容 Start-->
        <div class="secRightW mt3 border">
            <h3 class="h3Head articleH3">
                <a showType="rcmd" href="javascript:void(0);" class="sectionTitle<#if type="rcmd"> active</#if>">编辑推荐<span></span></a>
                <a showType="famous" href="javascript:void(0);" class="sectionTitle<#if type="famous"> active</#if>">名师文章<span></span></a>
                <a showType="new" href="javascript:void(0);" class="sectionTitle<#if type="new"> active</#if>">最新发布<span></span></a>
                <a showType="hot" href="javascript:void(0);" class="sectionTitle<#if type="hot"> active</#if>">点击率<span></span></a>
                <#--<a showType="digg" href="javascript:void(0);" class="sectionTitle<#if type="digg"> active</#if>">按顶排序<span></span></a>
                <a showType="trample" href="javascript:void(0);" class="sectionTitle<#if type="trample"> active</#if>">按踩排序<span></span></a>
                <a showType="star" href="javascript:void(0);" class="sectionTitle<#if type="star"> active</#if>">按星排序<span></span></a>-->
                <a showType="cmt" href="javascript:void(0);" class="sectionTitle<#if type="cmt"> active</#if>">评论最多<span></span></a>
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
<!--工作室 End-->
<#include '/WEB-INF/ftl2/footer.ftl'>

<#include "/WEB-INF/ftl2/common/ie6.ftl">

</body>
</html>
