from base_channel_manage import *
from placard_query import PlacardQuery
from cn.edustar.jitar.pojos import Placard

class channel_bulletin_add(BaseChannelManage):
    def __init__(self):        
        BaseChannelManage.__init__(self)
        self.channelId = 0
        self.channel = None
        self.placard = None
        
    def execute(self):
        self.channelId = self.params.safeGetIntParam("channelId")
        self.channel = self.channelPageService.getChannel(self.channelId)
        if self.channel == None:
            self.addActionError(u"不能加载频道对象。")
            return self.ERROR
        if self.isSystemAdmin() == False and self.isChannelSystemAdmin(self.channel) == False:
            self.addActionError(u"你无权管理本频道。")
            return self.ERROR
        placardId = self.params.safeGetIntParam("placardId")
        placardService = __spring__.getBean("placardService")
        placard = placardService.getPlacard(placardId)
        if request.getMethod() == "POST":
            title = self.params.safeGetStringParam("title")
            content = self.params.safeGetStringParam("placardContent")
            if title == "" or content == "":
                self.addActionError(u"请输入完整的标题和内容。")
                return self.ERROR
            if placard == None:
                placard = Placard()
                placard.objType = 19
                placard.objId = self.channel.channelId
                placard.userId = self.loginUser.userId
            placard.setTitle(title)
            placard.setContent(content)
            placardService.savePlacard(placard)
            self.addActionMessage(u"编辑完成。")
            self.addActionLink(u"返回", "channel_bulletin_list.py?channelId=" + str(self.channelId))
            return self.SUCCESS
        
        request.setAttribute("placard", placard)
        return "/WEB-INF/ftl/channel/channel_bulletin_add.ftl"
