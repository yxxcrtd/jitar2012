<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 </head>
 <body>
  <h2>showCategoryResource.action 调试页面</h2>
  
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
  
  <h3>分类资源</h3>
  <#if cate_resource_list??>
    <ul>
    <#list cate_resource_list as resource>
      <li><a href='showResource.action?resourceId=${resource.resourceId}'>${resource.title}</a></li>
    </#list>
    </ul>
    <div>
      <#include ('../inc/pager.ftl') >
    </div>
  </#if>
  
  
 </body>
</html>
