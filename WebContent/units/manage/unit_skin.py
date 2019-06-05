from unit_page import *
from java.io import File
from javax.servlet import ServletContext
from cn.edustar.jitar.util import FileCache

class unit_skin(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        
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
        
        if request.getMethod() == "POST":
            tmpl = self.params.safeGetStringParam("tmpl")
            if tmpl == "":
                self.unit.setThemeName(None)
            else:
                self.unit.setThemeName(tmpl)
            self.unitService.saveOrUpdateUnit(self.unit)
            fc = FileCache()
            fc.deleteUnitCacheFile(self.unit.unitName)
            fc = None
            return self.SUCCESS
        
        # 查找所有样式
        themeFolder = application.getRealPath("/") + "theme" + File.separator + "units" + File.separator        
        file = File(themeFolder)
        if file.exists() == True:
            theme_list = []
            fs = file.list()
            for theme in fs:
                fd = File(themeFolder + theme)
                if fd.isDirectory() == True:
                    theme_list.append(theme)            
            if len(theme_list) > 0:
                request.setAttribute("theme_list", theme_list)
                
        request.setAttribute("unit", self.unit)
        return "/WEB-INF/unitsmanage/unit_skin.ftl"
