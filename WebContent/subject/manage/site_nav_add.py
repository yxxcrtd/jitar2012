from subject_page import *
from base_action import ActionResult
from cn.edustar.jitar.pojos import SiteNav
from cn.edustar.jitar.util import ParamUtil

class site_nav_add(BaseSubject):
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
        
        siteNavId = self.params.safeGetIntParam("siteNavId")
        siteNav = self.siteNavService.getSiteNavById(siteNavId)        
        if request.getMethod() == "POST":
            siteName = self.params.safeGetStringParam("siteName")
            siteUrl = self.params.safeGetStringParam("siteUrl")
            #currentNav = self.params.safeGetStringParam("crtNav")
            siteShow = self.params.safeGetIntParam("siteShow")
            #siteType = self.params.safeGetIntParam("siteType")
            siteOrder = self.params.safeGetIntParam("siteOrder")
            if siteName == "":
                self.addActionError(u"请输入导航名称.")
                return ActionResult.ERROR
            
            if siteUrl == "" and siteNav != None and siteNav.isExternalLink == True:
                self.addActionError(u"请输入导航地址.")
                return ActionResult.ERROR            
            if siteNav == None:
                siteNav = SiteNav()
                siteNav.setIsExternalLink(True)
                siteNav.setOwnerType(2)
                siteNav.setOwnerId(self.subject.subjectId)
            if siteNav.isExternalLink == True:
                siteNav.setSiteNavUrl(siteUrl)                
            siteNav.setSiteNavName(siteName)            
            if siteShow == 1:
                siteNav.setSiteNavIsShow(True)
            else:
                siteNav.setSiteNavIsShow(False)

            siteNav.setSiteNavItemOrder(siteOrder)
            self.siteNavService.saveOrUpdateSiteNav(siteNav)
            cache = __jitar__.cacheProvider.getCache('sitenav')
            if cache != None:                    
                cache_list = cache.getAllKeys()
                cache_key = "subject_nav_" + str(self.subject.subjectId)
                for c in cache_list:
                    if c == cache_key:
                        cache.remove(c)
            self.addActionMessage(u"您成功编辑了一个自定义导航： " + siteName + u"。")
            self.addActionLink(u"返回", "subject_sitenav.py?id=" + str(self.subject.subjectId))
            return ActionResult.SUCCESS
        
        if siteNav != None:
            request.setAttribute("siteNav", siteNav)            
        return "/WEB-INF/subjectmanage/site_nav_add.ftl"
