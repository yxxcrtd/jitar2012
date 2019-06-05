from base_channel_manage import *
from cn.edustar.jitar.pojos import Channel
from cn.edustar.jitar.pojos import SiteNav
from cn.edustar.jitar.pojos import ChannelModule
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.model import SiteUrlModel

class add_channel(BaseChannelManage):
    def __init__(self):
        BaseChannelManage.__init__(self)
        
    def execute(self):
        # 本页面需要系统管理员
        if self.isSystemAdmin() == False:
            self.addActionError(u" 你没有管理的权限。需要系统管理员权限。")
            return self.ACCESS_ERROR
        channelId = self.params.safeGetIntParam("channelId")
        channel = self.channelPageService.getChannel(channelId)
        
        if request.getMethod() == "POST":
            isNew = False
            title = self.params.safeGetStringParam("title")
            if title == "":
                self.addActionError(u"标题不能为空。")
                return self.ERROR
            if channel == None:
                isNew = True
                channel = Channel()
                channel.setTitle(title)
                channel.setSkin("")
                channel.setHeaderTemplate("")
                channel.setFooterTemplate("")
                channel.setIndexPageTemplate("")
            else:
                channel.setTitle(title)
            
            self.channelPageService.saveOrUpdateChannel(channel)
            # 创建导航
            # 对于新建的频道，将增加系统模块、系统模板、系统导航等内容，
            if isNew:
                # 增加导航
                siteNavService = __spring__.getBean("siteNavService")
                siteName = [u"总站首页", u"频道首页", u"频道工作室", u"频道协助组", u"频道文章", u"频道资源", u"频道视频", u"频道图片"]
                siteUrlArray = ["index.py",
                           "channel/channel.action?channelId=" + str(channel.channelId),
                           "channel/channel.action?cmd=userlist&channelId=" + str(channel.channelId),
                           "channel/channel.action?cmd=grouplist&channelId=" + str(channel.channelId),
                           "channel/channel.action?cmd=articlelist&channelId=" + str(channel.channelId),
                           "channel/channel.action?cmd=resourcelist&channelId=" + str(channel.channelId),
                           "channel/channel.action?cmd=videolist&channelId=" + str(channel.channelId),
                           "channel/channel.action?cmd=photolist&channelId=" + str(channel.channelId)
                           ]
                siteHightlightArray = ["index", "channel", "user","group","article", "resource", "video", "photo"]
                for i in range(0, len(siteName)):
                    #先判断是否存在
                    siteNav = siteNavService.getSiteNavByName(SiteNav.SITENAV_OWNERTYPE_CHANNEL,channel.channelId, siteName[i])
                    if siteNav == None:
                        siteNav = SiteNav()
                        siteNav.setSiteNavName(siteName[i])
                        siteNav.setIsExternalLink(False)
                        siteNav.setSiteNavUrl(siteUrlArray[i])
                        siteNav.setSiteNavIsShow(True)
                        siteNav.setSiteNavItemOrder(i)
                        siteNav.setCurrentNav(siteHightlightArray[i])
                        siteNav.setOwnerType(SiteNav.SITENAV_OWNERTYPE_CHANNEL)
                        siteNav.setOwnerId(channel.channelId)
                        siteNavService.saveOrUpdateSiteNav(siteNav)
                
                self.addSystemModule(channel)
                self.addSystemTemplate(channel)                
            
            request.setAttribute("channel", channel)        
            return "/WEB-INF/ftl/channel/editchannelsuccess.ftl"
        request.setAttribute("channel", channel)
        return "/WEB-INF/ftl/channel/add_channel.ftl"
    
    def getFileContent(self, fp):
        ctx = request.getSession().getServletContext()
        filePath = ctx.getRealPath(fp)
        return CommonUtil.readFileToString(filePath, "utf-8")
    
    def addSystemModule(self, channel):
        # 这种写法便于修改哈
        sysModuleArray = [
                          {"displayName":u"文章列表", "moduleName":"ArticleList", "showCount":"10"},
                          {"displayName":u"上传资源列表", "moduleName":"ResourceList", "showCount":"10"},
                          {"displayName":u"文章分类列表", "moduleName":"ArticleCategory", "showCount":"0"},
                          {"displayName":u"资源分类列表", "moduleName":"ResourceCategory", "showCount":"0"},
                          {"displayName":u"网站公告", "moduleName":"Bulletin", "showCount":"10"},
                          {"displayName":u"搜索", "moduleName":"Search", "showCount":"0"},
                          {"displayName":u"学科导航", "moduleName":"SubjectNav", "showCount":"0"},
                          {"displayName":u"频道导航", "moduleName":"ChannelNav", "showCount":"0"},
                          {"displayName":u"总站导航", "moduleName":"SiteNav", "showCount":"0"},
                          {"displayName":u"频道统计", "moduleName":"Stat", "showCount":"0"},
                          {"displayName":u"视频列表", "moduleName":"VideoList", "showCount":"10"},
                          {"displayName":u"视频分类", "moduleName":"VideoCategory", "showCount":"0"},
                          {"displayName":u"图片列表", "moduleName":"PhotoList", "showCount":"10"},
                          {"displayName":u"图片分类", "moduleName":"PhotoCategory", "showCount":"0"},
                          {"displayName":u"工作室列表", "moduleName":"UserList", "showCount":"10"},
                          {"displayName":u"协助组列表", "moduleName":"GroupList", "showCount":"10"},
                          {"displayName":u"频道Logo", "moduleName":"Logo", "showCount":"0"}
                          ]
        
        for i in range(0, len(sysModuleArray)):                 
            module = ChannelModule()
            if sysModuleArray[i]["showCount"] != "0":
                module.setShowCount(int(sysModuleArray[i]["showCount"]))
            module.setChannelId(channel.channelId)
            module.setDisplayName(sysModuleArray[i]["displayName"])
            module.setModuleType(sysModuleArray[i]["moduleName"])
            template = self.getFileContent("/WEB-INF/channel/template1/" + sysModuleArray[i]["moduleName"] + ".ftl")
            module.setTemplate(template)
            module.setPageType("main")
            self.channelPageService.saveOrUpdateChannelModule(module)
    
    def addSystemTemplate(self, channel):
        indexPageTemplate = self.getFileContent("/WEB-INF/channel/template1/Main.ftl")
        headerTemplate = self.getFileContent("/WEB-INF/channel/template1/Header.ftl")
        footerTemplate = self.getFileContent("/WEB-INF/channel/template1/Footer.ftl")
        cssStyle = self.getFileContent("/css/channel/template1/common.css")
        cssStyle = cssStyle.replace("background:url(", "background:url(../css/channel/")
        channel.setIndexPageTemplate(indexPageTemplate)
        channel.setHeaderTemplate(headerTemplate)
        channel.setFooterTemplate(footerTemplate)
        channel.setCssStyle(cssStyle)
        channel.setLogo(SiteUrlModel.getSiteUrl() + "images/banner.swf")
        self.channelPageService.saveOrUpdateChannel(channel)
