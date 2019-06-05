from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import AccessControl
from base_action import *
from cn.edustar.jitar.jython import BaseAdminAction

class admin_content_manager_add(BaseAdminAction, SubjectMixiner):
    ADMIN_MAIN = "/WEB-INF/ftl/admin/main.ftl"
    ERROR = "/WEB-INF/ftl/Error.ftl"    
    SUCCESS = "/WEB-INF/ftl/success.ftl"
    
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            if accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SUPERADMIN, 0) == False and accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SYSTEMUSERADMIN, 0) == False:      # 没有用户管理权.
                self.addActionError(u"您不具有用户管理权限。")
                return ActionResult.ERROR
            
        self.params = ParamUtil(request)
        if request.getMethod() == "POST":
            accessControlService = __spring__.getBean("accessControlService")
            uid = self.params.safeGetIntValues("uid")
            if len(uid) < 1:
                self.addActionError(u"请选择一个用户.")
                return ActionResult.ERROR
            for userId in uid:
                # 检查是否已经是管理员
                isAdmin = accessControlService.checkUserAccessControlIsExists(userId, AccessControl.OBJECTTYPE_SYSTEMCONTENTADMIN, 0)
                # print "isAdmin = ", isAdmin
                if False == isAdmin:                        
                    accessControl = AccessControl()
                    accessControl.setUserId(userId)
                    accessControl.setObjectType(AccessControl.OBJECTTYPE_SYSTEMCONTENTADMIN)
                    accessControl.setObjectId(0)
                    accessControl.setObjectTitle(u"整站范围")
                    accessControlService.saveOrUpdateAccessControl(accessControl)
            response.sendRedirect("admin_content_manager.py")
            
        return "/WEB-INF/ftl/admin/admin_content_manager_add.ftl"
