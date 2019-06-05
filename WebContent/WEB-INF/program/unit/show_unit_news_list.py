from unit_page import *
from unit_other_query import *

class show_unit_news_list(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        
    def execute(self):
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        
        if self.unit.delState == True:
            self.addActionError(u"您所访问的机构已经被删除！")
            return self.ERROR
        
        type = self.params.safeGetIntParam("type")
        if type == 3:
            qry = UnitPrepareCourseQuery("pc.prepareCourseId, pc.title, pc.startDate, pc.endDate, u.userId")
        elif type == 4:
            qry = UnitGroupQuery("ug.groupName, ug.groupTitle")        
        else:
            qry = UnitNewsNoticeQuery(" un.unitNewsId, un.title, un.createDate")            
            qry.itemType = type
        qry.unitId = self.unit.unitId
        pager = self.params.createPager()
        if type == 1:
            pager.itemName = u"公告"
        elif type == 2:
            pager.itemName = u"动态"
        elif type == 3:
            pager.itemName = u"机构集备"
        elif type == 4:
            pager.itemName = u"机构协作组"
        elif type == 0:
            pager.itemName = u"图片新闻"
        else:
            pager.itemName = u"无效的标识"

        pager.itemUnit = u"个"
        pager.pageSize = 20
        pager.totalRows = qry.count()
        news_list = qry.query_map(pager)

        request.setAttribute("unit", self.unit)
        request.setAttribute("pager", pager)
        request.setAttribute("type", type)
        request.setAttribute("news_list", news_list)
        templateName = "template1"
        if self.unit.templateName != None:
            templateName = self.unit.templateName
        return "/WEB-INF/unitspage/" + templateName + "/show_unit_news_list.ftl"