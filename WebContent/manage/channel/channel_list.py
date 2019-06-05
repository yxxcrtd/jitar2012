from base_channel_manage import *

class channel_list(BaseChannelManage):
    def __init__(self):
        BaseChannelManage.__init__(self)
        
    def execute(self):
        if self.channelPageService == None:
            self.addActionError(u"无法加载 channelPageService 对象，配置错误。")
            return self.ERROR
        
        # 本页面需要系统管理员
        if self.isSystemAdmin() == False:
            self.addActionError(u" 你没有管理的权限。需要系统管理员权限。")
            return self.ACCESS_ERROR
        
        if request.getMethod() == "POST":
            cmd = self.params.safeGetStringParam("cmd")
            if cmd == "delete":
                guids = self.params.safeGetIntValues("guid")
                for g in guids:
                    channel = self.channelPageService.getChannel(g)
                    if channel != None:
                        self.channelPageService.deleteChannel(channel)                
                return "/WEB-INF/ftl/channel/editchannelsuccess.ftl"
            
        channel_list = self.channelPageService.getChannelList()
        request.setAttribute("channel_list",channel_list)
        return "/WEB-INF/ftl/channel/channel_list.ftl"
        
            