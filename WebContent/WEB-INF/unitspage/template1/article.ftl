<div class='tab_outer' style='min-height:226px;'> 
<div id="jitar_article_" class='tab'>
	<label class='tab_label_1'><img src='${SiteUrl}theme/units/${unit.themeName?default('theme1')}/j.gif' />&nbsp;${webpart.displayName}</label>
	<div class="cur" onmouseover="TabUtil.changeTab('jitar_article_',0)"><a href='${UnitRootUrl}article/unit_article.py?type=new'><span>最新文章</span></a></div>
	<div class="" onmouseover="TabUtil.changeTab('jitar_article_',1);"><a href='${UnitRootUrl}article/unit_article.py?type=hot'><span>最热文章</span></a></div>
	<div class="" onmouseover="TabUtil.changeTab('jitar_article_',2)"><a href='${UnitRootUrl}article/unit_article.py?type=rcmd'><span>推荐文章</span></a></div>
	<div class="" style='font-size:0;'></div>
</div>
<div class='tab_content'>
	<div id="jitar_article_0"  class="tres" style="display: block">		
	<!-- 最新文章 -->
	<#if newest_article_list??>
	<table style='width:100%;'>
	  <#list newest_article_list as article>
		<tr>
		<td style='vertical-align:top;padding-top:6px'><img src='${SiteUrl}theme/units/${unit.themeName?default('theme1')}/d.gif' /></td>
		<td style='width:100%'>
			<#if article.typeState == false>[原创]<#else>[转载]</#if>
			<a href='${SiteUrl}showArticle.action?articleId=${article.articleId}' target='_blank'>${article.title!?html}</a>
		</td>
		<td style="text-align:right"><nobr><a href='${SiteUrl}go.action?userId=${article.userId}'>${article.userTrueName!?html}</a> ${article.createDate?string('MM-dd')}</nobr></td>
		</tr> 
	  </#list>
	</table>
	</#if>		
	</div>

	<div id="jitar_article_1" class="tres" style="display: none;">
	<!-- 热门文章 -->
	<#if hot_article_list??>
	<table style='width:100%;'>
	  <#list hot_article_list as article>
		<tr>
		<td style='vertical-align:top;padding-top:6px'><img src='${SiteUrl}theme/units/${unit.themeName?default('theme1')}/d.gif' /></td>
		<td style='width:100%'>
			<#if article.typeState == false>[原创]<#else>[转载]</#if>
			<a href='${SiteUrl}showArticle.action?articleId=${article.articleId}' target='_blank'>${article.title!?html}</a>
		</td>
		<td style="text-align:right"><nobr><a href='${SiteUrl}go.action?userId=${article.userId}'>${article.userTrueName!?html}</a> ${article.createDate?string('MM-dd')}</nobr></td>
		</tr> 
	  </#list>
	</table>
	</#if>
	</div>

    <div id="jitar_article_2" class="tres" style="display: none;">   
    <!-- 推荐文章 -->
	<#if rcmd_article_list??>
	<table style='width:100%;'>
	  <#list rcmd_article_list as article>
		<tr>
		<td style='vertical-align:top;padding-top:6px'><img src='${SiteUrl}theme/units/${unit.themeName?default('theme1')}/d.gif' /></td>
		<td style='width:100%'>
			<#if article.typeState == false>[原创]<#else>[转载]</#if>
			<a href='${SiteUrl}showArticle.action?articleId=${article.articleId}' target='_blank'>${article.title!?html}</a>
		</td>
		<td style="text-align:right"><nobr><a href='${SiteUrl}go.action?userId=${article.userId}'>${article.userTrueName!?html}</a> ${article.createDate?string('MM-dd')}</nobr></td>
		</tr> 
	  </#list>
	</table>
	</#if>
    </div>	    
   <div id="jitar_article_3" style="display: none;"></div>
   </div>   
</div>