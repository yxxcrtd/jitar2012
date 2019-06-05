from base_channel_manage import *
from channel_query import ChannelVideoQuery

class channel_video_list(BaseChannelManage):
    def __init__(self):
        BaseChannelManage.__init__(self)
        self.channelId = 0
        self.channel = None
        self.videoService = __spring__.getBean("videoService")
        self.categoryService = __spring__.getBean("categoryService")       
        
    def execute(self):
        self.channelId = self.params.safeGetIntParam("channelId")
        self.channel = self.channelPageService.getChannel(self.channelId)
        if self.channel == None:
            self.addActionError(u"不能加载频道对象。")
            return self.ERROR
        if self.isSystemAdmin() == False and self.isChannelSystemAdmin(self.channel) == False and self.isChannelContentAdmin(self.channel) == False:
            self.addActionError(u"你无权管理本频道。")
            return self.ERROR
        if request.getMethod() == "POST":
            self.save_post()
        
        return self.get_list()
    
    def save_post(self):        
        cmd = self.params.safeGetStringParam("cmd")
        guids = self.params.safeGetIntValues("guid")
        if cmd == "remove":
            for guid in guids:
                r = self.channelPageService.getChannelVideo(guid)
                if r != None:
                    self.channelPageService.deleteChannelVideo(r)
        elif cmd == "recate":
            newCate = self.params.safeGetStringParam("newCate")            
            if newCate == "":
                for guid in guids:
                    channelVideo = self.channelPageService.getChannelVideo(guid)
                    if channelVideo != None and channelVideo.channelCateId != None:
                        channelVideo.channelCate = None
                        channelVideo.channelCateId = None
                        self.channelPageService.saveOrUpdateChannelVideo(channelVideo)
            else:
                newCateArray = newCate.split("/")
                if len(newCateArray) > 2:
                    newCateId = newCateArray[len(newCateArray)-2]
                    for guid in guids:
                        channelVideo = self.channelPageService.getChannelVideo(guid)
                        if channelVideo != None and channelVideo.channelCate != newCate:                            
                            channelVideo.channelCate = newCate
                            channelVideo.channelCateId = int(newCateId)
                            self.channelPageService.saveOrUpdateChannelVideo(channelVideo)

        
    def get_list(self):
        query = ChannelVideoQuery(""" cv.channelVideoId, cv.videoId, video.title, video.createDate, cv.channelCate, cv.viewCount, video.flvThumbNailHref, user.loginName,user.trueName, unit.unitName,unit.unitTitle """)
        query.channelId = self.channelId
       
        schannel = self.params.safeGetStringParam("schannel")
        if schannel != "":
            query.channelCate = schannel
        query.k = self.params.safeGetStringParam("k")
        query.f = self.params.safeGetStringParam("f")
        self.putChannelCategoryTree()
        
        # 调用分页函数.
        pager = self.createPager()
        pager.totalRows = query.count()
        
        # 得到列表.
        videoList = query.query_map(pager)
            
        # 传给页面.
        request.setAttribute("videoList", videoList)
        request.setAttribute("pager", pager)
        request.setAttribute("k", query.k)
        request.setAttribute("f", query.f)
        request.setAttribute("channel", self.channel)
        request.setAttribute("schannel", schannel)
        
        return "/WEB-INF/ftl/channel/channel_video_list.ftl"
        
    def putChannelCategoryTree(self):
        channel_video_categories = self.categoryService.getCategoryTree('channel_video_' + str(self.channelId))
        request.setAttribute("channel_video_categories", channel_video_categories)
        
    def createPager(self):
        # 调用Java的函数.
        pager = self.params.createPager()
        pager.itemName = u"视频"
        pager.itemUnit = u"个"
        pager.pageSize = 10
        return pager