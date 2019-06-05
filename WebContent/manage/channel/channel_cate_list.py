from base_channel_manage import *
from cn.edustar.jitar.pojos import Category
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.data import Command
from java.lang import Integer

class channel_cate_list(BaseChannelManage):
    def __init__(self):
        BaseChannelManage.__init__(self)
        self.channelId = 0
        self.channel = None
        self.categoryService = __jitar__.categoryService
        self.itemType = None
        self.parentId = None
        
    def execute(self):
        cmd = self.params.safeGetStringParam("cmd")
        self.itemType = self.params.safeGetStringParam("type")
        self.parentId = self.params.getIntParamZeroAsNull("parentId")
        if cmd == None or cmd == "": cmd = "list"
        
        self.channelId = self.params.safeGetIntParam("channelId")
        self.channel = self.channelPageService.getChannel(self.channelId)
        if self.channel == None:
            self.addActionError(u"不能加载频道对象。")
            return self.ERROR
        if self.isSystemAdmin() == False and self.isChannelSystemAdmin(self.channel) == False:
            self.addActionError(u"你无权管理本频道。")
            return self.ERROR
        
        request.setAttribute("parentId", self.parentId)
        request.setAttribute("channel", self.channel)
        request.setAttribute("type", self.itemType)
        if cmd == 'list':
            return self.list()
        elif cmd == 'add':
            return self.add()
        elif cmd == 'edit':
            return self.edit()
        elif cmd == 'save':
            return self.save()
        elif cmd == 'delete':
            return self.delete()
    
        self.addActionError(u"未知命令 : " + cmd)
        return self.ERROR
    
    def list(self):
        self.category_list = self.categoryService.getChildCategories(self.itemType, self.parentId)
        request.setAttribute("category_list", self.category_list)
        return "/WEB-INF/ftl/channel/channel_cate_list.ftl"
    
    # 添加一个分类.
    def add(self):
        if self.getParentCategory() == False:
            self.addActionError(u"没有上级分类。")
            return self.ERROR
        
        # 得到整个分类树.
        category_tree = self.categoryService.getCategoryTree(self.itemType)
        request.setAttribute("category_tree", category_tree)
        
        # 构造一个新分类.
        category = Category()
        category.itemType = self.itemType
        category.parentId = self.parentId
        request.setAttribute("category", category)
        
        return "/WEB-INF/ftl/channel/channel_cate_list_add.ftl"
    
    # 新建/保存一个分类.
    def save(self):
        # 获得和验证父分类参数.
        if self.getParentCategory() == False:
            return self.ERROR      
        
        # 从提交数据中组装出 category 对象.
        category = Category()
        category.categoryId = self.params.getIntParam("categoryId")
        category.name = self.params.getStringParam("name")
        category.itemType = self.itemType
        category.parentId = self.parentId
        category.description = self.params.getStringParam("description")
        
        # 简单验证.
        if category.name == None or category.name == "":
            self.addActionError(u"未填写分类名字.")
            return self.ERROR
        
        if category.categoryId == 0:
            # 创建该分类.
            self.categoryService.createCategory(category)
            self.addActionMessage(u"分类 " + category.name + u" 创建成功.")
        else:
            # 更新/移动分类.
            self.categoryService.updateCategory(category)
            # 修改文章、资源、图片、视频的分类标识 
            if self.itemType.startswith("channel_article_"):
                self.updateChannelCateId("ChannelArticle", category)
            elif self.itemType.startswith("channel_resource_"):
                self.updateChannelCateId("Resource", category)
            elif self.itemType.startswith("channel_photo_"):
                self.updateChannelCateId("Photo", category)
            elif self.itemType.startswith("channel_video_"):
                self.updateChannelCateId("Video", category)
            
            self.addActionMessage(u"分类 " + category.name + u" 修改/移动操作成功完成.")
          
        link = "?cmd=list&channelId=" + str(self.channelId) + "&type=" + self.itemType
        self.addActionLink(u"返回", link)
        return self.SUCCESS
    
    # 编辑修改/移动一个分类.
    def edit(self):
        # 得到要编辑的分类对象.
        if self.getCurrentCategory() == False:
            return self.ERROR
        request.setAttribute("category", self.category)
    
        # 得到整个分类树.
        category_tree = self.categoryService.getCategoryTree(self.itemType)
        request.setAttribute("category_tree", category_tree)
        
        return "/WEB-INF/ftl/channel/channel_cate_list_add.ftl"

    # 删除所选分类.
    def delete(self):
        # 得到要删除的分类对象.
        if self.getCurrentCategory() == False:
          return self.ERROR
        
        # 验证其是否有子分类, 有子分类的必须要先删除子分类才能删除分类.
        if self.hasChildCategories(self.category):
          self.addActionError(u"分类 " + self.category.name + u" 有子分类, 必须先删除其所有子分类才能删除该分类.")
          return self.ERROR
    
        # 设置 文章.groupCateId 都为 null.
        # 这里现在没有放在事务里面执行.
        if self.itemType.startswith("channel_article_"):
            self.deleteChannelCategory("ChannelArticle",self.category)
        elif self.itemType.startswith("channel_resource_"):
            self.deleteChannelCategory("Resource",self.category)
        elif self.itemType.startswith("channel_photo_"):
            self.deleteChannelCategory("Photo",self.category)
        elif self.itemType.startswith("channel_video_"):
            self.deleteChannelCategory("Video",self.category)
        else:
            self.addActionError(u"无效的分类")
            return self.ERROR
        # 执行业务.
        self.categoryService.deleteCategory(self.category)
        
        self.addActionMessage(u"分类 " + self.category.name + u" 已经成功删除.")
        return self.SUCCESS 
    
    
    # 得到父分类标识参数及父分类对象, 并进行 itemType 验证.
    def getParentCategory(self):
        self.parentId = self.params.getIntParamZeroAsNull("parentId")
        request.setAttribute("parentId", self.parentId)
        if self.parentId == None:     # 认为是根分类.
            return True
        
        self.parentCategory = self.categoryService.getCategory(self.parentId)
        request.setAttribute("parentCategory", self.parentCategory)
        # print "parentCategory = ", self.parentCategory
        if self.parentCategory == None:
            self.addActionError(u"未能找到指定标识的父分类, 请确定您是从有效的链接点击进入的.")
            return False
        if self.parentCategory.itemType != self.itemType:
            self.addActionError(u"不匹配的父分类类型: " + self.parentCategory.itemType)
            return False
        return True
    
    # 得到当前要操作的分类对象, 并验证其存在, 以及 itemType 匹配.
    # 返回 False 表示失败; True 表示成功.
    # 如果返回 True 则 self.category 中存放着拿出来的分类对象.
    def getCurrentCategory(self):
        categoryId = self.params.getIntParam("categoryId")
        if categoryId == 0:
            self.addActionError(u"未给出要操作的分类.")
            return False
        
        category = self.categoryService.getCategory(categoryId)
        if category == None:
            self.addActionError(u"未找到指定标识为 " + str(categoryId) + u" 的分类.")
            return False
        
        # 验证分类类型必须匹配.
        if category.itemType != self.itemType:
            self.addActionError(u"不匹配的分类类型.")
            return False
    
        self.category = category
        return True
    
    # 判断指定的分类是否具有子分类.
    def hasChildCategories(self, category):
        childCount = self.categoryService.getChildrenCount(category.categoryId)
        return childCount > 0
    
    # 设置 文章的分类为null .articleCateId == category.categoryId 都为 null
    def deleteChannelCategory(self, resType, category):
        if category == None:return        
        cmd = Command(" UPDATE " + resType + " SET channelCate = NULL,channelCateId = NULL WHERE channelId = :channelId AND channelCateId = :channelCateId ")
        cmd.setInteger("channelId", self.channel.channelId)
        cmd.setInteger("channelCateId", category.categoryId)
        count = cmd.update()
            
    def updateChannelCateId(self, resType, category):
        # 得到该分类及其下级所有分类
        cmd = Command("SELECT categoryId FROM Category WHERE itemType = :itemType")
        cmd.setString("itemType",self.itemType)
        cateIdList = cmd.open()
        for id in cateIdList:
            cate = self.categoryService.getCategory(id)
            if cate != None:
                catePath = CommonUtil.convertIntFrom36To10(cate.parentPath) + str(id) + "/"
                cmd = Command(" UPDATE " + resType + " SET channelCate = :channelCate WHERE channelCateId = :channelCateId ")
                cmd.setString("channelCate", catePath)
                cmd.setInteger("channelCateId", id)
                count = cmd.update()