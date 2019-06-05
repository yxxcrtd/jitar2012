from unit_page import *
from contentspacearticle_query import ContentSpaceArticleQuery

class showContent(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        
    def execute(self):
        articleId = self.params.safeGetIntParam("articleId")
        contentSpaceArticle = __spring__.getBean("contentSpaceService").getContentSpaceArticleById(articleId)
        if contentSpaceArticle == None:
            self.addActionError(u"该条文章不存在。")
            return self.ERROR
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"不能加载机构信息。")
            return self.ERROR 
        
        templateName = "template1"
        if self.unit.templateName != None:
            templateName = self.unit.templateName
        request.setAttribute("unit", self.unit)
        contentSpaceArticle.setViewCount(contentSpaceArticle.viewCount + 1)
        __spring__.getBean("contentSpaceService").saveOrUpdateArticle(contentSpaceArticle)
        request.setAttribute("contentSpaceArticle", contentSpaceArticle)
        return "/WEB-INF/unitspage/" + templateName + "/showContentSpaceArticle.ftl"
