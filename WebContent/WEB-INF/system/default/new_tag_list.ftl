<div id='new_tag_list'>
<#list tag_list as tag>
  <a href='${SiteUrl}s/tag/${tag.tagName?url}'>${tag.tagName?html}</a>
</#list>
</div>
