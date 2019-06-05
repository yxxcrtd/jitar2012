<div class='tab'>
  <!-- tab -->
  <div id="article_" class="tab2">
    <label class='titlecolor'>学科文章</label>
    <div class="cur" onmouseover="TabUtil.changeTab('article_',0)"><a href='subjectArticles.py?type=new&subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}'><span>最新</span></a></div>
    <div class="" onmouseover="TabUtil.changeTab('article_',1)"><a href='subjectArticles.py?type=hot&subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}'><span>最热</span></a></div>
    <div class="" onmouseover="TabUtil.changeTab('article_',2)"><a href='subjectArticles.py?type=rcmd&subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}'><span>推荐</span></a></div>
    <div class=""></div>
  </div>
  
	<div class='tab_content' style='overflow:hidden;height:220px;'>
    <!-- 学科最新文章 -->
	  <div id="article_0"  style="display: block">            
	  <#if new_article_list?? >
	  	<table border='0' width='100%' cellspacing='0'>
	      <#list new_article_list as article>
	        <#assign u = Util.userById(article.userId) >
	        <tr>
	       	<td class='table_list line1'><nobr>${article.createDate?string('MM-dd')}</nobr></td>
	       	<td width='100%' class='line1'>
	       	<#if article.typeState == false>[原创]<#else>[转载]</#if>
	       	<a href='${SiteUrl}showArticle.action?articleId=${article.articleId}' target='_blank'>${article.title!?html}</a>
	       	</td>
	     	<td align='right' class='line1'><nobr><a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'>${u.nickName!}</a></nobr></td>
	        </tr>
	      </#list>
	    </table>
	  </#if>
	  </div>
	  
    <!-- 学科热门文章 -->
		<div id="article_1"  style="display: none">
		  <#if hot_article_list?? >
	  	<table border='0' width='100%' cellspacing='0'>
	      <#list hot_article_list as article>
	        <#assign u = Util.userById(article.userId) >
	        <tr>
	       	<td class='table_list line1'><nobr>${article.createDate?string('MM-dd')}</nobr></td>
	       	<td width='100%' class='line1'><#if article.typeState == false>[原创]<#else>[转载]</#if><a href='${SiteUrl}showArticle.action?articleId=${article.articleId}' target='_blank'>${article.title!?html}</a></td>
	     	<td align='right' class='line1'><nobr><a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'>${u.nickName!}</a></nobr></td>
	        </tr>
	      </#list>
	    </table>
		  </#if>
		</div>
		
		<!-- 学科推荐文章 -->
		<div id="article_2"  style="display: none">           
      <#if rcmd_article_list?? >
	  	<table border='0' width='100%' cellspacing='0'>
	      <#list rcmd_article_list as article>
	        <#assign u = Util.userById(article.userId) >
	        <tr>
	       	<td class='table_list line1'><nobr>${article.createDate?string('MM-dd')}</nobr></td>
	       	<td width='100%' class='line1'><#if article.typeState == false>[原创]<#else>[转载]</#if><a href='${SiteUrl}showArticle.action?articleId=${article.articleId}' target='_blank'>${article.title!?html}</a></td>
	     	<td align='right' class='line1'><nobr><a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'>${u.nickName!}</a></nobr></td>
	        </tr>
	      </#list>
	    </table>

      </#if>
		</div>
		
		<div id="article_3"  style="display: none"> 
		</div>
	</div>
</div>
