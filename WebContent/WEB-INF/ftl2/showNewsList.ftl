<!doctype html>
<html>
 <head>
  <meta charset="utf-8">
  <title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 教研动态</title>
	<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
	<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
	<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/gradesubject_blogs.js" type="text/javascript"></script>
</head>
<body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<div style='height:20px;font-size:0;'></div>
<div class="rightWidth border" style="width:1000px;margin:0 auto">
  <h3 class="h3Head textIn"><a class="more"></a>教研动态</h3>
  <div class="listCont" id="evalu_list">
   <#if news_list??>
       <ul>
          <#list news_list as news>
          <li style="line-height:35px;" <#if (news_index%2)!=0>class='liBg'</#if> ><div style="float:right;width:100px">${news.createDate?string('yyyy-MM-dd')}</div>
              <#if news.picture?? && news.picture != ""><span style="color:#f00">[图]</span></#if>
              <a href='showNews.action?newsId=${news.newsId}' target='_blank'>
              	${Util.getCountedWords(news.title!?html,60)}
              </a>
          </li> 
        </#list> 
      </ul>
   </#if>
  </div>
  <#include '/WEB-INF/ftl2/pager.ftl'>
  <div style='height:15px;font-size:0;' style="background-color:white;"></div>
  <div class="imgShadow"><img  src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize9" /></div>
</div>
<#include '/WEB-INF/ftl2/footer.ftl'>
</body>
</html>