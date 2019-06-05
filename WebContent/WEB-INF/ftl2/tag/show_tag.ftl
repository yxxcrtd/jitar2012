<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 标签</title>
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
 </head>
 <body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<div class="secMain border" id="Main">
    <h3 class="h3Head textIn"><a href='tags.action'>标签中心</a> &gt; ${tag.tagName}</h3>
    <div class="secVideo clearfix">
    <div class="listContTitle">
        <div class="listContTitleBg">
                <a href='${SiteUrl}showMoreTagContent.action?type=user&amp;tagId=${tag.tagId}' class="more">更多…</a>使用<span class='redtext'>${tag.tagName}</span>的所有工作室
        </div>                
	 
	 <#if tag_user_list??>
	<ul>
	  <#list tag_user_list as user>
	<li><a href='${SiteUrl}go.action?loginName=${user.loginName}'>${user.loginName}</a></li>
	  </#list>
	</ul>
	  </#if>
    </div>
    
    <div class="listContTitle">
        <div class="listContTitleBg">
        <a href='${SiteUrl}showMoreTagContent.action?type=group&amp;tagId=${tag.tagId}' class="more">更多…</a>使用<span class='redtext'>${tag.tagName}</span>的所有协作组
        </div>
		 
      <#if tag_group_list??>
        <ul>
          <#list tag_group_list as group>
            <li><a href='g/${group.groupName}'>${group.groupTitle}</a></li>
          </#list>
        </ul>
      </#if>
    </div>
    
     <div class="listContTitle">
        <div class="listContTitleBg">
        <a href='${SiteUrl}showMoreTagContent.action?type=article&amp;tagId=${tag.tagId}' class="more">更多…</a>使用<span class='redtext'>${tag.tagName}</span>的所有文章
        </div>
      <#if tag_article_list??>
        <ul>
          <#list tag_article_list as article>
            <li><a href='${SiteUrl}showArticle.action?articleId=${article.articleId}'>${article.title}</a></li>
          </#list>
        </ul>
      </#if>
    </div>

     <div class="listContTitle">
        <div class="listContTitleBg">
        <a href='${SiteUrl}showMoreTagContent.action?type=resource&amp;tagId=${tag.tagId}' class="more">更多…</a>使用<span class='redtext'>${tag.tagName}</span>的所有资源
        </div>
	 
  <#if tag_resource_list??>
    <ul>
      <#list tag_resource_list as resource>
        <li><a href='showResource.action?resourceId=${resource.resourceId}'>${resource.title}</a></li>
      </#list>
    </ul>
  </#if>
</div>

     <div class="listContTitle">
        <div class="listContTitleBg">
        <a href='${SiteUrl}showMoreTagContent.action?type=photo&amp;tagId=${tag.tagId}' class="more">更多…</a>使用<span class='redtext'>${tag.tagName}</span>的所有图片
        </div>
  <#if tag_photo_list??>
    <ul>
      <#list tag_photo_list as photo>
      	<#assign u = Util.userById(photo.userId)>
        <!--<li><a href='${u.loginName}/py/user_photo_show.py?photoId=${photo.photoId}'>${photo.title}</a></li>-->
        <li><a href="photos.action?cmd=detail&photoId=${photo.photoId}">${photo.title}</a></li>
      </#list>
    </ul>
  </#if>
</div>

     <div class="listContTitle">
        <div class="listContTitleBg">
        <a href='${SiteUrl}showMoreTagContent.action?type=video&amp;tagId=${tag.tagId}' class="more">更多…</a>使用<span class='redtext'>${tag.tagName}</span>的所有视频
        </div>
  <#if tag_video_list??>
    <ul>
      <#list tag_video_list as video>
        <#assign u = Util.userById(video.userId)>
        <li><a href="manage/video.action?cmd=show&videoId=${video.videoId}">${video.title}</a></li>
      </#list>
    </ul>
  </#if>
</div>

     <div class="listContTitle">
        <div class="listContTitleBg">
        <a href='${SiteUrl}showMoreTagContent.action?type=preparecourse&amp;tagId=${tag.tagId}' class="more">更多…</a>使用<span class='redtext'>${tag.tagName}</span>的所有备课
        </div>
		 
  <#if tag_preparecourse_list??>
    <ul>
      <#list tag_preparecourse_list as pc>
        <li><a href='p/${pc.prepareCourseId}/0/'>${pc.title}</a></li>
      </#list>
    </ul>
  </#if>
</div>
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