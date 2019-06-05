from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import ParamUtil, CommonUtil
from base_action import ActionResult
from java.io import File, FileOutputStream, OutputStreamWriter
from java.net import URLDecoder
from cn.edustar.jitar.pojos import SiteIndexPart

class admin_site_update(BaseAdminAction, ActionResult):
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"没有管理站点配置的权限，只有超级管理员才能进行管理。")
            return ActionResult.ERROR
        
        strFile = request.getServletContext().getRealPath("/")
        strFile = strFile + "html" + File.separator
        strFile = URLDecoder.decode(strFile, "utf-8")
        file = File(strFile)
        if file.exists() == False:
            file.mkdirs()
        #创建学科导航
        strFile = strFile + "updateinfo.htm"
        file = File(strFile)
        if request.getMethod() == "POST":
            params = ParamUtil(request)
            html = params.safeGetStringParam("updateInfo")
            fw = OutputStreamWriter(FileOutputStream(file), "utf-8")
            fw.flush()
            fw.write(html)
            fw.close()
            siteIndexPartService = __spring__.getBean("siteIndexPartService")
            if html=="":
                siteIndexPart = siteIndexPartService.getSiteIndexPartByModuleName(u"系统维护通知")
                if siteIndexPart != None:
                    siteIndexPartService.deleteSiteIndexPart(siteIndexPart)
            else:
                siteIndexPart = siteIndexPartService.getSiteIndexPartByModuleName(u"系统维护通知")
                if siteIndexPart == None:
                    siteIndexPart = SiteIndexPart()
                siteIndexPart.setModuleName(u"系统维护通知")
                siteIndexPart.setModuleZone(1)
                siteIndexPart.setModuleOrder(0)
                siteIndexPart.setModuleDisplay(1)
                siteIndexPart.setModuleHeight(0)
                siteIndexPart.setContent(html)
                siteIndexPart.setPartType(100)
                siteIndexPart.setShowType(0)
                siteIndexPart.setShowBorder(0)
                siteIndexPartService.saveOrUpdateSiteIndexPart(siteIndexPart)
            request.setAttribute("deleteCache", "1")
        else:
            html = CommonUtil.readFile(file.getCanonicalPath(), "UTF-8")
            request.setAttribute("deleteCache", "0")
            
        
        request.setAttribute("updateInfo", html)
        return "/WEB-INF/ftl/admin/admin_site_update.ftl"