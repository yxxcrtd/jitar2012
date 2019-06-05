from base_action import *
from base_action import ActionResult
from cn.edustar.jitar.pojos import User
from cn.edustar.jitar.pojos import SiteIndexPart
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import ParamUtil

class site_index_add(BaseAdminAction):
    def __init__(self):
        self.params = ParamUtil(request)
        self.siteIndexPartService = __spring__.getBean("siteIndexPartService")
        
    def execute(self):
        user = self.loginUser
        if user == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(user) == False:
            self.addActionError(u"没有管理权限.")
            return ActionResult.ERROR
        
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"只有系统管理员才可进行配置.")
            return ActionResult.ERROR
        
        siteIndexPartId = self.params.safeGetIntParam("partId")
        moduleName = self.params.safeGetStringParam("moduleName")
        moduleZone = self.params.safeGetIntParam("moduleZone")
        moduleDisplay = self.params.safeGetIntParam("moduleDisplay")
        moduleHeight = self.params.safeGetIntParam("moduleHeight")
        moduleWidth = self.params.safeGetIntParam("moduleWidth")
        multiColumn = self.params.safeGetIntParam("multiColumn")
        moduleOrder = self.params.safeGetIntParam("moduleOrder")
        showBorder = self.params.safeGetIntParam("showBorder")
        content = self.params.safeGetStringParam("content")
        if siteIndexPartId == 0:
            siteIndexPart = SiteIndexPart()
            siteIndexPart.setSiteIndexPartId(0)
            siteIndexPart.setModuleName(u"未命名")
            siteIndexPart.setModuleZone(0)
            siteIndexPart.setModuleDisplay(1)
            siteIndexPart.setModuleHeight(0)
            siteIndexPart.setModuleWidth(300)
            siteIndexPart.setTextLength(0)
            siteIndexPart.setMultiColumn(1)
            siteIndexPart.setModuleOrder(0)
            siteIndexPart.setShowBorder(1)
            siteIndexPart.setContent("<span></span>")            
        else:
            siteIndexPart = self.siteIndexPartService.getSiteIndexPartById(siteIndexPartId)
            if siteIndexPart == None:
                self.addActionError(u"无法加载对象。")
                return ActionResult.ERROR
        if request.getMethod() == "POST":
            if moduleName == "":
                self.addActionError(u"模块名称不能为空。")
                return ActionResult.ERROR
            
            if content == "":
                self.addActionError(u"模块内容不能为空。")
                return ActionResult.ERROR
            siteIndexPart.setModuleName(moduleName)
            siteIndexPart.setModuleZone(moduleZone)
            siteIndexPart.setModuleDisplay(moduleDisplay)
            siteIndexPart.setModuleHeight(moduleHeight)
            siteIndexPart.setModuleWidth(moduleWidth)
            siteIndexPart.setTextLength(0)
            siteIndexPart.setModuleOrder(moduleOrder)
            siteIndexPart.setMultiColumn(multiColumn)
            siteIndexPart.setShowBorder(showBorder)            
            siteIndexPart.setContent(content)
            siteIndexPart.setPartType(0)
            siteIndexPart.setShowType(0)
            self.siteIndexPartService.saveOrUpdateSiteIndexPart(siteIndexPart)
            response.sendRedirect("site_index.py")
        
        request.setAttribute("siteIndexPart", siteIndexPart)
        return "/WEB-INF/ftl/admin/site_index_add.ftl"
