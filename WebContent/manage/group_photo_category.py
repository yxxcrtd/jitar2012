from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Category
from cn.edustar.jitar.data import Command
from base_action import *
from base_blog_page import CategoryMixiner

group_svc = __jitar__.groupService
cate_svc = __jitar__.categoryService

class group_photo_category(ActionExecutor, CategoryMixiner):
    def __init__(self):
        ActionExecutor.__init__(self)
        self.params = ParamUtil(request)

    # execute() 由 ActionExecutor 实现, 我们只需要实现 dispatcher 即可.
    def dispatcher(self, cmd):
        if self.loginUser == None:
            return ActionResult.LOGIN
    
        # 得到当前协作组.
        if self.getCurrentGroup() == False:            
            return ActionResult.ERROR
        
        # 计算协作组图片分类 itemType.
        self.itemType = self.toGroupPhotoCategoryItemType(self.group)
        
        uuid=group_svc.getGroupCateUuid(self.group)
        if uuid==CategoryService.GROUP_CATEGORY_GUID_KTYJ :
            request.setAttribute("isKtGroup", "1")
        else:
            request.setAttribute("isKtGroup", "0")
                    
        if cmd == None or cmd == '': cmd = 'list'
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
        
        self.addActionError("未知命令 : " + cmd)
        return ActionResult.ERROR
  
    # 列出图片分类列表.
    def list(self):
        #if self.getParentCategory() == False:
        #  return ActionResult.ERROR
          
        self.category_tree = cate_svc.getCategoryTree(self.itemType);
        # print "self.category_tree = ", self.category_tree
        request.setAttribute("category_tree", self.category_tree)
        
        return "/WEB-INF/ftl/group/group_cate_photo_list.ftl"
  
  
    # 添加一个分类.
    def add(self):
        if self.getParentCategory() == False:
            return ActionResult.ERROR
        
        # 得到整个分类树.
        category_tree = cate_svc.getCategoryTree(self.itemType)
        request.setAttribute("category_tree", category_tree)
        
        # 构造一个新分类.
        category = Category()
        category.itemType = self.itemType
        category.parentId = self.parentId
        request.setAttribute("category", category)
        
        return "/WEB-INF/ftl/group/group_cate_photo_add.ftl"


    # 编辑修改/移动一个分类.
    def edit(self):
        # 得到要编辑的分类对象.
        if self.getCurrentCategory() == False:
            return ActionResult.ERROR
        request.setAttribute("category", self.category)
    
        # 得到整个分类树.
        category_tree = cate_svc.getCategoryTree(self.itemType)
        request.setAttribute("category_tree", category_tree)
        
        return "/WEB-INF/ftl/group/group_cate_photo_add.ftl"
  
    
    # 新建/保存一个分类.
    def save(self):
        # 获得和验证父分类参数.
        if self.getParentCategory() == False:
            return ActionResult.ERROR
        
        #print "self.params = ", self.params
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
            return ActionResult.ERROR
        
        if category.categoryId == 0:
            # 创建该分类.
            cate_svc.createCategory(category)
            self.addActionMessage(u"分类 " + category.name + u" 创建成功.")
        else:
            # 更新/移动分类.
            cate_svc.updateCategory(category)
            self.addActionMessage(u"分类 " + category.name + u" 修改/移动操作成功完成.")
          
        link = "?cmd=list&amp;groupId=" + str(self.group.groupId)
        self.addActionLink(u"返回", link)
        return ActionResult.SUCCESS

    # 删除所选分类.
    def delete(self):
        # 得到要删除的分类对象.
        if self.getCurrentCategory() == False:
            return ActionResult.ERROR
        
        # 验证其是否有子分类, 有子分类的必须要先删除子分类才能删除分类.
        if self.hasChildCategories(self.category):
            self.addActionError(u"分类 " + self.category.name + u" 有子分类, 必须先删除其所有子分类才能删除该分类.")
            return ActionResult.ERROR
    
        # 设置 GroupResource.groupCateId 都为 null.
        # 这里现在没有放在事务里面执行.
        self.updateGroupCategoryId(self.category)
        
        # 执行业务.
        cate_svc.deleteCategory(self.category)
        
        self.addActionMessage(u"分类 " + self.category.name + u" 已经成功删除.")
        return ActionResult.SUCCESS
    
  
    # 得到当前协作组, 协作组标识由页面传递过来.
    # 返回 True 表示有协作组, False 表示没有.
    def getCurrentGroup(self):
        # 得到协作组参数.
        groupId = self.params.getIntParam('groupId')
        if groupId == 0:
            self.addActionError(u"未给出要管理的协作组标识, 请确定您是从有效的链接进入的.")
            return False
        
        # 得到协作组.
        self.group = group_svc.getGroupMayCached(groupId)
        if self.group == None:
            self.addActionError(u"未找到指定标识为 %d 的协作组, 请确定您是从有效的链接进入的." % groupId)
            return False
        request.setAttribute("group", self.group)
        
        # TODO: 验证协作组状态.
        
        # TODO: 得到当前登录用户在协作组的身份.
        
        
        return True
      

    # 得到当前要操作的分类对象, 并验证其存在, 以及 itemType 匹配.
    # 返回 False 表示失败; True 表示成功.
    # 如果返回 True 则 self.category 中存放着拿出来的分类对象.
    def getCurrentCategory(self):        
        categoryId = self.params.getIntParam("categoryId")
        if categoryId == 0:
            self.addActionError(u"未给出要操作的分类.")
            return False
        
        category = cate_svc.getCategory(categoryId)
        if category == None:
            self.addActionError(u"未找到指定标识为 " + str(categoryId) + u" 的分类.")
            return False
        
        # 验证分类类型必须匹配.
        if category.itemType != self.itemType:
            self.addActionError(u"不匹配的分类类型.")
            return False
    
        self.category = category
        return True
    
      
      # 得到父分类标识参数及父分类对象, 并进行 itemType 验证.
    def getParentCategory(self):
        self.parentId = self.params.getIntParamZeroAsNull("parentId")
        request.setAttribute("parentId", self.parentId)
        if self.parentId == None:     # 认为是根分类.
            return True
        
        self.parentCategory = cate_svc.getCategory(self.parentId)
        request.setAttribute("parentCategory", self.parentCategory)
        # print "parentCategory = ", self.parentCategory
        if self.parentCategory == None:
            self.addActionError(u"未能找到指定标识的父分类, 请确定您是从有效的链接点击进入的.")
            return False
        if self.parentCategory.itemType != self.itemType:
            self.addActionError(u"不匹配的父分类类型: " + self.parentCategory.itemType)
            return False
        return True
      
      
    # 判断指定的分类是否具有子分类.
    def hasChildCategories(self, category):
        childCount = cate_svc.getChildrenCount(category.categoryId)
        return childCount > 0
    
    
    # 设置 GroupResource.groupCateId == category.categoryId 都为 null
    def updateGroupCategoryId(self, category):
        cmd = Command(""" UPDATE GroupPhoto SET groupCateId = NULL
            WHERE groupId = :groupId AND groupCateId = :groupCateId """)
        cmd.setInteger("groupId", self.group.groupId)
        cmd.setInteger("groupCateId", category.categoryId)
        count = cmd.update()
