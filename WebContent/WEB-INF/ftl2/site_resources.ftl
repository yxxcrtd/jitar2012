<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 资源</title>
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
var categoryId = 0; //分类
var k = "${k!""}"; //关键字
var gradeId = 0; //学段
var subjectId = 0; //学科

    $(document).ready(function(){
        getResourceList("${subjectId!}","${gradeId!}");
          categoryId  = ${categoryId!0} ;
          //绑定分类点击
          $("#secSelectWrap3 li a").each(function(){$(this).bind("click",function(){$("#secInput3").attr("categoryId",$(this).attr("categoryId"));});});
          
          //搜索绑定 
          $(".secSearchBtn").bind("click",function(){ 
          k=$("#k").val();
          gradeId=$("#secInput1").attr("gradeId");
          subjectId=$("#secInput2").attr("subjectId");
          categoryId=$("#secInput3").attr("categoryId");
          ajaxGetContent(1);
          });
                  
    });

    function getResourceList(subId,graId){
        subjectId = subId;
        gradeId = graId;
        ajaxGetContent(1);
    }
    
var urlPattern="resources.action?cmd=ajax&type=${type!}&subjectId="+subjectId+"&categoryId="+categoryId+"&gradeId="+gradeId+"&page={page}&k="+encodeURIComponent(k)+"&t=" + (new Date()).getTime();

//每次重新设置参数值
function getUrl(){
 urlPattern="resources.action?cmd=ajax&type=${type!}&subjectId="+subjectId+"&categoryId="+categoryId+"&gradeId="+gradeId+"&page={page}&k="+encodeURIComponent(k)+"&t=" + (new Date()).getTime();
}

</script>
</head>
<body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<!--正文 Start-->
<div class="main mt25 clearfix">
    <div class="left">
    <#include '/WEB-INF/ftl2/resource/tree.ftl'>
    <#include '/WEB-INF/ftl2/resource/rank.ftl'>
    </div>
    <!--页面右栏 Start-->
    <div class="right">
    <#include '/WEB-INF/ftl2/resource/search.ftl'>
        <!--列表内容 Start-->
        <div class="secRightW mt3 border">
            <h3 class="h3Head articleH3">
                <a href="resources.action?type=new&categoryId=${(category!).categoryId!}" class="sectionTitle${(type == 'new')?string(' active','') }">最新发布<span></span></a>
                <a href="resources.action?type=rcmd&categoryId=${(category!).categoryId!}" class="sectionTitle${(type == 'rcmd')?string(' active','') }">编辑推荐<span></span></a>
                <a href="resources.action?type=hot&categoryId=${(category!).categoryId!}" class="sectionTitle${(type == 'hot')?string(' active','') }">最高人气<span></span></a>
                <a href="resources.action?type=cmt&categoryId=${(category!).categoryId!}" class="sectionTitle${(type == 'cmt')?string(' active','') }">评论最多<span></span></a>
            </h3>
            <!--循环-->
            <div class="listCont" style="min-height:823px" id="showContent">
              正在加载数据…………
            </div>
            <!--循环-->
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize6" /></div>
        </div>
        <!--列表内容 End-->
    </div>
    <!--页面右栏 End-->
</div>
<!-- End-->
<#include '/WEB-INF/ftl2/footer.ftl'>

<!--[if IE 6]>
<script src="${ContextPath}js/new/DD_belatedPNG.js" type="text/javascript"></script>
<script type="text/javascript">
  DD_belatedPNG.fix('.topSearch,.login a,.videoPlay,.videoPlayBg,.tx,.liW,.liX,.liP,.leftNavS li,.leftNavIcon,.leftNavIconH,.folder,.liFolder');
</script>
<![endif]-->

</body>
</html>

