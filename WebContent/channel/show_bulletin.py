from cn.edustar.jitar.util import ParamUtil
from base_action import BaseAction, ActionResult
from java.util import HashMap
from base_channel_page import ChannelPage

class show_bulletin(ChannelPage):
    def __init__(self):
        ChannelPage.__init__(self)
        self.params = ParamUtil(request)
        self.channelPageService = __spring__.getBean("channelPageService")        
        self.templateProcessor = __spring__.getBean("templateProcessor")
        self.channel = None
        
    def execute(self):
        channelId = self.params.safeGetIntParam("channelId")
        if channelId == 0:
            self.addActionError(u"您所访问的页面不存在！")
            return ActionResult.ACCESS_ERROR
        self.channel = self.channelPageService.getChannel(channelId)
        if self.channel == None:
            self.addActionError(u"无法加载指定的页面！")
            return ActionResult.ACCESS_ERROR
        map = HashMap()
        map.put("channel", self.channel)
        map.put("head_nav", "video")
        
        if self.channel.skin == None :
            skin = "template1"
        elif self.channel.skin == "" :
            skin = "template1"
        else:
            skin = self.channel.skin  
                    
        # 输出页头
        headerContent = self.GenerateContentFromTemplateString(self.channel.headerTemplate)
        footerContent = self.GenerateContentFromTemplateString(self.channel.footerTemplate)
        # 输出主体部分：
        placardId = self.params.getIntParam("placardId")
        if placardId == None or placardId == "":
            mainContent = ""
        else:            
            placardService = __jitar__.getPlacardService()
            placard = placardService.getPlacard(placardId)            
            map.put("placard", placard)       
            mainContent = self.templateProcessor.processTemplate(map, "/WEB-INF/channel/" + skin + "/show_bulletin.ftl", "utf-8")
        out = response.getWriter()
        if headerContent == "" and footerContent == "" and mainContent == "":
            out.println(u"该频道没有指定模板，无法显示页面内容。")
            return
        
        out.println(headerContent)
        out.println(mainContent)
        out.println(footerContent)