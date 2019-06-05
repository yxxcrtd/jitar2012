from cn.edustar.jitar.service import CacheService
from java.io import File
from cn.edustar.jitar.util import ParamUtil
from org.apache.commons.io import FileUtils
from cn.edustar.jitar.service import ConfigService

class clearall_cache:
    def execute(self):
        cache = __jitar__.cacheProvider.getCache('main')
        cache.clear()
        cache_key_list = cache.getAllKeys()
        if cache_key_list != None:                
            for key in cache_key_list:
                cache.remove(key)
        
        cacheService = __spring__.getBean("cacheService")
        
        cacheService.remove("new_user_list")
        cacheService.remove("rcmd_wr_list")
        cacheService.remove("hot_wr_list")
        cacheService.remove("rcmd_group_list")
        cacheService.remove("new_group_list")
        cacheService.remove("best_group_list")
        cacheService.remove("famous_teachers")
        cacheService.remove("expert_list")
        cacheService.remove("teacher_star")
        cacheService.remove("instructor_list")
        cacheService.remove("new_video_list")
        cacheService.remove("hot_video_list")
        cacheService.remove("school_list")
        cacheService.remove("course_list")
        cacheService.remove("special_subject_list")
        cacheService.remove("hot_photo_list")
        cacheService.remove("new_photo_list")
        cacheService.remove("site_stat")
        cacheService.remove("teacher_show")
        cacheService.remove("jitar_actions")
        cacheService.remove("famous_article_list")
        cacheService.remove("hot_article_list")
        cacheService.remove("newest_article_list")
        cacheService.remove("hot_resource_list")
        cacheService.remove("new_resource_list")
        cacheService.remove("site_placard_list")
        cacheService.remove("jitar_news")
        cacheService.remove("pic_news")
        cacheService.remove("show_custorm_part")
        
        cacheService.remove("all_subject")
        cacheService.remove("all_grade")
        cacheService.remove("all_meta_subject")
        
        cache = __jitar__.cacheProvider.getCache('siteTheme')
        cache.remove("siteTheme")
        
        cache = __jitar__.cacheProvider.getCache('user')
        cache.clear()
        
        cache = __jitar__.cacheProvider.getCache('group')
        cache.clear()
        
        cache = __jitar__.cacheProvider.getCache('page')
        cache.clear()
        
        cache = __jitar__.cacheProvider.getCache('category')
        cache.clear()
        
        cache = __jitar__.cacheProvider.getCache('subject')
        cache.clear()
        
        cache = __jitar__.cacheProvider.getCache('unit')
        cache.clear()
        
        cache = __jitar__.cacheProvider.getCache('defaultCache')
        cache.clear()
        
        cache = __jitar__.cacheProvider.getCache('rootUnit')
        cache.remove("rootUnit")
        
        
        subjectService = __jitar__.subjectService
        subjectService.clearCacheData()
        
        servlet_ctxt = request.getServletContext()
        servlet_ctxt.removeAttribute("metaGrade")
        servlet_ctxt.removeAttribute("meta_Grade")
        servlet_ctxt.removeAttribute("SubjectNav")
        servlet_ctxt.removeAttribute(ConfigService.CONFIG_KEY_NAME)
        
        siteNavigationService = __spring__.getBean("siteNavigationService")
        siteNavigationService.renderSiteNavition()
        
        self.params = ParamUtil(request)
        
        cachetype = self.params.safeGetStringParam("cachetype")
        if cachetype == "index":                
            strFile = request.getServletContext().getRealPath("/")
            file = File(strFile + "index.html")
            if file.exists() and file.isFile():
                file.delete()
            file = None
        elif cachetype == "user":
            strFile = request.getServletContext().getRealPath("/")
            strFile = strFile + "html" + File.separator + "user" + File.separator
            file = File(strFile)
            if file.exists():
                self.deleteDirectory(strFile)
            file = None
        elif cachetype == "unit":
            strFile = request.getServletContext().getRealPath("/")
            strFile = strFile + "html" + File.separator + "unit" + File.separator
            file = File(strFile)
            if file.exists():
                self.deleteDirectory(strFile)
            file = None
        response.contentType = "text/html; charset=UTF-8"
        return "/WEB-INF/ftl/admin/clear_cache.ftl"
    
    def deleteDirectory(self, sPath):
        # 如果sPath不以文件分隔符结尾，自动添加文件分隔符  
        dirFile = File(sPath)
        # 如果dir对应的文件不存在，或者不是一个目录，则退出  
        if dirFile.exists() == False or dirFile.isDirectory() == False:
            return
        
        FileUtils.deleteQuietly(dirFile)
        return
        
        """
        换一种新的方法，以下代码不用了
        """
        # 删除文件夹下的所有文件(包括子目录)
        files = dirFile.listFiles()
        if files == None or len(files) == 0:return
        for f in files:
            # 删除子文件
            if f.isFile():
                f.delete()
            else:
                self.deleteDirectory(f.getAbsolutePath())
        #/删除当前目录  
        dirFile.delete()
        dirFile = None