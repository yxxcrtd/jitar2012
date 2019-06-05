# script for admin_category
from cn.edustar.jitar.pojos import Category
from cn.edustar.jitar.data import Command
from java.lang import RuntimeException
from cn.edustar.jitar.service import CategoryService;
from base_action import *

# 工作室分类、协作组分类、文章分类、资源分类、图片分类
class admin_category(ActionExecutor):
    def __init__(self):
        # 调用基类的 __init__ 
        ActionExecutor.__init__(self)
        self.cate_svc = __jitar__.categoryService
    
    # execute()由'ActionExecutor'实现，我们只需要实现'dispatcher'即可
    def dispatcher(self, cmd):
        try:
            # 登录验证
            if self.loginUser == None:
                return ActionResult.LOGIN
            
            # 管理权限验证
            if self.canAdmin() == False:
                self.addActionError(u"没有管理系统分类的权限.")
                return ActionResult.ERROR

            result = self.__dispatcher__(cmd)
            return result
        except RuntimeException, ex:
            request.setAttribute("exception", ex)
            self.addActionError(u"执行操作的时候发生异常: " + ex.message)
            return ActionResult.ERROR
      
    
    def __dispatcher__(self, cmd):
        # 得到并验证分类类型
        self.itemType = self.params.getStringParam("type")
        
        # 验证是不是有效的系统分类
        if self.isValidItemType(self.itemType) == False:
            return ActionResult.ERROR
        
        #print "cmd:", cmd
        #print "itemType:", self.itemType
        
        # 缺省为系统分类，也就是文章分类
        if self.itemType == None or self.itemType == "": 
            self.itemType = "default"
        
        request.setAttribute("type", self.itemType)
    
        # 执行命令
        if cmd == None or cmd == "":
            cmd = "list"
        
        if cmd == "list":
            return self.list()
        elif cmd == "add":
            return self.add()
        elif cmd == "edit":
            return self.edit()
        elif cmd == "save":
            return self.save()
        elif cmd == "delete":
            return self.delete()
        
        return self.unknownCommand(cmd)
  

    # 分类列表
    def list(self):
        if self.getParentCategory() == False:
            return ActionResult.ERROR
        
        category_list = self.cate_svc.getChildCategories(self.itemType, self.parentId)
        request.setAttribute("category_list", category_list)
        return "/WEB-INF/ftl/admin/category_list.ftl"

  
    # 添加分类
    def add(self):
        if self.getParentCategory() == False:
            return ActionResult.ERROR

        # 得到整个分类树
        category_tree = self.cate_svc.getCategoryTree(self.itemType)
        request.setAttribute("category_tree", category_tree)

        # 构造一个新分类.
        category = Category()
        category.itemType = self.itemType
        category.parentId = self.parentId
        request.setAttribute("isKtGroup", "0")
        if self.parentId!=None:
            parentCate=self.cate_svc.getCategory(self.parentId)
            if parentCate!=None:
                uuid=parentCate.objectUuid
                if uuid==CategoryService.GROUP_CATEGORY_GUID_KTYJ :
                    request.setAttribute("isKtGroup", "1")
        #print category.itemType
        #print category.parentId
        request.setAttribute("category", category)
        return "/WEB-INF/ftl/admin/category_add.ftl"


    # 编辑修改或移动一个分类
    def edit(self):
        # 得到要编辑的分类对象
        if self.getCurrentCategory() == False:
            return ActionResult.ERROR
        request.setAttribute("category", self.category)
        # 得到整个分类树
        category_tree = self.cate_svc.getCategoryTree(self.itemType)
        request.setAttribute("category_tree", category_tree)
        return "/WEB-INF/ftl/admin/category_add.ftl"


    # 新建或保存一个分类
    def save(self):
        # 获得和验证父分类参数
        if self.getParentCategory() == False:
            return ActionResult.ERROR

        # 从提交数据中组装出 category 对象.
        category = Category()
        category.categoryId = self.params.getIntParam("categoryId")
        category.name = self.params.getStringParam("name")
        category.itemType = self.itemType
        category.parentId = self.parentId
        category.description = self.params.getStringParam("description")
    
        # 验证
        if category.name == None or category.name == "":
            self.addActionError(u"未填写分类名字！")
            return ActionResult.ERROR

        if category.categoryId == 0:
            # 创建该分类.
            self.cate_svc.createCategory(category)
            self.addActionMessage(u"分类：" + category.name + u" 创建成功！")
        else:
            # 更新/移动分类.
            self.cate_svc.updateCategory(category)
            self.addActionMessage(u"分类：" + category.name + u" 修改/移动操作成功完成.")
      
        link = "?cmd=list&amp;type=" + self.itemType + "&amp;parentId="
        if category.parentId != None:
            link += str(category.parentId)
        self.addActionLink(u"返回", link)
        return ActionResult.SUCCESS


    # 删除一个分类，分类删除不支持批处理
    def delete(self):
        # 得到要删除的分类对象
        if self.getCurrentCategory() == False:
            return ActionResult.ERROR
    
        # 验证其是否有子分类, 有子分类的必须要先删除子分类才能删除分类
        if self.hasChildCategories(self.category):
            self.addActionError(u"分类 " + self.category.name + u" 有子分类, 必须先删除其所有子分类才能删除该分类.")
            return ActionResult.ERROR

        # 执行业务
        self.cate_svc.deleteCategory(self.category)
        self.addActionMessage(u"分类：" + self.category.name + u" 已经成功删除！")
        return ActionResult.SUCCESS


    # 判断是否是我们支持的分类类型
    def isValidItemType(self, itemType):
        # 文章分类
        if itemType == 'default':
            return True
                
        # 工作室分类
        if itemType == "blog":
            return True
        
        # 协作组分类
        if itemType == 'group':
            return True
        
        # 资源分类
        if itemType == 'resource':
            return True
        
        # 视频分类
        if itemType == 'video':
            return True
        
        # 图片分类
        if itemType == 'photo':
            return True
        
        self.addActionError(u"不支持的分类类型：" + itemType)
        return False;


    # 得到当前要操作的分类对象，并验证其是否存在，以及'itemType'匹配。
    # 返回'False'表示失败；返回'True'表示成功。(如果返回'True'则'self.category'中存放着拿出来的分类对象
    def getCurrentCategory(self):
        categoryId = self.params.getIntParam("categoryId")
        if categoryId == 0:
            self.addActionError(u"未给出要操作的分类！")
            return False
    
        category = self.cate_svc.getCategory(categoryId)
        if category == None:
            self.addActionError(u"未找到指定标识为：" + str(categoryId) + u" 的分类！")
            return False
    
        # 验证分类类型必须匹配
        if category.itemType != self.itemType:
            self.addActionError(u"不匹配的分类类型！")
            return False

        self.category = category
        return True


    # 得到父分类标识参数及父分类对象，并进行'itemType'验证
    def getParentCategory(self):
        self.parentId = self.params.getIntParamZeroAsNull("parentId")
        #print "父分类ID标识：", self.parentId
        request.setAttribute("parentId", self.parentId)
        # 认为是根分类
        if self.parentId == None:
            return True
        # 根据'分类标识'得到分类对象
        self.parentCategory = self.cate_svc.getCategory(self.parentId)
        request.setAttribute("parentCategory", self.parentCategory)
        #print "self.parentCategory:", self.parentCategory
        if self.parentCategory == None:
            self.addActionError(u"未能找到指定标识的父分类，请确定您是从有效的链接点击进入的！")
            return False
        if self.parentCategory.itemType != self.itemType:
            self.addActionError(u"不匹配的父分类类型：" + self.parentCategory.itemType)
            return False
        return True
  
  
    # 判断指定的分类是否具有子分类
    def hasChildCategories(self, category):
        childCount = self.cate_svc.getChildrenCount(category.categoryId)
        return childCount > 0