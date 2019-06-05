<#if NewArticleList??>
<ul class="ulist">
<#list NewArticleList as article>
<li><span><a href="${SiteUrl}go.action?userId=${article.userId}" title="${article.userTrueName!?html}">${Util.getCountedWords(article.userTrueName!?html,4)}</a> ${article.createDate?string("MM-dd")}</span>
<#if article.typeState == false>[原创]<#else>[转载]</#if> <a href="${SiteUrl}showArticle.action?articleId=${article.articleId}" target='_blank' title='${article.title!?html}'>${Util.getCountedWords(article.title!?html,24)}</a></li>
</#list>
</ul>
</#if>