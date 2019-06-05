<!--文章 start-->
<div class="leftWidth articleWrap border mt3">
    <h3 class="h3Head">
        <a href="articles.action?type=new" class="more">更多</a>
        <a href="articles.action?type=hot" class="more none">更多</a>
        <a href="articles.action?type=famous" class="more none">更多</a>
        <a href="articles.action?type=new" class="sectionTitle active">最新文章<span></span></a>
        <a href="articles.action?type=hot" class="sectionTitle">最热文章<span></span></a>
        <a href="articles.action?type=famous" class="sectionTitle">名师文章<span></span></a>
    </h3> 
    <!--最新文章-->
    <div class="article">
        <#if (newest_article_list??) && (newest_article_list?size &gt; 0)>
        <#assign fa = newest_article_list[0]>
        <#assign u = Util.userById(fa.userId)>
        <div class="articleInfo">
            <a href="${ContextPath}go.action?loginName=${u.loginName!}"><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='images/default.gif'" alt="${u.trueName!?html}" class="articleImg" /></a>
            <h4 title="${fa.title!}"><a href="showArticle.action?articleId=${fa.articleId}">${Util.getCountedWords(fa.title!?html,10)}</a></h4>
            <p style="min-height:110px;" class="hardBreak">${Util.articleAbstract(fa.articleId,66)!}....</p>
            <p class="more1P"><a href="showArticle.action?articleId=${fa.articleId}" class="more1">了解详情</a></p>
        </div>
        <#if newest_article_list??>
        <ul class="ulList articleList">
       	 <#list newest_article_list as article>
       	 	<#if article_index != 0>
              <li>
                <em class="emDate"><a href="go.action?loginName=${article.loginName!}"<#if article.trueName!?length &gt; 3> title="${article.trueName!?html}"</#if>>${Util.getCountedWords(article.trueName!?html,4)}</a></em>
                <#if article.typeState == false>[原创]<#else>[转载]</#if>
                  <a href="showArticle.action?articleId=${article.articleId}" title="${article.title!}" target="_blank" >
                  	${Util.getCountedWords(article.title!?html,15)}
                  </a>
              </li>
            </#if>
         </#list>
        </ul>
        </#if>    
        </#if>
    </div>
    <!--最新文章end-->
    <!--最热文章-->
   <div class="article none" >
    <#if (hot_article_list??) && (hot_article_list?size &gt; 0) >
        <#assign fa = hot_article_list[0]>
        <#assign u = Util.userById(fa.userId)>
        <div class="articleInfo">
            <a href="${ContextPath}go.action?loginName=${u.loginName!}"><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='images/default.gif'" alt="${u.trueName!?html}" class="articleImg" /></a>
            <h4 title="${fa.title!}"><a href='showArticle.action?articleId=${fa.articleId}'>${Util.getCountedWords(fa.title!?html,10)}</a></h4>
            <p title="${fa.title!}" style="min-height:110px" class="hardBreak">${Util.articleAbstract(fa.articleId, 66)!}....</p>
            <p class="more1P"><a href="showArticle.action?articleId=${fa.articleId}" class="more1">了解详情</a></p>
        </div>
        <#if hot_article_list??>
          <ul class="ulList articleList">
          <#list hot_article_list as article>
				  <#if article_index != 0>
				    <li>
                     <em class="emDate"><a href="go.action?loginName=${article.loginName!}"<#if article.trueName!?length &gt; 3> title="${article.trueName!?html}"</#if>>${Util.getCountedWords(article.trueName!?html,4)}</a></em>
                     <#if article.typeState == false>[原创]<#else>[转载]</#if>
	                 <a title="${article.title!}" href="showArticle.action?articleId=${article.articleId}" target="_blank" >
	                    	${Util.getCountedWords(article.title!?html,15)}
	                 </a>
            	    </li>
              </#if>
           </#list>
          </ul>
        </#if>
      </#if>
    </div>

    <div class="article none">
        <#if (famous_article_list??) && (famous_article_list?size > 0) >
        <#assign fa = famous_article_list[0]>
        <#assign u = Util.userById(fa.userId)>
        <div class="articleInfo">
            <a href="${ContextPath}go.action?loginName=${fa.loginName!}"><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='images/default.gif'" alt="${u.trueName!?html}" class="articleImg" /></a>
            <h4><a title="${fa.title}" href='showArticle.action?articleId=${fa.articleId}'>${Util.getCountedWords(fa.title!?html,10)}</a></h4>
            <p title="${fa.title!}" style="min-height:110px" class="hardBreak">${Util.articleAbstract(fa.articleId, 66)!}....</p>
            <p class="more1P"><a href="showArticle.action?articleId=${fa.articleId}" class="more1">了解详情</a></p>
        </div>
        <#if famous_article_list??>
          <ul class="ulList articleList">
             <#list famous_article_list as article>
	           <#if article_index != 0>
		         <#assign u = Util.userById(article.userId)> 
		         <li>
                   <em class="emDate"><a href="go.action?loginName=${article.loginName!}"<#if article.trueName!?length &gt; 3> title="${article.trueName!?html}"</#if>>${Util.getCountedWords(article.trueName!?html,4)}</a></em>
                   <#if article.typeState == false>[原创]<#else>[转载]</#if>
                 <a title="${article.title!}" href="showArticle.action?articleId=${article.articleId}" target="_blank" >
                    	${Util.getCountedWords(article.title!?html,15)}
                 </a>
               </li>
                </#if>
           </#list>
          </ul>
        </#if>
      </#if>
    </div>
    <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize2" /></div>
</div>
<!--文章 End-->
