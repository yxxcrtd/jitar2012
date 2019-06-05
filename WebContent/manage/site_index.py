from base_action import *
from base_action import ActionResult
from cn.edustar.jitar.pojos import User
from cn.edustar.jitar.pojos import SiteIndexPart
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import ParamUtil

class site_index(BaseAdminAction):
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
        
        if request.getMethod() == "POST":
            cmd = self.params.safeGetStringParam("cmd")
            if cmd == "display":
                siteIndexPartId = self.params.safeGetIntParam("siteId")
                siteDisplay = self.params.safeGetIntParam("siteDisplay")
                siteIndexPart = self.siteIndexPartService.getSiteIndexPartById(siteIndexPartId)
                if siteIndexPart == None:
                    self.addActionError(u"无法加载对象。")
                    return ActionResult.ERROR
                siteIndexPart.setModuleDisplay(siteDisplay)
                self.siteIndexPartService.saveOrUpdateSiteIndexPart(siteIndexPart)
            elif cmd == "hide":
                guids = self.params.safeGetIntValues("guid")
                for g in guids:
                    siteIndexPart = self.siteIndexPartService.getSiteIndexPartById(g)
                    if siteIndexPart != None:
                        siteIndexPart.setModuleDisplay(0)
                        self.siteIndexPartService.saveOrUpdateSiteIndexPart(siteIndexPart)
                                                
            elif cmd == "show":
                guids = self.params.safeGetIntValues("guid")
                for g in guids:
                    siteIndexPart = self.siteIndexPartService.getSiteIndexPartById(g)
                    if siteIndexPart != None:
                        siteIndexPart.setModuleDisplay(1)
                        self.siteIndexPartService.saveOrUpdateSiteIndexPart(siteIndexPart)
                        
            elif cmd == "delete":
                guids = self.params.safeGetIntValues("guid")
                for g in guids:
                    siteIndexPart = self.siteIndexPartService.getSiteIndexPartById(g)
                    if siteIndexPart != None:
                        self.siteIndexPartService.deleteSiteIndexPart(siteIndexPart)  
            elif cmd == "order":
                guids = self.params.safeGetIntValues("nav_id")
                for g in guids:
                    siteOrder = self.params.safeGetIntParam("z_" + str(g))
                    siteIndexPart = self.siteIndexPartService.getSiteIndexPartById(g)
                    if siteIndexPart != None:
                        siteIndexPart.setModuleOrder(siteOrder)
                        self.siteIndexPartService.saveOrUpdateSiteIndexPart(siteIndexPart)
            elif cmd == "zone":
                siteIndexPartId = self.params.safeGetIntParam("siteId")
                zone = self.params.safeGetIntParam("siteDisplay")
                siteIndexPart = self.siteIndexPartService.getSiteIndexPartById(siteIndexPartId)
                if siteIndexPart == None:
                    self.addActionError(u"无法加载对象。")
                    return ActionResult.ERROR
                siteIndexPart.setModuleZone(zone)
                self.siteIndexPartService.saveOrUpdateSiteIndexPart(siteIndexPart)
            elif cmd == "showType":
                zoneId = self.params.safeGetIntParam("zoneId")
                showType = self.params.safeGetIntParam("showType")
                #print "showType=",showType
                #print "zoneId=",zoneId
                siteIndexPartList = self.siteIndexPartService.getSiteIndexPartList(None)
                for part in siteIndexPartList:
                    if part.moduleZone == zoneId and part.multiColumn != showType:
                        part.multiColumn = showType
                        self.siteIndexPartService.saveOrUpdateSiteIndexPart(part)
        
        siteItemList = self.siteIndexPartService.getSiteIndexPartList(None)
        #区域定义
        webpartZoneCount = []
        webpartZoneShowType = []
        # 默认设置5个区域
        for part in range(5):
            count = 0
            showType = 0
            for p in siteItemList:                
                if p.moduleZone - 1 == part:
                    count += 1
                    showType = p.multiColumn
            webpartZoneCount.append(count)            
            webpartZoneShowType.append(showType)
        request.setAttribute("webpartZoneCount",webpartZoneCount)
        #每个区域的显示方式只能有一种
        request.setAttribute("webpartZoneShowType",webpartZoneShowType)
        request.setAttribute("siteItemList",siteItemList)
        cache = __jitar__.cacheProvider.getCache('main')
        cache.clear()
        #print "webpartZoneShowType=",webpartZoneShowType
        return "/WEB-INF/ftl/admin/site_index.ftl"