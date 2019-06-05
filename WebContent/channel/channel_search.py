from cn.edustar.jitar.util import ParamUtil
from base_action import BaseAction, ActionResult
from java.util import HashMap
from base_channel_page import ChannelPage
from user_query import UserQuery
from article_query import ArticleQuery
from resource_query import ResourceQuery
from video_query import VideoQuery
from photo_query import PhotoQuery
from java.net import URLEncoder

class channel_search(ChannelPage):
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
        map.put("head_nav", "search")
        
        # 输出页头
        headerContent = self.GenerateContentFromTemplateString(self.channel.headerTemplate)
        footerContent = self.GenerateContentFromTemplateString(self.channel.footerTemplate)
        # 输出主体部分：
        k = self.params.safeGetStringParam("k")
        f = self.params.safeGetStringParam("f")
        k = URLEncoder.encode(k,"utf-8")
        if k == "":
            mainContent = u"<div style='padding:20px;font-size:14px;color:red;text-align:center'>请输入关键字。<br/><br/><a href='channel.py?channelId=" + str(channelId) + u"'>重新输入</a></div>"
        else:
            if f == "article":
                response.sendRedirect("channel.action?cmd=articlelist&channelId=" + str(self.channel.channelId) + "&k=" + k + "&f=title")
                return
            elif f == "resource":
                response.sendRedirect("channel.action?cmd=resourcelist&channelId=" + str(self.channel.channelId) + "&k=" + k + "&f=title")
                return
            elif f == "video":
                response.sendRedirect("channel.action?cmd=videolist&channelId=" + str(self.channel.channelId) + "&k=" + k + "&f=3")
                return
            elif f == "photo":
                response.sendRedirect("channel.action?cmd=photolist&channelId=" + str(self.channel.channelId) + "&k=" + k + "&f=3")
                return
            elif f == "user":
                response.sendRedirect("channel.action?cmd=userlist&channelId=" + str(self.channel.channelId) + "&k=" + k + "&f=trueName")
                return
            else:
                mainContent = u"<div style='padding:20px;font-size:14px;color:red;text-align:center'>查询的类别不正确。</div>"
        out = response.getWriter()
        if headerContent == "" and footerContent == "" and mainContent == "":
            out.println(u"该频道没有指定模板，无法显示页面内容。")
            return
        
        out.println(headerContent)
        out.println(mainContent)
        out.println(footerContent)
