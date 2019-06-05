from cn.edustar.jitar.util import ParamUtil
from base_action import BaseAction
from java.util import HashMap
from java.util import ArrayList
from base_channel_page import ChannelPage

class channel(ChannelPage):
    def __init__(self):
        ChannelPage.__init__(self)
        self.params = ParamUtil(request)
        
    def execute(self):
        if self.channel == None:
            self.addActionError(u"无法加载指定的页面！")
            return self.ERROR
        head_nav = "channel"
        out = response.getWriter()
        skin = self.params.safeGetStringParam("theme")
        if skin == "" :
            headerContent = self.GenerateContentFromTemplateString(self.channel.headerTemplate, head_nav)
            footerContent = self.GenerateContentFromTemplateString(self.channel.footerTemplate)
            indexPageTemplate = self.GenerateContentFromTemplateString(self.channel.indexPageTemplate)
        else:
            cssStyle = self.getFileContent("/css/channel/" + skin + "/common.css")
            cssStyle = cssStyle.replace("background:url(", "background:url(../css/channel/" + skin + "/")
            
            headerTemplate = self.getFileContent("/WEB-INF/channel/" + skin + "/Header.ftl")
            headerContent = self.GenerateContentFromTemplateString(headerTemplate, head_nav, cssStyle)
            footerTemplate = self.getFileContent("/WEB-INF/channel/" + skin + "/Footer.ftl")
            footerContent = self.GenerateContentFromTemplateString(footerTemplate)
            indexPageTemplate = self.getFileContent("/WEB-INF/channel/" + skin + "/Main.ftl")
            indexPageTemplate = self.GenerateContentFromTemplateString(indexPageTemplate)
        if headerContent == "" and footerContent == "" and indexPageTemplate == "":
            out.println(u"该频道没有指定模板,无法显示页面内容。")
            return
                
        out.println(headerContent)
        out.println(indexPageTemplate)
        out.println(footerContent)
        
    def getFileContent(self, fp):
        ctx = request.getSession().getServletContext()
        filePath = ctx.getRealPath(fp)
        return CommonUtil.readFileToString(filePath, "utf-8")