<div class="contentRelation">
  <h3><a href="${ContextPath}resources.action?type=new" class="more">更多</a>最新资源</h3>
    <ul class="ulList">
    <#if new_resource_list?? && new_resource_list?size &gt;0 >
      <#list new_resource_list as resource>
      <li<#if resource.title?size &gt; 13> title="${resource.title?html}"</#if> class="${Util.iconCss(resource.href!)}"><em class="emDate">${resource.createDate?string("MM-dd")}</em><a href="${ContextPath}showResource.action?resourceId=${resource.resourceId}">${Util.getCountedWords(resource.title,14)}</a></li>
      </#list>
    </#if>
    </ul>
</div>