from unit_page import UnitBasePage
from cn.edustar.jitar.util import FileCache

class unit_custorm(UnitBasePage):
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
            guids = self.params.safeGetIntValues("guid")
            cmd = self.params.safeGetStringParam("cmd")
            for g in guids:
                wb = self.unitService.getUnitWebpartById(g)
                if wb != None:
                    if cmd == "delete":
                        self.unitService.deleteUnitWebpart(wb)
                    if cmd == "visible":
                        wb.setVisible(True)
                        self.unitService.saveOrUpdateUnitWebpart(wb)
                    if cmd == "hidden":
                        wb.setVisible(False)
                        self.unitService.saveOrUpdateUnitWebpart(wb)
            
        webpart_list = self.unitService.getCustormUnitWebpart(self.unit.unitId)
        request.setAttribute("webpart_list", webpart_list)
        request.setAttribute("unit", self.unit)
        fc = FileCache()
        fc.deleteUnitCacheFile(self.unit.unitName)
        fc = None
        return "/WEB-INF/unitsmanage/unit_custorm.ftl"