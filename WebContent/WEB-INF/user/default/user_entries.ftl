<ul class="listul">
<#list article_list as article>
<li><span style="float:right">${article.createDate?string("MM-dd HH:mm")}</span>
<#if article.typeState == false>[原创]<#else>[转载]</#if>
<a href="${SiteUrl}showArticle.action?articleId=${article.articleId}" target="_blank"<#if article.title?length &gt; 22> title="${article.title?html}"</#if>>${Util.getCountedWords(article.title?html,22)}</a>
</#list>
</ul>
<div style="text-align: right;">
<a href="${UserSiteUrl}category/0.html">&gt;&gt; 所有文章</a>
</div>