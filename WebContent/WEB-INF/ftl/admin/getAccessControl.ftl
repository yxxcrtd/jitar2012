<#if aclist??>[
<#list aclist as ac>
{"accessControlId":"${ac.accessControlId}","UserId":"${ac.userId}","ObjectType":"${ac.objectType}","ObjectId":"${ac.objectId}","ObjectTitle":"${ac.objectTitle?js_string}"}
<#if ac_has_next>,</#if>
</#list>
]</#if>