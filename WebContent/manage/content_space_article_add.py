from base_action import *
from base_action import ActionResult
from cn.edustar.jitar.pojos import User, ContentSpaceArticle
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.util import ParamUtil
from java.util import Date

class content_space_article_add(BaseAdminAction):
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
        if accessControlService.isSystemAdmin(user) == False and accessControlService.isSystemContentAdmin(user) == False:
            self.addActionError(u"没有管理权限，只有超级管理员和系统内容管理员才可以使用此功能。")
            return ActionResult.ERROR

        if self.contentSpaceService == None:
            self.addActionError(u"无法获得 contentSpaceService 服务，检查配置文件。")
            return ActionResult.ERROR
        if request.getMethod() == "POST":
            contentSpaceArticle = ContentSpaceArticle()     
            contentSpaceId = self.params.safeGetIntParam("contentSpaceId")
            if contentSpaceId == 0:
                self.addActionError(u"无效的分类，请选择一个自定义分类。")
                return ActionResult.ERROR
            contentSpace = self.contentSpaceService.getContentSpaceById(contentSpaceId)
            if contentSpace == None:
                self.addActionError(u"不能加载自定义分类对象，请选择一个正确的标识。")
                return ActionResult.ERROR
            title = self.params.safeGetStringParam("title")
            pictureUrl = self.params.safeGetStringParam("pictureUrl")
            content = self.params.safeGetStringParam("content")
            if title == "":
                self.addActionError(u"请输入文章标题。")
                return ActionResult.ERROR
            if content == "":
                self.addActionError(u"请输入文章内容。")
                return ActionResult.ERROR
            contentSpaceArticle.setTitle(title)
            contentSpaceArticle.setContent(content)
            if pictureUrl == "":
                pictureUrl = None
            contentSpaceArticle.setPictureUrl(pictureUrl)
            contentSpaceArticle.setCreateUserId(self.loginUser.userId)
            contentSpaceArticle.setCreateUserLoginName(self.loginUser.loginName)
            contentSpaceArticle.setCreateDate(Date())
            contentSpaceArticle.setOwnerType(0)
            contentSpaceArticle.setOwnerId(0)
            contentSpaceArticle.setViewCount(0)
            contentSpaceArticle.setContentSpaceId(contentSpaceId)
            self.contentSpaceService.saveOrUpdateArticle(contentSpaceArticle)
            self.addActionMessage(u"您成功发表了一篇文章： " + title + u"。")            
            self.addActionLink(u"返回", "usercate_article.py")
            return ActionResult.SUCCESS
        
        self.get_content_space_cate()
        return "/WEB-INF/ftl/admin/content_space_article_add.ftl"
    
    def get_content_space_cate(self):
        #catelist = self.contentSpaceService.getAllContentSpaceList(0, 0)
        #request.setAttribute("catelist", catelist)
        #得到了categoryList
        categoryList = self.contentSpaceService.getContentSpaceTreeList(0, 0)
        #需要把categoryList转换为CategoryTreeModel
        category_tree = self.categoryService.getCategoryTree(categoryList);
        request.setAttribute("category_tree", category_tree)        
