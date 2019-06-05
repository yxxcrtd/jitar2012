from unit_page import UnitBasePage
from cn.edustar.jitar.util import FileCache

class unit_links(UnitBasePage):
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
        
        if request.getMethod() == "POST":
            self.clear_cache()
            guid = self.params.safeGetIntValues("guid")
            for d in guid:
                self.unitService.deleteUnitLinksByLinkId(d)
                
            fc = FileCache()
            fc.deleteUnitCacheFile(self.unit.unitName)
            fc = None
            
        links = self.unitService.getUnitLinksByUnitId(self.unit.unitId)
        if links != None:
            request.setAttribute("links", links)
        request.setAttribute("unit", self.unit)
        
        return "/WEB-INF/unitsmanage/unit_links.ftl"