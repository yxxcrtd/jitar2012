from base_action import BaseAction
from cn.edustar.jitar.util import ParamUtil
from site_config import SiteConfig

class preview_article(BaseAction):
    
    def execute(self):
        site_config = SiteConfig()
        site_config.get_config()
        
        if self.loginUser == None:
            return self.LOGIN
        
        accessControlService = __spring__.getBean("accessControlService")
        contol_list = accessControlService.getAllAccessControlByUser(self.loginUser)
        if contol_list == None:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        articleService = __spring__.getBean("articleService")
        self.params = ParamUtil(request)
        articleId = self.params.safeGetIntParam("articleId")
        article = articleService.getArticle(articleId)
        if article == None:
            self.addActionError(u"无法加载文章。")
            return self.ERROR
        request.setAttribute("article", article)
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/subjectmanage/preview_article.ftl"