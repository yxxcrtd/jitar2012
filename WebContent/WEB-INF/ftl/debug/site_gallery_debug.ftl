<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 </head>
 <body>
  <h2>photos.action 相册中心调试页面</h2>
  
  <h3>最新相片</h3>
  <#if new_photo_list??>
    <ul>
      <#list new_photo_list as photo>
        <li>${photo.title}, src=${photo.href}</li>
      </#list>
    </ul>
  </#if>

  <h3>图片排行(点击数)</h3>
  <#if hot_photo_list??>
    <ul>
      <#list hot_photo_list as photo>
        <li>${photo.title}, src=${photo.href}, hit=${photo.viewCount}</li>
      </#list>
    </ul>
  </#if>
  
  <h3>最新 n 个标签</h3>
  <#if new_tag_list??>
    <ul>
      <#list new_tag_list as tag>
        <a href='${SiteUrl}showTag.action?tagId=${tag.tagId}'>${tag.tagName}</a>
      </#list>
    </ul>
  </#if>
  
  <h3>相册分类1</h3>
  <#if photo_list_1??>
    <ul>
      <#list photo_list_1 as photo>
        <li>${photo.title}, src=${photo.href}, sysCateId=${photo.sysCateId!}</li>
      </#list>
    </ul>
  </#if>
  
  <h3>相册分类2, 3 同1</h3>
  
 </body>
</html>
