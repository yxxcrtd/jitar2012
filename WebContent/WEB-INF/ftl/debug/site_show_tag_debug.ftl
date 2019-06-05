<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 </head>
 <body>
  <h2>showtag.action 调试页面</h2>
  
  <h3>Tag 基本信息</h3>
  <ul>
    <li>tag = ${tag}</li>
  </ul>
  
  <h3>使用此标签的博客(用户)</h3>
  <#if tag_user_list??>
    <ul>
      <#list tag_user_list as user>
        <li><a href='${user.loginName}'>${user.loginName}</a></li>
      </#list>
    </ul>
  </#if>
  
  <h3>使用此标签的群组</h3>
  <#if tag_group_list??>
    <ul>
      <#list tag_group_list as group>
        <li><a href='g/${group.groupName}'>${group.groupTitle}</a></li>
      </#list>
    </ul>
  </#if>
  
  <h3>使用此标签的文章</h3>
  <#if tag_article_list??>
    <ul>
      <#list tag_article_list as article>
        <li><a href='${SiteUrl}showArticle.action?articleId=${article.articleId}'>${article.title}</a></li>
      </#list>
    </ul>
  </#if>
  
  <h3>使用此标签的资源</h3>
  <#if tag_resource_list??>
    <ul>
      <#list tag_resource_list as resource>
        <li><a href='#'>${resource.title}</a></li>
      </#list>
    </ul>
  </#if>
  
 </body>
</html>
