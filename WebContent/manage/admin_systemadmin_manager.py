from accesscontrol_query import AccessControlQuery
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.pojos import AccessControl
from base_action import *
from cn.edustar.jitar.jython import BaseAdminAction

class admin_systemadmin_manager(BaseAdminAction, SubjectMixiner):
    def execute(self):
        if self.loginUser == None:
            return ActionResult.LOGIN
        accessControlService = __spring__.getBean("accessControlService")
        if accessControlService.isSystemAdmin(self.loginUser) == False:            
            self.addActionError(u"您不具有用户管理权限。")
            return ActionResult.ERROR
            
        self.params = ParamUtil(request)
        
        if request.getMethod() == "POST":
            self.accessControlService = __spring__.getBean("accessControlService")
            guids = self.params.safeGetIntValues("guid")
            for g in guids:
                if self.loginUser.userId != g:                    
                    accessControl = self.accessControlService.getAccessControlById(g)
                    if accessControl != None:
                        self.accessControlService.deleteAccessControl(accessControl)
            
        self.get_admin_list()       
        return "/WEB-INF/ftl/admin/admin_systemadmin_manager.ftl"

    def get_admin_list(self):        
        qry = AccessControlQuery("ac.accessControlId, ac.userId, ac.objectType, ac.objectId, ac.objectTitle, u.loginName, u.trueName, u.unitId, u.userIcon")
        qry.custormAndWhere = "(ac.objectType = 1 And ac.objectId = 0)"
        qry.orderType = 1
        pager = self.createPager()
        pager.totalRows = qry.count() 
        admin_list = qry.query_map(pager)
        request.setAttribute("pager", pager)
        request.setAttribute("admin_list", admin_list)
        
    def createPager(self):
        # pager.
        pager = self.params.createPager()
        pager.itemName = u"系统管理员"
        pager.itemUnit = u"名"
        pager.pageSize = 30
        return pager
