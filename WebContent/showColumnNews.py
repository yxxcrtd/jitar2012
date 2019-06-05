from cn.edustar.jitar.util import ParamUtil
from site_config import SiteConfig
from jitar_column_news_query import *
from cn.edustar.jitar.jython import JythonBaseAction

class showColumnNews(JythonBaseAction):
    def execute(self):
        site_config = SiteConfig()
        site_config.get_config()
        
        param = ParamUtil(request)
        jitarColumnService = __spring__.getBean("jitarColumnService")
        columnNewsId = param.safeGetIntParam("columnNewsId")
        columnNews = jitarColumnService.getJitarColumnNewsById(columnNewsId)
        if columnNews == None:
            request.setAttribute("NoJitarColumnNews", "")
            return "/WEB-INF/ftl/jimo/showColumnNews.ftl"
        jitarColumnId = columnNews.columnId
        jitarColumn = jitarColumnService.getJitarColumnById(jitarColumnId)
        if jitarColumn == None:
            self.addActionError(u"该栏目已经不存在，或者已经被删除。")
            return "/WEB-INF/ftl/Error.ftl"
        
        canAnonymousShowContent = "1"
        if jitarColumn.anonymousShowContent == False and self.loginUser == None:
            canAnonymousShowContent = "0"
        strip = self.get_client_ip()
        if strip == None:strip = ""
        if strip.startswith("172.") or strip.startswith("192.") or strip.startswith("10.") or strip.startswith("127."):
            canAnonymousShowContent = "1"
        
        if param.existParam("from") == False:
            columnNews.viewCount = columnNews.viewCount + 1
            jitarColumnService.saveOrUpdateJitarColumnNews(columnNews)
        request.setAttribute("columnNews", columnNews)
        request.setAttribute("canAnonymousShowContent", canAnonymousShowContent)
        
        return "/WEB-INF/ftl/jimo/showColumnNews.ftl"
    
    def get_client_ip(self):
        strip = request.getHeader("x-forwarded-for")
        if strip == None or strip == "":
            strip = request.getRemoteAddr()
        return strip
