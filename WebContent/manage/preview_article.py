from base_action import *
from base_action import ActionResult
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import ContentSpace
from site_config import SiteConfig

class preview_article(BaseAdminAction):
    # 定义要返回的页面常量.
    ADMIN_MAIN = "/WEB-INF/ftl/admin/main.ftl"
    ERROR = "/WEB-INF/ftl/Error.ftl"    
    SUCCESS = "/WEB-INF/ftl/success.ftl"
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.contentSpaceService = __spring__.getBean("contentSpaceService")
        
    def execute(self):
        user = self.loginUser
        if user == None:
            return ActionResult.LOGIN
        if self.contentSpaceService == None:
            self.addActionError(u"无法获得 contentSpaceService 服务，检查配置文件。")
            return ActionResult.ERROR
        contentSpaceArticleId = self.params.safeGetIntParam("contentSpaceArticleId")
        contentSpaceArticle = self.contentSpaceService.getContentSpaceArticleById(contentSpaceArticleId)
        if contentSpaceArticle == None:
            self.addActionError(u"无法加载所请求的文章。")
            return ActionResult.ERROR
        site_config = SiteConfig()
        site_config.get_config()
        request.setAttribute("contentSpaceArticle", contentSpaceArticle)        
        return "/WEB-INF/ftl/admin/preview_article.ftl"
