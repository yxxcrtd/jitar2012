<table id='hot_tag_list' width='100%' cellspacing='1' border='0'>
 <thead>
  <tr>
   <th>文章标签</th>
   <th>引用数</th>
   <th>人气</th>
  </tr>
 </thead>
 <tbody>
 <#list tag_list as tag>
  <tr>
   <td><a href='${SiteUrl}s/tag/${tag.tagName?url}'>${tag.tagName?html}</a></td>
   <td>${tag.refCount}</td>
   <td>${tag.viewCount!}</td>
  </tr>
 </#list>
 </tbody>
</table>
