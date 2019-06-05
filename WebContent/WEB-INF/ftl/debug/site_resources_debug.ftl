<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>resources.py 调试页面</title>
 </head>
 <body>
  <h2>resources.py 调试页面</h2>
  
  
  <h3>FreeQuery Test</h3>
  ${Util.dataBean('freeQuery')}
  <#list fq_subject_list as subject>
    <li>subject = ${subject}</li>
  </#list>
  
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
  
  <h3>资源上载排行</h3>
  <#if upload_user_list??>
    <ul>
      <#list upload_user_list as user>
        <li><a href='${SiteUrl}${user.loginName}'>${user.loginName}</a> [${user.resourceCount!}]</li>
      </#list>
    </ul>
  </#if>
  
  <h3>资源标签</h3>
  
  <h3>资源分类</h3>
  <#if syscate_tree??>
    <ul>
      <#list syscate_tree.all as category>
        <li>${category.treeFlag2 + category.name}</li>
      </#list>
    </ul>
  </#if>
  
 </body>
</html>
