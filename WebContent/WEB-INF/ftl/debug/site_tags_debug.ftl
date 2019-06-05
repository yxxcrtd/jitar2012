<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 </head>
 <body>
  <h2>tags.py 调试页面</h2>
  
  <h3>所有标签列表</h3>
  <#list tag_list as tag>
    <a href='${SiteUrl}showTag.action?tagId=${tag.tagId}' title='引用次数: ${tag.refCount}, 查看次数: ${tag.viewCount}' >${tag.tagName}</a>
  </#list>
  
 </body>
</html>
