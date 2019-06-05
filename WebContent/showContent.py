from site_config import SiteConfig
from cn.edustar.jitar.util import ParamUtil

class showContent:
    def __init__(self):
        self.params = ParamUtil(request)
        self.contentSpaceService = __spring__.getBean("contentSpaceService")
        
    def execute(self):
        if self.contentSpaceService == None:
            self.addActionError(u"无法获得 contentSpaceService 服务，检查配置文件。")
            return ActionResult.ERROR
        contentSpaceArticleId = self.params.safeGetIntParam("articleId")
        contentSpaceArticle = self.contentSpaceService.getContentSpaceArticleById(contentSpaceArticleId)
        if contentSpaceArticle == None:
            self.addActionError(u"无法加载所请求的文章。")
            return ActionResult.ERROR
        
        contentSpaceArticle.setViewCount(contentSpaceArticle.viewCount + 1)
        self.contentSpaceService.saveOrUpdateArticle(contentSpaceArticle)
        site_config = SiteConfig()
        site_config.get_config()
        request.setAttribute("contentSpaceArticle", contentSpaceArticle)        
        return "/WEB-INF/ftl/admin/preview_article.ftl"
