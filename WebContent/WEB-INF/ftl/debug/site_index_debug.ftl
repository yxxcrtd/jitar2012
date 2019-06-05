<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>index.py 调试页面</title>
 </head>
 <body>
  <h2>index.py 调试页面</h2>
  
  <h3>教研动态 (从某个指定用户处获取的博文就认为是教研动态)</h3>
  <#if jitar_news??>
	  <ul>
	  <#list jitar_news as article>
	    <#assign u = Util.userById(article.userId)>
	    <li><a href='${SiteUrl}${u.loginName}/article/${article.articleId}.html'>${article.title!?html}</a> 
	     [<a href='${SiteUrl}${u.loginName}'>${u.nickName}</a>][${article.createDate?string('yy-MM-dd hh:mm')}]</li> 
	  </#list>
	  </ul>
  </#if>
  
  
  <h3>教研公告</h3>
  <#if site_placard_list??>
    <ul>
    <#list site_placard_list as placard>
      <li><a href='#'>${placard.title}</a> [${placard.createDate}]</li>
    </#list>
    </ul>
  </#if>
  
  <h3>最新文章</h3>
  <#if newest_article_list??>
  <ul>
  <#list newest_article_list as article>
    <#assign u = Util.userById(article.userId)>
    <li><a href='${SiteUrl}${u.loginName}/article/${article.articleId}.html'>${article.title!?html}</a> 
     [<a href='${SiteUrl}${u.loginName}'>${u.nickName}</a>][${article.createDate?string('yy-MM-dd hh:mm')}]</li> 
  </#list>
  </ul>
  </#if>
  
  <h3>热门文章</h3>
  <#if hot_article_list??>
  <ul>
  <#list hot_article_list as article>
    <#assign u = Util.userById(article.userId)>
    <li><a href='${SiteUrl}${u.loginName}/article/${article.articleId}.html'>${article.title!?html}</a> 
     [<a href='${SiteUrl}${u.loginName}'>${u.nickName}</a>][${article.viewCount}]</li> 
  </#list>
  </ul>
  </#if>
  
  <h3>推荐文章</h3>
  <#if rcmd_article_list??>
  <ul>
  <#list rcmd_article_list as article>
    <#assign u = Util.userById(article.userId)>
    <li><a href='${SiteUrl}${u.loginName}/article/${article.articleId}.html'>${article.title!?html}</a> 
     [<a href='${SiteUrl}${u.loginName}'>${u.nickName}</a>][${article.createDate}]</li> 
  </#list>
  </ul>
  </#if>
  
  <h3>名师文章</h3>
  <#if famous_article_list??>
  <ul>
  <#list famous_article_list as article>
    <#assign u = Util.userById(article.userId)>
    <li><a href='${SiteUrl}${u.loginName}/article/${article.articleId}.html'>${article.title!?html}</a> 
     [<a href='${SiteUrl}${u.loginName}'>${u.nickName}</a>][${article.createDate}]</li> 
  </#list>
  </ul>
  </#if>
  
  <h3>最新文章评论</h3>
  <#if new_comment_list??>
  <ul>
    <#list new_comment_list as comment>
      <li><a href='showArticle.action?articleId=${comment.objId}#comment_${comment.id}'>${comment.title}</a> 
        [${comment.userName!}] [${comment.createDate?string('MM-dd')}]</li>
    </#list>
  </ul>
  </#if>
  
  <h3>教研图集</h3>
  <#if photo_list??>
    <ul>
      <#list photo_list as photo>
        <li>${photo.title}, ${photo.href}</li>
      </#list>
    </ul>
  </#if>
  
  <h3>最新资源</h3>
  <#if new_resource_list??>
    <ul>
      <#list new_resource_list as resource>
        <li><a href='showResource.action?resourceId=${resource.resourceId}'>${resource.title!}</a>
          [${resource.createDate}]</li>
      </#list>
    </ul>
  </#if>
  
  <h3>热门资源</h3>
  <#if hot_resource_list??>
    <ul>
      <#list hot_resource_list as resource>
        <li><a href='showResource.action?resourceId=${resource.resourceId}'>${resource.title!}</a>
         [${resource.viewCount}]</li>
      </#list>
    </ul>
  </#if>
  
  <h3>推荐资源</h3>
  <#if rcmd_resource_list??>
    <ul>
      <#list rcmd_resource_list as resource>
        <li><a href='showResource.action?resourceId=${resource.resourceId}'>${resource.title!}</a>
         [${resource.viewCount}]</li>
      </#list>
    </ul>
  </#if>
  
  <h3>最新协作组</h3>
  <#if new_group_list??>
	  <ul>
	    <#list new_group_list as group>
	      <li><a href='${SiteUrl}g/${group.groupName}'>${group.groupTitle}</a></li>
	    </#list>
	  </ul>
  </#if>

  
  <h3>热门协作组</h3>
  <#if hot_group_list??>
	  <ul>
	    <#list hot_group_list as group>
	     <li><a href='#'>${group.groupTitle}</a> [${group.visitCount}]</li>
	    </#list>
	  </ul>
  </#if>
  
  
  <h3>推荐协作组</h3>
  <#if best_group_list??>
    <ul>
      <#list best_group_list as group>
       <li><a href='#'>${group.groupTitle}</a> [${group.visitCount}]</li>
      </#list>
    </ul>
  </#if>
  
  <h3>协作组最新话题</h3>
  <#if new_topic_list??>
	  <ul>
	    <#list new_topic_list as topic>
	      <li><a href='#'>${topic.title}</a></li>
	    </#list>
	  </ul>
  </#if>
  
  <h3>学科主页</h3>
  <#if subject_list??>
	  <ul>
	    <#list subject_list as subject>
	    <li><a href='${SiteUrl}showSubject.py?subjectId=${subject.subjectId}'>${subject.subjectName}</a></li>
	    </#list>
	  </ul>
  </#if>
  
 </body>
</html>
