from subject_page import *
from site_news_query import SiteNewsQuery

class newsList(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
    
    def execute(self):
        if self.subject == None:
            self.addActionError(u"无法加载指定的学科。")
            return self.ERROR
        
        self.templateName = "template1"
        if self.subject.templateName != None:
            self.templateName = self.subject.templateName
        
        type = self.params.safeGetStringParam("type")
        qry = SiteNewsQuery(" snews.newsId, snews.title, snews.createDate, snews.picture ")
        qry.subjectId = self.subject.subjectId
        Page_Title = self.params.safeGetStringParam("title")
        if "pic" == type:
            if Page_Title == "":
                Page_Title = u"图片新闻"
            qry.hasPicture = True
        elif "text" == type:
            qry.hasPicture = False
            if Page_Title == "":
                Page_Title = u"学科动态"        
        else:
            if Page_Title == "":
                Page_Title = u"学科新闻"
                
        pager = None
        pager = self.params.createPager()
        pager.pageSize = 10
        pager.itemName = u"新闻"
        pager.itemUnit = u"条"
        pager.totalRows = qry.count()
        news_list = qry.query_map(pager)
        request.setAttribute("Page_Title", Page_Title)
        request.setAttribute("news_list", news_list)
        request.setAttribute("pager", pager)
        request.setAttribute("subject", self.subject)
        
        return "/WEB-INF/subjectpage/" + self.templateName + "/newsList.ftl"   