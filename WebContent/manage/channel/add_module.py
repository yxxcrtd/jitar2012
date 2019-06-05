from base_channel_manage import *
from cn.edustar.jitar.pojos import ChannelModule

class add_module(BaseChannelManage):
    def __init__(self):
        BaseChannelManage.__init__(self)
        self.categoryService = __jitar__.categoryService
        self.unitTypeService = __jitar__.unitTypeService
        self.channelId = 0
        self.channel = None
        self.moduleId = 0
        self.channelModule = None
        
    def execute(self):
        self.channelId = self.params.safeGetIntParam("channelId")
        #print "self.channelId="+str(self.channelId)
        self.moduleId = self.params.safeGetIntParam("moduleId")
        #print "self.moduleId="+str(self.moduleId)
        mt = self.params.safeGetStringParam("mt")
        #print "mt="+mt
        self.channel = self.channelPageService.getChannel(self.channelId)
        self.channelModule = self.channelPageService.getChannelModule(self.moduleId)
        if self.channel == None:
            self.addActionError(u"不能加载频道对象。")
            return self.ERROR
        
        if self.isSystemAdmin() == False and self.isChannelSystemAdmin(self.channel) == False:
            self.addActionError(u"你无权管理本频道。")
            return self.ERROR
        
        if self.channelModule != None:
            mt = self.channelModule.moduleType
        if mt == "":
            self.addActionError(u"请选择一个模块类型。")
            return self.ERROR
        print "mt="+mt
        if mt == "UnitShow":
            #机构属性
            unitTypeList = self.unitTypeService.getUnitTypeNameList()
            request.setAttribute("unitTypeList", unitTypeList)        
        if mt == "CustomCategory":
            #得到文章 资源 图片 视频的分类
            #article_category_list = self.categoryService.getChildCategories("channel_article_"+str(self.channelId),None)
            article_category_list = self.categoryService.getCategoryTree("channel_article_" + str(self.channelId))
            request.setAttribute("article_category_list", article_category_list)
            #resource_category_list = self.categoryService.getChildCategories("channel_resource_"+str(self.channelId),None)
            resource_category_list = self.categoryService.getCategoryTree("channel_resource_" + str(self.channelId))
            request.setAttribute("resource_category_list", resource_category_list)
            #photo_category_list = self.categoryService.getChildCategories("channel_photo_"+str(self.channelId),None)
            photo_category_list = self.categoryService.getCategoryTree("channel_photo_" + str(self.channelId))
            request.setAttribute("photo_category_list", photo_category_list)
            #video_category_list = self.categoryService.getChildCategories("channel_video_"+str(self.channelId),None)
            video_category_list = self.categoryService.getCategoryTree("channel_video_" + str(self.channelId))
            request.setAttribute("video_category_list", video_category_list)
            
        if request.getMethod() == "POST":
            return self.AddSystemModule(mt)
                
        request.setAttribute("channel", self.channel)
        request.setAttribute("module", self.channelModule)
        request.setAttribute("moduleType", mt)
        return "/WEB-INF/ftl/channel/add_module_" + mt + ".ftl"
    
    def AddSystemModule(self, mt):
        if self.channelModule == None:
            self.channelModule = ChannelModule()
                        
        displayName = self.params.safeGetStringParam("moduleDisplayName")
        template = self.params.safeGetStringParam("template")
        if template == "" : template = None
        if self.params.existParam("showCount"):
            showCount = self.params.safeGetIntParam("showCount")
            if showCount == 0: showCount = 10
            self.channelModule.setShowCount(showCount)
        if self.params.existParam("moduleContent"):
            moduleContent = self.params.safeGetStringParam("moduleContent")        
            if moduleContent == "":
                self.addActionError(u"模块内容不能为空。")
                return self.ERROR
            self.channelModule.setContent(moduleContent)
            
        if displayName == "":
            self.addActionError(u"模块名称不能为空。")
            return self.ERROR

        channelExist = self.channelPageService.getChannelModuleByDisplayName(displayName,self.channelId)
        if channelExist != None :
            if self.channelModule.moduleId == None or self.channelModule.moduleId == 0 : 
                self.addActionError(u"模块名称已经存在。")
                return self.ERROR
            else :
                if self.channelModule.moduleId != channelExist.moduleId : 
                    self.addActionError(u"模块名称已经存在。")
                    return self.ERROR
                
        if mt == "UnitShow":
            unitTypeList = self.unitTypeService.getUnitTypeNameList()
            request.setAttribute("unitTypeList", unitTypeList)        
            typeName = self.params.safeGetStringParam("typeName")
            if typeName == "":
                self.addActionError(u"必须选择机构属性。")
                return self.ERROR
            self.channelModule.setUnitType(typeName);
                
        if mt == "CustomCategory":
            typeName = self.params.safeGetStringParam("typeName")
            if typeName == "":
                self.addActionError(u"必须选择分类。")
                return self.ERROR
            categoryId = self.params.safeGetIntParam(typeName+"Id")
            self.channelModule.setCateId(categoryId);
            if typeName=="articleCategory":
                self.channelModule.setCateItemType("channel_article_" + str(self.channelId))
            if typeName=="resourceCategory":
                self.channelModule.setCateItemType("channel_resource_" + str(self.channelId))
            if typeName=="photoCategory":
                self.channelModule.setCateItemType("channel_photo_" + str(self.channelId))
            if typeName=="videoCategory":
                self.channelModule.setCateItemType("channel_video_" + str(self.channelId))
        
        self.channelModule.setChannelId(self.channelId)
        self.channelModule.setDisplayName(displayName)
        self.channelModule.setTemplate(template)
        self.channelModule.setModuleType(mt)
        self.channelModule.setPageType("main")
        self.channelPageService.saveOrUpdateChannelModule(self.channelModule)
        if self.moduleId == 0:
            self.addActionMessage(u"添加成功。")
        else:
            self.addActionMessage(u"修改成功。")
        self.addActionLink(u"返回模块管理", "channelmodulelist.py?channelId=" + str(self.channelId))
        return self.SUCCESS
