from base_channel_manage import BaseChannelManage
from cn.edustar.jitar.pojos import AccessControl

class admin_channel_manager_by_channel_left(BaseChannelManage):
    def __init__(self):
        BaseChannelManage.__init__(self)
        self.channelPageService = __spring__.getBean("channelPageService")
        self.url = "admin_channel_manager_by_channel_right.py"
        
    def execute(self):
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:            
            if accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SUPERADMIN, 0) == False and accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SYSTEMUSERADMIN, 0) == False:      # 没有用户管理权.
                self.addActionError(u"您不具有用户管理权限。")
                return self.ERROR
            
        channel_list = self.channelPageService.getChannelList()
        html = ""
        for c in channel_list:
            html = html + "d.add(" + str(c.channelId) + ",-1,'" + c.title + "','" + self.url + "?channelId=" + str(c.channelId) + "','" + c.title + "','subjectmain');\r\n"
        request.setAttribute("channel_list", channel_list)
        request.setAttribute("html", html)
        return "/WEB-INF/ftl/admin/admin_channel_manager_by_channel_left.ftl"
