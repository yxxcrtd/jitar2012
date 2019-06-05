<!doctype html>
<html>
 <head>
  <meta charset="utf-8">
  <title>${Page_Title!?html}</title>
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
<div class="main rightWidth border">
  <h3 class="h3Head textIn"><a class="more"></a>${Page_Title!?html}</h3>
  <div class="listCont" id="evalu_list">
   <#if article_list?? && article_list?size &gt; 0>
       <ul>
          <#list article_list as article>
          <li style="line-height:35px;" <#if (article_index%2)!=0>class='liBg'</#if> ><div style="float:right;width:100px">${article.createDate?string('yyyy-MM-dd')}</div>
              <#if article.pictureUrl?? && article.pictureUrl != ""><span style="color:#f00">[图]</span></#if>
              <a href='${ContextPath}showContent.action?articleId=${article.contentSpaceArticleId}' target='_blank'>
                ${Util.getCountedWords(article.title!?html,60)}
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