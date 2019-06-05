<div>
<#if article_list??>
<ul class="listul">
<#list article_list as article>
<li>
<#if article.typeState == false>[原创]<#else>[转载]</#if>	  			
<a href="${SiteUrl}showArticle.action?articleId=${article.articleId}" target="_blank"<#if article.title?length &gt; 36> title="${article.title?html}"</#if>>${Util.getCountedWords(article.title?html,36)}</a>	  			
(${article.createDate?string('yyyy-MM-dd HH:mm:ss')})
</li>
</#list>
</ul>
<#else>
<div>没有检索到数据。</div>
</#if>
</div>