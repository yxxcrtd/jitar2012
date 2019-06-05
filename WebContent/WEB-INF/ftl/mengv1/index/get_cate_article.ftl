<#if !(textLength??)>
<#assign textLength=12 />
</#if>
<#if textLength &lt; 2 >
<#assign textLength = 2 />
</#if>
<#if article_list??>
<ul class="ulList">
<#list article_list as article>
	<li>
	<em class="emDate">${article.createDate?string('MM-dd')}</em>
  <a href='${ContextPath}showArticle.action?articleId=${article.articleId}' target='_blank'<#if textLength &lt; article.title?length>  title='${article.title!?html}'</#if>>${Util.getCountedWords(article.title!?html,textLength)}</a>
	</li>
</#list>
</ul>
</#if>