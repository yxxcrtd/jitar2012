from base_action import *
from base_action import ActionResult
from cn.edustar.jitar.pojos import User
from cn.edustar.jitar.pojos import SiteIndexPart
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import ParamUtil

class site_index_contentspace_add(BaseAdminAction):
    def __init__(self):
        self.params = ParamUtil(request)
        self.siteIndexPartService = __spring__.getBean("siteIndexPartService")
        self.contentSpaceService = __spring__.getBean("contentSpaceService")
        
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
        textLength = self.params.safeGetIntParam("textLength")
        multiColumn = self.params.safeGetIntParam("multiColumn")
        moduleOrder = self.params.safeGetIntParam("moduleOrder")
        showBorder = self.params.safeGetIntParam("showBorder")
        showCount = self.params.safeGetIntParam("showCount")
        contentSpaceId = self.params.safeGetIntParam("contentSpaceId")
        showType = self.params.safeGetIntParam("showType")
        if showType == 0:
            showType = 1
        if showCount == 0:
            showCount = 8
        if siteIndexPartId == 0:
            siteIndexPart = SiteIndexPart()
            siteIndexPart.setSiteIndexPartId(0)
            siteIndexPart.setModuleName(u"未命名")
            siteIndexPart.setModuleZone(0)
            siteIndexPart.setModuleDisplay(1)
            siteIndexPart.setModuleHeight(0)
            siteIndexPart.setModuleWidth(300)
            siteIndexPart.setTextLength(12)
            siteIndexPart.setMultiColumn(1)
            siteIndexPart.setModuleOrder(0)
            siteIndexPart.setShowBorder(1)
            siteIndexPart.setSysCateId(0)
            siteIndexPart.setShowCount(8)
            siteIndexPart.setPartType(2)
        else:
            siteIndexPart = self.siteIndexPartService.getSiteIndexPartById(siteIndexPartId)
            if siteIndexPart == None:
                self.addActionError(u"无法加载对象。")
                return ActionResult.ERROR
        if request.getMethod() == "POST":
            if moduleName == "":
                self.addActionError(u"模块名称不能为空。")
                return ActionResult.ERROR
            
            siteIndexPart.setModuleName(moduleName)
            siteIndexPart.setModuleZone(moduleZone)
            siteIndexPart.setModuleDisplay(moduleDisplay)
            siteIndexPart.setModuleHeight(moduleHeight)
            siteIndexPart.setModuleWidth(moduleWidth)
            siteIndexPart.setTextLength(textLength)
            siteIndexPart.setMultiColumn(multiColumn)
            siteIndexPart.setModuleOrder(moduleOrder)
            siteIndexPart.setShowBorder(showBorder)            
            siteIndexPart.setSysCateId(contentSpaceId)
            siteIndexPart.setShowCount(showCount)            
            siteIndexPart.setShowType(showType)
            self.siteIndexPartService.saveOrUpdateSiteIndexPart(siteIndexPart)
            cache = __jitar__.cacheProvider.getCache('main')
            cache.clear()
            response.sendRedirect("site_index.py")
        self.get_content_space_cate()
        request.setAttribute("siteIndexPart", siteIndexPart)
        return "/WEB-INF/ftl/admin/site_index_contentspace_add.ftl"
    
    def get_content_space_cate(self):
        catelist = self.contentSpaceService.getAllContentSpaceList(0, 0)
        request.setAttribute("catelist", catelist)