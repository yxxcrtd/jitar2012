<ul class="listul">
<#list article_list as article>
<li>
<#if article.typeState == false>[原创]<#else>[转载]</#if>	  			
<a href="${SiteUrl}showArticle.action?articleId=${article.id}" target="_blank">
${article.title?html}
</a>	  			
(${article.createDate?string('yyyy-MM-dd HH:mm:ss')})
</li>
</#list>
</ul>