from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.jython import BaseAdminAction
from base_action import ActionResult

class admin_mashup_platform(BaseAdminAction, ActionResult):    
    ERROR = "/WEB-INF/ftl/Error.ftl"
    ACCESS_DENIED = "/WEB-INF/ftl/Error.ftl"
    ACCESS_ERROR = "/WEB-INF/ftl/mengv1/access_error.ftl"
    SUCCESS = "/WEB-INF/ftl/success.ftl"
    LOGIN = "/login.jsp"
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.mashupService = __spring__.getBean("mashupService")        
        
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        if self.loginUser.loginName != "admin":
            self.addActionError(u"目前只有系统管理员才可以进行管理.")
            return ActionResult.ERROR
        
        cmd = self.params.safeGetStringParam("cmd")
        if request.getMethod() == "POST":
            if cmd == "save":                    
                mashIds = self.params.safeGetIntValues("mashId")
                for mashId in mashIds:
                    mashupPlatform = self.mashupService.getMashupPlatformById(mashId)
                    if mashupPlatform != None:
                        oldUnitName = mashupPlatform.getPlatformName()
                        oldUnitHref = mashupPlatform.getPlatformHref()
                        newUnitName = self.params.safeGetStringParam("unitName_new_" + str(mashId))
                        newUnitHref = self.params.safeGetStringParam("unitHref_new_" + str(mashId))
                        if oldUnitName != newUnitName or oldUnitHref != newUnitHref:
                            mashupPlatform.setPlatformName(newUnitName)
                            mashupPlatform.setPlatformHref(newUnitHref)
                            self.mashupService.saveOrUpdateMashupPlatform(mashupPlatform)
                            # 更新文章
                            self.mashupService.updateMashupContentByPlatform(mashupPlatform)
            elif cmd == "delete":
                gs = self.params.safeGetIntValues("guid")
                for g in gs:
                    mashupPlatform = self.mashupService.getMashupPlatformById(g)
                    if mashupPlatform != None:
                        self.mashupService.deleteMashupPlatform(mashupPlatform)
                        
            elif cmd == "approve":
                gs = self.params.safeGetIntValues("guid")
                for g in gs:
                    mashupPlatform = self.mashupService.getMashupPlatformById(g)
                    if mashupPlatform != None:
                        mashupPlatform.setPlatformState(0)
                        self.mashupService.saveOrUpdateMashupPlatform(mashupPlatform)
            elif cmd == "lock":
                gs = self.params.safeGetIntValues("guid")
                for g in gs:
                    mashupPlatform = self.mashupService.getMashupPlatformById(g)
                    if mashupPlatform != None:
                        mashupPlatform.setPlatformState(1)
                        self.mashupService.saveOrUpdateMashupPlatform(mashupPlatform)                
                
        unit_list = self.mashupService.getAllMashupPlatform(False)
        request.setAttribute("unit_list",unit_list)
        return "/WEB-INF/mashup/admin_mashup_platform.ftl"