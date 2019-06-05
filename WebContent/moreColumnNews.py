from cn.edustar.jitar.util import ParamUtil
from site_config import SiteConfig
from jitar_column_news_query import *

class moreColumnNews:
    def execute(self):
        site_config = SiteConfig()
        site_config.get_config()
        
        param = ParamUtil(request)
        columnId = param.safeGetIntParam("columnId")
        jitarColumnService = __spring__.getBean("jitarColumnService")
        jitarColumn = jitarColumnService.getJitarColumnById(columnId)
        if jitarColumn == None:
            request.setAttribute("NoJitarColumn", "")
            return "/WEB-INF/ftl/jimo/moreColumnNews.ftl"
            
               
        self.pager = param.createPager()
        self.pager.itemName = u""
        self.pager.itemUnit = u"条"
        self.pager.pageSize = 20

        qry = JitarColumnNewsQuery("jcn.columnNewsId, jcn.columnId, jcn.title, jcn.picture, jcn.createDate")
        qry.published = True
        qry.columnId = columnId
        self.pager.totalRows = qry.count()
        news_list = qry.query_map(self.pager)
        request.setAttribute("news_list", news_list)
        request.setAttribute("pager", self.pager)
        request.setAttribute("jitarColumn", jitarColumn)
        
        return "/WEB-INF/ftl/jimo/moreColumnNews.ftl"