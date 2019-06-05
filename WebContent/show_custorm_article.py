from cn.edustar.jitar.data import *
from cn.edustar.jitar.util import ParamUtil
from site_config import SiteConfig
from article_query import ArticleQuery
from contentspacearticle_query import ContentSpaceArticleQuery

class show_custorm_article:
    def execute(self):        
        site_config = SiteConfig()
        site_config.get_config()        
        
        # 得到分页
        param = ParamUtil(request)
        type = param.safeGetIntParam("type")
        
        pager = param.createPager()
        pager.itemName = u"文章"
        pager.itemUnit = u"篇"
        pager.pageSize = 30
        
        if type == 2:
            qry = ContentSpaceArticleQuery(""" cs.spaceName, csa.title, csa.createDate, csa.viewCount, csa.contentSpaceArticleId """)
            qry.ownerType = 0
            qry.ownerId = 0
            qry.contentSpaceId = param.safeGetIntParam("categoryId")
            pager.totalRows = qry.count()
            article_list = qry.query_map(pager)
            request.setAttribute("article_list", article_list)        
            request.setAttribute("pager", pager)
            request.setAttribute("Page_Title", param.safeGetStringParam("title"))
            return "/WEB-INF/ftl/mengv1/index/show_custorm_contentspace_article.ftl"
        else:
            qry = ArticleQuery(""" a.articleId, a.title, a.createDate, a.viewCount, a.commentCount, a.userId,
                                  a.recommendState, a.typeState, u.loginName, u.nickName, u.trueName """)
            pager.totalRows = qry.count()
            article_list = qry.query_map(pager)
            request.setAttribute("article_list", article_list)        
            request.setAttribute("pager", pager)
            request.setAttribute("Page_Title", param.safeGetStringParam("title"))
            return "/WEB-INF/ftl/mengv1/index/show_custorm_article.ftl"
