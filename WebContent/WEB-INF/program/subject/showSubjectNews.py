from subject_page import *
from cn.edustar.jitar.pojos import SiteNews

class showSubjectNews(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        
    def execute(self):
        if self.subject == None:
            self.addActionError(u"无法加载指定的学科。")
            return self.ERROR
        
        newsId = self.params.safeGetIntParam("newsId")
        news = self.subjectService.getSiteNews(newsId)
        if news == None:
            self.addActionError(u"该条新闻不存在。")
            return self.ERROR
        
        if self.isAdmin() == False:
            if news.status != 0:
                self.addActionError(u"该条新闻还未审核。")
                return self.ERROR
        
        templateName = "template1"
        if self.subject.templateName != None:
            templateName = self.subject.templateName
        request.setAttribute("subject",self.subject)
        request.setAttribute("news",news)
        return "/WEB-INF/subjectpage/" + templateName + "/showSubjectNews.ftl"