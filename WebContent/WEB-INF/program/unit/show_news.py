from unit_page import *
from cn.edustar.jitar.pojos import UnitNews

class show_news(UnitBasePage):
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
        unitNewsId = self.params.safeGetIntParam("unitNewsId")
        type = self.params.safeGetStringParam("type")
        unitService = __spring__.getBean("unitService")
        unitNews = unitService.getUnitNewsById(unitNewsId)
        if unitNews == None:
            self.addActionError(u"该条信息不存在，有可能已经被删除。")
            return self.ERROR
        
        request.setAttribute("unit",self.unit)
        request.setAttribute("unitNews",unitNews)
        
        templateName = "template1"
        if self.unit.templateName != None:
            templateName = self.unit.templateName
            
        if type == "picture":
            request.setAttribute("type","picture")
            
        return "/WEB-INF/unitspage/" + templateName + "/show_news.ftl"