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
            <#if (newest_article_list??) && (newest_article_list?size > 0)>
			<#assign fa = newest_article_list[0]>
			<#assign u = Util.userById(fa.userId)>
            <div class="article">
                <div class="articleInfo">
                    <img width="120" height="120" src="${SSOServerUrl +"upload/"+u.userIcon!"images/default.gif"}" onerror="this.src='images/default.gif'" alt="${fa.title!?html}" class="articleImg" />
                    <h4><a href="showArticle.action?articleId=${fa.articleId}">${fa.title!?html}</a></h4>
                    <p>${Util.articleAbstract(fa.articleId, 72)}....<a href="showArticle.action?articleId=${fa.articleId}" class="more1">了解详情</a></p>
                </div>
                <#if newest_article_list??>
                <ul class="ulList articleList">
               	 <#list newest_article_list as article>
               	 	<#if article_index != 0>
	                    <li>
		                    <em class="emDate"><a href="go.action?loginName=${article.loginName}">${Util.getCountedWords(article.trueName!?html,4)}</a></em>
		                    <#if article.typeState == false>[原创]<#else>[转载]</#if>
			                    <a href="showArticle.action?articleId=${article.articleId}" target="_blank" >
			                    	${Util.getCountedWords(article.title!?html,15)}
			                    </a>
	                    </li>
                    </#if>
                 </#list>
                </ul>
                </#if>
            </div>
            </#if>
            <!--最新文章end-->
            
            <!--最热文章-->
            <#if (hot_article_list??) && (hot_article_list?size > 0) >
		    <#assign fa = hot_article_list[0]>
            <div class="article none">
                <div class="articleInfo">
                    <img width="120" height="120" src='${SSOServerUrl +"upload/"+fa.userIcon!"images/default.gif"}' onerror="this.src='images/default.gif'" alt="${fa.title!?html}" class="articleImg" />
                    <h4><a href='showArticle.action?articleId=${fa.articleId}'>${fa.title!?html}</a></h4>
                    <p>${Util.articleAbstract(fa.articleId, 72)}....<a href="showArticle.action?articleId=${fa.articleId}" class="more1">了解详情</a></p>
                </div>
                <#if hot_article_list??>
	                <ul class="ulList articleList">
	                   <#list hot_article_list as article>
	    					<#if article_index != 0>
	    					    <li>
				                     <em class="emDate"><a href="go.action?loginName=${article.loginName}">${Util.getCountedWords(article.trueName!?html,4)}</a></em>
				                     <#if article.typeState == false>[原创]<#else>[转载]</#if>
					                 <a href="showArticle.action?articleId=${article.articleId}" target="_blank">
					                    	${Util.getCountedWords(article.title!?html,15)}
					                 </a>
                    			</li>
			                </#if>
		               </#list>
	                </ul>
                </#if>
            </div>
            </#if>
      		<#if (famous_article_list??) && (famous_article_list?size > 0) >
			<#assign fa = famous_article_list[0]>
			<#assign u = Util.userById(fa.userId)>
            <div class="article none">
                <div class="articleInfo">
                    <img width="120" height="120" src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" onerror="this.src='images/default.gif'" alt="${fa.title!?html}" class="articleImg" />
                    <h4><a href='showArticle.action?articleId=${fa.articleId}'>${fa.title!?html}</a></h4>
                    <p>${Util.articleAbstract(fa.articleId, 72)}....<a href="showArticle.action?articleId=${fa.articleId}" class="more1">了解详情</a></p>
                </div>
                <#if famous_article_list??>
	                <ul class="ulList articleList">
	                   <#list famous_article_list as article>
					       <#if article_index != 0>
						    <#assign u = Util.userById(article.userId)> 
						       <li>
			                     <em class="emDate"><a href="go.action?loginName=${article.loginName}">${Util.getCountedWords(article.trueName!?html,4)}</a></em>
			                     <#if article.typeState == false>[原创]<#else>[转载]</#if>
				                 <a href="showArticle.action?articleId=${article.articleId}" target="_blank">
				                    	${Util.getCountedWords(article.title!?html,15)}
				                 </a>
				               </li>
		                    </#if>
		               </#list>
	                </ul>
                </#if>
            </div>
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize2" /></div>
        </div>
     </#if>
<!--文章 End-->
