from base_unit import UnitBasePage

class get_unit(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        
    def execute(self):        
        organizationUnitService = __spring__.getBean("organizationUnitService")
        if organizationUnitService == None:
            self.log.error(u"无法加载组织机构服务，请检查 applicationContext.xml 配置文件！")
            return ""
        
        unitpath = self.params.safeGetStringParam("unitpath")
        if unitpath == "":unitpath = "/0"
        
        unit_list = organizationUnitService.getChildOrganizationUnits(unitpath)
        request.setAttribute("unit_list", unit_list)
        return "/WEB-INF/ftl/get_unit.ftl"