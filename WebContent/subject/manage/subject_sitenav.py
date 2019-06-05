from subject_page import *
from base_action import ActionResult
from cn.edustar.jitar.pojos import SiteIndexPart
from cn.edustar.jitar.util import ParamUtil

class subject_sitenav(BaseSubject):
    def __init__(self):
        BaseSubject.__init__(self)
        self.params = ParamUtil(request)
        self.siteNavService = __spring__.getBean("siteNavService")
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN        
        if self.isAdmin() == False:
            self.addActionError(u"您你没有管理的权限！")
            return self.ERROR
        
        if request.getMethod() == 'POST':
            cmd = self.params.safeGetStringParam("cmd")
            guids = self.params.safeGetIntValues("guid")
            if cmd == "delete":
                for guid in guids:
                    siteNav = self.siteNavService.getSiteNavById(guid)
                    if siteNav != None:
                        if siteNav.isExternalLink == True:
                            self.siteNavService.deleteSiteNav(siteNav)
            if cmd == "order":
                guids = self.params.safeGetIntValues("nav_id")
                for guid in guids:
                    order = self.params.safeGetIntParam("z_" + str(guid))
                    siteNav = self.siteNavService.getSiteNavById(guid)
                    if siteNav != None:
                        siteNav.setSiteNavItemOrder(order)
                        self.siteNavService.saveOrUpdateSiteNav(siteNav)                
            if cmd == "display":
                siteId = self.params.safeGetIntParam("siteId")
                siteDisplay = self.params.safeGetIntParam("siteDisplay")
                siteNav = self.siteNavService.getSiteNavById(siteId)
                if siteNav != None:
                    if siteDisplay == 1:
                        siteNav.setSiteNavIsShow(True)
                    else:
                        siteNav.setSiteNavIsShow(False)
                    self.siteNavService.saveOrUpdateSiteNav(siteNav)        
        self.get_site_nav_list()
        request.setAttribute("subject", self.subject)
        cache = __jitar__.cacheProvider.getCache('sitenav')
        if cache != None:                    
            cache_list = cache.getAllKeys()
            cache_key = "subject_nav_" + str(self.subject.subjectId)
            for c in cache_list:
                if c == cache_key:
                    cache.remove(c)
        return "/WEB-INF/subjectmanage/subject_sitenav.ftl"
    
    
    def get_site_nav_list(self):
        siteNavItemList = self.siteNavService.getAllSiteNav(True, 2, self.subject.subjectId)
        request.setAttribute("siteNavItemList", siteNavItemList)
