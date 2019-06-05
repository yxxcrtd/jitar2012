<#if plugin_list?? >
<#list plugin_list as p>
App.predefinePluginModule({name: '${p.pluginName?js_string}', title:'${p.pluginTitle?js_string}', ico:'${p.icon!}'});
</#list>
</#if>