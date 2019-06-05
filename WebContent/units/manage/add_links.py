from unit_page import UnitBasePage
from cn.edustar.jitar.pojos import UnitLinks

class add_links(UnitBasePage):
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
        
             
        linkId = self.params.safeGetIntParam("linkId")
        if linkId == 0: 
            unitLinks = UnitLinks()
        else:
            unitLinks = self.unitService.getUnitLinksById(linkId)
            if unitLinks == None:
                self.addActionError(u"无法加载对象，更新将终止。")
                return self.ERROR
            else:
                request.setAttribute("unitLinks", unitLinks)

        if request.getMethod() == "POST":
            self.clear_cache()
            linkName = self.params.safeGetStringParam("linkName")
            linkAddress = self.params.safeGetStringParam("linkAddress")
            linkIcon = self.params.safeGetStringParam("linkIcon")
            
            unitLinks.setUnitId(self.unit.unitId)
            unitLinks.setLinkName(linkName)
            unitLinks.setLinkAddress(linkAddress)
            if linkIcon == "":
                unitLinks.setLinkIcon(None)
            else:
                unitLinks.setLinkIcon(linkIcon)
            
            self.unitService.saveOrUpdateUnitLinks(unitLinks)
            response.sendRedirect("unit_links.py?unitId=" + str(self.unit.unitId))
        
        links = self.unitService.getUnitLinksByUnitId(self.unit.unitId)
        if links != None:
            request.setAttribute("links", links)
        request.setAttribute("unit", self.unit)
        
        return "/WEB-INF/unitsmanage/add_links.ftl"
