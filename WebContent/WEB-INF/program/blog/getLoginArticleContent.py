from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Article
from base_action import BaseAction
articleService = __jitar__.articleService

# 显示个人的文章
class getLoginArticleContent(BaseAction):
    def execute(self):
        if self.loginUser == None:
            response.getWriter().write(u"<p style='font-weight:bold;font-size:2em;color:red;text-align:center;padding:20px 0'>本文章的内容需要登录才能查看。</p>")
            return
        params = ParamUtil(request)
        articleId = params.safeGetIntParam("articleId")
        article = self.articleService.getArticle(articleId)
        if article == None:
            response.getWriter().write(u"<p style='color:red;text-align:center;padding:20px 0'>没有加载到文章。</p>")
            return
        response.getWriter().write(article.articleContent)