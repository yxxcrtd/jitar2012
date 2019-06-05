from unit_page import UnitBasePage
from cn.edustar.jitar.pojos import ContentSpace, ContentSpaceArticle

class preview_contentspace_article(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.unitService = __spring__.getBean("unitService")
        self.contentSpaceService = __spring__.getBean("contentSpaceService")
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        
        if self.contentSpaceService == None:
            self.addActionError(u"无法自己自定义分类服务，请检查配置文件。")
            return self.ERROR
        
        if self.isUnitAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
        
        
        
        contentSpaceArticleId = self.params.safeGetIntParam("contentSpaceArticleId")
        contentSpaceArticle = self.contentSpaceService.getContentSpaceArticleById(contentSpaceArticleId)
        if contentSpaceArticle == None:
            self.addActionError(u"不能加载文章。")
            return self.ERROR
        
        
        
        request.setAttribute("unit", self.unit)        
        request.setAttribute("contentSpaceArticle", contentSpaceArticle)
        templateName = "template1"
        if self.unit.templateName != None:
            templateName = self.unit.templateName
            
        return "/WEB-INF/unitspage/" + templateName + "/preview_contentspace_article.ftl"
