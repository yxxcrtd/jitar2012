from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command
from base_action import ActionResult

class count_history_article(BaseAdminAction, ActionResult):
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"没有管理站点配置的权限，只有超级管理员才能进行管理。")
            return ActionResult.ERROR
        params = ParamUtil(request)
        webSiteManageService = __spring__.getBean("webSiteManageService")
        if params.existParam("update"):
            articleCount = 0
            backYearList = webSiteManageService.getBackYearList("article")
            for bYear in backYearList:
                articleCount = articleCount + bYear.backYearCount
            #site_stat = __jitar__.statService.getSiteStat()
            timerCountService = __spring__.getBean("timerCountService")
            siteCounter = timerCountService.getTimerCountById(1)
            siteCounter.historyArticleCount = articleCount
            timerCountService.saveOrUpdateTimerCount(siteCounter)
            #site_stat.setHistoryArticleCount(articleCount)
            #__jitar__.statService.updateSiteStat(site_stat)
            return ActionResult.SUCCESS
        else:
            backYearList = webSiteManageService.getBackYearList("article")
            for bYear in backYearList:
                #重新统计
                webSiteManageService.getYearArticleCount(bYear.backYear)
            self.addActionLink(u"点击进行更新","count_history_article.py?update=1","_self")
            return ActionResult.SUCCESS