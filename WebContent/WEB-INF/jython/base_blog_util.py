# -*- coding: utf-8 -*-
from java.io import File
from java.io import FileOutputStream
from java.io import OutputStreamWriter
from java.io import IOException
from cn.edustar.jitar.util import ParamUtil

class FileCacheX:
    def getContentFromCache(self,filePath,cacheTime):
        
        path = self.getClass().getResource("/").getPath()
        
        
        return u"测试" + path
        
