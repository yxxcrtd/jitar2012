from base_action import *
from cn.edustar.jitar.pojos import User
from cn.edustar.jitar.pojos import SiteNav
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import ParamUtil

class site_nav_add(BaseAdminAction):
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
            self.addActionError(u"只有 超级管理员 用户才可进行配置.")
            return ActionResult.ERROR
        siteNavId = self.params.safeGetIntParam("siteNavId")
        siteNav = self.siteNavService.getSiteNavById(siteNavId)
        #print "siteNavUrl = ", siteNav.isExternalLink
        
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
            if siteNav.isExternalLink == True:
                siteNav.setSiteNavUrl(siteUrl)                
            siteNav.setSiteNavName(siteName)            
            if siteShow == 1:
                siteNav.setSiteNavIsShow(True)
            else:
                siteNav.setSiteNavIsShow(False)            

            siteNav.setSiteNavItemOrder(siteOrder)
            self.siteNavService.saveOrUpdateSiteNav(siteNav)
            self.siteNavService.setNewSiteNav(0, 0)
            return ActionResult.SUCCESS
        
        if siteNav != None:
            request.setAttribute("siteNav", siteNav)            
        return "/WEB-INF/ftl/admin/site_nav_add.ftl"
