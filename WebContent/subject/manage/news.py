from subject_page import *
from site_news_query import SiteNewsQuery

class news(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)

    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        if request.getMethod() == "POST":
            self.clear_subject_cache()
            self.post_action()
        
        return self.news_list()
        
    def news_list(self):
        qry = SiteNewsQuery(""" snews.newsId, snews.title, snews.picture, snews.status, snews.newsType, 
                                snews.createDate, snews.viewCount, snews.userId """)
        qry.subjectId = self.subject.subjectId
        qry.status = None
        pager = self.params.createPager()
        pager.itemName = u"新闻"
        pager.itemUnit = u"个"
        pager.pageSize = 25
        pager.totalRows = qry.count()
        subject_news_list = qry.query_map(pager)
        request.setAttribute("subject",self.subject)
        request.setAttribute("subject_news_list",subject_news_list)
        return "/WEB-INF/subjectmanage/news_list.ftl"
    
    def post_action(self):
        cmd = self.params.safeGetStringParam("cmd")
        guids = self.params.safeGetIntValues("guid")
        for id in guids:
            news = self.subjectService.getSiteNews(id)
            if news != None:
                if cmd == "delete":                
                    self.subjectService.deleteSiteNews(news)
                elif cmd == "approve":
                    self.subjectService.auditSiteNews(news,True)
                elif cmd == "unapprove":
                    self.subjectService.auditSiteNews(news,False)
                                       