<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 </head>
 <body>
  <h2>searchResourceList.action 调试页面</h2>
  
  <h3>资源列表</h3>
  <#if resource_list??>
    <ul>
    <#list resource_list as resource>
      <li><a href='showResource.action?resourceId=${resource.resourceId}' target='_blank'>${resource.title}</a></li>
    </#list>
    </ul>
    <div>
      <#include ('inc/pager.ftl') >
    </div>
  </#if>
  
  
 </body>
</html>
