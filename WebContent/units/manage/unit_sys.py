from unit_page import UnitBasePage
from cn.edustar.jitar.util import FileCache

class unit_sys(UnitBasePage):
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
            self.save_module()
        self.list_sysmodule()
        fc = FileCache()
        fc.deleteUnitCacheFile(self.unit.unitName)
        fc = None
        return "/WEB-INF/unitsmanage/unit_sys.ftl"
    
    def list_sysmodule(self):
        webpart_list = self.unitService.getSystemUnitWebpart(self.unit.unitId)
        request.setAttribute("unit", self.unit)
        request.setAttribute("webpart_list", webpart_list)
    
    def save_module(self):
        cmd = self.params.safeGetStringParam("cmd")
        guids = self.params.safeGetIntValues("guid")
        if cmd == "setDisplayName":
            self.setDisplayName()
        else:                
            for g in guids:
                webpart = self.unitService.getUnitWebpartById(g)
                if webpart != None:
                    if cmd == "visible":
                        webpart.setVisible(True)
                    elif cmd == "hidden":
                        webpart.setVisible(False)
                    self.unitService.saveOrUpdateUnitWebpart(webpart)
    def setDisplayName(self):
        unitWebpartId = self.params.safeGetIntValues("unitWebpartId")
        for uwd in unitWebpartId:
            webpart = self.unitService.getUnitWebpartById(uwd)
            if webpart != None:
                displayName = self.params.safeGetStringParam("displayName" + str(uwd))
                if webpart.getDisplayName() != displayName:
                    webpart.setDisplayName(displayName)
                    self.unitService.saveOrUpdateUnitWebpart(webpart)
