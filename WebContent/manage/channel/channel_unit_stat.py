from base_channel_manage import *
from java.util import UUID
from channel_query import *
from java.text import SimpleDateFormat
from java.util import Date
from cn.edustar.jitar.util import CommonUtil

class channel_unit_stat(BaseChannelManage):
    def __init__(self):
        BaseChannelManage.__init__(self)
        
    def execute(self):
        channelId = self.params.safeGetIntParam("channelId")
        self.channel = self.channelPageService.getChannel(channelId)
        if self.channel == None:
            self.addActionError(u"无法加载频道。")
            return self.ERROR
        AdminType = self.GetAdminType(self.channel)
        if AdminType == "":
            self.addActionError(u"你无权管理频道。")
            return self.ERROR        
        self.guid = self.params.safeGetStringParam("guid")
        self.cmd = self.params.safeGetStringParam("cmd")
        startDate = self.params.safeGetStringParam("startDate")
        endDate = self.params.safeGetStringParam("endDate")
        if request.getMethod() == "POST":
            if self.cmd == "export":               
                qry = ChannelUnitStatQuery("cuns.unitTitle, cuns.userCount, cuns.articleCount, cuns.resourceCount, cuns.photoCount, cuns.videoCount")
                qry.channelId = self.channel.channelId
                qry.statGuid = self.guid
                stat_list = qry.query_map(qry.count())
                request.setAttribute("startDate", startDate)
                request.setAttribute("endDate", endDate)
                request.setAttribute("channel",self.channel)
                request.setAttribute("stat_list", stat_list)
                request.setCharacterEncoding("utf-8") 
                response.reset()
                #response.setContentType("application/vnd.ms-excel")
                response.setHeader("Content-Type", "application/vnd.ms-excel; charset=GB2312")
                response.addHeader("Content-Disposition", "attachment;" + CommonUtil.encodeContentDisposition(request, self.channel.title + ".xls"))
                return "/WEB-INF/ftl/channel/channel_unit_excel.ftl"                
            else:
                if startDate != "" and endDate != "":
                    try:
                        SimpleDateFormat("yyyy-MM-dd").parse(startDate)
                    except:
                        startDate = ""
                    try:
                        SimpleDateFormat("yyyy-MM-dd").parse(endDate)
                    except:
                        endDate = ""
                if endDate == "" or startDate == "":
                    startDate = endDate = ""
                s = ""
                e = ""
                if startDate != "" and endDate != "":
                    s = startDate + " 00:00:00"
                    e = endDate + " 23:59:59"
                    
                self.channelPageService.statUnitData(self.channel.channelId, self.guid, s, e)
                reUrl = "channel_unit_stat.py?cmd=show&guid=" + self.guid + "&channelId=" + str(self.channel.channelId) + "&startDate=" + CommonUtil.urlUtf8Encode(startDate) + "&endDate=" + CommonUtil.urlUtf8Encode(endDate)
                response.sendRedirect(reUrl)
        else:
            if self.guid == "":
                self.guid = UUID.randomUUID().toString()
            request.setAttribute("startDate", startDate)
            request.setAttribute("endDate", endDate)
            request.setAttribute("channel",self.channel)
            request.setAttribute("guid",self.guid)
            if self.cmd == "show":
                qry = ChannelUnitStatQuery("cuns.unitTitle, cuns.userCount, cuns.articleCount, cuns.resourceCount, cuns.photoCount, cuns.videoCount")
                qry.channelId = self.channel.channelId
                qry.statGuid = self.guid
                pager = self.createPager()
                pager.totalRows = qry.count()
                stat_list = qry.query_map(pager)
                request.setAttribute("stat_list",stat_list)
                request.setAttribute("pager",pager)
            return "/WEB-INF/ftl/channel/channel_unit_stat.ftl"
    
    def createPager(self):
        # 调用Java的函数.
        pager = self.params.createPager()
        pager.itemName = u"记录"
        pager.itemUnit = u"条"
        pager.pageSize = 20
        return pager