from base_action import *
from base_action import ActionResult
from cn.edustar.jitar.pojos import User
from cn.edustar.jitar.pojos import Plugin
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.util import ParamUtil
from javax.servlet import ServletContext
from cn.edustar.jitar.model import SiteUrlModel
from java.io import File

class system_plugin(BaseAdminAction):
    ADMIN_MAIN = "/WEB-INF/ftl/admin/main.ftl"
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.plugin_svc =  __spring__.getBean("pluginService")
        self.pluginid = 0        
        
    def execute(self):
        user = self.loginUser
        if user == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(user) == False:
            self.addActionError(u"没有管理权限.")
            return ActionResult.ERROR        
        self.pluginid = self.params.getIntParam("pluginId")
        if request.getMethod() == "POST":
            cmd = self.params.safeGetStringParam("cmd")
            if cmd == "new":
                self.save_or_update()
            elif cmd=="edit":
                self.save_or_update()
            elif cmd == "enabled":
                self.enable_plugin()
            elif cmd == "disabled":
                self.disabled_plugin()
            elif cmd == "delete":
                self.delete_plugin()
            else:
                return self.get_form()
        
        return self.get_form()
     
    def get_form(self):
        if self.pluginid != 0:
            plugin = self.plugin_svc.getPluginById(self.pluginid)
            request.setAttribute("plugin", plugin)   
            
        plugin_list = self.plugin_svc.getAllPluginList()
        request.setAttribute("plugin_list", plugin_list)
        return "/WEB-INF/ftl/admin/system_plugin.ftl"
    
    def save_or_update(self):
        pluginName = self.params.safeGetStringParam("pluginName")
        pluginTitle = self.params.safeGetStringParam("pluginTitle")
        pluginIcon = self.params.safeGetStringParam("pluginIcon")
        if pluginName == "" or pluginTitle == "":
            self.addActionError(u"请输入英文和中文名称。")
            return ActionResult.ERROR
        if self.pluginid == 0:
            plugin = Plugin()            
            plugin.setItemOrder(0)
            plugin.setEnabled(1)
            plugin.setPluginType("system")
        else:
            plugin = self.plugin_svc.getPluginById(self.pluginid)
            
        plugin.setPluginName(pluginName)
        plugin.setPluginTitle(pluginTitle)
        if pluginIcon != "":
            plugin.setIcon(pluginIcon)
            
        self.plugin_svc.saveOrUpdatePlugin(plugin)
    def enable_plugin(self):
        guid = self.params.safeGetIntValues("plugin_id")
        for id in guid:
            plugin = self.plugin_svc.getPluginById(id)
            if plugin != None:
                plugin.setEnabled(1)
                self.plugin_svc.saveOrUpdatePlugin(plugin)
    def disabled_plugin(self):
        guid = self.params.safeGetIntValues("plugin_id")
        for id in guid:
            plugin = self.plugin_svc.getPluginById(id)
            if plugin != None:
                plugin.setEnabled(0)
                self.plugin_svc.saveOrUpdatePlugin(plugin)
    def delete_plugin(self):
        guid = self.params.safeGetIntValues("plugin_id")
        for id in guid:
            plugin = self.plugin_svc.getPluginById(id)
            if plugin != None:
                self.plugin_svc.deletePlugin(plugin)