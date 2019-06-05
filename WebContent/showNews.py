
from cn.edustar.jitar.pojos import SiteNews
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command
from site_config import SiteConfig

class showNews:    
    def execute(self):
        site_config = SiteConfig()
        site_config.get_config()
        
        param = ParamUtil(request)
        newsId = param.getIntParam("newsId")
        if newsId == None or newsId == "":
            return
        self.sub_svc = __jitar__.getSubjectService()      
        
        news = self.sub_svc.getSiteNews(newsId)
        if news == None:
            return
        sql = """ Update SiteNews Set viewCount = viewCount + 1 Where newsId = :newsId """ 
        cmd = Command(sql)
        cmd.setInteger("newsId", newsId)
        cmd.update()
        request.setAttribute("news", news)
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/show_news.ftl"