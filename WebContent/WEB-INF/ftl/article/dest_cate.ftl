<#if group_cate??>
<#if group_cate?size == 0>
  <#-- 该协作组尚未建立文章分类 -->
  []
<#else>
  [
  <#list group_cate.all as c>
    {id:${c.categoryId}, name:'${c.name!?js_string}', treeFlag2:'${c.treeFlag2!?js_string}'}<#if c_has_next>, </#if>
  </#list>
  ]
</#if>
<#else>
[]
</#if>