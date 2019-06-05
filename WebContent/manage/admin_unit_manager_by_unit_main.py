from base_action import *
from cn.edustar.jitar.pojos import AccessControl
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import ParamUtil, CommonUtil

class admin_unit_manager_by_unit_main(BaseAdminAction, SubjectMixiner):
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            if accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SUPERADMIN, 0) == False and accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SYSTEMUSERADMIN, 0) == False:      # 没有用户管理权.
                self.addActionError(u"您不具有用户管理权限, 或未经审核?")
                return ActionResult.ERROR

        self.unitService = __spring__.getBean("unitService")
        rootUnit = self.unitService.getRootUnit()
        if rootUnit == None:
            self.addActionError(u"没有根单位，在使用系统之前，需要先<a href='" + CommonUtil.getSiteUrl(request) + "add_root_unit.py' target='_top'>创建一个根单位</a>。")
            return ActionResult.ERROR
        request.setAttribute("rootUnit", rootUnit)
        return "/WEB-INF/ftl/admin/admin_unit_manager_by_unit_main.ftl"