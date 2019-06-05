from base_action import *
from base_action import ActionResult
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import ContentSpace, User
from java.util import Date

class content_space_edit(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.contentSpaceService = __spring__.getBean("contentSpaceService")
        self.categoryService = __spring__.getBean("categoryService")
        
    def execute(self):
        user = self.loginUser
        if user == None:
            return ActionResult.LOGIN
        
        if self.isAdmin() == False:
            self.addActionError(u"您没有管理的权限！")
            return ActionResult.ERROR
        
        if self.contentSpaceService == None:
            self.addActionError(u"无法获得 contentSpaceService 服务，检查配置文件。")
            return ActionResult.ERROR
        
        contentSpaceId = self.params.safeGetIntParam("contentSpaceId")
        if contentSpaceId > 0 :
            contentSpace = self.contentSpaceService.getContentSpaceById(contentSpaceId)
            if contentSpace == None:
                self.addActionError(u"不能加载所指定的自定义分类对象，该对象可能被删除。")
                return ActionResult.ERROR        
        else:
            contentSpace = ContentSpace()
            contentSpace.setCreateDate(Date())
            contentSpace.setCreateUserId(self.loginUser.userId)
            contentSpace.setOwnerType(ContentSpace.CONTENTSPACE_OWNERTYPE_SUBJECT)
            contentSpace.setOwnerId(self.subject.subjectId)

        if request.getMethod() == "POST":
            spaceName = self.params.safeGetStringParam("space_name")
            parentId = self.params.safeGetIntParam("parentId")
            if spaceName == "":
                self.addActionError(u"请输入自定义分类的名称。")
                return ActionResult.ERROR
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
            self.addActionLink(u"返回", "contentspace_list.py?id=" + str(self.subject.subjectId))
            return ActionResult.SUCCESS
        request.setAttribute("contentSpace", contentSpace)
        request.setAttribute("subject", self.subject)
        #得到了categoryList
        categoryList = self.contentSpaceService.getContentSpaceTreeList(ContentSpace.CONTENTSPACE_OWNERTYPE_SUBJECT, self.subject.subjectId)
        #需要把categoryList转换为CategoryTreeModel
        category_tree = self.categoryService.getCategoryTree(categoryList);
        request.setAttribute("category_tree", category_tree)        
        return "/WEB-INF/subjectmanage/content_space_edit.ftl"
