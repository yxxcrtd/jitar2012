from channel_query import ChannelResourceQuery
from base_channel_manage import *

class channel_resource_list(BaseChannelManage):
    def __init__(self):
        BaseChannelManage.__init__(self)
        self.channelId = 0
        self.channel = None
        self.groupService = __spring__.getBean("groupService")
        self.resourceService = __spring__.getBean("resourceService")
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
            #response.sendRedirect(request.getHeader("Referer"))
        return self.show_list()
    
    def save_post(self):        
        cmd = self.params.safeGetStringParam("cmd")
        guids = self.params.safeGetIntValues("guid")
        if cmd == "remove":
            for guid in guids:
                r = self.channelPageService.getChannelResource(guid)
                if r != None:
                    self.channelPageService.deleteChannelResource(r)
        elif cmd == "recate":
            newCate = self.params.safeGetStringParam("newCate")            
            if newCate == "":
                for guid in guids:
                    channelResource = self.channelPageService.getChannelResource(guid)
                    if channelResource != None and channelResource.channelCateId != None:
                        channelResource.channelCate = None
                        channelResource.channelCateId = None
                        self.channelPageService.saveOrUpdateChannelResource(channelResource)
            else:
                newCateArray = newCate.split("/")
                if len(newCateArray) > 2:
                    newCateId = newCateArray[len(newCateArray)-2]
                    for guid in guids:
                        channelResource = self.channelPageService.getChannelResource(guid)
                        if channelResource != None and channelResource.channelCate != newCate:                            
                            channelResource.channelCate = newCate
                            channelResource.channelCateId = int(newCateId)
                            self.channelPageService.saveOrUpdateChannelResource(channelResource)
        
    
    def show_list(self):
        self.collectionQueryString()
        qry = ChannelResourceQuery(" cr.channelResourceId, cr.resourceId, cr.userId, resource.title, resource.createDate,user.loginName, user.trueName as userTrueName, resource.href, resource.fsize, cr.channelCateId, unit.unitName,unit.unitTitle ")
        qry.channelId = self.channel.channelId
        if self.schannel != "":
            qry.channelCate = self.schannel
        
        pager = self.params.createPager()
        pager.itemName = u"资源"
        pager.itemUnit = u"个"
        pager.pageSize = 25
        pager.totalRows = qry.count()
        resource_list = qry.query_map(pager)

        request.setAttribute("channel_resource_categories", self.categoryService.getCategoryTree("channel_resource_" + str(self.channelId)))
        
        request.setAttribute("resource_list", resource_list)
        request.setAttribute("pager", pager)
        request.setAttribute("channel", self.channel)
        return "/WEB-INF/ftl/channel/channel_resource_list.ftl"        
        
    def collectionQueryString(self):  
        self.schannel = self.params.safeGetStringParam("schannel", "")

        request.setAttribute("schannel", self.schannel)
        
    def triblesimu(self, int1):
        if int1 == "0":
            return False
        else:
            return True