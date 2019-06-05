from base_channel_manage import *
from cn.edustar.jitar.pojos import Channel

class edit_channel(BaseChannelManage):
    def __init__(self):
        BaseChannelManage.__init__(self)
        self.channelId = 0
        self.channel = None
        self.tab = "home"
        
    def execute(self):
        self.channelId = self.params.safeGetIntParam("channelId")
        self.tab = self.params.safeGetStringParam("tab")
        if self.tab == "":
            self.tab = "home"
            
        self.channel = self.channelPageService.getChannel(self.channelId)
        if self.channel == None:
            self.addActionError(u"不能加载频道对象。")
            return self.ERROR
        
        if self.isSystemAdmin() == False and self.isChannelSystemAdmin(self.channel) == False:
            self.addActionError(u"你无权管理本频道。")
            return self.ERROR
            
        # 得到本频道的所有模块，供插入使用
        self.get_module_list()
        if self.channel.skin == None :
            request.setAttribute("skin","template1")
        elif self.channel.skin == "" :
            request.setAttribute("skin","template1")
        else:
            request.setAttribute("skin",self.channel.skin)
            
        request.setAttribute("channel",self.channel)
        request.setAttribute("tab",self.tab)
        if request.getMethod() == "POST":
            if self.tab == "title":
                return self.edit()
            elif self.tab == "home":
                return self.home()
            elif self.tab == "header":
                return self.header()
            elif self.tab == "footer":
                return self.footer()
            elif self.tab == "logo":
                return self.logo()
            elif self.tab == "css":
                return self.css()        

        return "/WEB-INF/ftl/channel/edit_channel.ftl"
    
    def edit(self):        
        title = self.params.safeGetStringParam("title")
        #skin = self.params.safeGetStringParam("skin") #皮肤样式单独模块处理
        headerTemplate = self.params.safeGetStringParam("headerTemplate")
        footerTemplate = self.params.safeGetStringParam("footerTemplate")
        indexPageTemplate = self.params.safeGetStringParam("indexPageTemplate")
        self.channel.setTitle(title)
        #self.channel.setSkin(skin)
        self.channel.setHeaderTemplate(headerTemplate)
        self.channel.setFooterTemplate(footerTemplate)
        self.channel.setIndexPageTemplate(indexPageTemplate)
        self.channelPageService.saveOrUpdateChannel(self.channel)
        request.setAttribute("mode","edit")
        return "/WEB-INF/ftl/channel/editchannelsuccess.ftl"
    
    def home(self):
        indexPageTemplate = self.params.safeGetStringParam("indexPageTemplate")
        self.channel.setIndexPageTemplate(indexPageTemplate)
        self.channelPageService.saveOrUpdateChannel(self.channel)      
        return "/WEB-INF/ftl/channel/edit_channel.ftl"
    
    def header(self):
        headerTemplate = self.params.safeGetStringParam("headerTemplate")
        self.channel.setHeaderTemplate(headerTemplate)
        self.channelPageService.saveOrUpdateChannel(self.channel)
        self.addActionMessage(u"修改成功。")
        return self.SUCCESS
        #return "/WEB-INF/ftl/channel/edit_channel.ftl"
    
    def footer(self):
        footerTemplate = self.params.safeGetStringParam("footerTemplate")
        self.channel.setFooterTemplate(footerTemplate)
        self.channelPageService.saveOrUpdateChannel(self.channel)
        self.addActionMessage(u"修改成功。")
        return self.SUCCESS
        #return "/WEB-INF/ftl/channel/edit_channel.ftl"
    
    def css(self):
        cssStyle = self.params.safeGetStringParam("cssStyle")
        if cssStyle == "" : cssStyle = None
        self.channel.setCssStyle(cssStyle)
        self.channelPageService.saveOrUpdateChannel(self.channel)
        self.addActionMessage(u"修改成功。")
        return self.SUCCESS
        #return "/WEB-INF/ftl/channel/edit_channel.ftl"
    
    def logo(self):
        logo = self.params.safeGetStringParam("logo")
        if logo == "" : logo = None
        self.channel.setLogo(logo)
        self.channelPageService.saveOrUpdateChannel(self.channel)
        self.addActionMessage(u"修改成功。")
        return self.SUCCESS
        #return "/WEB-INF/ftl/channel/edit_channel.ftl"
    
    def get_module_list(self):
        module_list = self.channelPageService.getChannelModuleList(self.channel.channelId)
        request.setAttribute("module_list",module_list)