<#if artile_list?? >
<ul class='listul'>
<#list artile_list as article>
  <#assign u = Util.userById(article.userId)>
  <li><span style='float:right'><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName}</a> ${article.createDate?string('MM-dd')}</span><a href='${SiteUrl}showArticle.action?articleId=${article.articleId}' target='_blank'>${article.title!?html}</a></li>
 </#list>
</ul>
<div style='text-align:right'>
<a href='${SiteUrl}p/${currentStage.prepareCourseId}/${prepareCourseStageId}/py/show_preparecourse_article_list.py'>&gt;&gt;全部文章</a>
<#if isMember?? >
<a href='${SiteUrl}manage/article.action?cmd=input&amp;prepareCourseStageId=${prepareCourseStageId}'>发布文章</a>
</#if>
</div>
<#else>
当前流程无文章。
<#if isMember?? >
<div style='text-align:right'>
<a href='${SiteUrl}manage/article.action?cmd=input&amp;prepareCourseStageId=${prepareCourseStageId}'>发布文章</a>
</div>
</#if>
</#if>