from base_channel_manage import *

class channelmodulelist(BaseChannelManage):
    def __init__(self):
        BaseChannelManage.__init__(self)
        self.channelId = 0
        self.channel = None
        
    def execute(self):
        self.channelId = self.params.safeGetIntParam("channelId")       
        self.channel = self.channelPageService.getChannel(self.channelId)
        if self.channel == None:
            self.addActionError(u"不能加载频道对象。")
            return self.ERROR
        
        if self.isSystemAdmin() == False and self.isChannelSystemAdmin(self.channel) == False:
            self.addActionError(u"你无权管理本频道。")
            return self.ERROR
        cmd = self.params.safeGetStringParam("cmd")
        if request.getMethod() == "POST":
            if cmd == "delete":
                self.delete()
            elif cmd=="showCount":
                self.saveShowCount()
                
            
        moduleList = self.channelPageService.getChannelModuleList(self.channelId)
        request.setAttribute("channel", self.channel)
        request.setAttribute("moduleList", moduleList)
        return "/WEB-INF/ftl/channel/channelmodulelist.ftl"
    
    def delete(self):
        guids = self.params.safeGetIntValues("guid")
        for g in guids:
            module = self.channelPageService.getChannelModule(g)
            if module != None:
                # 删除模板里面的标签
                indexPageTemplate = self.channel.indexPageTemplate
                headerTemplate = self.channel.headerTemplate
                footerTemplate = self.channel.footerTemplate
                indexPageTemplate = indexPageTemplate.replace("#[" + module.displayName + "]", "")
                headerTemplate = headerTemplate.replace("#[" + module.displayName + "]", "")
                footerTemplate = footerTemplate.replace("#[" + module.displayName + "]", "")
                self.channel.setIndexPageTemplate(indexPageTemplate)
                self.channel.setHeaderTemplate(headerTemplate)
                self.channel.setFooterTemplate(footerTemplate)
                
                self.channelPageService.saveOrUpdateChannel(self.channel)
                self.channelPageService.deleteChannelModule(module)
                
    def saveShowCount(self):
        guids = self.params.safeGetIntValues("md_id")
        for g in guids:
            module = self.channelPageService.getChannelModule(g)
            if module != None:
                showCount = self.params.getIntParamZeroAsNull("md_" + str(g))
                module.setShowCount(showCount)
                self.channelPageService.saveOrUpdateChannelModule(module)