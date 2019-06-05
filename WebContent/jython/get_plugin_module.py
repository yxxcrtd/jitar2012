
class get_plugin_module:
    def execute(self):
        plugin_svc =  __spring__.getBean("pluginService")
        if plugin_svc == None:
            return        
        plugin_list = plugin_svc.getAllPluginList()
        request.setAttribute("plugin_list", plugin_list)
        return "/WEB-INF/mod/get_plugin_module.ftl"