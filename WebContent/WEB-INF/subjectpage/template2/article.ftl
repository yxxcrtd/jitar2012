<div class='tab' style='min-height:180px'> 
	<div id="jitar_article_" class='tab2'>
		<label class='tab_label_1'><img src='${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/j.gif' />&nbsp;${webpart.displayName!?html}</label>
		<div class="cur" onmouseover="TabUtil.changeTab('jitar_article_',0)"><a href='${SubjectRootUrl}py/article.py?type=new&unitId=${unitId!}'><span>最新文章</span></a></div>
		<div class="" onmouseover="TabUtil.changeTab('jitar_article_',1);"><a href='${SubjectRootUrl}py/article.py?type=hot&unitId=${unitId!}'><span>最热文章</span></a></div>
		<div class="" onmouseover="TabUtil.changeTab('jitar_article_',2)"><a href='${SubjectRootUrl}py/article.py?type=rcmd&unitId=${unitId!}'><span>推荐文章</span></a></div>
		<div class="" style='font-size:0;'></div>
	</div>
	<div class='tab_content'>
		<div id="jitar_article_0"  style="display: block">		
		<!-- 最新文章 -->
		<#if newest_article_list??>
		<ul class='item_ul'>
			<#list newest_article_list as article>
			<li>
				<span>
					<a href='${SiteUrl}go.action?loginName=${article.loginName}'<#if article.userTrueName?length &gt; 3> title="${article.userTrueName}"</#if>>${Util.getCountedWords(article.userTrueName!?html,3)}</a>
					${article.createDate?string('MM-dd')}
				</span>
				<#if article.typeState == false>[原创]<#else>[转载]</#if><a target='_blank' href="${ContextPath}showArticle.action?articleId=${article.articleId}"<#if article.title?length &gt; 26> title="${article.title}"</#if>>${Util.getCountedWords(article.title!?html,27)}</a>
			</li> 
			</#list>
		</ul>    
		</#if>		
		</div>
    
		<div id="jitar_article_1" style="display: none;">
		<!-- 热门文章 -->
		<#if rcmd_article_list??>
		<ul class='item_ul'>
			<#list hot_article_list as article>				
			<li>
				<span>
					<a href='${SiteUrl}go.action?loginName=${article.loginName}'<#if article.userTrueName?length &gt; 3> title="${article.title}"</#if>>${Util.getCountedWords(article.userTrueName!?html,3)}</a>
					${article.createDate?string('MM-dd')}
				</span>
				<#if article.typeState == false>[原创]<#else>[转载]</#if><a target='_blank' href="${ContextPath}showArticle.action?articleId=${article.articleId}"<#if article.title?length &gt; 26> title="${article.title}"</#if>>${Util.getCountedWords(article.title!?html,27)}</a>
			</li> 
			</#list>
		</ul>    
		</#if>
    	</div>
    
	    <div id="jitar_article_2" style="display: none;">   
	    <!-- 推荐文章 -->
		<#if rcmd_article_list??>
		<ul class='item_ul'>
			<#list rcmd_article_list as article>				
			<li>
				<span>
					<a href='${SiteUrl}go.action?loginName=${article.loginName}'>${article.userTrueName!?html}</a>
					${article.createDate?string('MM-dd')}
				</span>
				<#if article.typeState == false>[原创]<#else>[转载]</#if><a target='_blank' href="${ContextPath}showArticle.action?articleId=${article.articleId}"<#if article.title?length &gt; 26> title="${article.title}"</#if>>${Util.getCountedWords(article.title!?html,27)}</a>
			</li> 
			</#list>
		</ul>    
		</#if>
	    </div>	    
	   <div id="jitar_article_3" style="display: none;"></div>
   </div>   
</div>