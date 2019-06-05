from base_channel_manage import *
from java.util import UUID
from channel_query import *
from java.text import SimpleDateFormat
from java.util import Date
from cn.edustar.jitar.util import CommonUtil

class channel_user_stat(BaseChannelManage):
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
        self.k = self.params.safeGetStringParam("k")
        self.f = self.params.safeGetStringParam("f")
        self.guid = self.params.safeGetStringParam("guid")
        self.cmd = self.params.safeGetStringParam("cmd")
        startDate = self.params.safeGetStringParam("startDate")
        endDate = self.params.safeGetStringParam("endDate")
        if request.getMethod() == "POST":
            if self.cmd == "export":               
                qry = ChannelUserStatQuery("cus.loginName, cus.userTrueName, cus.unitTitle, cus.articleCount, cus.resourceCount, cus.photoCount, cus.videoCount")
                qry.channelId = self.channel.channelId
                qry.statGuid = self.guid
                stat_list = qry.query_map(qry.count())
                request.setAttribute("k", self.params.safeGetStringParam("k"))
                request.setAttribute("f", self.params.safeGetStringParam("f"))
                request.setAttribute("startDate", startDate)
                request.setAttribute("endDate", endDate)
                request.setAttribute("channel",self.channel)
                request.setAttribute("stat_list", stat_list)
                request.setCharacterEncoding("utf-8") 
                response.reset()
                #response.setContentType("application/vnd.ms-excel")
                response.setHeader("Content-Type", "application/vnd.ms-excel; charset=GB2312")
                response.addHeader("Content-Disposition", "attachment;" + CommonUtil.encodeContentDisposition(request, self.channel.title + ".xls"))
                return "/WEB-INF/ftl/channel/channel_user_excel.ftl"                
            else:                
                #清空上次的查询                
                #self.channelPageService.deleteChannelUserStat(self.guid)
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
                
                if self.f != "":
                    if self.f == "0": #用户登录名
                        self.f = "loginName"                    
                    elif self.f == "1":
                        self.f = "trueName"
                    elif self.f == "2":
                        self.f = "unitTitle"
                    else:
                        self.f = ""
                        
                #public void statUserData(int channelId, String statGuid, String keyWord, String filter, String startDate, String endDate)
                #先找满足条件的人
                #qry = ChannelUserQuery("cu.userId, u.loginName, u.trueName, cu.unitId, cu.unitTitle")
                #qry.channelId = self.channel.channelId
                #qry.f = self.f
                #qry.k = self.k
                #user_list = qry.query_map(qry.count())
                #if user_list != None and len(user_list) > 0:
                #    for u in user_list:
                #        #再查数据并添加到临时表
                #        articleCount = self.channelPageService.getChannelArticleCount(self.channel.channelId,int(u["userId"]),startDate,endDate)
                #        resourceCount = self.channelPageService.getChannelResourceCount(self.channel.channelId,int(u["userId"]),startDate,endDate)
                #        photoCount = self.channelPageService.getChannelPhotoCount(self.channel.channelId,int(u["userId"]),startDate,endDate)
                #        videoCount = self.channelPageService.getChannelVideoCount(self.channel.channelId,int(u["userId"]),startDate,endDate)
                #        channelUserStat = ChannelUserStat()
                #        channelUserStat.channelId = self.channel.channelId
                #        channelUserStat.statGuid = self.guid
                #        channelUserStat.userId = int(u["userId"])
                #        channelUserStat.loginName = u["loginName"]
                #        channelUserStat.userTrueName = u["trueName"]
                #        channelUserStat.unitId = int(u["unitId"])
                #        channelUserStat.unitTitle = u["unitTitle"]
                #        channelUserStat.articleCount = articleCount
                #        channelUserStat.resourceCount = resourceCount
                #        channelUserStat.photoCount = photoCount
                #        channelUserStat.videoCount = videoCount
                #        self.channelPageService.saveChannelUserStat(channelUserStat)
                s = ""
                e = ""
                if startDate != "" and endDate != "":
                    s = startDate + " 00:00:00"
                    e = endDate + " 23:59:59"
                    
                self.channelPageService.statUserData(self.channel.channelId, self.guid, self.k, self.f, s, e)
                reUrl = "channel_user_stat.py?cmd=show&guid=" + self.guid + "&channelId=" + str(self.channel.channelId) + "&f=" + CommonUtil.urlUtf8Encode(self.params.safeGetStringParam("f")) + "&k=" + CommonUtil.urlUtf8Encode(self.params.safeGetStringParam("k")) + "&startDate=" + CommonUtil.urlUtf8Encode(startDate) + "&endDate=" + CommonUtil.urlUtf8Encode(endDate)
                response.sendRedirect(reUrl)
        else:
            if self.guid == "":
                self.guid = UUID.randomUUID().toString()
            request.setAttribute("k", self.params.safeGetStringParam("k"))
            request.setAttribute("f", self.params.safeGetStringParam("f"))
            request.setAttribute("startDate", startDate)
            request.setAttribute("endDate", endDate)
            request.setAttribute("channel",self.channel)
            request.setAttribute("guid",self.guid)
            if self.cmd == "show":
                qry = ChannelUserStatQuery("cus.loginName, cus.userTrueName, cus.unitTitle, cus.articleCount, cus.resourceCount,cus.photoCount,cus.videoCount")
                qry.channelId = self.channel.channelId
                qry.statGuid = self.guid
                pager = self.createPager()
                pager.totalRows = qry.count()
                stat_list = qry.query_map(pager)
                request.setAttribute("stat_list",stat_list)
                request.setAttribute("pager",pager)
            return "/WEB-INF/ftl/channel/channel_user_stat.ftl"
    
    def createPager(self):
        # 调用Java的函数.
        pager = self.params.createPager()
        pager.itemName = u"记录"
        pager.itemUnit = u"条"
        pager.pageSize = 20
        return pager