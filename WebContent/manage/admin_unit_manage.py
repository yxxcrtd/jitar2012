from cn.edustar.jitar.jython import BaseAdminAction
from base_action import ActionResult

class admin_unit_manage(BaseAdminAction, ActionResult):
    def execute(self):
        
        if self.loginUser == None:
            return ActionResult.LOGIN
        
        self.unitService = __spring__.getBean("unitService")
        rootUnit = self.unitService.getRootUnit()
        #if len(u_list) < 1:
        #    rootUnit = None
        #else:
        #   rootUnit = u_list[0]
        
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"没有管理的权限.")
            return ActionResult.ERROR
        
        request.setAttribute("rootUnit", rootUnit)
        return "/WEB-INF/ftl/admin/admin_unit_manage.ftl";

