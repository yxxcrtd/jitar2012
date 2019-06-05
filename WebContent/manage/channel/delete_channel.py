from base_channel_manage import *

class delete_channel(BaseChannelManage):    
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
        
        
        channelId = self.params.safeGetIntParam("channelId")
        channel = self.channelPageService.getChannel(channelId)
        if channel == None:
            self.addActionError(u" 无法加载频道对象对象。")
            return self.ERROR
        #先删除相关的下级内容
        self.channelPageService.deleteChannelArticle(channelId)
        self.channelPageService.deleteChannelResource(channelId)
        self.channelPageService.deleteChannelPhoto(channelId)
        self.channelPageService.deleteChannelVideo(channelId)
        self.channelPageService.deleteChannelOtherData(channelId)
        self.channelPageService.deleteChannel(channel)
        return "/WEB-INF/ftl/channel/editchannelsuccess.ftl"