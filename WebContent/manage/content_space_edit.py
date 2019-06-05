from base_action import *
from base_action import ActionResult
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import ContentSpace, User
from java.util import Date

class content_space_edit(BaseAdminAction):
    
    # 定义要返回的页面常量.
    ADMIN_MAIN = "/WEB-INF/ftl/admin/main.ftl"
    ERROR = "/WEB-INF/ftl/Error.ftl"    
    SUCCESS = "/WEB-INF/ftl/success.ftl"
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.contentSpaceService = __spring__.getBean("contentSpaceService")
        self.categoryService = __spring__.getBean("categoryService")
        
    def execute(self):
        user = self.loginUser
        if user == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(user) == False:
            self.addActionError(u"没有管理权限，只有超级管理员才可以使用此功能。")
            return ActionResult.ERROR
        
        if self.contentSpaceService == None:
            self.addActionError(u"无法获得 contentSpaceService 服务，检查配置文件。")
            return ActionResult.ERROR
        
        contentSpaceId = self.params.safeGetIntParam("id")
        print "contentSpaceId",contentSpaceId
        if contentSpaceId > 0 :
            contentSpace = self.contentSpaceService.getContentSpaceById(contentSpaceId)
            if contentSpace == None:
                self.addActionError(u"不能加载所指定的自定义分类对象，该对象可能被删除。")
                return ActionResult.ERROR        
        else:
            contentSpace = ContentSpace()
            contentSpace.setCreateDate(Date())
            contentSpace.setCreateUserId(self.loginUser.userId)
            contentSpace.setOwnerType(ContentSpace.CONTENTSPACE_OWNERTYPE_DEFAULT)
            contentSpace.setOwnerId(0)
            
        if request.getMethod() == "POST":
            spaceName = self.params.safeGetStringParam("space_name")
            if spaceName == "":
                self.addActionError(u"请输入自定义分类的名称。")
                return ActionResult.ERROR
            parentId = self.params.safeGetIntParam("parentId")
            if contentSpaceId > 0 :
                if self.contentSpaceService.isInChildPath(contentSpaceId,parentId) == True:
                    self.addActionError(u"不允许将父分类设置在子分类下面")
                    return ActionResult.ERROR
                if contentSpaceId == parentId:
                    self.addActionError(u"不允许将父分类设置成自己")
                    return ActionResult.ERROR
            contentSpace.setSpaceName(spaceName)
            contentSpace.setParentId(parentId)
            self.contentSpaceService.saveOrUpdateContentSpace(contentSpace)
            if contentSpaceId > 0 :
                self.addActionMessage(u"您成功修改了一个自定义文章分类： " + spaceName + u"。")
            else:    
                self.addActionMessage(u"您成功增加了一个自定义文章分类： " + spaceName + u"。")
            self.addActionLink(u"返回", "usercate.py")
            return ActionResult.SUCCESS
        request.setAttribute("contentSpace", contentSpace)
        #得到了categoryList
        categoryList = self.contentSpaceService.getContentSpaceTreeList(ContentSpace.CONTENTSPACE_OWNERTYPE_DEFAULT, 0)
        #需要把categoryList转换为CategoryTreeModel
        category_tree = self.categoryService.getCategoryTree(categoryList);
        request.setAttribute("category_tree", category_tree)
        return "/WEB-INF/ftl/admin/content_space_edit.ftl"
