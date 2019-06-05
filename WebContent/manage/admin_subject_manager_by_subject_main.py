from base_action import *
from cn.edustar.jitar.pojos import AccessControl
from cn.edustar.jitar.jython import BaseAdminAction

class admin_subject_manager_by_subject_main(BaseAdminAction, SubjectMixiner):
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            if accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SUPERADMIN, 0) == False and accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SYSTEMUSERADMIN, 0) == False:      # 没有用户管理权.
                self.addActionError(u"您不具有用户管理权限。")
                return ActionResult.ERROR
        return "/WEB-INF/ftl/admin/admin_subject_manager_by_subject_main.ftl"
