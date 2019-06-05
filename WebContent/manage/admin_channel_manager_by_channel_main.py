from base_channel_manage import BaseChannelManage
from cn.edustar.jitar.pojos import AccessControl

class admin_channel_manager_by_channel_main(BaseChannelManage):
    def __init__(self):
        BaseChannelManage.__init__(self)
        
    def execute(self):        
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            if accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SUPERADMIN, 0) == False and accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SYSTEMUSERADMIN, 0) == False:      # 没有用户管理权.
                self.addActionError(u"您不具有用户管理权限。")
                return self.ERROR
        return "/WEB-INF/ftl/admin/admin_channel_manager_by_channel_main.ftl"
