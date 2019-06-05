from base_channel_manage import *
from category_query import CategoryQuery

class channel_category_order(BaseChannelManage):
    def __init__(self):
        BaseChannelManage.__init__(self)
        self.channelId = 0
        self.channel = None
        self.categoryService = __jitar__.categoryService
        self.itemType = None
        self.parentId = None
        
    def execute(self):
        self.itemType = self.params.safeGetStringParam("type")
        self.channelId = self.params.safeGetIntParam("channelId")
        self.channel = self.channelPageService.getChannel(self.channelId)
        self.parentId = self.params.getIntParamZeroAsNull("parentId")
        
        if self.channel == None:
            self.addActionError(u"不能加载频道对象。")
            return self.ERROR
        
        if self.isSystemAdmin() == False and self.isChannelSystemAdmin(self.channel) == False:
            self.addActionError(u"你无权管理本频道。")
            return self.ERROR
        
        request.setAttribute("channel", self.channel)
        cmd = self.params.safeGetStringParam("cmd")
        if cmd == None or cmd == "" or cmd == "list":
            return self.list()          # 列表显示.
        elif cmd == "save":
            return self.save()          # 保存.
        else :
            self.addActionError(u"未知的命令参数：" + cmd)
        return self.ERROR
        
    def list(self):
        # 文章的分类
        if self.itemType.startswith("channel_article_"): 
            cateId = self.itemType[len("channel_article_"):]
            if cateId != str(self.channelId):
                self.addActionError(u"不正确的分类对象。")
                return self.ERROR
            query = CategoryQuery(""" cat.categoryId, cat.name, cat.itemType, cat.parentId, cat.parentPath,cat.childNum, cat.orderNum, cat.description """)
            query.itemType = self.itemType
            query.parentId = self.parentId            
            cateList = query.query_map(query.count())
            request.setAttribute("category_list", cateList)
            # 将参数放到查询的页面.
            request.setAttribute("parentId", query.parentId)
            request.setAttribute("itemType", query.itemType)
            request.setAttribute("type", query.itemType)
            
            if query.parentId == '' or query.parentId == None:
                request.setAttribute("parentparentId", "")
            else:
                pcategory = self.categoryService.getCategory(int(query.parentId))
                if pcategory == None:
                    request.setAttribute("parentparentId", "")
                else:    
                    request.setAttribute("parentparentId", pcategory.parentId)
            return "/WEB-INF/ftl/channel/channel_category_order.ftl"
        elif self.itemType.startswith("channel_resource_"): 
            cateId = self.itemType[len("channel_resource_"):]
            if cateId != str(self.channelId):
                self.addActionError(u"不正确的分类对象。")
                return self.ERROR
            query = CategoryQuery(""" cat.categoryId, cat.name, cat.itemType, cat.parentId, cat.parentPath,cat.childNum, cat.orderNum, cat.description """)
            query.itemType = self.itemType
            query.parentId = self.parentId            
            cateList = query.query_map(query.count())
        
            request.setAttribute("category_list", cateList)
            # 将参数放到查询的页面.
            request.setAttribute("parentId", query.parentId)
            request.setAttribute("itemType", query.itemType)
            request.setAttribute("type", query.itemType)
            
            if query.parentId == '' or query.parentId == None:
                request.setAttribute("parentparentId", "")
            else:
                pcategory = self.categoryService.getCategory(int(query.parentId))
                if pcategory == None:
                    request.setAttribute("parentparentId", "")
                else:    
                    request.setAttribute("parentparentId", pcategory.parentId)
            return "/WEB-INF/ftl/channel/channel_category_order.ftl"
        
        elif self.itemType.startswith("channel_photo_"): 
            cateId = self.itemType[len("channel_photo_"):]
            if cateId != str(self.channelId):
                self.addActionError(u"不正确的分类对象。")
                return self.ERROR
            query = CategoryQuery(""" cat.categoryId, cat.name, cat.itemType, cat.parentId, cat.parentPath,cat.childNum, cat.orderNum, cat.description """)
            query.itemType = self.itemType
            query.parentId = self.parentId            
            cateList = query.query_map(query.count())
        
            request.setAttribute("category_list", cateList)
            # 将参数放到查询的页面.
            request.setAttribute("parentId", query.parentId)
            request.setAttribute("itemType", query.itemType)
            request.setAttribute("type", query.itemType)
            
            if query.parentId == '' or query.parentId == None:
                request.setAttribute("parentparentId", "")
            else:
                pcategory = self.categoryService.getCategory(int(query.parentId))
                if pcategory == None:
                    request.setAttribute("parentparentId", "")
                else:    
                    request.setAttribute("parentparentId", pcategory.parentId)
            return "/WEB-INF/ftl/channel/channel_category_order.ftl"
        
        elif self.itemType.startswith("channel_video_"): 
            cateId = self.itemType[len("channel_video_"):]
            if cateId != str(self.channelId):
                self.addActionError(u"不正确的分类对象。")
                return self.ERROR
            query = CategoryQuery(""" cat.categoryId, cat.name, cat.itemType, cat.parentId, cat.parentPath,cat.childNum, cat.orderNum, cat.description """)
            query.itemType = self.itemType
            query.parentId = self.parentId            
            cateList = query.query_map(query.count())
        
            request.setAttribute("category_list", cateList)
            # 将参数放到查询的页面.
            request.setAttribute("parentId", query.parentId)
            request.setAttribute("itemType", query.itemType)
            request.setAttribute("type", query.itemType)
            
            if query.parentId == '' or query.parentId == None:
                request.setAttribute("parentparentId", "")
            else:
                pcategory = self.categoryService.getCategory(int(query.parentId))
                if pcategory == None:
                    request.setAttribute("parentparentId", "")
                else:    
                    request.setAttribute("parentparentId", pcategory.parentId)
            return "/WEB-INF/ftl/channel/channel_category_order.ftl"
        else:
            self.addActionError(u"无效的分类对象。")
            return self.ERROR
    
    def save(self):
        cate_ids = self.params.getIdList("cateid")
        nos = self.params.getIdList("orderNo")
    
        if cate_ids == None or cate_ids.size() == 0:
            self.addActionError(u"没有选择要操作的分类.")
            return self.ERROR
  
        if nos == None or nos.size() == 0:
            self.addActionError(u"没有选择要操作的分类序号.")
            return self.ERROR
        iIndex = -1
        for id in cate_ids:
            iIndex = iIndex + 1
            no = nos[iIndex]
            #print "id=" + str(id) + "  no=" + str(no)
            category = self.categoryService.getCategory(id)
            if category == None:
                self.addActionError(u"未能找到标识为 " + id + u" 的分类￀ￂￛ")
                self.ERROR
            else:  
                self.categoryService.setCategoryOrder(id, no)
        self.parentId = self.params.getIntParamZeroAsNull("parentId")
        self.itemType = self.params.getStringParam("type")
        link = "channel_cate_list.py?cmd=list&amp;type=" + str(self.itemType) + "&channelId=" + str(self.channelId) + "&amp;parentId=" 
        if self.parentId != None:
            link += str(self.parentId)
            self.addActionLink(u"返回", link)
        return self.SUCCESS
