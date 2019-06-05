class units:
    def __init__(self):
        self.unitService = __spring__.getBean("unitService")
        self.unit_main_url = "transfer.py?unitName="
        
    def execute(self):        
        request.setAttribute("head_nav", "units")
        
        # 显示单位，至少 ConfigUnitLevel=2
        ConfigUnitLevel = self.unitService.getConfigUnitLevel()
        if ConfigUnitLevel == 1:
            response.sendRedirect("index.py")
            return
        elif ConfigUnitLevel == 2:
            rootUnit = self.unitService.getRootUnit()
            if rootUnit == None:
                pid = 0
            else:
                pid = rootUnit.unitId
            childUnitList = self.unitService.getChildUnitListByParenId(pid)
            request.setAttribute("rootUnit", rootUnit)
            request.setAttribute("childUnitList", childUnitList)
            return "/WEB-INF/unitspage/site_units1.ftl"
        elif ConfigUnitLevel == 3:
            rootUnit = self.unitService.getRootUnit()
            if rootUnit == None:
                rootId = 0
            else:
                rootId = rootUnit.unitId
            childUnitList = self.unitService.getChildUnitListByParenId(rootId)
            parentUnitArray = []
            for cunit in childUnitList:
                ccunitList = self.unitService.getChildUnitListByParenId(cunit.unitId)
                childUnit = {"p":cunit,"clist":ccunitList}
                parentUnitArray.append(childUnit)
            request.setAttribute("parentUnitArray", parentUnitArray)
            return "/WEB-INF/unitspage/site_units2.ftl"
        else:
            self.get_unit_tree()
            return "/WEB-INF/unitspage/site_units3.ftl"
    
    def get_unit_tree(self):
        cache = __jitar__.cacheProvider.getCache('main')
        cache_key = "html_unit"
        html = None#cache.get(cache_key)
        if html == None:             
            u_list = self.unitService.getChildUnitListByParenId(0)
            if len(u_list) < 1:
                rootUnit = None
            else:
                rootUnit = u_list[0]
            request.setAttribute("rootUnit",rootUnit)
            if rootUnit == None:
                request.setAttribute("html", "")
                return
            html = "d.add(" + str(rootUnit.unitId) + ",-1,'<b>" + rootUnit.unitTitle + " (" + rootUnit.siteTitle + ")</b>','" + self.unit_main_url + str(rootUnit.unitName) + "');"
            html += self.getChildUnit(rootUnit)
            cache.put(cache_key, html)
        #print html
        request.setAttribute("html", html)

    def getChildUnit(self, parentUnit):
        s = "";
        unitList = self.unitService.getAllUnitOrChildUnitList(parentUnit)
        if unitList != None:
            for unit in unitList:
                s += "d.add(" + str(unit.unitId) + "," + str(parentUnit.unitId) + ",'" + unit.unitTitle + " (" + unit.siteTitle + ")','" + self.unit_main_url + str(unit.unitName) + "');\r\n"
                s += self.getChildUnit(unit)
        return s