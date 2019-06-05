from cn.edustar.jitar.model import Configure, UserMgrClientModel
from cn.edustar.jitar.util import ParamUtil, CommonUtil
from cn.edustar.jitar.data import Command
from cn.edustar.jitar import JitarRequestContext
from java.io import File, FileWriter, FileOutputStream, OutputStreamWriter
from java.util import ArrayList
from java.util import HashMap
from java.lang import System
from org.apache.commons.io import FileUtils

class checksetting:
    def execute(self):
        params = ParamUtil(request)
        cmd = params.safeGetStringParam("cmd")
        if cmd == "siteurl":
            self.setting_siteurl()
        elif cmd == "themeurl":
            self.setting_themeurl()
        elif cmd == "delete_index":
            self.delete_index()
        elif cmd == "create_index":
            self.create_index()
        elif cmd == "delete_subject_nav":
            self.delete_subject_nav()
        elif cmd == "create_subject_nav":
            self.create_subject_nav()
        elif cmd == "empty_updateinfo":
            self.empty_updateinfo()
        elif cmd == "add_updateinfo":
            self.empty_updateinfo()
        elif cmd == "empty_unit":
            self.empty_unit()
        elif cmd == "empty_user":
            self.empty_user()
        elif cmd == "empty_cache":
            self.empty_cache()
            
        # 检查统一用户配置
        try:
            
            request.setAttribute("JavaHome", System.getProperty("java.home"))
            request.setAttribute("UserMgrClientUrl", UserMgrClientModel.getUserMgrClientUrl())
            unitService = __spring__.getBean("unitService")
            rootUnit = unitService.getRootUnit()
            request.setAttribute("rootUnit", rootUnit)
            configService = __spring__.getBean("configService")            
            siteThemeUrlConfig = configService.getConfigByItemTypeAndName("jitar", "siteThemeUrl")
            if siteThemeUrlConfig == None:
                request.setAttribute("addSiteThemeUrl", request.getContextPath() + "/css/index/")
            else:
                request.setAttribute("siteThemeUrlConfig", siteThemeUrlConfig.getValue())
            
            strRootDir = request.getServletContext().getRealPath("/")
            strHtmlDir = strRootDir + "html" + File.separator
            _s = strRootDir + "index.html"
            indexHtmlFile = File(_s)
            if indexHtmlFile.exists():
                request.setAttribute("indexHtmlFile", "")
            
            _s = strHtmlDir + "subject_nav.html"
            subjectNavFile = File(_s)
            if subjectNavFile.exists():
                request.setAttribute("subjectNavFile", "")
                
            
            _s = strHtmlDir + "updateinfo.htm"
            updateInfoFile = File(_s)
            if updateInfoFile.exists():
                request.setAttribute("updateInfoFile", "")
            
            
            productConfigService = __spring__.getBean("ProductConfigService")
            request.setAttribute("IsValid", productConfigService.isValid())
            request.setAttribute("ProductName", productConfigService.getProductName())
            request.setAttribute("UnitLevel", productConfigService.getUnitLevel())
            request.setAttribute("UserCount", productConfigService.getUsercount())
            
        except BaseException, msg:
            request.setAttribute("errorMessage", str(msg))
            response.getWriter().write("检查过程中出现异常：<br/>" + str(msg))
        
        return "/WEB-INF/ftl/checksetting.ftl"    
        
    def setting_themeurl(self):
        configService = __spring__.getBean("configService")
        config = configService.getConfigByItemTypeAndName("jitar", "siteThemeUrl")
        siteThemeUrl = request.getContextPath() + "/css/index/"
        if config == None:
            config = Config()
            config.setItemType("jitar")
            config.setName("siteThemeUrl")
            config.setType("string")
            config.setTitle(u"网站的样式地址")
            config.setDescription(u"网站的样式地址  URL")
            config.setDefval(siteThemeUrl)
            config.setValue(siteThemeUrl)
            configService.createConfig(config)
        else:
            config.setDefval(siteThemeUrl)
            config.setValue(siteThemeUrl)
            configService.updateConfig(config)
        request.setAttribute("errorMessage", u"网站的样式地址  URL 配置完毕！已配置为：" + siteThemeUrl)
        
    def delete_index(self):
        strRootDir = request.getServletContext().getRealPath("/")
        _s = strRootDir + "index.html"
        indexHtmlFile = File(_s)
        if indexHtmlFile.exists():
            indexHtmlFile.delete()                
        request.setAttribute("errorMessage", u"首页缓存文件删除完毕！")
        
    def create_index(self):
        htmlGeneratorService = __spring__.getBean("htmlGeneratorService")
        htmlGeneratorService.SiteIndex()            
        request.setAttribute("errorMessage", u"首页缓存文件创建完毕！")
    
    def empty_updateinfo(self):
        strRootDir = request.getServletContext().getRealPath("/")
        strHtmlDir = strRootDir + "html" + File.separator
        _s = strHtmlDir + "updateinfo.htm"
        updateinfoFile = File(_s)
        if updateinfoFile.exists():
            updateinfoFile.delete()
        updateinfoFile.createNewFile()
        request.setAttribute("errorMessage", u"清空或者新建网站更新信息文件完毕！")
        
    def delete_subject_nav(self):
        strRootDir = request.getServletContext().getRealPath("/")
        strHtmlDir = strRootDir + "html" + File.separator
        _s = strHtmlDir + "subject_nav.html"
        subjectNavFile = File(_s)
        if subjectNavFile.exists():
            subjectNavFile.delete()         
        request.setAttribute("errorMessage", u"学科导航缓存文件删除完毕！")
        
    def create_subject_nav(self):
        subjectService = __spring__.getBean("subjectService")
        qry = Command(" SELECT DISTINCT metaGrade.gradeId FROM Subject Order By metaGrade.gradeId ASC") 
        mGradeId = qry.open()
        MetaGrade = ArrayList()
        metaSubject = ArrayList()
        for grade in mGradeId:
            mGrade = subjectService.getGrade(int(grade))
            MetaGrade.add(mGrade)
            subj = subjectService.getSubjectByGradeId(int(grade))
            m = ArrayList()
            if subj != None:
                for sj in range(0, subj.size()):
                    m.add(subj[sj].metaSubject)
                metaSubject.add({"gradeName" : mGrade.gradeName, "gradeId" : grade, "metaSubject" : m })
        map = HashMap()
        map.put("metaGrade", MetaGrade)
        map.put("meta_Grade", MetaGrade)
        map.put("SubjectNav", metaSubject)
        map.put("SiteUrl", request.getContextPath() + "/")
        
        templateProcessor = __spring__.getBean("templateProcessor")
        str = templateProcessor.processTemplate(map, "/WEB-INF/ftl/site_subject_nav.ftl", "utf-8")
        rootPath = JitarRequestContext.getRequestContext().getServletContext().getRealPath("/")
        fileIndex = rootPath + "html" + File.separator + "subject_nav.html"
        try:
            file = File(fileIndex)
            fw = OutputStreamWriter(FileOutputStream(file), "utf-8")
            #fw = FileWriter(file, False)
            fw.flush()
            fw.write(str)
            fw.close()
        finally:
            file = None
            fw = None
        request.setAttribute("errorMessage", u"生成学科导航缓存文件完毕！")
        
    def empty_unit(self):
        strRootDir = request.getServletContext().getRealPath("/")
        strUnitHtmlDir = strRootDir + "html" + File.separator + "unit" + File.separator
        dirFile = File(strUnitHtmlDir)
        if dirFile.exists() == False or dirFile.isDirectory() == False:
            request.setAttribute("errorMessage", u"单位缓存文件夹不存在！")
            return
        
        FileUtils.deleteQuietly(dirFile)
        request.setAttribute("errorMessage", u"删除所有单位缓存完毕！")
        
    def empty_user(self):
        strRootDir = request.getServletContext().getRealPath("/")
        strUserHtmlDir = strRootDir + "html" + File.separator + "user" + File.separator
        dirFile = File(strUserHtmlDir)
        if dirFile.exists() == False or dirFile.isDirectory() == False:
            request.setAttribute("errorMessage", u"用户缓存文件夹不存在！")
            return
        
        FileUtils.deleteQuietly(dirFile)
        request.setAttribute("errorMessage", u"删除所有用户缓存完毕！")
        
    def empty_cache(self):
        cache = __jitar__.cacheProvider.getCache('main')
        cache.clear()
        cache_key_list = cache.getAllKeys()
        if cache_key_list != None:                
            for key in cache_key_list:
                cache.remove(key)
        
        cache = __jitar__.cacheProvider.getCache('user')
        cache.clear()
        cache_key_list = cache.getAllKeys()
        if cache_key_list != None:                
            for key in cache_key_list:
                cache.remove(key)
                
                
        cache = __jitar__.cacheProvider.getCache('group')
        cache.clear()
        cache_key_list = cache.getAllKeys()
        if cache_key_list != None:                
            for key in cache_key_list:
                cache.remove(key)
                
                
        cache = __jitar__.cacheProvider.getCache('page')
        cache.clear()
        cache_key_list = cache.getAllKeys()
        if cache_key_list != None:                
            for key in cache_key_list:
                cache.remove(key)
                
                
        cache = __jitar__.cacheProvider.getCache('category')
        cache.clear()
        cache_key_list = cache.getAllKeys()
        if cache_key_list != None:                
            for key in cache_key_list:
                cache.remove(key)
                
                
        cache = __jitar__.cacheProvider.getCache('subject')
        cache.clear()
        cache_key_list = cache.getAllKeys()
        if cache_key_list != None:                
            for key in cache_key_list:
                cache.remove(key)
                
                
        cache = __jitar__.cacheProvider.getCache('unit')
        cache.clear()
        cache_key_list = cache.getAllKeys()
        if cache_key_list != None:                
            for key in cache_key_list:
                cache.remove(key)
        request.setAttribute("errorMessage", u"清空所有内存缓存执行完毕！")