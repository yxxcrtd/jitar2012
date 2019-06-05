<#if gres_categories??>
<#if gres_categories?size == 0>
  []
<#else>
  [
  <#list gres_categories.all as c>
    {id:${c.categoryId},path:"${Util.convertIntFrom36To10(c.parentPath) + c.id?string + "/"}", name:'${c.name!?js_string}', treeFlag2:'${c.treeFlag2!?js_string}'}<#if c_has_next>, </#if>
  </#list>
  ]
</#if>
<#else>
[]
</#if>