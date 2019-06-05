<#-- 以JSON格式返回为一组 option 列表 -->
[
<#list unit_list as unit>
  [${unit.unitId}, '${unit.unitName!?js_string}']<#if unit_has_next>,</#if>
</#list>
]
