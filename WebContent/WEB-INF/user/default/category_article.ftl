<ul class="listul">
  <#list article_list as article>
  <li>
  	<span style="float:right">${article.createDate?string("MM-dd HH:mm")}</span>
  	<#if article.typeState == false>[原创]<#else>[转载]</#if>
  	<a href="${SiteUrl}showArticle.action?articleId=${article.articleId}" target="_blank">${article.title!?html}</a>
	</#list>
</ul>
<div style="text-align: right;">
<a href="${UserSiteUrl}category/${userCateId}.html?title=${title}">&gt;&gt; 查看更多</a>
</div>