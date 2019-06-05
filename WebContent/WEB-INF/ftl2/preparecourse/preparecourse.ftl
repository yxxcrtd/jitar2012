<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 集体备课</title>
<#include "/WEB-INF/ftl2/common/favicon.ftl" />
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/gradesubject.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/ajaxGetContent.js" type="text/javascript"></script>
</head>
<body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<!--资源正文 Start-->
<div class="secMain mt25 clearfix">
    <div class="left">
    <#include '/WEB-INF/ftl2/preparecourse/tree.ftl'>
    </div>
    <!--页面右栏 Start-->
    <div class="right">
        <#include '/WEB-INF/ftl2/preparecourse/search.ftl'>
        <!--列表内容 Start-->
        <div class="secRightW mt3 border">
            <h3 class="h3Head articleH3">
              <a showType="recommend" href="javascript:void(0);" class="sectionTitle<#if type="recommend"> active</#if>">推荐的集备<span></span></a>
              <a showType="finished" href="javascript:void(0);" class="sectionTitle<#if type="finished"> active</#if>">已经结束的集备<span></span></a>
              <a showType="running" href="javascript:void(0);" class="sectionTitle<#if type="running"> active</#if>">正在进行的集备<span></span></a>
              <a showType="new" href="javascript:void(0);" class="sectionTitle<#if type="new"> active</#if>">计划进行的集备<span></span></a>
            </h3>
            <!--循环-->
            <div class="listCont" style="min-height:481px" id="showContent">
            正在加载数据…………
            </div>
            <!--循环-->
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize6" /></div>
        </div>
        <!--列表内容 End-->
    </div>
    <!--页面右栏 End-->
</div>
<!--资源正文 End-->
<!--公共尾部 Start-->
<#include '/WEB-INF/ftl2/footer.ftl'>

<script>
var categoryId = ${categoryId!0}; //分类
var k = "${k!}"; //关键字
var gradeId = ${gradeId!0}; //学段
var subjectId = ${subjectId!0}; //学科
var type="${type?default('new')}"; //标签类别

var urlPattern = "prepareCourse.action?cmd=ajax&categoryId=" + categoryId + "&subjectId=" + subjectId + "&gradeId=" + gradeId + "&type=" + type + "&page={page}&k=" + encodeURIComponent(k) + "&t=" + (new Date()).getTime();

//每次重新设置参数值
function getUrl(){
 urlPattern = "prepareCourse.action?cmd=ajax&categoryId=" + categoryId + "&subjectId=" + subjectId + "&gradeId=" + gradeId + "&type=" + type + "&page={page}&k=" + encodeURIComponent(k) + "&t=" + (new Date()).getTime();
}

//初始化
$(function(){

   //tree绑定，不进行关键字搜索
   $(".leftNav a").each(function(){
   $(this).bind("click",function(){
   k="";
   $("#k").val("");
   gradeId=$(this).attr("metaGradeId");
   subjectId=$(this).attr("metaSubjectId");
   //categoryId = $(this).attr("categoryId");
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
  ajaxGetContent(1);
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

<!--[if IE 6]>
<script src="${ContextPath}js/new/DD_belatedPNG.js" type="text/javascript"></script>
<script type="text/javascript">
  DD_belatedPNG.fix('.topSearch,.login a,.videoPlay,.videoPlayBg,.tx,.liW,.liX,.liP,.leftNavS li,.leftNavIcon,.leftNavIconH,.folder,.liFolder');
</script>
<![endif]-->
</body>
</html>
