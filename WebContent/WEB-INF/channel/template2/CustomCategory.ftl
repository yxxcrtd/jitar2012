<#if list??>
<#if cateItemType[0..12]=='channel_photo'>
    <#assign photo_list = list >
<#elseif cateItemType[0..12]=='channel_video'>
    <#assign video_list = list >
<#elseif cateItemType[0..14]=='channel_article'>
    <#assign NewArticleList = list >
<#elseif cateItemType[0..15]=='channel_resource'>
    <#assign resource_list = list >
</#if>
</#if>
<#if resource_list??>
<ul class="ulist">
<#list resource_list as r>
<li>
<span><a href="${SiteUrl}go.action?loginName=${r.loginName}" title="${r.userTrueName!?html}">${Util.getCountedWords(r.userTrueName!?html,4)}</a> ${r.createDate?string("MM-dd")}</span>
<img src='${Util.iconImage(r.href!)}' border='0' align='absmiddle' /> <a href='${SiteUrl}showResource.action?resourceId=${r.resourceId}' target='_blank' title='${r.title!?html}'>${Util.getCountedWords(r.title!?html,26)}</a></li>
</#list>
</ul>
</#if>

<#if NewArticleList??>
<ul class="ulist">
<#list NewArticleList as article>
<li><span><a href="${SiteUrl}go.action?userId=${article.userId}" title="${article.userTrueName!?html}">${Util.getCountedWords(article.userTrueName!?html,4)}</a> ${article.createDate?string("MM-dd")}</span>
<#if article.typeState == false>[原创]<#else>[转载]</#if> <a href="${SiteUrl}showArticle.action?articleId=${article.articleId}" target='_blank' title='${article.title!?html}'>${Util.getCountedWords(article.title!?html,24)}</a></li>
</#list>
</ul>
</#if>

<#if video_list??>
<ul class="ulist">
<#list video_list as v>
<li>
<span><a href="${SiteUrl}go.action?loginName=${v.loginName}" title="${v.userTrueName!?html}">${Util.getCountedWords(v.userTrueName!?html,4)}</a> ${v.createDate?string("MM-dd")}</span>
<a href='${SiteUrl}manage/video.action?cmd=show&videoId=${v.videoId}' target='_blank' title='${v.title!?html}'>${Util.getCountedWords(v.title!?html,26)}</a></li>
</#list>
</ul>
</#if>

<#if photo_list??>
<script src="${SiteUrl}js/jitar/marquee.js"></script>
<#assign id=Util.uuid()?string?replace("-","")>
<!-- CSS 样式的设置可能会影响显示效果，注意进行调整。 -->
<div id="scroll${id}" style="width:256px;overflow: hidden;text-align:center;">
<table border="0" cellpadding="0" cellspacing="0" id="content${id}" align="center">
<tr>
<#list photo_list as p>
<td>
<#if UserUrlPattern??>
<div style="text-align:center;overflow:hidden;padding:6px;">
<a href='${UserUrlPattern.replace('{loginName}',p.loginName)}photo/${p.photoId}.html'>
<img src="${Util.thumbNails(p.href!)}" border="0" width="${pw}"/>
</a>
<nobr><a style="display:block;padding-top:6px;overflow:hidden;width:${pw}px;" href='${UserUrlPattern.replace('{loginName}',p.loginName)}photo/${p.photoId}.html' title="${p.title!?html}">${p.title}</a></nobr>
</div>
<#else>
<div style="text-align:center;overflow:hidden;padding:6px;">
<a href='${SiteUrl}${p.loginName}/photo/${p.photoId}.html'><img src="${Util.thumbNails(p.href!)}" border="0" width="${pw}" /></a>
<nobr><a style="display:block;padding-top:6px;overflow:hidden;width:${pw}px;" href='${SiteUrl}${p.loginName}/photo/${p.photoId}.html' title="${p.title!?html}">${p.title}</a></nobr>
</div>
</#if>
</td>
</#list>
</tr>
</table>
</div>
<script>
window.setTimeout(function(){new MarqueeScroll("scroll${id}", "content${id}", 1, 10);},1000);
</script>
</#if>
