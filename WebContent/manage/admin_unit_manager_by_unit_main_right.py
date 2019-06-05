from accesscontrol_query import *
from cn.edustar.jitar.util import ParamUtil
from base_action import *
from cn.edustar.jitar.pojos import AccessControl
from cn.edustar.jitar.jython import BaseAdminAction

class admin_unit_manager_by_unit_main_right(BaseAdminAction, SubjectMixiner):
    ADMIN_MAIN = "/WEB-INF/ftl/admin/main.ftl"
    ERROR = "/WEB-INF/ftl/Error.ftl"    
    SUCCESS = "/WEB-INF/ftl/success.ftl" 
    
    def __init__(self):
        self.params = ParamUtil(request)
        self.unitService = __spring__.getBean("unitService")
        self.accessControlService = __spring__.getBean("accessControlService")
        self.unit = None
        
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            if accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SUPERADMIN, 0) == False and accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SYSTEMUSERADMIN, 0) == False:      # 没有用户管理权.
                self.addActionError(u"您不具有用户管理权限。")
                return ActionResult.ERROR
        unitId = self.params.safeGetIntParam("unitId")
        self.unit = self.unitService.getUnitById(unitId)
        if self.unit == None:
            self.addActionError(u"无法加载机构.")
            return ActionResult.ERROR
        
        if request.getMethod() == "POST":
            guids = self.params.safeGetIntValues("guid")
            for g in guids:
                accessControl = self.accessControlService.getAccessControlById(g)
                if accessControl != None:
                    #print accessControl
                    self.accessControlService.deleteAccessControl(accessControl)
        
        self.get_unit_accesscontrol()
        
        ObjectType = [u"未知的权限", u"系统超级管理员", u"系统用户管理员", u"系统内容管理员", u"机构系统管理员", u"机构用户管理员", u"机构内容管理员", u"学科系统管理员", u"学科用户管理员", u"学科内容管理员", u"元学科内容管理员"]
        
        request.setAttribute("unit", self.unit)
        request.setAttribute("ObjectType", ObjectType)
        return "/WEB-INF/ftl/admin/admin_unit_manager_by_unit_main_right.ftl"

    def get_unit_accesscontrol(self):
        qry = AccessControlQuery("ac.accessControlId, ac.userId, ac.objectType, ac.objectId, u.loginName, u.trueName, u.unitId")
        qry.custormAndWhere = "(ac.objectType = 4 or ac.objectType = 5 or ac.objectType = 6 )"
        qry.objectId = self.unit.unitId
        user_list = qry.query_map(qry.count())
        request.setAttribute("user_list", user_list)