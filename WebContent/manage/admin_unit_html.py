#encoding=utf-8
from cn.edustar.jitar.jython import BaseAdminAction
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import Config, User
from base_action import ActionResult


class admin_unit_html(BaseAdminAction, ActionResult):
    def __init__(self):
        self.cfg_svc = __spring__.getBean('configService')
        self.accessControlService = __spring__.getBean('accessControlService')  

    def execute(self):
        htmlGeneratorService = __spring__.getBean('htmlGeneratorService')
        unitService = __jitar__.unitService
        
        params = ParamUtil(request)
        if params.existParam("unitId"):
            unitId = params.safeGetIntParam("unitId")
            unit = unitService.getUnitById(unitId)
            if unit != None:
                htmlGeneratorService.UnitIndex(unit)
                response.getWriter().write(unit.unitTitle + u" 的首页生成完毕。")            
            return
        
        if self.loginUser == None: 
            return ActionResult.LOGIN    
        if self.accessControlService.isSystemAdmin(self.loginUser) == False:
            self.addActionError(u"没有管理站点配置的权限，只有超级管理员才能进行管理。")
            return ActionResult.ERROR
        
        unitList = unitService.getAllUnitOrChildUnitList(None,[False])
        if len(unitList) < 1:
            self.addActionError(u"没有机构需要生成。")
            return ActionResult.ERROR
        id = []
        title = ""
        for u in unitList:
            id.append(u.unitId)
            title += "\"" +  u.unitTitle.replace("\"","\\\"") + "\","
        title = title[0:len(title)-1]
        request.setAttribute("id",id)
        request.setAttribute("title",title)
        return "/WEB-INF/ftl/admin/admin_unit_html2.ftl"
        minId = 0
        maxId = 1
        if len(unitList) > 1:minId = maxId= unitList[0].unitId
        for u in unitList:
            if u.unitId > maxId:maxId = u.unitId
            if u.unitId < minId:minId = u.unitId
        request.setAttribute("maxId",maxId)
        request.setAttribute("minId",minId)
        return "/WEB-INF/ftl/admin/admin_unit_html.ftl"
        htmlGeneratorService.UnitIndex()
        self.addActionMessage(u"各机构首页生成完毕.")
        return ActionResult.SUCCESS