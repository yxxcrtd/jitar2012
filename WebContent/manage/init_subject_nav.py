from cn.edustar.jitar.pojos import SiteNav
from cn.edustar.jitar.jython import JythonBaseAction
from base_action import ActionResult

class init_subject_nav(JythonBaseAction):
    
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
            
        subjectService = __spring__.getBean("subjectService")
        siteNavService = __spring__.getBean("siteNavService")
        #先删除已有的系统导航项目
        siteNavService.deleteSiteNavOfOwnerType(2, None, False)
        
        subjectlist = subjectService.getSubjectList()
        siteNameArray = [u"总站首页", u"学科首页", u"文章", u"资源", u"工作室", u"协作组", u"集备", u"视频", u"活动", u"专题"]
        siteUrlArray = ["py/subjectHome.action", "", "article/", "resource/", "blog/", "groups/", "preparecourse/", "video/", "activity/", "specialsubject/"]
        siteHightlightArray = ["index", "subject", "article", "resource", "blog", "groups", "preparecourse", "video", "activity", "specialsubject"]
        
        for subject in subjectlist:
            for i in range(0, len(siteNameArray)):
                siteNav = SiteNav()
                siteNav.setSiteNavName(siteNameArray[i])
                siteNav.setIsExternalLink(False)
                siteNav.setSiteNavUrl(siteUrlArray[i])
                siteNav.setSiteNavIsShow(True)
                siteNav.setSiteNavItemOrder(i)
                siteNav.setCurrentNav(siteHightlightArray[i])
                siteNav.setOwnerType(2)
                siteNav.setOwnerId(subject.subjectId)
                siteNavService.saveOrUpdateSiteNav(siteNav)
        response.getWriter().println(u"设置学科导航执行完毕，本页面执行一次就可以了。")      
