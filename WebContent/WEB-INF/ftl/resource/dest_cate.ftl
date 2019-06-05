<#if gres_categories??>
<#if gres_categories?size == 0>
  <#-- 该协作组尚未建立资源分类 -->
  []
<#else>
  [
  <#list gres_categories.all as c>
    {id:${c.categoryId}, name:'${c.name!?js_string}', treeFlag2:'${c.treeFlag2!?js_string}'}<#if c_has_next>, </#if>
  </#list>
  ]
</#if>
<#else>
[]
</#if>