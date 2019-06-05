from subject_page import *

class showContent(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        
    def execute(self):
        if self.subject == None:
            self.addActionError(u"无法加载指定的学科。")
            return self.ERROR
        
        articleId = self.params.safeGetIntParam("articleId")
        contentSpaceArticle = __spring__.getBean("contentSpaceService").getContentSpaceArticleById(articleId)
        if contentSpaceArticle == None:
            self.addActionError(u"该条文章不存在。")
            return self.ERROR
        
        templateName = "template1"
        if self.subject.templateName != None:
            templateName = self.subject.templateName
        request.setAttribute("subject", self.subject)
        request.setAttribute("contentSpaceArticle", contentSpaceArticle)
        contentSpaceArticle.setViewCount(contentSpaceArticle.viewCount + 1)
        __spring__.getBean("contentSpaceService").saveOrUpdateArticle(contentSpaceArticle)
        return "/WEB-INF/subjectpage/" + templateName + "/showContentSpaceArticle.ftl"
