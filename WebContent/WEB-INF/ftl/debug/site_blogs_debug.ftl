<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 </head>
 <body>
  <h2>blogs.py 调试页面</h2>
  
  <h3>最新发表 (newest_article_list)</h3>
  <#if newest_article_list??>
  <ul>
  <#list newest_article_list as article>
	  <#assign u = Util.userById(article.userId)>
	  <li><a href='${SiteUrl}${u.loginName}/article/${article.articleId}.html'>${article.title!?html}</a> 
	   [<a href='${SiteUrl}${u.loginName}'>${u.nickName}</a>][${article.createDate?string('yy-MM-dd hh:mm')}]</li> 
  </#list>
  </ul>
  </#if>
  
  <h3>精华文章</h3>
  <ul>
  <#list best_article_list as article>
    <#assign u = Util.userById(article.userId)>
    <li><a href='${SiteUrl}${u.loginName}/article/${article.articleId}.html'>${article.title!?html}</a> 
     [<a href='${SiteUrl}${u.loginName}'>${u.nickName}</a>][${article.createDate?string('yy-MM-dd hh:mm')}]</li> 
  </#list>
  </ul>
  
  <h3>名师博客</h3>
  <#if famous_teachers??>
  <ul>
  <#list famous_teachers as user>
    <li><a href='${SiteUrl}${user.loginName}'>${user.nickName}</a></li>
  </#list>
  </ul>
  </#if>
  
  <h3>学科带头人博客</h3>
  <#if expert_list??>
  <ul>
  <#list expert_list as user>
    <li><a href='${SiteUrl}${user.loginName}'>${user.nickName}</a></li>
  </#list>
  </ul>
  </#if>
  
  <h3>推荐博客</h3>
  <#if rcmd_list??>
  <ul>
  <#list rcmd_list as user>
    <li><a href='${SiteUrl}${user.loginName}'>${user.nickName}</a></li>
  </#list>
  </ul>
  </#if>
  
  
  <h3>博客访问排行</h3>
  <#if blog_visit_charts??>
  <ul>
  <#list blog_visit_charts as user>
    <li><a href='${SiteUrl}${user.loginName}'>${user.nickName}</a> [${user.visitCount}]</li>
  </#list>
  </ul>
  </#if>
  
  <h3>统计信息</h3>
  <#if site_stat??>
    <ul>
      <li>注册用户数: ${site_stat.userCount}</li>
      <li>文章数: ${site_stat.articleCount}</li>
      <li>资源数: ${site_stat.resourceCount}</li>
      
    </ul>
  </#if>
  
  <h3>地域 (这个版本没有)</h3>
  
  <h3>学科</h3>
  <#if subject_list??>
  <ul>
    <#list subject_list as subject>
    <li><a href='${SiteUrl}showSubject.py?subjectId=${subject.subjectId}'>${subject.subjectName}</a></li>
    </#list>
  </ul>
  </#if>
  
  <h3>分类</h3>
  <#if syscate_tree??>
  <ul>
    <#list syscate_tree.root as category>
      <a href='showCategory.action?categoryId=${category.id}'>${category.name}</a> 
    </#list>
  </ul>
  </#if>

  <h3>最热门文章</h3>
  <#if hot_article_list??>
  <ul>
    <#list hot_article_list as article>
      <li><a href='${SiteUrl}showArticle.action?articleId=${article.articleId}'>${article.title}</a> [${article.viewCount}]</li>
    </#list>
  </ul>
  </#if>
  
  <h3>评论最多文章</h3>
  <#if cmt_article_list??>
  <ul>
    <#list cmt_article_list as article>
      <li><a href='${SiteUrl}showArticle.action?articleId=${article.articleId}'>${article.title}</a> [${article.commentCount}]</li>
    </#list>
  </ul>
  </#if>
  
  <h3>最新发表评论(仅文章的)</h3>
  <#if new_comment_list??>
  <ul>
    <#list new_comment_list as comment>
      <li><a href='showArticle.action?articleId=${comment.objId}#comment_${comment.id}'>${comment.title}</a> 
        [${comment.userName!}] [${comment.createDate?string('MM-dd')}]</li>
    </#list>
  </ul>
  </#if>
  
 </body>
</html>
