from base_channel_manage import *
from cn.edustar.jitar.pojos import Channel
from cn.edustar.jitar.pojos import SiteNav
from cn.edustar.jitar.pojos import ChannelModule
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.model import SiteUrlModel

class GetTemplate(BaseChannelManage):
    def __init__(self):
        BaseChannelManage.__init__(self)
        
    def execute(self):
        printer = response.getWriter()
        channelId = self.params.safeGetIntParam("channelId")
        #print "channelId="+str(channelId)
        channel = self.channelPageService.getChannel(channelId)
        if channel == None:
            printer.write(u"不能加载频道对象。")
            return
        if channel.skin == None:
            self.skin = "template1"
        elif channel.skin == "":
            self.skin = "template1"
        else:
            self.skin = channel.skin
            
        adminType = self.GetAdminType(channel)
        print "adminType=" + adminType
        print "self.skin=" + self.skin 
        if adminType.find("|SystemSuperAdmin|") >-1 or adminType.find("|ChannelSystemAdmin|") >-1 :
            templateName = self.params.safeGetStringParam("name")
            type = self.params.safeGetStringParam("type")
            print "type=" + type
            if type == "index":
                response.getWriter().write(self.getIndexTemplate(templateName))
            else:
                template = self.getFileContent("/WEB-INF/channel/" + self.skin + "/" + templateName + ".ftl")
                response.getWriter().write(template)
        else:
            response.getWriter().write(u"你没有管理此频道的权限。")
        return
    
    def getFileContent(self, fp):
        ctx = request.getSession().getServletContext()
        filePath = ctx.getRealPath(fp)
        return CommonUtil.readFileToString(filePath, "utf-8")
    
    def getIndexTemplate(self, templateName):
        print templateName
        if templateName == "indexPageTemplate":
            return self.getFileContent("/WEB-INF/channel/" + self.skin + "/Main.ftl")
        elif templateName == "headerTemplate":
            return self.getFileContent("/WEB-INF/channel/" + self.skin + "/Header.ftl")
        elif templateName == "footerTemplate":
            return self.getFileContent("/WEB-INF/channel/" + self.skin + "/Footer.ftl")
        elif templateName == "cssStyle":
            cssStyle = self.getFileContent("/css/channel/" + self.skin + "/common.css")
            cssStyle = cssStyle.replace("background:url(", "background:url(../css/channel/" + self.skin + "/")
            return cssStyle
        elif templateName == "SubjectNav":
            return self.getFileContent("/WEB-INF/ftl/site_subject_nav.ftl")        
        else:
            return ""