from unit_page import UnitBasePage

class clear_sitenav_cache(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        self.unitService = __spring__.getBean("unitService")
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR
        
        if self.isUnitAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
        cache = __jitar__.cacheProvider.getCache('sitenav')
        if cache != None:                    
            cache_list = cache.getAllKeys()
            cache_key = "unit_nav_" + str(self.unit.unitId)
            for c in cache_list:
                if c == cache_key:
                    cache.remove(c)
        response.getWriter().println(u"执行完毕。")
