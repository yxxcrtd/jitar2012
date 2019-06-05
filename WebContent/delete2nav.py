from org.apache.commons.io import FileUtils
from java.io import File

class delete2nav:
    def __init__(self):
        self.siteNavArray = []
    def execute(self):
        unitService = __jitar__.unitService
        siteNavService = __spring__.getBean("siteNavService")
        unit_list = unitService.getAllUnitOrChildUnitList(None,[False])
        print unit_list
        for u in unit_list:
            self.siteNavArray = []
            unitId = u.unitId
            site_list = siteNavService.getAllSiteNav(True, 1, unitId)
            for sn in site_list:
                if self.checkExists(sn) == True:
                    siteNavService.deleteSiteNav(sn)
                else:
                    self.siteNavArray.append(sn)
                    
                    
        strFile = request.getServletContext().getRealPath("/")
        strFile = strFile + "html" + File.separator + "unit" + File.separator
        file = File(strFile)
        if file.exists():
            FileUtils.deleteDirectory(file)           
        response.writer.write(u"代码已经执行完毕。")
            
    def checkExists(self, siteNav):
        for s in self.siteNavArray:
            if siteNav.siteNavName == s.siteNavName:
                return True       
        return False
            
            
            
        