from unit_page import UnitBasePage

class index(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        if self.canManege() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
        
        if self.unit.unitPathInfo == None or self.unit.unitPathInfo == "":
            self.addActionError(u"当前机构没有 unitPathInfo 属性 。")
            return self.ERROR
        
        request.setAttribute("unit", self.unit)
        cmd = self.params.safeGetStringParam("cmd")
                
        if self.isUnitAdmin() == True:
            request.setAttribute("unitAdmin", "1")
        else:
            request.setAttribute("unitAdmin", "0")
        
        if self.isUserAdmin() == True:
            request.setAttribute("unitUserAdmin", "1")
        else:
            request.setAttribute("unitUserAdmin", "0")
            
        if self.isContentAdmin() == True:
            request.setAttribute("unitContentAdmin", "1")
        else:
            request.setAttribute("unitContentAdmin", "0")
                
        if cmd == "menu":
            webSiteManageService = __spring__.getBean("webSiteManageService")
            bklist = webSiteManageService.getBackYearList("article")
            if bklist != None and len(bklist) > 0:
                request.setAttribute("bklist", bklist)
            
            configUnitLevel = self.unitService.getConfigUnitLevel()
            if configUnitLevel > 0:
                unitLevel = len(self.unit.unitPathInfo.split("/")) - 2
                if unitLevel < configUnitLevel:
                    request.setAttribute("canAddChildUnit", "1")
            else:
                request.setAttribute("canAddChildUnit", "1")
            return "/WEB-INF/unitsmanage/menu.ftl"
        elif cmd == "main":
            request.setAttribute("unitOnlyChildUserCount", self.unitService.getOnlyChildUserCount(self.unit))
            request.setAttribute("unitOnlyChildArticleCount", self.unitService.getOnlyChildArticleCount(self.unit))
            request.setAttribute("unitOnlyChildResourceCount", self.unitService.getOnlyChildResourceCount(self.unit))
            request.setAttribute("unitOnlyChildPhotoCount", self.unitService.getOnlyChildPhotoCount(self.unit))
            request.setAttribute("unitOnlyChildVideoCount", self.unitService.getOnlyChildVideoCount(self.unit))
            request.setAttribute("unitDayCount", self.unitService.queryUnitDayCount(self.unit.unitId))            
            return "/WEB-INF/unitsmanage/main.ftl"
        elif cmd == "head":
            request.setAttribute("ru", request.getScheme() + "://" + request.getServerName() + ":" + str(request.getServerPort()) + request.getContextPath())
            return "/WEB-INF/unitsmanage/head.ftl"
        else:
            return "/WEB-INF/unitsmanage/index.ftl"