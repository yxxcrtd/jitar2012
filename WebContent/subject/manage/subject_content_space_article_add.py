from subject_page import *
from cn.edustar.jitar.pojos import ContentSpace, ContentSpaceArticle
from java.util import Date

class subject_content_space_article_add(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.contentSpaceService = __spring__.getBean("contentSpaceService")
        self.categoryService = __spring__.getBean("categoryService")
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        if self.contentSpaceService == None:
            self.addActionError(u"无法自己自定义分类服务，请检查配置文件。")
            return self.ERROR
        contentSpaceArticleId = self.params.safeGetIntParam("contentSpaceArticleId")
        contentSpaceArticle = self.contentSpaceService.getContentSpaceArticleById(contentSpaceArticleId)
        if contentSpaceArticle == None:
            contentSpaceArticle = ContentSpaceArticle()
            contentSpaceArticle.setTitle("")             
            contentSpaceArticle.setContent("")
            contentSpaceArticle.setContentSpaceId(0)
                     
        if request.getMethod() == "POST":
            title = self.params.safeGetStringParam("title")
            content = self.params.safeGetStringParam("content")
            contentSpaceId = self.params.safeGetIntParam("contentSpaceId")
            pictureUrl = self.params.safeGetStringParam("pictureUrl")
            if title == "":
                self.addActionError(u"文章标题不能为空。")
                return self.ERROR
            if content == "":
                self.addActionError(u"文章内容不能为空。")
                return self.ERROR
            if pictureUrl == "":
                pictureUrl = None
            if contentSpaceArticleId == 0:
                contentSpaceArticle.setCreateDate(Date())
                contentSpaceArticle.setCreateUserId(self.loginUser.userId)
                contentSpaceArticle.setCreateUserLoginName(self.loginUser.loginName)
                contentSpaceArticle.setOwnerType(ContentSpace.CONTENTSPACE_OWNERTYPE_SUBJECT)
                contentSpaceArticle.setOwnerId(self.subject.subjectId)
              
            contentSpaceArticle.setTitle(title)
            contentSpaceArticle.setContent(content)
            contentSpaceArticle.setPictureUrl(pictureUrl)
            contentSpaceArticle.setContentSpaceId(contentSpaceId)            
           
            self.contentSpaceService.saveOrUpdateArticle(contentSpaceArticle)
            self.addActionMessage(u"您成功创建了一个自定义分类文章： " + title + u"。")
            self.addActionLink(u"返回", "subject_contentspace_article_list.py?id=" + str(self.subject.subjectId))
            return self.SUCCESS
                
            return self.SUCCESS
        
        self.get_contentspace_list()
        request.setAttribute("subject", self.subject)        
        request.setAttribute("contentSpaceArticle", contentSpaceArticle)
        return "/WEB-INF/subjectmanage/subject_content_space_article_add.ftl"
    
    def get_contentspace_list(self):
        #contentSpaceList = self.contentSpaceService.getAllContentSpaceList(ContentSpace.CONTENTSPACE_OWNERTYPE_SUBJECT, self.subject.subjectId)
        #request.setAttribute("contentSpaceList", contentSpaceList)
        #得到了categoryList
        categoryList = self.contentSpaceService.getContentSpaceTreeList(ContentSpace.CONTENTSPACE_OWNERTYPE_SUBJECT, self.subject.subjectId)
        #需要把categoryList转换为CategoryTreeModel
        category_tree = self.categoryService.getCategoryTree(categoryList);
        request.setAttribute("category_tree", category_tree)         
