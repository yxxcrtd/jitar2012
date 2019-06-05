from subject_page import *
from cn.edustar.jitar.pojos import ContentSpace
from java.util import Date

class contentspace_list(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.contentSpaceService = __spring__.getBean("contentSpaceService")
        self.categoryService = __spring__.getBean("categoryService")
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin() == False:
            self.addActionError(u"您没有管理的权限！")
            return self.ERROR
        
        if self.contentSpaceService == None:
            self.addActionError(u"无法自己自定义分类服务，请检查配置文件。")
            return self.ERROR
        
        if request.getMethod() == "POST":
            cmd = self.params.safeGetStringParam("cmd")
            if cmd == "add":
                space_name = self.params.safeGetStringParam("space_name")
                if space_name == "":
                    self.addActionError(u"分类名称不能为空，请输入自定义分类名称。")
                    return self.ERROR
                contentSpace = ContentSpace()
                contentSpace.setSpaceName(space_name)
                contentSpace.setCreateDate(Date())
                contentSpace.setCreateUserId(self.loginUser.userId)
                contentSpace.setOwnerType(ContentSpace.CONTENTSPACE_OWNERTYPE_SUBJECT)
                contentSpace.setOwnerId(self.subject.subjectId)
                contentSpace.setArticleCount(0)
                self.contentSpaceService.saveOrUpdateContentSpace(contentSpace)
                self.addActionMessage(u"您成功创建了一个自定义文章分类： " + space_name + u"。")
                self.addActionLink(u"返回", "contentspace_list.py?id=" + str(self.subject.subjectId))
                return self.SUCCESS                
            elif cmd == "delete":
                guids = self.params.safeGetIntValues("guid")
                for g in guids:
                    childList = self.contentSpaceService.getContentSpaceList(g)
                    if childList != None :
                        if childList.size() > 0 :
                            self.addActionError(u"分类下存在子分类，不允许删除该分类。")
                            return ActionResult.ERROR                    
                    contentSpace = self.contentSpaceService.getContentSpaceById(g)
                    if contentSpace != None:
                        # 删除文章
                        self.contentSpaceService.deleteContentSpaceArticleByContentSpaceId(contentSpace.contentSpaceId)
                        self.contentSpaceService.deleteContentSpace(contentSpace)
                self.addActionMessage(u"删除成功。")
                self.addActionLink(u"返回", "contentspace_list.py?id=" + str(self.subject.subjectId))
                return ActionResult.SUCCESS
            else:
                self.addActionError(u"无效的命令。")
                return ActionResult.ERROR
        
        self.get_contentspace_list()
        request.setAttribute("subject", self.subject)
        return "/WEB-INF/subjectmanage/contentspace_list.ftl"
    
    def get_contentspace_list(self):
        #contentSpaceList = self.contentSpaceService.getAllContentSpaceList(ContentSpace.CONTENTSPACE_OWNERTYPE_SUBJECT, self.subject.subjectId)
        #request.setAttribute("contentSpaceList", contentSpaceList)
        # 更新文章计数
        #for contentSpace in contentSpaceList:
        #    contentSpace.setArticleCount(self.contentSpaceService.getContentSpaceArticleCountById(contentSpace.contentSpaceId))
        #    self.contentSpaceService.saveOrUpdateContentSpace(contentSpace)
        #得到了categoryList
        categoryList = self.contentSpaceService.getContentSpaceTreeList(ContentSpace.CONTENTSPACE_OWNERTYPE_SUBJECT, self.subject.subjectId)
        #需要把categoryList转换为CategoryTreeModel
        category_tree = self.categoryService.getCategoryTree(categoryList);
        request.setAttribute("category_tree", category_tree)
        # 更新文章计数
        for category in categoryList:
            contentSpace = category.extendedObject
            contentSpace.setArticleCount(self.contentSpaceService.getContentSpaceArticleCountById(contentSpace.contentSpaceId))
            self.contentSpaceService.saveOrUpdateContentSpace(contentSpace)
