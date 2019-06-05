from subject_page import *
from cn.edustar.jitar.pojos import SiteNews

class news_add(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.siteNews = None
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        newsId = self.params.safeGetIntParam("newsId")
        if newsId > 0:
            self.siteNews = self.subjectService.getSiteNews(newsId)  
                      
        if request.getMethod() == "POST":
            self.clear_subject_cache()
            return self.save_post()
        
        return self.news_edit()
    
    def news_edit(self):
        request.setAttribute("siteNews", self.siteNews)
        return "/WEB-INF/subjectmanage/news_add.ftl"
    
    def save_post(self):      
        news_title = self.params.safeGetStringParam("news_title")
        content = self.params.safeGetStringParam("content")
        pic_url = self.params.safeGetStringParam("picUrl")
        if news_title == "":
            self.addActionError(u"请输入标题")
            return self.ERROR
        if content == "":
            self.addActionError(u"请输入内容")
            return self.ERROR
        if self.siteNews == None:
            self.siteNews = SiteNews()
            self.siteNews.setSubjectId(self.subject.subjectId)
            self.siteNews.setStatus(0)
            self.siteNews.setNewsType(0)
        self.siteNews.setTitle(news_title)
        self.siteNews.setUserId(self.loginUser.userId)
        self.siteNews.setContent(content)
        self.siteNews.setPicture(pic_url)  
        self.subjectService.saveOrUpateSiteNews(self.siteNews)
        response.sendRedirect("news.py?id=" + str(self.subject.subjectId))