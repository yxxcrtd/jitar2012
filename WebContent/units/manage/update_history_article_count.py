from unit_page import *
from cn.edustar.jitar.util import FileCache

class update_history_article_count(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        
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
        
        webSiteManageService = __spring__.getBean("webSiteManageService")
        backYearList = webSiteManageService.getBackYearList("article")
        if backYearList == None or len(backYearList) < 1:
            self.addActionMessage(u"没有历史文章可管理。")
            return self.SUCCESS
        for backYear in backYearList:
            print backYear.backYear
        
        