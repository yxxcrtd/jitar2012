<#if unit_list??><#list unit_list as u>${u.unitId}__${u.unitName?js_string}<#if u_has_next>||</#if></#list></#if>