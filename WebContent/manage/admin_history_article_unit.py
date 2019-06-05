from base_action import *
class admin_history_article_unit(ActionExecutor):        
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        
        webSiteManageService = __spring__.getBean("webSiteManageService")
        backYearList = webSiteManageService.getBackYearList("article")
        if len(backYearList) < 1:
            self.addActionMessage(u"没有历史记录可以统计。")
            return ActionResult.SUCCESS
        unitService = __jitar__.unitService
        unitList = unitService.getAllUnitOrChildUnitList(None,[False])
        for unit in unitList:
            articleCount = 0
            for bYear in backYearList:
                print "bYear.backYear="+str(bYear.backYear)
                articleCount = articleCount + webSiteManageService.getUnitYearArticleCount(bYear.backYear, unit.unitId)
            unit.setHistoryArticleCount(articleCount)
            unitService.saveOrUpdateUnit(unit)
        self.addActionMessage(u"所有机构历史文章统计完毕。")
        return ActionResult.SUCCESS
        
