from subject_page import *

class subjectlinks(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.siteLinksService = __spring__.getBean("siteLinksService")
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        if request.getMethod() == "POST":
            self.clear_subject_cache()
            self.post_action()
        
        return self.links_list()
    
    def links_list(self):
        links = self.siteLinksService.getSiteLinksList("subject",self.subject.subjectId)
        request.setAttribute("links",links)
        request.setAttribute("subject",self.subject)
        return "/WEB-INF/subjectmanage/subjectlinks.ftl"
    
    def post_action(self):
        gs = self.params.safeGetIntValues("guid")
        for g in gs:
            siteLinks = self.siteLinksService.getSiteLinks(g)
            if None != siteLinks:
                self.siteLinksService.delete(siteLinks)