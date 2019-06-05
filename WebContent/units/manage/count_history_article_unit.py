from unit_page import UnitBasePage

class count_history_article_unit(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.unitService = __spring__.getBean("unitService")
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        if self.unitService == None:
            self.addActionError(u"无法加载组织机构服务，请检查 applicationContext.xml 配置文件，是否缺少 unitService 节点。")
            return self.ERROR   
        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR        
        
        if self.isUnitAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
        
        webSiteManageService = __spring__.getBean("webSiteManageService")
        backYearList = webSiteManageService.getBackYearList("article")
        articleCount = 0
        for bYear in backYearList:
            articleCount = articleCount + webSiteManageService.getUnitYearArticleCount(bYear.backYear,self.unit.unitId)
        self.unit.setHistoryArticleCount(articleCount)
        self.unitService.saveOrUpdateUnit(self.unit)
        unitDayCount = self.unitService.queryUnitDayCount(self.unit.unitId)
        if unitDayCount != None:
            unitDayCount.setHistoryArticleCount(articleCount)
            self.unitService.saveOrUpdateUnitDayCount(unitDayCount)
        self.addActionMessage(u"本机构历史文章数：" + str(articleCount))
        #清空机构缓存
        htmlGeneratorService = __spring__.getBean("htmlGeneratorService")
        htmlGeneratorService.UnitIndex(self.unit)
        return self.SUCCESS       
