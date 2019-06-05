from base_channel_manage import *
from java.io import File
from javax.servlet import ServletContext
from cn.edustar.jitar.pojos import ChannelModule

class channel_skins(BaseChannelManage):
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
        if request.getMethod() == "POST":
            tmpl = self.params.safeGetStringParam("tmpl")
            if tmpl == "" :
                self.channel.setSkin("")
                skin = "template1"
            else:
                self.channel.setSkin(tmpl)
                skin = tmpl
            #选择了样式，需要把样式对应的模块以及页面内容写入到channel中
            headerTemplate = self.getFileContent("/WEB-INF/channel/"+skin+"/Header.ftl")
            footerTemplate = self.getFileContent("/WEB-INF/channel/"+skin+"/Footer.ftl")
            indexPageTemplate = self.getFileContent("/WEB-INF/channel/"+skin+"/Main.ftl")
            cssStyle = self.getFileContent("/css/channel/" + skin + "/common.css")
            cssStyle = cssStyle.replace("background:url(", "background:url(../css/channel/" + skin + "/")
            self.channel.setHeaderTemplate(headerTemplate)
            self.channel.setFooterTemplate(footerTemplate)
            self.channel.setIndexPageTemplate(indexPageTemplate)
            self.channel.setCssStyle(cssStyle)
            self.channelPageService.saveOrUpdateChannel(self.channel)    
            return self.SUCCESS        
        # 查找所有样式
        themeFolder = application.getRealPath("/") + "WEB-INF" + File.separator + "channel" + File.separator
        #print themeFolder
        file = File(themeFolder)
        if file.exists() == True:
            theme_list = []
            fs = file.list()
            for theme in fs:
                fd = File(themeFolder + theme)
                if fd.isDirectory() == True:
                    theme_list.append(theme)            
            if len(theme_list) > 0:
                request.setAttribute("theme_list", theme_list)
                
        request.setAttribute("channel", self.channel)
        return "/WEB-INF/ftl/channel/channel_skins.ftl"
    def getFileContent(self, fp):
        ctx = request.getSession().getServletContext()
        filePath = ctx.getRealPath(fp)
        return CommonUtil.readFileToString(filePath, "utf-8")