from accesscontrol_query import AccessControlQuery
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import AccessControl
from base_action import *
from cn.edustar.jitar.jython import BaseAdminAction

class admin_content_manager(BaseAdminAction, SubjectMixiner):
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:
            if accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SUPERADMIN, 0) == False and accessControlService.checkUserAccessControlIsExists(self.loginUser.userId, AccessControl.OBJECTTYPE_SYSTEMUSERADMIN, 0) == False:      # 没有用户管理权.
                self.addActionError(u"您不具有用户管理权限。")
                return ActionResult.ERROR
            
        self.params = ParamUtil(request)
        
        if request.getMethod() == "POST":
            self.accessControlService = __spring__.getBean("accessControlService")
            guids = self.params.safeGetIntValues("guid")
            for g in guids:
                accessControl = self.accessControlService.getAccessControlById(g)
                if accessControl != None:
                    self.accessControlService.deleteAccessControl(accessControl)
            
        self.get_admin_list()
        ObjectType = ["未知的权限", "系统超级管理员", "系统用户管理员", "系统内容管理员", "机构系统管理员", "机构用户管理员", "机构内容管理员", "学科系统管理员", "学科用户管理员", "学科内容管理员", "元学科内容管理员"]
        request.setAttribute("ObjectType", ObjectType)
        return "/WEB-INF/ftl/admin/admin_content_manager.ftl"

    def get_admin_list(self):        
        qry = AccessControlQuery("ac.accessControlId, ac.userId, ac.objectType, ac.objectId, ac.objectTitle, u.loginName,u.trueName,u.unitId")
        qry.custormAndWhere = "(ac.objectType = 3)"
        qry.orderType = 1
        pager = self.createPager()
        pager.totalRows = qry.count() 
        admin_list = qry.query_map(pager)
        request.setAttribute("pager", pager)
        request.setAttribute("admin_list", admin_list)
        
    def createPager(self):
        # pager.
        pager = self.params.createPager()
        pager.itemName = u"管理员"
        pager.itemUnit = u"名"
        pager.pageSize = 30
        return pager
