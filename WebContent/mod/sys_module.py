from cn.edustar.jitar.util import ParamUtil

class sys_module:
    def execute(self):
        self.params = ParamUtil(request)
        self.plugin_svc =  __spring__.getBean("pluginService")
        
        type = self.params.safeGetStringParam("type")
        if type != "":
            request.setAttribute("module_type", type)
            
        plugin_list = self.plugin_svc.getPluginList()
        request.setAttribute("plugin_list", plugin_list)
        return "/WEB-INF/mod/sys_module.ftl"