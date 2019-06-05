from unit_page import *
from cn.edustar.jitar.util import FileCache

class unit_news(UnitBasePage):
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
        
        if request.getMethod() == "POST":
            self.clear_cache()
            guids = self.params.safeGetIntValues("guid")
            for g in guids:
                unitNews = self.unitService.getUnitNewsById(g)
                if unitNews != None:
                    self.unitService.deleteUnitNews(unitNews)
            link = "unit_news.py?type=1&unitId=" + str(self.unit.unitId)
            self.addActionLink(u"返回", link)
            return self.SUCCESS            
        
        qry = UnitNewsNoticeQuery(""" un.unitNewsId, un.title, un.createDate,un.picture, un.itemType  """)
        qry.unitId = self.unit.unitId
        pager = self.params.createPager()
        pager.itemName = u"新闻"
        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        unit_news_list = qry.query_map(pager)
        request.setAttribute("unit_news_list", unit_news_list)
        request.setAttribute("pager", pager)
        request.setAttribute("unit", self.unit)
        fc = FileCache()
        fc.deleteUnitCacheFile(self.unit.unitName)
        fc = None
        return "/WEB-INF/unitsmanage/unit_news.ftl"