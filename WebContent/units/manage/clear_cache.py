from unit_page import *
from java.io import File
from cn.edustar.jitar.util import ParamUtil

class clear_cache(UnitBasePage):
    def __init__(self):
        UnitBasePage.__init__(self)
        
    def execute(self):
        if self.loginUser == None:
            return self.LOGIN
        self.unit = self.getUnit()
        if self.unit == None:
            self.addActionError(u"您所访问的机构不存在！")
            return self.ERROR         
        if self.isContentAdmin() == False:
            self.addActionError(u"你没有管理的权限。")
            return self.ERROR
        
        cache = __jitar__.cacheProvider.getCache('unit')
        cache_list = cache.getAllKeys()
        cache_key_head = "unit" + str(self.unit.unitId)
        for c in cache_list:
            if c.split("_")[0] == cache_key_head:
                cache.remove(c)
        
        #删除单位静态文件
        strFile = request.getServletContext().getRealPath("/")
        strFile = strFile + "html" + File.separator + "unit" + File.separator + self.unit.unitName + File.separator
        file = File(strFile)
        if file.exists():
            self.deleteDirectory(strFile)
        file = None
        return self.SUCCESS
    
    def deleteDirectory(self, sPath):
        # 如果sPath不以文件分隔符结尾，自动添加文件分隔符  
        dirFile = File(sPath)
        # 如果dir对应的文件不存在，或者不是一个目录，则退出  
        if dirFile.exists() == False or dirFile.isDirectory() == False:
            return
        # 删除文件夹下的所有文件(包括子目录)
        files = dirFile.listFiles()
        for f in files:
            # 删除子文件
            if f.isFile():
                f.delete()
            else:
                self.deleteDirectory(f.getAbsolutePath())
        #/删除当前目录  
        dirFile.delete()
        dirFile = None