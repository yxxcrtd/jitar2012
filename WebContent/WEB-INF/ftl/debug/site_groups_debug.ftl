<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>group.action 数据调试页面</title>
 </head>
 <body>
  <h2>groups.py 数据调试页面</h2>
  
  <h3>群组分类</h3>
  <#if syscate_tree??>
  <ul>
    <#list syscate_tree.root as category>
      <a href='showCategory.action?categoryId=${category.id}'>${category.name}</a> 
    </#list>
  </ul>
  
  <select>
    <#list syscate_tree.all as category>
    <option value='${category.id}'>${category.treeFlag2 + category.name}</option>
    </#list>
  </select>
  </#if>
  
  <h3>最新群组</h3>
  <#if new_group_list??>
  <ul>
    <#list new_group_list as group>
      <li><a href='${SiteUrl}g/${group.groupName}'>${group.groupTitle}</a></li>
    </#list>
  </ul>
  </#if>
  
  <h3>最新主题</h3>
  <#if new_topic_list??>
  <ul>
    <#list new_topic_list as topic>
      <li><a href='#'>${topic.title}</a></li>
    </#list>
  </ul>
  </#if>
  
  <h3>最新回复</h3>
  <#if new_reply_list??>
  <ul>
    <#list new_reply_list as reply>
      <li><a href='#'>${reply.title}</a></li>
    </#list>
  </ul>
  </#if>
  
  <h3>最新文章(包括非群组中的, 暂时未实现只限制发在群组中的)</h3>
  <#if newest_article_list??>
  <ul>
  <#list newest_article_list as article>
    <#assign u = Util.userById(article.userId)>
    <li><a href='${SiteUrl}${u.loginName}/article/${article.articleId}.html'>${article.title!?html}</a> 
     [<a href='${SiteUrl}${u.loginName}'>${u.nickName}</a>][${article.createDate?string('yy-MM-dd hh:mm')}]</li> 
  </#list>
  </ul>
  </#if>
  
  <h3>最热群组</h3>
  <#if hot_group_list??>
  <ul>
	  <#list hot_group_list as group>
	   <li><a href='#'>${group.groupTitle}</a> [${group.visitCount}]</li>
	  </#list>
  </ul>
  </#if>
  
  <h3>最活跃群组</h3>
  <#if active_group_list??>
  <ul>
    <#list active_group_list as group>
     <li><a href='#'>${group.groupTitle}</a> [${group.topicCount}]</li>
    </#list>
  </ul>
  </#if>
  
  <h3>TODO: 群组标签(??)</h3>
  
  
 </body>
</html>
