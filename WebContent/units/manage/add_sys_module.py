from unit_page import UnitBasePage
from cn.edustar.jitar.pojos import UnitWebpart

class add_sys_module(UnitBasePage):
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
        
        webpartId = self.params.safeGetIntParam("webpartId")
        if webpartId == 0:
            unitWebpart = UnitWebpart()
            unitWebpart.setUnitId(self.unit.unitId)
            unitWebpart.setRowIndex(0)
            unitWebpart.setSystemModule(False)
            unitWebpart.setVisible(True)
            unitWebpart.setShowCount(6)
            unitWebpart.setShowType(1)
            unitWebpart.setPartType(1)
        else:
            unitWebpart = self.unitService.getUnitWebpartById(webpartId)      
            
        if request.getMethod() == "POST":
            moduleName = self.params.safeGetStringParam("moduleName")
            webpartZone = self.params.safeGetIntParam("webpartZone")
            sysCateId = self.params.safeGetIntParam("sysCateId")
            showCount = self.params.safeGetIntParam("showCount")
            if showCount == 0:
                showCount = 6
            if "" == moduleName:
                self.addActionError(u"请输入模块名称。")
                return self.ERROR
            
            if 0 == webpartZone:
                self.addActionError(u"请选择模块位置。")
                return self.ERROR
            if sysCateId == 0:
                self.addActionError(u"请选择一个系统分类。")
                return self.ERROR
            
            unitWebpart.setModuleName(moduleName)
            unitWebpart.setDisplayName(moduleName)
            
            unitWebpart.setWebpartZone(webpartZone)
            unitWebpart.setContent(None)
            unitWebpart.setCateId(sysCateId)
            unitWebpart.setShowCount(showCount)
            self.unitService.saveOrUpdateUnitWebpart(unitWebpart)
            response.sendRedirect("unit_custorm.py?unitId=" + str(self.unit.unitId))
            
        request.setAttribute("unitWebpart", unitWebpart)
        request.setAttribute("unit", self.unit)
        self.get_article_cate_list()
        return "/WEB-INF/unitsmanage/add_sys_module.ftl"
    
    def get_article_cate_list(self):
        article_cates = __jitar__.categoryService.getCategoryTree("default")
        request.setAttribute("article_cates", article_cates)
