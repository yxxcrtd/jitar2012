from base_action import *
from base_action import ActionResult
from cn.edustar.jitar.pojos import User
from cn.edustar.jitar.pojos import SiteNav
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import ParamUtil

class site_nav(BaseAdminAction):
    # 定义要返回的页面常量.
    ADMIN_MAIN = "/WEB-INF/ftl/admin/main.ftl"
    ERROR = "/WEB-INF/ftl/Error.ftl"    
    SUCCESS = "/WEB-INF/ftl/success.ftl"
    
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.siteNavService = __spring__.getBean("siteNavService")
        
    def execute(self):
        user = self.loginUser
        if user == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(user) == False:
            self.addActionError(u"没有管理权限.")
            return ActionResult.ERROR
        
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"只有超级管理员用户才可进行配置.")
            return ActionResult.ERROR
        
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
        self.siteNavService.setNewSiteNav(0, 0)
        return "/WEB-INF/ftl/admin/site_nav.ftl"
    
    def get_site_nav_list(self):
        siteNavItemList = self.siteNavService.getAllSiteNav(True, 0, 0)
        request.setAttribute("siteNavItemList", siteNavItemList)
