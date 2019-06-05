from cn.edustar.jitar.pojos import SiteNav
from cn.edustar.jitar.jython import JythonBaseAction
from base_action import ActionResult

class init_unit_nav(JythonBaseAction):
    
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"没有管理权限.")
            return ActionResult.ERROR
        
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"只有 admin 用户才可进行配置.")
            return ActionResult.ERROR
        
        unitService = __spring__.getBean("unitService")
        siteNavService = __spring__.getBean("siteNavService")
        #先删除已有的系统导航项目
        siteNavService.deleteSiteNavOfOwnerType(1, None, False)
        
        unitlist = unitService.getAllUnitOrChildUnitList(None,[False])
        siteNameArray = [u"总站首页", u"机构首页", u"机构文章", u"机构资源", u"机构图片", u"机构视频", u"机构工作室"];
        siteUrlArray = ["py/subjectHome.action", "", "article/", "resource/", "photo/", "video/", "blog/"]
        siteHightlightArray = ["index", "unit", "unit_article", "unit_resource", "unit_photo", "unit_video", "unit_user"]
       
        for unit in unitlist:
            for i in range(0, len(siteNameArray)):
                #先判断是否存在
                siteNav = siteNavService.getSiteNavByName(SiteNav.SITENAV_OWNERTYPE_UNIT, unit.unitId, siteNameArray[i])
                if siteNav == None:
                    siteNav = SiteNav()
                    siteNav.setSiteNavName(siteNameArray[i])
                    siteNav.setIsExternalLink(False)
                    siteNav.setSiteNavUrl(siteUrlArray[i])
                    siteNav.setSiteNavIsShow(True)
                    siteNav.setSiteNavItemOrder(i)
                    siteNav.setCurrentNav(siteHightlightArray[i])
                    siteNav.setOwnerType(SiteNav.SITENAV_OWNERTYPE_UNIT)
                    siteNav.setOwnerId(unit.unitId)
                    siteNavService.saveOrUpdateSiteNav(siteNav)
        response.getWriter().println(u"设置机构导航执行完毕，本页面执行一次就可以了。")      
