<#if showType == 1>
  <#if article_list??>
	<ul class='item_ul'>
		<#list article_list as article>
			<li>
				<span>${article.createDate?string('yyyy-MM-dd')}</span>					
				<a href='${UnitRootUrl}py/showContent.py?articleId=${article.contentSpaceArticleId}' target='_blank' title='${article.title!?html}'>${article.title!?html}</a> [${article.viewCount}]
			</li>
		</#list>
		</ul>
  </#if>
<#elseif showType == 2 >
	<#if article_list?? >
	<#assign pics = ''>
	<#assign links = ''>
	<#assign texts = ''>
	<#list article_list as article >
	<#if article_index < 6 && article.pictureUrl??>
	 <#if article_has_next>
	  <#assign pics = pics + Util.url(article.pictureUrl) + '|' >
	  <#assign links = links + UnitRootUrl + 'py/showContent.py?articleId=${article.contentSpaceArticleId}|' >
	  <#assign texts = texts + article.title + '|' >
	 <#else>
	  <#assign pics = pics + Util.url(article.pictureUrl) >
	  <#assign links = links + UnitRootUrl + 'py/showContent.py?articleId=${article.contentSpaceArticleId}' >
	  <#assign texts = texts + article.title >
	 </#if>
	</#if> 
	</#list>

	<object type="application/x-shockwave-flash" data="${SiteUrl}images/slide.swf" width="300" height="200">
	<param name="movie" value="${SiteUrl}images/slide.swf" />
	<param name="allowScriptAcess" value="sameDomain" />
	<param name="quality" value="best" />
	<param name="bgcolor" value="#FFFFFF" />
	<param name="scale" value="noScale" />
	<param name="menu" value="false">
	<param name="wmode" value="opaque" />
	<param name="FlashVars" value="playerMode=embedded&amp;pics=${pics}&amp;links=${links}&amp;texts=${texts}&amp;borderwidth=300&amp;borderheight=180&amp;textheight=20" />
	</object>
	</#if>
<#elseif showType == 3 >
<table border='0' style='width:100%' cellspacing='2'>
<tr style='vertical-align:top'>
<td style='width:200px'>
	<#if article_list?? >
	<#assign pics = ''>
	<#assign links = ''>
	<#assign texts = ''>
	<#list article_list as article >
	<#if article_index < 6 && article.pictureUrl??>
	 <#if article_has_next>
	  <#assign pics = pics + Util.url(article.pictureUrl) + '|' >
	  <#assign links = links + UnitRootUrl + 'py/showContent.py?articleId=${article.contentSpaceArticleId}|' >
	  <#assign texts = texts + article.title + '|' >
	 <#else>
	  <#assign pics = pics + Util.url(article.pictureUrl) >
	  <#assign links = links + UnitRootUrl + 'py/showContent.py?articleId=${article.contentSpaceArticleId}' >
	  <#assign texts = texts + article.title >
	 </#if>
	</#if> 
	</#list>

	<object type="application/x-shockwave-flash" data="${SiteUrl}images/slide.swf" width="200" height="140">
	<param name="movie" value="${SiteUrl}images/slide.swf" />
	<param name="allowScriptAcess" value="sameDomain" />
	<param name="quality" value="best" />
	<param name="scale" value="noScale" />
	<param name="bgcolor" value="#FFFFFF" />
	<param name="menu" value="false">
	<param name="wmode" value="opaque" />
	<param name="FlashVars" value="playerMode=embedded&amp;pics=${pics}&amp;links=${links}&amp;texts=${texts}&amp;borderwidth=200&amp;borderheight=120&amp;textheight=20" />
	</object>
	</#if>
</td>
<td style='padding-left:10px'>
  <#if article_list??>
	<ul class='item_ul'>
		<#list article_list as article>
			<li>
				<span>${article.createDate?string('yyyy-MM-dd')}</span>					
				<a href='${UnitRootUrl}py/showContent.py?articleId=${article.contentSpaceArticleId}' target='_blank' title='${article.title!?html}'>${article.title!?html}</a> [${article.viewCount}]
			</li>
		</#list>
		</ul>
  </#if>
</td>
</tr>
</table>
<#else>
</#if>