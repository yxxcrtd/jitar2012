<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 </head>
 <body>
  <h2>showCategory.action 调试页面</h2>
  
  <h3>当前分类</h3>
  <ul>
    <li>category = ${category}</li>
  </ul>
  
  <h3>系统分类</h3>
  <#if syscate_tree??>
    <#list syscate_tree.all as category>
      <div>${category.treeFlag2} 
        <a href='showCategory.action?cmd=debug&amp;categoryId=${category.categoryId}'>${category.name}</a>
      </div>
    </#list>
  </#if>
  
  <h3>该分类的文章</h3>
  <#if cate_article_list??>
    <ul>
    <#list cate_article_list as article>
      <#assign u = Util.userById(article.userId)>
      <li>[${article.createDate?string('MM-dd')}] 
        <a href='${SiteUrl}${u.loginName}/article/${article.articleId}.html' target='_blank'>${article.title!?html}</a> [${u.nickName}]</li>
    </#list>
    </ul>
  </#if>
  <div><a href='showCategoryArticle.action?cmd=debug&categoryId=${category.categoryId}'>点击查看更多...</a></div>
  
  <h3>该分类的资源</h3>
  <#if cate_resource_list??>
    <ul>
    <#list cate_resource_list as resource>
      <#assign u = Util.userById(resource.userId)>
      <li><a href='showResource.action?resourceId=${resource.resourceId}'>${resource.title!?string}</a></li>
    </#list>
    </ul>
  </#if>
  <div><a href='showCategoryResource.action?cmd=debug&categoryId=${category.categoryId}'>点击查看更多...</a></div>
  
 </body>
</html>
