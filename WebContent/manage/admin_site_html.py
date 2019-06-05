from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Config, User
from base_action import ActionResult
from java.lang import String

class admin_site_html(BaseAdminAction, ActionResult):
    def __init__(self):
        self.cfg_svc = __spring__.getBean('configService')
        self.accessControlService = __spring__.getBean('accessControlService') 

    def execute(self):
        if self.loginUser == None: 
            return ActionResult.LOGIN
    
        if self.accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"没有管理站点配置的权限，只有超级管理员才能进行管理。")
            return ActionResult.ERROR        
        return "/WEB-INF/ftl/admin/admin_site_html.ftl"