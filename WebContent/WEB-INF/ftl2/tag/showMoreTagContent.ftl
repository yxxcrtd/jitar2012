<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 标签中心</title>
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script> 
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
 </head>
 <body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<div class="secMain border">
    <h3 class="h3Head textIn"><a href='tags.action'>标签中心</a> &gt; <a href='showTag.action?tagName=${tag.tagName}'>${tag.tagName}</a></h3>
    <div class="secVideo clearfix">
<#if tag_user_list?? >
    <div class="listContTitle">
        <div class="listContTitleBg">
                    使用<span class='redtext'>${tag.tagName}</span>的所有工作室
       </div>
    	<ul>
    	  <#list tag_user_list as user>
    		<li><a href='${SiteUrl}go.action?loginName=${user.loginName}'>${user.loginName}</a></li>
    	  </#list>
    	</ul>
    </div> 
 <div style='padding:5px'></div>
 </#if>
 
 <#if tag_group_list?? >
     <div class="listContTitle">
        <div class="listContTitleBg">
                    使用<span class='redtext'>${tag.tagName}</span>的所有协作组
        </div>
        <ul>
          <#list tag_group_list as group>
            <li><a href='g/${group.groupName}'>${group.groupTitle}</a></li>
          </#list>
        </ul>
      </div>
 <div style='padding:5px'></div>
</#if>

<#if tag_article_list??>
      <div class="listContTitle">
        <div class="listContTitleBg">
                    使用<span class='redtext'>${tag.tagName}</span>的所有文章
        </div>
        <ul>
          <#list tag_article_list as article>
            <li><a href='${SiteUrl}showArticle.action?articleId=${article.articleId}'>${article.title}</a></li>
          </#list>
        </ul>
      </div>
<div style='padding:5px'></div> 
</#if>

<#if tag_resource_list??>
      <div class="listContTitle">
        <div class="listContTitleBg">
                        使用<span class='redtext'>${tag.tagName}</span>的所有资源
        </div>
        <ul>
          <#list tag_resource_list as resource>
            <li><a href='showResource.action?resourceId=${resource.resourceId}'>${resource.title}</a></li>
          </#list>
        </ul>
    </div>
 
<div style='padding:5px'></div> 
</#if>

<#if tag_photo_list??>
      <div class="listContTitle">
        <div class="listContTitleBg">
                    使用<span class='redtext'>${tag.tagName}</span>的所有图片
        </div>
    <ul>
      <#list tag_photo_list as photo>
      	<#assign u = Util.userById(photo.userId)>
        <li><a href='${u.loginName}/py/user_photo_show.py?photoId=${photo.photoId}'>${photo.title}</a></li>
      </#list>
    </ul>
 </div>
<div style='padding:5px'></div>
</#if>

<#if tag_preparecourse_list??>
      <div class="listContTitle">
        <div class="listContTitleBg">
                    使用<span class='redtext'>${tag.tagName}</span>的所有备课
        </div>
    <ul>
      <#list tag_preparecourse_list as pc>
        <li><a href='p/${pc.prepareCourseId}/0/'>${pc.title}</a></li>
      </#list>
    </ul>
 </div>
</#if>
<#include '/WEB-INF/ftl2/pager.ftl'>

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