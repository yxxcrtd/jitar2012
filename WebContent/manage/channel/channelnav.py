from base_channel_manage import *

class channelnav(BaseChannelManage):
    def __init__(self):
        BaseChannelManage.__init__(self)
        self.channelId = 0
        self.channel = None
        self.siteNavService = __spring__.getBean("siteNavService")
        
    def execute(self):
        self.channelId = self.params.safeGetIntParam("channelId")       
        self.channel = self.channelPageService.getChannel(self.channelId)
        if self.channel == None:
            self.addActionError(u"不能加载频道对象。")
            return self.ERROR
        if self.isSystemAdmin() == False and self.isChannelSystemAdmin(self.channel) == False:
            self.addActionError(u"你无权管理本频道。")
            return self.ERROR
        if request.getMethod() == 'POST':
            cmd = self.params.safeGetStringParam("cmd")
            guids = self.params.safeGetIntValues("guid")
            if cmd == "delete":
                for guid in guids:
                    siteNav = self.siteNavService.getSiteNavById(guid)
                    if siteNav != None:
                        if siteNav.isExternalLink == True:
                            self.siteNavService.deleteSiteNav(siteNav)
            if cmd == "order":
                guids = self.params.safeGetIntValues("nav_id")
                for guid in guids:
                    order = self.params.safeGetIntParam("z_" + str(guid))
                    siteNav = self.siteNavService.getSiteNavById(guid)
                    if siteNav != None:
                        siteNav.setSiteNavItemOrder(order)
                        self.siteNavService.saveOrUpdateSiteNav(siteNav)                
            if cmd == "display":
                siteId = self.params.safeGetIntParam("siteId")
                siteDisplay = self.params.safeGetIntParam("siteDisplay")
                siteNav = self.siteNavService.getSiteNavById(siteId)
                if siteNav != None:
                    if siteDisplay == 1:
                        siteNav.setSiteNavIsShow(True)
                    else:
                        siteNav.setSiteNavIsShow(False)
                    self.siteNavService.saveOrUpdateSiteNav(siteNav)        
            cache = __jitar__.cacheProvider.getCache('sitenav')
            if cache != None:                    
                cache_list = cache.getAllKeys()
                cache_key = "channel_nav_" + str(self.channel.channelId)
                for c in cache_list:
                    if c == cache_key:
                        cache.remove(c)
        request.setAttribute("channel",self.channel)
        self.get_site_nav_list()
        return "/WEB-INF/ftl/channel/channelnav.ftl"
    
    def get_site_nav_list(self):
        siteNavItemList = self.siteNavService.getAllSiteNav(True, 3, self.channel.channelId)
        request.setAttribute("siteNavItemList", siteNavItemList)