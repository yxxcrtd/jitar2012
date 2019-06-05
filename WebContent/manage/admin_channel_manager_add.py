from base_channel_manage import BaseChannelManage
from cn.edustar.jitar.pojos import AccessControl

class admin_channel_manager_add(BaseChannelManage):
    def __init__(self):
        BaseChannelManage.__init__(self)
        self.channelPageService = __spring__.getBean("channelPageService")
        self.channel = None
        
    def execute(self):
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:            
            if accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SUPERADMIN, 0) == False and accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SYSTEMUSERADMIN, 0) == False:      # 没有用户管理权.
                self.addActionError(u"您不具有用户管理权限。")
                return ActionResult.ERROR
            
        channelId = self.params.safeGetIntParam("channelId")
        self.channel = self.channelPageService.getChannel(channelId)
        if self.channel == None:
            self.addActionError(u"无法加载频道。")
            return self.ERROR
        
        if request.getMethod() == "POST":
            accessControlService = __spring__.getBean("accessControlService")
            controlType = self.params.safeGetStringParam("controlType")
            uid = self.params.safeGetIntValues("uid")
            if not(controlType == "11" or controlType == "12" or controlType == "13"):
                self.addActionError(u"请选择管理员类型.")
                return self.ERROR
            if len(uid) < 1:
                self.addActionError(u"请选择一个用户.")
                return self.ERROR
            for userId in uid:
                # 检查是否已经是管理员
                if False == accessControlService.checkUserAccessControlIsExists(userId, int(controlType), self.channel.channelId):                        
                    accessControl = AccessControl()
                    accessControl.setUserId(userId)
                    accessControl.setObjectType(int(controlType))
                    accessControl.setObjectId(self.channel.channelId)
                    accessControl.setObjectTitle(self.channel.title)
                    accessControlService.saveOrUpdateAccessControl(accessControl)
            response.sendRedirect("admin_channel_manager_by_channel_right.py?channelId=" + str(self.channel.channelId))
            
        request.setAttribute("channel", self.channel)
        
        return "/WEB-INF/ftl/admin/admin_channel_manager_add.ftl"
