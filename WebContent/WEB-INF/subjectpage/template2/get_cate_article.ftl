<#if article_list??>
<ul class='item_ul'>
	<#list article_list as article>
	<li>
		<span>
			<a href='${SiteUrl}go.action?loginName=${article.loginName}'>${article.nickName!?html}</a>
			${article.createDate?string('MM-dd')}
		</span>
		<#if article.typeState == false>[原创]<#else>[转载]</#if> <a href='${ContextPath}go.action?articleId=${article.articleId}' target='_blank'>${article.title!?html}</a>
	</li> 
	</#list>
</ul>    
</#if>