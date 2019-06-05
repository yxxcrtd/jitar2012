<#-- 以JSON格式返回为一组 option 列表 -->
[
<#list subject_list as sub>
  [${sub.msubjId}, '${sub.msubjName!?js_string}']<#if sub_has_next>,</#if>
</#list>
]
