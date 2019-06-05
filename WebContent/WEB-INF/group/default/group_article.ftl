<div id="t${guid}" class='tab_div'>
	<div class="cur" onmouseover="TabUtil.changeTab('t${guid}',0)"><span>最新文章</span></div>
	<#--<div onmouseover="TabUtil.changeTab('t${guid}',1)"><span>热门文章</span></div>-->
	<div onmouseover="TabUtil.changeTab('t${guid}',1)"><span>精华文章</span></div>
	<div class=""></div>
</div>
<div class='tab_content'>
	<div id="t${guid}0" style='display:block;'>
		<ul class='listul'>
			 <#list new_list as article>
			 <#assign u = Util.userById(article.userId)>
			  <li><span style='float:right'><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName}</a> ${article.createDate!?string('MM-dd')}</span><#if article.typeState == false>[原创]<#else>[转载]</#if><a href='${SiteUrl}showArticle.action?articleId=${article.articleId}' target='_blank' title="${article.title!?html}">${Util.getCountedWords(article.title!?html,18)}</a></li>
			 </#list>
			</ul>
	</div><#--
	<div id="t${guid}1"  style='display:none;'>
	<ul class='listul'>
			 <#list hot_list as article>
			  <#assign u = Util.userById(article.userId)>
              <li><span style='float:right'><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName}</a> ${article.createDate?string('MM-dd')}</span><#if article.typeState == false>[原创]<#else>[转载]</#if><a href='${SiteUrl}showArticle.action?articleId=${article.articleId}' target='_blank'>${article.title!?html}</a></li>
             </#list>
			</ul>
	</div>-->
	<div id="t${guid}1" style='display:none;'>
	<ul class='listul'>
              <#list best_list as article>
			  <#assign u = Util.userById(article.userId)>
              <li><span style='float:right'><a href='${SiteUrl}go.action?loginName=${u.loginName!}'>${u.trueName!}</a> ${article.createDate!?string('MM-dd')}</span><#if article.typeState == false>[原创]<#else>[转载]</#if><a href='${SiteUrl}showArticle.action?articleId=${article.articleId}' target='_blank' title="${article.title!?html}">${Util.getCountedWords(article.title!?html,18)}</a></li>
             </#list>
			</ul>
	</div>
	<div id="t${guid}2" style='display:none;'>
	</div>
</div>
<div style="clear:both; text-align:right; margin-top:6px;"><a href='${SiteUrl}g/${group.groupName}/artcate/0.html'>&gt;&gt; 全部文章</a></div>
