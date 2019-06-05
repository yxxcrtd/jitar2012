from subject_page import *
from cn.edustar.jitar.pojos import SiteLinks

class links_add(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.siteLinksService = __spring__.getBean("siteLinksService")
        self.siteLinks = None
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.isAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        linkId = self.params.safeGetIntParam("linkId")
        if linkId > 0:
            self.siteLinks = self.siteLinksService.getSiteLinks(linkId)

        if request.getMethod() == "POST":
            self.save_post()
            self.clear_subject_cache()
        
        return self.news_edit()
    
    def news_edit(self):
        request.setAttribute("siteLinks", self.siteLinks)
        return "/WEB-INF/subjectmanage/links_add.ftl"
    
    def save_post(self):
        linkName = self.params.safeGetStringParam("linkName")
        linkAddress = self.params.safeGetStringParam("linkAddress")       
        if linkName == "":
            self.addActionError(u"请输入标题")
            return self.ERROR
        if linkAddress == "":
            self.addActionError(u"请输入链接 ")
            return self.ERROR
        if self.siteLinks == None:
            self.siteLinks = SiteLinks()
            self.siteLinks.setObjectId(self.subject.subjectId)
            self.siteLinks.setObjectType("subject")
        self.siteLinks.setLinkHref(linkAddress)
        self.siteLinks.setLinkTitle(linkName)
        self.siteLinksService.saveOrUpdate(self.siteLinks)
        response.sendRedirect("subjectlinks.py?id=" + str(self.subject.subjectId))