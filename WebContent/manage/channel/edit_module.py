from base_channel_manage import *
from cn.edustar.jitar.pojos import ChannelModule


class edit_module(BaseChannelManage):
    def __init__(self):
        BaseChannelManage.__init__(self)
        self.channelId = 0
        self.channel = None
        
    def execute(self):
        self.channelId = self.params.safeGetIntParam("channelId")
        moduleId = self.params.safeGetIntParam("moduleId")
        self.channel = self.channelPageService.getChannel(self.channelId)
        channelModule = self.channelPageService.getChannelModule(moduleId)
        if self.channel == None:
            self.addActionError(u"不能加载频道对象。")
            return self.ERROR
        if self.isSystemAdmin() == False and self.isChannelSystemAdmin(self.channel) == False:
            self.addActionError(u"你无权管理本频道。")
            return self.ERROR
        
        
        if request.getMethod() == "POST":
            displayName = self.params.safeGetStringParam("moduleDisplayName")
            moduleType = self.params.safeGetStringParam("moduleType")
            if displayName == "":
                self.addActionError(u"模块名称不能为空。")
                return self.ERROR
            
            if moduleType == "":
                self.addActionError(u"模块名称不能为空。")
                return self.ERROR
            if channelModule == None:
                channelModule = ChannelModule()
            channelModule.setChannelId(self.channelId)
            channelModule.setDisplayName(displayName)
            channelModule.setModuleType(moduleType)
            channelModule.setPageType("main")
            self.channelPageService.saveOrUpdateChannelModule(channelModule)
            if moduleId == 0:
                self.addActionMessage(u"添加成功。")
            else:
                self.addActionMessage(u"修改成功。")
            self.addActionLink(u"返回模块管理","channelmodulelist.py?channelId=" + str(self.channelId))
            return self.SUCCESS
        
        request.setAttribute("channel",self.channel)
        if channelModule != None:
            request.setAttribute("channelModule",channelModule)
        return "/WEB-INF/ftl/channel/edit_module.ftl"