<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 课题文章</title>
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
    <h3 class="h3Head textIn" style="width:1000px">课题成果-文章</h3>
    <!--循环 Start-->
    <div class="clearfix" style="width:1000px">
        <div class="listCont">
            <ul>
            <li class="listContTitle">
                <div class="listContTitleBg">
                    <span style="width:50%">文章名称</span>
                    <span style="width:30%">所在课题</span>
                    <span style="width:20%">发表日期</span>
                </div>
            </li>    
          <#if article_list??>
            <#list article_list as article>
              <#if article_index % 2 = 1>
               <li class="liBg">
              <#else> 
              <li>
              </#if>  
                    <span style="width:50%">
                    	<a href='${SiteUrl}showArticle.action?articleId=${article.articleId}' target='_blank' title="${article.title!}">
                    		${Util.drawoffHTML(article.title, 23)}
                    	</a>
                    </span>
                    <span style="width:30%">${article.groupTitle}</span>
                    <span style="width:20%">${article.createDate?string('yyyy-MM-dd')}</span>
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