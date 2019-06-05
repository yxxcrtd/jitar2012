<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 </head>
 <body>
  <h2>showCategoryArticle.action 调试页面</h2>
  
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
  
  <h3>文章列表(带分页)</h3>
  <#if cate_article_list??>
    <ul>
    <#list cate_article_list as article>
      <#assign u = Util.userById(article.userId)>
      <li><a href='${u.loginName}/article/${article.articleId}.html' target='_blank'>${article.title!?html}</a> 
        [${u.nickName}][${article.createDate?string('MM-dd')}]</li>
    </#list>
    </ul>
    <div>
      <#include ('../inc/pager.ftl') >
    </div>
    <div>pager = ${pager}</div>
  </#if>
  
 </body>
</html>
