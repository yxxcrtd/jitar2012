from base_channel_manage import *
from channel_query import ChannelPhotoQuery

class channel_photo_list(BaseChannelManage):
    def __init__(self):
        BaseChannelManage.__init__(self)
        self.channelId = 0
        self.channel = None
        self.photoService = __spring__.getBean("photoService")
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
            for g in guids:
                channelPhoto = self.channelPageService.getChannelPhoto(g)
                if channelPhoto != None:
                    self.channelPageService.deleteChannelPhoto(channelPhoto)
        elif cmd == "recate":
            newCate = self.params.safeGetStringParam("newCate")            
            if newCate == "":
                for guid in guids:
                    channelPhoto = self.channelPageService.getChannelPhoto(guid)
                    if channelPhoto != None and channelPhoto.channelCateId != None:
                        channelPhoto.channelCate = None
                        channelPhoto.channelCateId = None
                        self.channelPageService.saveOrUpdateChannelPhoto(channelPhoto)
            else:
                newCateArray = newCate.split("/")
                if len(newCateArray) > 2:
                    newCateId = newCateArray[len(newCateArray)-2]
                    for guid in guids:
                        channelPhoto = self.channelPageService.getChannelPhoto(guid)
                        if channelPhoto != None and channelPhoto.channelCate != newCate:                            
                            channelPhoto.channelCate = newCate
                            channelPhoto.channelCateId = int(newCateId)
                            self.channelPageService.saveOrUpdateChannelPhoto(channelPhoto)
        
    def get_list(self):
        showState = self.params.safeGetStringParam("sshowState")
        query = ChannelPhotoQuery(" cp.channelPhotoId, cp.photoId, photo.title, photo.href, cp.viewCount, photo.createDate, cp.channelCate, user.trueName as userTrueName,user.loginName, unit.unitName,unit.unitTitle ")
        
        query.channelId = self.channelId        
        schannel = self.params.safeGetStringParam("schannel")
        if schannel != "":
            query.channelCate = schannel
        self.putChannelCategoryTree()
        
        # 调用分页函数.
        pager = self.createPager()
        pager.totalRows = query.count()
        
        # 得到所有照片的列表.
        photoList = query.query_map(pager)
        #print "photoList = ", photoList
            
        # 传给页面.
        request.setAttribute("photoList", photoList)
        request.setAttribute("pager", pager)
        request.setAttribute("channel", self.channel)
        request.setAttribute("schannel", schannel)
        
        return "/WEB-INF/ftl/channel/channel_photo_list.ftl"
        
    def putChannelCategoryTree(self):
        channel_photo_categories = self.categoryService.getCategoryTree('channel_photo_' + str(self.channelId))
        request.setAttribute("channel_photo_categories", channel_photo_categories)
        
    def createPager(self):
        # 调用Java的函数.
        pager = self.params.createPager()
        pager.itemName = u"图片"
        pager.itemUnit = u"张"
        pager.pageSize = 10
        return pager