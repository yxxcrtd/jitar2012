<div class="contentRelation">
  <h3><a href="${ContextPath}resources.action?type=hot" class="more">更多</a>热门资源排行</h3>
    <ul class="ulList">
    <#if hot_resource_list?? && hot_resource_list?size &gt;0 >
      <#list hot_resource_list as resource>
      <li<#if resource.title?size &gt; 13> title="${resource.title?html}"</#if>><em class="emDate">${resource.createDate?string("MM-dd")}</em><span class="numIcon<#if resource_index &lt; 3> numRed</#if>">${resource_index+1}</span><a href="${ContextPath}showResource.action?resourceId=${resource.resourceId}">${Util.getCountedWords(resource.title,14)}</a></li>
      </#list>
    </#if>
    </ul>
</div>
