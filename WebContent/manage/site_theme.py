from base_action import *
from base_action import ActionResult
from cn.edustar.jitar.pojos import User, Config
from cn.edustar.jitar.pojos import SiteTheme
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import CommonUtil
from cn.edustar.jitar.util import ParamUtil
from javax.servlet import ServletContext
from cn.edustar.jitar.model import SiteUrlModel
from java.io import File

class site_theme(BaseAdminAction):
    # 定义要返回的页面常量.
    ADMIN_MAIN = "/WEB-INF/ftl/admin/main.ftl"
    ERROR = "/WEB-INF/ftl/Error.ftl"    
    SUCCESS = "/WEB-INF/ftl/success.ftl"
    
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.theme_svc = __jitar__.getSiteThemeService()
        self.configService = __spring__.getBean('configService')
        
    def execute(self):
        user = self.loginUser
        if user == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(user) == False:
            self.addActionError(u"没有管理权限.")
            return ActionResult.ERROR
        
        self.siteThemeId = self.params.safeGetIntParam("siteThemeId")
        
        if request.getMethod() == "POST":
            cmd = self.params.getStringParam("cmd")
            if cmd == "new":
                return self.new_theme()
            if cmd == "delete":
                return self.delete_theme()
            if cmd == "enable":
                return self.enable_theme()
            if cmd == "import":
                self.import_theme()
            if cmd == "edit":
                return self.edit_theme()
            if cmd == "reset":
                return self.reset_theme()       
                
        return self.get_theme_list()
    
    def get_theme_list(self):
        sitetheme = self.theme_svc.getSiteThemeById(self.siteThemeId)
        theme_list = self.theme_svc.getAllTheme()
        request.setAttribute("theme_list", theme_list)
        if sitetheme != None:
            request.setAttribute("sitetheme", sitetheme)            
        response.setContentType("text/html; charset=UTF-8")
        return "/WEB-INF/ftl/admin/site_theme.ftl"
    
    def enable_theme(self):
        usedtheme = self.params.getIntParam("usedtheme")
        if usedtheme == 0:
            self.addActionError(u"无效的标识。")
            return self.ERROR
                
        theme = self.theme_svc.getSiteThemeById(usedtheme)
        if theme == None:
            self.addActionError(u"不能加载样式。")
            return self.ERROR
        
        # 检查所选定的样式文件夹是否真的存在
        servlet_ctxt = self.theme_svc.getServletContext()
        filePath = servlet_ctxt.getRealPath("/") + "theme" + File.separator + "site" + File.separator + theme.folder + File.separator
        f = File(filePath)
        if f.exists() == False:
            self.addActionError(u"你所选定的样式的物理文件夹并不存在，无法设置你所选定的样式。")
            return self.ERROR
        
        #先删除启用的样式
        theme_list = self.theme_svc.getAllTheme()
        for t in theme_list:
            if t.status == 1:
                t.setStatus(0)
                self.theme_svc.saveOrUpdateSiteTheme(theme)
        
        theme.setStatus(1)
        self.theme_svc.saveOrUpdateSiteTheme(theme)
        
        #更新缓存
        themeUrl = SiteUrlModel.getSiteUrl() + "theme/site/" + theme.folder + "/"
        servlet_ctxt.setAttribute("SiteThemeUrl", themeUrl)
        self.saveThemeUrlToConfig(themeUrl)
        response.sendRedirect("site_theme.py")
        
    def new_theme(self):
        theme_title = self.params.safeGetStringParam("themeTitle")
        theme_folder = self.params.safeGetStringParam("themeFolder")
        
        if theme_title == "":
            self.addActionError(u"请输入样式名称。")
            return self.ERROR
        
        if theme_folder == "":
            self.addActionError(u"请输入样式目录。")
            return self.ERROR
        
        theme = SiteTheme()
        theme.setTitle(theme_title)
        theme.setFolder(theme_folder)
        theme.setStatus(0)
        self.theme_svc.saveOrUpdateSiteTheme(theme)
        response.sendRedirect("site_theme.py")
        
    def delete_theme(self):
        guids = self.params.safeGetIntValues("guid")
        candeletefile = self.params.safeGetStringParam("deletefile")
        servlet_ctxt = self.theme_svc.getServletContext()
        for guid in guids:
            theme = self.theme_svc.getSiteThemeById(guid)
            if theme != None:
                if theme.status != 1:
                    self.theme_svc.deleteSiteTheme(theme)
                    #删除物理文件
                    if candeletefile == "1":
                        filePath = servlet_ctxt.getRealPath("/") + "theme" + File.separator + "site" + File.separator + theme.folder + File.separator
                        CommonUtil.deleteDir(filePath)
        response.sendRedirect("site_theme.py")
                        
    def import_theme(self):
        servlet_ctxt = self.theme_svc.getServletContext()
        filePath = servlet_ctxt.getRealPath("/") + "theme" + File.separator + "site" + File.separator
        file = File(filePath)
        num = 0
        if file.exists() == True:
            fs = file.list()
            num = 0
            for f in fs:
                fd = File(filePath + f)
                if fd.isDirectory() == True:
                    theme = self.theme_svc.getSiteThemeByFolderName(f)
                    if theme == None:
                        theme = SiteTheme()
                        theme.setTitle(f)
                        theme.setFolder(f)
                        theme.setStatus(0)
                        self.theme_svc.saveOrUpdateSiteTheme(theme)
                        num = num + 1
        retMsg = str(num)
        request.setAttribute("retMsg", retMsg)       
        return "/WEB-INF/ftl/admin/site_theme.ftl"
    
    def edit_theme(self):
        theme = self.theme_svc.getSiteThemeById(self.siteThemeId)        
        theme_title = self.params.safeGetStringParam("themeTitle")
        theme_folder = self.params.safeGetStringParam("themeFolder")
        
        if theme_title == "":
            self.addActionError(u"请输入样式名称。")
            return self.ERROR
        
        if theme_folder == "":
            self.addActionError(u"请输入样式目录。")
            return self.ERROR

        theme.setTitle(theme_title)
        theme.setFolder(theme_folder)
        self.theme_svc.saveOrUpdateSiteTheme(theme)
        response.sendRedirect("site_theme.py")
    
    def reset_theme(self):
        self.theme_svc.resetDefaultSiteTheme()
        themeUrl = self.theme_svc.getSiteThemeUrl()
        if themeUrl != None and themeUrl != "":
            self.saveThemeUrlToConfig(themeUrl)
        response.sendRedirect("site_theme.py") 
        
    def saveThemeUrlToConfig(self, themeUrl):
        config = self.configService.getConfigByItemTypeAndName("jitar", "siteThemeUrl")
        if config == None:
            config = Config()
            config.setItemType("jitar")
            config.setName("siteThemeUrl")
            config.setValue(themeUrl)
            config.setType("string")
            config.setDefval("")
            config.setTitle(u"网站的样式地址。")
            config.setDescription(u"网站的样式地址。")
            self.configService.createConfig(config)
        else:
            config.value = themeUrl
            self.configService.updateConfig(config)
        self.configService.reloadConfig()
