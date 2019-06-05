from base_channel_manage import *
from placard_query import PlacardQuery
from cn.edustar.jitar.pojos import Placard

class channel_bulletin_list(BaseChannelManage):
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
        cmd = self.params.safeGetStringParam("cmd")
        if request.getMethod() == "POST":
            placardService = __spring__.getBean("placardService")
            guids = self.params.safeGetIntValues("guid")
            for g in guids:
                placard = placardService.getPlacard(g)
                if placard != None:
                    if cmd == "delete":
                        placardService.deletePlacard(placard)
                    elif cmd == "show":
                        placard.setHide(0)
                        placardService.savePlacard(placard)
                    elif cmd == "hide":
                        placard.setHide(1)
                        placardService.savePlacard(placard)
        
        qry = PlacardQuery(""" pld.id, pld.title, pld.createDate, pld.hide """)
        qry.objType = 19
        qry.objId = self.channel.channelId
        qry.hideState = None
        pager = self.createPager()
        pager.totalRows = qry.count()
        placard_list = qry.query_map(pager)
        request.setAttribute("placard_list", placard_list)
        request.setAttribute("pager", pager)
        request.setAttribute("channel", self.channel)
        
        return "/WEB-INF/ftl/channel/channel_bulletin_list.ftl"


    def createPager(self):
        pager = self.params.createPager()
        pager.pageSize = 10
        pager.itemName = u"公告"
        pager.itemUnit = u"个"
        return pager