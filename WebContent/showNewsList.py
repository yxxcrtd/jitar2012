from cn.edustar.jitar.data import *
from cn.edustar.jitar.util import ParamUtil
from site_config  import SiteConfig

class showNewsList:
    def execute(self):
        # print "Hello, showNewsList.py is running!"
        site_config = SiteConfig()
        site_config.get_config()
        
        # 得到分页
        param = ParamUtil(request)
        pager = param.createPager()
        pager.itemName = u"新闻"
        pager.itemUnit = u"条"
        pager.pageSize = 20
        
        #=======================================================================
        # qry = SiteNewsQuery(""" snews.newsId, snews.userId, snews.title, snews.picture, snews.createDate, snews.viewCount """)
        # qry.subjectId = 0
        # cc =  qry.count()
        # pager.totalRows = cc
        # news_list = qry.query_map(pager)
        #=======================================================================
        # 计算新闻总数.
        sql = """SELECT COUNT(*) FROM SiteNews news WHERE news.status = 0 AND news.subjectId=0 """
        count = Command(sql).int_scalar()
        pager.totalRows = count
        
        # 获取当前页新闻.
        sql = """SELECT new Map(news.newsId as newsId, news.userId as userId, news.title as title, 
                       news.picture as picture, news.status as status, news.newsType as newsType,
                       news.createDate as createDate, news.viewCount as viewCount)
                FROM SiteNews news
                WHERE news.status = 0 AND news.subjectId = 0
                ORDER BY news.newsId DESC """
        news_list = Command(sql).open(pager)        
    
        request.setAttribute("news_list", news_list)
        request.setAttribute("pager", pager)
    
        return "/WEB-INF/ftl/showNewsList.ftl"
