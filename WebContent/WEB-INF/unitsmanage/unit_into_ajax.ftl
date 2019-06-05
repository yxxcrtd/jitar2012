<#-- 以JSON格式返回为-->
[
<#if unit??>
  [${unit.unitId},'${unit.unitTitle!?js_string}','${unit.unitPhoto!?js_string}', '${unit.unitInfo!?js_string}']
</#if>
]
