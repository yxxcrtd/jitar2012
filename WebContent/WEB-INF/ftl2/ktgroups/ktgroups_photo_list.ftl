<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 课题图片</title>
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/gradesubject.js" type="text/javascript"></script>
</head>
<body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<div class="secMain mt25 clearfix">
<!--列表内容 Start-->
<div class="secRightW mt3 border">
    <h3 class="h3Head textIn" style="width:1000px">课题成果-图片</h3>
    <!--循环 Start-->
    <div class="clearfix" style="width:1000px">
        <div class="listCont">
            <ul>
            <li class="listContTitle">
                <div class="listContTitleBg">
                    <span style="width:50%">图片名称</span>
                    <span style="width:30%">所在课题</span>
                    <span style="width:20%">发表日期</span>
                </div>
            </li>    
          <#if photo_list??>
            <#list photo_list as photo>
              <#if photo_index % 2 = 1>
               <li class="liBg" style="height:71px">
              <#else> 
              <li style="height:71px">
              </#if>  
                    <span style="width:50%">
                        <a href='${ContextPath}photos.action?cmd=detail&photoId=${photo.photoId}' target='_blank' title="${photo.title!}">
                        <img src="${Util.GetimgUrl(Util.thumbNails(photo.href!),'70x70')}" alt='${photo.title!?html}' align="left" style="padding-right:6px"/></a>
                        <a href='${ContextPath}photos.action?cmd=detail&photoId=${photo.photoId}' target='_blank' title="${photo.title!}">${Util.drawoffHTML(photo.title, 23)}</a>
                    </span>
                    <span style="width:30%">${photo.groupTitle}</span>
                    <span style="width:20%">${photo.createDate?string('yyyy-MM-dd')}</span>
                </li>
                </#list>
           </#if>     
          </ul>   
          <#include '/WEB-INF/ftl2/pager.ftl'>
    </div>
    <!--循环 End-->
    <div class="imgShadow" style="width:1000px"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize6" style="width:1000px" /></div>
</div>
<!--列表内容 End-->
</div>
</div>
<#include '/WEB-INF/ftl2/footer.ftl'>
<!--[if IE 6]>
<script src="${ContextPath}js/new/ie6.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/DD_belatedPNG.js" type="text/javascript"></script>
<script type="text/javascript">
 DD_belatedPNG.fix('.topSearch,.login a,.videoPlay,.videoPlayBg,.tx,.liW,.liX,.liP,.leftNavS li,.leftNavIcon,.leftNavIconH,.folder,.liFolder,.secList li,.secVideoPlay');
</script>
<![endif]-->
</body>
</html>