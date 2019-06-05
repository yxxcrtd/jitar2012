from subject_page import *
from cn.edustar.jitar.pojos import ContentSpace, ContentSpaceArticle

class preview_contentspace_article(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.contentSpaceService = __spring__.getBean("contentSpaceService")
        
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
            self.addActionError(u"不能加载文章。")
            return self.ERROR
        request.setAttribute("subject", self.subject)        
        request.setAttribute("contentSpaceArticle", contentSpaceArticle)
        templateName = "template1"
        if self.subject.templateName != None:
            templateName = self.subject.templateName
            
        return "/WEB-INF/subjectpage/" + templateName + "/preview_contentspace_article.ftl"
