from unit_page import UnitBasePage
from cn.edustar.jitar.pojos import UnitWebpart
from cn.edustar.jitar.pojos import ContentSpace

class add_contentspace_module(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.unitService = __spring__.getBean("unitService")
        self.contentSpaceService = __spring__.getBean("contentSpaceService")
        
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
        
                
        
        webpartId = self.params.safeGetIntParam("webpartId")
        
        if webpartId == 0:
            unitWebpart = UnitWebpart()
            unitWebpart.setModuleName("")
            unitWebpart.setSystemModule(False)
            unitWebpart.setUnitId(self.unit.unitId)
            unitWebpart.setWebpartZone(1)
            unitWebpart.setRowIndex(0)
            unitWebpart.setContent("")
            unitWebpart.setVisible(True)
            unitWebpart.setCateId(0)
            unitWebpart.setShowCount(6)
            unitWebpart.setShowType(1)
            unitWebpart.setPartType(2)
        else:
            unitWebpart = self.unitService.getUnitWebpartById(webpartId)
            
        if request.getMethod() == "POST":
            moduleName = self.params.safeGetStringParam("moduleName")
            webpartZone = self.params.safeGetIntParam("webpartZone")
            sysCateId = self.params.safeGetIntParam("sysCateId")
            showCount = self.params.safeGetIntParam("showCount")
            showType = self.params.safeGetIntParam("showType")
            if showCount == 0:
                showCount = 8
            if "" == moduleName:
                self.addActionError(u"请输入模块名称。")
                return self.ERROR
            
            if 0 == webpartZone:
                self.addActionError(u"请选择模块位置。")
                return self.ERROR           
            
            if webpartId == 0:                
                unitWebpart.setUnitId(self.unit.unitId)
                unitWebpart.setRowIndex(0)
                unitWebpart.setSystemModule(False)
                unitWebpart.setVisible(True)
                unitWebpart.setPartType(2)
            unitWebpart.setModuleName(moduleName)
            unitWebpart.setDisplayName(moduleName)
            unitWebpart.setWebpartZone(webpartZone)
            unitWebpart.setContent(None)
            unitWebpart.setCateId(sysCateId)
            unitWebpart.setShowCount(showCount)
            unitWebpart.setShowType(showType)
            
            self.unitService.saveOrUpdateUnitWebpart(unitWebpart)
            response.sendRedirect("unit_custorm.py?unitId=" + str(self.unit.unitId))
                
        request.setAttribute("unitWebpart", unitWebpart)
        request.setAttribute("unit", self.unit)
        self.get_contentspace_list()
        return "/WEB-INF/unitsmanage/add_contentspace_module.ftl"
    
    def get_contentspace_list(self):
        contentSpaceList = self.contentSpaceService.getAllContentSpaceList(ContentSpace.CONTENTSPACE_OWNERTYPE_UNIT, self.unit.unitId)
        request.setAttribute("catelist", contentSpaceList)
