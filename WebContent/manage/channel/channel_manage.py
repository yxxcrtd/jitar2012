from base_channel_manage import *
from cn.edustar.jitar.data import Command
from java.util import ArrayList

# 单个频道管理界面
class channel_manage(BaseChannelManage):
    def __init__(self):
        BaseChannelManage.__init__(self)
        
    def execute(self):
        channelId = self.params.safeGetIntParam("channelId")
        channel = self.channelPageService.getChannel(channelId)
        if channel == None:
            self.addActionError(u"无法加载频道。")
            return self.ERROR
        response.sendRedirect(request.getContextPath() + "/manage/channel/channel.action?cmd=manage&channelId=" + str(channelId))
        return
        AdminType = self.GetAdminType(channel)
        if AdminType == "":
            self.addActionError(u"你无权管理频道。")
            return self.ERROR
        
        cmd = request.getParameter("cmd")        
        request.setAttribute("AdminType",AdminType)
        request.setAttribute("channel",channel)
        if cmd == "menu":      
            return "/WEB-INF/ftl/channel/channel_menu.ftl"
        elif cmd == "main":
            return "/WEB-INF/ftl/channel/main.ftl"
        elif cmd == "head":
            return "/WEB-INF/ftl/channel/head.ftl"
        else:
            return "/WEB-INF/ftl/channel/channel_manage.ftl"