<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title><#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}index.css" />
  <script src='js/jitar/core.js'></script>  
 </head>
 <body>
<#include 'site_head.ftl'>
<div style='height:8px;font-size:0;'></div>
 <div class='containter'>
 
 <div class='head_nav'><a href='index.action'>首页</a> &gt; <a href='tags.action'>标签中心</a> &gt; ${tag.tagName}</div>
 
<table class='border_table' cellspacing='1' cellpadding='0'>
 <tr>
 <td class='border_table_header' style='border:0'><span class='moredata'><a href='${SiteUrl}showMoreTagContent.action?type=user&amp;tagId=${tag.tagId}'>更多…</a></span>使用<span class='redtext'>${tag.tagName}</span>的所有工作室</td>
 </tr>
 <tr>
 <td style='padding:10px 0 10px 28px;'>			 
	 <#if tag_user_list??>
	<ul>
	  <#list tag_user_list as user>
	<li><a href='${SiteUrl}go.action?loginName=${user.loginName}'>${user.loginName}</a></li>
	  </#list>
	</ul>
	  </#if>
  </td>
 </tr>
 </table>
 
 <div style='padding:5px'></div> 
 <table class='border_table' cellspacing='1' cellpadding='0'>
 <tr>
 <td class='border_table_header' style='border:0'><span class='moredata'><a href='${SiteUrl}showMoreTagContent.action?type=group&amp;tagId=${tag.tagId}'>更多…</a></span>使用<span class='redtext'>${tag.tagName}</span>的所有协作组</td>
 </tr>
 <tr>
 <td style='padding:10px 0 10px 28px;'>			 
  <#if tag_group_list??>
    <ul>
      <#list tag_group_list as group>
        <li><a href='g/${group.groupName}'>${group.groupTitle}</a></li>
      </#list>
    </ul>
  </#if>
  </td>
 </tr>
 </table>
 
 <div style='padding:5px'></div> 
 <table class='border_table' cellspacing='1' cellpadding='0'>
 <tr>
 <td class='border_table_header' style='border:0'><span class='moredata'><a href='${SiteUrl}showMoreTagContent.action?type=article&amp;tagId=${tag.tagId}'>更多…</a></span>使用<span class='redtext'>${tag.tagName}</span>的所有文章</td>
 </tr>
 <tr>
 <td style='padding:10px 0 10px 28px;'>			 
  <#if tag_article_list??>
    <ul>
      <#list tag_article_list as article>
        <li><a href='${SiteUrl}showArticle.action?articleId=${article.articleId}'>${article.title}</a></li>
      </#list>
    </ul>
  </#if>
  </td>
 </tr>
 </table>
 
<div style='padding:5px'></div> 
 <table class='border_table' cellspacing='1' cellpadding='0'>
 <tr>
 <td class='border_table_header' style='border:0'><span class='moredata'><a href='${SiteUrl}showMoreTagContent.action?type=resource&amp;tagId=${tag.tagId}'>更多…</a></span>使用<span class='redtext'>${tag.tagName}</span>的所有资源</td>
 </tr>
 <tr>
 <td style='padding:10px 0 10px 28px;'>			 
  <#if tag_resource_list??>
    <ul>
      <#list tag_resource_list as resource>
        <li><a href='showResource.action?resourceId=${resource.resourceId}'>${resource.title}</a></li>
      </#list>
    </ul>
  </#if>
  </td>
 </tr>
 </table>

<div style='padding:5px'></div> 
 <table class='border_table' cellspacing='1' cellpadding='0'>
 <tr>
 <td class='border_table_header' style='border:0'><span class='moredata'><a href='${SiteUrl}showMoreTagContent.action?type=photo&amp;tagId=${tag.tagId}'>更多…</a></span>使用<span class='redtext'>${tag.tagName}</span>的所有图片</td>
 </tr>
 <tr>
 <td style='padding:10px 0 10px 28px;'>			 
  <#if tag_photo_list??>
    <ul>
      <#list tag_photo_list as photo>
      	<#assign u = Util.userById(photo.userId)>
        <li><a href='${u.loginName}/py/user_photo_show.py?photoId=${photo.photoId}'>${photo.title}</a></li>
      </#list>
    </ul>
  </#if>
  </td>
 </tr>
 </table>
 
<div style='padding:5px'></div> 
 <table class='border_table' cellspacing='1' cellpadding='0'>
 <tr>
 <td class='border_table_header' style='border:0'><span class='moredata'><a href='${SiteUrl}showMoreTagContent.action?type=preparecourse&amp;tagId=${tag.tagId}'>更多…</a></span>使用<span class='redtext'>${tag.tagName}</span>的所有备课</td>
 </tr>
 <tr>
 <td style='padding:10px 0 10px 28px;'>			 
  <#if tag_preparecourse_list??>
    <ul>
      <#list tag_preparecourse_list as pc>
        <li><a href='p/${pc.prepareCourseId}/0/'>${pc.title}</a></li>
      </#list>
    </ul>
  </#if>
  </td>
 </tr>
 </table>
<div style='clear:both'></div>
<#include ('footer.ftl') >
</body>
</html>