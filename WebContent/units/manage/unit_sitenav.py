from unit_page import *
from base_action import ActionResult
from cn.edustar.jitar.pojos import SiteIndexPart
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.util import FileCache

class unit_sitenav(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.params = ParamUtil(request)
        self.siteNavService = __spring__.getBean("siteNavService")
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        if self.isUnitAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
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
        request.setAttribute("unit", self.unit)
        fc = FileCache()
        fc.deleteUnitCacheFile(self.unit.unitName)
        fc = None
        cache = __jitar__.cacheProvider.getCache('sitenav')
        if cache != None:                    
            cache_list = cache.getAllKeys()
            cache_key = "unit_nav_" + str(self.unit.unitId)
            for c in cache_list:
                if c == cache_key:
                    cache.remove(c)
        return "/WEB-INF/unitsmanage/unit_sitenav.ftl"
    
    
    def get_site_nav_list(self):
        siteNavItemList = self.siteNavService.getAllSiteNav(True, 1, self.unit.unitId)
        request.setAttribute("siteNavItemList", siteNavItemList)
