from base_channel_manage import *
from cn.edustar.jitar.pojos import SiteNav

class channelnav_add(BaseChannelManage):
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
        siteNavId = self.params.safeGetIntParam("siteNavId")
        siteNav = self.siteNavService.getSiteNavById(siteNavId)
        
        if request.getMethod() == "POST":
            siteName = self.params.safeGetStringParam("siteName")
            siteUrl = self.params.safeGetStringParam("siteUrl")
            siteShow = self.params.safeGetIntParam("siteShow")
            siteOrder = self.params.safeGetIntParam("siteOrder")
            if siteName == "":
                self.addActionError(u"请输入导航名称.")
                return self.ERROR
            
            if siteUrl == "" and siteNav != None and siteNav.isExternalLink == True:
                self.addActionError(u"请输入导航地址.")
                return self.ERROR            
            if siteNav == None:
                siteNav = SiteNav()
                siteNav.setIsExternalLink(True)
                siteNav.setOwnerType(3)
                siteNav.setOwnerId(self.channel.channelId)
            if siteNav.isExternalLink == True:
                siteNav.setSiteNavUrl(siteUrl)                
            siteNav.setSiteNavName(siteName)            
            if siteShow == 1:
                siteNav.setSiteNavIsShow(True)
            else:
                siteNav.setSiteNavIsShow(False)

            siteNav.setSiteNavItemOrder(siteOrder)
            self.siteNavService.saveOrUpdateSiteNav(siteNav)
            cache = __jitar__.cacheProvider.getCache('sitenav')
            if cache != None:                    
                cache_list = cache.getAllKeys()
                cache_key = "channel_nav_" + str(self.channel.channelId)
                for c in cache_list:
                    if c == cache_key:
                        cache.remove(c)
            self.addActionMessage(u"您成功编辑了一个自定义导航： " + siteName)
            self.addActionLink(u"返回", "channelnav.py?channelId=" + str(self.channel.channelId))
            return self.SUCCESS
        
        if siteNav != None:
            request.setAttribute("siteNav", siteNav)
        return "/WEB-INF/ftl/channel/channelnav_add.ftl"